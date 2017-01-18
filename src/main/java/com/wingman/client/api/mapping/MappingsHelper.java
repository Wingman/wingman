package com.wingman.client.api.mapping;

import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.wingman.client.ClientSettings;
import com.wingman.client.api.net.HttpClient;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;

import java.io.FileReader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MappingsHelper {

    public static final ClassInfo[] CLASSES;
    public static final MethodInfo[] METHODS;
    public static final FieldInfo[] FIELDS;

    public static final Map<String, String> obfClasses = new HashMap<>();
    public static final Map<String, String> deobfClasses = new HashMap<>();

    public static final Map<String, Set<MethodInfo>> obfMethods = new HashMap<>();
    public static final Map<String, MethodInfo> deobfMethods = new HashMap<>();

    public static final Map<String, Set<FieldInfo>> obfFields = new HashMap<>();
    public static final Map<String, FieldInfo> deobfFields = new HashMap<>();

    private static BigInteger integerModulus = new BigInteger("4294967296");
    private static BigInteger longModulus = new BigInteger("18446744073709551616");

    public static void addInstructions(InsnList list, AbstractInsnNode... instructions) {
        for (AbstractInsnNode i : instructions) {
            list.add(i);
        }
    }

    public static AbstractInsnNode findNode(AbstractInsnNode start, int opcode) {
        return findNode(start, opcode, 0);
    }

    public static AbstractInsnNode findNode(AbstractInsnNode start, int opcode, int nodesToSkip) {
        while (start != null) {
            if (start.getOpcode() == opcode) {
                if (nodesToSkip == 0) {
                    return start;
                }
                nodesToSkip--;
            }
            start = start.getNext();
        }
        return null;
    }

    public static long getMMI(long multiplier, boolean isInt) {
        BigInteger modulus = isInt ? integerModulus : longModulus;

        BigInteger multiplierInt = BigInteger.valueOf(multiplier);
        BigInteger gcd = multiplierInt.gcd(modulus);

        if (gcd.equals(BigInteger.ONE)) {
            BigInteger result = multiplierInt.modInverse(modulus);
            return isInt ? result.intValue() : result.longValue();
        }

        return isInt ? gcd.intValue() : gcd.longValue();
    }

    public static String toUpperCaseFirstCharacter(String name) {
        char[] chars = name.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    static {
        HttpClient httpClient = new HttpClient();

        long localSize = ClientSettings.MAPPINGS_FILE
                .toFile()
                .length();

        String mappingsUrl = "https://wingman.github.io/download/mappings.json";

        try {
            long remoteSize = httpClient
                    .getContentLength(mappingsUrl);

            if (remoteSize != localSize) {
                httpClient.downloadFileSync(mappingsUrl, ClientSettings.MAPPINGS_FILE);
            }

            JsonParser parser = new JsonParser();

            JsonObject rootObject;

            try (JsonReader reader = new JsonReader(new FileReader(ClientSettings.MAPPINGS_FILE.toFile()))) {
                rootObject = parser
                        .parse(reader)
                        .getAsJsonObject();
            }

            Gson gson = new Gson();

            JsonArray classes = rootObject.getAsJsonArray("classes");

            CLASSES = new ClassInfo[classes.size()];

            for (int i = 0; i < classes.size(); i++) {
                CLASSES[i] = gson.fromJson(classes.get(i), ClassInfo.class);
            }

            JsonArray methods = rootObject.getAsJsonArray("methods");

            METHODS = new MethodInfo[methods.size()];

            for (int i = 0; i < methods.size(); i++) {
                METHODS[i] = gson.fromJson(methods.get(i), MethodInfo.class);
            }

            JsonArray fields = rootObject.getAsJsonArray("fields");

            FIELDS = new FieldInfo[fields.size()];

            for (int i = 0; i < fields.size(); i++) {
                FIELDS[i] = gson.fromJson(fields.get(i), FieldInfo.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw Throwables.propagate(e);
        }

        populateHelperFields();
    }

    private static void populateHelperFields() {
        for (ClassInfo classInfo : CLASSES) {
            obfClasses.put(classInfo.name, classInfo.realName);
            deobfClasses.put(classInfo.realName, classInfo.name);
        }

        for (MethodInfo methodInfo : METHODS) {
            obfMethods.computeIfAbsent(methodInfo.owner, k -> new HashSet<>())
                    .add(methodInfo);

            if (methodInfo.isStatic) {
                deobfMethods.put(methodInfo.realName, methodInfo);
            } else {
                String cleanName = obfClasses.get(methodInfo.owner);
                if (cleanName != null) {
                    deobfMethods.put(cleanName + "." + methodInfo.realName, methodInfo);
                }
            }
        }

        for (FieldInfo fieldInfo : FIELDS) {
            obfFields.computeIfAbsent(fieldInfo.owner, k -> new HashSet<>())
                    .add(fieldInfo);

            if (fieldInfo.isStatic) {
                deobfFields.put(fieldInfo.realName, fieldInfo);
            } else {
                String cleanName = obfClasses.get(fieldInfo.owner);
                if (cleanName != null) {
                    deobfFields.put(cleanName + "." + fieldInfo.realName, fieldInfo);
                }
            }
        }
    }
}

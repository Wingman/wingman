package com.wingman.client.api.mapping;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import okhttp3.Response;
import com.wingman.client.ClientSettings;
import com.wingman.client.api.net.HttpClient;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class MappingsHelper {

    public static HashCode gamepackHash;

    public static ClassInfo[] classes;
    public static MethodInfo[] methods;
    public static FieldInfo[] fields;

    public static Map<String, String> obfClasses = new HashMap<>();
    public static Map<String, String> deobfClasses = new HashMap<>();

    public static Map<String, Set<MethodInfo>> obfMethods = new HashMap<>();
    public static Map<String, MethodInfo> deobfMethods = new HashMap<>();

    public static Map<String, Set<FieldInfo>> obfFields = new HashMap<>();
    public static Map<String, FieldInfo> deobfFields = new HashMap<>();

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

    public static String toUpperCaseFirstCharacter(String name) {
        char[] chars = name.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    static {
        try {
            File mappingsFile = ClientSettings.MAPPINGS_FILE
                    .toFile();

            boolean shouldUpdate = true;

            HttpClient httpClient = new HttpClient();

            if (mappingsFile.exists()) {
                HashCode localHashCode = Files
                        .asByteSource(mappingsFile)
                        .hash(Hashing.sha256());

                Response response = httpClient
                        .downloadUrlSync("https://wingman.github.io/download/mappings.hash");

                HashCode remoteHashCode = HashCode
                        .fromString(response
                        .body()
                        .string());

                if (localHashCode.equals(remoteHashCode)) {
                    shouldUpdate = false;
                }
            }

            if (shouldUpdate) {
                httpClient.downloadFileSync("https://wingman.github.io/download/mappings.json",
                        ClientSettings.MAPPINGS_FILE);
            }

            JsonParser parser = new JsonParser();

            JsonObject rootObject;

            try (JsonReader reader = new JsonReader(new FileReader(mappingsFile))) {
                rootObject = parser
                        .parse(reader)
                        .getAsJsonObject();
            }

            gamepackHash = HashCode
                    .fromString(rootObject
                            .getAsJsonPrimitive("gameHash")
                            .getAsString());

            Gson gson = new Gson();

            JsonArray classes = rootObject.getAsJsonArray("classes");

            MappingsHelper.classes = new ClassInfo[classes.size()];

            for (int i = 0; i < classes.size(); i++) {
                MappingsHelper.classes[i] = gson.fromJson(classes.get(i), ClassInfo.class);
            }

            JsonArray methods = rootObject.getAsJsonArray("methods");

            MappingsHelper.methods = new MethodInfo[methods.size()];

            for (int i = 0; i < methods.size(); i++) {
                MappingsHelper.methods[i] = gson.fromJson(methods.get(i), MethodInfo.class);
            }

            JsonArray fields = rootObject.getAsJsonArray("fields");

            MappingsHelper.fields = new FieldInfo[fields.size()];

            for (int i = 0; i < fields.size(); i++) {
                MappingsHelper.fields[i] = gson.fromJson(fields.get(i), FieldInfo.class);
            }

            populateHelperFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void populateHelperFields() {
        for (ClassInfo classInfo : classes) {
            if (doesClassExist("com.wingman.client.api.generated." + classInfo.realName)) {
                obfClasses.put(classInfo.name, classInfo.realName);
                deobfClasses.put(classInfo.realName, classInfo.name);
            } else {
                System.out.println("Missing generated API for class "
                        + classInfo.realName + " (" + classInfo.name + ")");
            }
        }

        for (MethodInfo methodInfo : methods) {
            boolean shouldContinue = true;

            Type[] realArgumentTypes = Type
                    .getArgumentTypes(methodInfo.realDesc);

            for (Type argumentType : realArgumentTypes) {
                String argumentDescriptor = argumentType
                        .getDescriptor()
                        .replace("[", "");

                if (argumentDescriptor.charAt(0) == 'L'
                        && !doesClassExist(argumentDescriptor)) {
                    shouldContinue = false;
                }
            }

            if (!shouldContinue) {
                continue;
            }

            String realTypeDescriptor = Type
                    .getType(methodInfo.realType)
                    .getDescriptor()
                    .replace("[", "");

            if (realTypeDescriptor.charAt(0) == 'L'
                    && !doesClassExist(realTypeDescriptor)) {
                continue;
            }

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

        for (FieldInfo fieldInfo : fields) {
            String realTypeDescriptor = Type
                    .getType(fieldInfo.realType)
                    .getDescriptor()
                    .replace("[", "");

            if (realTypeDescriptor.charAt(0) == 'L'
                    && !doesClassExist(realTypeDescriptor)) {
                continue;
            }

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

    private static boolean doesClassExist(String className) {
        String cleanClassName = className
                .replace("/", ".");

        if (cleanClassName.charAt(0) == 'L') {
            cleanClassName = cleanClassName
                    .substring(1, cleanClassName.length() - 1);
        }

        try {
            Class.forName(cleanClassName);
        } catch (ClassNotFoundException e) {
            return false;
        }

        return true;
    }

    private MappingsHelper() {
        // This class should not be instantiated
    }
}

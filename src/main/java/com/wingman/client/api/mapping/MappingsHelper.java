package com.wingman.client.api.mapping;

import com.wingman.client.api.generated.MappingsDatabase;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MappingsHelper {

    public static final Map<String, String> obfClasses = new HashMap<>();
    public static final Map<String, String> deobfClasses = new HashMap<>();

    public static final Map<String, Set<MethodInfo>> obfMethods = new HashMap<>();
    public static final Map<String, MethodInfo> deobfMethods = new HashMap<>();

    public static final Map<String, Set<FieldInfo>> obfFields = new HashMap<>();
    public static final Map<String, FieldInfo> deobfFields = new HashMap<>();

    private static BigInteger integerModulus = new BigInteger("4294967296");
    private static BigInteger longModulus = new BigInteger("18446744073709551616");

    static {
        for (ClassInfo classInfo : MappingsDatabase.CLASSES) {
            obfClasses.put(classInfo.obfName, classInfo.cleanName);
            deobfClasses.put(classInfo.cleanName, classInfo.obfName);
        }

        for (MethodInfo methodInfo : MappingsDatabase.METHODS) {
            Set<MethodInfo> methods = obfMethods.get(methodInfo.owner);
            if (methods == null) {
                methods = new HashSet<>();
                obfMethods.put(methodInfo.owner, methods);
            }
            methods.add(methodInfo);

            if (methodInfo.isStatic) {
                deobfMethods.put(methodInfo.cleanName, methodInfo);
            } else {
                String cleanName = obfClasses.get(methodInfo.owner);
                if (cleanName != null) {
                    deobfMethods.put(cleanName + "." + methodInfo.cleanName, methodInfo);
                }
            }
        }

        for (FieldInfo fieldInfo : MappingsDatabase.FIELDS) {
            Set<FieldInfo> fields = obfFields.get(fieldInfo.owner);
            if (fields == null) {
                fields = new HashSet<>();
                obfFields.put(fieldInfo.owner, fields);
            }
            fields.add(fieldInfo);

            if (fieldInfo.isStatic) {
                deobfFields.put(fieldInfo.cleanName, fieldInfo);;
            } else {
                String cleanName = obfClasses.get(fieldInfo.owner);
                if (cleanName != null) {
                    deobfFields.put(cleanName + "." + fieldInfo.cleanName, fieldInfo);
                }
            }
        }
    }

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
}

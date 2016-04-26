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

    private static BigInteger integerRange = new BigInteger("" + 4294967296L);
    public static int getMMI(int multiplier) {
        BigInteger multiplierInt = new BigInteger("" + multiplier);
        return multiplierInt.modInverse(integerRange).intValue();
    }

    public static String upperCaseify(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static void addInstructions(InsnList list, AbstractInsnNode... instructions) {
        for (AbstractInsnNode i : instructions) {
            list.add(i);
        }
    }

    public static AbstractInsnNode seekNode(AbstractInsnNode start, int opCode) {
        AbstractInsnNode next = start.getNext();
        while (true) {
            if (next == null) {
                return null;
            }
            if (next.getOpcode() == opCode) {
                return next;
            }
            next = next.getNext();
        }
    }

    public static final Map<String, String> obfClasses = new HashMap<>();
    public static final Map<String, String> deobfClasses = new HashMap<>();
    static {
        for (ClassInfo classInfo : MappingsDatabase.CLASSES) {
            obfClasses.put(classInfo.obfName, classInfo.cleanName);
            deobfClasses.put(classInfo.cleanName, classInfo.obfName);
        }
    }

    public static final Map<String, Set<MethodInfo>> obfMethods = new HashMap<>();
    public static final Map<String, MethodInfo> deobfMethods = new HashMap<>();
    static {
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
    }

    public static final Map<String, Set<FieldInfo>> obfFields = new HashMap<>();
    public static final Map<String, FieldInfo> deobfFields = new HashMap<>();
    static {
        for (FieldInfo fieldInfo : MappingsDatabase.FIELDS) {
            Set<FieldInfo> fields = obfFields.get(fieldInfo.owner);
            if (fields == null) {
                fields = new HashSet<>();
                obfFields.put(fieldInfo.owner, fields);
            }
            fields.add(fieldInfo);

            if (fieldInfo.isStatic) {
                deobfFields.put(fieldInfo.cleanName, fieldInfo);
            } else {
                String cleanName = obfClasses.get(fieldInfo.owner);
                if (cleanName != null) {
                    deobfFields.put(cleanName + "." + fieldInfo.cleanName, fieldInfo);
                }
            }
        }
    }
}

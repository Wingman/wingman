package com.wingman.client.classloader.transformers.mapping;

import com.wingman.client.api.mapping.FieldInfo;
import com.wingman.client.api.mapping.MappingsHelper;
import com.wingman.client.api.mapping.MethodInfo;
import com.wingman.client.api.transformer.Transformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.HashSet;
import java.util.Set;

/**
 * {@link StaticsBridger} handles bridging the client API with members injected into the game. <br>

 * It replaces the static API methods in {@link com.wingman.client.api.generated.Static}.
 *
 * @see RSMemberInjector
 */
public class StaticsBridger implements Transformer {

    final String STATIC_GETTER_CLASS = "com.wingman.client.api.generated.Static";
    final String STATIC_SETTER_CLASS = "com.wingman.client.api.generated.Static$Unsafe";

    final Set<FieldInfo> fields = new HashSet<>();
    final Set<MethodInfo> methods = new HashSet<>();

    @Override
    public boolean canTransform(String name) {
        return name.equals(STATIC_GETTER_CLASS)
                || name.equals(STATIC_SETTER_CLASS);
    }

    @Override
    public ClassNode transform(ClassNode clazz) {
        clazz.methods.clear();

        if (clazz.name.replace("/", ".").equals(STATIC_GETTER_CLASS)) {
            transformMethods(clazz);
        }

        transformFields(clazz);

        return clazz;
    }

    @Override
    public boolean isUsed() {
        return true;
    }

    private void transformMethods(ClassNode clazz) {
        for (MethodInfo m : methods) {
            InsnList insnList = new InsnList();

            Type deobfDesc = Type.getType(m.cleanDesc);

            int index = 0;
            Type[] args = deobfDesc.getArgumentTypes();
            for (Type arg : args) {
                MappingsHelper.addInstructions(insnList,
                        new VarInsnNode(arg.getOpcode(Opcodes.ILOAD), index)
                );
                if (arg.getDescriptor().equals("J") || arg.getDescriptor().equals("D")) {
                    index++;
                }
                index++;
            }

            MethodNode methodNode = new MethodNode(
                    Opcodes.ASM5,
                    Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
                    m.cleanName,
                    deobfDesc.toString(),
                    null,
                    new String[]{}
            );

            MappingsHelper.addInstructions(insnList,
                    new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            m.owner,
                            m.cleanName + "_M__STATIC_WINGMANCLIENT",
                            deobfDesc.toString(),
                            false
                    ),
                    new InsnNode(Type.getType(m.deobfType).getOpcode(Opcodes.IRETURN))
            );

            methodNode.instructions = insnList;
            clazz.methods.add(methodNode);
        }
    }

    private void transformFields(ClassNode clazz) {
        for (FieldInfo f : fields) {
            InsnList insnList = new InsnList();

            Type deobfType = Type.getType(f.deobfType);

            String suffix = MappingsHelper.upperCaseify(f.cleanName);
            String destination = suffix + "_F__STATIC";

            MethodNode m;
            if (clazz.name.replace("/", ".").equals(STATIC_GETTER_CLASS)) {
                Type getterType = Type.getMethodType(deobfType);

                m = new MethodNode(
                        Opcodes.ASM5,
                        Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
                        "get" + suffix,
                        getterType.toString(),
                        null,
                        new String[]{}
                );

                MappingsHelper.addInstructions(insnList,
                        new MethodInsnNode(
                                Opcodes.INVOKESTATIC,
                                f.owner,
                                "get" + destination + "_WINGMANCLIENT",
                                getterType.toString(),
                                false
                        ),
                        new InsnNode(deobfType.getOpcode(Opcodes.IRETURN))
                );
            } else {
                Type setterType = Type.getMethodType(Type.VOID_TYPE, deobfType);

                m = new MethodNode(
                        Opcodes.ASM5,
                        Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
                        "set" + suffix,
                        setterType.toString(),
                        null,
                        new String[]{}
                );

                MappingsHelper.addInstructions(insnList,
                        new VarInsnNode(deobfType.getOpcode(Opcodes.ILOAD), 0),
                        new MethodInsnNode(
                                Opcodes.INVOKESTATIC,
                                f.owner,
                                "set" + destination + "_WINGMANCLIENT",
                                setterType.toString(),
                                false
                        ),
                        new InsnNode(Opcodes.RETURN)
                );
            }

            m.instructions = insnList;
            clazz.methods.add(m);
        }
    }

    {
        for (Set<FieldInfo> fieldInfoSet : MappingsHelper.obfFields.values()) {
            for (FieldInfo fieldInfo : fieldInfoSet) {
                if (fieldInfo.isStatic) {
                    fields.add(fieldInfo);
                }
            }
        }

        for (Set<MethodInfo> methodInfoSet : MappingsHelper.obfMethods.values()) {
            for (MethodInfo methodInfo : methodInfoSet) {
                if (methodInfo.isStatic) {
                    methods.add(methodInfo);
                }
            }
        }
    }
}

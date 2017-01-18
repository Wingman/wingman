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
 */
public class StaticsBridger implements Transformer {

    private final Set<FieldInfo> fields = new HashSet<>();
    private final Set<MethodInfo> methods = new HashSet<>();

    @Override
    public boolean canTransform(String name) {
        return name.equals("client");
    }

    @Override
    public ClassNode transform(ClassNode clazz) {
        clazz.interfaces.add("com/wingman/client/api/generated/Static");
        clazz.interfaces.add("com/wingman/client/api/generated/Static$Unsafe");

        transformMethods(clazz);
        transformFields(clazz);

        return clazz;
    }

    @Override
    public boolean isUsed() {
        return true;
    }

    private void transformMethods(ClassNode clazz) {
        for (MethodInfo m : methods) {
            Type obfType = Type.getType(m.type);
            Type obfDesc = Type.getType(m.desc);
            Type deobfType = Type.getType(m.realType);
            Type deobfDesc = Type.getType(m.realDesc);

            MethodNode method = new MethodNode(
                    Opcodes.ASM5,
                    Opcodes.ACC_PUBLIC,
                    m.realName,
                    deobfDesc.toString(),
                    null,
                    new String[]{});

            InsnList insnList = method.instructions;

            int index = 1;

            Type[] obfArgs = obfDesc.getArgumentTypes();
            Type[] deobfArgs = deobfDesc.getArgumentTypes();
            for (int i = 0; i < deobfArgs.length; i++) {
                Type obfArg = obfArgs[i];
                Type deobfArg = deobfArgs[i];

                MappingsHelper.addInstructions(insnList,
                        new VarInsnNode(deobfArg.getOpcode(Opcodes.ILOAD), index++)
                );

                if (deobfArg.getDescriptor().equals("J") || deobfArg.getDescriptor().equals("D")) {
                    index++;
                }

                if (!obfArg.equals(deobfArg)) {
                    MappingsHelper.addInstructions(insnList,
                            new TypeInsnNode(Opcodes.CHECKCAST, obfArg.getInternalName())
                    );
                }
            }

            if (m.hasOpaquePredicate) {
                MappingsHelper.addInstructions(insnList,
                        new LdcInsnNode(m.opaquePredicate)
                );
            }

            MappingsHelper.addInstructions(insnList,
                    new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            m.owner,
                            m.name,
                            m.desc,
                            false)
            );

            if (!obfType.equals(deobfType)) {
                MappingsHelper.addInstructions(insnList,
                        new TypeInsnNode(Opcodes.CHECKCAST, deobfType.getInternalName())
                );
            }

            MappingsHelper.addInstructions(insnList,
                    new InsnNode(obfType.getOpcode(Opcodes.IRETURN))
            );

            clazz.methods.add(method);
        }
    }

    private void transformFields(ClassNode clazz) {
        for (FieldInfo f : fields) {
            Type obfType = Type.getType(f.type);
            Type deobfType = Type.getType(f.realType);

            boolean needsCast = !f.type.equals(f.realType);
            String cleanName = MappingsHelper.toUpperCaseFirstCharacter(f.realName);

            {
                Type getterType = Type.getMethodType(deobfType);

                MethodNode getter = new MethodNode(
                        Opcodes.ASM5,
                        Opcodes.ACC_PUBLIC,
                        "get" + cleanName,
                        getterType.toString(),
                        null,
                        new String[]{});

                InsnList insnList = getter.instructions;

                MappingsHelper.addInstructions(insnList,
                        new FieldInsnNode(
                                Opcodes.GETSTATIC,
                                f.owner,
                                f.name,
                                obfType.toString())
                );

                if (needsCast) {
                    MappingsHelper.addInstructions(insnList,
                            new TypeInsnNode(Opcodes.CHECKCAST, deobfType.getInternalName())
                    );
                }

                if (f.getter != 1) {
                    if (f.type.equals("I")) {
                        MappingsHelper.addInstructions(insnList,
                                new LdcInsnNode(new Integer("" + f.getter)),
                                new InsnNode(Opcodes.IMUL)
                        );
                    } else {
                        MappingsHelper.addInstructions(insnList,
                                new LdcInsnNode(f.getter),
                                new InsnNode(Opcodes.LMUL)
                        );
                    }
                }

                MappingsHelper.addInstructions(insnList,
                        new InsnNode(deobfType.getOpcode(Opcodes.IRETURN))
                );

                clazz.methods.add(getter);
            }

            {
                Type setterType = Type.getMethodType(Type.VOID_TYPE, deobfType);

                MethodNode setter = new MethodNode(
                        Opcodes.ASM5,
                        Opcodes.ACC_PUBLIC,
                        "set" + cleanName,
                        setterType.toString(),
                        null,
                        new String[]{});

                InsnList insnList = setter.instructions;

                MappingsHelper.addInstructions(insnList,
                        new VarInsnNode(deobfType.getOpcode(Opcodes.ILOAD), 1)
                );

                if (needsCast) {
                    MappingsHelper.addInstructions(insnList,
                            new TypeInsnNode(Opcodes.CHECKCAST, obfType.getInternalName())
                    );
                }

                if (f.getter != 1) {
                    if (f.type.equals("I")) {
                        MappingsHelper.addInstructions(insnList,
                                new LdcInsnNode(new Integer("" + f.setter)),
                                new InsnNode(Opcodes.IMUL)
                        );
                    } else {
                        MappingsHelper.addInstructions(insnList,
                                new LdcInsnNode(f.setter),
                                new InsnNode(Opcodes.LMUL)
                        );
                    }
                }

                MappingsHelper.addInstructions(insnList,
                        new FieldInsnNode(Opcodes.PUTSTATIC, f.owner, f.name, obfType.toString()),
                        new InsnNode(Opcodes.RETURN)
                );

                clazz.methods.add(setter);
            }
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

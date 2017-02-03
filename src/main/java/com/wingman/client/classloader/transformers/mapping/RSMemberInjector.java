package com.wingman.client.classloader.transformers.mapping;

import com.wingman.client.api.mapping.FieldInfo;
import com.wingman.client.api.mapping.MappingsHelper;
import com.wingman.client.api.mapping.MethodInfo;
import com.wingman.client.api.transformer.Transformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.Set;

/**
 * {@link RSMemberInjector} injects members into the game for access through the client.
 * <p>
 * Instanced field getters and setters are injected like "type getFieldName()" or "void setFieldName(value)".
 */
public class RSMemberInjector implements Transformer {

    @Override
    public boolean canTransform(String name) {
        return MappingsHelper.obfClasses.get(name) != null
                || MappingsHelper.obfFields.get(name) != null
                || MappingsHelper.obfMethods.get(name) != null;
    }

    @Override
    public ClassNode transform(ClassNode clazz) {
        String cleanName = MappingsHelper.obfClasses.get(clazz.name);

        if (cleanName != null) {
            clazz.interfaces.add("com/wingman/client/api/generated/" + cleanName);
            clazz.interfaces.add("com/wingman/client/api/generated/" + cleanName + "$Unsafe");
        }

        transformMethods(clazz);
        transformFields(clazz);

        return clazz;
    }

    @Override
    public boolean isUsed() {
        return true;
    }

    private void transformMethods(ClassNode clazz) {
        Set<MethodInfo> methods = MappingsHelper.obfMethods.get(clazz.name);

        if (methods == null) {
            return;
        }

        for (MethodInfo m : methods) {
            if (m.isStatic) {
                continue;
            }

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

            int index = 0;
            MappingsHelper.addInstructions(insnList,
                    new VarInsnNode(Opcodes.ALOAD, index++)
            );

            Type[] obfArgs = obfDesc.getArgumentTypes();
            Type[] deobfArgs = deobfDesc.getArgumentTypes();
            for (int i = 0; i < deobfArgs.length; i++) {
                Type obfArg = obfArgs[i];
                Type deobfArg = deobfArgs[i];

                MappingsHelper.addInstructions(insnList,
                        new VarInsnNode(deobfArg.getOpcode(Opcodes.ILOAD), index++)
                );

                if ("J".equals(deobfArg.getDescriptor())
                        || "D".equals(deobfArg.getDescriptor())) {
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
                            Opcodes.INVOKEVIRTUAL,
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
        Set<FieldInfo> fields = MappingsHelper.obfFields.get(clazz.name);

        if (fields == null) {
            return;
        }

        for (FieldInfo f : fields) {
            if (f.isStatic) {
                continue;
            }

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
                        new VarInsnNode(Opcodes.ALOAD, 0)
                );

                MappingsHelper.addInstructions(insnList,
                        new FieldInsnNode(
                                Opcodes.GETFIELD,
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
                    if ("I".equals(f.type)) {
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

                int index = 0;
                MappingsHelper.addInstructions(insnList,
                        new VarInsnNode(Opcodes.ALOAD, index++)
                );

                MappingsHelper.addInstructions(insnList,
                        new VarInsnNode(deobfType.getOpcode(Opcodes.ILOAD), index)
                );

                if (needsCast) {
                    MappingsHelper.addInstructions(insnList,
                            new TypeInsnNode(Opcodes.CHECKCAST, obfType.getInternalName())
                    );
                }

                if (f.getter != 1) {
                    if ("I".equals(f.type)) {
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
                        new FieldInsnNode(Opcodes.PUTFIELD, f.owner, f.name, obfType.toString()),
                        new InsnNode(Opcodes.RETURN)
                );

                clazz.methods.add(setter);
            }
        }
    }
}

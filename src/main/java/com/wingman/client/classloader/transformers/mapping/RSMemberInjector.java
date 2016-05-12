package com.wingman.client.classloader.transformers.mapping;

import com.wingman.client.api.mapping.FieldInfo;
import com.wingman.client.api.mapping.MappingsHelper;
import com.wingman.client.api.mapping.MethodInfo;
import com.wingman.client.api.transformer.Transformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.text.MessageFormat;
import java.util.Set;

/**
 * {@link RSMemberInjector} injects members into the game for access through the client. <br>
 *
 * Instanced field getters and setters are injected like "type getFieldName()" or "void setFieldName(value)". <br>
 * Static field getters and setters are injected like "type getFieldName_F__STATIC_WINGMANCLIENT()" or "void setFieldName_F__STATIC_WINGMANCLIENT(value)". <br>
 *
 * Method invokers are injected like "returnType methodName_M__STATIC_WINGMANCLIENT(args...)".
 */
public class RSMemberInjector implements Transformer {

    @Override
    public boolean canTransform(String name) {
        return MappingsHelper.obfFields.get(name) != null
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

    private void transformMethods(ClassNode clazz) {
        Set<MethodInfo> methods = MappingsHelper.obfMethods.get(clazz.name);

        if (methods == null) {
            return;
        }

        for (MethodInfo m : methods) {
            Type obfType = Type.getType(m.obfType);
            Type obfDesc = Type.getType(m.desc);
            Type deobfType = Type.getType(m.deobfType);
            Type deobfDesc = Type.getType(m.cleanDesc);

            MethodNode method = new MethodNode(
                    Opcodes.ASM5,
                    Opcodes.ACC_PUBLIC | (m.isStatic ? Opcodes.ACC_STATIC : 0),
                    m.cleanName + (m.isStatic ? "_M__STATIC_WINGMANCLIENT" : ""),
                    deobfDesc.toString(),
                    null,
                    new String[]{});

            InsnList insnList = method.instructions;

            int index = 0;
            if (!m.isStatic) {
                MappingsHelper.addInstructions(insnList,
                        new VarInsnNode(Opcodes.ALOAD, index++)
                );
            }

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

            if (m.hasDummy) {
                MappingsHelper.addInstructions(insnList,
                        new LdcInsnNode(m.dummy)
                );
            }

            MappingsHelper.addInstructions(insnList,
                    new MethodInsnNode(
                            (m.isStatic ? Opcodes.INVOKESTATIC : Opcodes.INVOKEVIRTUAL),
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
            Type obfType = Type.getType(f.obfType);
            Type deobfType = Type.getType(f.deobfType);

            boolean needsCast = !f.obfType.equals(f.deobfType);
            String cleanName = MappingsHelper.upperCaseify(f.cleanName);

            {
                Type getterType = Type.getMethodType(deobfType);

                MethodNode getter = new MethodNode(
                        Opcodes.ASM5,
                        Opcodes.ACC_PUBLIC | (f.isStatic ? Opcodes.ACC_STATIC : 0),
                        "get" + cleanName + (f.isStatic ? "_F__STATIC_WINGMANCLIENT" : ""),
                        getterType.toString(),
                        null,
                        new String[]{});

                InsnList insnList = getter.instructions;

                if (!f.isStatic) {
                    MappingsHelper.addInstructions(insnList,
                            new VarInsnNode(Opcodes.ALOAD, 0)
                    );
                }

                MappingsHelper.addInstructions(insnList,
                        new FieldInsnNode(
                                (f.isStatic ? Opcodes.GETSTATIC : Opcodes.GETFIELD),
                                f.owner,
                                f.name,
                                obfType.toString())
                );

                if (needsCast) {
                    MappingsHelper.addInstructions(insnList,
                            new TypeInsnNode(Opcodes.CHECKCAST, deobfType.getInternalName())
                    );
                }

                if (f.multiplier != 1) {
                    MappingsHelper.addInstructions(insnList,
                            new LdcInsnNode(f.multiplier),
                            new InsnNode(Opcodes.IMUL)
                    );
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
                        Opcodes.ACC_PUBLIC | (f.isStatic ? Opcodes.ACC_STATIC : 0),
                        "set" + cleanName + (f.isStatic ? "_F__STATIC_WINGMANCLIENT" : ""),
                        setterType.toString(),
                        null,
                        new String[]{});

                InsnList insnList = setter.instructions;

                int index = 0;
                if (!f.isStatic) {
                    MappingsHelper.addInstructions(insnList,
                            new VarInsnNode(Opcodes.ALOAD, index++)
                    );
                }

                MappingsHelper.addInstructions(insnList,
                        new VarInsnNode(deobfType.getOpcode(Opcodes.ILOAD), index)
                );

                if (needsCast) {
                    MappingsHelper.addInstructions(insnList,
                            new TypeInsnNode(Opcodes.CHECKCAST, obfType.getInternalName())
                    );
                }

                try {
                    if (f.multiplier != 1) {
                        MappingsHelper.addInstructions(insnList,
                                new LdcInsnNode(MappingsHelper.getMMI(f.multiplier)),
                                new InsnNode(Opcodes.IMUL)
                        );
                    }

                    MappingsHelper.addInstructions(insnList,
                            new FieldInsnNode((f.isStatic ? Opcodes.PUTSTATIC : Opcodes.PUTFIELD), f.owner, f.name, obfType.toString()),
                            new InsnNode(Opcodes.RETURN)
                    );

                    clazz.methods.add(setter);
                } catch (ArithmeticException e) {
                    System.out.println(MessageFormat.format("Multiplier {0} for {1}.{2} ({3}) is broken - {4}",
                            f.multiplier,
                            f.owner,
                            f.name,
                            f.cleanName,
                            e.getMessage()));
                }
            }
        }
    }
}

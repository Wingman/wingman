package com.wingman.client.classloader.transformers;

import com.wingman.client.api.mapping.MappingsHelper;
import com.wingman.client.api.transformer.Transformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;
import java.util.ListIterator;

public class PreserveExceptionInfoTransformer implements Transformer {

    private String rsException;

    @Override
    public boolean canTransform(String name) {
        return rsException != null
                && name.equals(rsException);
    }

    @Override
    public ClassNode transform(ClassNode clazz) {
        Iterator<MethodNode> iterator = clazz.methods.iterator();
        while (iterator.hasNext()) {
            MethodNode m = iterator.next();
            if (m.name.equals("<init>")) {
                ListIterator<AbstractInsnNode> nodeIterator = m.instructions.iterator();
                while (nodeIterator.hasNext()) {
                    try {
                        MethodInsnNode i = (MethodInsnNode) nodeIterator.next();
                        if (i.owner.equals("java/lang/RuntimeException")) {
                            i.desc = "(Ljava/lang/String;Ljava/lang/Throwable;)V";

                            InsnList insnList = new InsnList();
                            MappingsHelper.addInstructions(insnList,
                                    new VarInsnNode(Opcodes.ALOAD, 2),
                                    new VarInsnNode(Opcodes.ALOAD, 1)
                            );
                            m.instructions.insertBefore(i, insnList);
                            break;
                        }
                    } catch (ClassCastException | NullPointerException e) {
                        //swallow
                    }
                }
            }
        }

        return clazz;
    }

    public PreserveExceptionInfoTransformer() {
        this.rsException = MappingsHelper.deobfClasses.get("RSException");
    }
}

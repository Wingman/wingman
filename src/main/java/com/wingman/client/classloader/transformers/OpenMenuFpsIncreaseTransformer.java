package com.wingman.client.classloader.transformers;

import com.wingman.client.api.mapping.MappingsHelper;
import com.wingman.client.api.mapping.MethodInfo;
import com.wingman.client.api.transformer.Transformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

/**
 * Removes code from the drawRightClickMenu method so that FPS does not drop when opening right-click menus.
 */
public class OpenMenuFpsIncreaseTransformer implements Transformer {

    private MethodInfo drawRightClickMenuMethod;

    @Override
    public boolean canTransform(String name) {
        return name.equals(drawRightClickMenuMethod.owner);
    }

    @Override
    public ClassNode transform(ClassNode clazz) {
        for (MethodNode m : clazz.methods) {
            if (!m.name.equals(drawRightClickMenuMethod.name)
                    || !m.desc.equals(drawRightClickMenuMethod.desc)) {
                continue;
            }

            Iterator<AbstractInsnNode> nodeIterator = m.instructions.iterator();
            while (nodeIterator.hasNext()) {
                AbstractInsnNode i = nodeIterator.next();
                if (i.getOpcode() != Opcodes.GETSTATIC
                        || !((FieldInsnNode) i).desc.equals("[Z")) {
                    continue;
                }

                VarInsnNode i2 = (VarInsnNode) i.getNext();

                InsnNode i3 = (InsnNode) i2.getNext();

                InsnNode i4 = (InsnNode) i3.getNext();
                if (i4.getOpcode() != Opcodes.BASTORE) {
                    continue;
                }

                nodeIterator.remove();
                for (int j = 0; j < 3; j++) {
                    nodeIterator.next();
                    nodeIterator.remove();
                }

                break;
            }
        }
        return clazz;
    }

    public OpenMenuFpsIncreaseTransformer() {
        this.drawRightClickMenuMethod = MappingsHelper.deobfMethods.get("drawRightClickMenu");
    }
}

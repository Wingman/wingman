package com.wingman.client.classloader.transformers;

import com.wingman.client.api.events.rendering.RenderHook;
import com.wingman.client.api.mapping.FieldInfo;
import com.wingman.client.api.mapping.MappingsHelper;
import com.wingman.client.api.mapping.MethodInfo;
import com.wingman.client.api.transformer.Transformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.awt.*;
import java.util.Iterator;

public class CanvasUpdatedTransformer implements Transformer {

    public static void runHook(Image image) {
        Iterator<RenderHook> it = RenderHook.renderHooks.iterator();
        while (it.hasNext()) {
            Graphics g = image.getGraphics().create();
            try {
                if (it.next().render(g)) {
                    it.remove();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            g.dispose();
        }
    }

    private MethodInfo drawFullGameImage;
    private FieldInfo gameDrawingMode;

    private String primaryGraphicsBuffer
            = MappingsHelper.deobfClasses.get("PrimaryGraphicsBuffer");

    private String secondaryGraphicsBuffer
            = MappingsHelper.deobfClasses.get("SecondaryGraphicsBuffer");

    @Override
    public boolean canTransform(String name) {
        return name.equals(gameDrawingMode.owner)
                || name.equals(primaryGraphicsBuffer)
                || name.equals(secondaryGraphicsBuffer);
    }

    @Override
    public ClassNode transform(ClassNode clazz) {
        if (drawFullGameImage != null) {
            if (clazz.name.equals(gameDrawingMode.owner)) {
                for (MethodNode m : clazz.methods) {
                    if (!m.name.equals("<clinit>")) {
                        continue;
                    }

                    Iterator<AbstractInsnNode> nodeIterator = m.instructions.iterator();
                    while (nodeIterator.hasNext()) {
                        try {
                            InsnNode i = (InsnNode) nodeIterator.next();

                            FieldInsnNode i2 = (FieldInsnNode) i.getNext();
                            if (i2.getOpcode() != Opcodes.PUTSTATIC
                                    || !i2.owner.equals(gameDrawingMode.owner)
                                    || !i2.name.equals(gameDrawingMode.name)) {
                                continue;
                            }

                            m.instructions.set(i, new InsnNode(Opcodes.ICONST_1));
                            break;
                        } catch (ClassCastException | NullPointerException e) {
                            //swallow
                        }
                    }
                }
            } else {
                for (MethodNode m : clazz.methods) {
                    if (!m.name.equals(drawFullGameImage.name)) {
                        continue;
                    }

                    Iterator<AbstractInsnNode> nodeIterator = m.instructions.iterator();
                    while (nodeIterator.hasNext()) {
                        try {
                            FieldInsnNode fieldInsnNode = (FieldInsnNode) nodeIterator.next();
                            if (!fieldInsnNode.desc.equals("Ljava/awt/Image;")) {
                                continue;
                            }

                            InsnList insnList = new InsnList();
                            MappingsHelper.addInstructions(insnList,
                                    new IntInsnNode(Opcodes.ALOAD, 0),
                                    new FieldInsnNode(Opcodes.GETFIELD,
                                            clazz.name,
                                            fieldInsnNode.name,
                                            fieldInsnNode.desc),
                                    new MethodInsnNode(Opcodes.INVOKESTATIC,
                                            this.getClass().getName().replace(".", "/"),
                                            "runHook",
                                            "(Ljava/awt/Image;)V",
                                            false)
                            );
                            m.instructions.insertBefore(fieldInsnNode.getPrevious().getPrevious(), insnList);
                            break;
                        } catch (ClassCastException | NullPointerException e) {
                            //swallow
                        }
                    }
                }
            }
        }

        return clazz;
    }

    @Override
    public boolean isUsed() {
        return true;
    }

    public CanvasUpdatedTransformer() {
        this.drawFullGameImage = MappingsHelper.deobfMethods.get("AbstractGraphicsBuffer.drawFullGameImage");
        this.gameDrawingMode = MappingsHelper.deobfFields.get("gameDrawingMode");
    }
}

package com.wingman.client.classloader.transformers;

import com.wingman.client.api.events.rendering.RenderHook;
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
            if (it.next().render(g)) {
                it.remove();
            }
            g.dispose();
        }
    }

    private MethodInfo drawTitleGraphics;
    private MethodInfo drawGameGraphics;

    @Override
    public boolean canTransform(String name) {
        return name.equals(MappingsHelper.deobfClasses.get("PrimaryGraphicsBuffer"))
                || name.equals(MappingsHelper.deobfClasses.get("SecondaryGraphicsBuffer"));
    }

    @Override
    public ClassNode transform(ClassNode clazz) {
        if (drawTitleGraphics != null && drawGameGraphics != null) {
            for (MethodNode m : clazz.methods) {
                if (!m.name.equals(drawTitleGraphics.name)
                        && !m.name.equals(drawGameGraphics.name)) {
                    continue;
                }

                Iterator<AbstractInsnNode> nodeIterator = m.instructions.iterator();
                while (nodeIterator.hasNext()) {
                    AbstractInsnNode i = nodeIterator.next();
                    if (i.getOpcode() != Opcodes.GETFIELD) {
                        continue;
                    }

                    FieldInsnNode fieldInsnNode = (FieldInsnNode) i;
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
                }
            }
        }

        return clazz;
    }

    public CanvasUpdatedTransformer() {
        this.drawTitleGraphics = MappingsHelper.deobfMethods.get("AbstractGraphicsBuffer.drawTitleGraphics");
        this.drawGameGraphics = MappingsHelper.deobfMethods.get("AbstractGraphicsBuffer.drawGameGraphics");
    }
}

package com.wingman.defaultplugins.entityhider;

import com.wingman.client.api.mapping.MappingsHelper;
import com.wingman.client.api.mapping.MethodInfo;
import com.wingman.client.api.transformer.Transformer;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class CancelEntityRenderingTransformer implements Transformer {

    private MethodInfo renderAtPoint;
    private MethodInfo renderCharacter2D;

    @Override
    public boolean canTransform(String name) {
        return this.renderAtPoint.owner.equals(name)
                || this.renderCharacter2D.owner.equals(name);
    }

    @Override
    public ClassNode transform(ClassNode clazz) {
        for (MethodNode m : clazz.methods) {
            if (m.name.equals(renderAtPoint.name) && m.desc.equals(renderAtPoint.desc)) {
                inject(m.instructions, false);
            } else if (m.name.equals(renderCharacter2D.name) && m.desc.equals(renderCharacter2D.desc)) {
                inject(m.instructions, true);
            }
        }

        return clazz;
    }

    private void inject(InsnList instructions, boolean drawingUI) {
        InsnList insnList = new InsnList();

        LabelNode labelNode = new LabelNode(new Label());

        insnList.add(labelNode);
        insnList.add(new InsnNode(Opcodes.RETURN));

        instructions.add(insnList);

        InsnList insnList2 = new InsnList();

        insnList2.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insnList2.add(new InsnNode(drawingUI ? Opcodes.ICONST_1 : Opcodes.ICONST_0));
        insnList2.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                EntityHider.class.getName().replace(".", "/"),
                "shouldDraw",
                "(Ljava/lang/Object;Z)Z",
                false));
        insnList2.add(new JumpInsnNode(Opcodes.IFEQ, labelNode));

        instructions.insertBefore(instructions.getFirst(), insnList2);
    }

    @Override
    public boolean isUsed() {
        return this.renderAtPoint != null
                && this.renderCharacter2D != null;
    }

    public CancelEntityRenderingTransformer() {
        this.renderAtPoint = MappingsHelper.deobfMethods.get("Entity.renderAtPoint");
        this.renderCharacter2D = MappingsHelper.deobfMethods.get("renderCharacter2D");
    }
}

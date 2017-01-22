package com.wingman.client.classloader.transformers;

import com.wingman.client.api.event.Event;
import com.wingman.client.api.events.ExperienceGainedEvent;
import com.wingman.client.api.mapping.FieldInfo;
import com.wingman.client.api.mapping.MappingsHelper;
import com.wingman.client.api.transformer.Transformer;
import com.wingman.client.plugin.PluginManager;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

public class ExperienceGainedTransformer implements Transformer {

    private FieldInfo expLevels;

    @Override
    public boolean canTransform(String name) {
        return true;
    }

    @Override
    public ClassNode transform(ClassNode clazz) {
        for (MethodNode m : clazz.methods) {
            ListIterator<AbstractInsnNode> nodeIterator = m.instructions.iterator();
            while (nodeIterator.hasNext()) {
                try {
                    FieldInsnNode i = (FieldInsnNode) nodeIterator.next();
                    if (!i.owner.equals(expLevels.owner)
                            || !i.name.equals(expLevels.name)) {
                        continue;
                    }

                    VarInsnNode i2 = (VarInsnNode) i.getNext();

                    InsnNode i3 = (InsnNode) i2.getNext();
                    if (i3.getOpcode() != Opcodes.ICONST_1) {
                        continue;
                    }

                    // Reverse order:
                    InsnNode i4 = (InsnNode) i.getPrevious();

                    VarInsnNode i5 = (VarInsnNode) i4.getPrevious();

                    VarInsnNode i6 = (VarInsnNode) i5.getPrevious();

                    // Current levels (boosts included)
                    FieldInsnNode i7 = (FieldInsnNode) i6.getPrevious();

                    InsnNode i8 = (InsnNode) i7.getPrevious();

                    VarInsnNode i9 = (VarInsnNode) i8.getPrevious();

                    VarInsnNode i10 = (VarInsnNode) i9.getPrevious();

                    // Experiences array
                    FieldInsnNode i11 = (FieldInsnNode) i10.getPrevious();

                    InsnList insnList = new InsnList();
                    MappingsHelper.addInstructions(insnList,
                            new TypeInsnNode(Opcodes.NEW,
                                    ExperienceGainedEvent.class.getName().replace(".", "/")),
                            new InsnNode(Opcodes.DUP),

                            // Skill ID
                            new VarInsnNode(Opcodes.ILOAD, i2.var),

                            new FieldInsnNode(Opcodes.GETSTATIC, i.owner, i.name, i.desc),
                            new VarInsnNode(Opcodes.ILOAD, i2.var),
                            new InsnNode(Opcodes.IALOAD),

                            new FieldInsnNode(Opcodes.GETSTATIC, i11.owner, i11.name, i11.desc),
                            new VarInsnNode(Opcodes.ILOAD, i2.var),
                            new InsnNode(Opcodes.IALOAD),

                            // New skill XP
                            new VarInsnNode(Opcodes.ILOAD, i9.var),

                            new MethodInsnNode(Opcodes.INVOKESPECIAL,
                                    ExperienceGainedEvent.class.getName().replace(".", "/"),
                                    "<init>",
                                    "(IIII)V",
                                    false),
                            new MethodInsnNode(Opcodes.INVOKESTATIC,
                                    PluginManager.class.getName().replace(".", "/"),
                                    "callEvent",
                                    "(L" + Event.class.getName().replace(".", "/") + ";)V",
                                    false)
                    );
                    m.instructions.insertBefore(i11, insnList);
                    break;
                } catch (ClassCastException | NullPointerException ignored) {
                }
            }
        }

        return clazz;
    }

    @Override
    public boolean isUsed() {
        return this.expLevels != null
                && ExperienceGainedEvent.eventListenerList.listeners != null;
    }

    public ExperienceGainedTransformer() {
        this.expLevels = MappingsHelper.deobfFields.get("expLevels");
    }
}

package com.wingman.client.classloader.transformers;

import com.wingman.client.api.event.Event;
import com.wingman.client.api.events.NpcUpdateEvent;
import com.wingman.client.api.generated.GameAPI;
import com.wingman.client.api.generated.NPC;
import com.wingman.client.api.mapping.FieldInfo;
import com.wingman.client.api.mapping.MappingsHelper;
import com.wingman.client.api.mapping.MethodInfo;
import com.wingman.client.api.transformer.Transformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

public class NpcUpdateTransformer implements Transformer {

    private static boolean[] spawnedNpcs = new boolean[32768];
    private String npcClass;
    private MethodInfo decodeNpcUpdateMethod;
    private FieldInfo npcDefinitionField;
    private FieldInfo npcArrayField;

    public NpcUpdateTransformer() {
        decodeNpcUpdateMethod = MappingsHelper.deobfMethods.get("decodeNpcUpdate");
        npcClass = MappingsHelper.deobfClasses.get("NPC");
        npcDefinitionField = MappingsHelper.deobfFields.get("NPC.definition");
        npcArrayField = MappingsHelper.deobfFields.get("npcs");
    }

    // Used for adding or updating NPCs.
    public static void runHook(int index, NPC npc) {
        NpcUpdateEvent.Type type;

        if (spawnedNpcs[index]) {
            type = NpcUpdateEvent.Type.UPDATE;
        } else {
            type = NpcUpdateEvent.Type.CREATE;
        }

        Event.callEvent(new NpcUpdateEvent(
                npc,
                type,
                index)
        );

        spawnedNpcs[index] = true;
    }

    // Used for removing NPCs.
    public static void runHook(int index) {
        Event.callEvent(new NpcUpdateEvent(
                GameAPI.getNpcs()[index],
                NpcUpdateEvent.Type.DELETE,
                index)
        );

        spawnedNpcs[index] = false;
    }

    @Override
    public boolean canTransform(String name) {
        return decodeNpcUpdateMethod.owner.equals(name);
    }

    @Override
    public ClassNode transform(ClassNode clazz) {
        for (MethodNode m : clazz.methods) {
            if (!m.name.equals(decodeNpcUpdateMethod.name) || !m.desc.equals(decodeNpcUpdateMethod.desc)) {
                continue;
            }

            VarInsnNode npcNode = null;
            VarInsnNode indexNode = null;

            ListIterator<AbstractInsnNode> nodeIterator = m.instructions.iterator();
            while (nodeIterator.hasNext()) {
                try {
                    FieldInsnNode i = (FieldInsnNode) nodeIterator.next();
                    if (!i.owner.equals(npcArrayField.owner)
                            || !i.name.equals(npcArrayField.name)) {
                        continue;
                    }

                    VarInsnNode i2 = (VarInsnNode) i.getNext();
                    InsnNode i3 = (InsnNode) i2.getNext();
                    npcNode = (VarInsnNode) i3.getNext();
                } catch (ClassCastException | NullPointerException ignored) {
                }
            }

            nodeIterator = m.instructions.iterator();
            while (nodeIterator.hasNext()) {
                try {
                    TypeInsnNode i = (TypeInsnNode) nodeIterator.next();
                    if (!i.desc.equals(npcClass)) {
                        continue;
                    }
                    indexNode = (VarInsnNode) i.getPrevious();
                } catch (ClassCastException | NullPointerException ignored) {
                }
            }

            if (npcNode == null || indexNode == null) {
                return clazz;
            }

            nodeIterator = m.instructions.iterator();
            while (nodeIterator.hasNext()) {
                try {
                    FieldInsnNode i = (FieldInsnNode) nodeIterator.next();
                    if (!i.owner.equals(npcDefinitionField.owner)
                            || !i.name.equals(npcDefinitionField.name)
                            || i.getOpcode() != Opcodes.PUTFIELD) {
                        continue;
                    }

                    // This instruction is used for removing an NPC from the NPCs array.
                    if (i.getPrevious().getOpcode() == Opcodes.ACONST_NULL) {
                        InsnNode i2 = (InsnNode) i.getPrevious();
                        InsnNode i3 = (InsnNode) i2.getPrevious();

                        InsnList insnList = new InsnList();
                        MappingsHelper.addInstructions(insnList,
                                new InsnNode(Opcodes.DUP),
                                new MethodInsnNode(Opcodes.INVOKESTATIC,
                                        this.getClass().getName().replace(".", "/"),
                                        "runHook",
                                        "(I)V",
                                        false)
                        );
                        m.instructions.insertBefore(i3, insnList);
                        continue;
                    }

                    InsnList insnList = new InsnList();
                    MappingsHelper.addInstructions(insnList,
                            new VarInsnNode(Opcodes.ILOAD, indexNode.var),
                            new VarInsnNode(Opcodes.ALOAD, npcNode.var),
                            new MethodInsnNode(Opcodes.INVOKESTATIC,
                                    this.getClass().getName().replace(".", "/"),
                                    "runHook",
                                    "(IL" + NPC.class.getName().replace(".", "/") + ";)V",
                                    false)
                    );
                    m.instructions.insert(i, insnList);
                } catch (ClassCastException | NullPointerException ignored) {
                }
            }
        }

        return clazz;
    }

    @Override
    public boolean isUsed() {
        return npcClass != null
                && decodeNpcUpdateMethod != null
                && npcDefinitionField != null
                && npcArrayField != null
                && NpcUpdateEvent.eventListenerList.listeners != null;
    }
}

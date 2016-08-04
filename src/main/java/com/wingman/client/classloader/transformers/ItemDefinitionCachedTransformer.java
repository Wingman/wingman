package com.wingman.client.classloader.transformers;

import com.wingman.client.api.event.Event;
import com.wingman.client.api.events.ItemDefinitionCachedEvent;
import com.wingman.client.api.generated.ItemDefinition;
import com.wingman.client.api.mapping.MappingsHelper;
import com.wingman.client.api.mapping.MethodInfo;
import com.wingman.client.api.transformer.Transformer;
import com.wingman.client.plugin.PluginManager;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class ItemDefinitionCachedTransformer implements Transformer {

    private MethodInfo getItemDefinition;

    @Override
    public boolean canTransform(String name) {
        return getItemDefinition.owner.equals(name);
    }

    @Override
    public ClassNode transform(ClassNode clazz) {
        for (MethodNode m : clazz.methods) {
            if (!m.name.equals(getItemDefinition.name)
                    || !m.desc.equals(getItemDefinition.desc)) {
                continue;
            }

            Iterator<AbstractInsnNode> nodeIterator = m.instructions.iterator();
            while (nodeIterator.hasNext()) {
                AbstractInsnNode i = nodeIterator.next();
                if (i.getOpcode() != Opcodes.ARETURN) {
                    continue;
                }

                AbstractInsnNode prev = i.getPrevious();
                if (prev.getOpcode() != Opcodes.ALOAD) {
                    continue;
                }

                VarInsnNode returnALoad = (VarInsnNode) prev;

                InsnList insnList = new InsnList();
                MappingsHelper.addInstructions(insnList,
                        new TypeInsnNode(Opcodes.NEW,
                                ItemDefinitionCachedEvent.class.getName().replace(".", "/")),
                        new InsnNode(Opcodes.DUP),
                        new VarInsnNode(Opcodes.ALOAD, returnALoad.var),
                        new MethodInsnNode(Opcodes.INVOKESPECIAL,
                                ItemDefinitionCachedEvent.class.getName().replace(".", "/"),
                                "<init>",
                                "(L" + ItemDefinition.class.getName().replace(".", "/") + ";)V",
                                false),
                        new MethodInsnNode(Opcodes.INVOKESTATIC,
                                PluginManager.class.getName().replace(".", "/"),
                                "callEvent",
                                "(L" + Event.class.getName().replace(".", "/") + ";)V",
                                false)
                );
                m.instructions.insertBefore(returnALoad, insnList);
                break;
            }
        }

        return clazz;
    }

    @Override
    public boolean isUsed() {
        return this.getItemDefinition != null
                && ItemDefinitionCachedEvent.eventListenerList.listeners != null;
    }

    public ItemDefinitionCachedTransformer() {
        this.getItemDefinition = MappingsHelper.deobfMethods.get("getItemDefinition");
    }
}

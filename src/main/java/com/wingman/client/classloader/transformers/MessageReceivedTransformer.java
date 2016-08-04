package com.wingman.client.classloader.transformers;

import com.wingman.client.api.event.Event;
import com.wingman.client.api.events.MessageReceivedEvent;
import com.wingman.client.api.generated.Message;
import com.wingman.client.api.mapping.MappingsHelper;
import com.wingman.client.api.mapping.MethodInfo;
import com.wingman.client.api.transformer.Transformer;
import com.wingman.client.plugin.PluginManager;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

public class MessageReceivedTransformer implements Transformer {

    private MethodInfo addMessage;

    @Override
    public boolean canTransform(String name) {
        return addMessage != null
                && addMessage.owner.equals(name);
    }

    @Override
    public ClassNode transform(ClassNode clazz) {
        for (MethodNode m : clazz.methods) {
            if (!m.name.equals(addMessage.name)
                    || !m.desc.equals(addMessage.desc)) {
                continue;
            }

            ListIterator<AbstractInsnNode> nodeIterator = m.instructions.iterator(m.instructions.size());
            while (nodeIterator.hasPrevious()) {
                AbstractInsnNode i = nodeIterator.previous();
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
                                MessageReceivedEvent.class.getName().replace(".", "/")),
                        new InsnNode(Opcodes.DUP),
                        new VarInsnNode(Opcodes.ALOAD, returnALoad.var),
                        new MethodInsnNode(Opcodes.INVOKESPECIAL,
                                MessageReceivedEvent.class.getName().replace(".", "/"),
                                "<init>",
                                "(L" + Message.class.getName().replace(".", "/") + ";)V",
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
        return MessageReceivedEvent.eventListenerList.listeners != null;
    }

    public MessageReceivedTransformer() {
        this.addMessage = MappingsHelper.deobfMethods.get("MessageContainer.addMessage");
    }
}

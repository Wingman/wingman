package com.wingman.client.classloader.transformers;

import com.wingman.client.api.event.Event;
import com.wingman.client.api.events.WidgetOpenedEvent;
import com.wingman.client.api.mapping.MappingsHelper;
import com.wingman.client.api.mapping.MethodInfo;
import com.wingman.client.api.transformer.Transformer;
import com.wingman.client.plugin.PluginManager;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class WidgetOpenedTransformer implements Transformer {

    private MethodInfo openWidget;

    @Override
    public boolean canTransform(String name) {
        return openWidget.owner.equals(name);
    }

    @Override
    public ClassNode transform(ClassNode clazz) {
        for (MethodNode m : clazz.methods) {
            if (!m.name.equals(openWidget.name)) {
                continue;
            }

            InsnList insnList = new InsnList();
            MappingsHelper.addInstructions(insnList,
                    new TypeInsnNode(Opcodes.NEW,
                            WidgetOpenedEvent.class.getName().replace(".", "/")),
                    new InsnNode(Opcodes.DUP),
                    new VarInsnNode(Opcodes.ILOAD, 0),
                    new MethodInsnNode(Opcodes.INVOKESPECIAL,
                            WidgetOpenedEvent.class.getName().replace(".", "/"),
                            "<init>",
                            "(I)V",
                            false),
                    new MethodInsnNode(Opcodes.INVOKESTATIC,
                            PluginManager.class.getName().replace(".", "/"),
                            "callEvent",
                            "(L" + Event.class.getName().replace(".", "/") + ";)V",
                            false)
            );
            m.instructions.insert(m.instructions.getFirst(), insnList);
        }

        return clazz;
    }

    @Override
    public boolean isUsed() {
        return this.openWidget != null
                && WidgetOpenedEvent.eventListenerList.listeners != null;
    }

    public WidgetOpenedTransformer() {
        this.openWidget = MappingsHelper.deobfMethods.get("openWidget");
    }
}

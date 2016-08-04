package com.wingman.client.classloader.transformers;

import com.wingman.client.api.events.ExternalPlayerMovedEvent;
import com.wingman.client.api.generated.BitBuffer;
import com.wingman.client.api.generated.GameAPI;
import com.wingman.client.api.mapping.MappingsHelper;
import com.wingman.client.api.mapping.MethodInfo;
import com.wingman.client.api.transformer.Transformer;
import com.wingman.client.plugin.PluginManager;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class ExternalPlayerMovedTransformer implements Transformer {

    public static void runHook(BitBuffer bitBuffer, int playerId) {
        int startPosition = bitBuffer.getBitPosition();

        int type = bitBuffer.getBits(2);

        if (type == 0) {
            if (bitBuffer.getBits(1) != 0) {
                runHook(bitBuffer, playerId);
            }

            int deltaX = bitBuffer.getBits(6);
            int deltaY = bitBuffer.getBits(6);

            int currentPos = GameAPI.getExternalPlayerLocations()[playerId];

            int oldPlane = currentPos >> 28;
            int oldX = currentPos >> 14 & 255;
            int oldY =  currentPos & 255;

            int newX = deltaX + oldX & 255;
            int newY = deltaY + oldY & 255;

            PluginManager.callEvent(new ExternalPlayerMovedEvent(
                    playerId,
                    ExternalPlayerMovedEvent.Type.ADDED_TO_LOCAL,
                    oldX, oldY, oldPlane,
                    newX, newY, oldPlane)
            );
        } else if (type == 1) {
            int deltaPlane = bitBuffer.getBits(2);

            int currentPos = GameAPI.getExternalPlayerLocations()[playerId];

            int oldPlane = currentPos >> 28;
            int oldX = currentPos >> 14 & 255;
            int oldY =  currentPos & 255;

            int newPlane = deltaPlane + oldPlane & 3;

            PluginManager.callEvent(new ExternalPlayerMovedEvent(
                    playerId,
                    ExternalPlayerMovedEvent.Type.PLANE_CHANGE,
                    oldX, oldY, oldPlane,
                    oldX, oldY, newPlane)
            );
        } else if (type == 2) {
            int data = bitBuffer.getBits(5);

            int deltaPlane = data >> 3;
            int direction = data & 7;

            int currentPos = GameAPI.getExternalPlayerLocations()[playerId];

            int oldPlane = currentPos >> 28;
            int oldX = currentPos >> 14 & 255;
            int oldY =  currentPos & 255;

            int newPlane = deltaPlane + oldPlane & 3;
            int newX = oldX;
            int newY = oldY;

            if(direction == 0) {
                --newX;
                --newY;
            }

            if(direction == 1) {
                --newY;
            }

            if(direction == 2) {
                ++newX;
                --newY;
            }

            if(direction == 3) {
                --newX;
            }

            if(direction == 4) {
                ++newX;
            }

            if(direction == 5) {
                --newX;
                ++newY;
            }

            if(direction == 6) {
                ++newY;
            }

            if(direction == 7) {
                ++newX;
                ++newY;
            }

            PluginManager.callEvent(new ExternalPlayerMovedEvent(
                    playerId,
                    ExternalPlayerMovedEvent.Type.ADJACENT_REGION,
                    oldX, oldY, oldPlane,
                    newX, newY, newPlane)
            );
        } else {
            int data = bitBuffer.getBits(18);

            int deltaPlane = data >> 16;
            int deltaX = data >> 8 & 255;
            int deltaY = data & 255;

            int currentPos = GameAPI.getExternalPlayerLocations()[playerId];

            int oldPlane = currentPos >> 28;
            int oldX = currentPos >> 14 & 255;
            int oldY =  currentPos & 255;

            int newPlane = deltaPlane + oldPlane & 3;
            int newX = deltaX + oldX & 255;
            int newY = deltaY + oldY & 255;

            PluginManager.callEvent(new ExternalPlayerMovedEvent(
                    playerId,
                    ExternalPlayerMovedEvent.Type.NONADJACENT_REGION,
                    oldX, oldY, oldPlane,
                    newX, newY, newPlane)
            );
        }

        ((BitBuffer.Unsafe) bitBuffer).setBitPosition(startPosition);
    }

    private MethodInfo decodeExternalPlayerMovement;

    @Override
    public boolean canTransform(String name) {
        return decodeExternalPlayerMovement.owner.equals(name);
    }

    @Override
    public ClassNode transform(ClassNode clazz) {
        for (MethodNode m : clazz.methods) {
            if (!m.name.equals(decodeExternalPlayerMovement.name) ||
                    !m.desc.equals(decodeExternalPlayerMovement.desc)) {
                continue;
            }

            InsnList insnList = new InsnList();
            MappingsHelper.addInstructions(insnList,
                    new IntInsnNode(Opcodes.ALOAD, 0),
                    new IntInsnNode(Opcodes.ILOAD, 1),
                    new MethodInsnNode(Opcodes.INVOKESTATIC,
                            this.getClass().getName().replace(".", "/"),
                            "runHook",
                            "(L" + BitBuffer.class.getName().replace(".", "/") + ";I)V",
                            false)
            );
            m.instructions.insert(m.instructions.getFirst(), insnList);
        }

        return clazz;
    }

    @Override
    public boolean isUsed() {
        return this.decodeExternalPlayerMovement != null
                && ExternalPlayerMovedEvent.eventListenerList.listeners != null;
    }

    public ExternalPlayerMovedTransformer() {
        this.decodeExternalPlayerMovement = MappingsHelper.deobfMethods.get("decodeExternalPlayerMovement");
    }
}

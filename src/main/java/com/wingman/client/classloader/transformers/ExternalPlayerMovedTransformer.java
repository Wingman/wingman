package com.wingman.client.classloader.transformers;

import com.wingman.client.api.event.Event;
import com.wingman.client.api.events.ExternalPlayerMovedEvent;
import com.wingman.client.api.generated.BitBuffer;
import com.wingman.client.api.generated.GameAPI;
import com.wingman.client.api.mapping.MappingsHelper;
import com.wingman.client.api.mapping.MethodInfo;
import com.wingman.client.api.transformer.Transformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class ExternalPlayerMovedTransformer implements Transformer {

    private static final int TYPE_BECAME_LOCAL_PLAYER = 0;
    private static final int TYPE_CHANGED_PLANE = 1;
    private static final int TYPE_MOVED_TO_ADJACENT_REGION = 2;

    // https://i.imgur.com/CcHDBkd.png
    private static final int MOVED_SOUTHWEST = 0;
    private static final int MOVED_SOUTH = 1;
    private static final int MOVED_SOUTHEAST = 2;
    private static final int MOVED_WEST = 3;
    private static final int MOVED_EAST = 4;
    private static final int MOVED_NORTHWEST = 5;
    private static final int MOVED_NORTH = 6;
    private static final int MOVED_NORTHEAST = 7;

    public static void runHook(BitBuffer bitBuffer, int playerId) {
        int startPosition = bitBuffer.getBitPosition();

        int type = bitBuffer.getBits(2);

        if (type == TYPE_BECAME_LOCAL_PLAYER) {
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

            Event.callEvent(new ExternalPlayerMovedEvent(
                    playerId,
                    ExternalPlayerMovedEvent.Type.ADDED_TO_LOCAL,
                    oldX, oldY, oldPlane,
                    newX, newY, oldPlane)
            );
        } else if (type == TYPE_CHANGED_PLANE) {
            int deltaPlane = bitBuffer.getBits(2);

            int currentPos = GameAPI.getExternalPlayerLocations()[playerId];

            int oldPlane = currentPos >> 28;
            int oldX = currentPos >> 14 & 255;
            int oldY =  currentPos & 255;

            int newPlane = deltaPlane + oldPlane & 3;

            Event.callEvent(new ExternalPlayerMovedEvent(
                    playerId,
                    ExternalPlayerMovedEvent.Type.PLANE_CHANGE,
                    oldX, oldY, oldPlane,
                    oldX, oldY, newPlane)
            );
        } else if (type == TYPE_MOVED_TO_ADJACENT_REGION) {
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

            if(direction == MOVED_SOUTHWEST) {
                --newX;
                --newY;
            }

            if(direction == MOVED_SOUTH) {
                --newY;
            }

            if(direction == MOVED_SOUTHEAST) {
                ++newX;
                --newY;
            }

            if(direction == MOVED_WEST) {
                --newX;
            }

            if(direction == MOVED_EAST) {
                ++newX;
            }

            if(direction == MOVED_NORTHWEST) {
                --newX;
                ++newY;
            }

            if(direction == MOVED_NORTH) {
                ++newY;
            }

            if(direction == MOVED_NORTHEAST) {
                ++newX;
                ++newY;
            }

            Event.callEvent(new ExternalPlayerMovedEvent(
                    playerId,
                    ExternalPlayerMovedEvent.Type.ADJACENT_REGION,
                    oldX, oldY, oldPlane,
                    newX, newY, newPlane)
            );
        } else {
            // Type 3 = moved to non-adjacent region

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

            Event.callEvent(new ExternalPlayerMovedEvent(
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

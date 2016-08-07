package com.wingman.client.api.generated;

import com.wingman.client.api.mapping.ClassInfo;
import com.wingman.client.api.mapping.FieldInfo;
import com.wingman.client.api.mapping.MethodInfo;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public abstract class MappingsDatabase {
    public static final ClassInfo[] CLASSES = new com.wingman.client.api.mapping.ClassInfo[]{
        new com.wingman.client.api.mapping.ClassInfo("AbstractGraphicsBuffer", "ca"),
        new com.wingman.client.api.mapping.ClassInfo("BitBuffer", "du"),
        new com.wingman.client.api.mapping.ClassInfo("ByteBuffer", "dc"),
        new com.wingman.client.api.mapping.ClassInfo("Character", "ay"),
        new com.wingman.client.api.mapping.ClassInfo("Client", "client"),
        new com.wingman.client.api.mapping.ClassInfo("DualNode", "gm"),
        new com.wingman.client.api.mapping.ClassInfo("Entity", "cz"),
        new com.wingman.client.api.mapping.ClassInfo("GameEngine", "em"),
        new com.wingman.client.api.mapping.ClassInfo("HealthBar", "ad"),
        new com.wingman.client.api.mapping.ClassInfo("HealthBarDefinition", "al"),
        new com.wingman.client.api.mapping.ClassInfo("HitUpdate", "j"),
        new com.wingman.client.api.mapping.ClassInfo("InteractableObject", "cv"),
        new com.wingman.client.api.mapping.ClassInfo("IsaacCipher", "dr"),
        new com.wingman.client.api.mapping.ClassInfo("ItemContainer", "d"),
        new com.wingman.client.api.mapping.ClassInfo("ItemDefinition", "bt"),
        new com.wingman.client.api.mapping.ClassInfo("Landscape", "cs"),
        new com.wingman.client.api.mapping.ClassInfo("LandscapeTile", "di"),
        new com.wingman.client.api.mapping.ClassInfo("Message", "ap"),
        new com.wingman.client.api.mapping.ClassInfo("MessageContainer", "ah"),
        new com.wingman.client.api.mapping.ClassInfo("Model", "dv"),
        new com.wingman.client.api.mapping.ClassInfo("NPC", "ab"),
        new com.wingman.client.api.mapping.ClassInfo("NPCDefinition", "aj"),
        new com.wingman.client.api.mapping.ClassInfo("Node", "hb"),
        new com.wingman.client.api.mapping.ClassInfo("NodeIterable", "gg"),
        new com.wingman.client.api.mapping.ClassInfo("NodeTable", "gt"),
        new com.wingman.client.api.mapping.ClassInfo("Player", "r"),
        new com.wingman.client.api.mapping.ClassInfo("PrimaryGraphicsBuffer", "ch"),
        new com.wingman.client.api.mapping.ClassInfo("RSException", "ee"),
        new com.wingman.client.api.mapping.ClassInfo("ReferenceTable", "fp"),
        new com.wingman.client.api.mapping.ClassInfo("SecondaryGraphicsBuffer", "cw"),
        new com.wingman.client.api.mapping.ClassInfo("WallObject", "cb"),
        new com.wingman.client.api.mapping.ClassInfo("Widget", "fx"),

    };

    public static final MethodInfo[] METHODS = new com.wingman.client.api.mapping.MethodInfo[]{
        new com.wingman.client.api.mapping.MethodInfo("decodeExternalPlayerMovement", "an", "s", "Z", "Z", "(Ldu;IB)Z", "(Lcom/wingman/client/api/generated/BitBuffer;I)Z", true, true, 15),
        new com.wingman.client.api.mapping.MethodInfo("getItemDefinition", "g", "g", "Lbt;", "Lcom/wingman/client/api/generated/ItemDefinition;", "(II)Lbt;", "(I)Lcom/wingman/client/api/generated/ItemDefinition;", true, true, 1039182812),
        new com.wingman.client.api.mapping.MethodInfo("getNpcDefinition", "f", "l", "Laj;", "Lcom/wingman/client/api/generated/NPCDefinition;", "(IB)Laj;", "(I)Lcom/wingman/client/api/generated/NPCDefinition;", true, true, 8),
        new com.wingman.client.api.mapping.MethodInfo("pushMessage", "k", "r", "V", "V", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", true, true, -1384917157),
        new com.wingman.client.api.mapping.MethodInfo("updateCharacterMovement", "dq", "as", "V", "V", "(Lay;II)V", "(Lcom/wingman/client/api/generated/Character;I)V", true, true, -614363242),
        new com.wingman.client.api.mapping.MethodInfo("addMessage", "ah", "l", "Lap;", "Lcom/wingman/client/api/generated/Message;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;I)Lap;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/wingman/client/api/generated/Message;", false, true, 571717869),
        new com.wingman.client.api.mapping.MethodInfo("drawClippedGameImage", "ca", "r", "V", "V", "(Ljava/awt/Graphics;IIIIB)V", "(Ljava/awt/Graphics;IIII)V", false, true, 0),
        new com.wingman.client.api.mapping.MethodInfo("drawClippedGameImage", "ch", "r", "V", "V", "(Ljava/awt/Graphics;IIIIB)V", "(Ljava/awt/Graphics;IIII)V", false, true, 0),
        new com.wingman.client.api.mapping.MethodInfo("drawClippedGameImage", "cw", "r", "V", "V", "(Ljava/awt/Graphics;IIIIB)V", "(Ljava/awt/Graphics;IIII)V", false, true, 0),
        new com.wingman.client.api.mapping.MethodInfo("drawFullGameImage", "ca", "g", "V", "V", "(Ljava/awt/Graphics;III)V", "(Ljava/awt/Graphics;II)V", false, true, 0),
        new com.wingman.client.api.mapping.MethodInfo("drawFullGameImage", "ch", "g", "V", "V", "(Ljava/awt/Graphics;III)V", "(Ljava/awt/Graphics;II)V", false, true, 0),
        new com.wingman.client.api.mapping.MethodInfo("drawFullGameImage", "cw", "g", "V", "V", "(Ljava/awt/Graphics;III)V", "(Ljava/awt/Graphics;II)V", false, true, 0),
        new com.wingman.client.api.mapping.MethodInfo("get", "gt", "l", "Lhb;", "Lcom/wingman/client/api/generated/Node;", "(J)Lhb;", "(J)Lcom/wingman/client/api/generated/Node;", false, false, 0),
        new com.wingman.client.api.mapping.MethodInfo("getBits", "du", "ia", "I", "I", "(II)I", "(I)I", false, true, 89959992),
        new com.wingman.client.api.mapping.MethodInfo("getFile", "fp", "r", "[B", "[B", "(IIB)[B", "(II)[B", false, true, 0),
        new com.wingman.client.api.mapping.MethodInfo("getModel", "ab", "r", "Ldv;", "Lcom/wingman/client/api/generated/Model;", "(I)Ldv;", "()Lcom/wingman/client/api/generated/Model;", false, true, -1358457193),
        new com.wingman.client.api.mapping.MethodInfo("getModel", "am", "r", "Ldv;", "Lcom/wingman/client/api/generated/Model;", "(I)Ldv;", "()Lcom/wingman/client/api/generated/Model;", false, true, 0),
        new com.wingman.client.api.mapping.MethodInfo("getModel", "aq", "r", "Ldv;", "Lcom/wingman/client/api/generated/Model;", "(I)Ldv;", "()Lcom/wingman/client/api/generated/Model;", false, true, -1358457193),
        new com.wingman.client.api.mapping.MethodInfo("getModel", "k", "r", "Ldv;", "Lcom/wingman/client/api/generated/Model;", "(I)Ldv;", "()Lcom/wingman/client/api/generated/Model;", false, true, 0),
        new com.wingman.client.api.mapping.MethodInfo("getModel", "p", "r", "Ldv;", "Lcom/wingman/client/api/generated/Model;", "(I)Ldv;", "()Lcom/wingman/client/api/generated/Model;", false, true, -1358457193),
        new com.wingman.client.api.mapping.MethodInfo("getModel", "r", "r", "Ldv;", "Lcom/wingman/client/api/generated/Model;", "(I)Ldv;", "()Lcom/wingman/client/api/generated/Model;", false, true, -1358457193),
        new com.wingman.client.api.mapping.MethodInfo("getModel", "cz", "r", "Ldv;", "Lcom/wingman/client/api/generated/Model;", "(I)Ldv;", "()Lcom/wingman/client/api/generated/Model;", false, true, 0),
        new com.wingman.client.api.mapping.MethodInfo("isLinked", "hb", "jc", "Z", "Z", "()Z", "()Z", false, false, 0),
        new com.wingman.client.api.mapping.MethodInfo("processRendering", "client", "u", "V", "V", "(B)V", "()V", false, true, 9),
        new com.wingman.client.api.mapping.MethodInfo("renderAtPoint", "cz", "af", "V", "V", "(IIIIIIIII)V", "(IIIIIIIII)V", false, false, 0),
        new com.wingman.client.api.mapping.MethodInfo("renderAtPoint", "dv", "af", "V", "V", "(IIIIIIIII)V", "(IIIIIIIII)V", false, false, 0),
        new com.wingman.client.api.mapping.MethodInfo("throwCriticalError", "em", "qe", "V", "V", "(Ljava/lang/String;I)V", "(Ljava/lang/String;)V", false, true, -836897791),
        new com.wingman.client.api.mapping.MethodInfo("unlink", "gm", "fr", "V", "V", "()V", "()V", false, false, 0),
        new com.wingman.client.api.mapping.MethodInfo("unlink", "hb", "ii", "V", "V", "()V", "()V", false, false, 0),

    };

    public static final FieldInfo[] FIELDS = new com.wingman.client.api.mapping.FieldInfo[]{
        new com.wingman.client.api.mapping.FieldInfo("appletHeight", "ay", "qb", "I", "I", true, 1324038261),
        new com.wingman.client.api.mapping.FieldInfo("appletWidth", "gb", "qq", "I", "I", true, -906707721),
        new com.wingman.client.api.mapping.FieldInfo("cameraPitch", "cq", "fr", "I", "I", true, -1139349345),
        new com.wingman.client.api.mapping.FieldInfo("cameraX", "ee", "fb", "I", "I", true, 864506953),
        new com.wingman.client.api.mapping.FieldInfo("cameraY", "cx", "ff", "I", "I", true, 1541264873),
        new com.wingman.client.api.mapping.FieldInfo("cameraYaw", "bs", "ft", "I", "I", true, 794988375),
        new com.wingman.client.api.mapping.FieldInfo("cameraZ", "n", "fc", "I", "I", true, -292844125),
        new com.wingman.client.api.mapping.FieldInfo("canvas", "dg", "qz", "Ljava/awt/Canvas;", "Ljava/awt/Canvas;", true, 1),
        new com.wingman.client.api.mapping.FieldInfo("clientPlane", "cu", "gg", "I", "I", true, 203936363),
        new com.wingman.client.api.mapping.FieldInfo("externalPlayerLocations", "ag", "b", "[I", "[I", true, 1),
        new com.wingman.client.api.mapping.FieldInfo("gameDrawingMode", "client", "lz", "I", "I", true, 1044551677),
        new com.wingman.client.api.mapping.FieldInfo("gameSettings", "fu", "r", "[I", "[I", true, 1),
        new com.wingman.client.api.mapping.FieldInfo("gameState", "client", "z", "I", "I", true, -128579603),
        new com.wingman.client.api.mapping.FieldInfo("itemContainers", "d", "l", "Lgt;", "Lcom/wingman/client/api/generated/NodeTable;", true, 1),
        new com.wingman.client.api.mapping.FieldInfo("landscape", "eo", "dd", "Lcs;", "Lcom/wingman/client/api/generated/Landscape;", true, 1),
        new com.wingman.client.api.mapping.FieldInfo("localPlayer", "e", "he", "Lr;", "Lcom/wingman/client/api/generated/Player;", true, 1),
        new com.wingman.client.api.mapping.FieldInfo("loopCycle", "client", "v", "I", "I", true, -651791851),
        new com.wingman.client.api.mapping.FieldInfo("mapAngle", "client", "fw", "I", "I", true, -791997341),
        new com.wingman.client.api.mapping.FieldInfo("mapOffset", "client", "en", "I", "I", true, 1588152977),
        new com.wingman.client.api.mapping.FieldInfo("mapScale", "client", "et", "I", "I", true, -571506033),
        new com.wingman.client.api.mapping.FieldInfo("messageContainers", "q", "l", "Ljava/util/Map;", "Ljava/util/Map;", true, 1),
        new com.wingman.client.api.mapping.FieldInfo("npcs", "client", "ce", "[Lab;", "[Lcom/wingman/client/api/generated/NPC;", true, 1),
        new com.wingman.client.api.mapping.FieldInfo("playerSettings", "fu", "g", "[I", "[I", true, 1),
        new com.wingman.client.api.mapping.FieldInfo("players", "client", "gi", "[Lr;", "[Lcom/wingman/client/api/generated/Player;", true, 1),
        new com.wingman.client.api.mapping.FieldInfo("resizableMode", "client", "lk", "Z", "Z", true, 1),
        new com.wingman.client.api.mapping.FieldInfo("tileHeights", "s", "l", "[[[I", "[[[I", true, 1),
        new com.wingman.client.api.mapping.FieldInfo("tileSettings", "s", "g", "[[[B", "[[[B", true, 1),
        new com.wingman.client.api.mapping.FieldInfo("viewPortHeight", "client", "op", "I", "I", true, 2023179217),
        new com.wingman.client.api.mapping.FieldInfo("viewPortScale", "client", "ow", "I", "I", true, 202303965),
        new com.wingman.client.api.mapping.FieldInfo("viewPortWidth", "client", "ov", "I", "I", true, 110362467),
        new com.wingman.client.api.mapping.FieldInfo("widgetSettings", "fu", "l", "[I", "[I", true, 1),
        new com.wingman.client.api.mapping.FieldInfo("widgets", "fx", "l", "[[Lfx;", "[[Lcom/wingman/client/api/generated/Widget;", true, 1),
        new com.wingman.client.api.mapping.FieldInfo("actions", "aj", "j", "[Ljava/lang/String;", "[Ljava/lang/String;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("animation", "ay", "be", "I", "I", false, -1571771687),
        new com.wingman.client.api.mapping.FieldInfo("bitPosition", "du", "m", "I", "I", false, -1652549435),
        new com.wingman.client.api.mapping.FieldInfo("combatLevel", "aj", "t", "I", "I", false, -1732609405),
        new com.wingman.client.api.mapping.FieldInfo("comment", "ee", "e", "Ljava/lang/String;", "Ljava/lang/String;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("definition", "ab", "l", "Laj;", "Lcom/wingman/client/api/generated/NPCDefinition;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("definition", "ad", "r", "Lal;", "Lcom/wingman/client/api/generated/HealthBarDefinition;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("graphicsBufferImage", "ca", "s", "Ljava/awt/Image;", "Ljava/awt/Image;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("groundActions", "bt", "as", "[Ljava/lang/String;", "[Ljava/lang/String;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("healthBars", "ay", "bq", "Lgg;", "Lcom/wingman/client/api/generated/NodeIterable;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("healthRatio", "j", "r", "I", "I", false, -554880233),
        new com.wingman.client.api.mapping.FieldInfo("hitUpdates", "ad", "e", "Lgg;", "Lcom/wingman/client/api/generated/NodeIterable;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("ids", "d", "g", "[I", "[I", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("index", "ap", "l", "I", "I", false, -1163282247),
        new com.wingman.client.api.mapping.FieldInfo("interactableObjects", "di", "q", "[Lcv;", "[Lcom/wingman/client/api/generated/InteractableObject;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("interactingIndex", "ay", "bj", "I", "I", false, 1774280051),
        new com.wingman.client.api.mapping.FieldInfo("isaacCipher", "du", "n", "Ldr;", "Lcom/wingman/client/api/generated/IsaacCipher;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("key", "hb", "em", "J", "J", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("landscapeTiles", "cs", "s", "[[[Ldi;", "[[[Lcom/wingman/client/api/generated/LandscapeTile;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("message", "ap", "s", "Ljava/lang/String;", "Ljava/lang/String;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("modelHeight", "cz", "cz", "I", "I", false, -1994798555),
        new com.wingman.client.api.mapping.FieldInfo("name", "aj", "s", "Ljava/lang/String;", "Ljava/lang/String;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("name", "bt", "w", "Ljava/lang/String;", "Ljava/lang/String;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("next", "gm", "cb", "Lgm;", "Lcom/wingman/client/api/generated/DualNode;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("next", "hb", "eq", "Lhb;", "Lcom/wingman/client/api/generated/Node;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("noteIndex", "bt", "ar", "I", "I", false, 38903507),
        new com.wingman.client.api.mapping.FieldInfo("noteTemplateIndex", "bt", "at", "I", "I", false, -1685935717),
        new com.wingman.client.api.mapping.FieldInfo("objectFlags", "di", "p", "[I", "[I", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("overHeadIcon", "aj", "ac", "I", "I", false, -1678730625),
        new com.wingman.client.api.mapping.FieldInfo("payload", "dc", "l", "[B", "[B", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("plane", "di", "e", "I", "I", false, -922709805),
        new com.wingman.client.api.mapping.FieldInfo("position", "dc", "g", "I", "I", false, -221102553),
        new com.wingman.client.api.mapping.FieldInfo("prefix", "ap", "h", "Ljava/lang/String;", "Ljava/lang/String;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("previous", "gm", "cs", "Lgm;", "Lcom/wingman/client/api/generated/DualNode;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("previous", "hb", "ef", "Lhb;", "Lcom/wingman/client/api/generated/Node;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("pushedLoopCycle", "ap", "g", "I", "I", false, 420119609),
        new com.wingman.client.api.mapping.FieldInfo("quantities", "d", "r", "[I", "[I", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("sender", "ap", "e", "Ljava/lang/String;", "Ljava/lang/String;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("spawnedLoopCycle", "j", "l", "I", "I", false, 2103373921),
        new com.wingman.client.api.mapping.FieldInfo("teamIndex", "bt", "an", "I", "I", false, 389970231),
        new com.wingman.client.api.mapping.FieldInfo("throwable", "ee", "h", "Ljava/lang/Throwable;", "Ljava/lang/Throwable;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("type", "ap", "r", "I", "I", false, 676138825),
        new com.wingman.client.api.mapping.FieldInfo("value", "bt", "c", "I", "I", false, -1485722379),
        new com.wingman.client.api.mapping.FieldInfo("visible", "aj", "c", "Z", "Z", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("visibleOnMiniMap", "aj", "x", "Z", "Z", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("widgetActions", "bt", "ac", "[Ljava/lang/String;", "[Ljava/lang/String;", false, 1),
        new com.wingman.client.api.mapping.FieldInfo("width", "al", "o", "I", "I", false, 1712785745),
        new com.wingman.client.api.mapping.FieldInfo("x", "ay", "as", "I", "I", false, -1376592207),
        new com.wingman.client.api.mapping.FieldInfo("x", "di", "g", "I", "I", false, -987515097),
        new com.wingman.client.api.mapping.FieldInfo("y", "ay", "ac", "I", "I", false, 1624622857),
        new com.wingman.client.api.mapping.FieldInfo("y", "di", "r", "I", "I", false, -304054191),

    };
}

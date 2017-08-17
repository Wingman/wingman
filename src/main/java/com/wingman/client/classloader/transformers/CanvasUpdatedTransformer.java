package com.wingman.client.classloader.transformers;

import com.wingman.client.api.generated.AbstractGraphicsBuffer;
import com.wingman.client.api.generated.GameAPI;
import com.wingman.client.api.mapping.FieldInfo;
import com.wingman.client.api.mapping.MappingsHelper;
import com.wingman.client.api.mapping.MethodInfo;
import com.wingman.client.api.overlay.Overlay;
import com.wingman.client.api.transformer.Transformer;
import com.wingman.client.plugin.PluginManager;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CanvasUpdatedTransformer implements Transformer {

    private String graphicsBuffer;

    private MethodInfo drawFullGameImage;

    private FieldInfo gameDrawingMode;

    public static void drawUnderUI() {
        AbstractGraphicsBuffer graphicsBuffer = GameAPI.getGraphicsBuffer();

        if (graphicsBuffer != null) {
            List<Overlay> overlays = PluginManager
                    .getOverlays(Overlay.DrawOrder.UNDER_2D_UI);

            drawOverlays(graphicsBuffer, overlays, true, !GameAPI.getResizableMode());
        }
    }

    public static void drawAlwaysOnTop() {
        AbstractGraphicsBuffer graphicsBuffer = GameAPI.getGraphicsBuffer();

        if (graphicsBuffer != null) {
            boolean isInGame = GameAPI.getGameState() > 20;

            List<Overlay> overlays;

            if (isInGame) {
                overlays = PluginManager
                        .getOverlays(Overlay.DrawOrder.ALWAYS_ON_TOP);
            } else {
                // Draw all registered overlays if we're in the login screen
                overlays = PluginManager
                        .getAllOverlays();
            }

            drawOverlays(graphicsBuffer, overlays, isInGame, false);
        }
    }

    private static void drawOverlays(AbstractGraphicsBuffer graphicsBuffer,
                                     List<Overlay> overlays,
                                     boolean isInGame,
                                     boolean useFixedModeBounds) {

        int[] gamePixels = graphicsBuffer.getPixels();
        int gameWidth = graphicsBuffer.getWidth();

        int maxIdx = gamePixels.length;

        for (Overlay overlay : overlays) {
            if (overlay.shouldDraw()) {
                Dimension d = overlay.getDimension();

                int width = d.width;
                int height = d.height;

                int[] pixels = overlay.cachedPixels;

                if (pixels == null || overlay.shouldUpdate()) {
                    pixels = new int[width * height];

                    BufferedImage i = new BufferedImage(
                            width, height,
                            BufferedImage.TYPE_INT_ARGB);

                    // Extract the overlay's pixels
                    Graphics2D g = i.createGraphics();
                    overlay.update(g);
                    g.dispose();

                    try {
                        new PixelGrabber(i, 0, 0, width, height, pixels, 0, width)
                                .grabPixels();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    overlay.cachedPixels = pixels;
                }

                Point p = overlay.getPosition();

                int startX = p.x;
                int startY = p.y;

                // Set up drawing bounds
                int minX = 0;
                int minY = 0;

                int maxX = width;
                int maxY = height;

                if (useFixedModeBounds) {
                    minX = 4;
                    minY = 4;

                    maxX = Math.min(516, maxX);
                    maxY = Math.min(338, maxY);
                }

                // Start drawing on the game canvas
                for (int x = minX; x < maxX; x++) {
                    for (int y = minY; y < maxY; y++) {
                        int newPixel = pixels[y * width + x];

                        int a = (newPixel >> 24) & 0xFF;
                        // If alpha > 0, pixel is filled
                        if (a > 0) {
                            int targetIdx = (y + startY) * gameWidth + x + startX;

                            if (targetIdx < maxIdx) {
                                if (isInGame && a < 255) {
                                    gamePixels[targetIdx] = blendWithAlpha(newPixel, gamePixels[targetIdx], a);
                                } else {
                                    gamePixels[targetIdx] = newPixel;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static int blendWithAlpha(int sourceColor, int targetColor, int alpha) {
        int sR = (sourceColor >> 16) & 0xFF;
        int sG = (sourceColor >> 8) & 0xFF;
        int sB = sourceColor & 0xFF;

        int tR = (targetColor >> 16) & 0xFF;
        int tG = (targetColor >> 8) & 0xFF;
        int tB = targetColor & 0xFF;

        int alphaRemainder = 255 - alpha;

        int rR = ((sR * alpha) + (tR * alphaRemainder)) / 255;
        int rG = ((sG * alpha) + (tG * alphaRemainder)) / 255;
        int rB = ((sB * alpha) + (tB * alphaRemainder)) / 255;

        return (rR & 0xFF) << 16
                | (rG & 0xFF) << 8
                | (rB & 0xFF);
    }

    @Override
    public boolean canTransform(String name) {
        return true;
    }

    @Override
    public ClassNode transform(ClassNode clazz) {
        if (clazz.name.equals(graphicsBuffer)) {
            for (MethodNode m : clazz.methods) {
                if (m.name.equals(drawFullGameImage.name)
                        && m.desc.equals(drawFullGameImage.desc)) {

                    Iterator<AbstractInsnNode> iterator = m.instructions.iterator();

                    while (iterator.hasNext()) {
                        AbstractInsnNode i = iterator.next();

                        if (i instanceof FieldInsnNode) {
                            FieldInsnNode i2 = (FieldInsnNode) i;
                            if (!"Ljava/awt/Image;".equals(i2.desc)) {
                                continue;
                            }

                            m.instructions.insertBefore(i2.getPrevious().getPrevious(),
                                    new MethodInsnNode(Opcodes.INVOKESTATIC,
                                            this.getClass().getName().replace(".", "/"),
                                            "drawAlwaysOnTop",
                                            "()V",
                                            false));
                            break;
                        }
                    }
                }
            }
        }

        for (MethodNode m : clazz.methods) {
            ListIterator<AbstractInsnNode> iterator = m.instructions.iterator();

            while (iterator.hasNext()) {
                AbstractInsnNode i = iterator.next();

                if (i instanceof FieldInsnNode) {
                    FieldInsnNode i2 = (FieldInsnNode) i;

                    if (i2.getOpcode() != Opcodes.GETSTATIC
                            || !i2.owner.equals(gameDrawingMode.owner)
                            || !i2.name.equals(gameDrawingMode.name)) {
                        continue;
                    }

                    m.instructions.set(i2, new InsnNode(Opcodes.ICONST_2));
                } else if (i instanceof JumpInsnNode) {
                    try {
                        /*
                            #152
                            GETFIELD hi.eu : I
                            LDC 631331557
                            IMUL
                            LDC 1
                            BASTORE <-- end
                            ILOAD 2
                            ILOAD 3
                            ILOAD 4
                            ILOAD 5
                            INVOKESTATIC jz.dm (IIII)V
                            GOTO L3 <-- start
                         */

                        if (i.getOpcode() != Opcodes.GOTO) {
                            continue;
                        }

                        MethodInsnNode i2 = (MethodInsnNode) i.getPrevious();

                        VarInsnNode i3 = (VarInsnNode) i2.getPrevious();
                        VarInsnNode i4 = (VarInsnNode) i3.getPrevious();
                        VarInsnNode i5 = (VarInsnNode) i4.getPrevious();
                        VarInsnNode i6 = (VarInsnNode) i5.getPrevious();

                        InsnNode i7 = (InsnNode) i6.getPrevious();
                        if (i7.getOpcode() != Opcodes.BASTORE) {
                            continue;
                        }

                        iterator.previous();
                        iterator.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                                this.getClass().getName().replace(".", "/"),
                                "drawUnderUI",
                                "()V",
                                false));
                        iterator.next();
                    } catch (ClassCastException | NullPointerException ignored) {
                    }
                }
            }
        }

        return clazz;
    }

    @Override
    public boolean isUsed() {
        return this.gameDrawingMode != null
                && this.drawFullGameImage != null
                && this.graphicsBuffer != null;
    }

    public CanvasUpdatedTransformer() {
        this.graphicsBuffer = MappingsHelper.deobfClasses.get("GraphicsBuffer");

        this.drawFullGameImage = MappingsHelper.deobfMethods.get("GraphicsBuffer.drawFullGameImage");

        this.gameDrawingMode = MappingsHelper.deobfFields.get("gameDrawingMode");
    }
}

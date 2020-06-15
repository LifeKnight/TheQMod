package com.lifeknight.qmod.gui.hud;

import com.lifeknight.qmod.gui.Manipulable;
import com.lifeknight.qmod.mod.Utilities;
import com.lifeknight.qmod.variables.LifeKnightCycle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.Arrays;

import static com.lifeknight.qmod.mod.Core.keystrokeFadeSpeed;
import static net.minecraft.util.EnumChatFormatting.*;
import static net.minecraft.util.EnumChatFormatting.BLACK;

public class Keystrokes extends Manipulable {
    private static final int spacebarHeight = 12, mouseButtonHeight = 20;
    private int forwardColor = 38;
    private int leftColor = 38;
    private int rightColor = 38;
    private int backColor = 38;
    private int leftMouseButtonColor = 38;
    private int rightMouseButtonColor = 38;
    private int spacebarColor = 38;
    public final LifeKnightCycle color;

    public Keystrokes(String name, int defaultX, int defaultY) {
        super(name, defaultX, defaultY);
        color = new LifeKnightCycle(name + "Color", "Invisible", new ArrayList<>(Arrays.asList(
                "Red",
                "Gold",
                "Yellow",
                "Green",
                "Aqua",
                "Blue",
                "Light Purple",
                "Dark Red",
                "Dark Green",
                "Dark Aqua",
                "Dark Blue",
                "Dark Purple",
                "White",
                "Gray",
                "Dark Gray",
                "Black"
        )), 12);
    }

    @Override
    public int getWidth() {
        return 71; // 20 / 24 - > 5 / 6
    }

    @Override
    public int getHeight() {
        return spacebarHeight + mouseButtonHeight + (getWidth() / 3 - 2) * 2 + 9;
    }


    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY, int xPosition, int yPosition, int width, int height, boolean isSelectedButton) {
        int movementKeyTileWidth = getWidth() / 3 - 2;
        renderBoxWithCenteredText(xPosition, yPosition + getHeight() - spacebarHeight, getWidth() - 1, spacebarHeight, "-------");
        renderBoxWithCenteredText(xPosition, yPosition + getHeight() - spacebarHeight - mouseButtonHeight - 3, getWidth() / 2 - 1, mouseButtonHeight, "LMB");
        renderBoxWithCenteredText(xPosition + getWidth() / 2 + 1, yPosition + getHeight() - spacebarHeight - mouseButtonHeight - 3, getWidth() / 2 - 1, 25, "RMB");
        renderBoxWithCenteredText(xPosition, yPosition + getHeight() - spacebarHeight - mouseButtonHeight - movementKeyTileWidth - 6, movementKeyTileWidth + 1, movementKeyTileWidth, "A");
        renderBoxWithCenteredText(xPosition + getWidth() / 2 + movementKeyTileWidth / 2 + 3, yPosition + getHeight() - spacebarHeight - mouseButtonHeight - movementKeyTileWidth - 6, movementKeyTileWidth + 1, movementKeyTileWidth, "D");
        renderBoxWithCenteredText(xPosition + getWidth() / 2 - movementKeyTileWidth / 2 - 1, yPosition + getHeight() - spacebarHeight - mouseButtonHeight - movementKeyTileWidth - 6, movementKeyTileWidth + 1, movementKeyTileWidth, "S");
        renderBoxWithCenteredText(xPosition + getWidth() / 2 - movementKeyTileWidth / 2 - 1, yPosition + getHeight() - spacebarHeight - mouseButtonHeight - (movementKeyTileWidth * 2) - 9, movementKeyTileWidth + 1, movementKeyTileWidth, "W");
    }

    public void onInput(int id) {
        switch (id) {
            case 0:
                forwardColor = 255;
                break;
            case 1:
                leftColor = 255;
                break;
            case 2:
                rightColor = 255;
                break;
            case 3:
                backColor = 255;
                break;
            case 4:
                leftMouseButtonColor = 255;
                break;
            case 5:
                rightMouseButtonColor = 255;
                break;
            default:
                spacebarColor = 255;
                break;
        }
    }

    public void onRenderTick() {
        if (forwardColor > 38) {
            int j = keystrokeFadeSpeed.getValue();
            while (forwardColor - j < 38) {
                j--;
            }
            forwardColor -= j;
        }
        if (leftColor > 38) {
            int j = keystrokeFadeSpeed.getValue();
            while (leftColor - j < 38) {
                j--;
            }
            leftColor -= j;
        }
        if (rightColor > 38) {
            int j = keystrokeFadeSpeed.getValue();
            while (rightColor - j < 38) {
                j--;
            }
            rightColor -= j;
        }
        if (backColor > 38) {
            int j = keystrokeFadeSpeed.getValue();
            while (backColor - j < 38) {
                j--;
            }
            backColor -= j;
        }
        if (leftMouseButtonColor > 38) {
            int j = keystrokeFadeSpeed.getValue();
            while (leftMouseButtonColor - j < 38) {
                j--;
            }
            leftMouseButtonColor -= j;
        }

        if (rightMouseButtonColor > 38) {
            int j = keystrokeFadeSpeed.getValue();
            while (rightMouseButtonColor - j < 38) {
                j--;
            }
            rightMouseButtonColor -= j;
        }
        if (spacebarColor > 38) {
            int j = keystrokeFadeSpeed.getValue();
            while (spacebarColor - j < 38) {
                j--;
            }
            spacebarColor -= j;
        }
    }

    public void renderKeystrokes() {
        int movementKeyTileWidth = getWidth() / 3 - 2;
        renderBoxWithCenteredText(super.getXCoordinate(), super.getYCoordinate() + getHeight() - spacebarHeight, getWidth() - 1, spacebarHeight, "-------", spacebarColor);
        renderBoxWithCenteredText(super.getXCoordinate(), super.getYCoordinate() + getHeight() - spacebarHeight - mouseButtonHeight - 3, getWidth() / 2 - 1, mouseButtonHeight, "LMB", leftMouseButtonColor);
        renderBoxWithCenteredText(super.getXCoordinate() + getWidth() / 2 + 1, super.getYCoordinate() + getHeight() - spacebarHeight - mouseButtonHeight - 3, getWidth() / 2 - 1, mouseButtonHeight, "RMB", rightMouseButtonColor);
        renderBoxWithCenteredText(super.getXCoordinate(), super.getYCoordinate() + getHeight() - spacebarHeight - mouseButtonHeight - movementKeyTileWidth - 6, movementKeyTileWidth + 1, movementKeyTileWidth, "A", leftColor);
        renderBoxWithCenteredText(super.getXCoordinate() + getWidth() / 2 + movementKeyTileWidth / 2 + 3, super.getYCoordinate() + getHeight() - spacebarHeight - mouseButtonHeight - movementKeyTileWidth - 6, movementKeyTileWidth + 1, movementKeyTileWidth, "D", rightColor);
        renderBoxWithCenteredText(super.getXCoordinate() + getWidth() / 2 - movementKeyTileWidth / 2 - 1, super.getYCoordinate() + getHeight() - spacebarHeight - mouseButtonHeight - movementKeyTileWidth - 6, movementKeyTileWidth + 1, movementKeyTileWidth, "S", backColor);
        renderBoxWithCenteredText(super.getXCoordinate() + getWidth() / 2 - movementKeyTileWidth / 2 - 1, super.getYCoordinate() + getHeight() - spacebarHeight - mouseButtonHeight - (movementKeyTileWidth * 2) - 9, movementKeyTileWidth + 1, movementKeyTileWidth, "W", forwardColor);
    }

    public void renderBoxWithCenteredText(int x, int y, int width, int height, String text) {
        renderBoxWithCenteredText(x, y, width, height, text, 38);
    }

    public void renderBoxWithCenteredText(int x, int y, int width, int height, String text, int color) {
        this.drawRect(x, y, x + width, y + height, color);
        Minecraft.getMinecraft().fontRendererObj.drawString(getEnumChatFormatting() + text,
                (x + width / 2F) - Minecraft.getMinecraft().fontRendererObj.getStringWidth(text) / 2F,
                y + height / 2F - 4,
                0xffffffff, false);
    }

    public void drawRect(int left, int top, int right, int bottom, int color) {
        if (left < right) {
            int i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            int j = top;
            top = bottom;
            bottom = j;
        }

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(color / 255F, color / 255F, color / 255F, 0.5F);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public EnumChatFormatting getEnumChatFormatting() {
        switch (color.getCurrentValueString()) {
            case "Red":
                return RED;
            case "Gold":
                return GOLD;
            case "Yellow":
                return YELLOW;
            case "Green":
                return GREEN;
            case "Aqua":
                return AQUA;
            case "Blue":
                return BLUE;
            case "Light Purple":
                return LIGHT_PURPLE;
            case "Dark Red":
                return DARK_RED;
            case "Dark Green":
                return DARK_GREEN;
            case "Dark Aqua":
                return DARK_AQUA;
            case "Dark Blue":
                return DARK_BLUE;
            case "Dark Purple":
                return DARK_PURPLE;
            case "White":
                return WHITE;
            case "Gray":
                return GRAY;
            case "Dark Gray":
                return DARK_GRAY;
        }
        return BLACK;
    }

    public String getCurrentColorString() {
        return getEnumChatFormatting() + color.getCurrentValueString();
    }

    public void nextColor() {
        color.next();
    }
}

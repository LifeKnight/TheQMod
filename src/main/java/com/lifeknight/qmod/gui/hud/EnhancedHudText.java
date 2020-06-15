package com.lifeknight.qmod.gui.hud;

import com.lifeknight.qmod.gui.Manipulable;
import com.lifeknight.qmod.variables.LifeKnightCycle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.Arrays;

import static net.minecraft.util.EnumChatFormatting.*;

public abstract class EnhancedHudText extends Manipulable {
    public static ArrayList<EnhancedHudText> textToRender = new ArrayList<>();
    private final LifeKnightCycle contentColor;

    public EnhancedHudText(String name, int defaultX, int defaultY, LifeKnightCycle contentColor) {
        super(name, defaultX, defaultY);
        this.contentColor = contentColor;
        textToRender.add(this);
    }

    public EnhancedHudText(String name, int defaultX, int defaultY) {
        this(name, defaultX, defaultY, new LifeKnightCycle(name + "Color", "Invisible", new ArrayList<>(Arrays.asList(
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
        )), 12));
    }

    public abstract String getTextToDisplay();

    @Override
    public String getDisplayText() {
            return getEnumChatFormatting() + getTextToDisplay();
    }

    public EnumChatFormatting getEnumChatFormatting() {
        switch (contentColor.getCurrentValueString()) {
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
        return getEnumChatFormatting() + contentColor.getCurrentValueString();
    }

    public abstract boolean isVisible();

    public void render() {
        if (this.isVisible()) {
            int stringWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(getDisplayText());
            Gui.drawRect(getXCoordinate(), getYCoordinate(), getXCoordinate() + 60, getYCoordinate() + getHeight(), Integer.MIN_VALUE);
            Minecraft.getMinecraft().fontRendererObj.drawString(getDisplayText(), getXCoordinate() + getWidth() / 2 - stringWidth / 2, getYCoordinate() + 4, 0xffffffff);
        }
    }

    public static void doRender() {
        for (EnhancedHudText hudText : textToRender) {
            hudText.render();
        }
    }

    public void nextColor() {
        contentColor.next();
    }

    @Override
    public int getWidth() {
        return 60;
    }

    @Override
    public int getHeight() {
        return 13;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY, int xPosition, int yPosition, int width, int height, boolean isSelectedButton) {
        Gui.drawRect(xPosition, yPosition, xPosition + width, yPosition + height, Integer.MIN_VALUE);
        int stringWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(getDisplayText());
        Minecraft.getMinecraft().fontRendererObj.drawString(getDisplayText(), xPosition + getWidth() / 2 - stringWidth / 2, yPosition + (height - 8) / 2 + 1, 0xffffffff);
    }
}

package com.lifeknight.qmod.gui.hud;

import com.lifeknight.qmod.gui.Manipulable;
import com.lifeknight.qmod.variables.LifeKnightCycle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;

import static net.minecraft.util.EnumChatFormatting.*;
import static net.minecraft.util.EnumChatFormatting.BLACK;

public class PotionHud extends Manipulable {
    public final LifeKnightCycle color;
    public PotionHud(String name, int defaultX, int defaultY) {
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
        if (Minecraft.getMinecraft().thePlayer.getActivePotionEffects().size() == 0) {
            return Minecraft.getMinecraft().fontRendererObj.getStringWidth("Speed I") + 25;
        } else {
            int longestPotionEffectNameLength = 0;
            for (PotionEffect potionEffect : Minecraft.getMinecraft().thePlayer.getActivePotionEffects()) {
                longestPotionEffectNameLength = Math.max(longestPotionEffectNameLength, Minecraft.getMinecraft().fontRendererObj.getStringWidth(potionEffect.getEffectName()));
            }
            return longestPotionEffectNameLength;
        }
    }

    @Override
    public int getHeight() {
        return Minecraft.getMinecraft().thePlayer.getActivePotionEffects().size() == 0 ? 20 : Minecraft.getMinecraft().thePlayer.getActivePotionEffects().size() * 25 - 5;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY, int xPosition, int yPosition, int width, int height, boolean isSelectedButton) {
        if (Minecraft.getMinecraft().thePlayer.getActivePotionEffects().size() == 0) {
            Potion speedPotion = Potion.potionTypes[1];
            if (speedPotion.hasStatusIcon())
            {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
                int i1 = speedPotion.getStatusIconIndex();
                this.drawTexturedModalRect(xPosition + 1, yPosition + 1, i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
            }
            Minecraft.getMinecraft().fontRendererObj.drawString("Speed I", xPosition + 20, yPosition, 0xffffffff);
            Minecraft.getMinecraft().fontRendererObj.drawString("0:30", xPosition + 20, yPosition + 10, 0xffffffff);
        } else {
            int potionNumber = 0;
            for (PotionEffect potioneffect : Minecraft.getMinecraft().thePlayer.getActivePotionEffects())
            {
                Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
                if(!potion.shouldRender(potioneffect)) continue;
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));

                if (potion.hasStatusIcon())
                {
                    int i1 = potion.getStatusIconIndex();
                    this.drawTexturedModalRect(xPosition + 1, yPosition + 1 + 25 * potionNumber, i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
                }
                String s1 = I18n.format(potion.getName());

                if (potioneffect.getAmplifier() == 1)
                {
                    s1 = s1 + " " + I18n.format("enchantment.level.2");
                }
                else if (potioneffect.getAmplifier() == 2)
                {
                    s1 = s1 + " " + I18n.format("enchantment.level.3");
                }
                else if (potioneffect.getAmplifier() == 3)
                {
                    s1 = s1 + " " + I18n.format("enchantment.level.4");
                }

                Minecraft.getMinecraft().fontRendererObj.drawString(s1, xPosition + 20, yPosition + 25 * potionNumber, 0xffffffff);
                String s = Potion.getDurationString(potioneffect);
                Minecraft.getMinecraft().fontRendererObj.drawString(s, xPosition + 20, yPosition + 25 * potionNumber + 10, 0xffffffff);
                potionNumber++;
            }
        }
    }
    

    public void renderHUD() {
        int potionNumber = 0;
        for (PotionEffect potioneffect : Minecraft.getMinecraft().thePlayer.getActivePotionEffects())
        {
            Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
            if(!potion.shouldRender(potioneffect)) continue;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
            if (potion.hasStatusIcon())
            {
                int i1 = potion.getStatusIconIndex();
                this.drawTexturedModalRect(super.getXCoordinate() + 1, super.getYCoordinate() + 1 + 25 * potionNumber, i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
            }
            potion.renderInventoryEffect(super.getXCoordinate() + 1, super.getYCoordinate() + 1 + 25 * potionNumber, potioneffect, Minecraft.getMinecraft());
            String s1 = I18n.format(potion.getName());

            if (potioneffect.getAmplifier() == 1)
            {
                s1 = s1 + " " + I18n.format("enchantment.level.2");
            }
            else if (potioneffect.getAmplifier() == 2)
            {
                s1 = s1 + " " + I18n.format("enchantment.level.3");
            }
            else if (potioneffect.getAmplifier() == 3)
            {
                s1 = s1 + " " + I18n.format("enchantment.level.4");
            }

            Minecraft.getMinecraft().fontRendererObj.drawString(getEnumChatFormatting() + s1, super.getXCoordinate() + 22, super.getYCoordinate() + 25 * potionNumber, 0xffffffff);
            String s = Potion.getDurationString(potioneffect);
            Minecraft.getMinecraft().fontRendererObj.drawString(getEnumChatFormatting() + s, super.getXCoordinate() + 22, super.getYCoordinate() + 25 * potionNumber + 10, 0xffffffff);
            potionNumber++;
        }
    }

    private void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((x ), (y + height), 0.0).tex(((float)(textureX ) * f), ((float)(textureY + height) * f1)).endVertex();
        worldrenderer.pos((x + width), (y + height), 0.0).tex(((float)(textureX + width) * f), ((float)(textureY + height) * f1)).endVertex();
        worldrenderer.pos((x + width), (y ), 0.0).tex(((float)(textureX + width) * f), ((float)(textureY ) * f1)).endVertex();
        worldrenderer.pos((x ), (y ), 0.0).tex(((float)(textureX ) * f), ((float)(textureY ) * f1)).endVertex();
        tessellator.draw();
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

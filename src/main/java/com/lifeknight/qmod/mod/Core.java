package com.lifeknight.qmod.mod;

import com.lifeknight.qmod.gui.LifeKnightGui;
import com.lifeknight.qmod.gui.ManipulableGui;
import com.lifeknight.qmod.gui.components.LifeKnightButton;
import com.lifeknight.qmod.gui.hud.EnhancedHudText;
import com.lifeknight.qmod.gui.hud.Keystrokes;
import com.lifeknight.qmod.gui.hud.PotionHud;
import com.lifeknight.qmod.variables.LifeKnightBoolean;
import com.lifeknight.qmod.variables.LifeKnightInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.Arrays;

import static com.lifeknight.qmod.variables.LifeKnightVariable.variables;
import static net.minecraft.util.EnumChatFormatting.GOLD;

@net.minecraftforge.fml.common.Mod(modid = Core.modId, name = Core.modName, version = Core.modVersion, clientSideOnly = true)
public class Core {
    public static final String
            modName = "The Q Mod",
            modVersion = "1.1",
            modId = "qmod";
    public static final EnumChatFormatting modColor = GOLD;
    public static GuiScreen guiToOpen = null;
    public static final LifeKnightBoolean runMod = new LifeKnightBoolean("Mod", "Main", true);
    public static final LifeKnightBoolean showClicksPerSecond = new LifeKnightBoolean("ShowCPS", "HUD", true);
    public static final LifeKnightBoolean showFramesPerSecond = new LifeKnightBoolean("ShowFPS", "HUD", true);
    public static final LifeKnightBoolean showPing = new LifeKnightBoolean("ShowPing", "HUD", true);
    public static final LifeKnightBoolean showKeystrokes = new LifeKnightBoolean("ShowKeystrokes", "HUD", true);
    public static final LifeKnightInteger keystrokeFadeSpeed = new LifeKnightInteger("KeystrokeFadeSpeed", "HUD", 5, 1, 15);
    public static final LifeKnightBoolean showPotionHud = new LifeKnightBoolean("ShowPotionHUD", "HUD", true);
    public static final LifeKnightBoolean toggleSprint = new LifeKnightBoolean("ToggleSprint", "HUD", false);
    public static final ArrayList<Long> leftClicks = new ArrayList<>();
    public static final EnhancedHudText pingDisplay = new EnhancedHudText("Ping", 0, 0) {
        @Override
        public String getTextToDisplay() {
            if (Minecraft.getMinecraft().isSingleplayer()) {
                return "0 PING";
            }
            if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.hasPlayerInfo()) {
                try {
                    return Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID()).getResponseTime() + " PING";
                } catch (Exception ignored) {
                }
            }
            return "[N/A] PING";
        }

        @Override
        public boolean isVisible() {
            return showPing.getValue();
        }
    };
    public static final EnhancedHudText framesPerSecondDisplay = new EnhancedHudText("FramesPerSecond", 0, 100) {
        @Override
        public String getTextToDisplay() {
            return Minecraft.getDebugFPS() + " FPS";
        }

        @Override
        public boolean isVisible() {
            return showFramesPerSecond.getValue();
        }
    };
    public static final EnhancedHudText clicksPerSecondDisplay = new EnhancedHudText("ClicksPerSecond", 0, 200) {

        @Override
        public String getTextToDisplay() {
            return leftClicks.size() + " CPS";
        }

        @Override
        public boolean isVisible() {
            return showClicksPerSecond.getValue();
        }
    };

    public static Keystrokes keystrokes;
    public static PotionHud potionHud;
    public static Configuration configuration;

    @EventHandler
    public void init(FMLInitializationEvent initEvent) {
        MinecraftForge.EVENT_BUS.register(this);
        keystrokes = new Keystrokes("Keystrokes", 960, 540);
        potionHud = new PotionHud("PotionHUD", 0, 540);

        configuration = new Configuration();
    }

    @SubscribeEvent
    public void onConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        Utilities.sendQueuedMessages();
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (guiToOpen != null) {
            Minecraft.getMinecraft().displayGuiScreen(guiToOpen);
            guiToOpen = null;
        }
        keystrokes.onRenderTick();
        if (runMod.getValue()) {
            if (Minecraft.getMinecraft().inGameHasFocus) {
                EnhancedHudText.doRender();
                if (showKeystrokes.getValue()) {
                    keystrokes.renderKeystrokes();
                }
                if (showPotionHud.getValue()) {
                    potionHud.renderHUD();
                }
                if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode())) {
                    keystrokes.onInput(0);
                }
                if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode())) {
                    keystrokes.onInput(3);
                }
                if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode())) {
                    keystrokes.onInput(1);
                }
                if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode())) {
                    keystrokes.onInput(2);
                }
                if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode())) {
                    keystrokes.onInput(6);
                }
                if (Mouse.isButtonDown(0)) {
                    keystrokes.onInput(4);
                }
                if (Mouse.isButtonDown(1)) {
                    keystrokes.onInput(5);
                }
                if (toggleSprint.getValue() && Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode())) {
                    EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
                    if (thePlayer.onGround && !thePlayer.movementInput.sneak && thePlayer.movementInput.moveForward >= 0.8 && !thePlayer.isSprinting() && (thePlayer.getFoodStats().getFoodLevel() > 6.0F || thePlayer.capabilities.allowFlying) && !thePlayer.isUsingItem() && !thePlayer.isPotionActive(Potion.blindness)) {
                        thePlayer.setSprinting(true);
                    }
                }
            }
        }
        leftClicks.removeIf(time -> !(time > System.currentTimeMillis() - 1000));
    }

    @SubscribeEvent
    public void onChatMessageReceived(ClientChatReceivedEvent event) {
        if (event.message.getUnformattedText().contains("OpenGL")) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.gui instanceof GuiIngameMenu) {
            event.buttonList.add(new LifeKnightButton("The Q Mod", 100, event.gui.width / 2 - 100, event.buttonList.get(4).yPosition, 200) {
                @Override
                public void work() {
                    openGui(new LifeKnightGui(modColor + "[" + modVersion + "]" + " " + modName, variables, new ArrayList<>(Arrays.asList(new LifeKnightButton("") {
                        @Override
                        public void work() {
                            clicksPerSecondDisplay.nextColor();
                        }

                        @Override
                        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
                            this.displayString = "CPS Color: " + clicksPerSecondDisplay.getCurrentColorString();
                            super.drawButton(mc, mouseX, mouseY);
                        }
                    }, new LifeKnightButton("") {
                        @Override
                        public void work() {
                            framesPerSecondDisplay.nextColor();
                        }

                        @Override
                        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
                            this.displayString = "FPS Color: " + framesPerSecondDisplay.getEnumChatFormatting() + framesPerSecondDisplay.getCurrentColorString();
                            super.drawButton(mc, mouseX, mouseY);
                        }
                    }, new LifeKnightButton("") {
                        @Override
                        public void work() {
                            pingDisplay.nextColor();
                        }

                        @Override
                        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
                            this.displayString = "Ping Color: " + pingDisplay.getEnumChatFormatting() + pingDisplay.getCurrentColorString();
                            super.drawButton(mc, mouseX, mouseY);
                        }
                    }, new LifeKnightButton("") {
                        @Override
                        public void work() {
                            keystrokes.nextColor();
                        }

                        @Override
                        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
                            this.displayString = "Keystrokes Color: " + keystrokes.getEnumChatFormatting() + keystrokes.getCurrentColorString();
                            super.drawButton(mc, mouseX, mouseY);
                        }
                    }, new LifeKnightButton("") {
                        @Override
                        public void work() {
                            potionHud.nextColor();
                        }

                        @Override
                        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
                            this.displayString = "Potion HUD Color: " + potionHud.getEnumChatFormatting() + potionHud.getCurrentColorString();
                            super.drawButton(mc, mouseX, mouseY);
                        }
                    }, new LifeKnightButton("Edit HUD") {
                        @Override
                        public void work() {
                            openGui(new ManipulableGui());
                        }
                    }))));
                }
            });
            for (GuiButton guiButton : event.buttonList) {
                if (guiButton.id == 0 || guiButton.id == 1 || guiButton.id == 12 || guiButton.id == 7) {
                    guiButton.yPosition += 23;
                }
            }
        }
    }

    @SubscribeEvent
    public void guiActionPerformed(GuiScreenEvent.ActionPerformedEvent event) {
        if (event.gui instanceof GuiIngameMenu) {
            if (event.button instanceof LifeKnightButton) {
                ((LifeKnightButton) event.button).work();
            }
        }
    }

    @SubscribeEvent
    public void onMousePressed(InputEvent.MouseInputEvent event) {
        if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
            leftClicks.add(System.currentTimeMillis());
        }
    }

    public static void openGui(GuiScreen guiScreen) {
        guiToOpen = guiScreen;
    }
}
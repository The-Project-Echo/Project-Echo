package net.projectecho.gui.join;

import lombok.val;
import lombok.var;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.projectecho.Echo;
import net.projectecho.gui.toggles.EchoGui;
import net.projectecho.manager.FontManager;
import net.projectecho.utils.RenderingUtils;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JoinGamesGui extends GuiScreen {
    ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
    public List<JoinButton> buttons = new ArrayList<>();
    public float x, y, width, height;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        renderBackground();
        var font = FontManager.getFont(28);
        font.drawCenteredStringWithShadow("Join Bedwars", x + (width / 2), y + 10, Echo.INSTANCE.getClientColor());
        font.drawCenteredStringWithShadow("Join Skywars", x + (width / 2), y + 86, Echo.INSTANCE.getClientColor());
        font.drawCenteredStringWithShadow("Join Murder Mystery", x + (width / 2), y + 163, Echo.INSTANCE.getClientColor());
        font.drawCenteredStringWithShadow("Join Less Popular Games", x + (width / 2), y + 240, Echo.INSTANCE.getClientColor());
        for (val button : buttons)
            button.drawComponent(mouseX, mouseY, button.isHovered(mouseX, mouseY, (float) button.x, (float) button.y, (float) button.width, (float) button.height));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        width = 562;
        height = 400;
        x = (float) scaledResolution.getScaledWidth() / 2 - (width / 2);
        y = (float) scaledResolution.getScaledHeight() / 2 - 200;
        buttons.add(new JoinButton(x + 6, y + 30, 136, 50, this, "/play bedwars_eight_one", "Solo Bedwars", -1));
        buttons.add(new JoinButton(x + 144, y + 30, 136, 50, this, "/play bedwars_eight_two", "Doubles Bedwars", -1));
        buttons.add(new JoinButton(x + 282, y + 30, 136, 50, this, "/play bedwars_four_three", "3v3 Bedwars", -1));
        buttons.add(new JoinButton(x + 420, y + 30, 136, 50, this, "/play bedwars_four_four", "4v4 Bedwars", -1));

        buttons.add(new JoinButton(x + 6, y + 107, 136, 50, this, "/play solo_normal", "Solo Skywars Normal", -1));
        buttons.add(new JoinButton(x + 144, y + 107, 136, 50, this, "/play solo_insane", "Solo Skywars Insane", -1));
        buttons.add(new JoinButton(x + 282, y + 107, 136, 50, this, "/play teams_normal", "Team Skywars Normal", -1));
        buttons.add(new JoinButton(x + 420, y + 107, 136, 50, this, "/play teams_insane", "Team Skywars Insane", -1));

        buttons.add(new JoinButton(x + 144, y + 183, 136, 50, this, "/play solo_normal", "Murder Classic", -1));
        buttons.add(new JoinButton(x + 282, y + 183, 136, 50, this, "/play solo_insane", "Murder Double Up", -1));


        buttons.add(new JoinButton(x + 144, y + 262, 136, 50, this, "/play pit", "The Pit", -1));
        buttons.add(new JoinButton(x + 282, y + 262, 136, 50, this, "/play sb", "Skyblock", -1));
        super.initGui();
    }

    private void renderBackground() {
        RenderingUtils.drawBorderCorneredRectangle(x, y, x + width, y + height, 1, Color.TRANSLUCENT, -1);
        RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, x, y, x + width, y + height, 0);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (val button : buttons)
            if (isHovered(mouseX, mouseY, (float) button.x, (float) button.y, (float) button.width, (float) button.height))
                button.onPressed(mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE)
            mc.displayGuiScreen(new EchoGui());
        if (keyCode == Keyboard.KEY_O)
            mc.displayGuiScreen(new JoinGamesGui());
        super.keyTyped(typedChar, keyCode);
    }

    public boolean isHovered(int mouseX, int mouseY, float x, float y, float width, float height) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
}

package net.projectecho.gui.toggles;

import lombok.var;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.Render;
import net.projectecho.Echo;
import net.projectecho.addon.impl.BedwarsHud;
import net.projectecho.addon.impl.InfoHud;
import net.projectecho.gui.toggles.button.ToggleButton;
import net.projectecho.manager.FontManager;
import net.projectecho.utils.MathUtils;
import net.projectecho.utils.RenderingUtils;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EchoGui extends GuiScreen {

    ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
    public List<ToggleButton> addons = new ArrayList<>();
    public float x, y, width, height, sliding, scroll, lastScroll, scrollTarget = 0, scrollHeight = 28, visibleItems = 6, totalItems = 10, maxScroll, scrollAccumulator = 0;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        width = (float) RenderingUtils.progressiveAnimation(width, 300, 0.6);
        height = (float) RenderingUtils.progressiveAnimation(height, 200, 0.6);

        x = (float) scaledResolution.getScaledWidth() / 2 - (width / 2);
        y = (float) scaledResolution.getScaledHeight() / 2 - (height / 2);

        scroll += (scrollTarget - scroll) * 0.2f;

        RenderingUtils.makeCropBox(x, y, (float) scaledResolution.getScaledWidth() / 2 + (width / 2), (float) scaledResolution.getScaledHeight() / 2 + (height / 2));
        renderBackground();
        RenderingUtils.destroyCropBox();

        RenderingUtils.makeCropBox(x, y + 27, x + 132, y + 200);
        for (ToggleButton addon : addons) {
            addon.color = -1;
            addon.drawComponent(mouseX, mouseY, addon.addon.hovered);
        }
        RenderingUtils.destroyCropBox();
    }

    @Override
    public void initGui() {
        x = (float) scaledResolution.getScaledWidth() / 2 - 150;
        y = (float) scaledResolution.getScaledHeight() / 2 - 100;
        maxScroll = Math.max(0, (totalItems * scrollHeight) - (visibleItems * scrollHeight));
        scroll = 0;
        lastScroll = 0;
        addons.add(new ToggleButton(x + 10, y + 30 - scroll, 122, 24, this, Echo.INSTANCE.getAddonManager().getAddon("agg"), -1));
        addons.add(new ToggleButton(x + 10, y + 58 - scroll, 122, 24, this, Echo.INSTANCE.getAddonManager().getAddon("bw"), -1,
                BedwarsHud.ironOpt, BedwarsHud.goldOpt, BedwarsHud.diamondOpt, BedwarsHud.emeraldOpt, BedwarsHud.ArrowOpt));
        addons.add(new ToggleButton(x + 10, y + 86 - scroll, 122, 24, this, Echo.INSTANCE.getAddonManager().getAddon("cchat"), -1));
        addons.add(new ToggleButton(x + 10, y + 114 - scroll, 122, 24, this, Echo.INSTANCE.getAddonManager().getAddon("ch"), -1));
        addons.add(new ToggleButton(x + 10, y + 142 - scroll, 122, 24, this, Echo.INSTANCE.getAddonManager().getAddon("infohud"), -1,
                InfoHud.coordinates, InfoHud.fps, InfoHud.direction, InfoHud.biomeOpt, InfoHud.cpsOpt));
        addons.add(new ToggleButton(x + 10, y + 170 - scroll, 122, 24, this, Echo.INSTANCE.getAddonManager().getAddon("ip"), -1));
        addons.add(new ToggleButton(x + 10, y + 198 - scroll, 122, 24, this, Echo.INSTANCE.getAddonManager().getAddon("jg"), -1));
        addons.add(new ToggleButton(x + 10, y + 226 - scroll, 122, 24, this, Echo.INSTANCE.getAddonManager().getAddon("keystrokes"), -1));
        addons.add(new ToggleButton(x + 10, y + 254 - scroll, 122, 24, this, Echo.INSTANCE.getAddonManager().getAddon("ph"), -1));
        addons.add(new ToggleButton(x + 10, y + 282 - scroll, 122, 24, this, Echo.INSTANCE.getAddonManager().getAddon("togglesprint"), -1));
    }

    public boolean isHovered(int mouseX, int mouseY, float x, float y, float width, float height) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        if (Mouse.hasWheel()) {
            int delta = Mouse.getDWheel();
            scrollAccumulator += delta;

            if (Math.abs(scrollAccumulator) >= 1f) {
                scrollTarget -= scrollAccumulator * 0.25f;
                scrollTarget = Math.max(0, Math.min(maxScroll, scrollTarget));
                scrollAccumulator = 0f;
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (ToggleButton addon : addons)
            if (addon.addon.hovered)
                addon.onPressed(mouseButton);
    }

    private void renderBackground() {
        var title_font = FontManager.getMedFont(32);
        var scrollbarY = y + 27;
        var scrollbarHeight = (visibleItems * scrollHeight) - 2;
        var scrollThumbHeight = Math.max(10, scrollbarHeight * (scrollbarHeight / (totalItems * scrollHeight)));
        var scrollThumbY = scrollbarY + (scroll / maxScroll) * (scrollbarHeight - scrollThumbHeight);

        RenderingUtils.drawRoundedRect(x, y, x + width, y + height, 10, new Color(10, 10, 15, 215).getRGB());
        RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, x, y, x + width, y + height, 10);
        RenderingUtils.drawRectangle(x + 136, y + 26, x + 137, y + height, new Color(40, 40, 55, 215).getRGB());
        RenderingUtils.drawRectangle(x, y + 25, x + width, y + 26, new Color(40, 40, 55, 215).getRGB());
        RenderingUtils.drawRectangle(x + 2, scrollbarY, x + 7, scrollbarY + scrollbarHeight, new Color(10, 10, 20, 215).getRGB());
        RenderingUtils.drawRectangle(x + 3, scrollThumbY + 1, x + 6, (scrollThumbY + scrollThumbHeight) - 1, new Color(40, 40, 55, 215).getRGB());
        title_font.drawCenteredStringWithShadow("Project Echo: Client 1.0.1", x + (width / 2), y + 5, new Color(145, 145, 165).getRGB());
    }
}

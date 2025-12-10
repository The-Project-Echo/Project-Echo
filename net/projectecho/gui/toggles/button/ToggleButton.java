package net.projectecho.gui.toggles.button;

import lombok.val;
import lombok.var;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.projectecho.Echo;
import net.projectecho.gui.toggles.Component;
import net.projectecho.addon.api.Addon;
import net.projectecho.gui.toggles.EchoGui;
import net.projectecho.gui.toggles.Option;
import net.projectecho.manager.FontManager;
import net.projectecho.utils.CustomFontRenderer;
import net.projectecho.utils.RenderingUtils;
import org.lwjgl.Sys;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToggleButton extends Component {

    public double slide;
    public Addon addon;
    public List<Option> options = new ArrayList<>();
    public int offset = 40;
    public boolean cust;

    public ToggleButton(double x, double y, double width, double height, EchoGui parent, Addon addon, int color, Option... options) {
        super(x, y, width, height, parent, color);
        this.addon = addon;
        Collections.addAll(this.options, options);
        slide = 0;
    }

    public ToggleButton(double x, double y, double width, double height, EchoGui parent, Addon addon, int color, boolean cust, Option... options) {
        super(x, y, width, height, parent, color);
        this.addon = addon;
        this.cust = cust;
        Collections.addAll(this.options, options);
        slide = 0;
    }

    @Override
    public void onPressed(int mouseX, int mouseY, int key) {
        if (addon.hovered)
            if (key == 0)
                addon.setState(!addon.isState());
        if (key == 1)
            parent.currentAddon = addon;
    }

    @Override
    public void drawComponent(int mouseX, int mouseY, boolean hovered) {
        var font = FontManager.getMedFont(22);
        RenderingUtils.drawRoundedRect((float) x, (float) y - parent.scroll, (float) (x + width), (float) (y + height) - parent.scroll,
                8, new Color(10, 10, 15, 170).getRGB());
        RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, (float) x, (float) y - parent.scroll, (float) (x + width),
                (float) (y + height) - parent.scroll, 8);
        font.drawStringWithShadow(addon.getDisplayName(), (float) (x + 8), (float) (y + 7) - parent.scroll,
                addon.isState() ? Echo.INSTANCE.getClientColor() : new Color(145, 145, 165).getRGB());

        RenderingUtils.drawRoundedRect((float) ((x + width) - 36), (float) (y + 4) - parent.scroll, (float) ((x + width) - 4), (float)
                ((y + height) - 4) - parent.scroll, 6, new Color(40, 40, 55, 115).getRGB());
        RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, (float) ((x + width) - 36), (float) (y + 4), (float) ((x + width) - 4),
                (float) ((y + height) - 4), 6);

        var state = addon.isState();
        RenderingUtils.drawRoundedRect((float) (state ? (x + width) - 17 : (x + width) - 35), (float) (y + 5) - parent.scroll, (float) (state ? (x + width) - 5 :
                (x + width) - 17), (float) ((y + height) - 5) - parent.scroll, 6, new Color(state ? 0 : 250, state ? 250 : 0, 0, 115).getRGB());
        RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, (float) (state ? (x + width) - 17 : (x + width) - 35), (float) (y + 5) - parent.scroll,
                (float) (state ? (x + width) - 5 : (x + width) - 17), (float) ((y + height) - 5) - parent.scroll, 6);
        var hoverY = (y + 4);
        addon.hovered = isHovered(mouseX, mouseY, (float) ((x + width) - 36), (float) hoverY - parent.scroll, 32F,
                (float) height - 4);
        int off = 12;
        for (val option : options) {
            createCheckboxes(mouseX, mouseY, font, option, (int) (parent.x + 140), (int) (parent.y + 36 + off), 120, 15);
            off += 22;
        }
    }

    public void createCheckboxes(int mouseX, int mouseY, CustomFontRenderer font, Option option, int x, int y, int width, int height) {
        if (parent.currentAddon == addon) {
            option.hovered = isHovered(mouseX, mouseY, x, y, width, height);
            font.drawStringWithShadow(option.display, x + 22, y + 2, option.isVal() ? Echo.INSTANCE.getClientColor() : new Color(145, 145, 165).getRGB());
            RenderingUtils.drawRoundedRect(x, y - 2, x + 18, y + 16, 8
                    , new Color(0, 0, 0, 100).getRGB());
            RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, x, y - 2, x + 18, y + 16, 8);
            RenderingUtils.drawRoundedRect(x + 2, y, x + 16, y + 14, 7
                    , new Color(option.val ? 0 : 255, option.val ? 255 : 0, 0, 100).getRGB());
            RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, x + 2, y, x + 16, y + 14, 7);
        }
    }

    public boolean isHovered(int mouseX, int mouseY, float x, float y, float width, float height) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
}

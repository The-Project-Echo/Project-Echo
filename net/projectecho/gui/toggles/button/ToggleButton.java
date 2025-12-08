package net.projectecho.gui.toggles.button;

import lombok.var;
import net.projectecho.Echo;
import net.projectecho.gui.toggles.Component;
import net.projectecho.addon.api.Addon;
import net.projectecho.gui.toggles.EchoGui;
import net.projectecho.gui.toggles.Option;
import net.projectecho.manager.FontManager;
import net.projectecho.utils.RenderingUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToggleButton extends Component {

    public double slide;
    public Addon addon;
    List<Option> options = new ArrayList<>();
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
    public void onPressed(int key) {
        super.onPressed(key);
        for (Option option : options)
            if (option.hovered)
                option.setVal(!option.val);
        if(addon.hovered)
            addon.setState(!addon.isState());
    }

    @Override
    public void drawComponent(int mouseX, int mouseY, boolean hovered) {
        var font = FontManager.getMedFont(22);
//        RenderingUtils.drawBorderedRectangle((float) x, (float) y, (float) (x + width), (float) (y + height), hovered ? 1.5f : 1,
//                new Color(25, 25, 25, 0).getRGB(), new Color(255, 255, 255, 120).getRGB());

        RenderingUtils.drawRoundedRect((float) x, (float) y - parent.scroll, (float) (x + width), (float) (y + height) - parent.scroll, 8, new Color(10, 10, 15, 170).getRGB());
        RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, (float) x, (float) y - parent.scroll, (float) (x + width), (float) (y + height) - parent.scroll, 8);
        font.drawStringWithShadow(addon.getDisplayName(), (float) (x + 8), (float) (y + 7) - parent.scroll,
                addon.isState() ? Echo.INSTANCE.getClientColor() : new Color(145, 145, 165).getRGB());

        RenderingUtils.drawRoundedRect((float) ((x + width) - 36), (float) (y + 4) - parent.scroll, (float) ((x + width) - 4), (float)
                        ((y + height) - 4) - parent.scroll, 6, new Color(40, 40, 55, 115).getRGB());
        RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, (float) ((x + width) - 36), (float) (y + 4), (float) ((x + width) - 4),
                (float) ((y + height) - 4), 6);

        var state = addon.isState();
        RenderingUtils.drawRoundedRect((float) (state ? (x + width) - 17 : (x + width) - 35), (float) (y + 5) - parent.scroll, (float) (state ? (x + width) - 5 :
                        (x + width) - 17), (float) ((y + height) - 5) - parent.scroll, 6, new Color(state ? 0 : 250, state ? 250 : 0, 0, 115).getRGB());
        RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, (float) (state ? (x + width) - 17 : (x + width) - 35), (float) (y + 5) - parent.scroll, (float) (state ? (x + width) - 5 :
                (x + width) - 17), (float) ((y + height) - 5) - parent.scroll, 6);
        var hoverY = (y + 4);
        addon.hovered = isHovered(mouseX, mouseY, (float) ((x + width) - 36), (float) hoverY - parent.scroll, 32F,
                (float) height - 4);
                int off = 12;
//        if (!cust)
//            createAddonCheckbox(mouseX, mouseY, addon, (int) (x + 4), (int) ((y + offset)), 6, 6);
//        else
//            addon.hovered = isHovered(mouseX, mouseY, (float) x, (float) y, (float) width, (float) height);
        for (Option option : options) {
            createCheckbox(mouseX, mouseY, option, (int) (parent.x + 140), (int) ((parent.y + 20) + off), 6, 6);
            off += 12;
        }
    }

    public void createCheckbox(int mouseX, int mouseY, Option option, int x, int y, int width, int height){
        if(option.val) {
            RenderingUtils.drawRectangle(x, y ,x + width, y + height, Echo.INSTANCE.getClientColor());
            RenderingUtils.drawRectangle(x, y ,x + width, y + height, Echo.INSTANCE.getClientColor());
            RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, x, y, x + width, y + height, 8);
        }
        RenderingUtils.drawBorderedRectangle(x, y, x + width, y + height, 1, Color.TRANSLUCENT, -1);
        FontManager.getFont(18).drawStringWithShadow(option.display, x + 9, y - 1, -1);
            option.hovered = isHovered(mouseX, mouseY, x, y, width, height);
    }

    public void createAddonCheckbox(int mouseX, int mouseY, Addon addon, int x, int y, int width, int height){
        if(addon.isState()) {
            RenderingUtils.drawRectangle(x, y ,x + width, y + height, Echo.INSTANCE.getClientColor());
            RenderingUtils.drawRectangle(x, y ,x + width, y + height, Echo.INSTANCE.getClientColor());
            RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, x, y, x + width, y + height, 8);
        }
        RenderingUtils.drawBorderedRectangle(x, y, x + width, y + height, 1, Color.TRANSLUCENT, -1);
        FontManager.getFont(18).drawStringWithShadow("Enabled", x + 9, y - 1, -1);
        addon.hovered = isHovered(mouseX, mouseY, x, y, width, height);
    }

    public boolean isHovered(int mouseX, int mouseY, float x, float y, float width, float height) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
}

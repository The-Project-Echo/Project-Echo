package net.projectecho.gui.join;

import lombok.var;
import net.minecraft.client.Minecraft;
import net.projectecho.Echo;
import net.projectecho.manager.FontManager;
import net.projectecho.utils.RenderingUtils;

import java.awt.*;

public class JoinButton extends Component {

    public String command, text;
    public double slide;

    public JoinButton(double x, double y, double width, double height, JoinGamesGui parent, String command, String text, int color) {
        super(x, y, width, height, parent, color);
        this.command = command;
        this.text = text;
        slide = 0;
    }

    @Override
    public void onPressed(int key) {
        Minecraft.getMinecraft().thePlayer.sendChatMessage(command);
        super.onPressed(key);
    }

    @Override
    public void drawComponent(int mouseX, int mouseY, boolean hovered) {
        hovered = isHovered(mouseX, mouseY, (float) x, (float) y, (float) width, (float) height);
        var font = FontManager.getFont(25);
        RenderingUtils.drawBorderedRectangle((float) x, (float) y, (float) (x + width), (float) (y + height), hovered ? 1.5f : 1,
                new Color(25, 25, 25, 25).getRGB(), new Color(255, 255, 255, 120).getRGB());
        RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, x, y, x + width, y + height, 0);
        RenderingUtils.drawBlurredRect(RenderingUtils.BlurType.NORMAL, x, y, x + width, y + height, 0);
        font.drawCenteredStringWithShadow(text, (int) (x + width / 2), (int) this.y + 20, Echo.INSTANCE.getClientColor());
    }

    public boolean isHovered(int mouseX, int mouseY, float x, float y, float width, float height) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
}

package attic.notium.gui;

import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;

@Environment(EnvType.CLIENT)
public class TransparentGridPanel extends WGridPanel {

    @Override
    public void paint(DrawContext context, int x, int y, int mouseX, int mouseY) {
        for(WWidget child : children) {
            child.paint(context, x + child.getX(), y + child.getY(), mouseX - child.getX(), mouseY - child.getY());
        }
    }
}
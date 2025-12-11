package attic.notium.gui;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class VanillaTextField extends WTextField {

    private static final int VANILLA_BG = 0x99_000000;
    private static final int VANILLA_BORDER = 0x00_000000;

    public VanillaTextField(Text suggestion) {
        super(suggestion);
    }

    @Override
    protected void renderBox(DrawContext context, int x, int y) {
        ScreenDrawing.coloredRect(context, x - 1, y - 1, width + 2, height + 2, VANILLA_BORDER);
        ScreenDrawing.coloredRect(context, x, y, width, height, VANILLA_BG);
    }
}
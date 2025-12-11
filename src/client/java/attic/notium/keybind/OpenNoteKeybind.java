package attic.notium.keybind;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class OpenNoteKeybind {

    public static KeyBinding OPEN_NOTE_SCREEN;

    public static void register() {
        OPEN_NOTE_SCREEN = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key.notium.open_note",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_X,
                        "category.notium"
                )
        );
    }
}

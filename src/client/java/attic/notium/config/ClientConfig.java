package attic.notium.config;

import org.lwjgl.glfw.GLFW;

public record ClientConfig(Keybinds keybinds) implements Config {

    public ClientConfig() {
        this(new Keybinds());
    }

    public record Keybinds(int OPEN_NOTE) {
        public Keybinds() {
            this(GLFW.GLFW_KEY_X);
        }
    }
}

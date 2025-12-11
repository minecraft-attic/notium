package attic.notium;

import attic.notium.config.ClientConfig;
import attic.notium.config.ConfigManager;
import attic.notium.gui.NoteScreen;
import attic.notium.keybind.OpenNoteKeybind;

import attic.notium.network.NotiumNetworking;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotiumClient implements ClientModInitializer {

    public static final String MOD_ID = "Notium";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static ConfigManager<ClientConfig> CONFIG;

	@Override
	public void onInitializeClient() {
        CONFIG = new ConfigManager<>(ClientConfig.class);
        CONFIG.load();

        NotiumNetworking.register();
        OpenNoteKeybind.register();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (OpenNoteKeybind.OPEN_NOTE_SCREEN.wasPressed()) {
                try {
                    String playerPosition = "0 0 0";

                    if (client.player != null) {
                        playerPosition = client.player.getBlockX() + " " + client.player.getBlockY() + " " + client.player.getBlockZ();
                    }

                    client.setScreen(NoteScreen.createScreen(playerPosition));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        LOGGER.info("Mod has been loaded");
	}
}
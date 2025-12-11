package attic.notium;

import attic.notium.config.ConfigManager;
import attic.notium.config.ServerConfig;

import attic.notium.network.Packets;
import net.fabricmc.api.DedicatedServerModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Notium implements DedicatedServerModInitializer {

	public static final String MOD_ID = "Notium";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static ConfigManager<ServerConfig> CONFIG;

	@Override
	public void onInitializeServer() {
        CONFIG = new ConfigManager<>(ServerConfig.class);
        CONFIG.load();

        Packets.register();

        LOGGER.info("Mod has been loaded");
	}
}
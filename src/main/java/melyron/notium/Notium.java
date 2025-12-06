package melyron.notium;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Notium implements ModInitializer {
	public static final String MOD_ID = "Notium";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ConfigManager.load();
        CommandManager.register();
        LOGGER.info("Mod has been initialized");
	}
}
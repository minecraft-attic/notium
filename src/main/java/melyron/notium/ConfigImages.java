package melyron.notium;

import java.util.HashMap;
import java.util.Map;

public class ConfigImages {

    private static final Map<String, String> DIMENSION_IMAGE_MAP = new HashMap<>();

    static {
        DIMENSION_IMAGE_MAP.put("minecraft:overworld", ConfigManager.config.overworldImageUrl);
        DIMENSION_IMAGE_MAP.put("minecraft:the_nether", ConfigManager.config.netherImageUrl);
        DIMENSION_IMAGE_MAP.put("minecraft:the_end", ConfigManager.config.endImageUrl);
    }

    public static String getThumbnailUrl(String dimension) {
        return DIMENSION_IMAGE_MAP.getOrDefault(dimension, ConfigManager.config.overworldImageUrl);
    }
}

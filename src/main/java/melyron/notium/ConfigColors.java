package melyron.notium;

import java.util.HashMap;
import java.util.Map;

public class ConfigColors {

    private static final Map<String, Integer> DIMENSION_COLOR_MAP = new HashMap<>();

    static {
        DIMENSION_COLOR_MAP.put("minecraft:overworld", 0x3BA55D);
        DIMENSION_COLOR_MAP.put("minecraft:the_nether", 0xED4245);
        DIMENSION_COLOR_MAP.put("minecraft:the_end", 0x9B59B6);
    }

    public static int getColor(String dimension) {
        return DIMENSION_COLOR_MAP.getOrDefault(dimension, 0x99AAB5);
    }
}

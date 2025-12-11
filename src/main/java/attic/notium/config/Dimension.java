package attic.notium.config;

public enum Dimension {
    OVERWORLD("minecraft:overworld"),
    NETHER("minecraft:the_nether"),
    END("minecraft:the_end");

    private final String id;

    Dimension(String id) {
        this.id = id;
    }

    public static Dimension fromId(String id) {
        for (Dimension d : values()) {
            if (d.id.equals(id)) return d;
        }
        throw new IllegalArgumentException("Unknown dimension: " + id);
    }
}

package attic.notium.config;

public record ServerConfig(
        Webhook webhook,
        Images images,
        Colors colors,
        Features features
) implements Config {

    public ServerConfig() {
        this(new Webhook(), new Images(), new Colors(), new Features());
    }

    public record Webhook(String url) {
        public Webhook() {
            this("");
        }
    }

    public record Images(String overworld, String nether, String end) {
        public Images() {
            this(
                    "https://static.wikia.nocookie.net/minecraft_gamepedia/images/c/c0/Grass_Block_JE3.png",
                    "https://static.wikia.nocookie.net/minecraft_gamepedia/images/0/02/Netherrack_JE4_BE2.png",
                    "https://static.wikia.nocookie.net/minecraft_gamepedia/images/4/43/End_Stone_JE3_BE2.png"
            );
        }

        public String getImage(Dimension dimension) {
            return switch (dimension) {
                case OVERWORLD -> overworld;
                case NETHER    -> nether;
                case END       -> end;
            };
        }
    }

    public record Colors(int overworld, int nether, int end) {
        public Colors() {
            this(0x3BA55D, 0xED4245, 0x9B59B6);
        }

        public int getColor(Dimension dimension) {
            return switch (dimension) {
                case OVERWORLD -> overworld;
                case NETHER    -> nether;
                case END       -> end;
            };
        }
    }

    public record Features(String positionFormat) {
        public Features() {
            this("%.0f %.0f %.0f");
        }
    }
}

package attic.notium.util;

import attic.notium.Notium;
import attic.notium.config.Dimension;
import attic.notium.config.ServerConfig;

import com.google.gson.JsonObject;

public class EmbedBuilder {

    private String author;
    private String thumbnail;
    private String title;
    private String description;
    private String footer;

    private Dimension dimension;

    public EmbedBuilder() {}

    public EmbedBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public EmbedBuilder setThumbnail(String url) {
        this.thumbnail = url;
        return this;
    }

    public EmbedBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public EmbedBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public EmbedBuilder setFooter(String footer) {
        this.footer = footer;
        return this;
    }

    public EmbedBuilder setDimension(Dimension dimension) {
        this.dimension = dimension;
        return this;
    }

    public JsonObject build() {
        ServerConfig config = Notium.CONFIG.get();

        JsonObject embed = new JsonObject();
        JsonObject thumbnailObj = new JsonObject();
        JsonObject authorObj = new JsonObject();
        JsonObject footerObj = new JsonObject();
        JsonObject root = new JsonObject();

        embed.addProperty("color", config.colors().getColor(this.dimension));
        embed.addProperty("title", this.title);
        embed.addProperty("description", this.description);

        thumbnailObj.addProperty("url",
                this.thumbnail != null ? this.thumbnail : config.images().getImage(this.dimension));
        embed.add("thumbnail", thumbnailObj);

        authorObj.addProperty("name", this.author);
        embed.add("author", authorObj);

        footerObj.addProperty("text",
                (this.footer != null ? this.footer : localizeDimension(this.dimension)));
        embed.add("footer", footerObj);

        root.add("embeds", Json.array(embed));
        return root;
    }

    private String localizeDimension(Dimension dimension) {
        return switch (dimension) {
            case OVERWORLD -> "Верхний мир";
            case NETHER -> "Нижний мир";
            case END -> "Энд";
        };
    }
}

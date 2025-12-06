package melyron.notium;

import com.google.gson.JsonObject;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebhookManager {

    public static void send(ServerPlayerEntity player, WebhookData data) {
        PlayerManager.sendMessage(player, Text.literal("Note is sending"), true);

        String playerName = PlayerManager.getName(player);
        String dimension = PlayerManager.getDimension(player);

        new Thread(() -> {
            JsonObject embed = new JsonObject();
            embed.addProperty("color", ConfigColors.getColor(dimension));
            embed.addProperty("title", data.title);
            embed.addProperty("description",
                    "**Координаты:** `" + data.position + "`" +
                            (data.description.isEmpty() ? "" : ("\n\n" + data.description))
            );

            JsonObject author = new JsonObject();
            author.addProperty("name", "Заметка от " + playerName);
            embed.add("author", author);

            JsonObject footer = new JsonObject();
            footer.addProperty("text", dimension
                    .replaceAll("minecraft:overworld", "Верхний мир")
                    .replaceAll("minecraft:the_nether", "Нижний мир")
                    .replaceAll("minecraft:the_end", "Энд"));
            embed.add("footer", footer);

            JsonObject thumbnail = new JsonObject();
            thumbnail.addProperty("url", ConfigImages.getThumbnailUrl(dimension));
            embed.add("thumbnail", thumbnail);

            JsonObject root = new JsonObject();
            root.add("embeds", Json.array(embed));

            try {
                sendWebhook(root);
                PlayerManager.sendMessage(player, Text.literal("Note has been sent").formatted(Formatting.GREEN), true);
            } catch (Exception e) {
                e.printStackTrace();
                PlayerManager.sendMessage(player, Text.literal("Note was not delivered").formatted(Formatting.RED), true);
                PlayerManager.sendMessage(player, Text.literal("[Notium] Not can't be delivered.\n[Dealium] Contact to Server Administrator.").formatted(Formatting.RED), false);
            }
        }).start();
    }

    private static void sendWebhook(JsonObject embed) throws Exception {
        URL url = new URL(ConfigManager.config.webhook);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(embed.toString().getBytes());
        }

        conn.getInputStream().close();
    }
}
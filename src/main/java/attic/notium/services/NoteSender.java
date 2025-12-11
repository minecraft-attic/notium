package attic.notium.services;

import attic.core.api.Api;
import attic.notium.Notium;
import attic.notium.config.Dimension;
import attic.notium.data.Note;
import attic.notium.util.EmbedBuilder;

import com.google.gson.JsonObject;
import net.minecraft.server.network.ServerPlayerEntity;

public class NoteSender {

    public static void send(ServerPlayerEntity player, Note note) {
        if (player == null) return;

        if (!isValidPosition(note.position())) {
            Api.Player.Message.error(player, "Invalid Position");
            return;
        }

        JsonObject embed = new EmbedBuilder()
                .setDimension(Dimension.fromId(player.getServerWorld().getRegistryKey().getValue().toString()))
                .setAuthor("Заметка от " + player.getName().getString())
                .setTitle(note.title())
                .setDescription("**Координаты:** `" + note.position() + "`" +
                        (note.description().isEmpty() ? "" : "\n\n" + note.description()))
                .build();

        Api.Player.Message.info(player, "Note is sending");

        int code = Api.sendWebhook(Notium.CONFIG.get().webhook().url(), embed);

        if (code < 200 || code >= 300) {
            Api.Player.Message.error(player, "Note was not delivered");
        } else {
            Api.Player.Message.success(player, "Note has been sent");
        }
    }

    private static boolean isValidPosition(String position) {
        String[] parts = position.trim().split("\\s+");
        if (parts.length < 2 || parts.length > 3) return false;

        try {
            for (String part : parts) {
                double d = Double.parseDouble(part);
                if (d != Math.floor(d)) return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

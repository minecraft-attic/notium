package attic.notium.network;

import attic.notium.data.Note;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class NotiumNetworking {

    public static void register() {
        PayloadTypeRegistry.playC2S().register(
                SendNoteC2SPayload.ID,
                SendNoteC2SPayload.CODEC
        );
    }

    public static void sendNote(String title, String description, String position) {
        ClientPlayNetworking.send(new SendNoteC2SPayload(new Note(title, description, position)));
    }
}

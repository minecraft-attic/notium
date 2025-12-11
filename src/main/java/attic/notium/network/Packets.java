package attic.notium.network;

import attic.notium.data.Note;
import attic.notium.services.NoteSender;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class Packets {

    public static final CustomPayload.Id<SendNoteC2SPayload> C2S_ID = new CustomPayload.Id<>(Identifier.of("notium", "send_note"));

    public static void register() {
        PayloadTypeRegistry.playC2S().register(C2S_ID, SendNoteC2SPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(C2S_ID, (packet, context) -> {
            Note note = new Note(packet.note().title(), packet.note().description(), packet.note().position());
            NoteSender.send(context.player(), note);
        });
    }
}

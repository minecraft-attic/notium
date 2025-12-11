package attic.notium.network;

import attic.notium.data.Note;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record SendNoteC2SPayload(Note note) implements CustomPayload {

    public static final Identifier SEND_NOTE_PAYLOAD_ID = Identifier.of("notium", "send_note");

    public static final CustomPayload.Id<SendNoteC2SPayload> ID = new CustomPayload.Id<>(SEND_NOTE_PAYLOAD_ID);

    public static final PacketCodec<RegistryByteBuf, Note> NOTE_CODEC =
            PacketCodec.tuple(
                    PacketCodecs.STRING, Note::title,
                    PacketCodecs.STRING, Note::description,
                    PacketCodecs.STRING, Note::position,
                    Note::new
            );

    public static final PacketCodec<RegistryByteBuf, SendNoteC2SPayload> CODEC =
            PacketCodec.tuple(
                    NOTE_CODEC,
                    SendNoteC2SPayload::note,
                    SendNoteC2SPayload::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}

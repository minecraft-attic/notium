package melyron.notium;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PlayerManager {

    public static String getName(ServerPlayerEntity player) {
        return player.getName().getString();
    }

    public static String getDimension(ServerPlayerEntity player) {
        return player.getServerWorld().getRegistryKey().getValue().toString();
    }

    public static void sendMessage(ServerPlayerEntity player, Text message, boolean overlay) {
        player.sendMessage(message, overlay);
    }
}

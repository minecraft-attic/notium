package melyron.notium;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class CommandManager {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

            // ===============================
            //           /note
            // ===============================

            dispatcher.register(
                    net.minecraft.server.command.CommandManager.literal("note")
                            .then(net.minecraft.server.command.CommandManager.argument("title", StringArgumentType.string())
                                    .executes(CommandManager::executeNote)
                                    .then(net.minecraft.server.command.CommandManager.argument("description", StringArgumentType.string())
                                            .executes(CommandManager::executeNote)
                                            .then(net.minecraft.server.command.CommandManager.argument("position", StringArgumentType.string())
                                                    .executes(CommandManager::executeNote)
                                            )
                                    )
                            )
            );



            // ===============================
            //           /notium
            // ===============================

            dispatcher.register(
                    net.minecraft.server.command.CommandManager.literal("notium")
                            .requires(source -> source.hasPermissionLevel(4))
                            .then(net.minecraft.server.command.CommandManager.literal("webhook")
                                    .then(net.minecraft.server.command.CommandManager.argument("url", StringArgumentType.string())
                                            .executes(CommandManager::executeNotium)
                                    )
                            )
            );
        });
    }

    private static int executeNote(CommandContext<ServerCommandSource> ctx) {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        if (player == null) return 0;

        String title = StringArgumentType.getString(ctx, "title");
        String position = "";
        String description = "";

        try {
            position = StringArgumentType.getString(ctx, "position");
            description = StringArgumentType.getString(ctx, "description");
        } catch (Exception ignored) {}

        if (position.isEmpty()) {
            position = String.format(ConfigManager.config.positionFormat, player.getX(), player.getY(), player.getZ());
        }

        if (!WebhookCache.check(ConfigManager.config.webhook)) {
            PlayerManager.sendMessage(player, Text.literal("[Notium] Invalid Webhook URL").formatted(Formatting.RED), false);
            return 0;
        }

        if (!isValidPosition(position)) {
            PlayerManager.sendMessage(player, Text.literal("[Notium] Invalid Position").formatted(Formatting.RED), false);
            return 0;
        }

        WebhookManager.send(player, new WebhookData(title, position, description));

        return 1;
    }

    private static int executeNotium(CommandContext<ServerCommandSource> ctx) {
        String webhook = StringArgumentType.getString(ctx, "url");

        if (!WebhookCache.check(webhook)) {
            ctx.getSource().sendError(Text.literal("[Notium] Invalid Webhook URL"));
            return 0;
        }

        ConfigManager.config.webhook = webhook;
        ConfigManager.save();

        ctx.getSource().sendFeedback(() -> Text.literal("[Notium] Webhook has been updated"), false);
        return 1;
    }

    private static boolean isValidPosition(String position) {
        if (position == null || position.isEmpty()) return false;

        String[] parts = position.trim().split("\\s+");
        if (parts.length < 2 || parts.length > 3) return false;

        try {
            for (String part : parts) {
                Double.parseDouble(part);
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}

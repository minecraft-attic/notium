package attic.notium.gui;

import attic.notium.network.NotiumNetworking;

import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WTextField;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import org.jetbrains.annotations.NotNull;

public class NoteScreen extends Screen {

    public NoteScreen() {
        super(Text.translatable("text.notium.screen_title"));
    }

    public static Screen createScreen(String position) {
        int startCol = 1;
        int fieldWidth = 10;

        int labelColor = 0xE0E0E0;

        TransparentGridPanel root = new TransparentGridPanel();

        root.add(new WLabel(Text.translatable("text.notium.title")).setColor(labelColor), startCol, 1, fieldWidth, 1);
        WTextField titleField = customize(new VanillaTextField(Text.literal("")));
        titleField.setMaxLength(100);
        root.add(titleField, startCol, 2, fieldWidth, 1);

        root.add(new WLabel(Text.literal("")), 0, 3, 12, 1);

        root.add(new WLabel(Text.translatable("text.notium.description")).setColor(labelColor), startCol, 4, fieldWidth, 1);
        WTextField descriptionField = customize(new VanillaTextField(Text.literal("")));
        descriptionField.setMaxLength(500);
        root.add(descriptionField, startCol, 5, fieldWidth, 3);

        root.add(new WLabel(Text.literal("")), 0, 6, 12, 1);

        root.add(new WLabel(Text.translatable("text.notium.position")).setColor(labelColor), startCol, 7, fieldWidth, 1);
        WTextField positionField = customize(new VanillaTextField(Text.literal("")));
        positionField.setMaxLength(100);
        positionField.setSuggestion(Text.literal(position));
        root.add(positionField, startCol, 8, fieldWidth, 1);

        root.add(new WLabel(Text.literal("")), 0, 9, 12, 1);

        WButton sendButton = getButton(titleField, descriptionField, positionField);
        root.add(sendButton, 4, 10, 4, 1);

        root.add(new WLabel(Text.literal("")), 0, 11, 12, 1);

        LightweightGuiDescription desc = new LightweightGuiDescription();
        desc.setRootPanel(root);
        root.validate(desc);

        return new CottonClientScreen(desc);
    }

    private static VanillaTextField customize(VanillaTextField textField) {
        int textFieldEnabledColor = 0xFFFFFF;
        int textFieldDisabledColor = 0x707070;
        int textFieldSuggestionColor = 0xA0A0A0;

        textField.setEnabledColor(textFieldEnabledColor);
        textField.setDisabledColor(textFieldDisabledColor);
        textField.setSuggestionColor(textFieldSuggestionColor);
        textField.setEditable(true);

        return textField;
    }

    private static @NotNull WButton getButton(WTextField titleField, WTextField descriptionField, WTextField positionField) {
        WButton sendButton = new WButton(Text.translatable("text.notium.send_button"));

        sendButton.setOnClick(() -> {
            String title = titleField.getText();
            String description = descriptionField.getText();
            String position = positionField.getText();

            if (title.isEmpty()) return;

            if (position.isBlank()) {
                var p = MinecraftClient.getInstance().player;
                if (p != null)
                    position = p.getBlockX() + " " + p.getBlockY() + " " + p.getBlockZ();
            }

            NotiumNetworking.sendNote(title, description, position);
            MinecraftClient.getInstance().setScreen(null);
        });

        return sendButton;
    }
}

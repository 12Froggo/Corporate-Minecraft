package froggo.corporateminecraft.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ComputerScreen extends Screen {

    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 250;

    public ComputerScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();

        int left =
                (this.width - WINDOW_WIDTH) / 2;

        int top =
                (this.height - WINDOW_HEIGHT) / 2;

        // Inbox button
        Button inboxButton = Button.builder(
                Component.literal("Inbox"),

                button -> {

                    this.minecraft.gui.setScreen(
                            new InboxScreen(
                                    Component.literal("Inbox")
                            )
                    );

                }

        ).bounds(
                left + 20,
                top + 50,
                100,
                20
        ).build();

        this.addRenderableWidget(inboxButton);
    }

    @Override
    public void extractRenderState(
            GuiGraphicsExtractor graphics,
            int mouseX,
            int mouseY,
            float delta
    ) {

        int left =
                (this.width - WINDOW_WIDTH) / 2;

        int top =
                (this.height - WINDOW_HEIGHT) / 2;

        // Computer background
        graphics.fill(
                left,
                top,
                left + WINDOW_WIDTH,
                top + WINDOW_HEIGHT,
                0xFF202020
        );

        // Header
        graphics.fill(
                left,
                top,
                left + WINDOW_WIDTH,
                top + 30,
                0xFF303030
        );

        // Computer title
        graphics.text(
                this.font,
                "CORPORATE COMPUTER",
                left + 10,
                top + 10,
                0xFFFFFFFF,
                true
        );

        // Unread mail count
        graphics.text(
                this.font,
                Mailbox.getUnreadCount()
                        + " unread messages",
                left + 20,
                top + 85,
                0xFFAAAAAA,
                false
        );

        graphics.text(
                this.font,
                "Your current reputation: " +
                        ReputationManager.getReputation(),
                left + 20,
                top + 120,
                0XFFAAAAAA,
                false


        );

        // Render buttons LAST.
        super.extractRenderState(
                graphics,
                mouseX,
                mouseY,
                delta
        );
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
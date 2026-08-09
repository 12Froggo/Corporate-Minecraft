package froggo.corporateminecraft.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class InboxScreen extends Screen {

    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 300;

    public InboxScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {

        super.init();

        int left =
                (this.width - WINDOW_WIDTH) / 2;

        int top =
                (this.height - WINDOW_HEIGHT) / 2;

        // Back button
        Button backButton = Button.builder(
                Component.literal("Back"),

                button -> {

                    this.minecraft.gui.setScreen(
                            new ComputerScreen(
                                    Component.literal(
                                            "Corporate Computer"
                                    )
                            )
                    );

                }

        ).bounds(
                left + 10,
                top + 10,
                60,
                20
        ).build();

        this.addRenderableWidget(backButton);

        // Starting position for mails
        int y = top + 60;

        // Get all mails
        for (Mail mail : Mailbox.getMails()) {

            String displayText;

            if (mail.isRead()) {
                displayText =
                        mail.getSubject();
            } else {
                displayText =
                        "[NEW] " + mail.getSubject();
            }

            Button mailButton = Button.builder(
                    Component.literal(displayText),

                    button -> {

                        this.minecraft.gui.setScreen(
                                new MailScreen(mail)
                        );

                    }

            ).bounds(
                    left + 20,
                    y,
                    WINDOW_WIDTH - 40,
                    20
            ).build();

            this.addRenderableWidget(mailButton);

            y += 30;

            // Prevent buttons from leaving the window.
            if (y > top + WINDOW_HEIGHT - 30) {
                break;
            }
        }
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

        // Background
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
                top + 40,
                0xFF303030
        );

        // Title
        graphics.text(
                this.font,
                "INBOX",
                left + 80,
                top + 16,
                0xFFFFFFFF,
                true
        );

        graphics.text(
                this.font,
                "Your current reputation: " +
                        ReputationManager.getReputation(),
                left + 150,
                top + 16,
                0XFFAAAAAA,
                false


        );

        // Unread count
        graphics.text(
                this.font,
                Mailbox.getUnreadCount()
                        + " unread",
                left + WINDOW_WIDTH - 100,
                top + 16,
                0xFFAAAAAA,
                false
        );

        // Render buttons.
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
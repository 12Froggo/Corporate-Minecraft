package froggo.corporateminecraft.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class MailScreen extends Screen {

    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 300;

    private final Mail mail;

    public MailScreen(Mail mail) {

        super(
                Component.literal(
                        mail.getSubject()
                )
        );

        this.mail = mail;

        // Opening a mail marks it as read.
        mail.markAsRead();
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
                            new InboxScreen(
                                    Component.literal("Inbox")
                            )
                    );

                }

        ).bounds(
                left + 10,
                top + 10,
                60,
                20
        ).build();

        int bodyWidth = WINDOW_WIDTH - 40;

        List<FormattedCharSequence> lines =
                this.font.split(
                        Component.literal(mail.getBody()),
                        bodyWidth
                );

        int bodyHeight = lines.size() * 12;


        Button answerGood = Button.builder(
                Component.literal("Good"),

                button -> {

                    ReputationManager.changeReputation(5);

                    Mailbox.removeMail(mail);

                    this.minecraft.gui.setScreen(
                            new InboxScreen(
                                    Component.literal("Inbox")
                            )
                    );
                }

        ).bounds(
                left + 10,
                top + 110 + bodyHeight + 10,
                60,
                20
        ).build();

        Button answerBad = Button.builder(
                Component.literal("Bad"),

                button -> {

                    ReputationManager.changeReputation(-5);

                    Mailbox.removeMail(mail);

                    this.minecraft.gui.setScreen(
                            new InboxScreen(
                                    Component.literal("Inbox")
                            )
                    );
                }
        ).bounds(
                left + 100,
                top + 110 + bodyHeight + 10,
                60,
                20
        ).build();

        this.addRenderableWidget(backButton);
        this.addRenderableWidget(answerGood);
        this.addRenderableWidget(answerBad);
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
                top + 55,
                0xFF303030
        );

        // Subject
        graphics.text(
                this.font,
                mail.getSubject(),
                left + 85,
                top + 15,
                0xFFFFFFFF,
                true
        );

        // Sender
        graphics.text(
                this.font,
                "From: " + mail.getSender(),
                left + 20,
                top + 75,
                0xFFAAAAAA,
                false
        );

        // Subject again
        graphics.text(
                this.font,
                "Subject: " + mail.getSubject(),
                left + 20,
                top + 100,
                0xFFFFFFFF,
                true
        );

        // Body
        int bodyX = left + 20;
        int bodyY = top + 110;

        int bodyWidth = WINDOW_WIDTH - 40;

        List<FormattedCharSequence>  lines =
                this.font.split(
                        Component.literal(mail.getBody()),
                        bodyWidth
                );

        for (int i = 0; i < lines.size(); i++) {

            graphics.text(
                    this.font,
                    lines.get(i),
                    bodyX,
                    bodyY + (i * 12),
                    0xFFFFFFFF
            );
        }

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
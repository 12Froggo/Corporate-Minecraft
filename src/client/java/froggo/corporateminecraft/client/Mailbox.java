package froggo.corporateminecraft.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mailbox {

    private static final List<Mail> MAILS = new ArrayList<>();

    private static final Random RANDOM = new Random();

    private static final int MIN_MAIL_DELAY = 10 * 20;
    private static final int MAX_MAIL_DELAY = 20 * 20;

    private static int mailTimer;

    public static void initialize() {
        resetMailTimer();
    }

    private static void resetMailTimer() {

        mailTimer = MIN_MAIL_DELAY
                + RANDOM.nextInt(
                MAX_MAIL_DELAY - MIN_MAIL_DELAY + 1
        );
    }

    public static void tick() {

        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) {
            return;
        }

        mailTimer--;

        if (mailTimer <= 0) {

            Mail newMail = MailGenerator.generateMail();

            addMail(newMail);

            minecraft.gui.hud.setOverlayMessage(
                    Component.literal(
                            "New email from " + newMail.getSender()
                    ),
                    false
            );

            minecraft.player.playSound(
                    SoundEvents.NOTE_BLOCK_PLING.value(),
                    1.0F,
                    1.0F
            );

            resetMailTimer();
        }
    }

    public static List<Mail> getMails() {
        return MAILS;
    }

    public static void addMail(Mail mail) {
        MAILS.add(mail);
    }

    public static void removeMail(Mail mail) {
        MAILS.remove(mail);
    }

    public static int getUnreadCount() {

        int count = 0;

        for (Mail mail : MAILS) {

            if (!mail.isRead()) {
                count++;
            }
        }

        return count;
    }
}
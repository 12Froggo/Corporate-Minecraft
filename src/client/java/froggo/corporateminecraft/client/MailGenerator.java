package froggo.corporateminecraft.client;

import java.util.Random;

public class MailGenerator {

    private static final Random RANDOM = new Random();

    private static final String[] SENDERS = {
            "Michael - HR",
            "Sarah - Accounting",
            "David - Management",
            "Jennifer - IT",
            "Robert - CEO",
            "Corporate Security",
            "Office Administration"
    };

    private static final String[] SUBJECTS = {
            "Meeting tomorrow",
            "Important company notice",
            "Your performance review",
            "Please review this document",
            "IT maintenance",
            "Office announcement",
            "Action required",
            "Your weekly report"
    };

    private static final String[] BODIES = {
            "Please attend the meeting tomorrow morning. Your attendance is required.",
            "This is an important announcement from management. Please make sure you read the updated company policy.",
            "Your performance review has been scheduled. Please come to the HR office when you have time.",
            "Please review the attached information and respond when you have finished reading it.",
            "The company's computer systems will undergo maintenance tonight. Some services may be unavailable.",
            "There has been an important change to the office schedule.",
            "We need you to respond to this email as soon as possible.",
            "Your weekly work report is ready. Please review it and respond with any comments."
    };

    public static Mail generateMail() {

        String sender = SENDERS[RANDOM.nextInt(SENDERS.length)];
        String subject = SUBJECTS[RANDOM.nextInt(SUBJECTS.length)];
            String body = BODIES[RANDOM.nextInt(BODIES.length)];

        return new Mail(sender, subject, body);
    }
}
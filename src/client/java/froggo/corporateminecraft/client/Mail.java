package froggo.corporateminecraft.client;

public class Mail {

    private final String sender;
    private final String subject;
    private final String body;

    private boolean read;
    private boolean answered;

    public Mail(String sender, String subject, String body) {
        this.sender = sender;
        this.subject = subject;
        this.body = body;
        this.read = false;
        this.answered = false;
    }

    public String getSender() {
        return sender;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public boolean isRead() {
        return read;
    }

    public void markAsRead() {
        this.read = true;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void markAsAnswered() {
        this.answered = true;
    }
}
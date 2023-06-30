package Functional;

public class Message {
    private final String text;
    private Receiver receiver;
    private Sender sender;

    public Message(String text) {
        this.text = text;
    }


    public void setSender(Sender sender){
        this.sender = sender;
    }
    public Sender getSender() {
        return sender;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public Receiver getReceiver(){
        return this.receiver;
    }

    public String getText(){
        return this.text;
    }
}
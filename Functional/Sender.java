package Functional;

import Functional.Message;
import Functional.Receiver;
import Functional.ReceiversManager;

import java.util.List;
import java.util.Random;
public class Sender implements Runnable {
    private int frequency;
    private final Message message;
    private final String number;
    private final ReceiversManager rm;
    private int counter = 0;

    private boolean active;
    private boolean stopped;

    private final Random random = new Random();

    public Sender(String number, Message message, int frequency, ReceiversManager rm) {
        this.number = number;
        this.frequency = frequency;
        this.message = message;
        this.active = true;
        this.stopped = false;

        this.message.setSender(this);

        this.rm = rm;
    }

    public void setFrequency(int newFrequency) {
        frequency = newFrequency;
    }

    @Override
    public void run() {
        while (!stopped) {
            try {
                synchronized (this){
                    while (!active){
                        wait();
                    }
                }
                List<Receiver> receiverList = rm.getReceivers();
                if (receiverList.size() != 0) {
                    this.message.setReceiver(receiverList.get(random.nextInt(receiverList.size())));
                    sendMessage(this.message);
                }
                try {
                    Thread.sleep(6000 - frequency);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(Message message){
        System.out.println("Functional.Message: " + message.getText() + " from number: " + message.getSender().getNumber() +
                " was sent to number of receiver: " + message.getReceiver().getNumber());
        rm.getLeftBTS().receiveMessage(message);
        counter++;
        System.out.println(counter);
    }

    public Message getMessage() {
        return message;
    }

    public String getNumber() {
        return this.number;
    }

    public int getSentMessages(){
        return this.counter;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
        if (active){
            notify();
        }
    }
    public void stop(){
        this.stopped = true;
    }
}


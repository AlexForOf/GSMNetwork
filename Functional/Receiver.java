package Functional;

import Functional.Message;

import java.util.ArrayList;
import java.util.List;

public class Receiver implements Runnable{
    private final String number;
    private final List<Message> messages;
    private boolean stopped;
    private boolean deleteMessages;
    private int counter;

    private ReceiverNotifier rn;

    public Receiver(String number) {
        this.number = number;
        this.messages = new ArrayList<>();
    }

    private void updateInfoMessages(String updatedInfo){
        if (rn != null){
            rn.onUpdateMessages(updatedInfo);
        }
    }

    public void setUpdateNotifier(ReceiverNotifier rn) {
        this.rn = rn;
    }

    public String getNumber(){
        return this.number;
    }

    public void receiveMessage(Message message){
        messages.add(message);
        System.out.println("Functional.Message: " + message + " has been received on number: " + this.number + " from contact: " +
                message.getSender());
        updateInfoMessages(Integer.toString(messages.size()));
    }

    @Override
    public void run() {
        while (!stopped){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (deleteMessages){
                counter++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (counter == 10){
                    deleteMessages();
                }
            }
        }
    }
    private void deleteMessages(){
        messages.clear();
        counter = 0;
        updateInfoMessages(Integer.toString(0));
    }


    public void stop(){
        stopped = true;
    }

    public void setDeleteMessages(boolean option){
        deleteMessages = option;
        counter = 0;
    }
}

package Functional;

import java.util.ArrayList;
import java.util.List;

public abstract class Tower implements Runnable{
    protected final List<Message> messages;
    protected int position;
    protected final LayersManager lm;
    protected final ReceiversManager rm;
    public int type;
    protected boolean stopped;

    protected UpdateNotifier un;

    protected Tower(int position, LayersManager lm, ReceiversManager rm) {
        this.rm = rm;
        this.messages = new ArrayList<>();
        this.position = position;
        this.lm = lm;
        this.type = 0;
        this.stopped = false;
    }

    public void setUpdateNotifier(UpdateNotifier un) {
        this.un = un;
    }

    protected void updateInfoMessages(String updatedInfo){
        if (un != null){
            un.onUpdateMessages(updatedInfo);
        }
    }

    protected void updateRedirection(String text, Tower tower, int position, LayersManager lm){
        if (this.un != null){
            un.onUpdateRedirection(text, tower, position, lm);
        }
    }


    protected void receiveMessage(Message message){
        System.out.println(message.getReceiver().getNumber() + " receiver of the message: " + message.getText() + " from: "
                + message.getSender());
        messages.add(message);
        updateInfoMessages(Integer.toString(messages.size()));
        System.out.println(lm.getAllLayers());
    }

    protected void sendForward(Message message){
        if (position + 1 == lm.getAllLayers().size()){
            System.out.println("Sending message forward to: " + message.getReceiver());
            try {
                rm.getReceivers().get(rm.getReceivers().indexOf(message.getReceiver())).receiveMessage(message);
            }catch (IndexOutOfBoundsException e){
                System.out.println("Functional.Receiver no longer exists, nevertheless, this is the message: ");
                System.out.println(message.getText());
            }
        }else{
            int smsNow = lm.getNextLayer(position).get(0).messages.size();
            int indexWithLeastSMS = 0;
            for (Tower tower : lm.getNextLayer(position)) {
                if (smsNow > tower.messages.size()){
                    indexWithLeastSMS = lm.getNextLayer(position).indexOf(tower);
                }
            }
            System.out.println("Next layer: " + lm.getNextLayer(position));
            Tower next = lm.getNextLayer(position).get(indexWithLeastSMS);
            System.out.println("Sending message forward to: " + next);
            next.receiveMessage(message);
        }
        updateInfoMessages(Integer.toString(messages.size()));
    }

    protected void redirectMessage(Message message, Tower tower){
        int index = lm.getNextLayer(position-1).indexOf(this);
        tower.receiveMessage(message);
        updateInfoMessages(Integer.toString(messages.size()));
        System.out.println("Functional.Tower ran out of capacity, redirecting message to: " + lm.getNextLayer(position-1).get(index+1));
    }

    public void stop(){
        this.stopped = true;
    }
}

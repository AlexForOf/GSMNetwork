package Functional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Functional implements SendersManager, ReceiversManager, LayersManager {
    private final List<Sender> senders;
    private final List<Receiver> receivers;
    private final List<List<Tower>> connectors;
    private final PDUEncoder pduEncoder = new PDUEncoder();


    public Functional() {
        senders = new ArrayList<>();
        connectors = new LinkedList<>();
        receivers = new ArrayList<>();
    }

    public void init(){
        List<Tower> firstLayer = new ArrayList<>();
        firstLayer.add(new BTS(0, this, this));
        List<Tower> secondLayer = new ArrayList<>();
        secondLayer.add(new BSC(1, this, this));
        List<Tower> thirdLayer = new ArrayList<>();
        thirdLayer.add(new BTS(2, this, this));
        connectors.add(firstLayer);
        connectors.add(secondLayer);
        connectors.add(thirdLayer);
    }

    public static void saveMessages(List<Sender> senders){
        PDUEncoder.writePduToFile(senders);
    }

    public static void programExit(){
        System.exit(0);
    }
    @Override
    public void addLayer(int position, ArrayList<Tower> newLayer) {
        connectors.add(position, newLayer);
    }

    @Override
    public void deleteLayer(int position) {
        connectors.remove(position);
    }

    @Override
    public void migratePositions() {
        for (int i = 2; i < connectors.size(); i++) {
            for (int j = 0; j < connectors.get(i).size(); j++) {
                connectors.get(i).get(j).position += 1;
            }
        }
    }

    @Override
    public List<List<Tower>> getAllLayers() {
        return connectors;
    }

    @Override
    public List<Tower> getNextLayer(int currentPosition) {
        return connectors.get(currentPosition+1);
    }

    @Override
    public void addReceiver(String number) {
        Receiver newReceiver = new Receiver(number);
        Thread thread = new Thread(newReceiver);
        thread.start();
        receivers.add(newReceiver);
    }

    @Override
    public void deleteReceiver(Receiver receiver) {
        receivers.remove(receiver);
    }

    @Override
    public List<Receiver> getReceivers() {
        return receivers;
    }

    @Override
    public Tower getLeftBTS() {
        return connectors.get(0).get(0);
    }

    @Override
    public void addSender(String number, String message, int frequency, ReceiversManager rm) {
        Message newMessage = new Message(pduEncoder.encodePdu(number, message));
        Sender sender = new Sender(number, newMessage, frequency, rm);
        Thread thread = new Thread(sender);
        thread.start();
        senders.add(sender);
    }

    @Override
    public void deleteSender(Sender sender) {
        senders.remove(sender);
    }

    @Override
    public List<Sender> getSenders() {
        return senders;
    }
}

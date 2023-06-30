package Functional;

import Functional.Receiver;

import java.util.List;

public interface ReceiversManager {
    void addReceiver(String number);
    void deleteReceiver(Receiver receiver);
    List<Receiver> getReceivers();
    Tower getLeftBTS();
}

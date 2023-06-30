package Functional;

import Functional.ReceiversManager;

import java.util.List;
public interface SendersManager {
    void addSender(String number, String message, int frequency, ReceiversManager rm);
    void deleteSender(Sender sender);
    List<Sender> getSenders();
}


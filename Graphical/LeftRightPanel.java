package Graphical;

import Functional.Receiver;
import Functional.ReceiversManager;
import Functional.Sender;
import Functional.SendersManager;

import javax.swing.*;
import java.awt.*;

public abstract class LeftRightPanel extends JPanel {
    protected final JPanel panelContainer;
    protected final SendersManager sm;
    protected final ReceiversManager rm;
    protected LeftRightPanel(SendersManager sm, ReceiversManager rm){
        this.sm = sm;
        this.rm = rm;

        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        panelContainer = new JPanel();
        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));
        scrollPane.setViewportView(panelContainer);
    }
    protected boolean isNumberCorrect(String phoneNumber){
        if (phoneNumber == null) return false;
        if (!phoneNumber.matches("\\d{9}")) return false;
        for (Sender senders : sm.getSenders()){
            if (phoneNumber.equals(senders.getNumber())) return false;
        }
        for (Receiver receivers : rm.getReceivers()){
            if (phoneNumber.equals(receivers.getNumber())) return false;
        }
        return true;
    }
}
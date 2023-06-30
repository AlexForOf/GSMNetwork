package Graphical;

import Functional.Receiver;
import Functional.ReceiverNotifier;
import Functional.ReceiversManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
public class ReceiverPanel extends JPanel implements ReceiverNotifier {
    Receiver receiver;
    private final JLabel messageCounter;
    private int receiverIndex;
    private final ReceiversManager rm;
    public ReceiverPanel(String receiverNumber, ReceiversManager rm) {
        this.receiver = new Receiver(receiverNumber);
        this.rm = rm;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, 75));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout());
        JTextField receiverNumberField = new JTextField("Number: " + receiverNumber);
        messageCounter = new JLabel("Messages received: 0");
        receiverNumberField.setEditable(false);
        upperPanel.add(receiverNumberField, BorderLayout.LINE_START);
        upperPanel.add(messageCounter, BorderLayout.LINE_END);
        contentPanel.add(upperPanel);

        JPanel checkPanel = new JPanel();
        checkPanel.setLayout(new BorderLayout());

        JCheckBox checkBox = new JCheckBox();
        checkBox.addItemListener(e -> rm.getReceivers().get(receiverIndex).setDeleteMessages(e.getStateChange() == ItemEvent.SELECTED));

        JLabel checkLabel = new JLabel("Clear all messages each 10 seconds: ");
        checkPanel.add(checkBox, BorderLayout.LINE_END);
        checkPanel.add(checkLabel, BorderLayout.LINE_START);
        contentPanel.add(checkPanel);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            rm.getReceivers().get(receiverIndex).stop();
            Container parent = getParent();
            if (parent != null) {
                parent.remove(ReceiverPanel.this);
                System.out.println(rm.getReceivers());
                parent.revalidate();
                parent.repaint();
            }
        });

        rm.addReceiver(receiverNumber);
        receiverIndex = rm.getReceivers().size() - 1;
        registerNotifier();
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(deleteButton);

        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void registerNotifier(){
        rm.getReceivers().get(receiverIndex).setUpdateNotifier(this);
    }

    @Override
    public void onUpdateMessages(String updatedData) {
        SwingUtilities.invokeLater(() -> messageCounter.setText("Messages received: " + updatedData));
    }
}
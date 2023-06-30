package Graphical;

import Functional.ReceiversManager;
import Functional.SendersManager;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
public class PhoneNumberPanel extends JPanel {
    private final String phoneNumber;
    private boolean active;
    private int frequency;
    private int senderIndex;

    private final SendersManager sm;
    private final ReceiversManager rm;

    public PhoneNumberPanel(String phoneNumber, String message, SendersManager sm, ReceiversManager rm) {
        this.phoneNumber = phoneNumber;
        this.sm = sm;
        this.rm = rm;

        System.out.println(sm.getSenders());
        active = true;
        frequency = 3000;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JTextField phoneNumberField = new JTextField("Number: " + phoneNumber);
        phoneNumberField.setEditable(false);
        contentPanel.add(phoneNumberField);

        JTextField smsField = new JTextField("Functional.Message: " + message);
        smsField.setEditable(false);
        contentPanel.add(smsField);

        JSlider frequencySlider = new JSlider(1, 5, 3);

        frequencySlider.setPaintTicks(true);
        frequencySlider.setPaintLabels(true);
        frequencySlider.setMajorTickSpacing(1);
        frequencySlider.setSnapToTicks(true);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(frequencySlider.getMinimum(), new JLabel(
                formulateSliderLabel(frequencySlider.getMinimum()))
        );
        labelTable.put(frequencySlider.getMaximum(), new JLabel(
                formulateSliderLabel(frequencySlider.getMaximum()))
        );
        labelTable.put(frequencySlider.getValue(), new JLabel(
                formulateSliderLabel(frequencySlider.getValue()))
        );

        frequencySlider.setLabelTable(labelTable);
        frequencySlider.addChangeListener(e -> {
            if (!frequencySlider.getValueIsAdjusting()) {
                frequency = frequencySlider.getValue() * 1000;
                if (sm.getSenders().get(senderIndex) != null) {
                    sm.getSenders().get(senderIndex).setFrequency(frequency);
                }
            }
        });
        contentPanel.add(frequencySlider);

        JComboBox<String> activityComboBox = new JComboBox<>(new String[]{"Active", "Disabled"});
        activityComboBox.addActionListener(e -> {
            String selectedOption = (String) activityComboBox.getSelectedItem();
            if (selectedOption != null){
                active = selectedOption.equals("Active");
            }
            if (sm.getSenders().get(senderIndex) != null) {
                sm.getSenders().get(senderIndex).setActive(active);
            }
        });
        contentPanel.add(activityComboBox);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            stopSendingMessages();
            Container parent = getParent();
            if (parent != null) {
                parent.remove(PhoneNumberPanel.this);
                parent.revalidate();
                parent.repaint();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(deleteButton);

        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void startSendingMessages(String message) {
        sm.addSender(phoneNumber, message, frequency, rm);
        senderIndex = sm.getSenders().size() - 1;
        System.out.println(sm.getSenders());
    }

    public void stopSendingMessages() {
        if (sm.getSenders().get(senderIndex) != null) {
            sm.getSenders().get(senderIndex).stop();
            System.out.println(sm.getSenders());
        }
    }

    private String formulateSliderLabel(int labelValue){
        return ((double)labelValue)/5 + "sms/sec";
    }
}
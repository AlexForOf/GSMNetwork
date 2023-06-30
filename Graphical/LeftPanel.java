package Graphical;

import Functional.ReceiversManager;
import Functional.SendersManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class LeftPanel extends LeftRightPanel {
    public LeftPanel(SendersManager sm, ReceiversManager rm) {
        super(sm, rm);
        panelContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));


        JButton addButton = new JButton("Add Phone Number");
        addButton.addActionListener(new AddPhoneNumberActionListener());
        add(addButton, BorderLayout.SOUTH);

    }

    private class AddPhoneNumberActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String phoneNumber = JOptionPane.showInputDialog("Enter phone number(9 digits without spaces):");
            if (isNumberCorrect(phoneNumber)){
                String message = JOptionPane.showInputDialog("Enter message(from 1 to 25 digits):");
                if (message.length() > 0 && message.length() < 25) {
                    PhoneNumberPanel phoneNumberPanel = new PhoneNumberPanel(phoneNumber, message, sm, rm);
                    panelContainer.add(phoneNumberPanel);
                    panelContainer.revalidate();
                    panelContainer.repaint();

                    phoneNumberPanel.setPreferredSize(new Dimension(panelContainer.getWidth()-25, 175));
                    phoneNumberPanel.setMaximumSize(new Dimension(panelContainer.getWidth()-25, 175));

                    JScrollPane scrollPane = (JScrollPane) panelContainer.getParent().getParent();
                    SwingUtilities.invokeLater(() -> {
                        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
                        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
                    });

                    phoneNumberPanel.startSendingMessages(message);
                }else{
                    JOptionPane.showMessageDialog(null, "Invalid SMS!");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Invalid phone number, either incorrect format or this number already exists!");
            }
        }
    }
}

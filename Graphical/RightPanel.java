package Graphical;

import Functional.ReceiversManager;
import Functional.SendersManager;
import Graphical.LeftRightPanel;
import Graphical.ReceiverPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RightPanel extends LeftRightPanel {

    public RightPanel(SendersManager sm, ReceiversManager rm) {
        super(sm, rm);

        panelContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JButton addButton = new JButton("Add Functional.Receiver");
        addButton.addActionListener(new AddReceiverActionListener());
        add(addButton, BorderLayout.SOUTH);
    }

    private class AddReceiverActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String receiverNumber = JOptionPane.showInputDialog("Enter receiver number:(9 digits without spaces)");
            if (isNumberCorrect(receiverNumber)) {
                ReceiverPanel receiverPanel = new ReceiverPanel(receiverNumber, rm);
                panelContainer.add(receiverPanel);
                panelContainer.revalidate();
                panelContainer.repaint();

                receiverPanel.setPreferredSize(new Dimension(panelContainer.getWidth()-25, 100));
                receiverPanel.setMaximumSize(new Dimension(panelContainer.getWidth()-25, 100));

                JScrollPane scrollPane = (JScrollPane) panelContainer.getParent().getParent();
                SwingUtilities.invokeLater(() -> {
                    JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
                    verticalScrollBar.setValue(verticalScrollBar.getMaximum());
                });
            } else {
                JOptionPane.showMessageDialog(null, "Invalid phone number, either incorrect format or this number already exists!");
            }
        }
    }
}

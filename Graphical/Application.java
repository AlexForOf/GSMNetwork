package Graphical;

import Functional.Functional;
import Functional.LayersManager;
import Functional.ReceiversManager;
import Functional.SendersManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class Application {
    private ReceiversManager rm;
    private SendersManager sm;
    private LayersManager lm;

    public void run(ReceiversManager rm, SendersManager sm, LayersManager lm){
        this.sm = sm;
        this.rm = rm;
        this.lm = lm;

        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("GSM Network");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Exit, saving all the information in the file: messages.bin");
                Functional.saveMessages(sm.getSenders());
                Functional.programExit();
            }
        });
        frame.setPreferredSize(new Dimension(1280, 720));

        JPanel mainPanel = new JPanel(new BorderLayout());

        LeftPanel leftPanel = new LeftPanel(this.sm, this.rm);
        RightPanel rightPanel = new RightPanel(this.sm, this.rm);
        CentralPanel centralPanel = new CentralPanel(this.lm, this.rm);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(centralPanel, BorderLayout.CENTER);

        int windowWidth = frame.getPreferredSize().width;
        int leftRightWidth = (int) (windowWidth * 0.275);

        leftPanel.setPreferredSize(new Dimension(leftRightWidth, 0));
        rightPanel.setPreferredSize(new Dimension(leftRightWidth, 0));

        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }
}






package Graphical;

import Functional.LayersManager;
import Functional.Tower;
import Functional.UpdateNotifier;
import Graphical.LayerPanel;

import javax.swing.*;
import java.awt.*;
public class TowerGraphical implements UpdateNotifier {
    private final JLabel counter;
    public final JPanel mainPanel;
    public JButton terminateButton;
    private final Tower thisTower;

    public TowerGraphical(String text, Tower thisTower, int position, LayersManager lm){
        this.thisTower = thisTower;
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BorderLayout());
        labelPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        JPanel counterPanel = new JPanel();
        counterPanel.setLayout(new BorderLayout());
        counter = new JLabel("Messages: 0");
        counter.setHorizontalAlignment(SwingConstants.CENTER);
        counter.setVerticalAlignment(SwingConstants.CENTER);
        counterPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        counterPanel.add(counter, BorderLayout.CENTER);

        JPanel terminatePanel = new JPanel();
        terminatePanel.setLayout(new BorderLayout());
        terminatePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        terminateButton = new JButton("Terminate last");
        terminatePanel.add(terminateButton, BorderLayout.CENTER);
        terminatePanel.setBackground(Color.BLUE);
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        labelPanel.add(label, BorderLayout.CENTER);
        labelPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        mainPanel.add(labelPanel);
        mainPanel.add(counterPanel);
        mainPanel.add(terminatePanel);
        registerNotifier();
        Thread thread = new Thread(thisTower);
        thread.start();

    }

    public void registerNotifier(){
        thisTower.setUpdateNotifier(this);
    }
    @Override
    public void onUpdateMessages(String updatedData) {
        SwingUtilities.invokeLater(() -> counter.setText("Messages: " + updatedData));
    }

    @Override
    public void onUpdateRedirection(String text, Tower tower, int position, LayersManager lm) {
        LayerPanel layerPanel = (LayerPanel) (mainPanel.getParent().getParent().getParent().getParent());
        layerPanel.addNewTower(text, tower, position, lm);
    }
}
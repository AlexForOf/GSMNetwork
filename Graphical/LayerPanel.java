package Graphical;

import Functional.LayersManager;
import Functional.Tower;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class LayerPanel extends JPanel {
    public List<JPanel> towers;
    private final JPanel contentPanel;
    JScrollPane scrollPane;
    public LayerPanel(String text, Tower tower, int position, LayersManager lm) {
        towers = new ArrayList<>();

        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


        this.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        this.setLayout(new BorderLayout());
        contentPanel = new JPanel();
        contentPanel.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        addNewTower(text, tower, position, lm);
    }
    public void addNewTower(String text, Tower tower, int position, LayersManager lm){
        for (int i = 0; i < 1; i++) {
            TowerGraphical newTower = new TowerGraphical(text, tower, position, lm);
            JPanel towerPanel = newTower.mainPanel;
            newTower.terminateButton.addActionListener(e -> {
                if (towers.size() > 1){
                    Object button = e.getSource();
                    JButton thisButton = (JButton) button;
                    JPanel newPanel = (JPanel) thisButton.getParent().getParent();
                    JPanel somePanel = (JPanel) newPanel.getParent();
                    tower.stop();
                    towers.remove(newPanel);
                    somePanel.remove(newPanel);
                    somePanel.revalidate();
                    somePanel.repaint();
                }
            });
            towers.add(towerPanel);
            contentPanel.add(towerPanel);
        }
        scrollPane.setViewportView(contentPanel);
        this.add(scrollPane);
    }
}

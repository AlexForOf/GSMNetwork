package Graphical;

import Functional.BSC;
import Functional.LayersManager;
import Functional.ReceiversManager;
import Functional.Tower;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class CentralPanel extends JPanel {

    private final LayersManager lm;
    private final ReceiversManager rm;

    private final TopPanel topPanel;
    private final List<LayerPanel> layers;
    public CentralPanel(LayersManager lm, ReceiversManager rm){
        this.lm = lm;
        this.rm = rm;
        layers = new ArrayList<>();

        this.setLayout(new BorderLayout());

        topPanel = new TopPanel();

        JPanel bottomPanel = new JPanel();
        JPanel addPanel = new JPanel();
        JPanel deletePanel = new JPanel();
        addPanel.setLayout(new BorderLayout());
        deletePanel.setLayout(new BorderLayout());
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
        JButton addButton = new JButton("Add Layer");
        addPanel.add(addButton, BorderLayout.CENTER);
        JButton deleteButton = new JButton("Delete Layer");
        deletePanel.add(deleteButton, BorderLayout.CENTER);

        bottomPanel.add(addPanel);
        bottomPanel.add(deletePanel);
        addButton.addActionListener(e -> {
            ArrayList<Tower> newLayer = new ArrayList<>();
            BSC newBSC = new BSC(2, lm, rm);
            lm.migratePositions();
            newLayer.add(newBSC);
            int newPosition = lm.getAllLayers().size()/2 == 1 ? 2 : lm.getAllLayers().size()/2;
            lm.addLayer(newPosition, newLayer);
            System.out.println(lm.getAllLayers());
            System.out.println(newPosition);

            topPanel.createLayer("Functional.Functional.BSC", newBSC, newPosition);
            topPanel.revalidate();
            topPanel.repaint();
        });
        deleteButton.addActionListener(e -> {
            if (lm.getAllLayers().size() > 3){
                int newPosition = lm.getAllLayers().size()/2 == 1 ? 2 : lm.getAllLayers().size()/2;
                lm.deleteLayer(newPosition);
                System.out.println(lm.getAllLayers());
                topPanel.contentPanel.remove(2);
                topPanel.revalidate();
                topPanel.repaint();
            }
        });

        System.out.println(lm.getAllLayers());
        this.setBackground(Color.BLUE);
        this.add(topPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }
    private class TopPanel extends JPanel{
        public JPanel contentPanel;
        private TopPanel(){
            this.setLayout(new BorderLayout());
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            this.add(scrollPane, BorderLayout.CENTER);
            contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
            scrollPane.setViewportView(contentPanel);

            for (List<Tower> list: lm.getAllLayers()) {
                String text = list.get(0).type == 1 ? "Functional.Functional.BTS" : "Functional.Functional.BSC";
                createLayer(text, list.get(0), lm.getAllLayers().indexOf(list));
            }
        }
        public void createLayer(String text, Tower tower, int position){
            LayerPanel layerPanel = new LayerPanel(text, tower, position, lm);
            contentPanel.add(layerPanel, position);
            layers.add(layerPanel);
            System.out.println(layers);
        }
    }
}

package Functional;

import java.util.ArrayList;
import java.util.List;

public interface LayersManager {
    void addLayer(int position, ArrayList<Tower> newLayer);
    void deleteLayer(int position);
    void migratePositions();
    List<List<Tower>> getAllLayers();
    List<Tower> getNextLayer(int currentPosition);
}

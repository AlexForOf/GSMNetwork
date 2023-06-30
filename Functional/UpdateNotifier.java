package Functional;

import Functional.LayersManager;

public interface UpdateNotifier {
    void onUpdateMessages(String updatedData);
    void onUpdateRedirection(String text, Tower tower, int position, LayersManager lm);
}

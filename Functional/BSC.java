package Functional;

import java.util.Random;

public class BSC extends Tower {

    private int count = 0;
    private final Random random = new Random();
    public UpdateNotifier un = null;

    public BSC(int position, LayersManager lm, ReceiversManager rm) {
        super(position, lm, rm);
        super.type = 2;
    }


    @Override
    public void run() {
        System.out.println(this);
        int delay = random.nextInt(11) + 5;
        int timeWaited = 0;
        while (!stopped){
            if (this.messages.size() >= 5){
                boolean isAvailableTower = false;
                for (int i = 0; i < lm.getNextLayer(position-1).size(); i++) {
                    if (lm.getNextLayer(position-1).get(i).messages.size() < 5){
                        redirectMessage(messages.get(0), lm.getNextLayer(position-1).get(i));
                        isAvailableTower = true;
                        break;
                    }
                }
                if (!isAvailableTower){
                    BSC newBSC = new BSC(position, lm, rm);
                    lm.getNextLayer(position-1).add(newBSC);
                    updateRedirection("Functional.Functional.BTS", newBSC, position, lm);
                    redirectMessage(messages.get(0), newBSC);
                }
                messages.remove(0);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (this.messages.size() != 0){
                delay = 15 - (delay - timeWaited);
                count++;
                if (count >= delay){
                    sendForward(messages.get(0));
                    timeWaited += delay;
                    messages.remove(0);
                    count = 0;
                    delay = random.nextInt(11) + 5;
                    updateInfoMessages(Integer.toString(messages.size()));
                }
            }
        }
    }
}

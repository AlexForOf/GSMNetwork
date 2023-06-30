package Functional;

public class BTS extends Tower {
    private int count;

    protected BTS(int position, LayersManager lm, ReceiversManager rm) {
        super(position, lm, rm);
        super.type = 1;
    }

    @Override
    public void run() {
        System.out.println(this);
        int timeWaited = 0;
        int delay = 3;
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
                    BTS newBTS = new BTS(position, lm, rm);
                    lm.getNextLayer(position-1).add(newBTS);
                    updateRedirection("Functional.Functional.BTS", newBTS, position, lm);
                    redirectMessage(messages.get(0), newBTS);
                }
                messages.remove(0);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (this.messages.size() != 0){
                count++;
                if (count == 3){
                    sendForward(messages.get(0));
                    timeWaited += delay;
                    messages.remove(0);
                    count = 0;
                    updateInfoMessages(Integer.toString(messages.size()));
                }
            }
        }
    }
}

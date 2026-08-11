package mancala.domain;
import java.util.concurrent.ThreadLocalRandom;

class Beurt {
    private int beurt;

    public Beurt() {
//        beurt = 1;
        this.beurt = ThreadLocalRandom.current().nextInt(1, 3);
    }

    public Beurt(int beginSpeler) {
        beurt = beginSpeler;
    }

    void switchBeurt() {
        if (beurt == 1) {
            beurt = 2;
        } else {
            beurt = 1;
        }
    }

    public int getBeurt() {
        return beurt;
    }
}

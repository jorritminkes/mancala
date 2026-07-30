package mancala.domain;

public class Beurt {
    protected int beurt;

    public Beurt() {
        beurt = 1;
    }

    protected void switchBeurt() {
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

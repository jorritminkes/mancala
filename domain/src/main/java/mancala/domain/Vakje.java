package mancala.domain;

public abstract class Vakje {
    protected int pocketNumber;
    protected int aantalStenen;
    protected Vakje volgendVakje;

    public abstract void zet();

    public int getAantalStenen() {
        return aantalStenen;
    }

    protected void setAantalStenen(int aantalStenen) {
        this.aantalStenen = aantalStenen;
    }

    protected Vakje getVolgendVakje() {
        return volgendVakje;
    }

    public Vakje getVakjeOpPositie(int positie) {
        if (positie == 1) {
            return this;
        }
        return this.volgendVakje.getVakjeOpPositie(positie - 1);
    }

    protected void geefStenenDoor(int doorgegevenStenen) {
        if (doorgegevenStenen != 0) {
            volgendVakje.setAantalStenen(volgendVakje.getAantalStenen() + 1);
            volgendVakje.geefStenenDoor(doorgegevenStenen - 1);
        }
    }







}

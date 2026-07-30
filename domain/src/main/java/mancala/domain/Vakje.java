package mancala.domain;

public abstract class Vakje {
    protected int pocketNumber;
    protected int aantalStenen;
    protected Vakje volgendVakje;

    protected Vakje(int pocketNumber, Vakje eerste) {
        this.pocketNumber = pocketNumber;
        Vakje zichzelf = (eerste != null) ? eerste : this;

        if (pocketNumber < 14) {
            this.volgendVakje = maakVolgendVakje(zichzelf, pocketNumber + 1);
        } else {
            this.volgendVakje = zichzelf;
        }
    }

    private Vakje maakVolgendVakje(Vakje eerste, int pocketNumber) {
        switch (pocketNumber) {
            case 7:
            case 14:
                return new Mancala(eerste, pocketNumber);
            default:
                return new Pocket(eerste, pocketNumber);
        }
    }

    public abstract void zet();

    public int getPocketNumber () {
        return pocketNumber;
    }

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

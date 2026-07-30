package mancala.domain;


public class Pocket extends Vakje {
//    private int pocketNumber;
//    private int aantalStenen;
//    private Pocket volgendePocket;
//    private int totaalAantalPockets = 12;
//    private boolean heeftEenDeBeurt = true;

    public Pocket() {
        pocketNumber = 1;
        aantalStenen = 4;
        volgendVakje = new Pocket(this, 2);
    }

    private Pocket(Vakje eerste, int pocketNumber) {
        this.pocketNumber = pocketNumber;
        aantalStenen = 4;

        if (pocketNumber < 12) {
            this.volgendVakje = new Pocket(eerste, pocketNumber + 1);
        } else if (pocketNumber == 12) {
            this.volgendVakje = eerste;
        }
    }

    public void zet() {
//        Vakje volgendVakje = this.volgendVakje;

        if (this.aantalStenen == 0) {
            throw new IllegalArgumentException("Kan niet op een leeg vakje spelen");
        }
        geefStenenDoor(aantalStenen);
        this.aantalStenen = 0;
    }

    public int getPocketNumber() {
        return this.pocketNumber;
    }

//    public int getAantalStenen() {
//        return this.aantalStenen;
//    }

//    protected void setAantalStenen(int aantalStenen) {
//        this.aantalStenen = aantalStenen;
//    }

    public Vakje getVolgendePocket() {
        return volgendVakje;
    }

    public Pocket getPocketOpPositie(int positie) {
        if (positie == 1) {
            return this;
        }
        return this.volgendePocket.getPocketOpPositie(positie - 1);
    }


}

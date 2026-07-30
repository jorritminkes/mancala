package mancala.domain;


public class Pocket extends Vakje {

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

    @Override
    public void zet() {

        if (this.aantalStenen == 0) {
            throw new IllegalArgumentException("Kan niet op een leeg vakje spelen");
        }

        geefStenenDoor(aantalStenen);
        this.aantalStenen = 0;
    }

    public Pocket getVolgendePocket() {
        return (Pocket) getVolgendVakje();
    }

    public Pocket getPocketOpPositie(int positie) {
        return (Pocket) getVakjeOpPositie(positie);
    }


}

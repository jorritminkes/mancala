package mancala.domain;


public class Pocket extends Vakje {

    public Pocket() {
        this(null, 1, 1);
    }

    Pocket(Vakje eerste, int pocketNumber, int owner) {
        super(pocketNumber, eerste);
        aantalStenen = 4;
        this.owner = owner;

    }

    @Override
    public void zet() {

        if (aantalStenen == 0) {
            throw new IllegalArgumentException("Kan niet op een leeg vakje spelen");
        }

        int doorgegevenStenen = aantalStenen;
        aantalStenen = 0;
        volgendVakje.ontvangStenen(doorgegevenStenen);
    }

//    public Pocket getVolgendePocket() {
//        return (Pocket) getVolgendVakje();
//    }
//
//    public Pocket getPocketOpPositie(int positie) {
//        return (Pocket) getVakjeOpPositie(positie);
//    }

    public void ontvangStenen(int ontvangenStenen) {
        this.setAantalStenen(this.aantalStenen + 1);

        if (ontvangenStenen - 1 > 0) {
            this.volgendVakje.ontvangStenen(ontvangenStenen - 1);
        }

    }


}

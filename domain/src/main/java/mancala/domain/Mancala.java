package mancala.domain;

public class Mancala extends Vakje {
    Mancala(Vakje eerste, int pocketNumber, int owner) {
        super(pocketNumber, eerste);
        aantalStenen = 0;
        this.owner = owner;
    }

    @Override
    public void zet() {
        throw new UnsupportedOperationException("Mancala kan niet als zet gekozen worden!");
    }

//    public void ontvangStenen(int ontvangenStenen) {
//        if (owner == beurt) {
//            this.setAantalStenen(aantalStenen + 1);
//            this.volgendVakje.ontvangStenen(ontvangenStenen - 1);
//        } else {
//            this.volgendVakje.ontvangStenen(ontvangenStenen);
//        }
//
//    }

    public void ontvangStenen(int ontvangenStenen) {
        this.setAantalStenen(this.aantalStenen + 1);

        if (ontvangenStenen - 1 > 0) {
            this.volgendVakje.ontvangStenen(ontvangenStenen - 1);
        }

    }

}

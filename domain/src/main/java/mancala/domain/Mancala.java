package mancala.domain;

public class Mancala extends Vakje {
//    protected Mancala(Vakje eerste, int pocketNumber, int owner, Beurt beurt) {
//        super(pocketNumber, eerste, beurt);
//        aantalStenen = 0;
//        this.owner = owner;
//    }

    protected Mancala(Vakje eerste, int pocketNumber, int owner, Beurt beurt) {
        super(pocketNumber, eerste, beurt);
        this.aantalStenen = 0;
        this.owner = owner;

        if (pocketNumber < 14) {
            this.volgendVakje = new Pocket(this.eersteVakje, pocketNumber + 1, 2, beurt);
        } else {
            this.volgendVakje = this.eersteVakje;
        }
    }

    @Override
    public void zet() {
        throw new UnsupportedOperationException("Mancala kan niet als zet gekozen worden!");
    }

    public void ontvangStenen(int ontvangenStenen) {
        int huidigeSpeler = beurt.getBeurt();

        if (owner == huidigeSpeler) {
            this.setAantalStenen(aantalStenen + 1);
            mancalaGeeftStenenDoor(ontvangenStenen);

        } else {
            this.volgendVakje.ontvangStenen(ontvangenStenen);
        }

    }

    private void mancalaGeeftStenenDoor(int ontvangenStenen) {
        if (ontvangenStenen - 1 > 0) {
            this.volgendVakje.ontvangStenen(ontvangenStenen - 1);
        }
    }

}

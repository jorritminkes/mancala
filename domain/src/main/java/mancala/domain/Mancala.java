package mancala.domain;

public class Mancala extends Vakje {
    protected Mancala(Vakje eerste, int pocketNumber, int owner, Beurt beurt) {
        super(pocketNumber, eerste, beurt);
        aantalStenen = 0;
        this.owner = owner;
    }

    @Override
    public void zet() {
        throw new UnsupportedOperationException("Mancala kan niet als zet gekozen worden!");
    }

    public void ontvangStenen(int ontvangenStenen) {
        int huidigeSpeler = beurt.getBeurt();

        if (owner == huidigeSpeler) {
            this.setAantalStenen(aantalStenen + 1);
            this.volgendVakje.ontvangStenen(ontvangenStenen - 1);
        } else {
            this.volgendVakje.ontvangStenen(ontvangenStenen);
        }

    }

}

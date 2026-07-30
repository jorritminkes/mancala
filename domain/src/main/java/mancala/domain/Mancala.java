package mancala.domain;

public class Mancala extends Vakje {
    Mancala(Vakje eerste, int pocketNumber) {
        super(pocketNumber, eerste);
        aantalStenen = 0;
    }

    @Override
    public void zet() {
        throw new UnsupportedOperationException("Mancala kan niet als zet gekozen worden!");
    }

}

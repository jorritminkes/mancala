package mancala.domain;

public class Mancala extends Vakje {

    Mancala(Vakje eerste, int pocketNumber, int owner, Beurt beurt) {
        super(pocketNumber, eerste, beurt);
        setAantalStenen(0);
        setOwner(owner);

        if (pocketNumber < totaalVakjes) {
            setVolgendVakje(new Pocket(this.getEersteVakje(), pocketNumber + 1, 2, beurt));
        } else {
            setVolgendVakje(getEersteVakje());
        }
    }

    void ontvangStenen(int ontvangenStenen) {
        int huidigeSpeler = getBeurt();

        if (getOwner() == huidigeSpeler) {
            voegAantalStenenToe(1);
            mancalaGeeftStenenDoor(ontvangenStenen);
        } else {
            getVolgendVakje().ontvangStenen(ontvangenStenen);
        }
    }

    private void mancalaGeeftStenenDoor(int ontvangenStenen) {
        if (ontvangenStenen > 1) {
            getVolgendVakje().ontvangStenen(ontvangenStenen - 1);
        }
    }


    @Override
    public boolean isSpelAfgelopen() {
        return zijnPocketsLeegVanSpeler(1) || zijnPocketsLeegVanSpeler(2);
    }

    private boolean zijnPocketsLeegVanSpeler(int speler) {
        int totaalStenenPerSpeler = getTotaalStenenInPocketsPerSpeler(speler);
        return totaalStenenPerSpeler == 0;
    }

    int getTotaalStenenInPocketsPerSpeler(int speler) {
        int mancalaOffset = (speler == 1) ? 0 : vakjesPerKant;
        int totaalStenen = 0;

        for (int positie = 1; positie <= pocketsPerKant; positie++) {
            totaalStenen += getVakjeOpPositie(mancalaOffset + positie).getAantalStenen();
        }

        return totaalStenen;
    }

}

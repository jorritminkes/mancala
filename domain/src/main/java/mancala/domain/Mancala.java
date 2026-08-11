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

    @Override
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
    int telStenenInPockets() {
        return 0;
    }

}

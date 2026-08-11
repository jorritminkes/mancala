package mancala.domain;

public class Mancala extends Vakje {

    Mancala(Vakje eerste, int pocketNumber, int owner, Beurt beurt, int[] opstelling) {
        super(pocketNumber, opstelling[pocketNumber - 1], eerste, beurt);
        setOwner(owner);

        int volgendNummer = pocketNumber + 1;

        if (pocketNumber < totaalVakjes) {
            int volgendOwner = (owner == 1) ? 2 : 1;
            setVolgendVakje(new Pocket(this.getEersteVakje(), volgendNummer, volgendOwner, beurt, opstelling));
        } else {
            setVolgendVakje(getEersteVakje());
        }
    }

    @Override
    void ontvangStenen(int ontvangenStenen) {
        int huidigeSpeler = getBeurt();
        int stenenOmDoorTeGeven = ontvangenStenen;

        if (getOwner() == huidigeSpeler) {
            voegAantalStenenToeAanVakje(1);
            stenenOmDoorTeGeven = ontvangenStenen - 1;
        }

        if (stenenOmDoorTeGeven > 0) {
            getVolgendVakje().ontvangStenen(stenenOmDoorTeGeven);
        }
    }

    @Override
    int telStenenInPockets() {
        return 0;
    }

}

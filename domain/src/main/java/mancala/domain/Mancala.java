package mancala.domain;

public class Mancala extends Vakje {

    Mancala(Vakje eerste, int pocketNumber, Speler eigenaar, int[] opstelling) {
        super(pocketNumber, opstelling[pocketNumber - 1], eerste, eigenaar);

        int volgendNummer = pocketNumber + 1;

        if (pocketNumber < totaalVakjes) {
            Speler volgendOwner = eigenaar.getTegenstander();
            setVolgendVakje(new Pocket(this.getEersteVakje(), volgendNummer, volgendOwner, opstelling));
        } else {
            setVolgendVakje(getEersteVakje());
        }
    }

    @Override
    void ontvangStenen(int ontvangenStenen) {
        int stenenOmDoorTeGeven = ontvangenStenen;

        if (getEigenaar().isAanZet()) {
            voegAantalStenenToeAanVakje(1);
            stenenOmDoorTeGeven = ontvangenStenen - 1;
        }

        if (stenenOmDoorTeGeven > 0) {
            getVolgendVakje().ontvangStenen(stenenOmDoorTeGeven);
        }
    }

    @Override
    void verzamelStenenNaarMancala(int stenenTeVerzamelen) {
        voegAantalStenenToeAanVakje(stenenTeVerzamelen);
    }



}

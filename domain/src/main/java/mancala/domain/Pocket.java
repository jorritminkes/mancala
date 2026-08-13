package mancala.domain;


public class Pocket extends Vakje {
    private static final int[] standaardOpstelling = {4,4,4,4,4,4,0,4,4,4,4,4,4,0};

    public Pocket() {
        this(null, 1, new Speler(), standaardOpstelling);
    }

    public Pocket(int beginSpeler) {
        this(null, 1, new Speler(beginSpeler), standaardOpstelling);
    }

    public Pocket(int beginSpeler, int[] testOpstelling) {
        this(null, 1, new Speler(beginSpeler), testOpstelling);
    }


    Pocket(Vakje eerste, int pocketNumber, Speler eigenaar, int[] opstelling) {
        super(pocketNumber, opstelling[pocketNumber - 1], eerste, eigenaar);

        int volgendNummer = pocketNumber + 1;

        if (volgendNummer % vakjesPerKant == 0) {
            setVolgendVakje(new Mancala(getEersteVakje(), volgendNummer, eigenaar, opstelling));
        } else {
            setVolgendVakje(new Pocket(getEersteVakje(), volgendNummer, eigenaar, opstelling));
        }
    }


    public void zet() {
        if (getAantalStenen() == 0) {
            throw new IllegalArgumentException("Kan niet op een leeg vakje spelen");
        }

        if (getEigenaar().isAanZet()) {
            leegVakjeEnGeefStenenDoor();
        } else {
            throw new IllegalArgumentException("Het is niet jouw beurt om deze pocket te spelen");
        }

        leegAllePocketsAlsSpelKlaarIs();
    }

    private void leegVakjeEnGeefStenenDoor() {
        int stenenOmDoorTeGeven = getAantalStenen();
        leegVakje();
        if (stenenOmDoorTeGeven >= 1) {
            getVolgendVakje().ontvangStenen(stenenOmDoorTeGeven);
        }
    }

    @Override
    void ontvangStenen(int stenenInHand) {

        voegAantalStenenToeAanVakje(1);

        int stenenOmDoorTeGeven = stenenInHand - 1;

        if (stenenOmDoorTeGeven == 0) {
            beeindigZet();
        }
        if (stenenOmDoorTeGeven > 0) {
            getVolgendVakje().ontvangStenen(stenenOmDoorTeGeven);
        }
    }

    private void leegAllePocketsAlsSpelKlaarIs() {
        if (isSpelAfgelopen()) {
            Speler speler1 = getEersteVakje().getEigenaar();
            Speler speler2 = speler1.getTegenstander();

            verzamelStenenVanSpeler(speler1);
            verzamelStenenVanSpeler(speler2);
        }
    }

    private void verzamelStenenVanSpeler(Speler speler) {
        getVakjeOpPositie(getStartPositie(speler)).verzamelStenenNaarMancala(0);
    }

    @Override
    void verzamelStenenNaarMancala(int stenenTeVerzamelen) {
        int stenenOmDoorTeGeven = stenenTeVerzamelen + getAantalStenen();
        leegVakje();
        getVolgendVakje().verzamelStenenNaarMancala(stenenOmDoorTeGeven);
    }

    private void beeindigZet() {
        veroverIndienGelandOpLeegVakje();
        switchBeurt();
    }

    private void veroverIndienGelandOpLeegVakje() {
        if (getAantalStenen()==1) {
            landenOpLegeEigenPocket();
        }
    }

    private void landenOpLegeEigenPocket() {
        if (getEigenaar().isAanZet()) {

            int eigenPocketNumber = this.getPocketNumber();
            int buurPocketNumber = getPocketNumberNeighbor(getPocketNumber());
            int buit = berekenBuitLeegVakje(buurPocketNumber, eigenPocketNumber);

            getVakjeOpPositie(getMancalaPositie(getEigenaar())).voegAantalStenenToeAanVakje(buit);
            leegVakje();
            getVakjeOpPositie(buurPocketNumber).leegVakje();
        }
    }

    private int getPocketNumberNeighbor(int pocketNumber) {
        return totaalVakjes - pocketNumber;
    }

    private int berekenBuitLeegVakje(int buurPocketNumber, int eigenPocketNumber) {
        int buit = 0;
        if (getVakjeOpPositie(buurPocketNumber).getAantalStenen() > 0) {
            buit = getVakjeOpPositie(eigenPocketNumber).getAantalStenen() + getVakjeOpPositie(buurPocketNumber).getAantalStenen();
        }
        return buit;
    }

    @Override
    int telCollectieveStenenInPockets() {
        return getAantalStenen() + getVolgendVakje().telCollectieveStenenInPockets();
    }



}

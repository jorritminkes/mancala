package mancala.domain;


public class Pocket extends Vakje {
    private static final int[] standaardOpstelling = {4,4,4,4,4,4,0,4,4,4,4,4,4,0};


    public Pocket() {
        this(null, 1, 1, new Speler(), standaardOpstelling);
    }

    public Pocket(int beginSpeler) {
        this(null, 1, 1, new Speler(beginSpeler), standaardOpstelling);
    }

    public Pocket(int beginSpeler, int[] testOpstelling) {
        this(null, 1, 1, new Speler(beginSpeler), testOpstelling);
    }


    Pocket(Vakje eerste, int pocketNumber, int owner, Speler speler, int[] opstelling) {
        super(pocketNumber, opstelling[pocketNumber - 1], eerste, speler);
        setOwner(owner);

        int volgendNummer = pocketNumber + 1;

        if (volgendNummer % vakjesPerKant == 0) {
            setVolgendVakje(new Mancala(getEersteVakje(), volgendNummer, owner, speler, opstelling));
        } else {
            setVolgendVakje(new Pocket(getEersteVakje(), volgendNummer, owner, speler, opstelling));
        }
    }


    public void zet() {
        if (getAantalStenen() == 0) {
            throw new IllegalArgumentException("Kan niet op een leeg vakje spelen");
        }

        if (behoortPocketBijBeurt(getBeurt())) {
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
            beeindigZet(getBeurt());
        }
        if (stenenOmDoorTeGeven > 0) {
            getVolgendVakje().ontvangStenen(stenenOmDoorTeGeven);
        }
    }

    private boolean behoortPocketBijBeurt(int huidigeSpeler) {
        return huidigeSpeler == getOwner();
    }

    private void leegAllePocketsAlsSpelKlaarIs() {
        if (isSpelAfgelopen()) {
            verzamelStenenVanSpeler(1);
            verzamelStenenVanSpeler(2);
        }
    }

    private void verzamelStenenVanSpeler(int speler) {
        int startPositie = (speler == 1) ? 1 : vakjesPerKant + 1;
        getVakjeOpPositie(startPositie).verzamelStenenNaarMancala(0);
    }

    @Override
    void verzamelStenenNaarMancala(int stenenTeVerzamelen) {
        int stenenOmDoorTeGeven = stenenTeVerzamelen + getAantalStenen();
        leegVakje();
        getVolgendVakje().verzamelStenenNaarMancala(stenenOmDoorTeGeven);
    }



    private void beeindigZet(int beurt) {
        veroverIndienGelandOpLeegVakje(beurt);
        switchBeurt();
    }

    private void veroverIndienGelandOpLeegVakje(int huidigeSpeler) {
        if (getAantalStenen()==1) {
            landenOpLegeEigenPocket(huidigeSpeler);
        }
    }

    private void landenOpLegeEigenPocket(int huidigeSpeler) {
        if (behoortPocketBijBeurt(getBeurt())) {

            int eigenPocketNumber = this.getPocketNumber();
            int buurPocketNumber = getPocketNumberNeighbor(getPocketNumber());
            int buit = berekenBuitLeegVakje(buurPocketNumber, eigenPocketNumber);

            getVakjeOpPositie(getMancalaPositie(huidigeSpeler)).voegAantalStenenToeAanVakje(buit);
            leegVakje();
            getVakjeOpPositie(buurPocketNumber).leegVakje();
        }
    }

    private static int getMancalaPositie(int huidigeSpeler) {
        return huidigeSpeler == 1 ? vakjesPerKant : 2*vakjesPerKant;
    }

    private int getPocketNumberNeighbor(int pocketNumber) {
        return totaalVakjes-pocketNumber;
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

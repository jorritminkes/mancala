package mancala.domain;


public class Pocket extends Vakje {
    private static final int[] standaardOpstelling = {4,4,4,4,4,4,0,4,4,4,4,4,4,0};

    public Pocket() {
        this(null, 1, 1, new Beurt(), standaardOpstelling);
    }

    public Pocket(int beginSpeler) {
        this(null, 1, 1, new Beurt(beginSpeler), standaardOpstelling);
    }

    public Pocket(int beginSpeler, int[] testOpstelling) {
        this(null, 1, 1, new Beurt(beginSpeler), testOpstelling);
    }


    Pocket(Vakje eerste, int pocketNumber, int owner, Beurt beurt, int[] opstelling) {
        super(pocketNumber, opstelling[pocketNumber - 1], eerste, beurt);
        setOwner(owner);

        int volgendNummer = pocketNumber + 1;

        if (volgendNummer % vakjesPerKant == 0) {
            setVolgendVakje(new Mancala(getEersteVakje(), volgendNummer, owner, beurt, opstelling));
        } else {
            setVolgendVakje(new Pocket(getEersteVakje(), volgendNummer, owner, beurt, opstelling));
        }
    }

    public void zet() {
        if (getAantalStenen() == 0) {
            throw new IllegalArgumentException("Kan niet op een leeg vakje spelen");
        }

//        stenenDoorgeven();

        if (behoortPocketBijBeurt(getPocketNumber(), getBeurt())) {
            stenenDoorgeven();
        } else {
            throw new IllegalArgumentException("Het is niet jouw beurt om deze pocket te spelen");
        }

        leegAllePocketsAlsSpelKlaarIs();
    }

    private void stenenDoorgeven() {
        int doorgegevenStenen = getAantalStenen();
        leegVakje();
        getVolgendVakje().ontvangStenen(doorgegevenStenen);
    }

    @Override
    void ontvangStenen(int ontvangenStenen) {
        voegAantalStenenToe(1);
        geefStenenDoorAanVolgende(ontvangenStenen);
        if (ontvangenStenen == 1) {
            beeindigZet(getBeurt());
        }
    }

    private boolean behoortPocketBijBeurt(int pocketNumber, int huidigeSpeler) {
        return huidigeSpeler == getOwner();
    }

    private void leegAllePocketsAlsSpelKlaarIs() {
        if (isSpelAfgelopen()) {
            leegPocketsNaarMancala(getMancalaPositie(1));
            leegPocketsNaarMancala(getMancalaPositie(2));
        }
    }

    private void leegPocketsNaarMancala(int mancalaPositie) {
        Vakje mancala = getVakjeOpPositie(mancalaPositie);
        int startPositie = mancalaPositie - pocketsPerKant;
        int eindPositie = mancalaPositie - 1;
        for (int positie = startPositie; positie <= eindPositie; ++positie) {
            Vakje pocket = getVakjeOpPositie(positie);
            mancala.voegAantalStenenToe(pocket.getAantalStenen());
            pocket.leegVakje();
        }
    }

    private void geefStenenDoorAanVolgende(int ontvangenStenen) {
        if (ontvangenStenen > 1) {
            getVolgendVakje().ontvangStenen(ontvangenStenen - 1);
        }
    }

    private void beeindigZet(int beurt) {
        veroverOpLeegVakje(beurt);
        switchBeurt();
    }

    private void veroverOpLeegVakje(int huidigeSpeler) {
        if (getAantalStenen()==1) {
            landenOpLegeEigenPocket(huidigeSpeler);
        }
    }

    private void landenOpLegeEigenPocket(int huidigeSpeler) {
        if (behoortPocketBijBeurt(getPocketNumber(), getBeurt())) {

            int eigenPocketNumber = this.getPocketNumber();
            int buurPocketNumber = getPocketNumberNeighbor(getPocketNumber());
            int buit = berekenBuitLeegVakje(buurPocketNumber, eigenPocketNumber);

            getVakjeOpPositie(getMancalaPositie(huidigeSpeler)).voegAantalStenenToe(buit);
            getVakjeOpPositie(eigenPocketNumber).leegVakje();
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
    int telStenenInPockets() {
        return getAantalStenen() + getVolgendVakje().telStenenInPockets();
    }



}

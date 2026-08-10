package mancala.domain;


public class Pocket extends Vakje {

    public Pocket() {
        this(null, 1, 1, new Beurt());
    }

    Pocket(Vakje eerste, int pocketNumber, int owner, Beurt beurt) {
        super(pocketNumber, eerste, beurt);
        setAantalStenen(4);
        setOwner(owner);

        int volgendNummer = pocketNumber + 1;

        if (volgendNummer == getMancalaPositie(1)) {
            setVolgendVakje(new Mancala(getEersteVakje(), volgendNummer, 1, beurt));
        }
        if (volgendNummer == getMancalaPositie(2)) {
            setVolgendVakje(new Mancala(getEersteVakje(), volgendNummer, 2, beurt));
        }
        if (volgendNummer != 7 && volgendNummer < totaalVakjes) {
            int volgendOwner = (volgendNummer <= pocketsPerKant) ? 1 : 2;
            setVolgendVakje(new Pocket(getEersteVakje(), volgendNummer, volgendOwner, beurt));
        }
    }

    public Pocket getPocketOpPositie(int positie) {
        Vakje vakje = getVakjeOpPositie(positie);

        if (vakje instanceof Pocket pocket) {
            return pocket;
        }

        throw new IllegalArgumentException("Vakje op positie " + positie + " is een Mancala, geen Pocket!");
    }

    public void zet() {
        if (getAantalStenen() == 0) {
            throw new IllegalArgumentException("Kan niet op een leeg vakje spelen");
        }

        stenenDoorgeven();
        leegAllePocketsAlsSpelKlaarIs();
    }

    private void stenenDoorgeven() {
        if (behoortPocketBijBeurt(getPocketNumber(), getBeurt())) {
            int doorgegevenStenen = getAantalStenen();
            setAantalStenen(0);
            getVolgendVakje().ontvangStenen(doorgegevenStenen);
        } else {
            throw new IllegalArgumentException("Het is niet jouw beurt om deze pocket te spelen");
        }
    }

    void ontvangStenen(int ontvangenStenen) {
        voegAantalStenenToe(1);
        geefStenenDoorAanVolgende(ontvangenStenen);
        if (ontvangenStenen == 1) {
            beeindigZet(getBeurt());
        }
    }

    private boolean behoortPocketBijBeurt(int pocketNumber, int huidigeSpeler) {
        return ((huidigeSpeler == 1) && (pocketNumber > 0) && (pocketNumber < vakjesPerKant))
                || ((huidigeSpeler == 2) && (pocketNumber > vakjesPerKant) && (pocketNumber < totaalVakjes));
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
            pocket.setAantalStenen(0);
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
            getVakjeOpPositie(eigenPocketNumber).setAantalStenen(0);
            getVakjeOpPositie(buurPocketNumber).setAantalStenen(0);
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

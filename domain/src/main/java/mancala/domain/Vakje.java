package mancala.domain;

public abstract class Vakje {
    private final int pocketNumber;
    private int aantalStenen;
    private Speler eigenaar;
    private final Vakje eersteVakje;
    private Vakje volgendVakje;

    static final int pocketsPerKant = 6;
    static final int vakjesPerKant = pocketsPerKant + 1;
    static final int totaalVakjes = 2 * vakjesPerKant;


    Vakje(int pocketNumber, int aantalStenen, Vakje eerste, Speler eigenaar) {
        this.pocketNumber = pocketNumber;
        this.aantalStenen = aantalStenen;
        this.eigenaar = eigenaar;
        this.eersteVakje = (eerste != null) ? eerste : this;
    }

    abstract void ontvangStenen(int ontvangenStenen);


    int getPocketNumber() {
        return pocketNumber;
    }

    Speler getEigenaar() {
        return eigenaar;
    }


    int getAantalStenen() {
        return aantalStenen;
    }

    private void setAantalStenen(int aantalStenen) {
        this.aantalStenen = aantalStenen;
    }

    void leegVakje() {
        setAantalStenen(0);
    }

    void voegAantalStenenToeAanVakje(int aantalStenenToevoegen) {
        setAantalStenen(this.aantalStenen + aantalStenenToevoegen);
    }


    Vakje getEersteVakje() {
        return eersteVakje;
    }

    Vakje getVolgendVakje() {
        return volgendVakje;
    }

    void setVolgendVakje(Vakje volgendVakje) {
        this.volgendVakje = volgendVakje;
    }

    Vakje getVakjeOpPositie(int positie) {
        return eersteVakje.getVakjeOpPositieRecursief(positie);
    }

    private Vakje getVakjeOpPositieRecursief(int positie) {
        if (positie == 1) {
            return this;
        }
        return volgendVakje.getVakjeOpPositieRecursief(positie - 1);
    }

    void switchBeurt() {
        eigenaar.switchBeurt();
    }

    int getStartPositie(Speler speler) {
        return (speler == eersteVakje.getEigenaar()) ? 1 : vakjesPerKant + 1;
    }

    int getMancalaPositie(Speler speler) {
        return (speler == eersteVakje.getEigenaar()) ? vakjesPerKant : totaalVakjes;
    }


    public boolean isSpelAfgelopen() {
        Speler speler1 = eersteVakje.getEigenaar();
        Speler speler2 = speler1.getTegenstander();
        return zijnPocketsLeegVanSpeler(speler1) || zijnPocketsLeegVanSpeler(speler2);
    }

    private boolean zijnPocketsLeegVanSpeler(Speler speler) {
        int totaalStenen = getVakjeOpPositie(getStartPositie(speler)).telCollectieveStenenInPockets();
        return (totaalStenen == 0);
    }

    abstract int telCollectieveStenenInPockets();

    abstract void verzamelStenenNaarMancala(int stenenTeVerzamelen);


    public Speler getWinnaar() {
        if (!isSpelAfgelopen()) {
            return null; //misschien niet null. Misschien optional
        }

        Speler speler1 = eersteVakje.getEigenaar();
        Speler speler2 = speler1.getTegenstander();

        int stenenSpelerEen = getVakjeOpPositie(getMancalaPositie(speler1)).getAantalStenen();
        int stenenSpelerTwee = getVakjeOpPositie(getMancalaPositie(speler2)).getAantalStenen();

        //enum voor winnaar en moet alleen aangevraagd worden of reactie gegeven worden if isSpelAfgelopen
        // Optional<speler> gebruiken
        if (stenenSpelerEen > stenenSpelerTwee) {
            return speler1;
        }
        if (stenenSpelerTwee > stenenSpelerEen) {
            return speler2;
        }
        return null; // dit is eng
    }

}

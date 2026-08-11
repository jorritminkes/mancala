package mancala.domain;

public abstract class Vakje {
    private final int pocketNumber;
    private int aantalStenen;
    private int owner;
    private final Vakje eersteVakje;
    private Vakje volgendVakje;
    private Beurt beurt;

    static final int pocketsPerKant = 6;
    static final int vakjesPerKant = pocketsPerKant + 1;
    static final int totaalVakjes = 2 * vakjesPerKant;

//    static final int totaalVakjes = 14;
//    static final int pocketsPerKant = 6;
//    static final int vakjesPerKant = 7;


    Vakje(int pocketNumber, Vakje eerste, Beurt beurt) {
        this.pocketNumber = pocketNumber;
        this.beurt = beurt;
        this.eersteVakje = (eerste != null) ? eerste : this;
    }

    abstract void ontvangStenen(int ontvangenStenen);


    int getPocketNumber() {
        return pocketNumber;
    }

    int getOwner() {
        return owner;
    }

    void setOwner(int owner) {
        this.owner = owner;
    }


    int getAantalStenen() {
        return aantalStenen;
    }

    void setAantalStenen(int aantalStenen) {
        this.aantalStenen = aantalStenen;
    }

    void voegAantalStenenToe(int aantalStenenToevoegen) {
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


    int getBeurt() {
        return beurt.getBeurt();
    }

    void switchBeurt() { beurt.switchBeurt();}


    public boolean isSpelAfgelopen() {
        return zijnPocketsLeegVanSpeler(1) || zijnPocketsLeegVanSpeler(2);
    }

    private boolean zijnPocketsLeegVanSpeler(int speler) {
        int startPositie = (speler == 1) ? 1 : vakjesPerKant + 1;
        int totaalStenen = getVakjeOpPositie(startPositie).telStenenInPockets();
        return totaalStenen == 0;
    }

    abstract int telStenenInPockets();

}

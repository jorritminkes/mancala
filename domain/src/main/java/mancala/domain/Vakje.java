package mancala.domain;

public abstract class Vakje {
    private int pocketNumber;
    private int aantalStenen;
    private int owner;
    private final Vakje eersteVakje;
    private Vakje volgendVakje;
    private Beurt beurt;

    Vakje(int pocketNumber, Vakje eerste, Beurt beurt) {
        this.pocketNumber = pocketNumber;
        this.beurt = beurt;
        this.eersteVakje = (eerste != null) ? eerste : this;
    }

    abstract void ontvangStenen(int ontvangenStenen);

    public int getPocketNumber() {
        return pocketNumber;
    }

    void setOwner(int owner) {
        this.owner = owner;
    }

    void setVolgendVakje(Vakje volgendVakje) {
        this.volgendVakje = volgendVakje;
    }

    Vakje getEersteVakje() {
        return eersteVakje;
    }

    void switchBeurt() { beurt.switchBeurt();}

    public int getOwner() {
        return owner;
    }

    public int getAantalStenen() {
        return aantalStenen;
    }

    void setAantalStenen(int aantalStenen) {
        this.aantalStenen = aantalStenen;
    }

    void voegAantalStenenToe(int aantalStenenToevoegen) {
        setAantalStenen(this.aantalStenen + aantalStenenToevoegen);
    }

    Vakje getVolgendVakje() {
        return volgendVakje;
    }

    public int getBeurt() {
        return beurt.getBeurt();
    }

    public Vakje getVakjeOpPositie(int positie) {
        return eersteVakje.getVakjeOpPositieRecursief(positie);
    }

    private Vakje getVakjeOpPositieRecursief(int positie) {
        if (positie == 1) {
            return this;
        }
        return volgendVakje.getVakjeOpPositieRecursief(positie - 1);
    }

    public boolean isSpelAfgelopen() {
        return zijnPocketsLeegVanSpeler(1) || zijnPocketsLeegVanSpeler(2);
    }

    private boolean zijnPocketsLeegVanSpeler(int speler) {
        int totaalStenenPerSpeler = getTotaalStenenInPocketsPerSpeler(speler);
        return totaalStenenPerSpeler == 0;
    }

    private int getTotaalStenenInPocketsPerSpeler(int speler) {
        int mancalaOffset = (speler == 1) ? 0 : 7;
        int totaalStenen = 0;
        totaalStenen += this.getVakjeOpPositie(mancalaOffset + 1).getAantalStenen();
        totaalStenen += this.getVakjeOpPositie(mancalaOffset + 2).getAantalStenen();
        totaalStenen += this.getVakjeOpPositie(mancalaOffset + 3).getAantalStenen();
        totaalStenen += this.getVakjeOpPositie(mancalaOffset + 4).getAantalStenen();
        totaalStenen += this.getVakjeOpPositie(mancalaOffset + 5).getAantalStenen();
        totaalStenen += this.getVakjeOpPositie(mancalaOffset + 6).getAantalStenen();
        return totaalStenen;
    }


}

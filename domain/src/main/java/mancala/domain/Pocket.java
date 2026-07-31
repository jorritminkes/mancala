package mancala.domain;


public class Pocket extends Vakje {

    public Pocket() {
        this(null, 1, 1, new Beurt());
    }

    protected Pocket(Vakje eerste, int pocketNumber, int owner, Beurt beurt) {
        super(pocketNumber, eerste, beurt);
        aantalStenen = 4;
        this.owner = owner;

    }

    public boolean behoortPocketBijBeurt(int pocketNumber, Beurt beurt) {
        int huidigeSpeler = beurt.getBeurt();

        return (huidigeSpeler == 1 && pocketNumber >= 1 && pocketNumber <= 6)
                || (huidigeSpeler == 2 && pocketNumber >= 8 && pocketNumber <= 13);
    }

    @Override
    public void zet() {

        if (aantalStenen == 0) {
            throw new IllegalArgumentException("Kan niet op een leeg vakje spelen");
        }

        stenenDoorgeven();
    }

    private void stenenDoorgeven() {
        if (behoortPocketBijBeurt(pocketNumber, beurt)) {
            int doorgegevenStenen = aantalStenen;
            aantalStenen = 0;
            volgendVakje.ontvangStenen(doorgegevenStenen);
        } else {
            throw new IllegalArgumentException("Het is niet jouw beurt om deze pocket te spelen");
        }
    }

    public void ontvangStenen(int ontvangenStenen) {
        this.setAantalStenen(this.aantalStenen + 1);
        int huidigeSpeler = beurt.getBeurt();

        if (ontvangenStenen - 1 > 0) {
            this.volgendVakje.ontvangStenen(ontvangenStenen - 1);
        }

        if (ontvangenStenen == 1) {
            indienVakjeLeegDanVakjeEnBuurNaarMancala(huidigeSpeler);
            beurt.switchBeurt();
        }

    }

    private void indienVakjeLeegDanVakjeEnBuurNaarMancala(int huidigeSpeler) {
        if (volgendVakje.aantalStenen==0) {
            landenOpLegeEigenPocket(huidigeSpeler);
        }
    }

    private void landenOpLegeEigenPocket(int huidigeSpeler) {
        if (behoortPocketBijBeurt(getPocketNumber(), beurt)) {

            int eigenPocketNumber = this.getPocketNumber();
            int buurPocketNumber = getPocketNumberNeighbor(this.pocketNumber);

            int buit = berekenBuitLeegVakje(buurPocketNumber, eigenPocketNumber);

            getVakjeOpPositie(7 * huidigeSpeler).voegAantalStenenToe(buit);
            getVakjeOpPositie(eigenPocketNumber).setAantalStenen(0);
            getVakjeOpPositie(buurPocketNumber).setAantalStenen(0);
        }
    }

    private int berekenBuitLeegVakje(int buurPocketNumber, int eigenPocketNumber) {
        int buit = 0;
        if (getVakjeOpPositie(buurPocketNumber).getAantalStenen() > 0) {
            buit = getVakjeOpPositie(eigenPocketNumber).getAantalStenen() + getVakjeOpPositie(buurPocketNumber).getAantalStenen();
        }
        return buit;
    }



}

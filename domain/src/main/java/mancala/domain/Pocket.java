package mancala.domain;


public class Pocket extends Vakje {

    public Pocket() {
        this(null, 1, 1, new Beurt());
    }

    protected Pocket(Vakje eerste, int pocketNumber, int owner, Beurt beurt) {
        super(pocketNumber, eerste, beurt);
        this.aantalStenen = 4;
        this.owner = owner;

        int volgendNummer = pocketNumber + 1;

        if (volgendNummer == 7) {
            this.volgendVakje = new Mancala(this.eersteVakje, volgendNummer, 1, beurt);
        }
        if (volgendNummer == 14) {
            this.volgendVakje = new Mancala(this.eersteVakje, volgendNummer, 2, beurt);
        }
        if (volgendNummer != 7 && volgendNummer < 14) {
            int volgendOwner = (volgendNummer <= 6) ? 1 : 2;
            this.volgendVakje = new Pocket(this.eersteVakje, volgendNummer, volgendOwner, beurt);
        }
    }


    public boolean behoortPocketBijBeurt(int pocketNumber, Beurt beurt) {
        int huidigeSpeler = beurt.getBeurt();

        return (huidigeSpeler == 1 && pocketNumber >= 1 && pocketNumber <= 6)
                || (huidigeSpeler == 2 && pocketNumber >= 8 && pocketNumber <= 13);
    }

    @Override
    public void zet() {

        if (isSpelAfgelopen()) {
            throw new IllegalStateException("Het spel is al afgelopen!");
        }

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
        if (aantalStenen==1) {
            landenOpLegeEigenPocket(huidigeSpeler);
        }
    }

    private void landenOpLegeEigenPocket(int huidigeSpeler) {
        if (behoortPocketBijBeurt(getPocketNumber(), beurt)) {

            int eigenPocketNumber = this.getPocketNumber();
            int buurPocketNumber = getPocketNumberNeighbor(this.pocketNumber);

            int buit = berekenBuitLeegVakje(buurPocketNumber, eigenPocketNumber);

            getVakjeOpPositie(getPlayersMancalaPocketNumber(huidigeSpeler)).voegAantalStenenToe(buit);
            getVakjeOpPositie(eigenPocketNumber).setAantalStenen(0);
            getVakjeOpPositie(buurPocketNumber).setAantalStenen(0);
        }
    }

    private static int getPlayersMancalaPocketNumber(int huidigeSpeler) {
        return 7 * huidigeSpeler;
    }

    private int berekenBuitLeegVakje(int buurPocketNumber, int eigenPocketNumber) {
        int buit = 0;
        if (getVakjeOpPositie(buurPocketNumber).getAantalStenen() > 0) {
            buit = getVakjeOpPositie(eigenPocketNumber).getAantalStenen() + getVakjeOpPositie(buurPocketNumber).getAantalStenen();
        }
        return buit;
    }



}

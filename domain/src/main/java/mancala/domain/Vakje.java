package mancala.domain;

public abstract class Vakje {
    protected int pocketNumber;
    protected int aantalStenen;
    protected int owner;
    protected Vakje eersteVakje;
    protected Vakje volgendVakje;
    protected Beurt beurt;

    protected Vakje(int pocketNumber, Vakje eerste, Beurt beurt) {
        this.pocketNumber = pocketNumber;
        this.beurt = beurt;
        Vakje zichzelf = (eerste != null) ? eerste : this;
        this.eersteVakje = zichzelf;

        if (pocketNumber < 14) {
            this.volgendVakje = maakVolgendVakje(zichzelf, pocketNumber + 1, beurt);
        } else {
            this.volgendVakje = zichzelf;
        }
    }

    private Vakje maakVolgendVakje(Vakje eerste, int pocketNumber, Beurt beurt) {
        return switch (pocketNumber) {
            case 1, 2, 3, 4, 5, 6 -> new Pocket(eerste, pocketNumber, 1, beurt);
            case 7 -> new Mancala(eerste, pocketNumber, 1, beurt);
            case 8, 9, 10, 11, 12, 13 -> new Pocket(eerste, pocketNumber, 2, beurt);
            case 14 -> new Mancala(eerste, pocketNumber, 2, beurt);
            default -> throw new IllegalArgumentException("Ongeldig pocketNumber: " + pocketNumber);
        };
    }

    public abstract void zet();

    protected abstract void ontvangStenen(int ontvangenStenen);

    public int getPocketNumber() {
        return pocketNumber;
    }

    public int getOwner() {
        return owner;
    }

    public int getAantalStenen() {
        return aantalStenen;
    }

    protected void setAantalStenen(int aantalStenen) {
        this.aantalStenen = aantalStenen;
    }

    protected Vakje getVolgendVakje() {
        return volgendVakje;
    }

    protected void voegAantalStenenToe(int aantalStenenToevoegen) {
        setAantalStenen(this.aantalStenen + aantalStenenToevoegen);
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


    public int getBeurt() {
        return beurt.getBeurt();
    }

    public int getPocketNumberNeighbor(int pocketNumber) {
        return 14-pocketNumber;
    }


    public boolean isSpelAfgelopen() {
        return zijnPocketsLeegVanSpeler(1) || zijnPocketsLeegVanSpeler(2);
    }

    private boolean zijnPocketsLeegVanSpeler(int speler) {
        int totaalStenenPerSpeler = getTotaalStenenInPocketsPerSpeler(speler);
        if (totaalStenenPerSpeler == 0) {
            return true;
        } else {
            return false;
        }
    }

    private int getTotaalStenenInPocketsPerSpeler(int speler) {
        int mancalaOffset = 7*(speler-1);
        int totaalStenen = 0;
        totaalStenen += this.getVakjeOpPositie(mancalaOffset + 1).getAantalStenen();
        totaalStenen += this.getVakjeOpPositie(mancalaOffset + 2).getAantalStenen();
        totaalStenen += this.getVakjeOpPositie(mancalaOffset + 3).getAantalStenen();
        totaalStenen += this.getVakjeOpPositie(mancalaOffset + 4).getAantalStenen();
        totaalStenen += this.getVakjeOpPositie(mancalaOffset + 5).getAantalStenen();
        totaalStenen += this.getVakjeOpPositie(mancalaOffset + 6).getAantalStenen();
        return totaalStenen;
    }

    public int checkWinnaar() {
        if (!isSpelAfgelopen()) {
            return -1;
        }

        int eindscoreSpeler1 = berekenEindscore(1);
        int eindscoreSpeler2 = berekenEindscore(2);

        if (eindscoreSpeler1 > eindscoreSpeler2) {
            return 1;
        }

        if (eindscoreSpeler2 > eindscoreSpeler1) {
            return 2;
        }

        if (eindscoreSpeler1 == eindscoreSpeler2) {
            return 0;
        }
        return -1;
    }

    protected int berekenEindscore(int speler) {
        int mancalaPocketNumberVanSpeler = 7*speler;
        int score;
        score = getVakjeOpPositie(mancalaPocketNumberVanSpeler).getAantalStenen() + getTotaalStenenInPocketsPerSpeler(speler);
        return score;
    }


}

package mancala.domain;

public abstract class Vakje {
    protected int pocketNumber;
    protected int aantalStenen;
    protected int owner;
    protected Vakje volgendVakje;
    protected Beurt beurt;

    protected Vakje(int pocketNumber, Vakje eerste, Beurt beurt) {
        this.pocketNumber = pocketNumber;
        this.beurt = beurt;
        Vakje zichzelf = (eerste != null) ? eerste : this;

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
//    protected abstract void geefStenenDoor(int doorgegevenStenen);
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

    protected void voegAantalStenenToe(int aantalStenenToevoegen) {
        setAantalStenen(this.aantalStenen + aantalStenenToevoegen);
    }

    protected void setAantalStenen(int aantalStenen) {
        this.aantalStenen = aantalStenen;
    }

    protected Vakje getVolgendVakje() {
        return volgendVakje;
    }

    public Vakje getVakjeOpPositie(int positie) {
        if (positie == 1) {
            return this;
        }
        return volgendVakje.getVakjeOpPositie(positie - 1);
    }

    public int getBeurt() {
        return beurt.getBeurt();
    }

    public int getPocketNumberNeighbor(int pocketNumber) {
        return 14-pocketNumber;
    }

    public int getPocketNumberOfMancala(int pocketNumber) {
        if ((pocketNumber > 0) && (pocketNumber < 8)) {
            return 7;
        } else {
            return 14;
        }
    }







}

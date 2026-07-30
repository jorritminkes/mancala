package mancala.domain;

public abstract class Vakje {
    protected int pocketNumber;
    protected int aantalStenen;
    protected int owner;
    protected Vakje volgendVakje;

    protected Vakje(int pocketNumber, Vakje eerste) {
        this.pocketNumber = pocketNumber;
        Vakje zichzelf = (eerste != null) ? eerste : this;

        if (pocketNumber < 14) {
            this.volgendVakje = maakVolgendVakje(zichzelf, pocketNumber + 1);
        } else {
            this.volgendVakje = zichzelf;
        }
    }

    private Vakje maakVolgendVakje(Vakje eerste, int pocketNumber) {
        return switch (pocketNumber) {
            case 1, 2, 3, 4, 5, 6 -> new Pocket(eerste, pocketNumber, 1);
            case 7 -> new Mancala(eerste, pocketNumber, 1);
            case 8, 9, 10, 11, 12, 13 -> new Pocket(eerste, pocketNumber, 2);
            case 14 -> new Mancala(eerste, pocketNumber, 2);
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

//    protected void geefStenenDoor(int doorgegevenStenen) {
//        if (doorgegevenStenen != 0) {
//            volgendVakje.setAantalStenen(volgendVakje.getAantalStenen() + 1);
//            volgendVakje.geefStenenDoor(doorgegevenStenen - 1);
//        }
//    }







}

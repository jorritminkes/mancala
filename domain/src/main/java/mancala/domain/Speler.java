package mancala.domain;

public class Speler {
    private int spelerNummer;
    private boolean aanZet;
    private final Speler tegenstander;

    Speler() {
        this(1);
    }

    Speler(int beginSpeler) {
        this.spelerNummer = 1;
        this.aanZet = (beginSpeler == 1);
        this.tegenstander = new Speler(2, beginSpeler, this);
    }

    Speler(int spelerNummer, int beginSpeler, Speler tegenstander) {
        this.spelerNummer = spelerNummer;
        this.aanZet = (beginSpeler == spelerNummer);
        this.tegenstander = tegenstander;
    }

    public int getSpelerNummer() {
        return spelerNummer;
    }

    void switchBeurt() {
        this.aanZet = !this.aanZet;
        tegenstander.aanZet = !tegenstander.aanZet;
    }

    int getSpelerAanZet() {
        return aanZet? spelerNummer : tegenstander.getSpelerNummer();
    }
}

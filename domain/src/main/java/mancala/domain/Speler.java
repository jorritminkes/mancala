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

    private Speler(int spelerNummer, int beginSpeler, Speler tegenstander) {
        this.spelerNummer = spelerNummer;
        this.aanZet = (beginSpeler == spelerNummer);
        this.tegenstander = tegenstander;
    }

    int getSpelerNummer() {
        return spelerNummer;
    }

    public boolean isAanZet() {
        return aanZet;
    }

    Speler getTegenstander() {
        return tegenstander;
    }

    void switchBeurt() {
        this.aanZet = !this.aanZet;
        tegenstander.aanZet = !tegenstander.aanZet;
    }
}

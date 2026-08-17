package mancala.domain;

import java.util.Optional;

public class Facade {
    private Vakje eersteVakje;
    
    public Facade() {
        this.eersteVakje = new Pocket(1);
    }
    
    public void zet(int positie) {
        Vakje vakje = eersteVakje.getVakjeOpPositie(positie);
        if (!(vakje instanceof Pocket pocket)) {
            throw new IllegalArgumentException("Kan niet op een Mancala een zet doen.");
        }
        pocket.zet();
    }
    
    public int getAantalStenen(int positie) {
        return eersteVakje.getVakjeOpPositie(positie).getAantalStenen();
    }
    
    public boolean isSpelAfgelopen() {
        return ((Pocket) eersteVakje).isSpelAfgelopen();
    }
    
    public Optional<Speler> getWinnaar() {
        return ((Pocket) eersteVakje).getWinnaar();
    }
}

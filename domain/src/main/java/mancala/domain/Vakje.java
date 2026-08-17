package mancala.domain;

import java.util.Optional;

public abstract class Vakje {
    private final int pocketNumber;
    private int aantalStenen;
    private Speler eigenaar;
    private final Vakje eersteVakje;
    private Vakje volgendVakje;

    static final int pocketsPerKant = 6;
    static final int vakjesPerKant = pocketsPerKant + 1;
    static final int totaalVakjes = 2 * vakjesPerKant;


    Vakje(int pocketNumber, int aantalStenen, Vakje eerste, Speler eigenaar) {
        this.pocketNumber = pocketNumber;
        this.aantalStenen = aantalStenen;
        this.eigenaar = eigenaar;
        this.eersteVakje = (eerste != null) ? eerste : this;
    }

    abstract void ontvangStenen(int ontvangenStenen);


    int getPocketNumber() {
        return pocketNumber;
    }

    Speler getEigenaar() {
        return eigenaar;
    }


    int getAantalStenen() {
        return aantalStenen;
    }

    private void setAantalStenen(int aantalStenen) {
        this.aantalStenen = aantalStenen;
    }

    void leegVakje() {
        setAantalStenen(0);
    }

    void voegAantalStenenToeAanVakje(int aantalStenenToevoegen) {
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

    void switchBeurt() {
        eigenaar.switchBeurt();
    }

    int getStartPositie(Speler speler) {
        return (speler == eersteVakje.getEigenaar()) ? 1 : vakjesPerKant + 1;
    }

    int getMancalaPositie(Speler speler) {
        return (speler == eersteVakje.getEigenaar()) ? vakjesPerKant : totaalVakjes;
    }

    abstract void verzamelStenenNaarMancala(int stenenTeVerzamelen);



}

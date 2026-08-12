package mancala.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import org.junit.jupiter.api.Test;

public class PocketTest {
    private Vakje eersteVakje;

    @BeforeEach
    public void setUp() {
        eersteVakje = new Pocket(1);
    }

    public void speelKortsteSpel() {
        ((Pocket) eersteVakje.getVakjeOpPositie(3)).zet();
        ((Pocket) eersteVakje.getVakjeOpPositie(6)).zet();
        ((Pocket) eersteVakje.getVakjeOpPositie(9)).zet();
        ((Pocket) eersteVakje.getVakjeOpPositie(10)).zet();
        ((Pocket) eersteVakje.getVakjeOpPositie(1)).zet();
        ((Pocket) eersteVakje.getVakjeOpPositie(11)).zet();
        ((Pocket) eersteVakje.getVakjeOpPositie(1)).zet();
        ((Pocket) eersteVakje.getVakjeOpPositie(12)).zet();
        ((Pocket) eersteVakje.getVakjeOpPositie(1)).zet();
        ((Pocket) eersteVakje.getVakjeOpPositie(13)).zet();
    }

    @Test
    public void TestPocketBestaatNaInitialisatie() {
        assertNotNull(eersteVakje, "Pocket should not be null");
    }

    @Test
    public void TestNieuwePocketHeeftVierStenen() {
        int aantalStenen = eersteVakje.getAantalStenen();
        assertEquals(4, aantalStenen);
    }

    @Test
    public void TestPocketVeertienBestaat() {
        Vakje vakjeVeertien = eersteVakje.getVakjeOpPositie(14);
        assertNotNull(vakjeVeertien);
    }

    @ParameterizedTest
    @CsvSource({"1,1", "2,2", "14,14", "15,1", "16,2"})
    public void TestPocketNumberKloptMetPositie(int positie, int verwachttePocketNumber) {
        int pocketNumber = eersteVakje.getVakjeOpPositie(positie).getPocketNumber();
        assertEquals(verwachttePocketNumber, pocketNumber);
    }



    @ParameterizedTest
    @CsvSource({"1,2", "2,3", "14,1", "15,2"})
    public void TestPocketNumberVanVolgendePocketMatcht(int pocketNumber, int verwachtteVolgendePocketNumber) {
        int volgendePocketNumber = eersteVakje.getVakjeOpPositie(pocketNumber).getVolgendVakje().getPocketNumber();
        assertEquals(verwachtteVolgendePocketNumber, volgendePocketNumber);
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,6,12,13})
    public void TestHebbenAllePocketsVierStenen(int pocketNumber) {
        int aantalStenen = eersteVakje.getVakjeOpPositie(pocketNumber).getAantalStenen();
        assertEquals(4, aantalStenen);
    }

    @Test
    public void TestLeegtEenZetZijnPocket() {
        Vakje vakje5 = eersteVakje.getVakjeOpPositie(5);
        ((Pocket) vakje5).zet();
        int aantalStenen = vakje5.getAantalStenen();
        assertEquals(0, aantalStenen);
    }

    @Test
    public void TestZetWordtOvergeslagenBijLeegVakje() {
        Vakje vakje5 = eersteVakje.getVakjeOpPositie(5);
        ((Pocket) vakje5).zet();
        assertThrows(IllegalArgumentException.class, () -> {
            ((Pocket) vakje5).zet();
        });
    }

    @Test
    public void TestVolgendePocketKrijgtEenSteenBijZet() {
        Vakje vakje2 = eersteVakje.getVakjeOpPositie(2);
        Vakje vakje3 = eersteVakje.getVakjeOpPositie(3);
        int aantalStenenOpDrieVoorZet = vakje3.getAantalStenen();
        ((Pocket) vakje2).zet();
        assertEquals(aantalStenenOpDrieVoorZet + 1, vakje3.getAantalStenen());
    }

    @ParameterizedTest
    @CsvSource({"1,0", "2,5", "5,5", "6,4"})
    public void TestAlleSteentjesWordenDoorgegevenBijZetZonderMancala(int pocketNumber, int steentjesNaZet) {
        Vakje gespeeldVakje = eersteVakje.getVakjeOpPositie(1);
        Vakje gecontroleerdVakje = eersteVakje.getVakjeOpPositie(pocketNumber);
        ((Pocket) gespeeldVakje).zet();
        int aantalStenenInGecontroleerdePocket = gecontroleerdVakje.getAantalStenen();
        assertEquals(steentjesNaZet, aantalStenenInGecontroleerdePocket);
    }

    @Test
    public void TestPocketZevenIsMancala() {
        Vakje vakjeZeven = eersteVakje.getVakjeOpPositie(7);
        assertInstanceOf(Mancala.class, vakjeZeven);
    }

    @Test
    public void TestPocketVeertienIsMancala() {
        Vakje vakjeVeertien = eersteVakje.getVakjeOpPositie(14);
        assertInstanceOf(Mancala.class, vakjeVeertien);
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6})
    public void TestPocketsEenTotZesZijnVanSpelerEen(int pocketNumber) {
        Vakje vakje = eersteVakje.getVakjeOpPositie(pocketNumber);
        int ownerVakje = vakje.getEigenaar().getSpelerNummer();
        assertEquals(1, ownerVakje);
    }

    @ParameterizedTest
    @ValueSource(ints = {8,9,10,11,12,13})
    public void TestPocketsAchtTotDertienZijnVanSpelerTwee(int pocketNumber) {
        Vakje vakje = eersteVakje.getVakjeOpPositie(pocketNumber);
        int ownerVakje = vakje.getEigenaar().getSpelerNummer();
        assertEquals(2, ownerVakje);
    }

    @Test
    public void TestMancalaKrijgtSteenVanEigenSpeler() {
        Vakje vakje6 = eersteVakje.getVakjeOpPositie(6);
        Vakje mancala1 = eersteVakje.getVakjeOpPositie(7);
        ((Pocket) vakje6).zet();
        assertEquals(1, mancala1.getAantalStenen());
    }

    @Test
    public void TestMancalaKrijgtGeenSteenVanAndereSpeler() {
        int[] testOpstelling = {4,4,4,4,4,8,  0,  4,4,4,4,4,4,  0};
        eersteVakje = new Pocket(1, testOpstelling);

        Vakje vakje6 = eersteVakje.getVakjeOpPositie(6);
        Vakje mancala2 = eersteVakje.getVakjeOpPositie(14);
        ((Pocket) vakje6).zet();
        assertEquals(0, mancala2.getAantalStenen());
    }

    @Test
    public void TestZetLuktBijEigenPocket() {
        Vakje vakje5 = eersteVakje.getVakjeOpPositie(5);
        assertDoesNotThrow(() -> {
            ((Pocket) vakje5).zet();
        });
    }

    @Test
    public void TestZetMisluktBijPocketVanTegenstander() {
        Vakje vakje12 = eersteVakje.getVakjeOpPositie(12);
        assertThrows(IllegalArgumentException.class, () -> {
            ((Pocket) vakje12).zet();
        });
    }

    @Test
    public void TestVerandertBeurtBijEindigenOpPocket() {
        Vakje vakje1 = eersteVakje.getVakjeOpPositie(1);
        ((Pocket) vakje1).zet();
        Speler speler1 = vakje1.getEigenaar();
        assertFalse(speler1.isAanZet());
    }

    @Test
    public void TestBeurtBlijftGelijkBijEindigenInEigenMancala() {
        Vakje vakje3 = eersteVakje.getVakjeOpPositie(3);
        ((Pocket) vakje3).zet();
        Speler speler1 = vakje3.getEigenaar();
        assertTrue(speler1.isAanZet());
    }

    @Test
    public void TestLandenOpLegeEigenPocketMancala() {
        int[] testOpstelling = {4,4,4,4,0,4,  0,  4,4,4,4,4,4,  0};
        eersteVakje = new Pocket(1, testOpstelling);

        Vakje vakje1 = eersteVakje.getVakjeOpPositie(1);
        Vakje mancala1 = eersteVakje.getVakjeOpPositie(7);
        ((Pocket) vakje1).zet();
        assertEquals(5, mancala1.getAantalStenen());
    }

    @Test
    public void TestLandenOpLegeEigenPocketPocket6leeg() {
        int[] testOpstelling = {4,4,4,4,0,4,  0,  4,4,4,4,4,4,  0};
        eersteVakje = new Pocket(1, testOpstelling);

        Vakje vakje1 = eersteVakje.getVakjeOpPositie(1);
        Vakje vakje5 = eersteVakje.getVakjeOpPositie(5);
        ((Pocket) vakje1).zet();
        assertEquals(0, vakje5.getAantalStenen());
    }

    @Test
    public void TestLandenOpLegeEigenPocketPocket8leeg() {
        int[] testOpstelling = {4,4,4,4,0,4,  0,  4,4,4,4,4,4,  0};
        eersteVakje = new Pocket(1, testOpstelling);

        Vakje vakje1 = eersteVakje.getVakjeOpPositie(1);
        Vakje vakje9 = eersteVakje.getVakjeOpPositie(9);
        ((Pocket) vakje1).zet();
        assertEquals(0, vakje9.getAantalStenen());
    }

    @Test
    public void TestLandenOpTegenstanderLeegVakVoegtNietToeAanMancala() {
        int[] testOpstelling = {4,4,4,4,4,4,  0,  0,4,4,4,4,4,  0};
        eersteVakje = new Pocket(1, testOpstelling);

        Vakje vakje4 = eersteVakje.getVakjeOpPositie(4);
        Vakje mancala1 = eersteVakje.getVakjeOpPositie(7);
        ((Pocket) vakje4).zet();
        assertEquals(1, mancala1.getAantalStenen());
    }

    @ParameterizedTest
    @CsvSource({"1,0", "2,5", "3,5", "4,5", "5,0", "6,5", "7,8", "8,0", "9,0", "10,5", "11,5", "12,5", "13,5", "14,0"})
    public void TestNaDrieZettenZijnStenenCorrectVerdeeld(int pocketNumber, int verwachtteStenen) {
        Vakje eersteZetVakje = eersteVakje.getVakjeOpPositie(5);
        Vakje tweedeZetVakje = eersteVakje.getVakjeOpPositie(8);
        Vakje derdeZetVakje = eersteVakje.getVakjeOpPositie(1);
        Vakje controleVakje = eersteVakje.getVakjeOpPositie(pocketNumber);
        ((Pocket) eersteZetVakje).zet();
        ((Pocket) tweedeZetVakje).zet();
        ((Pocket) derdeZetVakje).zet();
        assertEquals(verwachtteStenen, controleVakje.getAantalStenen(), "Foutieve pocket: " + pocketNumber);
    }

    @ParameterizedTest
    @CsvSource({"1,4", "2,4", "3,4", "4,4", "5,0", "6,5", "7,1", "8,0", "9,6", "10,1", "11,6", "12,6", "13,6", "14,1"})
    public void TestNaDrieZettenIsBeurtBijDeJuisteSpeler(int pocketNumber, int verwachtteStenen) {
        Vakje eersteZetVakje = eersteVakje.getVakjeOpPositie(5);
        Vakje tweedeZetVakje = eersteVakje.getVakjeOpPositie(10);
        Vakje derdeZetVakje = eersteVakje.getVakjeOpPositie(8);
        Vakje controleVakje = eersteVakje.getVakjeOpPositie(pocketNumber);
        ((Pocket) eersteZetVakje).zet();
        ((Pocket) tweedeZetVakje).zet();
        ((Pocket) derdeZetVakje).zet();
        assertEquals(verwachtteStenen, controleVakje.getAantalStenen(), "Foutieve pocket: " + pocketNumber);
    }

    @ParameterizedTest
    @ValueSource(ints = {8,9,10,11,12,13})
    public void TestSpelerTweeKlaarNaKortsteSpel(int pocketNumber) {
        speelKortsteSpel();
        assertEquals(0, eersteVakje.getVakjeOpPositie(pocketNumber).getAantalStenen());
    }

    @Test
    public void TestSpelNogNietKlaar() {
        Vakje testVakje = eersteVakje.getVakjeOpPositie(1);
        ((Pocket) testVakje).zet();
        assertFalse(testVakje.isSpelAfgelopen());
    }

    @Test
    public void TestSpelKlaar() {
        speelKortsteSpel();
        assertTrue(eersteVakje.isSpelAfgelopen());
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,8,9,10,11,12,13})
    public void TestNaPotKlaarIsElkePocketLeeg(int pocketNumber) {
        Vakje vakje = eersteVakje.getVakjeOpPositie(pocketNumber);
        speelKortsteSpel();
        assertEquals(0, vakje.getAantalStenen());
    }

    @Test
    public void TestNaPotKlaarHebbenMancalasAlleStenen() {
        Vakje mancala1 = eersteVakje.getVakjeOpPositie(7);
        Vakje mancala2 = eersteVakje.getVakjeOpPositie(14);
        speelKortsteSpel();
        int totaleStenen = mancala1.getAantalStenen() + mancala2.getAantalStenen();
        assertEquals(48, totaleStenen);
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,8,9,10,11,12,13})
    public void TestNaPotKlaarGeenZetMogelijk(int pocketNumber) {
        Vakje vakje = eersteVakje.getVakjeOpPositie(pocketNumber);
        speelKortsteSpel();
        assertThrows(IllegalArgumentException.class, () -> {
            ((Pocket) vakje).zet();
        });
    }

    @Test
    public void TestNaPotKlaarMancalaEenHeeft41Stenen() {
        Vakje mancala = eersteVakje.getVakjeOpPositie(7);
        speelKortsteSpel();
        assertEquals(41, mancala.getAantalStenen());
    }

    @Test
    public void TestNaPotKlaarMancalaTweeHeeft7Stenen() {
        Vakje mancala = eersteVakje.getVakjeOpPositie(14);
        speelKortsteSpel();
        assertEquals(7, mancala.getAantalStenen());
    }

    @Test
    public void TestZetOpMancalaWerktNiet() {
        Vakje mancala = eersteVakje.getVakjeOpPositie(7);
        assertThrows(ClassCastException.class, () -> {
            ((Pocket) mancala).zet();
        });
    }

    @Test
    public void TestMancalaBuurmanWordtOvergeslagen() {
        int[] testOpstelling = {4,4,4,4,4,100,  0,  4,4,4,4,4,4,  0};
        eersteVakje = new Pocket(1, testOpstelling);

        Vakje vakje6 = eersteVakje.getVakjeOpPositie(6);
        Vakje mancalaBuurman = eersteVakje.getVakjeOpPositie(14);
        ((Pocket) vakje6).zet();
        assertEquals(0, mancalaBuurman.getAantalStenen());
    }

    @Test
    public void TestEigenMancalaWordtNietOvergeslagen() {
        int[] testOpstelling = {4,4,4,4,4,100,  0,  4,4,4,4,4,4,  0};
        eersteVakje = new Pocket(1, testOpstelling);

        Vakje vakje6 = eersteVakje.getVakjeOpPositie(6);
        Vakje mancalaEigen = eersteVakje.getVakjeOpPositie(7);
        ((Pocket) vakje6).zet();
        assertEquals(8, mancalaEigen.getAantalStenen());
    }

    @Test
    public void TestVeroverenMisluktAlsTegenoverliggendePocketLeegIs() {
        int[] testOpstelling = {4,4,4,4,4,13,  0,  0,4,4,4,4,4,  0};
        eersteVakje = new Pocket(1, testOpstelling);

        Vakje vakje6 = eersteVakje.getVakjeOpPositie(6);
        Vakje vakjeBuurman = eersteVakje.getVakjeOpPositie(Vakje.totaalVakjes - 6);
        ((Pocket) vakje6).zet();
        assertEquals(0, vakjeBuurman.getAantalStenen());
    }

    @Test
    public void TestSteenGegooidInLegePocketMisluktVerovering() {
        int[] testOpstelling = {4,4,4,4,4,13,  0,  0,4,4,4,4,4,  0};
        eersteVakje = new Pocket(1, testOpstelling);

        Vakje vakje6 = eersteVakje.getVakjeOpPositie(6);
        ((Pocket) vakje6).zet();
        assertEquals(0, vakje6.getAantalStenen());
    }

    @Test
    public void TestBlijftMancalaGelijkNaVeroverenMetLegeOverkant() {
        int[] testOpstelling = {4,4,4,4,0,4,  5,  4,0,4,4,4,4,  0};
        eersteVakje = new Pocket(1, testOpstelling);

        Vakje gespeeldeVakje = eersteVakje.getVakjeOpPositie(1);
        Vakje eigenMancala = eersteVakje.getVakjeOpPositie(7);

        ((Pocket) gespeeldeVakje).zet();

        assertEquals(5, eigenMancala.getAantalStenen());
    }

    @Test
    public void TestLeegtSpelCorrectNaarDeMancalaBijEindeSpeler1() {
        int[] testOpstelling = {1,0,0,0,0,0,  0,  4,4,4,4,27,4,  0};
        eersteVakje = new Pocket(1, testOpstelling);

        Vakje pocket1 = eersteVakje.getVakjeOpPositie(1);
        Vakje mancala1 = eersteVakje.getVakjeOpPositie(7);

        ((Pocket) pocket1).zet();

        assertEquals(28, mancala1.getAantalStenen());
    }

    @Test
    public void TestLeegtSpelCorrectNaarDeMancalaBijEindeSpeler2() {
        int[] testOpstelling = {1,0,0,0,0,0,  0,  4,4,4,4,27,4,  0};
        eersteVakje = new Pocket(1, testOpstelling);

        Vakje pocket1 = eersteVakje.getVakjeOpPositie(1);
        Vakje mancala2 = eersteVakje.getVakjeOpPositie(14);

        ((Pocket) pocket1).zet();

        assertEquals(20, mancala2.getAantalStenen());
    }

    @Test
    public void TestWintSpelerEenHetKortsteSpel() {
        speelKortsteSpel();
        assertEquals(1, eersteVakje.getWinnaar());
    }

    @Test
    public void TestWintSpelerTweeGegevenSpel() {
        int[] testOpstelling = {1,0,0,0,0,0,  0,  4,12,4,4,19,4,  0};
        eersteVakje = new Pocket(1, testOpstelling);
        Vakje pocket1 = eersteVakje.getVakjeOpPositie(1);

        ((Pocket) pocket1).zet();

        assertEquals(2, eersteVakje.getWinnaar());
    }


}

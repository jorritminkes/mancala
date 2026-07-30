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
        eersteVakje = new Pocket();
    }

    @Test
    public void TestPocketInstanceExists() {
        assertNotNull(eersteVakje, "Pocket should not be null");
    }

    @Test
    public void TestPocketHasFourBalls() {
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
        Vakje pocket5 = eersteVakje.getVakjeOpPositie(5);
        pocket5.zet();
        int aantalStenen = pocket5.getAantalStenen();
        assertEquals(0, aantalStenen);
    }

    @Test
    public void TestZetWordtOvergeslagenBijLeegVakje() {
//        pocket.getPocketOpPositie(5).setAantalStenen(0);
        Vakje pocket5 = eersteVakje.getVakjeOpPositie(5);
        pocket5.zet();
        assertThrows(IllegalArgumentException.class, () -> {
            pocket5.zet();
        });
    }

    @Test
    public void TestVolgendePocketKrijgtEenSteenBijZet() {
        Vakje pocket2 = eersteVakje.getVakjeOpPositie(2);
        Vakje pocket3 = eersteVakje.getVakjeOpPositie(3);
        int aantalStenenOpDrieVoorZet = pocket3.getAantalStenen();
        pocket2.zet();
        assertEquals(aantalStenenOpDrieVoorZet + 1, pocket3.getAantalStenen());
    }

    @ParameterizedTest
    @CsvSource({"1,0", "2,5", "5,5", "6,4"})
    public void TestAlleSteentjesWordenDoorgegevenBijZetZonderMancala(int pocketNumber, int steentjesNaZet) {
        Vakje gespeeldePocket = eersteVakje.getVakjeOpPositie(1);
        Vakje gecontroleerdePocket = eersteVakje.getVakjeOpPositie(pocketNumber);
        gespeeldePocket.zet();
        int aantalStenenInGecontroleerdePocket = gecontroleerdePocket.getAantalStenen();
        assertEquals(steentjesNaZet, aantalStenenInGecontroleerdePocket);
    }

    @Test
    public void TestPocketNumberZevenIsMancala() {
        Vakje vakjeZeven = eersteVakje.getVakjeOpPositie(7);
        assertTrue(vakjeZeven instanceof Mancala);
    }

    @Test
    public void TestPocketNummerVeertienIsMancala() {
        Vakje vakjeVeertien = eersteVakje.getVakjeOpPositie(14);
        assertTrue(vakjeVeertien instanceof Mancala);
    }


}

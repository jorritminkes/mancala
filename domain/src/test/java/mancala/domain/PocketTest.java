package mancala.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import org.junit.jupiter.api.Test;

public class PocketTest {
    private Pocket pocket;

    @BeforeEach
    public void setUp() {
        pocket = new Pocket();
    }

    @Test
    public void TestPocketInstanceExists() {
        assertNotNull(pocket, "Pocket should not be null");
    }

    @Test
    public void TestPocketHasFourBalls() {
        int aantalStenen = pocket.getAantalStenen();
        assertEquals(4, aantalStenen);
    }

    @Test
    public void TestPocketTwaalfBestaat() {
        Pocket pocketTwaalf = pocket.getPocketOpPositie(12);
        assertNotNull(pocketTwaalf);
    }

    @ParameterizedTest
    @CsvSource({"1,1", "2,2", "12,12", "13,1", "14,2"})
    public void TestPocketNumberKloptMetPositie(int positie, int verwachttePocketNumber) {
        int pocketNumber = pocket.getPocketOpPositie(positie).getPocketNumber();
        assertEquals(verwachttePocketNumber, pocketNumber);
    }



    @ParameterizedTest
    @CsvSource({"1,2", "2,3", "12,1", "13,2"})
    public void TestPocketNumberVanVolgendePocketMatcht(int pocketNumber, int verwachtteVolgendePocketNumber) {
        int volgendePocketNumber = pocket.getPocketOpPositie(pocketNumber).getVolgendePocket().getPocketNumber();
        assertEquals(verwachtteVolgendePocketNumber, volgendePocketNumber);
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,6,12,13})
    public void TestHebbenAllePocketsVierStenen(int pocketNumber) {
        int aantalStenen = pocket.getPocketOpPositie(pocketNumber).getAantalStenen();
        assertEquals(4, aantalStenen);
    }

    @Test
    public void TestLeegtEenZetZijnPocket() {
        Pocket pocket5 = pocket.getPocketOpPositie(5);
        pocket5.zet();
        int aantalStenen = pocket5.getAantalStenen();
        assertEquals(0, aantalStenen);
    }

    @Test
    public void TestZetWordtOvergeslagenBijLeegVakje() {
//        pocket.getPocketOpPositie(5).setAantalStenen(0);
        Pocket pocket5 = pocket.getPocketOpPositie(5);
        pocket5.zet();
        assertThrows(IllegalArgumentException.class, () -> {
            pocket.getPocketOpPositie(5).zet();
        });
    }

    @Test
    public void TestVolgendePocketKrijgtEenSteenBijZet() {
        Pocket pocket2 = pocket.getPocketOpPositie(2);
        Pocket pocket3 = pocket.getPocketOpPositie(3);
        int aantalStenenOpDrieVoorZet = pocket3.getAantalStenen();
        pocket2.zet();
        assertEquals(aantalStenenOpDrieVoorZet + 1, pocket3.getAantalStenen());
    }

    @ParameterizedTest
    @CsvSource({"1,0", "2,5", "5,5", "6,4"})
    public void TestAlleSteentjesWordenDoorgegevenBijZetZonderMancala(int pocketNumber, int steentjesNaZet) {
        Pocket gespeeldePocket = pocket.getPocketOpPositie(1);
        Pocket gecontroleerdePocket = pocket.getPocketOpPositie(pocketNumber);
        gespeeldePocket.zet();
        int aantalStenenInGecontroleerdePocket = gecontroleerdePocket.getAantalStenen();
        assertEquals(steentjesNaZet, aantalStenenInGecontroleerdePocket);
    }

    public void TestPocketNumberZevenIsMancala() {

    }


}

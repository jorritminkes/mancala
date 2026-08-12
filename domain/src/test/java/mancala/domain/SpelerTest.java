package mancala.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import org.junit.jupiter.api.Test;

public class SpelerTest {

    @Test
    public void TestSpelerEenIsAanZetBijBeginspelerEen() {
        Speler speler1 = new Speler(1);
        assertTrue(speler1.isAanZet());
    }

    @Test
    public void TestSpelerTweeIsAanZetBijBeginspelerTwee() {
        Speler speler1 = new Speler(2);
        Speler speler2 = speler1.getTegenstander();
        assertTrue(speler2.isAanZet());
    }

    @Test
    public void TestSpelerEenIsAanZetBijGeenBeginspelerOpgeven() {
        Speler speler1 = new Speler();
        assertTrue(speler1.isAanZet());
    }

    @Test
    public void TestDubbeleBeurtSwitchKomtBijOorspronkelijke() {
        Speler speler = new Speler(1);
        speler.switchBeurt();
        speler.switchBeurt();
        assertTrue(speler.isAanZet());
    }

}

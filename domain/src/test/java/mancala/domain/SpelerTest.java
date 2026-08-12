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
        assertEquals(1, speler1.getSpelerAanZet());
    }

    @Test
    public void TestSpelerTweeIsAanZetBijBeginspelerTwee() {
        Speler speler2 = new Speler(2);
        assertEquals(2, speler2.getSpelerAanZet());
    }

    @Test
    public void TestSpelerEenIsAanZetBijGeenBeginspelerOpgeven() {
        Speler speler1 = new Speler();
        assertEquals(1, speler1.getSpelerAanZet());
    }

    @Test
    public void TestDubbeleBeurtSwitchKomtBijOorspronkelijke() {
        Speler speler = new Speler(1);
        speler.switchBeurt();
        speler.switchBeurt();
        assertEquals(1, speler.getSpelerAanZet());
    }

}

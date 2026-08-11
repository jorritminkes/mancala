package mancala.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BeurtTest {

    @Test
    public void TestSwitchtBeurtCorrectBeginspelerEen() {
        Beurt beurt = new Beurt(1);
        beurt.switchBeurt();
        assertEquals(2, beurt.getBeurt());
    }

    @Test
    public void TestSwitchtBeurtCorrectBeginspelerTwee() {
        Beurt beurt = new Beurt(2);
        beurt.switchBeurt();
        assertEquals(1, beurt.getBeurt());
    }
}

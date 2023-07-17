package bgu.spl.mics.application.passiveObjects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EwokTest {

    private Ewok ewok;

    @Before
    public void setUp() throws Exception {
        ewok=new Ewok (1);
    }

    @After
    public void tearDown() throws Exception {
        ewok=null;
    }

    @Test
    public void acquire() {
        ewok.acquire();
        assertFalse(ewok.getAvailability());
    }

    @Test
    public void release() {
        ewok.release();
        assertEquals (true,ewok.getAvailability());
    }
}
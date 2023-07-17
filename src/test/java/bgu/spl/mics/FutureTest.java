package bgu.spl.mics;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class FutureTest {

    private Future<String> future;

    @Before
    public void setUp() throws Exception {
        future = new Future<>();
    }

    @After
    public void tearDown() throws Exception {
        future=null;
    }

    @Test
    public void get() throws InterruptedException {
        assertFalse(future.isDone());
        future.resolve("");
        String answer=future.get();
        assertTrue(future.isDone());
        assertEquals(answer,"");
    }

    @Test
    public void resolve() throws InterruptedException {
        String str = "someResult";
        future.resolve(str);
        assertTrue(future.isDone());
        assertTrue(str.equals(future.get()));
    }

    @Test
    public void isDone() {
        assertFalse (future.isDone());
        future.resolve("");
        assertTrue(future.isDone());
    }

    @Test
    public void testGet() throws InterruptedException {
        assertFalse(future.isDone());
        future.get(100, TimeUnit.MILLISECONDS);
        assertFalse(future.isDone());
        future.resolve("foo");
        assertEquals(future.get(100,TimeUnit.MILLISECONDS),"foo");
    }
}
package com.example.gameoflife;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

public class PetridishTest {
    @Test
    void testStableBlockStops() throws Exception {
        Petridish p = new Petridish();
        p.setCellState(10,10,true);
        p.setCellState(10,11,true);
        p.setCellState(11,10,true);
        p.setCellState(11,11,true);
        String before = p.getBoardHash();
        AtomicInteger iter = new AtomicInteger();
        p.iterateLife(() -> iter.incrementAndGet() < 10);
        assertEquals(before, p.getBoardHash());
        assertTrue(iter.get() <= 2);
    }

    @Test
    void testBlinkerStops() throws Exception {
        Petridish p = new Petridish();
        p.setCellState(5,5,true);
        p.setCellState(5,6,true);
        p.setCellState(5,7,true);
        String before = p.getBoardHash();
        AtomicInteger iter = new AtomicInteger();
        p.iterateLife(() -> iter.incrementAndGet() < 10);
        assertEquals(before, p.getBoardHash());
        assertEquals(2, iter.get());
    }
}

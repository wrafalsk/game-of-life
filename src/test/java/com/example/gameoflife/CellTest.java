package com.example.gameoflife;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellTest {
    @Test
    void testMakeAlive() {
        Cell cell = new Cell();
        cell.makeAlive();
        assertEquals(1, cell.getAlive());
        assertEquals('@', cell.getCurrentShape());
        assertEquals(Cell.SPECIES_NORMAL, cell.getSpecies());
    }

    @Test
    void testMakeDead() {
        Cell cell = new Cell();
        cell.makeAlive();
        cell.makeDead();
        assertEquals(0, cell.getAlive());
        assertEquals('-', cell.getCurrentShape());
    }

    @Test
    void testSpeciesShapes() {
        Cell cell = new Cell();
        cell.setSpecies(Cell.SPECIES_COEXISTENT);
        cell.makeAlive();
        assertEquals('C', cell.getCurrentShape());
        cell.setSpecies(Cell.SPECIES_AGGRESSIVE);
        assertEquals('X', cell.getCurrentShape());
    }
}

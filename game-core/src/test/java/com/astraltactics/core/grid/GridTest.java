package com.astraltactics.core.grid;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GridTest {
    @Test void acceptsAllFourCorners() {
        var grid = new Grid(10, 10);
        for (var cell : new Cell[]{new Cell(0, 0), new Cell(0, 9), new Cell(9, 0), new Cell(9, 9)}) {
            assertTrue(grid.contains(cell));
        }
    }
    @Test void rejectsOutsideCoordinates() {
        var grid = new Grid(10, 10);
        for (var cell : new Cell[]{new Cell(-1, 0), new Cell(0, -1), new Cell(10, 0), new Cell(0, 10)}) {
            assertFalse(grid.contains(cell));
        }
        assertFalse(grid.contains(null));
    }
    @Test void rejectsEmptyDimensions() {
        assertThrows(IllegalArgumentException.class, () -> new Grid(0, 10));
        assertThrows(IllegalArgumentException.class, () -> new Grid(10, -1));
    }
}

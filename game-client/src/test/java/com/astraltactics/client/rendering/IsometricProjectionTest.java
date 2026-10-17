package com.astraltactics.client.rendering;

import com.astraltactics.core.grid.Cell;
import com.astraltactics.core.grid.Grid;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IsometricProjectionTest {
    private final Grid grid = new Grid(10, 10);
    private final IsometricProjection projection = new IsometricProjection(96, 48);

    @Test void mapsKnownCoordinates() {
        assertEquals(new IsometricProjection.Point(48, -72), projection.center(new Cell(2, 1)));
    }
    @Test void picksAllCentersAndInteriorPoints() {
        for (int r = 0; r < 10; r++) for (int c = 0; c < 10; c++) {
            var cell = new Cell(c, r);
            var point = projection.center(cell);
            assertEquals(cell, projection.cellAt(point.x(), point.y(), grid).orElseThrow());
            assertEquals(cell, projection.cellAt(point.x() + 12, point.y() + 6, grid).orElseThrow());
        }
    }
    @Test void usesHalfOpenBoundaries() {
        // Grid coordinate (.5, 0), common edge between (0,0) and (1,0).
        assertEquals(new Cell(1, 0), projection.cellAt(24, -12, grid).orElseThrow());
        assertEquals(new Cell(0, 0), projection.cellAt(24 - 0.001, -12, grid).orElseThrow());
        assertEquals(new Cell(0, 0), projection.cellAt(-24, 12, grid).orElseThrow());
        assertTrue(projection.cellAt(-24 - 0.001, 12, grid).isEmpty());
        assertTrue(projection.cellAt(456, -228, grid).isEmpty()); // column 9.5
    }
    @Test void rejectsOutsideAndNonFinitePositions() {
        assertTrue(projection.cellAt(0, 25, grid).isEmpty());
        assertTrue(projection.cellAt(1000000, -1000000, grid).isEmpty());
        assertTrue(projection.cellAt(Double.NaN, 0, grid).isEmpty());
        assertTrue(projection.cellAt(0, Double.POSITIVE_INFINITY, grid).isEmpty());
    }
    @Test void rejectsInvalidTileDimensions() {
        assertThrows(IllegalArgumentException.class, () -> new IsometricProjection(0, 48));
        assertThrows(IllegalArgumentException.class, () -> new IsometricProjection(96, Double.NaN));
    }
}

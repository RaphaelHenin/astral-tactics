package com.astraltactics.client.input;

import com.astraltactics.core.grid.Cell;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GridSelectionTest {
    @Test void hoverDoesNotSelectUntilClick() {
        var state = new GridSelection();
        var cell = new Cell(3, 4);
        state.hover(cell);
        assertTrue(state.selected().isEmpty());
        state.selectHovered();
        assertEquals(cell, state.selected().orElseThrow());
    }
    @Test void leavingMapAndClickingOutsidePreservesSelection() {
        var state = new GridSelection();
        var cell = new Cell(9, 9);
        state.hover(cell);
        state.selectHovered();
        state.hover(null);
        state.selectHovered();
        assertTrue(state.hovered().isEmpty());
        assertTrue(state.selected().isEmpty());
    }
}

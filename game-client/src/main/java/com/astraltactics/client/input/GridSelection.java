package com.astraltactics.client.input;

import com.astraltactics.core.grid.Cell;
import java.util.Optional;

/** UI state, not combat state. Clicking outside the map preserves selection. */
public final class GridSelection {
    private Cell hovered = null;
    private Cell selected = null;

    public void hover(Cell cell) {
        hovered = cell;
    }

    public void selectHovered() {
        hovered().ifPresentOrElse(cell -> selected = cell, () -> selected = null);
    }

    public Optional<Cell> hovered() {
        return Optional.ofNullable(hovered);
    }

    public Optional<Cell> selected() {
        return Optional.ofNullable(selected);
    }
}

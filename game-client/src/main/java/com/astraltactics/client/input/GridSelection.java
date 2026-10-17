package com.astraltactics.client.input;

import com.astraltactics.core.grid.Cell;
import java.util.Optional;

/** UI state, not combat state. Clicking outside the map preserves selection. */
public final class GridSelection {
    private Optional<Cell> hovered = Optional.empty();
    private Optional<Cell> selected = Optional.empty();

    public void hover(Optional<Cell> cell) { hovered = cell; }
    public void selectHovered() { hovered.ifPresent(cell -> selected = Optional.of(cell)); }
    public Optional<Cell> hovered() { return hovered; }
    public Optional<Cell> selected() { return selected; }
}

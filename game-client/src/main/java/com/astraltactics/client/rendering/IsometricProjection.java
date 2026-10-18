package com.astraltactics.client.rendering;

import com.astraltactics.core.grid.Cell;
import com.astraltactics.core.grid.Grid;

/** Y points upwards in world space; increasing grid rows move down-left. */
public record IsometricProjection(double tileWidth, double tileHeight) {
    public IsometricProjection {
        if (!Double.isFinite(tileWidth) || !Double.isFinite(tileHeight)
                || tileWidth <= 0 || tileHeight <= 0) {
            throw new IllegalArgumentException("Tile dimensions must be finite and positive");
        }
    }

    public record Point(double x, double y) { }

    public Point center(Cell cell) {
        return new Point((cell.column() - (double) cell.row()) * tileWidth / 2,
                -(cell.column() + (double) cell.row()) * tileHeight / 2);
    }

    /**
     * Each cell covers [column-.5,column+.5) × [row-.5,row+.5).
     */
    public Cell cellAt(double x, double y, Grid grid) {
        if (!Double.isFinite(x) || !Double.isFinite(y)) return null;
        double column = x / tileWidth - y / tileHeight;
        double row = -x / tileWidth - y / tileHeight;
        // Check before conversion: no truncation or integer overflow outside the map.
        if (column < -0.5 || column >= grid.columns() - 0.5
                || row < -0.5 || row >= grid.rows() - 0.5) return null;
        return new Cell((int) Math.floor(column + 0.5),
                (int) Math.floor(row + 0.5));
    }
}

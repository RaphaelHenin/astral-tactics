package com.astraltactics.core.grid;

public record Grid(int columns, int rows) {
    public Grid {
        if (columns <= 0 || rows <= 0) {
            throw new IllegalArgumentException("Grid dimensions must be positive");
        }
    }

    public boolean contains(Cell cell) {
        return cell != null && cell.column() >= 0 && cell.column() < columns
                && cell.row() >= 0 && cell.row() < rows;
    }
}

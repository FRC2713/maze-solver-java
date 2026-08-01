package com.frc2713.mazesolver;

/**
 * A concrete {@link Maze} backed by an N/S/E/W bitmask grid (a set bit means
 * that side of the cell is OPEN). Start is the top-left cell {@code (0, 0)};
 * goal is the bottom-right cell {@code (rows - 1, cols - 1)} — the convention
 * the whole maze module uses.
 *
 * <p>This is the entry point lesson snippets use: {@code new GridMaze(grid)}
 * turns a raw {@code int[][]} into a maze you can hand a {@link Robot}.
 */
public class GridMaze implements Maze {

    private final int[][] grid;
    private final int rows;
    private final int cols;
    private final int goalRow;
    private final int goalCol;
    private final GridRobot bot;

    public GridMaze(int[][] grid) {
        this.grid = grid;
        this.rows = grid.length;
        this.cols = grid.length == 0 ? 0 : grid[0].length;
        this.goalRow = rows - 1;
        this.goalCol = cols - 1;
        this.bot = new GridRobot(this);
    }

    int mask(int row, int col) {
        return grid[row][col];
    }

    @Override
    public int rows() {
        return rows;
    }

    @Override
    public int cols() {
        return cols;
    }

    @Override
    public Cell cellAt(int row, int col) {
        return new GridCell(this, row, col);
    }

    @Override
    public boolean isGoalCell(int row, int col) {
        return row == goalRow && col == goalCol;
    }

    @Override
    public Robot robot() {
        return bot;
    }
}

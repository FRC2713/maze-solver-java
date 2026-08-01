package com.frc2713.mazesolver;

/** One square of a {@link GridMaze}. Wall flags come from the cell's bitmask. */
final class GridCell implements Cell {

    private final GridMaze maze;
    private final int row;
    private final int col;

    GridCell(GridMaze maze, int row, int col) {
        this.maze = maze;
        this.row = row;
        this.col = col;
    }

    private int mask() {
        return maze.mask(row, col);
    }

    @Override
    public int row() {
        return row;
    }

    @Override
    public int col() {
        return col;
    }

    @Override
    public boolean wall(Direction dir) {
        // A set bit means that side is OPEN, so a wall is the bit being clear.
        return (mask() & dir.bit()) == 0;
    }

    @Override
    public boolean isStart() {
        return row == 0 && col == 0;
    }

    @Override
    public boolean isGoal() {
        return maze.isGoalCell(row, col);
    }
}

package com.frc2713.mazesolver;

/**
 * A rectangular grid maze. Build one from an N/S/E/W bitmask grid with
 * {@link GridMaze}. The start is always the top-left cell {@code (0, 0)} and the
 * goal the bottom-right cell {@code (rows - 1, cols - 1)}.
 */
public interface Maze {

    /** Number of rows. */
    int rows();

    /** Number of columns. */
    int cols();

    /** The cell at the given row and column. */
    Cell cellAt(int row, int col);

    /** Is the given row/column the goal cell (bottom-right)? */
    boolean isGoalCell(int row, int col);

    /** A fresh {@link Robot} standing on the start cell of this maze. */
    Robot robot();
}

package com.frc2713.mazesolver;

/**
 * A maze the student can explore.
 *
 * <p>A {@code Maze} is built from the raw {@code int[][]} bitmask grid the
 * lesson site produces (see {@code CONTRACT.md}), but it hides that entirely:
 * once you have a {@code Maze} you work with {@link Cell}s and a {@link Robot},
 * never bitmasks.
 *
 * <p>The library must provide a concrete implementation constructable from an
 * {@code int[][]} grid so the site can build one — see {@code CONTRACT.md} for
 * the agreed entry point.
 */
public interface Maze {

    /** Number of rows in the maze. */
    int rows();

    /** Number of columns in the maze. */
    int cols();

    /**
     * The cell at the given position.
     *
     * @throws IndexOutOfBoundsException if the position is off the grid
     */
    Cell cellAt(int row, int col);

    /** A fresh robot standing on the start cell (top-left), ready to solve. */
    Robot robot();

    /** {@code true} if the given position is the goal cell (bottom-right). */
    boolean isGoalCell(int row, int col);
}

package com.frc2713.mazesolver;

/**
 * One square of the maze.
 *
 * <p>This is the beginner-facing view of a cell: instead of asking "what is the
 * bitmask of this cell?", you ask plain yes/no questions like "is there a wall
 * to my right?". Implementations (see the library README) translate the
 * underlying N/S/E/W bitmask into these methods so the students using this
 * library never touch a bitmask.
 *
 * <p>Directions are screen-relative, the way the maze is drawn: <b>up</b> is
 * toward row 0, <b>down</b> is toward the last row, <b>left</b> is toward
 * column 0, <b>right</b> is toward the last column.
 */
public interface Cell {

    /** The row (0-based) this cell sits in; row 0 is the top. */
    int row();

    /** The column (0-based) this cell sits in; column 0 is the left edge. */
    int col();

    /** {@code true} if a wall blocks movement off the top of this cell. */
    boolean wallUp();

    /** {@code true} if a wall blocks movement off the bottom of this cell. */
    boolean wallDown();

    /** {@code true} if a wall blocks movement off the left of this cell. */
    boolean wallLeft();

    /** {@code true} if a wall blocks movement off the right of this cell. */
    boolean wallRight();

    /** {@code true} if this is the maze's start cell (top-left). */
    boolean isStart();

    /** {@code true} if this is the maze's goal cell (bottom-right). */
    boolean isGoal();
}

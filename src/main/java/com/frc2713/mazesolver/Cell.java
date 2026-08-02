package com.frc2713.mazesolver;

/**
 * One square of a {@link Maze}. A cell knows where it sits and which of its four
 * sides are walls — the same questions a robot standing on it can ask, without
 * any bitmask arithmetic.
 */
public interface Cell {

    /** This cell's row (0 is the top row). */
    int row();

    /** This cell's column (0 is the left column). */
    int col();

    /** Is the given side of this cell a wall (i.e. blocked, not carved open)? */
    boolean wall(Direction dir);

    /** Is this the start cell (top-left)? */
    boolean isStart();

    /** Is this the goal cell (bottom-right)? */
    boolean isGoal();
}

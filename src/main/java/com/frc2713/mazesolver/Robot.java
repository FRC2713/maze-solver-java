package com.frc2713.mazesolver;

/**
 * A robot that walks through the maze, one cell at a time.
 *
 * <p>This is the main tool a student uses to write a solving algorithm. The
 * robot always sits in exactly one cell (its position). You ask whether it
 * <em>can</em> move a direction, and if so you tell it to move — for example:
 *
 * <pre>{@code
 * while (!robot.atGoal()) {
 *     if (robot.canMoveRight())      robot.moveRight();
 *     else if (robot.canMoveDown())  robot.moveDown();
 *     else                           robot.moveUp();
 * }
 * }</pre>
 *
 * <p>Directions are screen-relative (see {@link Cell}). A robot never needs to
 * know about bitmasks — it only knows where it is and which ways it can go.
 */
public interface Robot {

    /** The robot's current row (0-based); row 0 is the top. */
    int row();

    /** The robot's current column (0-based); column 0 is the left edge. */
    int col();

    /** The cell the robot is currently standing on. */
    Cell cell();

    /** {@code true} if there is no wall above and a cell to move into. */
    boolean canMoveUp();

    /** {@code true} if there is no wall below and a cell to move into. */
    boolean canMoveDown();

    /** {@code true} if there is no wall to the left and a cell to move into. */
    boolean canMoveLeft();

    /** {@code true} if there is no wall to the right and a cell to move into. */
    boolean canMoveRight();

    /**
     * Step one cell up. Implementations should throw
     * {@link IllegalStateException} if {@link #canMoveUp()} is {@code false},
     * so a student learns to check before moving.
     */
    void moveUp();

    /** Step one cell down. See {@link #moveUp()} for the blocked-move rule. */
    void moveDown();

    /** Step one cell left. See {@link #moveUp()} for the blocked-move rule. */
    void moveLeft();

    /** Step one cell right. See {@link #moveUp()} for the blocked-move rule. */
    void moveRight();

    /** {@code true} once the robot reaches the goal cell (bottom-right). */
    boolean atGoal();

    /**
     * Every cell the robot has stood on, in order, as {@code {row, col}} pairs —
     * starting with the start cell and ending with its current cell. Handy for
     * drawing the path the algorithm took.
     */
    int[][] trail();
}

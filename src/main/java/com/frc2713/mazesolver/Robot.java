package com.frc2713.mazesolver;

/**
 * A robot that walks a {@link Maze} one cell at a time. It starts on the maze's
 * start cell and knows only what's around it — never a bird's-eye view of the
 * whole maze.
 *
 * <p>The robot has two abilities, mirroring a real one: it can <em>sense</em> a
 * wall on any side ({@link #readWallSensor}) and it can <em>drive</em> one cell
 * in a direction ({@link #drive}). It also remembers one fact about itself —
 * which way it last drove, its {@link #facing()} — because a robot that just
 * moved up is now facing up.
 */
public interface Robot {

    /**
     * Reads the wall sensor on one side of the robot's current cell.
     *
     * @return {@code true} if that side is a wall (the robot cannot drive that
     *         way), {@code false} if it is open
     */
    boolean readWallSensor(Direction dir);

    /**
     * Drives one cell in the given direction. If that side is a wall the robot
     * stays put and nothing happens; otherwise it moves and its
     * {@link #facing()} becomes {@code dir}.
     */
    void drive(Direction dir);

    /**
     * The direction the robot last drove — the one fact it remembers about
     * itself. Before it has moved, this is its initial heading.
     */
    Direction facing();

    /** Is the robot standing on the goal cell? */
    boolean atGoal();

    /** The robot's current row (0 is the top row). */
    int row();

    /** The robot's current column (0 is the left column). */
    int col();

    /** The {@link Cell} the robot is currently standing on. */
    Cell cell();

    /**
     * The trail of every cell the robot has stood on, in order, as
     * {@code [row, col]} pairs starting with the start cell. A drive blocked by
     * a wall adds no entry.
     */
    int[][] trail();
}

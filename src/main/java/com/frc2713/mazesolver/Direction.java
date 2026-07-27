package com.frc2713.mazesolver;

/**
 * The N/S/E/W bitmask constants used by {@link MazeSolver}. Values match the
 * {@code maze-generator} npm package's convention: N=1, S=2, E=4, W=8.
 */
public final class Direction {

    public static final int NORTH = 1;
    public static final int SOUTH = 2;
    public static final int EAST = 4;
    public static final int WEST = 8;

    private Direction() {
    }
}

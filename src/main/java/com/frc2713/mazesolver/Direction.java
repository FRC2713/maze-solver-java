package com.frc2713.mazesolver;

/**
 * One of the four ways a {@link Robot} can face or move, named the way a student
 * looking at the maze on screen would name them: {@code UP}, {@code DOWN},
 * {@code LEFT}, {@code RIGHT}.
 *
 * <p>Each direction carries the single bit it occupies in a cell's N/S/E/W
 * bitmask (the maze wire format shared with the {@code maze-generator} npm
 * package): {@code UP == 1}, {@code DOWN == 2}, {@code RIGHT == 4},
 * {@code LEFT == 8}. On screen those are north/south/east/west respectively — a
 * cell open to the north and east only is {@code UP.bit() | RIGHT.bit() == 5}.
 * See {@link MazeSolver} for the maze format and {@code CONTRACT.md}.
 */
public enum Direction {

    UP(1),
    DOWN(2),
    RIGHT(4),
    LEFT(8);

    private final int bit;

    Direction(int bit) {
        this.bit = bit;
    }

    /**
     * This direction's bit in a cell's open-sides bitmask
     * ({@code UP=1, DOWN=2, RIGHT=4, LEFT=8}).
     */
    public int bit() {
        return bit;
    }

    /** The opposite heading — where you'd point after turning all the way around. */
    public Direction opposite() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            default:
                return LEFT;
        }
    }

    /** The heading 90° clockwise from this one (UP→RIGHT→DOWN→LEFT→UP). */
    public Direction clockwise() {
        switch (this) {
            case UP:
                return RIGHT;
            case RIGHT:
                return DOWN;
            case DOWN:
                return LEFT;
            default:
                return UP;
        }
    }

    /** The heading 90° counter-clockwise from this one (UP→LEFT→DOWN→RIGHT→UP). */
    public Direction counterClockwise() {
        switch (this) {
            case UP:
                return LEFT;
            case LEFT:
                return DOWN;
            case DOWN:
                return RIGHT;
            default:
                return UP;
        }
    }
}

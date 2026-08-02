package com.frc2713.mazesolver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectionTest {

    @Test
    void bitmaskValuesMatchMazeGeneratorConvention() {
        assertEquals(1, Direction.UP.bit());
        assertEquals(2, Direction.DOWN.bit());
        assertEquals(4, Direction.RIGHT.bit());
        assertEquals(8, Direction.LEFT.bit());
    }

    @Test
    void directionsAreDistinctBits() {
        int all = Direction.UP.bit() | Direction.DOWN.bit()
                | Direction.RIGHT.bit() | Direction.LEFT.bit();
        assertEquals(15, all);
    }

    @Test
    void oppositeFlipsTheHeading() {
        assertEquals(Direction.DOWN, Direction.UP.opposite());
        assertEquals(Direction.UP, Direction.DOWN.opposite());
        assertEquals(Direction.RIGHT, Direction.LEFT.opposite());
        assertEquals(Direction.LEFT, Direction.RIGHT.opposite());
    }

    @Test
    void turnsRotateThroughAllFour() {
        assertEquals(Direction.RIGHT, Direction.UP.clockwise());
        assertEquals(Direction.DOWN, Direction.RIGHT.clockwise());
        assertEquals(Direction.LEFT, Direction.DOWN.clockwise());
        assertEquals(Direction.UP, Direction.LEFT.clockwise());

        for (Direction d : Direction.values()) {
            assertEquals(d, d.clockwise().counterClockwise());
        }
    }
}

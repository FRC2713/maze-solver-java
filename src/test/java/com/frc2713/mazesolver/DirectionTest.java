package com.frc2713.mazesolver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectionTest {

    @Test
    void bitmaskValuesMatchMazeGeneratorConvention() {
        assertEquals(1, Direction.NORTH);
        assertEquals(2, Direction.SOUTH);
        assertEquals(4, Direction.EAST);
        assertEquals(8, Direction.WEST);
    }

    @Test
    void directionsAreDistinctBits() {
        int all = Direction.NORTH | Direction.SOUTH | Direction.EAST | Direction.WEST;
        assertEquals(15, all);
    }
}

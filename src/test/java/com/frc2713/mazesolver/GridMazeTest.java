package com.frc2713.mazesolver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GridMazeTest {

    // A 3x3 maze with every interior wall carved open (fully connected). Bits:
    // UP=1, DOWN=2, RIGHT=4, LEFT=8. Each cell is open toward every neighbor it
    // has, so adjacent cells always agree.
    private static int[][] openGrid() {
        return new int[][] {
            { 6, 14, 10 },
            { 7, 15, 11 },
            { 5, 13, 9 },
        };
    }

    @Test
    void robotSensesWallsAndOpenings() {
        Robot robot = new GridMaze(openGrid()).robot();
        // Top-left corner: walls up and left, openings down and right.
        assertTrue(robot.readWallSensor(Direction.UP));
        assertTrue(robot.readWallSensor(Direction.LEFT));
        assertFalse(robot.readWallSensor(Direction.DOWN));
        assertFalse(robot.readWallSensor(Direction.RIGHT));
    }

    @Test
    void driveMovesAndUpdatesFacing() {
        Robot robot = new GridMaze(openGrid()).robot();
        assertEquals(Direction.RIGHT, robot.facing()); // initial heading

        robot.drive(Direction.DOWN);
        assertEquals(1, robot.row());
        assertEquals(0, robot.col());
        assertEquals(Direction.DOWN, robot.facing());

        robot.drive(Direction.RIGHT);
        assertEquals(1, robot.row());
        assertEquals(1, robot.col());
        assertEquals(Direction.RIGHT, robot.facing());
    }

    @Test
    void driveIntoWallDoesNothing() {
        Robot robot = new GridMaze(openGrid()).robot();
        robot.drive(Direction.UP); // walled — should be a no-op
        assertEquals(0, robot.row());
        assertEquals(0, robot.col());
        assertEquals(1, robot.trail().length); // no new trail entry
        assertEquals(Direction.RIGHT, robot.facing()); // heading unchanged
    }

    @Test
    void robotReachesGoalAndRecordsTrail() {
        Robot robot = new GridMaze(openGrid()).robot();
        robot.drive(Direction.DOWN);
        robot.drive(Direction.DOWN);
        robot.drive(Direction.RIGHT);
        robot.drive(Direction.RIGHT);
        assertTrue(robot.atGoal());
        assertEquals(5, robot.trail().length);
        assertArrayEquals(new int[] { 0, 0 }, robot.trail()[0]);
        assertArrayEquals(new int[] { 2, 2 }, robot.trail()[4]);
    }

    @Test
    void solverFindsShortestPath() {
        int[][] path = new DefaultMazeSolver().solve(openGrid());
        assertArrayEquals(new int[] { 0, 0 }, path[0]);
        assertArrayEquals(new int[] { 2, 2 }, path[path.length - 1]);
        assertEquals(5, path.length); // 4 steps is the shortest across a 3x3
        // Every consecutive pair is an adjacent, carved move.
        int[][] grid = openGrid();
        for (int i = 1; i < path.length; i++) {
            int dr = path[i][0] - path[i - 1][0];
            int dc = path[i][1] - path[i - 1][1];
            assertEquals(1, Math.abs(dr) + Math.abs(dc), "steps must be to an adjacent cell");
        }
    }

    @Test
    void solverReturnsEmptyWhenGoalUnreachable() {
        // Start cell sealed off (no open sides); goal can't be reached.
        int[][] sealed = {
            { 0, 8 },
            { 0, 1 },
        };
        assertEquals(0, new DefaultMazeSolver().solve(sealed).length);
    }

    @Test
    void solverRejectsMalformedMazes() {
        assertThrows(IllegalArgumentException.class, () -> new DefaultMazeSolver().solve(null));
        assertThrows(IllegalArgumentException.class, () -> new DefaultMazeSolver().solve(new int[0][]));
        assertThrows(IllegalArgumentException.class,
            () -> new DefaultMazeSolver().solve(new int[][] { { 1, 2 }, { 3 } }));
    }
}

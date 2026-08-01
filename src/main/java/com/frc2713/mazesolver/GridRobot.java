package com.frc2713.mazesolver;

import java.util.ArrayList;

/**
 * A {@link Robot} that walks a {@link GridMaze}. It records a trail: every cell
 * it has stood on, start first. A drive blocked by a wall does nothing and adds
 * no trail entry, so an algorithm that drives into a wall leaves a trail that
 * simply stays put there.
 *
 * <p>The robot remembers one fact about itself — {@link #facing()} — and updates
 * it every time it actually moves, because a robot that just drove up is now
 * facing up. It starts facing {@link Direction#RIGHT}.
 */
final class GridRobot implements Robot {

    private final GridMaze maze;
    private int row = 0;
    private int col = 0;
    private Direction facing = Direction.RIGHT;
    private final ArrayList<int[]> path = new ArrayList<>();

    GridRobot(GridMaze maze) {
        this.maze = maze;
        path.add(new int[] { row, col });
    }

    private int mask() {
        return maze.mask(row, col);
    }

    @Override
    public boolean readWallSensor(Direction dir) {
        // A set bit means that side is OPEN, so a wall is the bit being clear.
        return (mask() & dir.bit()) == 0;
    }

    @Override
    public void drive(Direction dir) {
        if (readWallSensor(dir)) {
            return; // wall ahead — the drivetrain can't push through it
        }
        switch (dir) {
            case UP:
                row -= 1;
                break;
            case DOWN:
                row += 1;
                break;
            case LEFT:
                col -= 1;
                break;
            case RIGHT:
                col += 1;
                break;
        }
        facing = dir;
        path.add(new int[] { row, col });
    }

    @Override
    public Direction facing() {
        return facing;
    }

    @Override
    public boolean atGoal() {
        return maze.isGoalCell(row, col);
    }

    @Override
    public int row() {
        return row;
    }

    @Override
    public int col() {
        return col;
    }

    @Override
    public Cell cell() {
        return maze.cellAt(row, col);
    }

    @Override
    public int[][] trail() {
        return path.toArray(new int[0][]);
    }
}

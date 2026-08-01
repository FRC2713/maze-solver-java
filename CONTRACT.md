# API contract

This is the contract shared between this library and the `software_training`
lesson site — both sides need to agree on it, since lesson snippets and this
library speak the same maze format.

## Maze representation

A maze is `int[][] maze`, indexed `maze[row][col]`. Every row has the same
length. Each cell's value is a bitmask of which sides are **open** (carved
through, i.e. passable), using the same bit values as the `maze-generator`
npm package the lesson site already uses to build mazes. The bits are named
the way they read on screen (`UP`/`DOWN`/`LEFT`/`RIGHT`), which correspond to
the generator's compass directions:

| Direction | Compass | Bit  | Value |
|-----------|---------|------|-------|
| UP        | north   | 0001 | 1     |
| DOWN      | south   | 0010 | 2     |
| RIGHT     | east    | 0100 | 4     |
| LEFT      | west    | 1000 | 8     |

A cell open upward and to the right only is `UP.bit() \| RIGHT.bit() == 5`. A
cell open on all four sides is `15`. `com.frc2713.mazesolver.Direction` is an
`enum { UP, DOWN, LEFT, RIGHT }`; each constant's `bit()` returns its value
above.

The maze is assumed internally consistent: if `maze[r][c]` has `RIGHT` set,
then `maze[r][c + 1]` has `LEFT` set, and so on. `solve` is not required to
validate this beyond basic shape checks.

Start is always the top-left cell (`[0][0]`); the goal is always the
bottom-right cell (`[rows - 1][cols - 1]`).

## Public API

```java
package com.frc2713.mazesolver;

public interface MazeSolver {
    int[][] solve(int[][] maze);
}
```

`solve` returns the path from start to goal as an ordered array of
`{row, col}` pairs, starting with `{0, 0}` and ending at the goal cell, or
`new int[0][]` if no path exists. It throws `IllegalArgumentException` for a
null, empty, or non-rectangular `maze`. `com.frc2713.mazesolver.DefaultMazeSolver`
is the shipped implementation (a breadth-first search, so the path is shortest).

### Interactive API (used by the lesson playground)

Lessons don't call `solve` — students write the algorithm themselves against a
robot they drive one cell at a time. That surface is also part of this contract:

```java
Maze maze = new GridMaze(grid);   // GridMaze is the concrete Maze
Robot robot = maze.robot();       // a robot on the start cell

robot.readWallSensor(Direction dir); // true if that side is a wall
robot.drive(Direction dir);          // move one cell if open; sets facing()
robot.facing();                      // Direction it last drove (starts RIGHT)
robot.atGoal();                      // on the bottom-right cell?
robot.row(); robot.col(); robot.cell();
robot.trail();                       // int[][] of [row,col] stood on, start first
```

`Direction` also offers `opposite()`, `clockwise()`, and `counterClockwise()`
so a wall-follower can be written relative to `facing()`. A `drive` into a wall
is a no-op (no move, no trail entry, `facing()` unchanged). The lesson site
interpolates a maze into a harness, splices in the student's method, drives the
robot, and animates `trail()` back — so the shapes of `GridMaze`, `Robot`,
`Cell`, and `Direction` are shared surface, not internal detail.

## Changing this contract

If either side (this library or the lesson site) needs the shape of `solve`,
the bitmask values, or the start/goal convention to change, update this file
first and treat it as the single source of truth — the site's
`maze-generator` usage and this library's `Direction` constants both need to
move together.

# API contract

This is the contract shared between this library and the `software_training`
lesson site — both sides need to agree on it, since lesson snippets and this
library speak the same maze format.

There are two layers, and keeping them separate is the whole point:

- **The wire format** — a raw `int[][]` bitmask — is how a maze crosses from the
  site (which generates mazes in JavaScript) into Java. It is an implementation
  detail; students never see it.
- **The student-facing API** — `Maze`, `Cell`, `Robot`, `Direction`, and the
  concrete `GridMaze` — is what students write code against. It is bitmask-free
  by design.

## Wire format (implementer-facing)

A maze arrives as `int[][] maze`, indexed `maze[row][col]`. Every row has the
same length. Each cell's value is a bitmask of which sides are **open** (carved
through, i.e. passable), using the same bit values as the `maze-generator` npm
package the lesson site uses to build mazes. The bits are named the way they
read on screen (`UP`/`DOWN`/`LEFT`/`RIGHT`), which correspond to the generator's
compass directions:

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
then `maze[r][c + 1]` has `LEFT` set, and so on. Implementations need not
validate this beyond basic shape checks.

Start is always the top-left cell (`[0][0]`); the goal is always the
bottom-right cell (`[rows - 1][cols - 1]`).

## Student-facing API (what the site depends on)

These types are shipped by the library. Students never see the bitmask; they use
these. See the Javadoc in each type for details.

```java
package com.frc2713.mazesolver;

public enum Direction {                     // UP=1, DOWN=2, RIGHT=4, LEFT=8
    UP, DOWN, LEFT, RIGHT;
    int bit();                              // its bit in a cell's open-sides mask
    Direction opposite();                   // turn around
    Direction clockwise();                  // turn right (UP→RIGHT→DOWN→LEFT)
    Direction counterClockwise();           // turn left
}

public interface Cell {                     // one square; plain yes/no queries
    int row(); int col();
    boolean wall(Direction dir);            // is that side a wall?
    boolean isStart(); boolean isGoal();
}

public interface Robot {                    // walks the maze one cell at a time
    boolean readWallSensor(Direction dir);  // true if that side is a wall
    void drive(Direction dir);              // move one cell; no-op into a wall
    Direction facing();                     // the way it last drove (starts RIGHT)
    boolean atGoal();
    int row(); int col();
    Cell cell();
    int[][] trail();                        // {row,col} pairs, start..current
}

public interface Maze {                     // the maze, hiding the int[][]
    int rows(); int cols();
    Cell cellAt(int row, int col);
    Robot robot();                          // fresh robot at the start cell
    boolean isGoalCell(int row, int col);
}
```

### Entry point the site relies on

The library ships the concrete `Maze` the site builds directly from the wire
format:

```java
public final class GridMaze implements Maze {
    public GridMaze(int[][] maze) { ... }    // maze = the bitmask grid above
}
```

The site constructs one per lesson snippet (`new GridMaze(grid)`), hands
`maze.robot()` to the student's algorithm, then reads `robot.trail()` to draw
the path. A `drive` into a wall is a no-op (no move, no trail entry, `facing()`
unchanged) — the site harness and lessons rely on that. `GridMaze` and the
`Cell`/`Robot` it returns are shipped by the library, not the site.

### The library's own solver

Separately from the interactive API, the library ships a batch solver:

```java
public interface MazeSolver { int[][] solve(int[][] maze); }
```

`com.frc2713.mazesolver.DefaultMazeSolver` implements it as a breadth-first
search, returning the shortest path from start to goal as `{row, col}` pairs
(starting `{0, 0}`), or `new int[0][]` if none exists. It throws
`IllegalArgumentException` for a null, empty, or non-rectangular maze.

## Changing this contract

If either side needs the shape of these types, the bitmask values, the
screen-relative direction mapping, or the start/goal convention to change,
update this file first and treat it as the single source of truth — the site's
`maze-generator` usage and this library's `Direction` enum both need to move
together.

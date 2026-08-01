# API contract

This is the contract shared between this library and the `software_training`
lesson site — both sides need to agree on it, since lesson snippets and this
library speak the same maze format.

There are two layers, and keeping them separate is the whole point:

- **The wire format** — a raw `int[][]` bitmask — is how a maze crosses from the
  site (which generates mazes in JavaScript) into Java. It is an implementation
  detail. Only the library's *implementer* (Owen) touches it.
- **The student-facing API** — `Maze`, `Cell`, `Robot`, `MazeSolver` — is what
  new students write code against. It is bitmask-free by design.

## Wire format (implementer-facing)

A maze arrives as `int[][] maze`, indexed `maze[row][col]`. Every row has the
same length. Each cell's value is a bitmask of which sides are **open** (carved
through, i.e. passable), using the same convention as the `maze-generator` npm
package the lesson site uses to build mazes:

| Direction | Bit  | Value |
|-----------|------|-------|
| NORTH     | 0001 | 1     |
| SOUTH     | 0010 | 2     |
| EAST      | 0100 | 4     |
| WEST      | 1000 | 8     |

A cell open to the north and east only is `NORTH \| EAST == 5`. A cell open on
all four sides is `15`. These constants are exposed as
`com.frc2713.mazesolver.Direction.{NORTH,SOUTH,EAST,WEST}`.

The maze is assumed internally consistent: if `maze[r][c]` has `EAST` set, then
`maze[r][c + 1]` has `WEST` set, and so on. Implementations need not validate
this beyond basic shape checks.

Start is always the top-left cell (`[0][0]`); the goal is always the
bottom-right cell (`[rows - 1][cols - 1]`). Screen-relative directions map to
the bitmask as: **up** = NORTH, **down** = SOUTH, **right** = EAST,
**left** = WEST.

## Student-facing API (what the site depends on)

These interfaces are shipped by the library. New students never see the bitmask;
they use these types. See the Javadoc in each interface for details.

```java
package com.frc2713.mazesolver;

public interface Cell {                     // one square; plain yes/no queries
    int row(); int col();
    boolean wallUp();  boolean wallDown();
    boolean wallLeft(); boolean wallRight();
    boolean isStart(); boolean isGoal();
}

public interface Robot {                    // walks the maze one cell at a time
    int row(); int col();
    Cell cell();
    boolean canMoveUp();  boolean canMoveDown();
    boolean canMoveLeft(); boolean canMoveRight();
    void moveUp();  void moveDown();
    void moveLeft(); void moveRight();       // throw IllegalStateException if blocked
    boolean atGoal();
    int[][] trail();                         // {row,col} pairs, start..current
}

public interface Maze {                     // the maze, hiding the int[][]
    int rows(); int cols();
    Cell cellAt(int row, int col);
    Robot robot();                           // fresh robot at the start cell
    boolean isGoalCell(int row, int col);
}

public interface MazeSolver {               // the ALGORITHM — a student writes this
    void solve(Robot robot);                 // move the robot until robot.atGoal()
}
```

### Entry point the site relies on

The library **must** provide a concrete `Maze` the site can build directly from
the wire format:

```java
public final class GridMaze implements Maze {
    public GridMaze(int[][] maze) { ... }    // maze = the bitmask grid above
}
```

The site constructs one per lesson snippet (`new GridMaze(grid)`), hands
`maze.robot()` to the student's `MazeSolver`, then reads `robot.trail()` to draw
the path. `GridMaze` (and the `Cell`/`Robot` it returns) is the implementer's
job; everything else in the student-facing API is an interface the student's
solver is written against.

## Changing this contract

If either side needs the shape of these types, the bitmask values, the
screen-relative direction mapping, or the start/goal convention to change,
update this file first and treat it as the single source of truth — the site's
`maze-generator` usage and this library's `Direction` constants both need to
move together.

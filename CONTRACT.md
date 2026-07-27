# API contract

This is the contract shared between this library and the `software_training`
lesson site — both sides need to agree on it, since lesson snippets and this
library speak the same maze format.

## Maze representation

A maze is `int[][] maze`, indexed `maze[row][col]`. Every row has the same
length. Each cell's value is a bitmask of which sides are **open** (carved
through, i.e. passable), using the same convention as the `maze-generator`
npm package the lesson site already uses to build mazes:

| Direction | Bit  | Value |
|-----------|------|-------|
| NORTH     | 0001 | 1     |
| SOUTH     | 0010 | 2     |
| EAST      | 0100 | 4     |
| WEST      | 1000 | 8     |

A cell open to the north and east only is `NORTH \| EAST == 5`. A cell open
on all four sides is `15`. These constants are exposed as
`com.frc2713.mazesolver.Direction.{NORTH,SOUTH,EAST,WEST}`.

The maze is assumed internally consistent: if `maze[r][c]` has `EAST` set,
then `maze[r][c + 1]` has `WEST` set, and so on. `solve` is not required to
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
null, empty, or non-rectangular `maze`.

`com.frc2713.mazesolver.DefaultMazeSolver` is the shipped implementation.

## Changing this contract

If either side (this library or the lesson site) needs the shape of `solve`,
the bitmask values, or the start/goal convention to change, update this file
first and treat it as the single source of truth — the site's
`maze-generator` usage and this library's `Direction` constants both need to
move together.

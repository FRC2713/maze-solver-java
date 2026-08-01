# maze-solver

Pure-Java maze solving library, built as a `.jar` and loaded in the browser
by [CheerpJ](https://cheerpj.com) from the `software_training` repo's lesson
site.

## Constraints (why the build is set up this way)

CheerpJ runs **OpenJDK 8** in the browser, so this library has to play by
those rules:

1. **Java 8 bytecode.** The build compiles with `--release 8`
   (`maven.compiler.release` in `pom.xml`). Java 9+ syntax (`var`,
   `List.of`, records, switch expressions, text blocks, ...) will fail to
   *load*, not just fail to compile elsewhere, so this is enforced at build
   time, not just documented.
2. **Pure computation only.** No networking, no threads, no real
   filesystem, no AWT/Swing. Maze solving is pure by nature, so this should
   never come up — but if you find yourself reaching for any of those, stop
   and reconsider.
3. **Small.** The jar downloads into the browser on first run. There are
   currently **zero runtime dependencies** — keep it that way. (Test-only
   dependencies, like JUnit, never end up in the built jar.) CI fails the
   build if the jar exceeds a size budget (see `.github/workflows/build.yml`).
4. **One documented public API.** See [`CONTRACT.md`](CONTRACT.md) for the
   `MazeSolver` interface and the N/S/E/W bitmask convention.

## Building

```
mvn verify
```

Produces `target/maze-solver.jar`. Requires a JDK capable of `--release 8`
(any reasonably recent JDK — this was set up against JDK 21/26) and Maven.

CI (`.github/workflows/build.yml`) additionally verifies, on every push:
- every `.class` file in the jar has bytecode major version `52` (Java 8)
- the jar is under the size budget

## Project layout

The student-facing API is a set of interfaces; the concrete implementations
(the parts that read the bitmask) are the implementer's job.

```
src/main/java/com/frc2713/mazesolver/
  Cell.java        interface — one square, plain wallUp()/wallRight() queries
  Robot.java       interface — walks the maze; canMoveRight()/moveRight()/atGoal()
  Maze.java        interface — the maze, hands out Cells and a Robot
  MazeSolver.java  interface — the ALGORITHM a student writes: solve(Robot)
  Direction.java   N/S/E/W bitmask constants (implementer-facing only)
src/test/java/...   unit tests

  -- to implement (see CONTRACT.md) --
  GridMaze + the Cell/Robot it returns   the concrete side, reading int[][]
```

New students write a `MazeSolver` against `Robot`/`Cell` and never touch a
bitmask. See [`CONTRACT.md`](CONTRACT.md) for the full API contract shared with
the lesson site, including the `GridMaze` entry point the site depends on.

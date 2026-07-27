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

```
src/main/java/com/frc2713/mazesolver/
  MazeSolver.java          the public API (interface)
  Direction.java           N/S/E/W bitmask constants
  DefaultMazeSolver.java   implementation — solving logic goes here
src/test/java/...           unit tests
```

See [`CONTRACT.md`](CONTRACT.md) for the API contract shared with the lesson
site.

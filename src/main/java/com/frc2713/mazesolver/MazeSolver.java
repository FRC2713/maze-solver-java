package com.frc2713.mazesolver;

/**
 * Solves a maze expressed as a grid of cells, where each cell is an
 * N/S/E/W bitmask marking which sides are open (carved) rather than walled.
 *
 * <p>Bitmask convention (matches the {@code maze-generator} npm package used
 * to author lesson content, so a maze can round-trip between the site and
 * this library unchanged). The bits are named the way they read on screen —
 * see {@link Direction}:
 * <pre>
 *   UP    = 1  (0b0001)   (north)
 *   DOWN  = 2  (0b0010)   (south)
 *   RIGHT = 4  (0b0100)   (east)
 *   LEFT  = 8  (0b1000)   (west)
 * </pre>
 * A cell's value is the bitwise OR of every open side, e.g. a cell open
 * upward and to the right only is {@code UP.bit() | RIGHT.bit() == 5}.
 *
 * <p>Implementations must be pure: no networking, no threads, no filesystem,
 * no AWT/Swing. This library runs inside a browser JVM (CheerpJ, OpenJDK 8),
 * where none of those are available.
 */
public interface MazeSolver {

    /**
     * Finds a path through the maze.
     *
     * @param maze a rectangular grid, indexed {@code maze[row][col]}, where
     *             each value is an N/S/E/W bitmask (see {@link Direction})
     *             describing which sides of that cell are open. The maze is
     *             assumed to be a valid grid: every row the same length, and
     *             every "open" side agreed upon by both adjacent cells (e.g.
     *             if {@code maze[r][c]} has {@code RIGHT} set, then
     *             {@code maze[r][c + 1]} has {@code LEFT} set).
     * @return the path from the top-left cell ({@code [0][0]}) to the
     *         bottom-right cell ({@code [rows - 1][cols - 1]}), as an ordered
     *         list of {@code [row, col]} coordinate pairs starting with
     *         {@code {0, 0}} and ending with the destination cell. Returns
     *         an empty array ({@code new int[0][]}) if no path exists.
     * @throws IllegalArgumentException if {@code maze} is null, empty, or
     *         not a valid rectangular grid
     */
    int[][] solve(int[][] maze);
}

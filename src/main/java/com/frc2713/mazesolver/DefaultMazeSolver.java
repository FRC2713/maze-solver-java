package com.frc2713.mazesolver;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The library's {@link MazeSolver} implementation. Finds the shortest path from
 * the top-left start to the bottom-right goal with a breadth-first search over
 * the cells reachable through carved (open) sides.
 */
public final class DefaultMazeSolver implements MazeSolver {

    @Override
    public int[][] solve(int[][] maze) {
        if (maze == null || maze.length == 0 || maze[0] == null || maze[0].length == 0) {
            throw new IllegalArgumentException("maze must be a non-empty rectangular grid");
        }
        int rows = maze.length;
        int cols = maze[0].length;
        for (int[] row : maze) {
            if (row == null || row.length != cols) {
                throw new IllegalArgumentException("maze must be rectangular: every row the same length");
            }
        }

        int goalR = rows - 1;
        int goalC = cols - 1;

        // parent[r][c] encodes the cell we arrived from as r*cols + c; -1 means
        // unvisited, -2 marks the start (which has no parent).
        int[][] parent = new int[rows][cols];
        for (int[] row : parent) {
            Arrays.fill(row, -1);
        }

        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[] { 0, 0 });
        parent[0][0] = -2;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0];
            int c = cur[1];
            if (r == goalR && c == goalC) {
                return reconstruct(parent, cols, goalR, goalC);
            }
            for (Direction dir : Direction.values()) {
                if ((maze[r][c] & dir.bit()) == 0) {
                    continue; // that side is walled off
                }
                int nr = r;
                int nc = c;
                switch (dir) {
                    case UP:
                        nr -= 1;
                        break;
                    case DOWN:
                        nr += 1;
                        break;
                    case LEFT:
                        nc -= 1;
                        break;
                    case RIGHT:
                        nc += 1;
                        break;
                }
                if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) {
                    continue;
                }
                if (parent[nr][nc] != -1) {
                    continue; // already reached by an equal-or-shorter path
                }
                parent[nr][nc] = r * cols + c;
                queue.add(new int[] { nr, nc });
            }
        }

        return new int[0][]; // goal unreachable
    }

    // Walk the parent pointers back from the goal to the start, then reverse.
    private static int[][] reconstruct(int[][] parent, int cols, int goalR, int goalC) {
        ArrayList<int[]> reversed = new ArrayList<>();
        int r = goalR;
        int c = goalC;
        while (parent[r][c] != -2) {
            reversed.add(new int[] { r, c });
            int p = parent[r][c];
            r = p / cols;
            c = p % cols;
        }
        reversed.add(new int[] { 0, 0 });

        int n = reversed.size();
        int[][] path = new int[n][];
        for (int i = 0; i < n; i++) {
            path[i] = reversed.get(n - 1 - i);
        }
        return path;
    }
}

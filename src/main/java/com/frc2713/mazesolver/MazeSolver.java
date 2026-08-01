package com.frc2713.mazesolver;

/**
 * A maze-solving algorithm — this is what a <em>student</em> writes.
 *
 * <p>The library hands you a {@link Robot} sitting at the start of a maze; your
 * job is to move it until it reaches the goal. You decide <em>how</em> — that
 * decision is the algorithm. A simple "wall follower" might look like:
 *
 * <pre>{@code
 * public class MySolver implements MazeSolver {
 *     public void solve(Robot robot) {
 *         while (!robot.atGoal()) {
 *             if (robot.canMoveRight())      robot.moveRight();
 *             else if (robot.canMoveDown())  robot.moveDown();
 *             else if (robot.canMoveLeft())  robot.moveLeft();
 *             else                           robot.moveUp();
 *         }
 *     }
 * }
 * }</pre>
 *
 * <p>The path the robot took is available afterwards via {@link Robot#trail()}.
 */
public interface MazeSolver {

    /**
     * Drive the robot from the start cell to the goal cell.
     *
     * @param robot a robot positioned at the maze's start; move it with
     *              {@link Robot#moveUp()} and friends until
     *              {@link Robot#atGoal()} is {@code true}
     */
    void solve(Robot robot);
}

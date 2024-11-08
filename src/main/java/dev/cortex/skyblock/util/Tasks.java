package dev.cortex.skyblock.util;

import java.util.function.Consumer;

import dev.cortex.skyblock.CortexSkyblock;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
/**
 * Utilities for quickly creating various Bukkit tasks inside your plugin.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Tasks {

    /**
     * Runs the task once on the primary thread, without delay nor repetition.
     *
     * @param task The task to run
     * @return The BukkitTask that was initialized for this task
     */
    @NotNull
    public static BukkitTask runSync(@NotNull final Consumer<BukkitRunnable> task) {
        return createRunnable(task).runTask(CortexSkyblock.instance);
    }

    /**
     * Runs the task once, asynchronously, without delay nor repetition.
     *
     * @param task The task to run
     * @return The BukkitTask that was initialized for this task
     */
    @NotNull
    public static BukkitTask runAsync(@NotNull final Consumer<BukkitRunnable> task) {
        return createRunnable(task).runTaskAsynchronously(CortexSkyblock.instance);
    }

    /**
     * Runs the task once, with an initial delay.
     *
     * @param task  The task to run
     * @param delay The number of ticks to delay
     * @return The BukkitRunnable that was initialized for this task
     */
    @NotNull
    public static BukkitTask delay(@NotNull final Consumer<BukkitRunnable> task, final long delay) {
        return createRunnable(task).runTaskLater(CortexSkyblock.instance, delay);
    }

    /**
     * Runs the task once, asynchronously, with an initial delay.
     *
     * @param task  The task to run
     * @param delay The number of ticks to delay
     * @return The BukkitTask that was initialized for this task
     */
    @NotNull
    public static BukkitTask delayAsync(
            @NotNull final Consumer<BukkitRunnable> task,
            final long delay) {
        return createRunnable(task).runTaskLaterAsynchronously(CortexSkyblock.instance, delay);
    }

    /**
     * Runs the task repeatedly, with an initial delay and a delay between runs.
     *
     * @param task         The task to run
     * @param initialDelay The number of ticks to delay before the task starts repeating
     * @param repeatDelay  The number of ticks between each run
     * @return The BukkitTask that was initialized for this task
     */
    @NotNull
    public static BukkitTask repeat(
            @NotNull final Consumer<BukkitRunnable> task,
            final long initialDelay,
            final long repeatDelay) {
        return createRunnable(task).runTaskTimer(CortexSkyblock.instance, initialDelay, repeatDelay);
    }

    /**
     * Runs the task repeatedly, with an initial delay, a delay between runs, and a number of
     * repetitions.
     *
     * @param task         The task to run
     * @param initialDelay The number of ticks to delay before the task starts repeating
     * @param repeatDelay  The number of ticks between each run
     * @param limit        The maximum number of times the task will be repeated
     * @return The BukkitTask that was initialized for this task
     */
    @NotNull
    public static BukkitTask repeat(
            @NotNull final Consumer<BukkitRunnable> task,
            final long initialDelay,
            final long repeatDelay,
            final int limit) {

        final BukkitRunnable runnable =
                new BukkitRunnable() {
                    int repeats = 0;

                    @Override
                    public void run() {

                        if (repeats > limit) {
                            cancel();
                            return;
                        }

                        task.accept(this);
                        repeats++;
                    }
                };

        return runnable.runTaskTimer(CortexSkyblock.instance, initialDelay, repeatDelay);
    }

    /**
     * Runs the task repeatedly, asynchronously, with an initial delay and a delay between runs.
     *
     * @param task         The task to run
     * @param initialDelay The number of ticks to delay before the task starts repeating
     * @param repeatDelay  The number of ticks between each run
     * @return The BukkitTask that was initialized for this task
     */
    @NotNull
    public static BukkitTask repeatAsync(
            @NotNull final Consumer<BukkitRunnable> task,
            final long initialDelay,
            final long repeatDelay) {
        return createRunnable(task)
                .runTaskTimerAsynchronously(CortexSkyblock.instance, initialDelay, repeatDelay);
    }

    /**
     * Runs the task repeatedly, asynchronously, with an initial delay, a delay between runs, and a
     * number of repetitions.
     *
     * @param task         The task to run
     * @param initialDelay The number of ticks to delay before the task starts repeating
     * @param repeatDelay  The number of ticks between each run
     * @param limit        The maximum number of times the task will be repeated
     * @return The BukkitTask that was initialized for this task
     */
    @NotNull
    public static BukkitTask repeatAsync(
            @NotNull final Consumer<BukkitRunnable> task,
            final long initialDelay,
            final long repeatDelay,
            final int limit) {

        final BukkitRunnable runnable =
                new BukkitRunnable() {
                    int repeats = 0;

                    @Override
                    public void run() {

                        if (repeats > limit) {
                            cancel();
                            return;
                        }

                        task.accept(this);
                        repeats++;
                    }
                };

        return runnable.runTaskTimerAsynchronously(CortexSkyblock.instance, initialDelay, repeatDelay);
    }

    private static BukkitRunnable createRunnable(final Consumer<BukkitRunnable> task) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                task.accept(this);
            }
        };
    }

}

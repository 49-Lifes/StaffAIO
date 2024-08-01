package info.preva1l.staffaio.utils;

import info.preva1l.staffaio.StaffAIO;
import lombok.experimental.UtilityClass;

/**
 * Easy creation of Bukkit Tasks
 */
public final class TaskManager {
    /**
     * Synchronous Tasks
     */
    @UtilityClass
    public class Sync {
        /**
         * Run a synchronous task once. Helpful when needing to run some sync code in an async loop
         * @param runnable The runnable, lambda supported yeh
         */
        public void run(Runnable runnable) {
            StaffAIO.getInstance().getServer().getScheduler().runTask(StaffAIO.getInstance(), runnable);
        }

        /**
         * Run a synchronous task forever with a delay between runs.
         * @param runnable The runnable, lambda supported yeh
         * @param interval Time between each run
         */
        public void runTask(Runnable runnable, long interval) {
            StaffAIO.getInstance().getServer().getScheduler().runTaskTimer(StaffAIO.getInstance(), runnable, 0L, interval);
        }

        /**
         * Run a synchronous task once with a delay. Helpful when needing to run some sync code in an async loop
         * @param runnable The runnable, lambda supported yeh
         * @param delay Time before running.
         */
        public void runLater(Runnable runnable, long delay) {
            StaffAIO.getInstance().getServer().getScheduler().runTaskLater(StaffAIO.getInstance(), runnable, delay);
        }
    }

    /**
     * Asynchronous tasks
     */
    @UtilityClass
    public class Async {
        /**
         * Run an asynchronous task once. Helpful when needing to run some sync code in an async loop
         * @param runnable The runnable, lambda supported yeh
         */
        public void run(Runnable runnable) {
            StaffAIO.getInstance().getServer().getScheduler().runTaskAsynchronously(StaffAIO.getInstance(), runnable);
        }

        /**
         * Run an asynchronous task forever with a delay between runs.
         * @param runnable The runnable, lambda supported yeh
         * @param interval Time between each run
         */
        public void runTask(Runnable runnable, long interval) {
            StaffAIO.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(StaffAIO.getInstance(), runnable, 0L, interval);
        }

        /**
         * Run an asynchronous task once with a delay. Helpful when needing to run some sync code in an async loop
         * @param runnable The runnable, lambda supported yeh
         * @param delay Time before running.
         */
        public void runLater(Runnable runnable, long delay) {
            StaffAIO.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(StaffAIO.getInstance(), runnable, delay);
        }
    }
}
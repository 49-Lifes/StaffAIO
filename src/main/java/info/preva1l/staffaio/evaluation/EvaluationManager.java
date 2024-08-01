package info.preva1l.staffaio.evaluation;

import info.preva1l.staffaio.StaffAIO;
import info.preva1l.staffaio.hooks.impl.LitebansHook;
import info.preva1l.staffaio.logging.Log;
import info.preva1l.staffaio.logging.User;
import org.jetbrains.annotations.Blocking;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class EvaluationManager {
    private final StaffAIO plugin;

    public EvaluationManager(StaffAIO plugin) {
        this.plugin = plugin;
    }

    /**
     * Calculates a score of how useful as staff is. It is a percentage out of 100%
     * Takes the amount of punishments and what those punishments are for.
     * Only takes the past month into consideration.
     * @param user staff to evaluate
     * @return their score 0% - 100%
     */
    @Blocking
    public double calculateScore(User user) {
        Optional<LitebansHook> litebansHook = plugin.getHookManager().getHook(LitebansHook.class);
        if (litebansHook.isEmpty()) {
            return 0;
        }
        List<Log> userLogs = litebansHook.get().getLogs(user);

        if (userLogs.isEmpty()) return 0;

        double score = 0;

        // prune logs for old ones
        for (Log log : userLogs) {
            Instant now = Instant.now();
            Instant epoch = Instant.ofEpochMilli(log.getStartDate());
            if (ChronoUnit.DAYS.between(epoch, now) >= 30) {
                userLogs.remove(log);
            }
        }

        int size = userLogs.size();

        if (size >= 5 && size <= 10) {
            score += 10;
        } else if (size > 10 && size <= 20) {
            score += 25;
        } else if (size > 20) {
            score += 35;
        }

        for (Log log : userLogs) {
            switch (log.getPunishment()) {
                case BAN -> score += plugin.getSettings().getInt("eval.ban-score");
                case IP_BAN -> score += plugin.getSettings().getInt("eval.ipban-score");
                case MUTE -> score += plugin.getSettings().getInt("eval.mute-score");
                case IP_MUTE -> score += plugin.getSettings().getInt("eval.ipmute-score");
            }
        }

        if (score >= 100) {
            return 100;
        }

        return score;
    }
}

package info.preva1l.staffaio.logging;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class LogCache {
    private final List<Log> logs = new ArrayList<>();

    public void addLog(Log log) {
        logs.add(log);
    }

    public List<Log> getLogs(UUID staffMember) {
        return logs.stream().filter(log -> log.getStaffMember().getUniqueId().equals(staffMember)).toList();
    }
}

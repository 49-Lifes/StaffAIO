package info.preva1l.staffaio.logging;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Log {
    private final User staffMember;
    private final User punishedMember;
    private final Punishment punishment;
    private final String reason;
    private final long startDate;
    /**
     * -1 if permanent
     */
    private final long endDate;
}

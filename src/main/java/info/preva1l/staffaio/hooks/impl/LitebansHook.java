package info.preva1l.staffaio.hooks.impl;

import info.preva1l.staffaio.hooks.Hook;
import info.preva1l.staffaio.logging.Log;
import info.preva1l.staffaio.logging.Punishment;
import info.preva1l.staffaio.logging.User;
import litebans.api.Database;
import org.jetbrains.annotations.Blocking;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class LitebansHook extends Hook {
    public LitebansHook() {
        super("LiteBans");
    }

    @Override
    protected void enable() {
        // nothing here!
    }

    @Blocking
    public List<Log> getLogs(User staff) {
        List<Log> returnValue = new ArrayList<>();
        returnValue.addAll(getBans(staff));
        returnValue.addAll(getMutes(staff));
        return returnValue;
    }

    private List<Log> getBans(User staff) {
        List<Log> returnValue = new ArrayList<>();
        String query = "SELECT * FROM {bans} WHERE banned_by_uuid=?";
        try (PreparedStatement st = Database.get().prepareStatement(query)) {
            st.setString(1, staff.getUniqueId().toString());
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    final UUID punishedUUID = UUID.fromString(rs.getString("uuid"));
                    final User punished = new User(Database.get().getPlayerName(punishedUUID), punishedUUID);
                    final Punishment punishment = rs.getBoolean("ipban") ? Punishment.IP_BAN : Punishment.BAN;
                    final long startDate = rs.getLong("time");
                    final long endDate = rs.getLong("until");
                    returnValue.add(new Log(staff, punished, punishment, startDate, endDate));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return returnValue;
    }

    private List<Log> getMutes(User staff) {
        List<Log> returnValue = new ArrayList<>();
        String query = "SELECT * FROM {mutes} WHERE banned_by_uuid=?";
        try (PreparedStatement st = Database.get().prepareStatement(query)) {
            st.setString(1, staff.getUniqueId().toString());
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    final UUID punishedUUID = UUID.fromString(rs.getString("uuid"));
                    final User punished = new User(Database.get().getPlayerName(punishedUUID), punishedUUID);
                    final Punishment punishment = rs.getBoolean("ipban") ? Punishment.IP_MUTE : Punishment.MUTE;
                    final long startDate = rs.getLong("time");
                    final long endDate = rs.getLong("until");
                    returnValue.add(new Log(staff, punished, punishment, startDate, endDate));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return returnValue;
    }
}

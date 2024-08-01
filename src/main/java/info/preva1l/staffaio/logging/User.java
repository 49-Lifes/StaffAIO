package info.preva1l.staffaio.logging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class User {
    private final String username;
    private final UUID uniqueId;

    public static User adapt(Player player) {
        return new User(player.getName(), player.getUniqueId());
    }
}

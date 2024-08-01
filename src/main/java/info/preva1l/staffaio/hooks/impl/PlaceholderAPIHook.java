package info.preva1l.staffaio.hooks.impl;

import info.preva1l.staffaio.hooks.Hook;
import info.preva1l.staffaio.logging.User;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;

public final class PlaceholderAPIHook extends Hook {
    public PlaceholderAPIHook() {
        super("PlaceholderAPI");
    }

    @Override
    protected void enable() {
        // Do nothing
    }

    public String getUserPrefix(User user) {
        return PlaceholderAPI.setPlaceholders(Bukkit.getOfflinePlayer(user.getUniqueId()),"%vault_prefix%");
    }
}

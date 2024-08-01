package info.preva1l.staffaio.staffchat;

import info.preva1l.staffaio.StaffAIO;
import info.preva1l.staffaio.hooks.impl.DiscordHook;
import info.preva1l.staffaio.hooks.impl.PlaceholderAPIHook;
import info.preva1l.staffaio.logging.User;
import info.preva1l.staffaio.utils.TaskManager;
import info.preva1l.staffaio.utils.Text;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class StaffChatManager {
    private final StaffAIO plugin;
    private final Map<UUID, Boolean> toggled = new ConcurrentHashMap<>();

    public StaffChatManager(StaffAIO plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(new StaffChatListener(plugin), plugin);

        TaskManager.Async.runTask(() -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!isToggled(User.adapt(player))) continue;
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                        TextComponent.fromLegacyText(Text.message("&b&lStaffAIO &fYou are currently talking in StaffChat!")));
            }
        }, 20L);
    }

    public String format(User user, String message) {
        final Optional<PlaceholderAPIHook> papiHook = plugin.getHookManager().getHook(PlaceholderAPIHook.class);
        String rank = "";
        if (papiHook.isPresent()) {
            rank = papiHook.get().getUserPrefix(user);
        }
        return plugin.getSettings().getString("staffchat.format")
                .replace("%player%", user.getUsername())
                .replace("%message%", message)
                .replace("%rank%", rank);
    }

    public void sendMessage(User user, String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.hasPermission("staffaio.staffchat")) continue;

            player.sendMessage(Text.message(format(user, message)));

            plugin.getHookManager().getHook(DiscordHook.class)
                    .ifPresent(hook -> hook.sendStaffChatMessage(user, message));
        }
    }

    public void sendJoin(User user) {
        final Optional<PlaceholderAPIHook> papiHook = plugin.getHookManager().getHook(PlaceholderAPIHook.class);
        String rank = "";
        if (papiHook.isPresent()) {
            rank = papiHook.get().getUserPrefix(user);
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.hasPermission("staffaio.staffchat")) continue;

            player.sendMessage(Text.message(plugin.getSettings().getString("staffchat.join")
                    .replace("%player%", user.getUsername())
                    .replace("%rank%", rank)));

            plugin.getHookManager().getHook(DiscordHook.class)
                    .ifPresent(hook -> hook.sendStaffChatJoin(user));
        }
    }

    public void sendLeave(User user) {
        final Optional<PlaceholderAPIHook> papiHook = plugin.getHookManager().getHook(PlaceholderAPIHook.class);
        String rank = "";
        if (papiHook.isPresent()) {
            rank = papiHook.get().getUserPrefix(user);
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.hasPermission("staffaio.staffchat")) continue;

            player.sendMessage(Text.message(plugin.getSettings().getString("staffchat.leave")
                    .replace("%player%", user.getUsername())
                    .replace("%rank%", rank)));

            plugin.getHookManager().getHook(DiscordHook.class)
                    .ifPresent(hook -> hook.sendStaffChatLeave(user));
        }
    }

    public boolean toggleStaffChat(User player) {
        boolean current = isToggled(player);
        toggled.put(player.getUniqueId(), !current);
        return !current;
    }

    public boolean isToggled(User player) {
        return toggled.getOrDefault(player.getUniqueId(), false);
    }
}

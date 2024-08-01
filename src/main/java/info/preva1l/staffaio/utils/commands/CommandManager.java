package info.preva1l.staffaio.utils.commands;

import info.preva1l.staffaio.StaffAIO;
import info.preva1l.staffaio.utils.CommandMapUtil;
import info.preva1l.staffaio.utils.TaskManager;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public class CommandManager {
    protected final StaffAIO plugin;
    protected final List<BasicCommand> loadedCommands;

    public CommandManager(StaffAIO plugin) {
        this.plugin = plugin;
        this.loadedCommands = new ArrayList<>();
    }

    /**
     * Register a new command.
     * @param basicCommand the command.
     */
    public void registerCommand(BasicCommand basicCommand) {
        CommandMapUtil.getCommandMap(plugin.getServer()).register("staffaio", new CommandExecutor(basicCommand));
        loadedCommands.add(basicCommand);
        StaffAIO.getInstance().getLogger().info(String.format("Registered Command %s", basicCommand.getInfo().name()));
    }

    public class CommandExecutor extends BukkitCommand {
        private final BasicCommand basicCommand;

        public CommandExecutor(BasicCommand basicCommand) {
            super(basicCommand.getInfo().name());
            this.setAliases(Arrays.asList(basicCommand.getInfo().aliases()));
            if (!basicCommand.getInfo().permission().isEmpty()) {
                this.setPermission(basicCommand.getInfo().permission());
            }
            this.basicCommand = basicCommand;
        }

        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
            if (this.basicCommand.getInfo().inGameOnly() && sender instanceof ConsoleCommandSender) {
                sender.sendMessage("MUST BE PLAYER LOCALE");
                return false;
            }
            if (this.getPermission() != null && !sender.hasPermission(this.getPermission())) {
                sender.sendMessage("NO PERMISSION LOCALE");
                return false;
            }

            if (this.basicCommand.getInfo().async()) {
                TaskManager.Async.run(() -> basicCommand.execute(sender, args));
            } else {
                TaskManager.Sync.run(() -> basicCommand.execute(sender, args));
            }
            return false;
        }

        @NotNull
        public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
            // Primary argument
            if (args.length <= 1) {
                List<String> completors = basicCommand.tabComplete(sender, args);

                if (completors.isEmpty() && !basicCommand.getSubCommands().isEmpty()) {
                    List<String> ret = new ArrayList<>();
                    for (BasicSubCommand subCommand : basicCommand.getSubCommands()) {
                        if (!subCommand.getInfo().permission().isEmpty() && !sender.hasPermission(subCommand.getInfo().permission())) {
                            continue;
                        }
                        ret.add(subCommand.getInfo().name());
                        Collections.addAll(ret, subCommand.getInfo().aliases());
                    }
                    if (args.length == 0) {
                        completors.addAll(ret);
                    } else {
                        StringUtil.copyPartialMatches(args[0], ret, completors);
                    }
                    return completors;
                }

                return completors;
            }

            // Sub command tab completer
            List<String> completors = new ArrayList<>();

            List<String> ret = new ArrayList<>();
            for (BasicSubCommand subCommand : basicCommand.getSubCommands()) {
                if (!subCommand.getInfo().name().equals(args[0]) && !Arrays.stream(subCommand.getInfo().aliases()).toList().contains(args[0])) {
                    continue;
                }
                if (!subCommand.getInfo().permission().isEmpty() && !sender.hasPermission(subCommand.getInfo().permission())) {
                    continue;
                }
                ret.addAll(subCommand.tabComplete(sender, removeFirstElement(args)));
            }
            StringUtil.copyPartialMatches(args[args.length - 1], ret, completors);
            return completors;
        }
    }

    /**
     * Remove the first element of the args array.
     * @param array args
     * @return args - 1st element
     */
    protected String[] removeFirstElement(String[] array) {
        if (array == null || array.length == 0) {
            return new String[]{};
        }

        String[] newArray = new String[array.length - 1];
        System.arraycopy(array, 1, newArray, 0, array.length - 1);

        return newArray;
    }
}
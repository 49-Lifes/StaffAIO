package info.preva1l.staffaio.staffchat.commands;

import info.preva1l.staffaio.StaffAIO;
import info.preva1l.staffaio.logging.User;
import info.preva1l.staffaio.utils.commands.BasicCommand;
import info.preva1l.staffaio.utils.commands.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand extends BasicCommand {
    @Command(name = "staffchat", permission = "staffaio.staffchat", aliases = "sc", async = true)
    public StaffChatCommand(StaffAIO plugin) {
        super(plugin);
        getSubCommands().add(new StaffChatToggleSubCommand(plugin));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            getSubCommands().get(0).execute(sender, args);
            return;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("toggle")) {
            getSubCommands().get(0).execute(sender, args);
            return;
        }

        plugin.getStaffChatManager().sendMessage(User.adapt((Player) sender), String.join(" ", args));
    }
}

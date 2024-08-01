package info.preva1l.staffaio.staffchat.commands;

import info.preva1l.staffaio.StaffAIO;
import info.preva1l.staffaio.logging.User;
import info.preva1l.staffaio.utils.Text;
import info.preva1l.staffaio.utils.commands.BasicSubCommand;
import info.preva1l.staffaio.utils.commands.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatToggleSubCommand extends BasicSubCommand {
    @Command(name = "toggle", async = true, permission = "staffaio.staffchat")
    public StaffChatToggleSubCommand(StaffAIO plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        User user = User.adapt((Player) sender);
        boolean previous = plugin.getStaffChatManager().toggleStaffChat(user);
        sender.sendMessage(Text.message("&b&lStaffAIO &fStaff chat has been %s&f!"
                .formatted(previous ? "&aEnabled" : "&cDisabled")));
    }
}

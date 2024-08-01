package info.preva1l.staffaio.misc;

import info.preva1l.staffaio.StaffAIO;
import info.preva1l.staffaio.utils.Text;
import info.preva1l.staffaio.utils.commands.BasicCommand;
import info.preva1l.staffaio.utils.commands.Command;
import org.bukkit.command.CommandSender;


public class StaffAIOCommand extends BasicCommand {
    @Command(name = "staffaio", permission = "staffaio.admin", inGameOnly = false)
    public StaffAIOCommand(StaffAIO plugin) {
        super(plugin);
        getSubCommands().add(new ReloadSubCommand(plugin));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 1) {
            if (subCommandExecutor(sender, args)) return;
            sender.sendMessage(Text.message("&b&lStaffAIO &cCommand does not exist!"));
            return;
        }

        sender.sendMessage(Text.message("&b&lStaffAIO &fMade by Preva1l"));
    }
}

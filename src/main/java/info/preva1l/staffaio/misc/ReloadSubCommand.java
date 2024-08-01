package info.preva1l.staffaio.misc;

import info.preva1l.staffaio.StaffAIO;
import info.preva1l.staffaio.utils.Text;
import info.preva1l.staffaio.utils.commands.BasicSubCommand;
import info.preva1l.staffaio.utils.commands.Command;
import org.bukkit.command.CommandSender;

public class ReloadSubCommand extends BasicSubCommand {
    @Command(name = "reload", permission = "staffaio.admin")
    public ReloadSubCommand(StaffAIO plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        plugin.getSettings().load();
        sender.sendMessage(Text.message("&b&lStaffAIO &fReloaded Configs!"));
    }
}

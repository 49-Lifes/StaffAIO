package info.preva1l.staffaio.evaluation.commands;

import info.preva1l.staffaio.StaffAIO;
import info.preva1l.staffaio.logging.User;
import info.preva1l.staffaio.utils.Text;
import info.preva1l.staffaio.utils.commands.BasicCommand;
import info.preva1l.staffaio.utils.commands.Command;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.List;

public class EvaluationCommand extends BasicCommand {
    @Command(name = "staffeval", aliases = {"staff-evaluation", "eval"}, permission = "staffaio.staff-eval", async = true)
    public EvaluationCommand(StaffAIO plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Text.message("&b&lStaffEval &cYou must specify a player!"));
            return;
        }
        OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
        if (!player.hasPlayedBefore() || player.getName() == null) {
            sender.sendMessage(Text.message("&b&lStaffEval &cThis player has never played!"));
            return;
        }

        sender.sendMessage(Text.message("&b&lStaffEval &fCalculating score..."));
        double score = plugin.getEvaluationManager().calculateScore(User.adapt(player));
        String scoreColor = "&a";
        if (score <= 30) {
            scoreColor = "&c";
        }
        if (score <= 80 && score > 30) {
            scoreColor = "&6";
        }
        if (score > 80) {
            scoreColor = "&a";
        }
        sender.sendMessage(Text.message("&b&lStaffEval &f%player%'s score is %score%% &8/ %max%%"
                .replace("%player%", player.getName())
                .replace("%score%", scoreColor + score)
                .replace("%max%", scoreColor + "100"))
        );
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return null;
    }
}

package info.preva1l.staffaio.utils.commands;


import info.preva1l.staffaio.StaffAIO;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public abstract class BasicSubCommand {
    public StaffAIO plugin;
    private final Command info;

    public BasicSubCommand(StaffAIO plugin) {
        this.plugin = plugin;
        this.info = Arrays.stream(this.getClass().getConstructors())
                .filter(method -> method.getAnnotation(Command.class) != null)
                .map(method -> method.getAnnotation(Command.class)).findFirst().orElse(null);

        if (info == null) {
            throw new RuntimeException("BasicSubCommand constructor must be annotated with @Command");
        }
    }

    public abstract void execute(CommandSender sender, String[] args);

    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
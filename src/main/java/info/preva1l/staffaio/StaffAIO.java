package info.preva1l.staffaio;

import info.preva1l.staffaio.evaluation.EvaluationManager;
import info.preva1l.staffaio.evaluation.commands.EvaluationCommand;
import info.preva1l.staffaio.hooks.HookManager;
import info.preva1l.staffaio.hooks.impl.DiscordHook;
import info.preva1l.staffaio.hooks.impl.LitebansHook;
import info.preva1l.staffaio.hooks.impl.PlaceholderAPIHook;
import info.preva1l.staffaio.misc.StaffAIOCommand;
import info.preva1l.staffaio.staffchat.StaffChatManager;
import info.preva1l.staffaio.staffchat.commands.StaffChatCommand;
import info.preva1l.staffaio.utils.BasicConfig;
import info.preva1l.staffaio.utils.commands.CommandManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

public final class StaffAIO extends JavaPlugin {
    @Getter private static StaffAIO instance;

    @Getter private BasicConfig settings;

    @Getter private CommandManager commandManager;
    @Getter private HookManager hookManager;
    @Getter private StaffChatManager staffChatManager;
    @Getter private EvaluationManager evaluationManager;

    @Override
    public void onEnable() {
        instance = this;

        staffChatManager = new StaffChatManager(this);
        evaluationManager = new EvaluationManager(this);
        settings = new BasicConfig(this, "config.yml");

        loadHooks();
        loadCommands();

        getLogger().info("StaffAIO Enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("StaffAIO Disabled!");
    }

    public void loadCommands() {
        commandManager = new CommandManager(this);

        Stream.of(
                new StaffChatCommand(this),
                new StaffAIOCommand(this),
                new EvaluationCommand(this)
        ).forEach(commandManager::registerCommand);
    }

    private void loadHooks() {
        hookManager = new HookManager(this);

        Stream.of(
                new LitebansHook(),
                new PlaceholderAPIHook(),
                new DiscordHook()
        ).forEach(hookManager::registerHook);
    }
}

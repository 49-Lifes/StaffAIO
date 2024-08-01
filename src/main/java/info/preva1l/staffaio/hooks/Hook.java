package info.preva1l.staffaio.hooks;

import info.preva1l.staffaio.StaffAIO;
import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public abstract class Hook {
    private final String requiredPlugin;
    private boolean enabled = false;
    private final StaffAIO plugin;

    public Hook() {
        this("NONE");
    }

    public Hook(String requiredPlugin) {
        this(StaffAIO.getInstance(), requiredPlugin);
    }

    public Hook(StaffAIO plugin, String requiredPlugin) {
        this.plugin = plugin;
        this.requiredPlugin = requiredPlugin;
    }

    protected boolean shouldLoad() {
        return getRequiredPlugin().equals("NONE") || Bukkit.getPluginManager().getPlugin(getRequiredPlugin()) != null;
    }

    protected abstract void enable();

    /**
     * This loads the hook.
     * Do not call.
     */
    protected final void loadHook() {
        enable();
        enabled = true;
    }
}

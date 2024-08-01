package info.preva1l.staffaio.hooks;

import info.preva1l.staffaio.StaffAIO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class HookManager {
    private final StaffAIO plugin;
    private final List<Hook> registeredHooks = new ArrayList<>();

    public HookManager(StaffAIO plugin) {
        this.plugin = plugin;
    }

    public void registerHook(Hook hook) {
        if (!hook.shouldLoad()) return;
        hook.loadHook();
        registeredHooks.add(hook);
    }

    public int hookCount() {
        return registeredHooks.size();
    }

    /**
     * Get a hook.
     * @param hook the class of the hook
     * @return an optional of the hook, empty if the hook is not registered
     */
    public <H extends Hook> Optional<H> getHook(Class<H> hook) {
        return (Optional<H>) registeredHooks.stream().filter(current -> current.getClass() == hook).findFirst();
    }
}

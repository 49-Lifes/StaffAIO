package info.preva1l.staffaio.hooks.impl;

import info.preva1l.staffaio.hooks.Hook;
import litebans.api.Entry;
import litebans.api.Events;

public final class LitebansHook extends Hook {
    public LitebansHook() {
        super("LiteBans");
    }

    @Override
    protected void enable() {
        Events.get().register(new Events.Listener() {
            @Override
            public void entryAdded(Entry entry) {
                switch (entry.getType()) {
                    case "ban": {

                    };
                    case "mute": ;
                    case "kick": ;
                    case "warn": ;
                }
            }
        });

    }


    enum PunishmentType {
        MUTE,
        IP_MUTE,
        BAN,
        IP_BAN,
        WARN,
        KICK
        ;
    }
}

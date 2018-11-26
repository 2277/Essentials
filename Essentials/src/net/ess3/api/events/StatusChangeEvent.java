package net.ess3.api.events;

import org.bukkit.event.Cancellable;

import net.ess3.api.IUser;


/**
 * This handles common boilerplate for other StatusChangeEvents
 */
public class StatusChangeEvent extends StateChangeEvent implements Cancellable {
    private boolean newValue;

    public StatusChangeEvent(IUser affected, IUser controller, boolean value) {
        super(affected, controller);
        this.newValue = value;
    }

    public StatusChangeEvent(boolean isAsync, IUser affected, IUser controller, boolean value) {
        super(isAsync, affected, controller);
        this.newValue = value;
    }

    public boolean getValue() {
        return newValue;
    }
}

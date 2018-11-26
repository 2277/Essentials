package net.ess3.api.events;

import org.bukkit.event.Cancellable;

import net.ess3.api.IUser;


public class NickChangeEvent extends StateChangeEvent implements Cancellable {
    private String newValue;

    public NickChangeEvent(IUser affected, IUser controller, String value) {
        super(affected, controller);
        this.newValue = value;
    }

    public String getValue() {
        return newValue;
    }
}

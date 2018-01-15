package org.ships.event.custom;

import java.io.File;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class VesselAttemptToLoadEvent extends Event implements Cancellable {

	boolean CANCELLED;
	File FILE;

	static HandlerList LIST = new HandlerList();

	public VesselAttemptToLoadEvent(File file) {
		FILE = file;
	}

	public File getFile() {
		return FILE;
	}

	@Override
	public boolean isCancelled() {
		return CANCELLED;
	}

	@Override
	public void setCancelled(boolean arg0) {
		CANCELLED = true;
	}

	@Override
	public HandlerList getHandlers() {
		return LIST;
	}

	public static HandlerList getHandlerList() {
		return LIST;
	}

}

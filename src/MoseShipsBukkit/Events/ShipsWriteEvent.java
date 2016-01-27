package MoseShipsBukkit.Events;

import java.io.File;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ShipsWriteEvent extends Event implements Cancellable {

	boolean CANCELLED;
	File FILE;
	Object[] WRITE;

	static HandlerList LIST = new HandlerList();

	public ShipsWriteEvent(File file, Object... write) {
		FILE = file;
		WRITE = write;
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

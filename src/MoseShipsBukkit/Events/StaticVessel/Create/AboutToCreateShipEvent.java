package MoseShipsBukkit.Events.StaticVessel.Create;

import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.StaticVessel.StaticShipEvent;
import MoseShipsBukkit.Ships.VesselTypes.Satic.StaticShipType;

public class AboutToCreateShipEvent<S extends StaticShipType> extends StaticShipEvent<S> implements Cancellable {

	Block SOURCE;
	boolean CANCELLED = false;

	public AboutToCreateShipEvent(S type, Block block) {
		super(type);
		SOURCE = block;
	}

	public Block getSourceBlock() {
		return SOURCE;
	}

	@Override
	public boolean isCancelled() {
		return CANCELLED;
	}

	@Override
	public void setCancelled(boolean check) {
		CANCELLED = check;
	}

	public static HandlerList getHandlerList() {
		return HANDLER;
	}

}

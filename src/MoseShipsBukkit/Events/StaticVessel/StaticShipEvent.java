package MoseShipsBukkit.Events.StaticVessel;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Ships.VesselTypes.Satic.StaticShipType;

public class StaticShipEvent<S extends StaticShipType> extends Event {

	protected S TYPE;
	protected static HandlerList HANDLER = new HandlerList();

	public StaticShipEvent(S type) {
		TYPE = type;
	}

	public S getType() {
		return TYPE;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLER;
	}
	
	public static HandlerList getHandler(){
		return HANDLER;
	}

}

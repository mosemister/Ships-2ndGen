package MoseShipsBukkit.Events.Vessel.Create;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.Vessel.ShipsEvent;
import MoseShipsBukkit.Ships.ShipsData;

public class ShipsCreateEvent extends ShipsEvent implements Cancellable {

	boolean CANCELLED;

	public ShipsCreateEvent(ShipsData ship) {
		super(ship);
	}

	@Override
	public boolean isCancelled() {
		return CANCELLED;
	}

	@Override
	public void setCancelled(boolean arg0) {
		CANCELLED = arg0;
	}

	public static HandlerList getHandlerList() {
		return HANDLER;
	}
	
	public static class Sign extends ShipsCreateEvent {

		public Sign(ShipsData ship) {
			super(ship);
		}
		
		public Sign getLicence(){
			return (Sign)getShip().getLocation().getBlock().getState();
		}
		
		public static HandlerList getHandlerList(){
			return HANDLER;
		}
		
	}

}

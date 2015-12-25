package MoseShipsBukkit.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.StillShip.Vessel.MovableVessel;

public class ShipFinishedMovingEvent extends Event{
	
	MovableVessel VESSEL;
	static final HandlerList HANDLERS = new HandlerList();

	public ShipFinishedMovingEvent(MovableVessel vessel){
		VESSEL = vessel;
	}
	
	public MovableVessel getVessel(){
		return VESSEL;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static HandlerList getHandlerList(){
		return HANDLERS;
	}

}

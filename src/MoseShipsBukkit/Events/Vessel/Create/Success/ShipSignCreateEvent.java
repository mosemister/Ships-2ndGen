package MoseShipsBukkit.Events.Vessel.Create.Success;

import org.bukkit.block.Sign;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.Vessel.Create.ShipCreateEvent;
import MoseShipsBukkit.Ships.ShipsData;

public class ShipSignCreateEvent extends ShipCreateEvent{

	public ShipSignCreateEvent(ShipsData data) {
		super(data);
	}
	
	public Sign getSign(){
		return getShip().getLicence().get();
	}
	
	public static HandlerList getHandlerList(){
		return g_handlers;
	}

}

package MoseShipsBukkit.Events.Vessel.Create.Success;

import org.bukkit.block.Sign;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.Vessel.Create.ShipCreateEvent;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;

public class ShipSignCreateEvent extends ShipCreateEvent{

	public ShipSignCreateEvent(LoadableShip data) {
		super(data);
	}
	
	public Sign getSign(){
		return getShip().getLicence().get();
	}
	
	@Override
	public LoadableShip getShip() {
		return (LoadableShip)super.getShip();
	}
	
	public static HandlerList getHandlerList(){
		return g_handlers;
	}

}

package MoseShipsBukkit.Events.Create;

import org.bukkit.block.Sign;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Vessel.Common.RootTypes.ShipsData;

/**
 * implemented
 * 
 * @author Mose
 *
 */
public class ShipSignCreateEvent extends ShipCreateEvent {

	public ShipSignCreateEvent(ShipsCause cause, ShipsData data) {
		super(cause, data);
	}

	public Sign getSign() {
		return getShip().getLicence().get();
	}

	public static HandlerList getHandlerList() {
		return g_handlers;
	}

}

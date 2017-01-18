package MoseShipsBukkit.Events.Create.Fail;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Configs.ShipsConfig;
import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Vessel.Data.AbstractShipsData;
import MoseShipsBukkit.Vessel.Data.LiveShip;

/**
 * implemented
 * @author Mose
 *
 */
public class ShipCreateFailedFromConflictingNames extends ShipCreateFailedEvent {

	LiveShip g_conflict;
	
	public ShipCreateFailedFromConflictingNames(ShipsCause cause, AbstractShipsData data, Player player, LiveShip ship) {
		super(cause, data, player, ShipsConfig.CONFIG.get(String.class, ShipsConfig.PATH_MESSAGE_SIGN_CREATE_FAILED_NAME));
		g_conflict = ship;
	}
	
	public LiveShip getConflict(){
		return g_conflict;
	}
	
	public String getName(){
		return g_conflict.getName();
	}
	
	public static HandlerList getHandlerList(){
		return g_handlers;
	}
	
}

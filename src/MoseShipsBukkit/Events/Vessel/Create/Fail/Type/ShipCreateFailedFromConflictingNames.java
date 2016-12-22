package MoseShipsBukkit.Events.Vessel.Create.Fail.Type;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Configs.Files.ShipsConfig;
import MoseShipsBukkit.Events.Vessel.Create.Fail.ShipCreateFailedEvent;
import MoseShipsBukkit.Ships.ShipsData;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;

public class ShipCreateFailedFromConflictingNames extends ShipCreateFailedEvent {

	LoadableShip g_conflict;
	
	public ShipCreateFailedFromConflictingNames(ShipsData data, Player player, LoadableShip ship) {
		super(data, player, ShipsConfig.CONFIG.get(String.class, ShipsConfig.PATH_MESSAGE_SIGN_CREATE_FAILED_NAME));
		g_conflict = ship;
	}
	
	public LoadableShip getConflict(){
		return g_conflict;
	}
	
	public String getName(){
		return g_conflict.getName();
	}
	
	public static HandlerList getHandlerList(){
		return g_handlers;
	}
	
}

package MoseShipsBukkit.Events.Vessel.Create.Fail.Type;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Configs.Files.ShipsConfig;
import MoseShipsBukkit.Events.Vessel.Create.Fail.ShipCreateFailedEvent;
import MoseShipsBukkit.Ships.ShipsData;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveData;

public class ShipCreateFailedFromConflictingNames extends ShipCreateFailedEvent {

	LiveData g_conflict;
	
	public ShipCreateFailedFromConflictingNames(ShipsData data, Player player, LiveData ship) {
		super(data, player, ShipsConfig.CONFIG.get(String.class, ShipsConfig.PATH_MESSAGE_SIGN_CREATE_FAILED_NAME));
		g_conflict = ship;
	}
	
	public LiveData getConflict(){
		return g_conflict;
	}
	
	public String getName(){
		return g_conflict.getName();
	}
	
	public static HandlerList getHandlerList(){
		return g_handlers;
	}
	
}

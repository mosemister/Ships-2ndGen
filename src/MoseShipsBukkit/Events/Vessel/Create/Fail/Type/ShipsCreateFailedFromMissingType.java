package MoseShipsBukkit.Events.Vessel.Create.Fail.Type;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Configs.Files.ShipsConfig;
import MoseShipsBukkit.Events.Vessel.Create.Fail.ShipsCreateFailedEvent;
import MoseShipsBukkit.Ships.ShipsData;

public class ShipsCreateFailedFromMissingType extends ShipsCreateFailedEvent{

	String g_type;
	
	public ShipsCreateFailedFromMissingType(ShipsData data, Player player, String type) {
		super(data, player, ShipsConfig.CONFIG.get(String.class, ShipsConfig.PATH_MESSAGE_SIGN_CREATE_FAILED_TYPE));
		g_type = type;
	}
	
	public String getAttemptedType(){
		return g_type;
	}
	
	public static HandlerList getHandlerList(){
		return g_handlers;
	}

}

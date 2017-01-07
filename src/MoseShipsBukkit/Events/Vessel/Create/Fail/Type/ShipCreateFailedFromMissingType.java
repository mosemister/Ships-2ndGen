package MoseShipsBukkit.Events.Vessel.Create.Fail.Type;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Causes.ShipsCause;
import MoseShipsBukkit.Configs.Files.ShipsConfig;
import MoseShipsBukkit.Events.Vessel.Create.Fail.ShipCreateFailedEvent;
import MoseShipsBukkit.Ships.AbstractShipsData;

/**
 * implemented
 * @author Mose
 *
 */
public class ShipCreateFailedFromMissingType extends ShipCreateFailedEvent{

	String g_type;
	
	public ShipCreateFailedFromMissingType(ShipsCause cause, AbstractShipsData data, Player player, String type) {
		super(cause, data, player, ShipsConfig.CONFIG.get(String.class, ShipsConfig.PATH_MESSAGE_SIGN_CREATE_FAILED_TYPE));
		g_type = type;
	}
	
	public String getAttemptedType(){
		return g_type;
	}
	
	public static HandlerList getHandlerList(){
		return g_handlers;
	}

}

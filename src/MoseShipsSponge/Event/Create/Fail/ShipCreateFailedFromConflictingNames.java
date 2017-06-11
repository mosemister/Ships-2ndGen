package MoseShipsSponge.Event.Create.Fail;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;

import MoseShipsSponge.Configs.ShipsConfig;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class ShipCreateFailedFromConflictingNames extends ShipCreateFailedEvent{

	LiveShip g_conflict;
	
	public ShipCreateFailedFromConflictingNames(LiveShip ship, Player player, LiveShip conflict, Cause cause) {
		super(ship, player, Text.of(ShipsConfig.CONFIG.get(String.class, ShipsConfig.PATH_MESSAGE_SIGN_CREATE_FAILED_NAME)), cause);
		g_conflict = conflict;
	}
	
	public LiveShip getConflict(){
		return g_conflict;
	}
	
	public String getName(){
		return g_conflict.getName();
	}
	
	

}

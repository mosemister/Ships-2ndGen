package MoseShipsSponge.Event.Create.Fail;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;

import MoseShipsSponge.Configs.ShipsConfig;
import MoseShipsSponge.Vessel.Common.RootTypes.ShipsData;

public class ShipCreateFailedFromMissingType extends ShipCreateFailedEvent {

	String g_type;

	public ShipCreateFailedFromMissingType(ShipsData ship, Player player, String missingType, Cause cause) {
		super(ship, player,
				Text.of(ShipsConfig.CONFIG.get(String.class, ShipsConfig.PATH_MESSAGE_SIGN_CREATE_FAILED_TYPE)), cause);
		g_type = missingType;
	}

	public String getAttemptedType() {
		return g_type;
	}

}

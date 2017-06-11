package MoseShipsSponge.Movement.Result;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

import MoseShipsSponge.Configs.ShipsConfig;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class NoBlocksErrorMovementResult implements MovementResult<Boolean>{

	@Override
	public void sendMessage(LiveShip ship, CommandSource source, Boolean value) {
		String message = ShipsConfig.CONFIG.get(String.class, ShipsConfig.PATH_MESSAGE_SIZE_NONE);
		if (message == null) {
			message = "No size of ship found";
		}
		if (message.contains("%Ship%")) {
			message = message.replace("%Ship%", ship.getName());
		}
		source.sendMessage(Text.of(message));
	}

}

package MoseShipsSponge.Movement.Result;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class NotInWaterErrorMovementResult implements MovementResult<Boolean> {

	@Override
	public void sendMessage(LiveShip ship, CommandSource source, Boolean value) {
		source.sendMessage(Text.of("Ship is not in water"));
	}

}

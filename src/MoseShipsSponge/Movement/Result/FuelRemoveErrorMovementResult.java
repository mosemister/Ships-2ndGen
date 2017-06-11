package MoseShipsSponge.Movement.Result;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class FuelRemoveErrorMovementResult implements MovementResult<Boolean>{

	@Override
	public void sendMessage(LiveShip ship, CommandSource source, Boolean value) {
		source.sendMessage(Text.of("Ships failed to collect fuel to remove"));
	}

}

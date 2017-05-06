package MoseShipsSponge.Movement.Result;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class NotEnoughBlocksErrorMovementResult extends MovementResult<Integer> {

	@Override
	public void sendMessage(LiveShip ship, CommandSource source, Integer value) {
		source.sendMessage(Text.of("You need " + value + " more blocks"));
	}

}

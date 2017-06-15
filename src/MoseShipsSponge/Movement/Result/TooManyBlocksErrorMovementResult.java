package MoseShipsSponge.Movement.Result;

import org.spongepowered.api.command.CommandSource;

import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class TooManyBlocksErrorMovementResult implements MovementResult<Integer> {

	@Override
	public void sendMessage(LiveShip ship, CommandSource source, Integer value) {
		source.sendMessage(ShipsMain.format("You need " + value + " less blocks", true));
	}

}

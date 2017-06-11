package MoseShipsSponge.Movement.Result;

import org.spongepowered.api.command.CommandSource;
import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class NoSpeedSetErrorMovementResult implements MovementResult<Integer>{

	@Override
	public void sendMessage(LiveShip ship, CommandSource source, Integer value) {
		source.sendMessage(ShipsMain.format("No speed was set", true));
	}

}

package MoseShipsSponge.Movement.Result;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

import MoseShipsSponge.Movement.StoredMovement.AutoPilot;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class AutoPilotOutOfMovesErrorMovementResult implements MovementResult<AutoPilot> {

	@Override
	public void sendMessage(LiveShip ship, CommandSource source, AutoPilot value) {
		source.sendMessage(Text.of("AutoPilot ran out of moves"));
	}

}

package MoseShipsSponge.Movement.Result;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class MissingRequiredBlockErrorMovementResult implements MovementResult<BlockState> {

	@Override
	public void sendMessage(LiveShip ship, CommandSource source, BlockState value) {
		if (value.getType().equals(BlockTypes.FIRE)) {
			source.sendMessage(Text.of("You are missing a burner from your ship"));
		} else {
			source.sendMessage(Text.of("You are missing " + value.getType().getName() + " from your ship", true));
		}
	}

}

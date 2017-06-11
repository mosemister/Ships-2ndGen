package MoseShipsSponge.Movement.Result;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.command.CommandSource;

import MoseShips.Stores.TwoStore;
import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class NotEnoughPercentageErrorMovementResult implements MovementResult<TwoStore<BlockState, Float>>{

	@Override
	public void sendMessage(LiveShip ship, CommandSource source, TwoStore<BlockState, Float> value) {
		source.sendMessage(ShipsMain.format("You need " + value.getSecond() + " more blocks of " + value.getFirst().getName(), true));
	}

}

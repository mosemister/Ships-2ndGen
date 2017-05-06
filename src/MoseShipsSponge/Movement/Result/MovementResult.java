package MoseShipsSponge.Movement.Result;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.command.CommandSource;

import MoseShipsSponge.Movement.StoredMovement.AutoPilot;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public abstract class MovementResult <T extends Object> {
	
	public static final MovementResult<Boolean> FUEL_REMOVE_ERROR = new FuelRemoveErrorMovementResult();
	public static final MovementResult<Boolean> NOT_IN_WATER_ERROR = new NotInWaterErrorMovementResult();
	public static final MovementResult<AutoPilot> AUTO_PILOT_OUT_OF_MOVES = new AutoPilotOutOfMovesErrorMovementResult();
	public static final MovementResult<Boolean> OUT_OF_FUEL = new OutOfFuelErrorMovementResult();
	public static final MovementResult<BlockState> MISSING_REQUIRED_BLOCK = new MissingRequiredBlockErrorMovementResult();
	public static final MovementResult<Boolean> NO_BLOCKS = new NoBlocksErrorMovementResult();
	public static final MovementResult<Integer> NOT_ENOUGH_BLOCKS = new NotEnoughBlocksErrorMovementResult();
	
	public abstract void sendMessage(LiveShip ship, CommandSource source, T value);

}

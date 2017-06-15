package MoseShipsSponge.Movement.Result;

import java.util.List;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.command.CommandSource;

import MoseShips.Stores.TwoStore;
import MoseShipsSponge.Movement.MovingBlock;
import MoseShipsSponge.Movement.StoredMovement.AutoPilot;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public interface MovementResult<T extends Object> {

	public static final MovementResult<Boolean> FUEL_REMOVE_ERROR = new FuelRemoveErrorMovementResult();
	public static final MovementResult<Boolean> NOT_IN_WATER_ERROR = new NotInWaterErrorMovementResult();
	public static final MovementResult<AutoPilot> AUTO_PILOT_OUT_OF_MOVES = new AutoPilotOutOfMovesErrorMovementResult();
	public static final MovementResult<Boolean> OUT_OF_FUEL = new OutOfFuelErrorMovementResult();
	public static final MovementResult<BlockState> MISSING_REQUIRED_BLOCK = new MissingRequiredBlockErrorMovementResult();
	public static final MovementResult<Boolean> NO_BLOCKS = new NoBlocksErrorMovementResult();
	public static final MovementResult<Integer> NOT_ENOUGH_BLOCKS = new NotEnoughBlocksErrorMovementResult();
	public static final MovementResult<Integer> TOO_MANY_BLOCKS = new TooManyBlocksErrorMovementResult();
	public static final MovementResult<TwoStore<BlockState, Float>> NOT_ENOUGH_PERCENT = new NotEnoughPercentageErrorMovementResult();
	public static final MovementResult<Boolean> PLUGIN_CANCELLED = new PluginCancelledErrorMovementResult();
	public static final MovementResult<List<MovingBlock>> COLLIDE_WITH = new CollideWithErrorMovementResult();
	public static final MovementResult<Boolean> UNKNOWN_ERROR = new UnknownErrorMovementResult();
	public static final MovementResult<Integer> NO_SPEED_SET = new NoSpeedSetErrorMovementResult();

	public void sendMessage(LiveShip ship, CommandSource source, T value);

}

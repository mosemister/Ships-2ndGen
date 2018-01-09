package MoseShipsSponge.Vessel.RootTypes.DataShip.General;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

import MoseShipsSponge.Movement.Movement;
import MoseShipsSponge.Movement.Result.FailedMovement;
import MoseShipsSponge.Movement.StoredMovement.StoredMovement;
import MoseShipsSponge.Vessel.Common.GeneralTypes.WaterType;
import MoseShipsSponge.Vessel.Common.RootTypes.ShipsData;
import MoseShipsSponge.Vessel.RootTypes.DataShip.DataVessel;

public abstract class AbstractWaterType extends DataVessel implements WaterType{

	public AbstractWaterType(String name, Location<World> sign, Vector3i teleport) {
		super(name, sign, teleport);
	}
	
	public AbstractWaterType(ShipsData data) {
		super(data);
	}

	@Override
	public Optional<FailedMovement> teleport(StoredMovement move) {
		return Movement.teleport(this, move, BlockTypes.AIR.getDefaultState(), BlockTypes.WATER.getDefaultState(), BlockTypes.FLOWING_WATER.getDefaultState());
	}
	
	@Override
	public Optional<FailedMovement> rotateLeft(Cause cause) {
		return Movement.rotateLeft(this, cause, BlockTypes.AIR.getDefaultState(), BlockTypes.WATER.getDefaultState(), BlockTypes.FLOWING_WATER.getDefaultState());
	}
	
	@Override
	public Optional<FailedMovement> rotateRight(Cause cause) {
		return Movement.rotateRight(this, cause, BlockTypes.AIR.getDefaultState(), BlockTypes.WATER.getDefaultState(), BlockTypes.FLOWING_WATER.getDefaultState());
	}

	@Override
	public int getWaterLevel() {
		List<Location<World>> list = getBasicStructure();
		int water = 0;
		for(Location<World> loc : list) {
			Direction[] dirs = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
			for(Direction dir : dirs) {
				Location<World> loc2 = loc.getRelative(dir);
				if(loc2.getBlockType().equals(BlockTypes.WATER) || loc2.getBlockType().equals(BlockTypes.FLOWING_WATER)) {
					if (loc2.getBlockY() > water) {
						water = loc2.getBlockY();
					}
				}
			}
		}
		return 0;
	}

}

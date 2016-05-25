package MoseShipsSponge.Ships.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

import MoseShipsSponge.Causes.FailedCause;
import MoseShipsSponge.Ships.VesselTypes.ShipType;

public class Movement {
	
	public static Optional<FailedCause> move(ShipType ship, Vector3i vector, @Nullable User player){
		FailedCause.Builder builder = new FailedCause.Builder();
		List<MovingBlock> pri = new ArrayList<>();
		List<MovingBlock> norm = new ArrayList<>();
		List<MovingBlock> air = new ArrayList<>();
		for(Location<World> loc : ship.getBasicStructure()){
			MovingBlock block = new MovingBlock(loc, vector);
			switch(block.getPriority()){
				case AIR:
					air.add(block);
					break;
				case NORMAL:
					norm.add(block);
					break;
				case PRIORITY:
					pri.add(block);
					break;
			}
		}
		List<MovingBlock> blocks = new ArrayList<>();
		blocks.addAll(pri);
		blocks.addAll(norm);
		blocks.addAll(air);
		builder.add(blocks);
		return Optional.empty();
	}
	
	public static Optional<FailedCause> move(ShipType ship, int X, int Y, int Z, @Nullable User player){
		return move(ship, new Vector3i(X, Y, Z), player);
	}
	
	public static Optional<FailedCause> rotateRight(ShipType ship, @Nullable User player){
		return Optional.empty();
	}
	
	public static Optional<FailedCause> rotateLeft(ShipType ship, @Nullable User player){
		return Optional.empty();
	}
	
	public static Optional<FailedCause> teleport(ShipType ship, Location<World> loc, User player){
		return Optional.empty();
	}

}

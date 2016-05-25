package MoseShipsSponge.Ships.Movement;

import java.util.Optional;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;

public class MovingBlock {
	
	Location<World> ORIGIN;
	Location<World> MOVING_TO;
	BlockState STATE;
	
	public MovingBlock(Location<World> original, Location<World> moving){
		ORIGIN = original;
		MOVING_TO = moving;
		STATE = original.getBlock();
	}
	
	public MovingBlock(Location<World> original, int X, int Y, int Z){
		ORIGIN = original;
		MOVING_TO = original.add(X, Y, Z);
		STATE = original.getBlock();
	}
	
	public MovingBlock(Location<World> original, Vector3i vector){
		ORIGIN = original;
		MOVING_TO = original.add(vector.toDouble());
		STATE = original.getBlock();
	}
	
	public Priority getPriority(){
		return Priority.getType(STATE.getType());
	}
	
	public MovingBlock rotateLeft(Location<World> centre){
		rotate(true);
		int shift = centre.getBlockX() - centre.getBlockZ();
		double symmetry = centre.getBlockX();

		double X = MOVING_TO.getX() - (MOVING_TO.getX() - symmetry) * 2.0D - shift;
		double Y = MOVING_TO.getY();
		double Z = MOVING_TO.getZ() + shift;
		MOVING_TO.setPosition(new Vector3d(X, Y, Z));
		return this;
	}
	
	public MovingBlock rotateRight(Location<World> centre){
		rotate(false);
		int shift = centre.getBlockX() - centre.getBlockZ();
		double symmetry = centre.getBlockZ();

		double X = MOVING_TO.getX() - shift;
		double Y = MOVING_TO.getY();
		double Z = MOVING_TO.getZ() - (MOVING_TO.getZ() - symmetry) * 2.0 + shift;
		MOVING_TO.setPosition(new Vector3d(X, Y, Z));
		return this;
	}
	
	private void rotate(boolean left){
		Optional<Direction> opDir = STATE.get(Keys.DIRECTION);
		if(opDir.isPresent()){
			Direction dir = opDir.get();
			switch(dir){
				case EAST:
					if(left){
						STATE.with(Keys.DIRECTION, Direction.NORTH);
						return;
					}else{
						STATE.with(Keys.DIRECTION, Direction.SOUTH);
						return;
					}
				case NORTH:
					if(left){
						STATE.with(Keys.DIRECTION, Direction.WEST);
						return;
					}else{
						STATE.with(Keys.DIRECTION, Direction.EAST);
						return;
					}
				case SOUTH:
					if(left){
						STATE.with(Keys.DIRECTION, Direction.EAST);
						return;
					}else{
						STATE.with(Keys.DIRECTION, Direction.WEST);
						return;
					}
				case WEST:
					if(left){
						STATE.with(Keys.DIRECTION, Direction.SOUTH);
						return;
					}else{
						STATE.with(Keys.DIRECTION, Direction.NORTH);
						return;
					}
				default:
					return;
			}
		}
	}
	
	public enum Priority{
		NORMAL,
		PRIORITY,
		AIR;
		
		public static Priority getType(BlockType type){
			if((type.equals(BlockTypes.TORCH)) ||
					(type.equals(BlockTypes.FIRE)) ||
					(type.equals(BlockTypes.REDSTONE_WIRE)) ||
					(type.equals(BlockTypes.ACACIA_DOOR)) ||
					(type.equals(BlockTypes.BIRCH_DOOR)) ||
					(type.equals(BlockTypes.DARK_OAK_DOOR)) ||
					(type.equals(BlockTypes.IRON_DOOR)) ||
					(type.equals(BlockTypes.JUNGLE_DOOR)) ||
					(type.equals(BlockTypes.SPRUCE_DOOR)) ||
					(type.equals(BlockTypes.WOODEN_DOOR)) ||
					(type.equals(BlockTypes.LADDER)) ||
					(type.equals(BlockTypes.WALL_SIGN)) ||
					(type.equals(BlockTypes.LEVER)) ||
					(type.equals(BlockTypes.STONE_PRESSURE_PLATE)) ||
					(type.equals(BlockTypes.WOODEN_PRESSURE_PLATE)) ||
					(type.equals(BlockTypes.HEAVY_WEIGHTED_PRESSURE_PLATE)) ||
					(type.equals(BlockTypes.LIGHT_WEIGHTED_PRESSURE_PLATE)) ||
					(type.equals(BlockTypes.UNPOWERED_REPEATER)) ||
					(type.equals(BlockTypes.POWERED_REPEATER)) ||
					(type.equals(BlockTypes.UNPOWERED_COMPARATOR)) ||
					(type.equals(BlockTypes.POWERED_COMPARATOR)) ||
					(type.equals(BlockTypes.UNLIT_REDSTONE_TORCH)) ||
					(type.equals(BlockTypes.REDSTONE_TORCH)) ||
					(type.equals(BlockTypes.STONE_BUTTON)) ||
					(type.equals(BlockTypes.TRAPDOOR)) ||
					(type.equals(BlockTypes.TRIPWIRE_HOOK)) ||
					(type.equals(BlockTypes.TRIPWIRE)) ||
					(type.equals(BlockTypes.IRON_TRAPDOOR)) ||
					(type.equals(BlockTypes.WALL_BANNER))){
				return PRIORITY;
			}else if(type.equals(BlockTypes.AIR)){
				return AIR;
			}else{
				return NORMAL;
			}
		}
	}

}

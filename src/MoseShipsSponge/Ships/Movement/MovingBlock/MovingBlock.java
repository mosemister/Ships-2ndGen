package MoseShipsSponge.Ships.Movement.MovingBlock;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;

import MoseShipsSponge.Configs.Files.BlockList;
import MoseShipsSponge.Ships.Movement.MovementType;
import MoseShipsSponge.Ships.Movement.Collide.CollideType;
import MoseShipsSponge.Ships.Movement.MovingBlock.Block.AttachableSnapshot;
import MoseShipsSponge.Ships.Movement.MovingBlock.Block.BlockSnapshot;
import MoseShipsSponge.Ships.Movement.MovingBlock.Block.RotatableSnapshot;
import MoseShipsSponge.Ships.Movement.MovingBlock.Block.SpecialSnapshot;
import MoseShipsSponge.Ships.Movement.Movement.Rotate;

public class MovingBlock {

	Location MOVING_TO;
	BlockSnapshot STATE;

	public MovingBlock(Block original, Location moving) {
		MOVING_TO = moving;
		STATE = BlockSnapshot.createSnapshot(original);
	}

	public MovingBlock(Block original, int X, int Y, int Z) {
		MOVING_TO = original.getRelative(X, Y, Z).getLocation();
		STATE = BlockSnapshot.createSnapshot(original);
	}

	public Location getOrigin() {
		return STATE.getLocation();
	}

	public Location getMovingTo() {
		return MOVING_TO;
	}
	
	public Material getMaterial(){
		return STATE.getMaterial();
	}
	
	public MaterialData getData(){
		return STATE.getData();
	}
	
	@SuppressWarnings("deprecation")
	public byte getDataValue(){
		return STATE.getData().getData();
	}

	public MovementType getMovementType() {
		if ((getOrigin().getBlockX() == MOVING_TO.getBlockX()) && (getOrigin().getBlockZ() == MOVING_TO.getBlockZ())) {
			return MovementType.FORWARDS;
		} else if ((getOrigin().getBlockX() > MOVING_TO.getBlockX()) || (getOrigin().getBlockZ() > MOVING_TO.getBlockZ())) {
			return MovementType.ROTATE_RIGHT;
		} else {
			return MovementType.ROTATE_LEFT;
		}
	}

	public MovingBlock clearOriginalBlock() {
		STATE.placeBlock(STATE.getLocation().getBlock(), Material.AIR, (byte)0);
		return this;
	}

	public MovingBlock clearMovingToBlock() {
		STATE.placeBlock(MOVING_TO.getBlock(), Material.AIR, (byte)0);
		return this;
	}

	public MovingBlock move() {
		STATE.placeBlock(MOVING_TO.getBlock());
		return this;
	}

	public MovingBlock replaceOriginalBlock(Material type, byte data) {
		STATE.placeBlock(STATE.getLocation().getBlock(), type, data);
		return this;
	}

	public MovingBlock replaceMovingToBlock(Material type, byte data) {
		STATE.placeBlock(MOVING_TO.getBlock(), type, data);
		return this;
	}

	public Priority getPriority() {
		if(STATE instanceof AttachableSnapshot){
			return Priority.PRIORITY;
		}else if(STATE instanceof SpecialSnapshot){
			return Priority.SPECIAL;
		}else if(STATE.getMaterial().equals(Material.AIR)){
			return Priority.AIR;
		}else{
			return Priority.NORMAL;
		}
	}

	@SuppressWarnings("deprecation")
	public CollideType getCollision(List<Block> ignore) {
		if (ignore.contains(MOVING_TO.getBlock())) {
			return CollideType.NONE;
		} else if (BlockList.BLOCK_LIST.contains(MOVING_TO.getBlock().getType(), MOVING_TO.getBlock().getData(), BlockList.ListType.MATERIALS)) {
			return CollideType.COLLIDE;
		} else if (BlockList.BLOCK_LIST.contains(MOVING_TO.getBlock().getType(), MOVING_TO.getBlock().getData(), BlockList.ListType.RAM)) {
			return CollideType.RAM;
		} else {
			return CollideType.NONE;
		}
	}

	public MovingBlock rotate(Rotate rotate, Block centre) {
		switch (rotate) {
			case LEFT:
				return rotateLeft(centre);
			case RIGHT:
				return rotateRight(centre);
		}
		return this;
	}

	public MovingBlock rotateLeft(Block centre) {
		rotate(true);
		int shift = centre.getX() - centre.getZ();
		double symmetry = centre.getX();

		double X = MOVING_TO.getX() - (MOVING_TO.getX() - symmetry) * 2.0D - shift;
		double Y = MOVING_TO.getY();
		double Z = MOVING_TO.getZ() + shift;
		MOVING_TO = new Location(MOVING_TO.getWorld(), X, Y, Z);
		return this;
	}

	public MovingBlock rotateRight(Block centre) {
		rotate(false);
		int shift = centre.getX() - centre.getZ();
		double symmetry = centre.getZ();

		double X = MOVING_TO.getX() - shift;
		double Y = MOVING_TO.getY();
		double Z = MOVING_TO.getZ() - (MOVING_TO.getZ() - symmetry) * 2.0 + shift;
		MOVING_TO = new Location(MOVING_TO.getWorld(), X, Y, Z);
		return this;
	}

	@SuppressWarnings("deprecation")
	private void rotate(boolean left) {
		if(STATE instanceof RotatableSnapshot){
			RotatableSnapshot shot = (RotatableSnapshot)STATE;
			if(left){
				STATE.getData().setData(shot.getRotateLeft());
			}else{
				STATE.getData().setData(shot.getRotateRight());
			}
		}
	}

	public static List<MovingBlock> setPriorityOrder(List<MovingBlock> blocks) {
		List<MovingBlock> normalList = new ArrayList<MovingBlock>();
		List<MovingBlock> airList = new ArrayList<MovingBlock>();
		List<MovingBlock> specList = new ArrayList<MovingBlock>();
		List<MovingBlock> priList = new ArrayList<MovingBlock>();
		for(MovingBlock block : blocks){
			switch(block.getPriority()){
			case NORMAL: normalList.add(block);
			case PRIORITY: priList.add(block);
			case AIR: airList.add(block);
			case SPECIAL: specList.add(block);
			}
		}
		List<MovingBlock> retList = new ArrayList<MovingBlock>();
		retList.addAll(priList);
		retList.addAll(specList);
		retList.addAll(normalList);
		retList.addAll(airList);
		return retList;
	}

	public enum Priority {
		NORMAL,
		PRIORITY,
		SPECIAL,
		AIR;
	}

}

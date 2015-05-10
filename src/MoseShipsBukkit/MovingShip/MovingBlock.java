package MoseShipsBukkit.MovingShip;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.StillShip.SpecialBlock;
import MoseShipsBukkit.StillShip.Vessel;

public class MovingBlock {
	
	Block BLOCK;
	SpecialBlock SPE_BLOCK;
	int ID;
	byte DATA;
	Location MOVETO;
	
	@SuppressWarnings("deprecation")
	public MovingBlock(Block block, Vessel vessel, MovementMethod move){
		BLOCK = block;
		ID = block.getTypeId();
		DATA = block.getData();
		BlockFace facing = vessel.getFacingDirection();
		if (move.equals(MovementMethod.MOVE_FORWARD)){
			Block block2 = block.getRelative(facing, move.getSpeed());
			MOVETO = block2.getLocation();
		}else if (move.equals(MovementMethod.MOVE_BACKWARD)){
			Block block2 = block.getRelative(facing.getOppositeFace(), move.getSpeed());
			MOVETO = block2.getLocation();
		}else if (move.equals(MovementMethod.MOVE_LEFT)){
			Block block2 = block.getRelative(Ships.getSideFace(facing, true), move.getSpeed());
			MOVETO = block2.getLocation();
		}else if (move.equals(MovementMethod.MOVE_RIGHT)){
			Block block2 = block.getRelative(Ships.getSideFace(facing, false), move.getSpeed());
			MOVETO = block2.getLocation();
		}else if (move.equals(MovementMethod.MOVE_UP)){
			Block block2 = block.getRelative(0, move.getSpeed(), 0);
			MOVETO = block2.getLocation();
		}else if (move.equals(MovementMethod.MOVE_DOWN)){
			Block block2 = block.getRelative(0, -move.getSpeed(), 0);
			MOVETO = block2.getLocation();
		}else if (move.equals(MovementMethod.ROTATE_RIGHT)){
			Block block2 = vessel.getSign().getBlock();
			int shift = block2.getLocation().getBlockX() - block2.getLocation().getBlockZ();
			double symmetry = block2.getLocation().getBlockZ();
			
			double X = block.getLocation().getX() - shift;
			double Y = block.getLocation().getY();
			double Z = block.getLocation().getZ() - (block.getLocation().getZ() - symmetry)*2.0D + shift;
			Location loc = new Location(block.getWorld(), Z, Y, X);
			MOVETO = loc;
		}else if (move.equals(MovementMethod.ROTATE_LEFT)){
			int shift = block.getLocation().getBlockX() - block.getLocation().getBlockZ();
			double symmetry = block.getLocation().getBlockX();
			
			double X = block.getLocation().getX() - (block.getLocation().getX() - symmetry)*2.0D + shift;
			double Y = block.getLocation().getY();
			double Z = block.getLocation().getZ() + shift;
			Location loc = new Location(block.getWorld(), Z, Y, X);
			MOVETO = loc;
		}
	}
	
	@SuppressWarnings("deprecation")
	public MovingBlock(SpecialBlock sBlock, Vessel vessel, MovementMethod move){
		Block block = sBlock.getBlock();
		SPE_BLOCK = sBlock;
		BLOCK = block;
		ID = block.getTypeId();
		DATA = block.getData();
		BlockFace facing = vessel.getFacingDirection();
		if (move.equals(MovementMethod.MOVE_FORWARD)){
			Block block2 = block.getRelative(facing, move.getSpeed());
			MOVETO = block2.getLocation();
		}else if (move.equals(MovementMethod.MOVE_BACKWARD)){
			Block block2 = block.getRelative(facing.getOppositeFace(), move.getSpeed());
			MOVETO = block2.getLocation();
		}else if (move.equals(MovementMethod.MOVE_LEFT)){
			Block block2 = block.getRelative(Ships.getSideFace(facing, true), move.getSpeed());
			MOVETO = block2.getLocation();
		}else if (move.equals(MovementMethod.MOVE_RIGHT)){
			Block block2 = block.getRelative(Ships.getSideFace(facing, false), move.getSpeed());
			MOVETO = block2.getLocation();
		}else if (move.equals(MovementMethod.MOVE_UP)){
			Block block2 = block.getRelative(0, move.getSpeed(), 0);
			MOVETO = block2.getLocation();
		}else if (move.equals(MovementMethod.MOVE_DOWN)){
			Block block2 = block.getRelative(0, -move.getSpeed(), 0);
			MOVETO = block2.getLocation();
		}else if (move.equals(MovementMethod.ROTATE_RIGHT)){
			Block block2 = vessel.getSign().getBlock();
			int shift = block2.getLocation().getBlockX() - block2.getLocation().getBlockZ();
			double symmetry = block2.getLocation().getBlockZ();
			
			double X = block.getLocation().getX() - shift;
			double Y = block.getLocation().getY();
			double Z = block.getLocation().getZ() - (block.getLocation().getZ() - symmetry)*2.0D + shift;
			Location loc = new Location(block.getWorld(), Z, Y, X);
			MOVETO = loc;
		}else if (move.equals(MovementMethod.ROTATE_LEFT)){
			int shift = block.getLocation().getBlockX() - block.getLocation().getBlockZ();
			double symmetry = block.getLocation().getBlockX();
			
			double X = block.getLocation().getX() - (block.getLocation().getX() - symmetry)*2.0D + shift;
			double Y = block.getLocation().getY();
			double Z = block.getLocation().getZ() + shift;
			Location loc = new Location(block.getWorld(), Z, Y, X);
			MOVETO = loc;
		}
	}
	
	public SpecialBlock getSpecialBlock(){
		return SPE_BLOCK;
	}
	
	public int getId(){
		return ID;
	}
	
	public byte getData(){
		return DATA;
	}
	
	public Location getMovingTo(){
		return MOVETO;
	}
	
	public Block getBlock(){
		return BLOCK;
	}
}

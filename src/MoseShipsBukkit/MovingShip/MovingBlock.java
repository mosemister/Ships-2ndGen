package MoseShipsBukkit.MovingShip;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.BlockHandler.BlockHandler;
import MoseShipsBukkit.StillShip.Vessel.BaseVessel;

public class MovingBlock {

	BlockHandler handler;
	Location MOVETO;

	public MovingBlock(Block block, BaseVessel vessel, MovementMethod move) {
		this(BlockHandler.getBlockHandler(block), vessel, move);
	}

	public MovingBlock(BlockHandler sBlock, BaseVessel vessel, MovementMethod move) {
		handler = sBlock;
		move(handler.getBlock(), vessel, move);
	}

	public MovingBlock(Block block, Location moveTo) {
		this(BlockHandler.getBlockHandler(block), moveTo);
	}

	public MovingBlock(BlockHandler sBlock, Location moveTo) {
		handler = sBlock;
		MOVETO = moveTo;
	}

	private void move(Block block, BaseVessel vessel, MovementMethod move) {
		BlockFace facing = vessel.getFacingDirection();
		Block block2 = null;
		switch (move) {
		case MOVE_FORWARD:
			block2 = block.getRelative(facing, move.getSpeed());
			MOVETO = block2.getLocation();
			break;
		case MOVE_BACKWARD:
			block2 = block.getRelative(facing.getOppositeFace(), move.getSpeed());
			MOVETO = block2.getLocation();
			break;

		case MOVE_LEFT:
			block2 = block.getRelative(Ships.getSideFace(facing, true), move.getSpeed());
			MOVETO = block2.getLocation();
			break;
		case MOVE_RIGHT:
			block2 = block.getRelative(Ships.getSideFace(facing, false), move.getSpeed());
			MOVETO = block2.getLocation();
			break;

		case MOVE_UP:
			block2 = block.getRelative(0, move.getSpeed(), 0);
			MOVETO = block2.getLocation();
			break;
		case MOVE_DOWN:
			block2 = block.getRelative(0, -move.getSpeed(), 0);
			MOVETO = block2.getLocation();
			break;

		case ROTATE_RIGHT: {
			block2 = vessel.getLocation().getBlock();
			int shift = block2.getLocation().getBlockX() - block2.getLocation().getBlockZ();
			double symmetry = block2.getLocation().getBlockZ();

			double X = block.getLocation().getX() - shift;
			double Y = block.getLocation().getY();
			double Z = block.getLocation().getZ() - (block.getLocation().getZ() - symmetry) * 2.0D + shift;
			Location loc = new Location(block.getWorld(), Z, Y, X);
			MOVETO = loc;
			break;
		}
		case ROTATE_LEFT:
			block2 = vessel.getLocation().getBlock();
			int shift = block2.getLocation().getBlockX() - block2.getLocation().getBlockZ();
			double symmetry = block2.getLocation().getBlockX();

			double X = block.getLocation().getX() - (block.getLocation().getX() - symmetry) * 2.0D - shift;
			double Y = block.getLocation().getY();
			double Z = block.getLocation().getZ() + shift;
			Location loc = new Location(block.getWorld(), Z, Y, X);
			MOVETO = loc;
			break;

		case MOVE_POSITIVE_X:
			block2 = block.getRelative(move.getSpeed(), 0, 0);
			MOVETO = block2.getLocation();
			break;
		case MOVE_NEGATIVE_X:
			block2 = block.getRelative(-move.getSpeed(), 0, 0);
			MOVETO = block2.getLocation();
			break;

		case MOVE_POSITIVE_Z:
			block2 = block.getRelative(0, 0, move.getSpeed());
			MOVETO = block2.getLocation();
			break;
		case MOVE_NEGATIVE_Z:
			block2 = block.getRelative(0, 0, -move.getSpeed());
			MOVETO = block2.getLocation();
			break;

		case TELEPORT:
			break;

		default:
			Bukkit.getConsoleSender().sendMessage(Ships.runShipsMessage(
					"Something went wrong, maybe a custom [API] MovementMethod. (" + move + ")", true));
			break;
		}
	}

	public BlockHandler getHandle() {
		return handler;
	}

	public Location getMovingTo() {
		return MOVETO;
	}

	public Block getBlock() {
		return handler.getBlock();
	}

	public static List<Block> convertToBlockArray(List<MovingBlock> blocks) {
		List<Block> blocks2 = new ArrayList<Block>();
		for (MovingBlock block : blocks) {
			blocks2.add(block.getBlock());
		}
		return blocks2;
	}
}

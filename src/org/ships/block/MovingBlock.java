package org.ships.block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.plugin.Ships;
import org.ships.ship.Ship;
import org.ships.ship.movement.MovementMethod;

public class MovingBlock {

	BlockHandler<? extends org.bukkit.block.BlockState> handler;
	Location MOVETO;

	public MovingBlock(Block block, Ship vessel, MovementMethod move) {
		this(BlockHandler.getBlockHandler(block), vessel, move);
	}

	public MovingBlock(BlockHandler<? extends org.bukkit.block.BlockState> sBlock, Ship vessel, MovementMethod move) {
		handler = sBlock;
		move(handler.getBlock(), vessel, move);
	}

	public MovingBlock(Block block, Location moveTo) {
		this(BlockHandler.getBlockHandler(block), moveTo);
	}

	public MovingBlock(BlockHandler<? extends org.bukkit.block.BlockState> sBlock, Location moveTo) {
		handler = sBlock;
		MOVETO = moveTo;
	}

	private void move(Block block, Ship vessel, MovementMethod move) {
		//BlockFace facing = vessel.getFacingDirection();
		BlockData data = vessel.getLocation().getBlock().getBlockData();
		if(!(data instanceof Rotatable)) {
			return;
		}
		BlockFace facing = ((Rotatable)data).getRotation().getOppositeFace();
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

	public BlockHandler<? extends org.bukkit.block.BlockState> getHandle() {
		return handler;
	}

	public Location getMovingTo() {
		return MOVETO;
	}

	public Block getBlock() {
		return handler.getBlock();
	}
	
	public static Set<MovingBlock> convert(Ship vessel, MovementMethod method) {
		List<MovingBlock> blocks = new ArrayList<>();
		vessel.getStructure().getAllBlocks().stream().forEach(b -> blocks.add(new MovingBlock(b.getBlock(), vessel, method)));
		return new HashSet<>(blocks);
	}

	public static List<Block> convertToBlockArray(List<MovingBlock> blocks) {
		List<Block> blocks2 = new ArrayList<Block>();
		for (MovingBlock block : blocks) {
			blocks2.add(block.getBlock());
		}
		return blocks2;
	}
}

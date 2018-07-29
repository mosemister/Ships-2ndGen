package org.ships.block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.plugin.Ships;
import org.ships.ship.Ship;
import org.ships.ship.movement.MovementMethod;

public class MovingBlock {

	protected BlockHandler<? extends BlockState> handler;
	protected Location movingTo;

	public MovingBlock(Block block, Ship vessel, MovementMethod move) {
		this(BlockHandler.getBlockHandler(block), vessel, move);
	}

	public MovingBlock(BlockHandler<? extends BlockState> sBlock, Ship vessel, MovementMethod move) {
		this.handler = sBlock;
		this.move(this.handler.getBlock(), vessel, move);
	}

	public MovingBlock(Block block, Location moveTo) {
		this(BlockHandler.getBlockHandler(block), moveTo);
	}

	public MovingBlock(BlockHandler<? extends BlockState> sBlock, Location moveTo) {
		this.handler = sBlock;
		this.movingTo = moveTo;
	}

	private void move(Block block, Ship vessel, MovementMethod move, BlockFace facing) {
		Block block2 = null;
		switch (move) {
			case MOVE_LEFT: {
				block2 = block.getRelative(Ships.getSideFace(facing, true), move.getSpeed());
				this.movingTo = block2.getLocation();
				break;
			}
			case MOVE_RIGHT: {
				block2 = block.getRelative(Ships.getSideFace(facing, false), move.getSpeed());
				this.movingTo = block2.getLocation();
				break;
			}
			case MOVE_UP: {
				block2 = block.getRelative(0, move.getSpeed(), 0);
				this.movingTo = block2.getLocation();
				break;
			}
			case MOVE_DOWN: {
				block2 = block.getRelative(0, -move.getSpeed(), 0);
				this.movingTo = block2.getLocation();
				break;
			}
			case ROTATE_RIGHT: {
				block2 = vessel.getLocation().getBlock();
				int shift = block2.getLocation().getBlockX() - block2.getLocation().getBlockZ();
				double symmetry = block2.getLocation().getBlockZ();
				double X = block.getLocation().getX() - shift;
				double Y = block.getLocation().getY();
				double Z = block.getLocation().getZ() - (block.getLocation().getZ() - symmetry) * 2.0 + shift;
				this.movingTo = new Location(block.getWorld(), Z, Y, X);
				break;
			}
			case ROTATE_LEFT: {
				block2 = vessel.getLocation().getBlock();
				int shift = block2.getLocation().getBlockX() - block2.getLocation().getBlockZ();
				double symmetry = block2.getLocation().getBlockX();
				double X = block.getLocation().getX() - (block.getLocation().getX() - symmetry) * 2.0 - shift;
				double Y = block.getLocation().getY();
				double Z = block.getLocation().getZ() + shift;
				this.movingTo = new Location(block.getWorld(), Z, Y, X);
				break;
			}
			case MOVE_POSITIVE_X: {
				block2 = block.getRelative(move.getSpeed(), 0, 0);
				this.movingTo = block2.getLocation();
				break;
			}
			case MOVE_NEGATIVE_X: {
				block2 = block.getRelative(-move.getSpeed(), 0, 0);
				this.movingTo = block2.getLocation();
				break;
			}
			case MOVE_POSITIVE_Z: {
				block2 = block.getRelative(0, 0, move.getSpeed());
				this.movingTo = block2.getLocation();
				break;
			}
			case MOVE_NEGATIVE_Z: {
				block2 = block.getRelative(0, 0, -move.getSpeed());
				this.movingTo = block2.getLocation();
				break;
			}
			case TELEPORT: {
				break;
			}
			default: {
				Bukkit.getConsoleSender().sendMessage(Ships.runShipsMessage("Something went wrong, maybe a custom [API] MovementMethod. (" + (move) + ")", true));
			}
		}
	}

	private void move(Block block, Ship vessel, MovementMethod move) {
		BlockData data = vessel.getLocation().getBlock().getBlockData();
		if (!(data instanceof Rotatable)) {
			this.move(block, vessel, move, null);
			return;
		}
		BlockFace facing = ((Rotatable) data).getRotation().getOppositeFace();
		this.move(block, vessel, move, facing);
	}

	public BlockHandler<? extends BlockState> getHandle() {
		return this.handler;
	}

	public Location getMovingTo() {
		return this.movingTo;
	}

	public Block getBlock() {
		return this.handler.getBlock();
	}

	public static Set<MovingBlock> convert(Ship vessel, MovementMethod method) {
		Set<MovingBlock> blocks = new HashSet<>();
		vessel.getStructure().getAllBlocks().stream().forEach(b -> {
			blocks.add(new MovingBlock(b.getBlock(), vessel, method));
		});
		return blocks;
	}

	public static List<Block> convertToBlockArray(List<MovingBlock> blocks) {
		ArrayList<Block> blocks2 = new ArrayList<Block>();
		for (MovingBlock block : blocks) {
			blocks2.add(block.getBlock());
		}
		return blocks2;
	}

}

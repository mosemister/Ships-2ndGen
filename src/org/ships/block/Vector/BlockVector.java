package org.ships.block.Vector;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.ship.Ship;

public class BlockVector {
	int X;
	int Y;
	int Z;

	public BlockVector(Block first, Block second) {
		this(first.getX(), first.getY(), first.getZ(), second);
	}

	public BlockVector(int x, int y, int z, Block block) {
		Block block2 = block.getRelative(x, y, z);
		this.X = block.getX() - block2.getX();
		this.Y = block.getY() - block2.getY();
		this.Z = block.getZ() - block2.getZ();
	}

	public BlockVector(int x, int y, int z) {
		this.X = x;
		this.Y = y;
		this.Z = z;
	}

	public int getX() {
		return this.X;
	}

	public BlockVector setX(int x) {
		this.X = x;
		return this;
	}

	public int getY() {
		return this.Y;
	}

	public BlockVector setY(int y) {
		this.Y = y;
		return this;
	}

	public int getZ() {
		return this.Z;
	}

	public BlockVector setZ(int z) {
		this.Z = z;
		return this;
	}

	public Block getBlock(Block originalLocation) {
		Block block = originalLocation.getRelative(this.getX(), this.getY(), this.getZ());
		return block;
	}

	@Override
	public String toString() {
		return "" + this.getX() + "," + this.getY() + "," + this.getZ();
	}

	public static List<BlockVector> convert(Ship vessel) {
		ArrayList<BlockVector> vectors = new ArrayList<BlockVector>();
		Block block2 = vessel.getLocation().getBlock();
		for (BlockHandler<? extends BlockState> block : vessel.getStructure().getAllBlocks()) {
			BlockVector vector = new BlockVector(block2, block.getBlock());
			vectors.add(vector);
		}
		return vectors;
	}
}

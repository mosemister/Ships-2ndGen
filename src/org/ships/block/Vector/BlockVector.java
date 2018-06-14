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
		X = block.getX() - block2.getX();
		Y = block.getY() - block2.getY();
		Z = block.getZ() - block2.getZ();
	}

	public BlockVector(int x, int y, int z) {
		X = x;
		Y = y;
		Z = z;
	}

	public int getX() {
		return X;
	}

	public BlockVector setX(int x) {
		X = x;
		return this;
	}

	public int getY() {
		return Y;
	}

	public BlockVector setY(int y) {
		Y = y;
		return this;
	}

	public int getZ() {
		return Z;
	}

	public BlockVector setZ(int z) {
		Z = z;
		return this;
	}

	public Block getBlock(Block originalLocation) {
		Block block = originalLocation.getRelative(getX(), getY(), getZ());
		return block;
	}

	public String toString() {
		return getX() + "," + getY() + "," + getZ();
	}

	public static List<BlockVector> convert(Ship vessel) {
		List<BlockVector> vectors = new ArrayList<BlockVector>();
		Block block2 = vessel.getLocation().getBlock();
		for (BlockHandler<? extends BlockState> block : vessel.getStructure().getAllBlocks()) {
			BlockVector vector = new BlockVector(block2, block.getBlock());
			vectors.add(vector);
		}
		return vectors;
	}

}

package MoseShipsBukkit.StillShip.Vectors;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;

import MoseShipsBukkit.BlockHandler.BlockHandler;
import MoseShipsBukkit.StillShip.Vessel.BaseVessel;

public class BlockVector {

	int X;
	int Y;
	int Z;

	Material MATERIAL;
	byte DATA;

	@SuppressWarnings("deprecation")
	public BlockVector(Block first, Block second) {
		int x = first.getX() - second.getX();
		int y = first.getY() - second.getY();
		int z = first.getZ() - second.getZ();

		X = x;
		Y = y;
		Z = z;

		MATERIAL = second.getType();
		DATA = second.getData();
	}

	@SuppressWarnings("deprecation")
	public BlockVector(int x, int y, int z, Block block) {
		X = x;
		Y = y;
		Z = z;

		MATERIAL = block.getType();
		DATA = block.getData();
	}

	public BlockVector(int x, int y, int z, Material material, byte data) {
		X = x;
		Y = y;
		Z = z;

		MATERIAL = material;
		DATA = data;
	}

	@SuppressWarnings("deprecation")
	public BlockVector(int x, int y, int z, BaseVessel vessel) {
		Block block = vessel.getLocation().getBlock();
		Block block2 = block.getRelative(x, y, z);
		X = x;
		Y = y;
		Z = z;

		MATERIAL = block2.getType();
		DATA = block2.getData();
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

	public static List<BlockVector> convert(BaseVessel vessel) {
		List<BlockVector> vectors = new ArrayList<BlockVector>();
		Block block2 = vessel.getLocation().getBlock();
		for (BlockHandler block : vessel.getStructure().getAllBlocks()) {
			BlockVector vector = new BlockVector(block2, block.getBlock());
			vectors.add(vector);
		}
		return vectors;
	}

}

package MoseShipsBukkit.ShipBlock;

import org.bukkit.block.Block;

public class ShipVector {

	int g_x;
	int g_y;
	int g_z;

	public ShipVector() {

	}

	public ShipVector(Block base, Block other) {
		g_x = base.getX() - other.getX();
		g_y = base.getY() - other.getY();
		g_z = base.getZ() - other.getZ();
	}

	public ShipVector(int x, int y, int z) {
		g_x = x;
		g_y = y;
		g_z = z;
	}

	public int getX() {
		return g_x;
	}

	public int getY() {
		return g_y;
	}

	public int getZ() {
		return g_z;
	}

	public int get(VectorCoord vector) {
		switch (vector) {
		case X:
			return getX();
		case Y:
			return getY();
		case Z:
			return getZ();
		}
		return 0;
	}

	public ShipVector setX(int x) {
		g_x = x;
		return this;
	}

	public ShipVector setY(int y) {
		g_y = y;
		return this;
	}

	public ShipVector setZ(int z) {
		g_z = z;
		return this;
	}

	public ShipVector set(VectorCoord vector, int value) {
		switch (vector) {
		case X:
			return setX(value);
		case Y:
			return setY(value);
		case Z:
			return setZ(value);
		}
		return this;
	}

	public static enum VectorCoord {
		X, Y, Z;
	}

}

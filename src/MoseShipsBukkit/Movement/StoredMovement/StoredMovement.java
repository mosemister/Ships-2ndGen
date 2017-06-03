package MoseShipsBukkit.Movement.StoredMovement;

import org.bukkit.Location;
import org.bukkit.block.Block;

import MoseShipsBukkit.Movement.Type.RotateType;
import MoseShipsBukkit.Utils.SOptional;

public class StoredMovement {

	Integer X, Y, Z;
	Location TELEPORT;
	RotateType ROTATE;

	public StoredMovement(Location teleport, RotateType rotate, Integer x, Integer y, Integer z) {
		X = x;
		Y = y;
		Z = z;
		TELEPORT = teleport;
		ROTATE = rotate;
	}

	public SOptional<Integer> getX() {
		return new SOptional<Integer>(X);
	}

	public SOptional<Integer> getY() {
		return new SOptional<Integer>(Y);
	}

	public SOptional<Integer> getZ() {
		return new SOptional<Integer>(Z);
	}

	public SOptional<Location> getTeleportTo() {
		return new SOptional<Location>(TELEPORT);
	}

	public SOptional<RotateType> getRotation() {
		return new SOptional<RotateType>(ROTATE);
	}

	public Block getEndResult(Block start, Block centre) {
		if(TELEPORT != null){
			start = TELEPORT.getBlock();
		}
		if(ROTATE != null){
			if(ROTATE.equals(RotateType.LEFT)){
				int shift = centre.getX() - centre.getZ();
				double symmetry = centre.getX();

				double X = start.getX() - (start.getX() - symmetry) * 2.0D - shift;
				double Y = start.getY();
				double Z = start.getZ() + shift;
				start = new Location(start.getWorld(), Z, Y, X).getBlock();
			}else if (ROTATE.equals(RotateType.RIGHT)){
				int shift = centre.getX() - centre.getZ();
				double symmetry = centre.getZ();

				double X = start.getX() - shift;
				double Y = start.getY();
				double Z = start.getZ() - (start.getZ() - symmetry) * 2.0 + shift;
				start = new Location(start.getWorld(), Z, Y, X).getBlock();
			}
		}
		if (X != null){
			start = start.getRelative(X, 0, 0);
		}
		if (Y != null){
			start = start.getRelative(0, Y, 0);
		}
		if (Z != null){
			start = start.getRelative(0, 0, Z);
		}
		return start;
		
	}

	public static class Builder {

		Integer X, Y, Z;
		Location TELEPORT;
		RotateType ROTATE;

		public Builder setX(int x) {
			X = x;
			return this;
		}

		public Builder setY(int y) {
			Y = y;
			return this;
		}

		public Builder setZ(int z) {
			Z = z;
			return this;
		}

		public Builder setTeleportTo(Location loc) {
			TELEPORT = loc;
			return this;
		}

		public Builder setRotation(RotateType rotate) {
			ROTATE = rotate;
			return this;
		}

		public StoredMovement build() {
			return new StoredMovement(TELEPORT, ROTATE, X, Y, Z);
		}
	}

}

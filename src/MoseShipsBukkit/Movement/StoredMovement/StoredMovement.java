package MoseShipsBukkit.Movement.StoredMovement;

import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.block.Block;

import MoseShipsBukkit.Movement.Type.RotateType;

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

	public Optional<Integer> getX() {
		return Optional.ofNullable(X);
	}

	public Optional<Integer> getY() {
		return Optional.ofNullable(Y);
	}

	public Optional<Integer> getZ() {
		return Optional.ofNullable(Z);
	}

	public Optional<Location> getTeleportTo() {
		return Optional.ofNullable(TELEPORT);
	}

	public Optional<RotateType> getRotation() {
		return Optional.ofNullable(ROTATE);
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

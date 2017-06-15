package MoseShipsSponge.Movement.StoredMovement;

import java.util.Optional;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Movement.Type.RotateType;

public class StoredMovement {

	Integer X, Y, Z;
	Location<World> TELEPORT;
	RotateType ROTATE;
	Cause CAUSE;

	public StoredMovement(Location<World> teleport, RotateType rotate, int x, int y, int z, Cause cause) {
		X = x;
		Y = y;
		Z = z;
		TELEPORT = teleport;
		ROTATE = rotate;
		CAUSE = cause;
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

	public Optional<Location<World>> getTeleportTo() {
		return Optional.ofNullable(TELEPORT);
	}

	public Optional<RotateType> getRotation() {
		return Optional.ofNullable(ROTATE);
	}

	public Location<World> getEndResult(Location<World> start, Location<World> centre) {
		if (TELEPORT != null) {
			Location<World> ret = TELEPORT.add(X, Y, Z);
			return ret;
		}
		if (ROTATE != null) {
			if (ROTATE.equals(RotateType.LEFT)) {
				int shift = centre.getBlockX() - centre.getBlockZ();
				double symmetry = centre.getX();

				double X = start.getX() - (start.getX() - symmetry) * 2.0D - shift;
				double Y = start.getY();
				double Z = start.getZ() + shift;
				start = new Location<>(start.getExtent(), Z, Y, X);
			} else if (ROTATE.equals(RotateType.RIGHT)) {
				int shift = centre.getBlockX() - centre.getBlockZ();
				double symmetry = centre.getZ();

				double X = start.getX() - shift;
				double Y = start.getY();
				double Z = start.getZ() - (start.getZ() - symmetry) * 2.0 + shift;
				start = new Location<>(start.getExtent(), Z, Y, X);
			}
		}
		if (X != null) {
			start = start.add(X, 0, 0);
		}
		if (Y != null) {
			start = start.add(0, Y, 0);
		}
		if (Z != null) {
			start = start.add(0, 0, Z);
		}
		return start;
	}

	public Cause getCause() {
		return CAUSE;
	}

	public static class Builder {

		int X, Y, Z;
		Location<World> TELEPORT;
		RotateType ROTATE;
		Cause CAUSE;

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

		public Builder setTeleportTo(Location<World> loc) {
			TELEPORT = loc;
			return this;
		}

		public Builder setRotation(RotateType rotate) {
			ROTATE = rotate;
			return this;
		}

		public Cause getCause() {
			return CAUSE;
		}

		public Builder setCause(Cause cause) {
			CAUSE = cause;
			return this;
		}

		public StoredMovement build() {
			return new StoredMovement(TELEPORT, ROTATE, X, Y, Z, CAUSE);
		}
	}

}

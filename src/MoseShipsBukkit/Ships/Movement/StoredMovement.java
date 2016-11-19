package MoseShipsBukkit.Ships.Movement;

import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.block.Block;

import MoseShipsBukkit.Ships.Movement.MovementType.Rotate;

public class StoredMovement {

	int X, Y, Z;
	Location TELEPORT;
	Rotate ROTATE;

	public StoredMovement(Location teleport, Rotate rotate, int x, int y, int z) {
		X = x;
		Y = y;
		Z = z;
		TELEPORT = teleport;
		ROTATE = rotate;
	}

	public int getX() {
		return X;
	}

	public int getY() {
		return Y;
	}

	public int getZ() {
		return Z;
	}

	public Optional<Location> getTeleportTo() {
		return Optional.ofNullable(TELEPORT);
	}

	public Optional<Rotate> getRotation() {
		return Optional.ofNullable(ROTATE);
	}

	public Block getEndResult(Block start) {
		if (TELEPORT != null) {
			Block ret = TELEPORT.getBlock().getRelative(X, Y, Z);
			return ret;
		} else {
			Block ret = start.getRelative(X, Y, Z);
			return ret;
		}
	}

	public static class Builder {

		int X, Y, Z;
		Location TELEPORT;
		Rotate ROTATE;

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

		public Builder setRotation(Rotate rotate) {
			ROTATE = rotate;
			return this;
		}

		public StoredMovement build() {
			return new StoredMovement(TELEPORT, ROTATE, X, Y, Z);
		}
	}

}

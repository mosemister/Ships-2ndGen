package MoseShipsSponge.Vessel.Common.RootTypes;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.util.Direction;

import com.flowpowered.math.vector.Vector3i;

import MoseShipsSponge.Movement.Result.FailedMovement;
import MoseShipsSponge.Movement.StoredMovement.StoredMovement;
import MoseShipsSponge.Movement.Type.RotateType;
import MoseShipsSponge.Tasks.ShipsTaskRunner;
import MoseShipsSponge.Utils.Lists.MovingBlockList;
import MoseShipsSponge.Vessel.Common.ShipCommands.ShipCommands;
import MoseShipsSponge.Vessel.Common.Static.StaticShipType;

public interface LiveShip extends ShipsData {

	public int getMaxBlocks();
	public int getMinBlocks();

	public int getEngineSpeed();
	public int getBoostSpeed();
	public int getAltitudeSpeed();

	public ShipsTaskRunner getTaskRunner();

	public StaticShipType getStatic();

	public Map<String, Object> getInfo();

	public Map<UUID, Vector3i> getPlayerVectorSpawns();

	public boolean isMoving();

	public boolean isLoaded();

	public LiveShip setRemoveNextCycle(boolean remove);

	public LiveShip setMaxBlocks(int max);
	public LiveShip setMinBlocks(int min);
	public LiveShip setEngineSpeed(int speed);
	public LiveShip setBoostSpeed(int speed);
	public LiveShip setAltitudeSpeed(int speed);

	public boolean willRemoveNextCycle();

	public Optional<FailedMovement> hasRequirements(MovingBlockList blocks);

	public LiveShip load(Cause cause);
	public LiveShip unload(Cause cause);

	public List<ShipCommands> getCommands();

	public void remove(Player player);
	public boolean save();

	public Optional<FailedMovement> teleport(StoredMovement move);
	public Optional<FailedMovement> rotateLeft(Cause cause);
	public Optional<FailedMovement> rotateRight(Cause cause);
	
	public default Optional<FailedMovement> move(Direction dir, int speed, Cause cause) {
		Vector3i off = dir.asBlockOffset().mul(speed);
		StoredMovement sm = new StoredMovement.Builder().setX(off.getX()).setY(off.getY()).setZ(off.getZ())
				.setCause(cause).build();
		return teleport(sm);
	}

	public default Optional<FailedMovement> move(int X, int Y, int Z, Cause cause) {
		StoredMovement sm = new StoredMovement.Builder().setX(X).setY(Y).setZ(Z).setCause(cause).build();
		return teleport(sm);
	}

	public default Optional<FailedMovement> rotate(RotateType type, Cause cause) {
		StoredMovement sm = new StoredMovement.Builder().setRotation(type).setCause(cause).build();
		return teleport(sm);
	}

	public default void move(Vector3i vector, Cause cause) {
		move(vector.getX(), vector.getY(), vector.getZ(), cause);
	}

	public default void remove() {
		remove(null);
	}

}

package MoseShipsBukkit.Vessel.Data;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Movement.MovingBlock;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Movement.StoredMovement.StoredMovement;
import MoseShipsBukkit.Movement.Type.MovementType.Rotate;
import MoseShipsBukkit.ShipBlock.ShipVector;
import MoseShipsBukkit.Tasks.ShipsTaskRunner;
import MoseShipsBukkit.Vessel.Static.StaticShipType;

public interface LiveShip extends ShipsData {

	// public static final List<LiveShip> LOADED_SHIPS = new
	// ArrayList<LiveShip>();

	public int getMaxBlocks();
	public int getMinBlocks();
	public int getEngineSpeed();
	public int getBoostSpeed();
	public int getAltitudeSpeed();
	public ShipsTaskRunner getTaskRunner();
	public StaticShipType getStatic();
	public Map<String, Object> getInfo();
	public Map<UUID, ShipVector> getPlayerVectorSpawns();

	public boolean isMoving();
	public boolean isLoaded();

	public LiveShip setRemoveNextCycle(boolean remove);
	public LiveShip setMaxBlocks(int A);
	public LiveShip setMinBlocks(int A);
	public LiveShip setEngineSpeed(int A);
	public LiveShip setAltitudeSpeed(int A);
	public LiveShip setBoostSpeed(int A);

	public boolean willRemoveNextCycle();
	public abstract Optional<FailedMovement> hasRequirements(List<MovingBlock> blocks);
	public LiveShip load(ShipsCause cause);
	public LiveShip unload(ShipsCause cause);
	public void remove();
	public void remove(Player player);
	public boolean save();

	public Optional<FailedMovement> move(BlockFace dir, int speed, ShipsCause cause);
	public Optional<FailedMovement> move(int X, int Y, int Z, ShipsCause cause);
	public Optional<FailedMovement> rotateLeft(ShipsCause cause);
	public Optional<FailedMovement> rotateRight(ShipsCause cause);
	public Optional<FailedMovement> rotate(Rotate type, ShipsCause cause);
	public Optional<FailedMovement> teleport(StoredMovement move, ShipsCause cause);
	public Optional<FailedMovement> teleport(Location loc, ShipsCause cause);
	public Optional<FailedMovement> teleport(Location loc, int X, int Y, int Z, ShipsCause cause);

}

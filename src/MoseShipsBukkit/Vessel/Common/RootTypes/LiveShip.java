package MoseShipsBukkit.Vessel.Common.RootTypes;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Movement.StoredMovement.StoredMovement;
import MoseShipsBukkit.Movement.Type.RotateType;
import MoseShipsBukkit.ShipBlock.ShipVector;
import MoseShipsBukkit.Tasks.ShipsTaskRunner;
import MoseShipsBukkit.Utils.SOptional;
import MoseShipsBukkit.Utils.Lists.MovingBlockList;
import MoseShipsBukkit.Vessel.Common.ShipCommands.ShipCommands;
import MoseShipsBukkit.Vessel.Common.Static.StaticShipType;

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

	public abstract SOptional<FailedMovement> hasRequirements(MovingBlockList blocks);

	public LiveShip load(ShipsCause cause);

	public LiveShip unload(ShipsCause cause);
	
	public List<ShipCommands> getCommands();

	public void remove();

	public void remove(Player player);

	public boolean save();

	public SOptional<FailedMovement> move(BlockFace dir, int speed, ShipsCause cause);

	public SOptional<FailedMovement> move(int X, int Y, int Z, ShipsCause cause);

	public SOptional<FailedMovement> rotateLeft(ShipsCause cause);

	public SOptional<FailedMovement> rotateRight(ShipsCause cause);

	public SOptional<FailedMovement> rotate(RotateType type, ShipsCause cause);

	public SOptional<FailedMovement> teleport(StoredMovement move, ShipsCause cause);

	public SOptional<FailedMovement> teleport(Location loc, ShipsCause cause);

	public SOptional<FailedMovement> teleport(Location loc, int X, int Y, int Z, ShipsCause cause);

}

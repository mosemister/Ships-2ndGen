package MoseShipsBukkit.Ships.VesselTypes.DataTypes;

import java.util.List;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Causes.ShipsCause;
import MoseShipsBukkit.Causes.Failed.MovementResult;
import MoseShipsBukkit.Ships.Movement.MovementType.Rotate;
import MoseShipsBukkit.Ships.Movement.StoredMovement;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.Loading.ShipsLocalDatabase;
import MoseShipsBukkit.Ships.VesselTypes.Running.ShipsTaskRunner;
import MoseShipsBukkit.Ships.VesselTypes.Satic.StaticShipType;

public interface LiveShip extends ShipsData{
	
	public int getMaxBlocks();
	public int getMinBlocks();
	public ShipsLocalDatabase getLocalDatabase();
	public ShipsTaskRunner getTaskRunner();
	public StaticShipType getStatic();
	
	public boolean isMoving();
	public boolean isLoaded();
	
	public LiveShip setRemoveNextCycle(boolean remove);
	public LiveShip setMaxBlocks(int A);
	public LiveShip setMinBlocks(int A);
		
	public boolean willRemoveNextCycle();
	
	public abstract Optional<MovementResult> hasRequirements(List<MovingBlock> blocks);
	
	public LiveShip load(ShipsCause cause);
	public LiveShip unload(ShipsCause cause);
	
	public void remove();
	public void remove(Player player);
	
	public Optional<MovementResult> move(BlockFace dir, int speed, ShipsCause cause);
	public Optional<MovementResult> move(int X, int Y, int Z, ShipsCause cause);
	public Optional<MovementResult> rotateLeft(ShipsCause cause);
	public Optional<MovementResult> rotateRight(ShipsCause cause);
	public Optional<MovementResult> rotate(Rotate type, ShipsCause cause);
	public Optional<MovementResult> teleport(StoredMovement move, ShipsCause cause);
	public Optional<MovementResult> teleport(Location loc, ShipsCause cause);
	public Optional<MovementResult> teleport(Location loc, int X, int Y, int Z, ShipsCause cause);

	

}

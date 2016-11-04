package MoseShipsBukkit.Ships.VesselTypes.DataTypes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Causes.MovementResult;
import MoseShipsBukkit.Ships.ShipsData;
import MoseShipsBukkit.Ships.Movement.StoredMovement;
import MoseShipsBukkit.Ships.Movement.Movement.Rotate;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Ships.VesselTypes.Loading.ShipsLocalDatabase;
import MoseShipsBukkit.Utils.State.BlockState;

public interface LiveData {
	
	public Optional<Sign> getLicence();
	public String getName();
	public Optional<OfflinePlayer> getOwner();
	public LoadableShip setOwner(OfflinePlayer player);
	public List<OfflinePlayer> getSubPilots();
	public World getWorld();
	public Location getLocation();
	public Location getTeleportToLocation();
	public LiveData setTeleportToLocation(Location loc);
	public Map<String, String> getBasicData();
	public List<Block> getBasicStructure();
	public List<Entity> getEntities();
	public boolean hasLocation(Location loc);
	public List<Block> updateBasicStructure();
	public List<Block> setBasicStructure(List<Block> locs, Block licence);
	public List<Block> setBasicStructure(List<Block> locs, Block licence, Location teleport);
	public ShipsData cloneOnto(ShipsData data);
	
	public boolean willRemoveNextCycle();
	public LoadableShip setRemoveNextCycle(boolean remove);
	public boolean isLoaded();
	public LoadableShip load();
	public LoadableShip unload();
	public int getMaxBlocks();
	public LoadableShip setMaxBlocks(int A);
	public boolean isMoving();
	public void remove();
	public void remove(Player player);
	public ShipsLocalDatabase getLocalDatabase();
	public Optional<MovementResult> move(BlockFace dir, int speed, BlockState... movingTo);
	public Optional<MovementResult> move(int X, int Y, int Z, BlockState... movingTo);
	public Optional<MovementResult> rotateLeft(BlockState... movingTo);
	public Optional<MovementResult> rotateRight(BlockState... movingTo);
	public Optional<MovementResult> rotate(Rotate type, BlockState... movingTo);
	public Optional<MovementResult> teleport(StoredMovement move, BlockState... movingTo);
	public Optional<MovementResult> teleport(Location loc, BlockState... movingTo);
	public Optional<MovementResult> teleport(Location loc, int X, int Y, int Z, BlockState... movingTo);

	

}

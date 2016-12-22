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
import MoseShipsBukkit.Ships.Movement.MovementType.Rotate;
import MoseShipsBukkit.Ships.Movement.StoredMovement;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Ships.VesselTypes.Loading.ShipsLocalDatabase;

public interface LiveData {
	
	public Optional<Sign> getLicence();
	public String getName();
	public Optional<OfflinePlayer> getOwner();
	public List<OfflinePlayer> getSubPilots();
	public World getWorld();
	public Location getLocation();
	public Location getTeleportToLocation();
	public LiveData setTeleportToLocation(Location loc);
	public Map<String, String> getBasicData();
	public List<Block> getBasicStructure();
	public List<Entity> getEntities();
	public int getMaxBlocks();
	public int getMinBlocks();
	public ShipsLocalDatabase getLocalDatabase();
	
	public boolean hasLocation(Location loc);
	
	public boolean isMoving();
	public boolean isLoaded();
	
	public List<Block> updateBasicStructure();
	
	public LoadableShip setOwner(OfflinePlayer player);
	public List<Block> setBasicStructure(List<Block> locs, Block licence);
	public List<Block> setBasicStructure(List<Block> locs, Block licence, Location teleport);
	public LoadableShip setRemoveNextCycle(boolean remove);
	public LoadableShip setMaxBlocks(int A);
	public LoadableShip setMinBlocks(int A);
	
	public ShipsData cloneOnto(ShipsData data);
	
	public boolean willRemoveNextCycle();
	
	public LoadableShip load();
	public LoadableShip unload();
	
	public void remove();
	public void remove(Player player);
	
	public Optional<MovementResult> move(BlockFace dir, int speed);
	public Optional<MovementResult> move(int X, int Y, int Z);
	public Optional<MovementResult> rotateLeft();
	public Optional<MovementResult> rotateRight();
	public Optional<MovementResult> rotate(Rotate type);
	public Optional<MovementResult> teleport(StoredMovement move);
	public Optional<MovementResult> teleport(Location loc);
	public Optional<MovementResult> teleport(Location loc, int X, int Y, int Z);

	

}

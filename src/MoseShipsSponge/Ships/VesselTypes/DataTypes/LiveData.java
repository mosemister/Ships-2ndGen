package MoseShipsSponge.Ships.VesselTypes.DataTypes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Causes.MovementResult;
import MoseShipsSponge.Movement.StoredMovement.StoredMovement;
import MoseShipsSponge.Movement.Type.RotateType;
import MoseShipsSponge.Ships.ShipsData;
import MoseShipsSponge.Ships.VesselTypes.StaticShipType;
import MoseShipsSponge.Ships.VesselTypes.Loading.ShipsLocalDatabase;
import MoseShipsSponge.Ships.VesselTypes.Running.ShipsTaskRunner;

public interface LiveData {
	
	public Optional<Sign> getLicence();
	public String getName();
	public Optional<User> getOwner();
	public List<User> getSubPilots();
	public World getWorld();
	public Location<World> getLocation();
	public Location<World> getTeleportToLocation();
	public Map<String, String> getBasicData();
	public List<Entity> getEntities();
	public int getMaxBlocks();
	public int getMinBlocks();
	public ShipsLocalDatabase getLocalDatabase();
	public ShipsTaskRunner getTaskRunner();
	public StaticShipType getStatic();
	
	public boolean hasLocation(Location<World> loc);
	
	public boolean isMoving();
	public boolean isLoaded();
	
	public List<Location<World>> updateBasicStructure();
	
	public LiveData setTeleportToLocation(Location<World> loc);
	public LiveData setOwner(User user);
	public List<Location<World>> setBasicStructure(List<Location<World>> locs, Location<World> licence);
	public List<Location<World>> setBasicStructure(List<Location<World>> locs, Location<World> licnece, Location<World> teleport);
	public LiveData setRemoveNextCycle(boolean remove);
	public LiveData setMaxBlocks(int A);
	public LiveData setMinBlocks(int A);
	
	public ShipsData cloneOnto(ShipsData data);
	
	public LiveData load();
	public LiveData unload();
	
	public void remove();
	public void remove(Player player);
	
	public Optional<MovementResult> move(Direction dir, int speed, Cause cause);
	public Optional<MovementResult> move(int X, int Y, int Z, Cause cause);
	public Optional<MovementResult> rotateLeft(Cause cause);
	public Optional<MovementResult> rotateRight(Cause cause);
	public Optional<MovementResult> rotate(RotateType type, Cause cause);
	public Optional<MovementResult> teleport(StoredMovement move, Cause cause);
	public Optional<MovementResult> teleport(Location<World> loc, Cause cause);
	public Optional<MovementResult> teleport(Location<World> loc, int X, int Y, int Z, Cause cause);
	
	boolean willRemoveNextCycle();

}

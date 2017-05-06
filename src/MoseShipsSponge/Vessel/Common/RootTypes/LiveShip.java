package MoseShipsSponge.Vessel.Common.RootTypes;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.entity.living.player.Player;

import MoseShipsSponge.Ships.VesselTypes.StaticShipType;
import MoseShipsSponge.Ships.VesselTypes.Running.ShipsTaskRunner;

public interface LiveShip extends ShipsData {
	
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
	public boolean isLoading();
	public LiveShip setRemoveNextCycle(boolean remove);
	public LiveShip setMaxBlocks(int max);
	public LiveShip setMinBlocks(int min);
	public LiveShip setEngineSpeed(int speed);
	public LiveShip setBoostSpeed(int speed);
	public LiveShip setAltitudeSpeed(int speed);
	public boolean willRemoveNextCycle();
	public Optional<FailedMovement> hasRequirements(MovingBlockList blocks);
	public LiveShip load(ShipsCause cause);
	public LiveShip unload(ShipsCause cause);
	public void remove();
	public void remove(Player player);
	public boolean save();
	

}

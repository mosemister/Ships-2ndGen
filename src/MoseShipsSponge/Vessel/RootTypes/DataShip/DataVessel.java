package MoseShipsSponge.Vessel.RootTypes.DataShip;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

import MoseShipsSponge.Event.Load.ShipLoadEvent;
import MoseShipsSponge.Event.Load.ShipUnloadEvent;
import MoseShipsSponge.Movement.MovingBlock;
import MoseShipsSponge.Movement.Result.FailedMovement;
import MoseShipsSponge.Tasks.ShipsTask;
import MoseShipsSponge.Tasks.ShipsTaskRunner;
import MoseShipsSponge.Utils.Lists.MovingBlockList;
import MoseShipsSponge.Vessel.Common.OpenLoader.Loader;
import MoseShipsSponge.Vessel.Common.OpenLoader.OpenRAWLoader;
import MoseShipsSponge.Vessel.Common.RootTypes.AbstractShipsData;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;
import MoseShipsSponge.Vessel.Common.RootTypes.ShipsData;
import MoseShipsSponge.Vessel.Common.ShipCommands.ShipCommands;
import MoseShipsSponge.Vessel.RootTypes.DataShip.Data.RequirementData;

public abstract class DataVessel extends AbstractShipsData implements LiveShip {

	public abstract void onRemove(@Nullable Player player);

	protected boolean g_moving = false;
	protected int g_speed_engine = 2;
	protected int g_speed_boost = 3;
	protected int g_speed_altitude = 2;
	protected int g_max_blocks = 4000;
	protected int g_min_blocks = 200;
	protected boolean g_remove = false;
	ShipsTaskRunner g_task_runner = new ShipsTaskRunner(this);
	Map<UUID, Vector3i> g_player_leave_spawns = new HashMap<>();
	public List<ShipCommands> g_commands = new ArrayList<>();

	public DataVessel(String name, Location<World> sign, Vector3i teleport) {
		super(name, sign, teleport);
	}

	public DataVessel(ShipsData data) {
		super(data);
	}

	public List<RequirementData> getRequirementData() {
		return getData(RequirementData.class);
	}

	public File getFile() {
		return new File(Loader.LOADER_ROOT, getStatic().getName() + "/" + g_name + ".conf");
	}

	public MovingBlockList createUnofficalMovingBlocks(Direction direction, int distance) {
		MovingBlockList list = new MovingBlockList();
		getBasicStructure().stream().forEach(s -> {
			MovingBlock moving = new MovingBlock(s, direction, distance);
			list.add(moving);
		});
		return list;
	}

	@Override
	public int getMaxBlocks() {
		return g_max_blocks;
	}

	@Override
	public int getMinBlocks() {
		return g_min_blocks;
	}

	@Override
	public int getEngineSpeed() {
		return g_speed_engine;
	}

	@Override
	public int getBoostSpeed() {
		return g_speed_boost;
	}

	@Override
	public int getAltitudeSpeed() {
		return g_speed_altitude;
	}

	@Override
	public ShipsTaskRunner getTaskRunner() {
		return g_task_runner;
	}

	@Override
	public Map<String, Object> getInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		DataVessel vessel = this;
		getRequirementData().stream().forEach(r -> map.putAll(r.getInfo(vessel)));
		return map;
	}

	@Override
	public Map<UUID, Vector3i> getPlayerVectorSpawns() {
		return g_player_leave_spawns;
	}

	@Override
	public boolean isMoving() {
		return g_moving;
	}

	@Override
	public boolean isLoaded() {
		return Loader.getLoadedShips().stream().anyMatch(s -> s.getName().equals(getName()));
	}

	@Override
	public LiveShip setRemoveNextCycle(boolean remove) {
		g_remove = remove;
		return this;
	}

	@Override
	public LiveShip setMaxBlocks(int max) {
		g_max_blocks = max;
		return this;
	}

	@Override
	public LiveShip setMinBlocks(int min) {
		g_min_blocks = min;
		return this;
	}

	@Override
	public LiveShip setEngineSpeed(int speed) {
		g_speed_engine = speed;
		return this;
	}

	@Override
	public LiveShip setBoostSpeed(int speed) {
		g_speed_boost = speed;
		return null;
	}

	@Override
	public LiveShip setAltitudeSpeed(int speed) {
		g_speed_altitude = speed;
		return this;
	}

	@Override
	public boolean willRemoveNextCycle() {
		return g_remove;
	}

	@Override
	public Optional<FailedMovement> hasRequirements(MovingBlockList blocks) {
		for (RequirementData data : getRequirementData()) {
			Optional<FailedMovement> last = data.hasRequirement(this, blocks);
			if (last.isPresent()) {
				return last;
			}
		}
		return Optional.empty();
	}

	@Override
	public List<Location<World>> updateBasicStructure(boolean overtime) {
		if(!overtime) {
			return updateBasicStructure();
		}
		
		return null;
	}
	
	@Override
	public LiveShip load(Cause cause) {
		if (isLoaded()) {
			return this;
		}
		ShipLoadEvent event = new ShipLoadEvent(this, cause);
		Sponge.getEventManager().post(event);
		if (!event.isCancelled()) {
			Loader.LOADED_SHIPS.add(this);
		}
		return this;
	}

	@Override
	public LiveShip unload(Cause cause) {
		ShipUnloadEvent event = new ShipUnloadEvent(this, cause);
		Sponge.getEventManager().post(event);
		Loader.LOADED_SHIPS.remove(this);
		g_task_runner.pauseScheduler();
		save();
		return this;
	}

	@Override
	public List<ShipCommands> getCommands() {
		return g_commands;
	}

	@Override
	public void remove(Player player) {
		Loader.LOADED_SHIPS.remove(this);
		onRemove(player);
		List<ShipsTask> tasks = new ArrayList<>(getTaskRunner().getTasks());
		tasks.stream().forEach(t -> getTaskRunner().unregister(t));
		getFile().delete();
	}

	@Override
	public boolean save() {
		OpenRAWLoader loader = getStatic().getLoaders()[0];
		loader.RAWSave(this, getFile());
		return false;
	}

}

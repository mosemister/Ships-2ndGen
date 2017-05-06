package MoseShipsBukkit.Vessel.RootType.LoadableShip;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Events.Load.ShipLoadEvent;
import MoseShipsBukkit.Events.Load.ShipUnloadEvent;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Movement.Type.MovementType.Rotate;
import MoseShipsBukkit.ShipBlock.ShipVector;
import MoseShipsBukkit.Tasks.ShipsTask;
import MoseShipsBukkit.Tasks.ShipsTaskRunner;
import MoseShipsBukkit.Vessel.Common.OpenLoader.Loader;
import MoseShipsBukkit.Vessel.Common.OpenLoader.OpenRAWLoader;
import MoseShipsBukkit.Vessel.Common.RootTypes.AbstractShipsData;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.Common.RootTypes.ShipsData;

public abstract class LoadableShip extends AbstractShipsData implements LiveShip {

	public abstract void onRemove(@Nullable Player player);

	protected boolean g_moving = false;
	protected int g_speed_engine = 2;
	protected int g_speed_boost = 3;
	protected int g_speed_altitude = 2;
	protected int g_max_blocks = 4000;
	protected int g_min_blocks = 200;
	protected boolean g_remove = false;
	ShipsTaskRunner g_task_runner = new ShipsTaskRunner(this);
	Map<UUID, ShipVector> g_player_leave_spawns = new HashMap<UUID, ShipVector>();

	public LoadableShip(String name, Block sign, Location teleport) {
		super(name, sign, teleport);
	}

	public LoadableShip(ShipsData data) {
		super(data);
	}

	public File getFile() {
		return new File("plugins/Ships/VesselData/" + getStatic().getName() + "/" + g_name + ".yml");
	}

	@Override
	public int getAltitudeSpeed() {
		return g_speed_altitude;
	}

	@Override
	public int getBoostSpeed() {
		return g_speed_boost;
	}

	@Override
	public int getEngineSpeed() {
		return g_speed_engine;
	}

	@Override
	public LiveShip setAltitudeSpeed(int A) {
		g_speed_altitude = A;
		return this;
	}

	@Override
	public LiveShip setBoostSpeed(int A) {
		g_speed_boost = A;
		return this;
	}

	@Override
	public LiveShip setEngineSpeed(int A) {
		g_speed_engine = A;
		return this;
	}

	@Override
	public Map<UUID, ShipVector> getPlayerVectorSpawns() {
		return g_player_leave_spawns;
	}

	@Override
	public ShipsTaskRunner getTaskRunner() {
		return g_task_runner;
	}

	@Override
	public Optional<FailedMovement> rotate(Rotate type, ShipsCause cause) {
		switch (type) {
		case LEFT:
			return rotateLeft(cause);
		case RIGHT:
			return rotateRight(cause);
		}
		return null;
	}

	@Override
	public boolean willRemoveNextCycle() {
		return g_remove;
	}

	@Override
	public LoadableShip setRemoveNextCycle(boolean remove) {
		g_remove = remove;
		return this;
	}

	@Override
	public boolean isLoaded() {
		for (LiveShip ship : Loader.getShips()) {
			if (ship.getName().equals(getName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public LoadableShip load(ShipsCause cause) {
		if (isLoaded()) {
			return this;
		}
		ShipLoadEvent event = new ShipLoadEvent(cause, this);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			Loader.LOADED_SHIPS.add(this);
		}
		return this;
	}

	@Override
	public LoadableShip unload(ShipsCause cause) {
		ShipUnloadEvent event = new ShipUnloadEvent(cause, this);
		Bukkit.getPluginManager().callEvent(event);
		Loader.LOADED_SHIPS.remove(this);
		g_task_runner.pauseScheduler();
		return this;
	}

	@Override
	public int getMaxBlocks() {
		return g_max_blocks;
	}

	@Override
	public LoadableShip setMaxBlocks(int A) {
		g_max_blocks = A;
		return this;
	}

	@Override
	public int getMinBlocks() {
		return g_min_blocks;
	}

	@Override
	public LoadableShip setMinBlocks(int A) {
		g_min_blocks = A;
		return this;
	}

	@Override
	public boolean isMoving() {
		return g_moving;
	}

	public void setMoving(boolean check) {
		g_remove = false;
		g_moving = check;
	}

	@Override
	public void remove() {
		remove(null);
	}

	@Override
	public void remove(Player player) {
		Loader.LOADED_SHIPS.remove(this);
		onRemove(player);
		List<ShipsTask> tasks = new ArrayList<ShipsTask>(getTaskRunner().getTasks());
		for (ShipsTask task : tasks) {
			getTaskRunner().unregister(task);
		}
		getFile().delete();
	}

	@Override
	public List<Block> updateBasicStructure() {
		List<Block> structure = super.updateBasicStructure();
		save();
		return structure;
	}

	@Override
	public List<Block> setBasicStructure(List<Block> locs, Block licence) {
		List<Block> structure = super.setBasicStructure(locs, licence);
		save();
		return structure;
	}

	@Override
	public List<Block> setBasicStructure(List<Block> locs, Block licence, Location teleport) {
		List<Block> structure = super.setBasicStructure(locs, licence, teleport);
		save();
		return structure;
	}

	@Override
	public LoadableShip setTeleportToLocation(Location loc) {
		super.setTeleportToLocation(loc);
		save();
		return this;
	}

	@Override
	public LoadableShip setOwner(OfflinePlayer user) {
		super.setOwner(user);
		save();
		return this;
	}

	@Override
	public boolean save() {
		OpenRAWLoader loader = getStatic().getLoaders()[0];
		loader.RAWSave(this, getFile());
		return false;
	}

}

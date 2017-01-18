package MoseShipsBukkit.Vessel.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Configs.ShipsLocalDatabase;
import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Events.Load.ShipLoadEvent;
import MoseShipsBukkit.Events.Load.ShipUnloadEvent;
import MoseShipsBukkit.Movement.MovingBlock;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Movement.Type.MovementType.Rotate;
import MoseShipsBukkit.ShipBlock.ShipVector;
import MoseShipsBukkit.ShipBlock.Signs.ShipLicenceSign;
import MoseShipsBukkit.ShipBlock.Signs.ShipSign;
import MoseShipsBukkit.Tasks.ShipsTask;
import MoseShipsBukkit.Tasks.ShipsTaskRunner;
import MoseShipsBukkit.Tasks.Types.StructureCheckingTask;
import MoseShipsBukkit.Utils.LocationUtil;
import MoseShipsBukkit.Vessel.Loader.ShipLoader;
import MoseShipsBukkit.Vessel.Static.StaticShipType;

public abstract class LoadableShip extends AbstractShipsData implements LiveShip {

	public abstract Optional<FailedMovement> hasRequirements(List<MovingBlock> blocks);

	public abstract Map<String, Object> getInfo();

	public abstract void onSave(ShipsLocalDatabase database);

	public abstract void onRemove(@Nullable Player player);

	public abstract StaticShipType getStatic();

	protected boolean g_moving = false;
	protected int g_max_blocks = 4000;
	protected int g_min_blocks = 200;
	protected boolean g_remove = false;
	ShipsTaskRunner g_task_runner = new ShipsTaskRunner(this);
	Map<UUID, ShipVector> g_player_leave_spawns = new HashMap<UUID, ShipVector>(); 

	static List<LoadableShip> SHIPS = new ArrayList<LoadableShip>();

	public LoadableShip(String name, Block sign, Location teleport) {
		super(name, sign, teleport);
	}

	public LoadableShip(AbstractShipsData data) {
		super(data);
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
		for (LoadableShip ship : SHIPS) {
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
		if(!event.isCancelled()){
			SHIPS.add(this);
		}
		return this;
	}

	@Override
	public LoadableShip unload(ShipsCause cause) {
		ShipUnloadEvent event = new ShipUnloadEvent(cause, this);
		Bukkit.getPluginManager().callEvent(event);
		SHIPS.remove(this);
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
		SHIPS.remove(this);
		onRemove(player);
		List<ShipsTask> tasks = new ArrayList<ShipsTask>(getTaskRunner().getTasks());
		for (ShipsTask task : tasks){
			getTaskRunner().unregister(task);
		}
		getLocalDatabase().getFile().delete();
	}

	@Override
	public ShipsLocalDatabase getLocalDatabase() {
		return new ShipsLocalDatabase(this);
	}

	@Override
	public List<Block> updateBasicStructure() {
		StructureCheckingTask checking = g_task_runner.getTasks(StructureCheckingTask.class).iterator().next();
		if (checking.canUpdateStructure()) {
			List<Block> structure = super.updateBasicStructure();
			getLocalDatabase().saveBasicShip(this);
			checking.setUpdateStructure(false);
			return structure;
		}
		return new ArrayList<Block>();
	}

	@Override
	public List<Block> setBasicStructure(List<Block> locs, Block licence) {
		StructureCheckingTask checking = g_task_runner.getTasks(StructureCheckingTask.class).iterator().next();
		if (checking.canUpdateStructure()) {
		List<Block> structure = super.setBasicStructure(locs, licence);
		getLocalDatabase().saveBasicShip(this);
		checking.setUpdateStructure(false);
		return structure;
		}
		return new ArrayList<Block>();
	}

	@Override
	public List<Block> setBasicStructure(List<Block> locs, Block licence, Location teleport) {
		List<Block> structure = super.setBasicStructure(locs, licence, teleport);
		getLocalDatabase().saveBasicShip(this);
		StructureCheckingTask checking = g_task_runner.getTasks(StructureCheckingTask.class).iterator().next();
		checking.setUpdateStructure(false);
		return structure;
	}

	@Override
	public LoadableShip setTeleportToLocation(Location loc) {
		super.setTeleportToLocation(loc);
		getLocalDatabase().saveBasicShip(this);
		return this;
	}

	@Override
	public LoadableShip setOwner(OfflinePlayer user) {
		super.setOwner(user);
		getLocalDatabase().saveBasicShip(this);
		return this;
	}

	public static Optional<LoadableShip> getShip(String name) {
		for (LoadableShip ship : getShips()) {
			if (ship.getName().equalsIgnoreCase(name)) {
				return Optional.of(ship);
			}
		}
		return ShipLoader.loadShip(name);
	}

	public static Optional<LoadableShip> getShip(ShipSign type, Sign sign, boolean refresh) {
		if (type instanceof ShipLicenceSign) {
			String text = sign.getLine(2);
			return getShip(ChatColor.stripColor(text));
		} else {
			return getShip(sign.getBlock(), refresh);
		}
	}

	public static Optional<LoadableShip> getShip(Block loc, boolean updateStructure) {
		for (LoadableShip ship : SHIPS) {
			if (updateStructure) {
				ship.updateBasicStructure();
			}
			if (LocationUtil.blockContains(ship.getBasicStructure(), loc)) {
				return Optional.of(ship);
			}
		}
		for (LoadableShip ship : getShips()) {
			if (updateStructure) {
				ship.updateBasicStructure();
			}
			if (LocationUtil.blockContains(ship.getBasicStructure(), loc)) {
				return Optional.of(ship);
			}
		}
		return Optional.empty();
	}

	public static List<LoadableShip> getReasentlyUsedShips() {
		return SHIPS;
	}

	public static List<LoadableShip> getShips() {
		List<LoadableShip> ships = new ArrayList<LoadableShip>();
		ships.addAll(SHIPS);
		for (StaticShipType type : StaticShipType.TYPES) {
			File[] files = new File("plugins/Ships/VesselData/" + type.getName()).listFiles();
			if (files != null) {
				for (File file : files) {
					String name = file.getName().replace(".yml", "");
					boolean check = false;
					for (LoadableShip ship : ships) {
						if (ship.equals(name)) {
							check = true;
						}
					}
					if (!check) {
						Optional<LoadableShip> opShip = ShipLoader.loadShip(file);
						if (opShip.isPresent()) {
							ships.add(opShip.get());
						}
					}
				}
			}
		}
		return ships;
	}

	public static <T extends StaticShipType> List<LoadableShip> getShips(StaticShipType type) {
		List<LoadableShip> ships = new ArrayList<LoadableShip>();
		for (LoadableShip ship : ships) {
			if (type.equals(ship.getStatic())) {
				ships.add(ship);
			}
		}
		File[] files = new File("plugins/Ships/VesselData/" + type.getName()).listFiles();
		if (files != null) {
			for (File file : files) {
				String name = file.getName().replace(".yml", "");
				for (LoadableShip ship : ships) {
					if (!ship.getName().equals(name)) {
						Optional<LoadableShip> opShip = ShipLoader.loadShip(file);
						if (opShip.isPresent()) {
							ships.add(opShip.get());
						}
					}
				}
			}
		}
		return ships;
	}

	public static List<LoadableShip> getShipsByRequirements(Class<? extends LiveShip> type) {
		List<LoadableShip> ships = new ArrayList<LoadableShip>();
		for (LoadableShip ship : getShips()) {
			if (type.isInstance(ship)) {
				ships.add(ship);
			}
		}
		return ships;
	}

}

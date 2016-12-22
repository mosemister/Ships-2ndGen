package MoseShipsBukkit.Ships.VesselTypes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Causes.MovementResult;
import MoseShipsBukkit.Ships.ShipsData;
import MoseShipsBukkit.Ships.Movement.MovementType.Rotate;
import MoseShipsBukkit.Ships.Movement.StoredMovement;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveData;
import MoseShipsBukkit.Ships.VesselTypes.Loading.ShipLoader;
import MoseShipsBukkit.Ships.VesselTypes.Loading.ShipsLocalDatabase;
import MoseShipsBukkit.Ships.VesselTypes.Running.ShipsTaskRunner;
import MoseShipsBukkit.Ships.VesselTypes.Running.Tasks.StructureCheckingTask;
import MoseShipsBukkit.Ships.VesselTypes.Satic.StaticShipType;
import MoseShipsBukkit.Signs.ShipsSigns.SignType;
import MoseShipsBukkit.Utils.LocationUtils;

public abstract class LoadableShip extends ShipsData implements LiveData {

	public abstract Optional<MovementResult> hasRequirements(List<MovingBlock> blocks);

	public abstract Map<String, Object> getInfo();

	public abstract void onSave(ShipsLocalDatabase database);

	public abstract void onRemove(@Nullable Player player);

	public abstract StaticShipType getStatic();

	@Override
	public abstract Optional<MovementResult> move(BlockFace dir, int speed);

	@Override
	public abstract Optional<MovementResult> move(int X, int Y, int Z);

	@Override
	public abstract Optional<MovementResult> rotateLeft();

	@Override
	public abstract Optional<MovementResult> rotateRight();

	@Override
	public abstract Optional<MovementResult> teleport(StoredMovement move);

	@Override
	public abstract Optional<MovementResult> teleport(Location loc);

	@Override
	public abstract Optional<MovementResult> teleport(Location loc, int X, int Y, int Z);

	protected boolean g_moving = false;
	protected int g_max_blocks = 4000;
	protected int g_min_blocks = 200;
	protected boolean g_remove = false;
	ShipsTaskRunner g_task_runner = new ShipsTaskRunner(this);

	static List<LoadableShip> SHIPS = new ArrayList<LoadableShip>();

	public LoadableShip(String name, Block sign, Location teleport) {
		super(name, sign, teleport);
	}

	public LoadableShip(ShipsData data) {
		super(data);
	}

	@Override
	public Optional<MovementResult> rotate(Rotate type) {
		switch (type) {
			case LEFT:
				return rotateLeft();
			case RIGHT:
				return rotateRight();
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
	public LoadableShip load() {
		if (isLoaded()) {
			return this;
		}
		SHIPS.add(this);
		return this;
	}

	@Override
	public LoadableShip unload() {
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
		getLocalDatabase().getFile().delete();
	}

	@Override
	public ShipsLocalDatabase getLocalDatabase() {
		return new ShipsLocalDatabase(this);
	}

	@Override
	public ShipsData cloneOnto(ShipsData data) {
		super.cloneOnto(data);
		if (data instanceof LoadableShip) {
			LoadableShip ship = (LoadableShip) data;
			ship.g_moving = this.g_moving;
			ship.g_max_blocks = this.g_max_blocks;
			ship.g_min_blocks = this.g_min_blocks;
			ship.g_remove = this.g_remove;
			return ship;
		}
		return data;
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

	public static boolean addToRam(LoadableShip type) {
		for (LoadableShip ship : getShips()) {
			if (ship.getName().equalsIgnoreCase(type.getName())) {
				return false;
			}
		}
		return true;
	}

	public static Optional<LoadableShip> getShip(String name) {
		for (LoadableShip ship : getShips()) {
			if (ship.getName().equalsIgnoreCase(name)) {
				return Optional.of(ship);
			}
		}
		return ShipLoader.loadShip(name);
	}

	public static Optional<LoadableShip> getShip(SignType type, Sign sign, boolean refresh) {
		if (type.equals(SignType.LICENCE)) {
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
			if (LocationUtils.blockContains(ship.getBasicStructure(), loc)) {
				return Optional.of(ship);
			}
		}
		for (LoadableShip ship : getShips()) {
			if (updateStructure) {
				ship.updateBasicStructure();
			}
			if (LocationUtils.blockContains(ship.getBasicStructure(), loc)) {
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

	public static List<LoadableShip> getShipsByRequirements(Class<? extends LiveData> type) {
		List<LoadableShip> ships = new ArrayList<LoadableShip>();
		for (LoadableShip ship : getShips()) {
			if (type.isInstance(ship)) {
				ships.add(ship);
			}
		}
		return ships;
	}

}

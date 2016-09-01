package MoseShipsSponge.Ships.VesselTypes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

import com.flowpowered.math.vector.Vector3i;

import MoseShips.Bypasses.FinalBypass;

import MoseShipsSponge.BlockFinder.BasicBlockFinder;
import MoseShipsSponge.Causes.MovementResult;
import MoseShipsSponge.Ships.ShipsData;
import MoseShipsSponge.Ships.Movement.Movement;
import MoseShipsSponge.Ships.Movement.StoredMovement;
import MoseShipsSponge.Ships.Movement.Movement.Rotate;
import MoseShipsSponge.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsSponge.Ships.VesselTypes.DataTypes.LiveData;
import MoseShipsSponge.Ships.VesselTypes.Loading.ShipLoader;
import MoseShipsSponge.Ships.VesselTypes.Loading.ShipsLocalDatabase;
import MoseShipsSponge.Signs.ShipsSigns.SignType;
import MoseShipsSponge.Utils.LocationUtils;

public abstract class LoadableShip extends ShipsData {

	public abstract Optional<MovementResult> hasRequirements(List<MovingBlock> blocks, Cause cause);

	public abstract boolean shouldFall();

	public abstract int getMaxBlocks();

	public abstract int getMinBlocks();

	public abstract Map<Text, Object> getInfo();

	public abstract StaticShipType getStatic();

	static List<LoadableShip> SHIPS = new ArrayList<>();

	public LoadableShip(String name, Location<World> sign, Location<World> teleport) {
		super(name, sign, teleport);
	}

	public LoadableShip(ShipsData data) {
		super(data);
	}

	public ShipsLocalDatabase getLocalDatabase() {
		return new ShipsLocalDatabase(this);
	}

	public Optional<MovementResult> move(Vector3i moveBy, Cause cause) {
		return Movement.move(this, moveBy, cause);
	}

	public Optional<MovementResult> move(Direction dir, int speed, Cause cause) {
		System.out.println("speed: " + speed);
		Vector3i vector3i = LocationUtils.getReletive(dir, speed);
		return move(vector3i, cause);
	}

	public Optional<MovementResult> move(int X, int Y, int Z, Cause cause) {
		return Movement.move(this, X, Y, Z, cause);
	}

	public Optional<MovementResult> rotateLeft(Cause cause) {
		return Movement.rotateLeft(this, cause);
	}

	public Optional<MovementResult> rotateRight(Cause cause) {
		return Movement.rotateRight(this, cause);
	}

	public Optional<MovementResult> rotate(Rotate type, Cause cause) {
		return Movement.rotate(this, cause, type);
	}

	public Optional<MovementResult> teleport(StoredMovement move) {
		return Movement.teleport(this, move);
	}

	@SuppressWarnings("unchecked")
	public Optional<MovementResult> teleport(Location<? extends Extent> loc, Cause cause) {
		Location<World> loc2 = null;
		if (loc.getExtent() instanceof Chunk) {
			Chunk chunk = (Chunk) loc.getExtent();
			loc2 = chunk.getWorld().getLocation(loc.getBlockPosition());
		} else {
			loc2 = (Location<World>) loc;
		}
		return Movement.teleport(this, loc2, cause);
	}

	@SuppressWarnings("unchecked")
	public Optional<MovementResult> teleport(Location<? extends Extent> loc, int X, int Y, int Z, Cause cause) {
		Location<World> loc2 = null;
		if (loc.getExtent() instanceof Chunk) {
			Chunk chunk = (Chunk) loc.getExtent();
			loc2 = chunk.getWorld().getLocation(loc.getBlockPosition());
		} else {
			loc2 = (Location<World>) loc;
		}
		return Movement.teleport(this, loc2, X, Y, Z, cause);
	}

	@Override
	public List<Location<World>> setBasicStructure(List<Location<World>> locs, Location<World> licence) {
		List<Location<World>> structure = super.setBasicStructure(locs, licence);
		getLocalDatabase().saveBasicShip(this);
		return structure;
	}

	@Override
	public LoadableShip setTeleportToLocation(Location<World> loc) {
		super.setTeleportToLocation(loc);
		getLocalDatabase().saveBasicShip(this);
		return this;
	}

	@Override
	public LoadableShip setOwner(User user) {
		super.setOwner(user);
		getLocalDatabase().saveBasicShip(this);
		return this;
	}

	public static void inject(LoadableShip type) {
		SHIPS.add(type);
	}

	public static Optional<LoadableShip> getShip(String name) {
		return SHIPS.stream().filter(s -> s.getName().equals(name)).findFirst();
	}

	public static Optional<LoadableShip> getShip(Text text) {
		return getShip(text.toPlain());
	}

	public static Optional<LoadableShip> getShip(SignType type, Sign sign, boolean refresh) {
		if (type.equals(SignType.LICENCE)) {
			Text text = sign.lines().get(2);
			return getShip(text.toPlain());
		} else {
			if (refresh) {
				// List<Location<World>> structure =
				// BasicBlockFinder.SHIPS5.getConnectedBlocks(ShipsConfig.CONFIG.get(Integer.class,
				// ShipsConfig.STRUCTURE_STRUCTURELIMITS_TRACKLIMIT),
				// sign.getLocation());
				System.out.println("finding ship");
				List<Location<World>> structure = BasicBlockFinder.SHIPS5.getConnectedBlocks(5000, sign.getLocation());
				System.out.println("structure size: " + structure.size());
				FinalBypass<Optional<LoadableShip>> shipType = new FinalBypass<>(null);
				structure.stream().forEach(l -> {
					Optional<TileEntity> opTE = l.getTileEntity();
					if (opTE.isPresent()) {
						TileEntity TE = opTE.get();
						if (TE instanceof Sign) {
							Sign sign2 = (Sign) TE;
							Text text = sign2.lines().get(2);
							Optional<LoadableShip> type2 = getShip(text.toPlain());
							if (type2.isPresent()) {
								type2.get().setBasicStructure(structure, l);
							}
							shipType.set(type2);
						}
					}
				});
				return shipType.get();
			} else {
				FinalBypass<LoadableShip> shipType = new FinalBypass<>(null);
				SHIPS.stream().forEach(s -> {
					s.getBasicStructure().stream().forEach(l -> {
						if (l.equals(sign.getLocation())) {
							shipType.set(s);
						}
					});
				});
				return Optional.ofNullable(shipType.get());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static Optional<LoadableShip> getShip(Location<? extends Extent> loc, boolean updateStructure) {
		if (loc.getExtent() instanceof Chunk) {
			Chunk chunk = (Chunk) loc.getExtent();
			loc = chunk.getWorld().getLocation(loc.getBlockPosition());
		}
		final Location<World> loc2 = (Location<World>) loc;
		return SHIPS.stream().filter(s -> {
			// CHECK THOUGH ALL SHIPS
			if (updateStructure) {
				// UPDATE THE STRUCTURE IF SPECIFIED
				s.updateBasicStructure();
			}
			return s.hasLocation(loc2);
		}).findFirst();
	}

	public static List<LoadableShip> getReasentlyUsedShips() {
		return SHIPS;
	}

	public static List<LoadableShip> getShips() {
		List<LoadableShip> ships = new ArrayList<>();
		ships.addAll(SHIPS);
		StaticShipType.TYPES.stream().forEach(t -> {
			File[] files = new File("config/Ships/VesselData/" + t.getName()).listFiles();
			if (files != null) {
				for (File file : files) {
					String name = file.getName().replace(".conf", "");
					if (!ships.stream().anyMatch(s -> s.getName().equals(name))) {
						Optional<LoadableShip> opShip = ShipLoader.loadShip(file);
						if (opShip.isPresent()) {
							ships.add(opShip.get());
						}
					}
				}
			}
		});
		return ships;
	}

	public static <T extends StaticShipType> List<LoadableShip> getShips(StaticShipType type) {
		List<LoadableShip> ships = new ArrayList<>();
		SHIPS.stream().forEach(s -> {
			if (type.equals(s.getStatic())) {
				ships.add(s);
			}
		});
		File[] files = new File("config/Ships/VesselData/" + type.getName()).listFiles();
		if (files != null) {
			for (File file : files) {
				String name = file.getName().replace(".conf", "");
				if (!ships.stream().anyMatch(s -> s.getName().equals(name))) {
					Optional<LoadableShip> opShip = ShipLoader.loadShip(file);
					if (opShip.isPresent()) {
						ships.add(opShip.get());
					}
				}
			}
		}
		return ships;
	}

	public static List<LoadableShip> getShipsByRequirements(Class<? extends LiveData> type) {
		return getShips().stream().filter(s -> type.isInstance(s)).collect(Collectors.toList());
	}

}

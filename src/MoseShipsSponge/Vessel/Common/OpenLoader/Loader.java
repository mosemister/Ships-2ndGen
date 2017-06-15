package MoseShipsSponge.Vessel.Common.OpenLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

import MoseShipsSponge.Configs.BasicConfig;
import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.ShipBlock.Signs.ShipLicenceSign;
import MoseShipsSponge.ShipBlock.Signs.ShipSign;
import MoseShipsSponge.Utils.StaticShipTypeUtil;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;
import MoseShipsSponge.Vessel.Common.Static.StaticShipType;

public class Loader {

	public static final File LOADER_ROOT = new File(ShipsMain.CONFIGURATION_ROOT, "VesselData");

	public static final Object[] OPEN_LOADER_NAME = {
			"OpenLoader",
			"Name" };
	public static final Object[] OPEN_LOADER_VERSION = {
			"OpenLoader",
			"Version" };
	public static final Object[] OPEN_LOADER_ERROR = {
			"OpenLoader",
			"Error" };

	public static final List<LiveShip> LOADED_SHIPS = new ArrayList<>();

	public static void saveLoader(BasicConfig config, OpenRAWLoader loader) {
		String name = null;
		for (int A : loader.getLoaderVersion()) {
			if (name == null) {
				name = A + "";
			} else {
				name = name + "." + A;
			}
		}
		config.set(loader.getLoaderName(), OPEN_LOADER_NAME);
		config.set(name, OPEN_LOADER_VERSION);
	}

	public static Optional<OpenRAWLoader> getOpenLoader(File file) {
		for (StaticShipType type : StaticShipTypeUtil.getTypes()) {
			for (OpenRAWLoader loader : type.getLoaders()) {
				if (loader.willLoad(file)) {
					return Optional.of(loader);
				}
			}
		}
		return Optional.empty();
	}

	public static Optional<LiveShip> loadFromFile(File file) {
		for (StaticShipType type : StaticShipTypeUtil.getTypes()) {
			for (OpenRAWLoader loader : type.getLoaders()) {
				if (loader.willLoad(file)) {
					return loader.RAWLoad(file);
				}
			}
		}
		return Optional.empty();
	}

	public static File[] getShipFiles(StaticShipType type) {
		return new File(LOADER_ROOT, type.getName()).listFiles();
	}

	public static Optional<File> getShipFile(String name) {
		for (File file : getAllShipsFiles()) {
			String name2 = file.getName();
			name2 = name2.substring(0, name2.length() - 5);
			if (name.equals(name2)) {
				return Optional.of(file);
			}
		}
		return Optional.empty();
	}

	public static File[] getAllShipsFiles() {
		List<File> files = new ArrayList<>();
		StaticShipTypeUtil.getTypes().stream().forEach(t -> {
			File[] sFiles = getShipFiles(t);
			if (sFiles != null) {
				for (File file : sFiles) {
					files.add(file);
				}
			}
		});
		File[] ret = new File[files.size()];
		return files.toArray(ret);
	}

	public static List<LiveShip> safeLoadAllShips() {
		File[] files = getAllShipsFiles();
		List<LiveShip> ships = new ArrayList<>();
		for (File file : files) {
			boolean check = true;
			for (LiveShip ship : LOADED_SHIPS) {
				String name = file.getName();
				name = name.substring(0, name.length() - 5);
				if (ship.getName().equals(name)) {
					ships.add(ship);
					check = false;
					break;
				}
			}
			if (check) {
				Optional<LiveShip> opShip = loadFromFile(file);
				if (opShip.isPresent()) {
					ships.add(opShip.get());
				}
			}
		}
		return ships;
	}

	public static List<LiveShip> getLoadedShips() {
		return LOADED_SHIPS;
	}

	public static Optional<LiveShip> safeLoadShip(String name) {
		Optional<LiveShip> opShip = getLoadedShips().stream().filter(s -> s.getName().equalsIgnoreCase(name)).findAny();
		if (opShip.isPresent()) {
			return opShip;
		}
		Optional<File> opFile = getShipFile(name);
		if (opFile.isPresent()) {
			return loadFromFile(opFile.get());
		}
		return Optional.empty();
	}

	public static Optional<LiveShip> safeLoadShip(Text text) {
		return safeLoadShip(text.toPlain());
	}

	public static Optional<LiveShip> safeLoadShip(ShipSign type, Sign sign, boolean refresh) {
		if (type instanceof ShipLicenceSign) {
			Optional<Text> opText = sign.getSignData().get(2);
			if (opText.isPresent()) {
				return safeLoadShip(opText.get());
			}
			return Optional.empty();
		} else {
			return safeLoadShip(sign.getLocation(), refresh);
		}
	}

	public static Optional<LiveShip> safeLoadShip(Location<? extends Extent> loc, boolean refresh) {
		World world;
		int X = loc.getBlockX();
		int Y = loc.getBlockY();
		int Z = loc.getBlockZ();
		if (loc.getExtent() instanceof World) {
			world = (World) loc.getExtent();
		} else {
			Chunk chunk = (Chunk) loc.getExtent();
			world = chunk.getWorld();
		}
		Optional<LiveShip> opShip = getLoadedShips().stream().filter(s -> {
			if (refresh) {
				s.updateBasicStructure();
			}
			for (Location<World> loc2 : s.getBasicStructure()) {
				if ((X == loc2.getBlockX()) && (Y == loc2.getBlockY()) && (Z == loc2.getBlockZ())
						&& (world.equals(loc2.getExtent()))) {
					return true;
				}
			}
			return false;
		}).findAny();
		if (opShip.isPresent()) {
			return opShip;
		}
		return safeLoadAllShips().stream().filter(s -> {
			if (refresh) {
				s.updateBasicStructure();
			}
			for (Location<World> loc2 : s.getBasicStructure()) {
				if ((X == loc2.getBlockX()) && (Y == loc2.getBlockY()) && (Z == loc2.getBlockZ())
						&& (world.equals(loc2.getExtent()))) {
					return true;
				}
			}
			return false;
		}).findAny();

	}

}

package MoseShipsBukkit.Vessel.OpenLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.ShipBlock.Signs.ShipLicenceSign;
import MoseShipsBukkit.ShipBlock.Signs.ShipSign;
import MoseShipsBukkit.Utils.LocationUtil;
import MoseShipsBukkit.Utils.StaticShipTypeUtil;
import MoseShipsBukkit.Vessel.Data.LiveShip;
import MoseShipsBukkit.Vessel.Static.StaticShipType;

public class Loader {

	public static final String OPEN_LOADER_NAME = "OpenLoader.Name";
	public static final String OPEN_LOADER_VERSION = "OpenLoader.Version";
	public static final String OPEN_LOADER_ERROR = "OpenLoader.Error";
	
	public static final List<LiveShip> LOADED_SHIPS = new ArrayList<LiveShip>();
	
	public static void saveLoader(BasicConfig config, OpenLoader loader){
		config.set(loader.getLoaderName(), OPEN_LOADER_NAME);
	}
	
	public static Optional<LiveShip> loadDirectlyFromFile(File file){
		for(StaticShipType type : StaticShipTypeUtil.getTypes()){
			for(OpenRAWLoader loader : type.getLoaders()){
				if(loader.willLoad(file)){
					return loader.RAWLoad(file);
				}
			}
		}
		return Optional.empty();
	}
	
	public static List<OpenRAWLoader> getLoadersFromFile(File file){
		List<OpenRAWLoader> list = new ArrayList<OpenRAWLoader>();
		for(StaticShipType type : StaticShipTypeUtil.getTypes()){
			for(OpenRAWLoader loader : type.getLoaders()){
				list.add(loader);
			}
		}
		return list;
	}
	
	public static Optional<File> getFileFromName(String name){
		File file = new File("plugins/Ships/VesselData");
		for(StaticShipType type : StaticShipTypeUtil.getTypes()){
			File shipFile = new File(file, type.getName());
			File[] shipFiles = shipFile.listFiles();
			if(shipFiles != null){
				for(File shipFile2 : shipFiles){
					if(shipFile2.exists()){
						return Optional.of(shipFile2);
					}
				}
			}
		}
		return Optional.empty();
	}
	
	public static Optional<LiveShip> loadDirectlyFromName(String name){
		Optional<File> opFile = getFileFromName(name);
		if(opFile.isPresent()){
			return loadDirectlyFromFile(opFile.get());
		}
		return Optional.empty();
	}
	
	public static Optional<LiveShip> getShip(UUID uuid) {
		for (LiveShip ship : getShips()) {
			if ((ship.getOwner().isPresent()) && (ship.getOwner().get().getUniqueId().equals(uuid))) {
				return Optional.of(ship);
			}
			for (OfflinePlayer player : ship.getSubPilots()) {
				if (player.getUniqueId().equals(uuid)) {
					return Optional.of(ship);
				}
			}
			if (ship.getPlayerVectorSpawns().containsKey(uuid)) {
				return Optional.of(ship);
			}
		}
		return Optional.empty();
	}

	public static Optional<LiveShip> getShip(String name) {
		for (LiveShip ship : getShips()) {
			if (ship.getName().equalsIgnoreCase(name)) {
				return Optional.of(ship);
			}
		}
		return loadDirectlyFromName(name);
	}

	public static Optional<LiveShip> getShip(ShipSign type, Sign sign, boolean refresh) {
		if (type instanceof ShipLicenceSign) {
			String text = sign.getLine(2);
			return getShip(ChatColor.stripColor(text));
		} else {
			return getShip(sign.getBlock(), refresh);
		}
	}

	public static Optional<LiveShip> getShip(Block loc, boolean updateStructure) {
		for (LiveShip ship : LOADED_SHIPS) {
			if (updateStructure) {
				ship.updateBasicStructure();
			}
			if (LocationUtil.blockContains(ship.getBasicStructure(), loc)) {
				return Optional.of(ship);
			}
		}
		for (LiveShip ship : getShips()) {
			if (updateStructure) {
				ship.updateBasicStructure();
			}
			if (LocationUtil.blockContains(ship.getBasicStructure(), loc)) {
				return Optional.of(ship);
			}
		}
		return Optional.empty();
	}

	public static List<LiveShip> getReasentlyUsedShips() {
		return LOADED_SHIPS;
	}

	public static List<LiveShip> getShips() {
		List<LiveShip> ships = new ArrayList<LiveShip>();
		ships.addAll(LOADED_SHIPS);
		for (StaticShipType type : StaticShipType.TYPES) {
			File[] files = new File("plugins/Ships/VesselData/" + type.getName()).listFiles();
			if (files != null) {
				for (File file : files) {
					String name = file.getName().replace(".yml", "");
					boolean check = false;
					for (LiveShip ship : ships) {
						if (ship.equals(name)) {
							check = true;
						}
					}
					if (!check) {
						Optional<LiveShip> opShip = loadDirectlyFromFile(file);
						if (opShip.isPresent()) {
							ships.add(opShip.get());
						}
					}
				}
			}
		}
		return ships;
	}

	public static <T extends StaticShipType> List<LiveShip> getShips(StaticShipType type) {
		List<LiveShip> ships = new ArrayList<LiveShip>();
		for (LiveShip ship : LOADED_SHIPS) {
			if (type.equals(ship.getStatic())) {
				ships.add(ship);
			}
		}
		File[] files = new File("plugins/Ships/VesselData/" + type.getName()).listFiles();
		if (files != null) {
			for (File file : files) {
				String name = file.getName().replace(".yml", "");
				for (LiveShip ship : ships) {
					if (!ship.getName().equals(name)) {
						Optional<LiveShip> opShip = loadDirectlyFromFile(file);
						if (opShip.isPresent()) {
							ships.add(opShip.get());
						}
					}
				}
			}
		}
		return ships;
	}

	public static List<LiveShip> getShipsByRequirements(Class<? extends LiveShip> type) {
		List<LiveShip> ships = new ArrayList<LiveShip>();
		for (LiveShip ship : getShips()) {
			if (type.isInstance(ship)) {
				ships.add(ship);
			}
		}
		return ships;
	}
	
}

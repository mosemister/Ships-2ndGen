package MoseShipsBukkit.Vessel.Common.OpenLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.ShipBlock.ShipVector;
import MoseShipsBukkit.ShipBlock.Signs.ShipLicenceSign;
import MoseShipsBukkit.ShipBlock.Signs.ShipSign;
import MoseShipsBukkit.Utils.SOptional;
import MoseShipsBukkit.Utils.StaticShipTypeUtil;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.Common.Static.StaticShipType;

public class Loader {

	public static final String OPEN_LOADER_NAME = "OpenLoader.Name";
	public static final String OPEN_LOADER_VERSION = "OpenLoader.Version";
	public static final String OPEN_LOADER_ERROR = "OpenLoader.Error";

	public static final List<LiveShip> LOADED_SHIPS = new ArrayList<LiveShip>();

	public static void saveLoader(BasicConfig config, OpenLoader loader) {
		String name = null;
		for(int A : loader.getLoaderVersion()){
			if(name == null){
				name = A + "";
			}else{
				name = name + "." + A;
			}
		}
		config.set(loader.getLoaderName(), OPEN_LOADER_NAME);
		config.set(name, OPEN_LOADER_VERSION);
	}
	
	public static SOptional<OpenRAWLoader> getOpenLoader(File file){
		for (StaticShipType type : StaticShipTypeUtil.getTypes()) {
			for (OpenRAWLoader loader : type.getLoaders()) {
				if (loader.willLoad(file)) {
					return new SOptional<OpenRAWLoader>(loader);
				}
			}
		}
		return new SOptional<OpenRAWLoader>();
	}
	
	public static SOptional<LiveShip> loadFromFile(File file){
		for (StaticShipType type : StaticShipTypeUtil.getTypes()) {
			for (OpenRAWLoader loader : type.getLoaders()) {
				if (loader.willLoad(file)) {
					return loader.RAWLoad(file);
				}
			}
		}
		return new SOptional<LiveShip>();
	}
	
	public static File[] getShipFiles(StaticShipType type){
		return new File("plugins/Ships/VesselData/" + type.getName() + "/").listFiles();
	}
	
	public static SOptional<File> getShipFile(String name){
		for(File file2 : getAllShipsFiles()){
			String name2 = file2.getName();
			name2 = name2.substring(0, name2.length() - 4);
			if(name.equals(name2)){
				return new SOptional<File>(file2);
			}
		}
		return new SOptional<File>();
	}
	
	public static File[] getAllShipsFiles(){
		List<File> files = new ArrayList<File>();
		for(StaticShipType type : StaticShipTypeUtil.getTypes()){
			File[] sFile = getShipFiles(type);
			if(sFile == null){
				continue;
			}
			for(File file : sFile){
				files.add(file);
			}
		}
		File[] ret = new File[files.size()];
		return files.toArray(ret);
	}
	
	public static List<LiveShip> safeLoadAllShips(){
		File[] files = getAllShipsFiles();
		List<LiveShip> ships = new ArrayList<LiveShip>();
		for(File file : files){
			boolean check = true;
			for(LiveShip ship : LOADED_SHIPS){
				String name = file.getName();
				name = name.substring(0, name.length() - 4);
				if(ship.getName().equals(name)){
					ships.add(ship);
					check = false;
					break;
				}
			}
			if(check){
				SOptional<LiveShip> opShip = loadFromFile(file);
				if(opShip.isPresent()){
					ships.add(opShip.get());
				}
			}
		}
		return ships;
	}
		
	public static List<LiveShip> getLoadedShips(){
		return LOADED_SHIPS;
	}
	
	public static SOptional<LiveShip> safeLoadShip(String name){
		for(LiveShip ship : getLoadedShips()){
			if(ship.getName().equalsIgnoreCase(name)){
				return new SOptional<LiveShip>(ship);
			}
		}
		SOptional<File> opFile = getShipFile(name);
		if(opFile.isPresent()){
			SOptional<LiveShip> loaded = loadFromFile(opFile.get());
			return loaded;
		}
		return new SOptional<LiveShip>();
	}
	
	public static SOptional<LiveShip> safeLoadShip(ShipSign type, Sign sign, boolean refresh) {
		if (type instanceof ShipLicenceSign) {
			String text = sign.getLine(2);
			return safeLoadShip(ChatColor.stripColor(text));
		} else {
			return safeLoadShip(sign.getLocation(), refresh);
		}
	}
	
	public static SOptional<LiveShip> safeLoadShip(Location loc, boolean refresh){
		for(LiveShip ship : getLoadedShips()){
			if(refresh){
				ship.updateBasicStructure();
			}
			for (Block block : ship.getBasicStructure()){
				if((block.getX() == loc.getBlockX()) && (block.getY() == loc.getBlockY()) && (block.getZ() == loc.getBlockZ()) && (loc.getWorld().equals(block.getWorld()))){
					return new SOptional<LiveShip>(ship);
				}
			}
		}
		for(LiveShip ship : safeLoadAllShips()){
			if(refresh){
				ship.updateBasicStructure();
			}
			for (Block block : ship.getBasicStructure()){
				if((block.getX() == loc.getBlockX()) && (block.getY() == loc.getBlockY()) && (block.getZ() == loc.getBlockZ()) && (loc.getWorld().equals(block.getWorld()))){
					return new SOptional<LiveShip>(ship);
				}
			}
		}
		return new SOptional<LiveShip>();
	}

	public static List<LiveShip> safeLoadShipByOwner(UUID uuid){
		List<LiveShip> list = new ArrayList<LiveShip>();
		for(LiveShip ship : safeLoadAllShips()){
			SOptional<OfflinePlayer> opOwner = ship.getOwner();
			if(opOwner.isPresent()){
				if(opOwner.get().getUniqueId().equals(uuid)){
					list.add(ship);
				}
			}
		}
		return list;
	}
	
	public static List<LiveShip> safeLoadShipBySubPilot(UUID uuid){
		List<LiveShip> list = new ArrayList<LiveShip>();
		for(LiveShip ship : safeLoadAllShips()){
			List<OfflinePlayer> subPilots = ship.getSubPilots();
			for(OfflinePlayer player : subPilots){
				if(player.getUniqueId().equals(uuid)){
					list.add(ship);
					break;
				}
			}
		}
		return list;
	}
	
	public static SOptional<LiveShip> safeLoadShipByVectorSpawn(UUID uuid){
		for(LiveShip ship : safeLoadAllShips()){
			for (Entry<UUID, ShipVector> entry : ship.getPlayerVectorSpawns().entrySet()){
				if(entry.getKey().equals(uuid)){
					return new SOptional<LiveShip>(ship);
				}
			}
		}
		return new SOptional<LiveShip>();
	}
	
	/*public static SOptional<LiveShip> loadDirectlyFromFile(File file) {
		for (StaticShipType type : StaticShipTypeUtil.getTypes()) {
			for (OpenRAWLoader loader : type.getLoaders()) {
				if (loader.willLoad(file)) {
					return loader.RAWLoad(file);
				}
			}
		}
		return new SOptional<LiveShip>();
	}

	public static List<OpenRAWLoader> getLoadersFromFile(File file) {
		List<OpenRAWLoader> list = new ArrayList<OpenRAWLoader>();
		for (StaticShipType type : StaticShipTypeUtil.getTypes()) {
			for (OpenRAWLoader loader : type.getLoaders()) {
				list.add(loader);
			}
		}
		return list;
	}

	public static SOptional<File> getFileFromName(String name) {
		File file = new File("plugins/Ships/VesselData");
		for (StaticShipType type : StaticShipTypeUtil.getTypes()) {
			File shipFile = new File(file, type.getName());
			File[] shipFiles = shipFile.listFiles();
			if (shipFiles != null) {
				for (File shipFile2 : shipFiles) {
					if (shipFile2.exists()) {
						return new SOptional<File>(shipFile2);
					}
				}
			}
		}
		return new SOptional<File>();
	}

	public static SOptional<LiveShip> loadDirectlyFromName(String name) {
		SOptional<File> opFile = getFileFromName(name);
		if (opFile.isPresent()) {
			return loadDirectlyFromFile(opFile.get());
		}
		return new SOptional<LiveShip>();
	}

	public static SOptional<LiveShip> getShip(UUID uuid) {
		for (LiveShip ship : getShips()) {
			if ((ship.getOwner().isPresent()) && (ship.getOwner().get().getUniqueId().equals(uuid))) {
				return new SOptional<LiveShip>(ship);
			}
			for (OfflinePlayer player : ship.getSubPilots()) {
				if (player.getUniqueId().equals(uuid)) {
					return new SOptional<LiveShip>(ship);
				}
			}
			if (ship.getPlayerVectorSpawns().containsKey(uuid)) {
				return new SOptional<LiveShip>(ship);
			}
		}
		return new SOptional<LiveShip>();
	}

	public static SOptional<LiveShip> getShip(String name) {
		System.out.println("Searching: " + name);
		for (LiveShip ship : getShips()) {
			System.out.println("Found: " + ship.getName());
			if (ship.getName().equalsIgnoreCase(name)) {
				System.out.println("Match");
				return new SOptional<LiveShip>(ship);
			}
		}
		System.out.println("Attempting to load directly from name");
		return loadDirectlyFromName(name);
	}

	public static SOptional<LiveShip> getShip(ShipSign type, Sign sign, boolean refresh) {
		if (type instanceof ShipLicenceSign) {
			String text = sign.getLine(2);
			return getShip(ChatColor.stripColor(text));
		} else {
			return getShip(sign.getBlock(), refresh);
		}
	}

	public static SOptional<LiveShip> getShip(Block loc, boolean updateStructure) {
		for (LiveShip ship : LOADED_SHIPS) {
			if (updateStructure) {
				ship.updateBasicStructure();
			}
			if (LocationUtil.blockContains(ship.getBasicStructure(), loc)) {
				return new SOptional<LiveShip>(ship);
			}
		}
		for (LiveShip ship : getShips()) {
			if (updateStructure) {
				ship.updateBasicStructure();
			}
			if (LocationUtil.blockContains(ship.getBasicStructure(), loc)) {
				return new SOptional<LiveShip>(ship);
			}
		}
		return new SOptional<LiveShip>();
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
						SOptional<LiveShip> opShip = loadDirectlyFromFile(file);
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
						SOptional<LiveShip> opShip = loadDirectlyFromFile(file);
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
	}*/

}

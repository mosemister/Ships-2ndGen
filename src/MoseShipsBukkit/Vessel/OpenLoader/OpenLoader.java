package MoseShipsBukkit.Vessel.OpenLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;

import MoseShips.CustomDataHolder.DataHolder;
import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.ShipBlock.ShipVector;
import MoseShipsBukkit.Utils.SerializedData;
import MoseShipsBukkit.Vessel.Data.AbstractShipsData;
import MoseShipsBukkit.Vessel.Data.LiveShip;
import MoseShipsBukkit.Vessel.Data.LoadableShip;
import MoseShipsBukkit.Vessel.Data.ShipsData;

public abstract class OpenLoader implements OpenRAWLoader {

	public static final String ERROR_NO_WORLD_READ = "No World Read";
	public static final String ERROR_NO_WORLD_BY_NAME = "No World By Name";
	public static final String ERROR_NO_LOCATION_READ = "No Location Read";
	
	public abstract Optional<LiveShip> load(ShipsData data);
	public abstract OpenLoader save(LiveShip ship, BasicConfig config);
	
	protected String g_error = null;
	
	@Override
	public String getError(File file) {
		return g_error;
	}
	
	@Override
	public Optional<LiveShip> RAWLoad(File file) {
		int tempX = 0;
		int tempY = 0;
		int tempZ = 0;
		BasicConfig config = new BasicConfig(file);
		String[] sLic = config.get(String.class, AbstractShipsData.DATABASE_BLOCK).split(",");
		String name = config.get(String.class, AbstractShipsData.DATABASE_NAME);
		String sPilot = config.get(String.class, AbstractShipsData.DATABASE_PILOT);
		List<String> lStructure = config.getList(String.class, AbstractShipsData.DATABASE_STRUCTURE);
		String sSubPilots = config.get(String.class, AbstractShipsData.DATABASE_SUB_PILOTS);
		String[] sTel = config.get(String.class, AbstractShipsData.DATABASE_TELEPORT).split(",");
		String sWorld = config.get(String.class, AbstractShipsData.DATABASE_WORLD);
		World world;
		Location lic = null;
		Location tel = null;
		ShipsData data;
		OfflinePlayer user = Bukkit.getOfflinePlayer(UUID.fromString(sPilot));
		
		if(sWorld == null) {
			g_error = ERROR_NO_WORLD_READ;
			return Optional.empty();
		}
		world = Bukkit.getServer().getWorld(sWorld);
		if(world == null) {
			g_error = ERROR_NO_WORLD_BY_NAME;
			return Optional.empty();
		}
		try {
			tempX = Integer.parseInt(sLic[0]);
			tempY = Integer.parseInt(sLic[1]);
			tempZ = Integer.parseInt(sLic[2]);
			lic = new Location(world, tempX, tempY, tempZ);
		}catch (NumberFormatException e) {
			g_error = ERROR_NO_LOCATION_READ;
			return Optional.empty();
		}
		try {
			tempX = Integer.parseInt(sTel[0]);
			tempX = Integer.parseInt(sTel[1]);
			tempX = Integer.parseInt(sTel[2]);
			tel = new Location(world, lic.getBlockX() + tempX, lic.getBlockY() + tempY, lic.getBlockZ() + tempZ);
		} catch (NumberFormatException e) {
			tel = lic;
		}
		data = new AbstractShipsData(name, lic.getBlock(), tel);
		if(sPilot != null) {
			data.setOwner(user);
		}
		if(lStructure == null) {
			data.updateBasicStructure();
		}else {
			for(String values : lStructure) {
				String[] valueArgs = values.split(",");
				try {
					tempX = Integer.parseInt(valueArgs[0]);
					tempY = Integer.parseInt(valueArgs[1]);
					tempZ = Integer.parseInt(valueArgs[2]);
					Block block = world.getBlockAt(lic.getBlockX() + tempX, lic.getBlockY() + tempY, lic.getBlockZ() + tempZ);
					data.getStructure().add(block);
				}catch(NumberFormatException e) {
					continue;
				}
			}
		}
		if (sSubPilots != null) {
			List<OfflinePlayer> subPilots = new ArrayList<OfflinePlayer>();
			for (String sUuid : sSubPilots.split(",")) {
				subPilots.add(Bukkit.getOfflinePlayer(UUID.fromString(sUuid)));
			}
			data.getSubPilots().addAll(subPilots);
		}
		return load(data);
	}

	@Override
	public OpenRAWLoader RAWSave(LiveShip ship, File file) {
		BasicConfig config = new BasicConfig(file);
		String name = ship.getName();
		String type = ship.getStatic().getName();
		String world = ship.getLocation().getWorld().getName();
		Optional<OfflinePlayer> opUuid = ship.getOwner();
		List<String> pilots = new ArrayList<String>();
		List<String> structure = new ArrayList<String>();
		String block = ship.getLocation().getBlockX() + "," + ship.getLocation().getBlockY() + "," + ship.getLocation().getBlockZ();
		String teleport = ship.getTeleportToLocation().getBlockX() + "," + ship.getTeleportToLocation().getBlockY() + "," + ship.getTeleportToLocation().getBlockZ();
		for (OfflinePlayer player : ship.getSubPilots()) {
			pilots.add(player.getUniqueId().toString());
		}
		for(ShipVector vector : ship.getStructure().getRawVectors(ship)) {
			structure.add(vector.getX() + "," + vector.getY() + "," + vector.getZ());
		}
		config.setOverride(pilots, AbstractShipsData.DATABASE_SUB_PILOTS);
		config.setOverride(structure, AbstractShipsData.DATABASE_STRUCTURE);
		config.setOverride(block, AbstractShipsData.DATABASE_BLOCK);
		config.setOverride(teleport, AbstractShipsData.DATABASE_TELEPORT);
		config.setOverride(name, AbstractShipsData.DATABASE_NAME);
		config.setOverride(type, AbstractShipsData.DATABASE_TYPE);
		config.setOverride(world, AbstractShipsData.DATABASE_WORLD);
		config.setOverride(getLoaderName(), Loader.OPEN_LOADER_NAME);
		if(opUuid.isPresent()){
			config.setOverride(opUuid.get().getUniqueId().toString(), AbstractShipsData.DATABASE_PILOT);
		}
		if (ship instanceof LoadableShip) {
			LoadableShip ship2 = (LoadableShip) ship;
			for (DataHolder d : ship2.getAllData()) {
				if (d instanceof SerializedData) {
					SerializedData serData = (SerializedData) d;
					Set<Entry<String, Object>> entrySet = serData.getSerializedData().entrySet();
					for (Entry<String, Object> entry : entrySet) {
						config.set(entry.getValue(), entry.getKey());
					}
				}
			}
		}
		save(ship, config);
		config.save();
		return this;
	}

}

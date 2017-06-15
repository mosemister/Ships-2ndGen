package MoseShipsSponge.Vessel.Common.OpenLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

import MoseShips.CustomDataHolder.DataHolder;
import MoseShipsSponge.SerializedData;
import MoseShipsSponge.Configs.BasicConfig;
import MoseShipsSponge.Vessel.Common.RootTypes.AbstractShipsData;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;
import MoseShipsSponge.Vessel.Common.RootTypes.ShipsData;
import ninja.leaping.configurate.ConfigurationNode;

public abstract class OpenLoader implements OpenRAWLoader {

	public static final String ERROR_NO_WORLD_READ = "No World Read";
	public static final String ERROR_NO_WORLD_BY_NAME = "No World By Name";
	public static final String ERROR_NO_LOCATION_READ = "No Location Read";

	public abstract Optional<LiveShip> load(ShipsData data, BasicConfig config);

	public abstract OpenLoader save(LiveShip ship, BasicConfig config);

	protected String g_error = null;

	@SuppressWarnings("unchecked")
	@Override
	public Optional<LiveShip> RAWLoad(File file) {
		int tempX = 0;
		int tempY = 0;
		int tempZ = 0;
		BasicConfig config = new BasicConfig(file);
		String[] sLic = config.get(String.class, AbstractShipsData.DATABASE_BLOCK).split(",");
		String name = config.get(String.class, AbstractShipsData.DATABASE_NAME);
		String sPilot = config.get(String.class, AbstractShipsData.DATABASE_PILOT);
		List<String> lStructure = config.getList(ConfigurationNode::getString, AbstractShipsData.DATABASE_STRUCTURE);
		String sSubPilots = config.get(String.class, AbstractShipsData.DATABASE_SUB_PILOTS);
		String[] sTel = config.get(String.class, AbstractShipsData.DATABASE_TELEPORT).split(",");
		String sWorld = config.get(String.class, AbstractShipsData.DATABASE_WORLD);
		World world;
		Vector3i lic = null;
		Vector3i tel = null;
		ShipsData data;
		UserStorageService userStorage = Sponge.getServiceManager().getRegistration(UserStorageService.class).get()
				.getProvider();
		Optional<User> opUser = userStorage.get(UUID.fromString(sPilot));

		if (sWorld == null) {
			g_error = ERROR_NO_WORLD_READ;
			return Optional.empty();
		}
		Optional<World> opWorld = Sponge.getServer().getWorld(sWorld);
		if (opWorld.isPresent()) {
			world = opWorld.get();
		} else {
			g_error = ERROR_NO_WORLD_BY_NAME;
			return Optional.empty();
		}
		try {
			tempX = Integer.parseInt(sLic[0]);
			tempY = Integer.parseInt(sLic[1]);
			tempZ = Integer.parseInt(sLic[2]);
			lic = new Vector3i(tempX, tempY, tempZ);
		} catch (NumberFormatException e) {
			g_error = ERROR_NO_LOCATION_READ;
			return Optional.empty();
		}
		try {
			tempX = Integer.parseInt(sTel[0]);
			tempY = Integer.parseInt(sTel[1]);
			tempZ = Integer.parseInt(sTel[2]);
			tel = new Vector3i(tempX, tempY, tempZ);
		} catch (NumberFormatException e) {
			g_error = ERROR_NO_LOCATION_READ;
			return Optional.empty();
		}
		data = new AbstractShipsData(name, new Location<World>(world, lic), tel);
		if (opUser.isPresent()) {
			data.setOwner(opUser.get());
		}
		if (lStructure.isEmpty()) {
			data.updateBasicStructure();
		} else {
			lStructure.stream().forEach(s -> {
				String[] valueArgs = s.split(",");
				try {
					int X = Integer.parseInt(valueArgs[0]);
					int Y = Integer.parseInt(valueArgs[1]);
					int Z = Integer.parseInt(valueArgs[2]);
					Location<World> loc = new Location<World>(world, X, Y, Z);
					data.getStructure().add(loc);
				} catch (NumberFormatException e) {
				}
			});
		}
		if (sSubPilots != null) {
			List<User> subPilots = new ArrayList<>();
			for (String sUuid : sSubPilots.split(",")) {
				subPilots.add(userStorage.get(UUID.fromString(sUuid)).get());
			}
			data.getSubPilots().addAll(subPilots);
		}
		return load(data, config);
	}

	@Override
	public OpenRAWLoader RAWSave(LiveShip ship, File file) {
		BasicConfig config = new BasicConfig(file);
		String name = ship.getName();
		String type = ship.getStatic().getName();
		String world = ship.getLocation().getExtent().getName();
		Optional<User> opUser = ship.getOwner();
		List<String> pilots = new ArrayList<>();
		List<String> structure = new ArrayList<>();
		String block = ship.getLocation().getBlockX() + "," + ship.getLocation().getBlockY() + ","
				+ ship.getLocation().getBlockZ();
		String teleport = ship.getTeleportToLocation().getBlockX() + "," + ship.getTeleportToLocation().getBlockY()
				+ "," + ship.getTeleportToLocation().getBlockZ();
		ship.getSubPilots().stream().forEach(u -> pilots.add(u.getUniqueId().toString()));
		ship.getStructure().getRawVectors(ship).stream()
				.forEach(b -> structure.add(b.getX() + "," + b.getY() + "," + b.getZ()));
		config.setOverride(pilots, AbstractShipsData.DATABASE_SUB_PILOTS);
		config.setOverride(structure, AbstractShipsData.DATABASE_STRUCTURE);
		config.setOverride(block, AbstractShipsData.DATABASE_BLOCK);
		config.setOverride(teleport, AbstractShipsData.DATABASE_TELEPORT);
		config.setOverride(name, AbstractShipsData.DATABASE_NAME);
		config.setOverride(type, AbstractShipsData.DATABASE_TYPE);
		config.setOverride(world, AbstractShipsData.DATABASE_WORLD);
		config.setOverride(getLoaderName(), Loader.OPEN_LOADER_NAME);
		if (opUser.isPresent()) {
			config.setOverride(opUser.get().getUniqueId().toString(), AbstractShipsData.DATABASE_PILOT);
		}
		if (ship instanceof DataHolder) {
			DataHolder ship2 = (DataHolder) ship;
			ship2.getAllData().stream().forEach(d -> {
				if (d instanceof SerializedData) {
					SerializedData serData = (SerializedData) d;
					Set<Entry<Object[], Object>> entrySet = serData.getSerializedData().entrySet();
					entrySet.stream().forEach(e -> config.set(e.getValue(), e.getKey()));
				}
			});
		}
		save(ship, config);
		config.save();
		return this;
	}

	@Override
	public String getError(File file) {
		return g_error;
	}

}

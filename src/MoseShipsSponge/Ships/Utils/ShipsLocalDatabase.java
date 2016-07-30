package MoseShipsSponge.Ships.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.flowpowered.math.vector.Vector3i;

import MoseShipsSponge.SerializedData;
import MoseShipsSponge.Ships.ShipsData;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ShipsLocalDatabase {

	File FILE;
	ConfigurationLoader<CommentedConfigurationNode> LOADER;
	ConfigurationNode ROOT_NODE;

	public ShipsLocalDatabase(ShipsData data) throws IOException {
		FILE = new File("config/Ships/ShipsData/" + data.getName() + ".conf");
		FILE.getParentFile().mkdirs();
		FILE.createNewFile();
		LOADER = HoconConfigurationLoader.builder().setFile(FILE).build();
		ROOT_NODE = LOADER.load();
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> Optional<T> get(Class<T> type, Object... location) {
		ConfigurationNode node = ROOT_NODE.getNode(location);
		Object object = node.getValue();
		if (type.isInstance(object)) {
			return Optional.of((T) object);
		}
		return Optional.empty();
	}

	public <T extends Object> List<T> getList(Function<? super ConfigurationNode, T> type, Object... path) {
		ConfigurationNode node = ROOT_NODE.getNode(path);
		return node.getChildrenList().stream().map(type).collect(Collectors.toList());
	}

	public ShipsLocalDatabase set(Object value, Object... path) {
		ConfigurationNode node = ROOT_NODE.getNode(path);
		node.setValue(value);
		return this;
	}
	
	public boolean save(){
		try {
			LOADER.save(ROOT_NODE);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ShipsLocalDatabase saveBasicShip(ShipsData data) {
		List<String> pilots = new ArrayList<>();
		data.getSubPilots().stream().forEach(p -> pilots.add(p.getUniqueId().toString()));
		List<String> structure = new ArrayList<>();
		data.getBasicStructure().stream().forEach(l -> {
			Vector3i loc = l.getBlockPosition();
			int X = loc.getX();
			int Y = loc.getY();
			int Z = loc.getZ();
			structure.add(X + "," + Y + "," + Z);
		});
		String block = (data.getLocation().getBlockPosition().getX() + "," + data.getLocation().getBlockPosition().getY() + "," + data.getLocation().getBlockPosition().getZ());
		String teleport = (data.getTeleportToLocation().getBlockPosition().getX() + "," + data.getTeleportToLocation().getBlockPosition().getY() + "," + data.getTeleportToLocation().getBlockPosition()
				.getZ());
		set(structure, ShipsData.DATABASE_STRUCTURE);
		set(data.getName(), ShipsData.DATABASE_NAME);
		if (data.getOwner().isPresent()) {
			set(data.getOwner().get().getUniqueId().toString(), ShipsData.DATABASE_PILOT);
		}
		set(pilots, ShipsData.DATABASE_SUB_PILOTS);
		set(data.getTeleportToLocation().getExtent().getName(), ShipsData.DATABASE_WORLD);
		set(block, ShipsData.DATABASE_BLOCK);
		set(teleport, ShipsData.DATABASE_TELEPORT);
		data.getAllData().stream().forEach(d -> {
			if (d instanceof SerializedData) {
				SerializedData serData = (SerializedData) d;
				Set<Entry<String[], Object>> entrySet = serData.getSerializedData().entrySet();
				entrySet.forEach(e -> set(e.getKey(), e.getValue()));
			}
		});
		save();
		return this;
	}

}

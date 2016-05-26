package MoseShipsSponge.Ships.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.spongepowered.api.entity.living.player.User;

import MoseShipsSponge.Ships.ShipsData;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ShipsLocalDatabase {
	
	File FILE;
	ConfigurationLoader<CommentedConfigurationNode> LOADER;
	ConfigurationNode ROOT_NODE;
	
	public ShipsLocalDatabase(ShipsData data) throws IOException{
		FILE = new File("config/Ships/ShipsData/" + data.getName() + ".conf");
		LOADER = HoconConfigurationLoader.builder().setFile(FILE).build();
		ROOT_NODE = LOADER.load();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Object> Optional<T> get(Class<T> type, Object... location){
		ConfigurationNode node = ROOT_NODE.getNode(location);
		Object object = node.getValue();
		if(type.isInstance(object)){
			return Optional.of((T)object);
		}
		return Optional.empty();
	}
	
	public <T extends Object> List<T> getList(Function<? super ConfigurationNode, T> type, Object...path){
		ConfigurationNode node = ROOT_NODE.getNode(path);
		return node.getChildrenList().stream().map(type).collect(Collectors.toList());
	}
	
	public ShipsLocalDatabase set(Object value, Object... path){
		ConfigurationNode node = ROOT_NODE.getNode(path);
		node.setValue(path);
		return this;
	}
	
	public ShipsLocalDatabase saveBasicShip(ShipsData data){
		List<String> pilots = new ArrayList<>();
		for(User user : data.getSubPilots()){
			pilots.add(user.getUniqueId().toString());
		}
		String block = (data.getLocation().getBlockPosition().getX() + "," + data.getLocation().getBlockPosition().getY() + "," + data.getLocation().getBlockPosition().getZ());
		String teleport = (data.getTeleportToLocation().getBlockPosition().getX() + "," + data.getTeleportToLocation().getBlockPosition().getY() + "," + data.getTeleportToLocation().getBlockPosition().getZ());
		set(ShipsData.DATABASE_NAME, data.getName());
		set(ShipsData.DATABASE_PILOT, data.getOwner().getUniqueId().toString());
		set(ShipsData.DATABASE_NAME, pilots);
		set(ShipsData.DATABASE_WORLD, data.getTeleportToLocation().getExtent().getName());
		set(ShipsData.DATABASE_BLOCK, block);
		set(ShipsData.DATABASE_TELEPORT, teleport);
		return this;
	}

}

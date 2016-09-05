package MoseShipsSponge.Ships.VesselTypes.Loading;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;

import MoseShips.CustomDataHolder.DataHolder;
import MoseShipsSponge.SerializedData;
import MoseShipsSponge.Configs.BasicConfig;
import MoseShipsSponge.Ships.ShipsData;
import MoseShipsSponge.Ships.VesselTypes.LoadableShip;

public class ShipsLocalDatabase extends BasicConfig{

	public ShipsLocalDatabase(LoadableShip ship) {
		super(new File("config/Ships/VesselData/" + ship.getStatic().getName() + "/" + ship.getName() + ".conf"));
	}

	public ShipsLocalDatabase saveBasicShip(LoadableShip data) {
		List<String> pilots = new ArrayList<String>();
		for(OfflinePlayer player : data.getSubPilots()){
			pilots.add(player.getUniqueId().toString());
		}
		List<String> structure = new ArrayList<String>();
		for(Block block : data.getBasicStructure()){
			int X = block.getX();
			int Y = block.getY();
			int Z = block.getZ();
			structure.add(X + "," + Y + "," + Z);
		}
		String block = (data.getLocation().getBlockX() + "," + data.getLocation().getBlockY() + "," + data.getLocation().getBlockZ());
		String teleport = (data.getTeleportToLocation().getBlockX() + "," + data.getTeleportToLocation().getBlockY() + "," + data.getTeleportToLocation().getBlockZ());
		set(structure, ShipsData.DATABASE_STRUCTURE);
		set(data.getName(), ShipsData.DATABASE_NAME);
		set(data.getStatic().getName(), ShipsData.DATABASE_TYPE);
		if (data.getOwner().isPresent()) {
			set(data.getOwner().get().getUniqueId().toString(), ShipsData.DATABASE_PILOT);
		}
		set(pilots, ShipsData.DATABASE_SUB_PILOTS);
		set(data.getTeleportToLocation().getWorld().getName(), ShipsData.DATABASE_WORLD);
		set(block, ShipsData.DATABASE_BLOCK);
		set(teleport, ShipsData.DATABASE_TELEPORT);
		for(DataHolder d : data.getAllData()){
			if (d instanceof SerializedData) {
				SerializedData serData = (SerializedData) d;
				Set<Entry<String, Object>> entrySet = serData.getSerializedData().entrySet();
				for(Entry<String, Object> entry : entrySet){
					set(entry.getValue(), entry.getKey());
				}
			}
		}
		save();
		return this;
	}

}

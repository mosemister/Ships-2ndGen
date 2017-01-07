package MoseShipsBukkit.Ships.VesselTypes.Loading;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;

import MoseShips.CustomDataHolder.DataHolder;
import MoseShipsBukkit.SerializedData;
import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Ships.AbstractShipsData;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveShip;

public class ShipsLocalDatabase extends BasicConfig {

	public ShipsLocalDatabase(LiveShip ship) {
		super(new File("plugins/Ships/VesselData/" + ship.getStatic().getName() + "/" + ship.getName() + ".yml"));
	}

	public ShipsLocalDatabase saveBasicShip(LiveShip data) {
		List<String> pilots = new ArrayList<String>();
		for (OfflinePlayer player : data.getSubPilots()) {
			pilots.add(player.getUniqueId().toString());
		}
		List<String> structure = new ArrayList<String>();
		for (Block block : data.getBasicStructure()) {
			int X = block.getX();
			int Y = block.getY();
			int Z = block.getZ();
			structure.add(X + "," + Y + "," + Z);
		}
		String block = (data.getLocation().getBlockX() + "," + data.getLocation().getBlockY() + ","
				+ data.getLocation().getBlockZ());
		String teleport = (data.getTeleportToLocation().getBlockX() + "," + data.getTeleportToLocation().getBlockY()
				+ "," + data.getTeleportToLocation().getBlockZ());
		setOverride(structure, AbstractShipsData.DATABASE_STRUCTURE);
		setOverride(data.getName(), AbstractShipsData.DATABASE_NAME);
		setOverride(data.getStatic().getName(), AbstractShipsData.DATABASE_TYPE);
		if (data.getOwner().isPresent()) {
			setOverride(data.getOwner().get().getUniqueId().toString(), AbstractShipsData.DATABASE_PILOT);
		}
		setOverride(pilots, AbstractShipsData.DATABASE_SUB_PILOTS);
		setOverride(data.getTeleportToLocation().getWorld().getName(), AbstractShipsData.DATABASE_WORLD);
		setOverride(block, AbstractShipsData.DATABASE_BLOCK);
		setOverride(teleport, AbstractShipsData.DATABASE_TELEPORT);
		if (data instanceof LoadableShip) {
			LoadableShip ship = (LoadableShip) data;
			for (DataHolder d : ship.getAllData()) {
				if (d instanceof SerializedData) {
					SerializedData serData = (SerializedData) d;
					Set<Entry<String, Object>> entrySet = serData.getSerializedData().entrySet();
					for (Entry<String, Object> entry : entrySet) {
						set(entry.getValue(), entry.getKey());
					}
				}
			}
			ship.onSave(this);
		}
		save();
		return this;
	}

}

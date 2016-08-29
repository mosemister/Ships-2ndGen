package MoseShipsSponge.Ships.VesselTypes.Loading;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.flowpowered.math.vector.Vector3i;

import MoseShipsSponge.SerializedData;
import MoseShipsSponge.Configs.BasicConfig;
import MoseShipsSponge.Ships.ShipsData;
import MoseShipsSponge.Ships.VesselTypes.LoadableShip;

public class ShipsLocalDatabase extends BasicConfig{

	public ShipsLocalDatabase(LoadableShip ship) {
		super(new File("config/Ships/VesselData/" + ship.getStatic().getName() + "/" + ship.getName() + ".conf"));
	}

	public ShipsLocalDatabase saveBasicShip(LoadableShip data) {
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
		set(data.getStatic().getName(), ShipsData.DATABASE_TYPE);
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

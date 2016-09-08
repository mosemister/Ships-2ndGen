package MoseShipsBukkit.Ships.VesselTypes.DefaultTypes;

import org.bukkit.Location;
import org.bukkit.block.Block;

import MoseShipsBukkit.Ships.ShipsData;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;

public abstract class WaterType extends LoadableShip {

	public WaterType(String name, Block sign, Location teleport) {
		super(name, sign, teleport);
	}

	public WaterType(ShipsData data) {
		super(data);
	}

	public int getWaterLevel() {
		return 0;
	}

}

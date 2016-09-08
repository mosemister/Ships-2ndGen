package MoseShipsBukkit.Ships.VesselTypes.DefaultTypes;

import org.bukkit.Location;
import org.bukkit.block.Block;

import MoseShipsBukkit.Ships.ShipsData;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;

public abstract class AirType extends LoadableShip {

	public AirType(String name, Block sign, Location teleport) {
		super(name, sign, teleport);
	}

	public AirType(ShipsData data) {
		super(data);
	}

}

package MoseShipsSponge.Ships.VesselTypes.DefaultTypes;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Ships.ShipsData;
import MoseShipsSponge.Ships.VesselTypes.ShipType;

public abstract class AirType extends ShipType {
	
	public AirType(String name, Location<World> sign, Location<World> teleport) {
		super(name, sign, teleport);
	}
	
	public AirType(ShipsData data){
		super(data);
	}

}

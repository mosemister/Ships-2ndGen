package MoseShipsSponge.Ships.VesselTypes.DefaultTypes;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Ships.ShipsData;
import MoseShipsSponge.Ships.VesselTypes.ShipType;

public abstract class WaterType extends ShipType{
	
	public WaterType(String name, Location<World> sign, Location<World> teleport) {
		super(name, sign, teleport);
	}
	
	public WaterType(ShipsData data){
		super(data);
	}
	
	public int getWaterLevel(){
		return 0;
	}

}

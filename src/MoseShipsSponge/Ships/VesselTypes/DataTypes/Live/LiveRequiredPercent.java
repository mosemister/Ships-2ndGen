package MoseShipsSponge.Ships.VesselTypes.DataTypes.Live;

import MoseShipsSponge.Ships.VesselTypes.DataTypes.LiveData;

public interface LiveRequiredPercent extends LiveData {
	
	public int getRequiredPercent();
	public int getRequirement();
	public String[] getPercentBlocks();

}

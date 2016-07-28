package MoseShipsSponge.Ships.VesselTypes.DataTypes.Live;

import MoseShipsSponge.Ships.VesselTypes.DataTypes.LiveData;

public interface LiveFuel extends LiveData {

	public int getFuel();

	public String[] getFuelMaterials();

	public boolean removeFuel(int Amount);

}

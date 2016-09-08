package MoseShipsBukkit.Ships.VesselTypes.DataTypes.Live;

import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveData;

public interface LiveFuel extends LiveData {

	public int getFuel();

	public String[] getFuelMaterials();

	public boolean removeFuel(int Amount);

}

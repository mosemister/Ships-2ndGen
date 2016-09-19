package MoseShipsBukkit.Ships.VesselTypes.DataTypes.Live;

import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveData;
import MoseShipsBukkit.Utils.State.BlockState;

public interface LiveFuel extends LiveData {

	public int getFuel();

	public BlockState[] getFuelMaterials();
	
	public int getConsumptionAmount();

	public boolean removeFuel(int Amount);

}

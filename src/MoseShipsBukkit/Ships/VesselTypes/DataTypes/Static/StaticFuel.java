package MoseShipsBukkit.Ships.VesselTypes.DataTypes.Static;

import MoseShipsBukkit.Utils.State.BlockState;

public interface StaticFuel {
	
	public int getDefaultFuel();
	public BlockState[] getDefaultFuelMaterial();
	public boolean getDefaultConsumptionAmount();

}

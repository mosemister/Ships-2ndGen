package MoseShipsBukkit.Ships.VesselTypes.DataTypes.Live;

import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveShip;
import MoseShipsBukkit.Utils.State.BlockState;

public interface LiveFuel extends LiveShip {

	public static final String FUEL_MATERIALS = "ShipsData.Fuel.Items";
	public static final String FUEL_CONSUMPTION = "ShipsData.Fuel.Consumption";

	public int getFuel();

	public BlockState[] getFuelMaterials();

	public int getConsumptionAmount();

	public boolean removeFuel(int amount);

}

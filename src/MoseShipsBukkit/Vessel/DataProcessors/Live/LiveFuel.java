package MoseShipsBukkit.Vessel.DataProcessors.Live;

import MoseShipsBukkit.ShipBlock.BlockState;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;

public interface LiveFuel extends LiveShip {

	public static final String FUEL_MATERIALS = "ShipsData.Fuel.Items";
	public static final String FUEL_CONSUMPTION = "ShipsData.Fuel.Consumption";

	public int getFuel();

	public BlockState[] getFuelMaterials();

	public int getConsumptionAmount();

	public boolean removeFuel(int amount);

}

package MoseShipsBukkit.Vessel.Common.Static.Types;

import MoseShipsBukkit.ShipBlock.BlockState;
import MoseShipsBukkit.Vessel.Common.Static.StaticShipType;

public interface StaticFuel extends StaticShipType {

	public static final String DEFAULT_FUEL = "Default.Requirement.Fuel.Fuels";
	public static final String DEFAULT_FUEL_CONSUMPTION = "Default.Requirement.Fuel.Consumption";

	public BlockState[] getDefaultFuelMaterial();
	public void setDefaultFuelMaterial(BlockState... state);

	public int getDefaultConsumptionAmount();
	public void setDefaultConsumptionAmount(int amount);

}

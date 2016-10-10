package MoseShipsBukkit.Ships.VesselTypes.DataTypes.Static;

import MoseShipsBukkit.Utils.State.BlockState;

public interface StaticFuel {

	public static final String DEFAULT_FUEL = "Default.Requirement.Fuel.Fuels";
	public static final String DEFAULT_FUEL_CONSUMPTION = "Default.Requirement.Fuel.Consumption";

	public BlockState[] getDefaultFuelMaterial();

	public int getDefaultConsumptionAmount();

}

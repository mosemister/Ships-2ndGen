package MoseShipsBukkit.ShipsTypes.HookTypes;

import java.util.Map;

import org.bukkit.Material;

import MoseShipsBukkit.StillShip.Vessel.BaseVessel;

public interface Fuel {

	/**
	 * removes a set amount of fuel from a vessel.
	 * 
	 * <p>
	 * if this returns 'false' then the move event will be cancelled. If returns
	 * 'true' then it will continue the move event
	 * </p>
	 */
	public boolean removeFuel(BaseVessel vessel);

	/**
	 * gets the total amount of fuel
	 */
	public int getTotalFuel(BaseVessel vessel);

	/**
	 * gets the fuel type
	 */
	public Map<Material, Byte> getFuel();

}

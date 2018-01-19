package MoseShipsBukkit.ShipsTypes.HookTypes;

import java.util.Collection;
import java.util.Set;

import org.bukkit.Material;

import MoseShipsBukkit.StillShip.Vessel.Ship;

public interface Fuel {

	/**
	 * removes a set amount of fuel from a vessel.
	 * 
	 * <p>
	 * if this returns 'false' then the move event will be cancelled. If returns
	 * 'true' then it will continue the move event
	 * </p>
	 */
	public boolean removeFuel(Ship vessel);

	/**
	 * gets the total amount of fuel
	 */
	public int getTotalFuel(Ship vessel);

	/**
	 * gets the fuel consumption
	 */
	public int getFuelConsumption();
	
	/**
	 * gets the accepted fuel types
	 */
	public Set<Material> getFuelTypes();
	
	
	/**
	 * sets the accepted fuel types
	 */
	public void setFuelTypes(Collection<Material> fuels);
	
	/**
	 * sets the fuel consumption amount
	 */
	public void setFuelConsumption(int consumption);

}

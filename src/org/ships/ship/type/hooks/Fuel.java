package org.ships.ship.type.hooks;

import java.util.Collection;
import java.util.Set;

import org.bukkit.Material;
import org.ships.ship.Ship;

public interface Fuel {
	public boolean removeFuel(Ship var1);

	public int getTotalFuel(Ship var1);

	public int getFuelConsumption();

	public Set<Material> getFuelTypes();

	public void setFuelTypes(Collection<Material> var1);

	public void setFuelConsumption(int var1);
}

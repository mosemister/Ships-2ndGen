package MoseShipsBukkit.ShipTypes.NormalRequirements;

import java.util.Map;

import org.bukkit.Material;

import MoseShipsBukkit.StillShip.Vessel;

@Deprecated
public interface Fuel {
	
	//This is the interface that forces select vessels to use fuel.

	Map<Material, Byte> getFuel();
	int getFuelTakeAmount();
	int getFuelCount(Vessel vessel);
	void setFuel(Map<Material, Byte> fuels);
	void setFuelTakeAmount(int A);
	
}

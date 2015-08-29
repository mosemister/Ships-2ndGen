package MoseShipsBukkit.ShipTypes.SubType;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.ItemStack;

import MoseShipsBukkit.ShipTypes.VesselType;
import MoseShipsBukkit.StillShip.SpecialBlock;
import MoseShipsBukkit.StillShip.Vessel;

public abstract class FuelVesselType extends VesselType{
	
	Map<Material, Byte> FUEL;
	int FOUNDFUEL;

	public FuelVesselType(String name, int speed, int boost, boolean isAutoPilot) {
		super(name, speed, boost, isAutoPilot);
	}
	
	public FuelVesselType(String name, int speed, int boost, boolean isAutoPilot, boolean canFall) {
		super(name, speed, boost, isAutoPilot, canFall);
	}
	
	public Map<Material, Byte> getFuel() {
		return FUEL;
	}
	
	public void setFuel(Map<Material, Byte> fuel){
		FUEL = fuel;
	}

	public int getFuelTakeAmount() {
		return FOUNDFUEL;
	}
	
	public void setFuelTakeAmount(int A){
		FOUNDFUEL = A;
	}
	
	@SuppressWarnings("deprecation")
	public int getFuelCount(Vessel vessel) {
		int count = 0;
		for(SpecialBlock block : vessel.getStructure().getSpecialBlocks()){
			if (block.getBlock().getState() instanceof Furnace){
				Furnace furnace = (Furnace)block.getBlock().getState();
				ItemStack item = furnace.getInventory().getFuel();
				if (item != null){
					Map<Material, Byte> fuels = getFuel();
					for (Entry<Material, Byte> fuel : fuels.entrySet()){
						if (fuel.getKey().equals(item.getType())){
							if (fuel.getValue() == -1){
								count = (count + item.getAmount());
							}else{
								if (fuel.getValue() == item.getData().getData()){
									count = (count + item.getAmount());
								}
							}
						}
					}
				}
			}
		}
		return count;
	}

}

package MoseShipsBukkit.ShipTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;

import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.MovingShip.MovingBlock;
import MoseShipsBukkit.ShipTypes.Types.AirShip;
import MoseShipsBukkit.ShipTypes.Types.Marsship;
import MoseShipsBukkit.ShipTypes.Types.Plane;
import MoseShipsBukkit.ShipTypes.Types.SolarShip;
import MoseShipsBukkit.ShipTypes.Types.Submarine;
import MoseShipsBukkit.ShipTypes.Types.WaterShip;
import MoseShipsBukkit.StillShip.SpecialBlock;
import MoseShipsBukkit.StillShip.Vessel;

public abstract class VesselType implements VesselDefaults{
	
	//default vessel types with default values
	public static VesselType WATERSHIP = new WaterShip();
	public static VesselType AIRSHIP = new AirShip();
	public static VesselType MARSSHIP = new Marsship();
	public static VesselType PLANE = new Plane();
	public static VesselType SUBMARINE = new Submarine();
	public static VesselType SOLARSHIP = new SolarShip();
	
	String TYPENAME;
	int DEFAULTSPEED;
	int DEFAULTBOOSTSPEED;
	boolean ISAUTOPILOTALLOWED;
	List<Material> MOVEIN;
	boolean CANFALL;
	
	static List<VesselType> CUSTOMVESSELS = new ArrayList<VesselType>();
	
	public abstract boolean CheckRequirements(Vessel vessel, MovementMethod move, List<MovingBlock> blocks, Player player);
	
	public VesselType(String name, int speed, int boost, boolean isAutoPilot){
		TYPENAME = name;
		DEFAULTSPEED = speed;
		DEFAULTBOOSTSPEED = boost;
		ISAUTOPILOTALLOWED = isAutoPilot;
	}
	
	public VesselType(String name, int speed, int boost, boolean isAutoPilot, boolean canFall){
		TYPENAME = name;
		DEFAULTSPEED = speed;
		DEFAULTBOOSTSPEED = boost;
		ISAUTOPILOTALLOWED = isAutoPilot;
		CANFALL = canFall;
	}
	
	public String getName(){
		return TYPENAME;
	}
	
	public int getDefaultSpeed(){
		return DEFAULTSPEED;
	}
	
	public List<Material> getMoveInMaterials(){
		return MOVEIN;
	}
	
	public void setMoveInMaterials(List<Material> material){
		MOVEIN = material;
	}
	
	public void setDefaultSpeed(int A){
		DEFAULTSPEED = A;
	}
	
	public void setDefaultBoostSpeed(int A){
		DEFAULTBOOSTSPEED = A;
	}
	
	public int getDefaultBoostSpeed(){
		return DEFAULTBOOSTSPEED;
	}
	
	public boolean isAutoPilotAllowed(){
		return ISAUTOPILOTALLOWED;
	}
	
	//checks if the vessel is going to be moving into a select few blocks, returns true if vessel is moving into at least 1 material value
	public boolean isMaterialInMovingTo(List<MovingBlock> blocks, List<Material> material){
		for(MovingBlock block : blocks){
			if (material.contains(block.getMovingTo().getBlock().getType())){
				return true;
			}
		}
		return false;
	}
	
	//checks if the vessel is moving into anything but the ignorelist
	public boolean isMovingInto(List<MovingBlock> blocks, List<Material> ignoreList){
		for(MovingBlock block : blocks){
			if (!ignoreList.contains(block.getMovingTo().getBlock().getType())){
				
				return true;
			}
		}
		return false;
	}
	
	//checks if the vessel has enough fuel to be taken 
	@SuppressWarnings("deprecation")
	public boolean checkFuel(Map<Material, Byte> fuel, Vessel vessel, int take){
		int count = 0;
		for(SpecialBlock block : vessel.getStructure().getSpecialBlocks()){
			if (block.getBlock().getState() instanceof Furnace){
				Furnace bBlock = (Furnace)block.getBlock().getState();
				FurnaceInventory inv = bBlock.getInventory();
				ItemStack item = inv.getFuel();
				for(Entry<Material, Byte> entry : fuel.entrySet()){
					if (item != null){
						if (entry.getKey().equals(item.getType())){
							if(entry.getValue() != -1){
								if (entry.getValue() == item.getData().getData()){
									if (item.getAmount() - take >= 0){
										count = (count + take);
									}else{
										count = count + item.getAmount();
									}
								}
							}else{
								if (item.getAmount() - take >= 0){
									count = (count + take);
								}else{
									count = count + item.getAmount();
								}
							}
						}
					}
				}
			}
			if (count >= take){
				return true;
			}
		}
		return false;
	}
	
	//does the same as above only takes the fuel away instead of checking it
	@SuppressWarnings("deprecation")
	public boolean takeFuel(Map<Material, Byte> fuel, Vessel vessel, int take){
		int count = 0;
		while (count != take){
			for(SpecialBlock block : vessel.getStructure().getSpecialBlocks()){
				if (block.getBlock().getState() instanceof Furnace){
					Furnace bBlock = (Furnace)block.getBlock().getState();
					FurnaceInventory inv = bBlock.getInventory();
					ItemStack item = inv.getFuel();
					for(Entry<Material, Byte> entry : fuel.entrySet()){
						if (item != null){
							if (entry.getKey().equals(item.getType())){
								if(entry.getValue() != -1){
									if (entry.getValue() == item.getData().getData()){
										if (item.getAmount() - take > 0){
											count = (count + take);
											item.setAmount(item.getAmount() - take);
										}else{
											count = count + item.getAmount();
											Map<Integer, ItemStack> map = block.getItems();
											map.remove(1);
											map.put(1, null);
										}
									}
								}else{
									if (item.getAmount() - take > 0){
										count = (count + take);
										item.setAmount(item.getAmount() - take);
									}else{
										count = count + item.getAmount();
										Map<Integer, ItemStack> map = block.getItems();
										map.remove(1);
										map.put(1, null);
									}
								}
							}
							if (count == take){
								return true;
							}else if (count < take){
								int differnce = take - count;
								item.setAmount(differnce);
								return true;
							}
						}
					}
				}
			}
		}
		if (count == take){
			return true;
		}
		return false;
	}
	
	//checks vessels current state and checks if the selected material is in that state
	public boolean isMaterialInMovingFrom(List<MovingBlock> blocks, Material material){
		for(MovingBlock block : blocks){
			if (block.getBlock().getType().equals(material)){
				return true;
			}
		}
		return false;
	}
	
	//checks vessels current state, then out of that state it grabs the selected materials, then finds the percentage of those selected blocks
	//compared to the full list of blocks. returns true if the result is over the minPercent
	public boolean isPercentInMovingFrom(List<MovingBlock> blocks, List<Material> checkForBlocks, float minPercent){
		float count = 0;
		for(MovingBlock block : blocks){
			if (checkForBlocks.contains(block.getBlock().getType())){
				count++;
			}
		}
		float percentAmount = (blocks.size()*(minPercent/100f));
		if (count >= percentAmount){
			return true;
		}
		return false;
	}
	
	// returns the percent you are off by.
	public float getOffBy(List<MovingBlock> blocks, List<Material> material, float minPercent){
		float count = 0;
		for(MovingBlock block : blocks){
			if (material.contains(block.getBlock().getType())){
				count++;
			}
		}
		float percentAmount = (blocks.size()*(minPercent/100f));
		if (count >= percentAmount){
			return 0;
		}
		return blocks.size() - count;
	}
	
	//adds custom vessels to Ships (newer method)
	public void inject(){
		CUSTOMVESSELS.add(this);
	}
	
	//gets a vesselType by its name, returns VesselType if found, returns null in non are found
	public static VesselType getTypeByName(String name){
		for(VesselType type : VesselType.values()){			
			if (type.getName().equalsIgnoreCase(name)){
				return type;
			}
		}
		return null;
	}
	
	//adds custom vessels to Ships (older method)
	public static void injectCustomVesselType(VesselType type){
		CUSTOMVESSELS.add(type);
	}
	
	//gets all VesselTypes that are not custom
	public static VesselType[] defaultValues(){
		VesselType[] types = {WATERSHIP, AIRSHIP, MARSSHIP, PLANE, SUBMARINE, SOLARSHIP};
		return types;
	}
	
	//gets all vesselTypes that are custom
	public static List<VesselType> customValues(){
		return CUSTOMVESSELS;
	}
	
	//gets all vesselTypes no matter if they are custom or not
	public static List<VesselType> values(){
		List<VesselType> types = customValues();
		for(VesselType type : defaultValues()){
			types.add(type);
		}
		return types;
	}
}

package MoseShipsBukkit.ShipTypes.Types;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.MovingShip.MovingBlock;
import MoseShipsBukkit.ShipTypes.VesselType;
import MoseShipsBukkit.ShipTypes.NormalRequirements.Fuel;
import MoseShipsBukkit.ShipTypes.NormalRequirements.RequiredBlock;
import MoseShipsBukkit.ShipTypes.NormalRequirements.RequiredBlockPercent;
import MoseShipsBukkit.StillShip.SpecialBlock;
import MoseShipsBukkit.StillShip.Vessel;

public class Submarine extends VesselType implements Fuel, RequiredBlock, RequiredBlockPercent{

	Map<Material, Byte> FUEL;
	int FOUNDFUEL;
	int PERCENT;
	List<Material> REQUIREDBLOCK;
	List<Material> MOVEINBLOCK;
	int MAXBLOCKS;
	int MINBLOCKS;
	
	public Submarine() {
		super("Submarine", 2, 3, false);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void save(Vessel vessel) {
		File file = new File("plugins/Ships/VesselData/" + vessel.getName() + ".yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection config = configuration.createSection("ShipsData");
		config.set("Player.Name", vessel.getOwner().getUniqueId().toString());
		config.set("Type", "Submarine");
		config.set("Protected", vessel.isProtected());
		config.set("Config.Block.Percent", getPercent());
		config.set("Config.Block.Max", getMaxBlocks());
		config.set("Config.Block.Min", getMinBlocks());
		List<String> fuels = new ArrayList<String>();
		for (Entry<Material, Byte> fuels2 : this.getFuel().entrySet()){
			fuels.add(fuels2.getKey().getId() + "," + fuels2.getValue());
		}
		config.set("Config.Fuel.Fuels", fuels);
		config.set("Config.Fuel.Consumption", getFuelTakeAmount());
		config.set("Config.Speed.Engine", getDefaultSpeed());
		try{
			configuration.save(file);
		}catch(IOException e){
			e.printStackTrace();
		}		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void createConfig() {
		File file = getFile();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("Speed.Engine", 4);
		config.set("Speed.Boost", 5);
		config.set("Blocks.Max", 4500);
		config.set("Blocks.Min", 1);
		config.set("Blocks.requiredPercent", 50);
		List<Integer> requiredBlocks = new ArrayList<Integer>();
		requiredBlocks.add(42);
		config.set("Blocks.requiredBlocks", requiredBlocks);
		List<String> fuel = new ArrayList<String>();
		fuel.add(Material.COAL_BLOCK.getId() + ",-1");
		config.set("Fuel.Fuels", fuel);
		config.set("Fuel.TakeAmount", 1);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void loadDefault() {
		File file = getFile();
		if (!file.exists()){
			createConfig();
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		this.setDefaultSpeed(config.getInt("Speed.Engine"));
		this.setDefaultBoostSpeed(config.getInt("Speed.Boost"));
		this.setPercent(config.getInt("Blocks.requiredPercent"));
		this.setMaxBlocks(config.getInt("Blocks.Max"));
		this.setMinBlocks(config.getInt("Blocks.Min"));
		List<Material> requiredmaterials = new ArrayList<Material>();
		for(int id : config.getIntegerList("Blocks.requiredBlocks")){
			Material material = Material.getMaterial(id);
			requiredmaterials.add(material);
		}
		int take = config.getInt("Fuel.TakeAmount");
		String[] fuel = config.getString("Fuel.Fuels").split(",");
		Material material = Material.getMaterial(Integer.parseInt(fuel[0].replace("[", "")));
		byte data = Byte.parseByte(fuel[1].replace("]", ""));
		Map<Material, Byte> fuels = new HashMap<Material, Byte>();
		fuels.put(material, data);
		this.setFuel(fuels);
		this.setRequiredBlock(requiredmaterials);
		this.setFuelTakeAmount(take);
		List<Material> moveIn = new ArrayList<Material>();
		moveIn.add(Material.WATER);
		moveIn.add(Material.STATIONARY_WATER);
		this.setMoveInMaterials(moveIn);
		
	}

	@Override
	public File getFile() {
		File file = new File("plugins/Ships/Configuration/VesselTypes/Submarine.yml");
		return file;
	}

	@Override
	public int getMaxBlocks() {
		return MAXBLOCKS;
	}

	@Override
	public int getMinBlocks() {
		return MINBLOCKS;
	}

	@Override
	public void setMaxBlocks(int amount) {
		MAXBLOCKS = amount;
		
	}

	@Override
	public void setMinBlocks(int amount) {
		MINBLOCKS = amount;
		
	}

	@Override
	public int getPercent() {
		return PERCENT;
	}

	@Override
	public void setPercent(int percent) {
		PERCENT = percent;
	}

	@Override
	public List<Material> getRequiredBlock() {
		return REQUIREDBLOCK;
	}

	@Override
	public void setRequiredBlock(List<Material> material) {
		REQUIREDBLOCK = material;
	}

	@Override
	public Map<Material, Byte> getFuel() {
		return FUEL;
	}

	@Override
	public int getFuelTakeAmount() {
		return FOUNDFUEL;
	}

	@Override
	public void setFuel(Map<Material, Byte> fuels) {
		FUEL = fuels;
		
	}

	@Override
	public void setFuelTakeAmount(int A) {
		FOUNDFUEL = A;
	}

	@Override
	public boolean CheckRequirements(Vessel vessel, MovementMethod move, List<MovingBlock> blocks, Player player) {
		if (blocks.size() <= getMaxBlocks()){
			if (blocks.size() >= getMinBlocks()){
				if (this.isMaterialInMovingTo(blocks, getMoveInMaterials())){
					if (this.isPercentInMovingFrom(blocks, getRequiredBlock(), getPercent())){
						if (move.equals(MovementMethod.MOVE_DOWN)){
							return true;
						}else{
							if (this.checkFuel(getFuel(), vessel, getFuelTakeAmount())){
								this.takeFuel(getFuel(), vessel, getFuelTakeAmount());
								return true;
							}else{
								player.sendMessage(Ships.runShipsMessage("Needs more fuel", true));
								return false;
							}
						}
					}else{
						List<String> materials = new ArrayList<String>();
						for(Material material : getRequiredBlock()){
							materials.add(material.name());
						}
						player.sendMessage(Ships.runShipsMessage("Needs more (of either) " + materials, true));
						return false;
					}
				}else{
					player.sendMessage(Ships.runShipsMessage("Must be in water", true));
					return false;
				}
			}else{
				player.sendMessage(Ships.runShipsMessage("Your vessel is " + blocks.size() + " and needs to be " + getMinBlocks() + " or more", true));
				return false;
			}
		}else{
			player.sendMessage(Ships.runShipsMessage("Your vessel is " + blocks.size() + " and needs to be " + getMaxBlocks() + " or less", true));
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
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

	@Override
	public Vessel loadFromClassicVesselFile(Vessel vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Submarine){
			Submarine submarine = (Submarine)type;
			List<Integer> fuels = config.getIntegerList("ShipsData.Config.Fuel.Fuels");
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			int percent = config.getInt("ShipsData.Config.Block.Percent");
			submarine.setPercent(percent);
			submarine.setFuelTakeAmount(consumption);
			HashMap<Material, Byte> fuelsR = new HashMap<Material, Byte>();
			for (int id : fuels){
				@SuppressWarnings("deprecation")
				Material material = Material.getMaterial(id);
				fuelsR.put(material, (byte)-1);
			}
			submarine.setFuel(fuelsR);
			vessel.setVesselType(submarine);
		}
		return vessel;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Vessel loadFromNewVesselFile(Vessel vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Submarine){
			Submarine sub = (Submarine)type;
			int percent = config.getInt("ShipsData.Config.Block.Percent");
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			List<String> fuelsL = config.getStringList("ShipsData.Config.Fuel.Fuels");
			sub.setPercent(percent);
			sub.setFuelTakeAmount(consumption);
			if (fuelsL.size() != 0){
				Map<Material, Byte> fuels = new HashMap<Material, Byte>();
				for (String fuelS : fuelsL){
					String[] fuelM = fuelS.split(",");
					fuels.put(Material.getMaterial(Integer.parseInt(fuelM[0])), Byte.parseByte(fuelM[1]));
				}
				sub.setFuel(fuels);
			}
			vessel.setVesselType(sub);
		}
		return vessel;
	}

}

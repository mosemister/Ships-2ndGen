package MoseShipsBukkit.ShipTypes.Types;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.block.Sign;
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

public class AirShip extends VesselType implements RequiredBlock, RequiredBlockPercent, Fuel{

	Map<Material, Byte> FUEL;
	int FOUNDFUEL;
	int PERCENT;
	List<Material> REQUIREDBLOCK;
	List<Material> MOVEINBLOCK;
	int MAXBLOCKS;
	int MINBLOCKS;
	
	public AirShip() {
		super("Airship", 2, 3, true);
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
	public boolean CheckRequirements(Vessel vessel, MovementMethod move, List<MovingBlock> blocks, Player player) {
		if (blocks.size() <= getMaxBlocks()){
			if (blocks.size() >= getMinBlocks()){
				if (this.isMovingInto(blocks, getMoveInMaterials())){
					if (this.isPercentInMovingFrom(blocks, getRequiredBlock(), getPercent())){
						if (this.isMaterialInMovingFrom(blocks, Material.FIRE)){
							if (move.equals(MovementMethod.MOVE_DOWN)){
								return true;
							}else{
								if (this.checkFuel(getFuel(), vessel, getFuelTakeAmount())){
									this.takeFuel(getFuel(), vessel, getFuelTakeAmount());
									return true;
								}else{
									if (player != null){
										player.sendMessage(Ships.runShipsMessage("Needs more fuel", true));
									}
									return false;
								}
							}
						}else{
							if (player != null){
								player.sendMessage(Ships.runShipsMessage("Needs burner", true));
							}
							return false;
						}
					}else{
						List<String> materials = new ArrayList<String>();
						for(Material material : getRequiredBlock()){
							materials.add(material.name());
						}
						if (player != null){
							player.sendMessage(Ships.runShipsMessage("Needs more (of either) " + materials, true));
							player.sendMessage(Ships.runShipsMessage("You are off by " + getOffBy(blocks,  getRequiredBlock(), getPercent()), true));
						}
						return false;
					}
				}else{
					if (player != null){
						player.sendMessage(Ships.runShipsMessage("Must be in Air", true));
					}
					return false;
				}
			}else{
				if (player != null){
					player.sendMessage(Ships.runShipsMessage("Your vessel is " + blocks.size() + " and needs to be " + getMinBlocks() + " or more", true));
				}
				return false;
			}
		}else{
			if (player != null){
				player.sendMessage(Ships.runShipsMessage("Your vessel is " + blocks.size() + " and needs to be " + getMaxBlocks() + " or less", true));
			}
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void save(Vessel vessel) {
		File file = new File("plugins/Ships/VesselData/" + vessel.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("ShipsData.Player.Name", vessel.getOwner().getUniqueId().toString());
		config.set("ShipsData.Type", "Airship");
		config.set("ShipsData.Config.Block.Percent", getPercent());
		config.set("ShipsData.Config.Block.Max", getMaxBlocks());
		config.set("ShipsData.Config.Block.Min", getMinBlocks());
		List<String> fuels = new ArrayList<String>();
		for (Entry<Material, Byte> fuels2 : this.getFuel().entrySet()){
			fuels.add(fuels2.getKey().getId() + "," + fuels2.getValue());
		}
		config.set("ShipsData.Config.Fuel.Fuels", fuels);
		config.set("ShipsData.Config.Fuel.Consumption", getFuelTakeAmount());
		config.set("ShipsData.Config.Speed.Engine", getDefaultSpeed());
		Sign sign = vessel.getSign();
		Location loc = vessel.getTeleportLocation();
		config.set("ShipsData.Location.Sign", sign.getLocation().getX() + "," + sign.getLocation().getY() + "," + sign.getLocation().getZ() + "," + sign.getLocation().getWorld().getName());
		config.set("ShipsData.Location.Teleport", loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getWorld().getName());
		try{
			config.save(file);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void createConfig() {
		File file = getFile();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("Speed.Engine", 2);
		config.set("Speed.Boost", 3);
		config.set("Blocks.Max", 3750);
		config.set("Blocks.Min", 1);
		config.set("Blocks.requiredPercent", 60);
		List<Integer> requiredBlocks = new ArrayList<Integer>();
		requiredBlocks.add(35);
		config.set("Blocks.requiredBlocks", requiredBlocks);
		List<String> fuel = new ArrayList<String>();
		fuel.add(Material.COAL.getId() + ",-1");
		config.set("Fuel.Fuels", fuel);
		config.set("Fuel.TakeAmount", 1);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public File getFile() {
		File file = new File("plugins/Ships/Configuration/VesselTypes/Airship.yml");
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
		moveIn.add(Material.AIR);
		this.setMoveInMaterials(moveIn);
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
		if (type instanceof AirShip){
			AirShip airship = (AirShip)type;
			List<Integer> fuels = config.getIntegerList("ShipsData.Config.Fuel.Fuels");
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			int percent = config.getInt("ShipsData.Config.Block.Percent");
			airship.setPercent(percent);
			airship.setFuelTakeAmount(consumption);
			HashMap<Material, Byte> fuelsR = new HashMap<Material, Byte>();
			for (int id : fuels){
				@SuppressWarnings("deprecation")
				Material material = Material.getMaterial(id);
				fuelsR.put(material, (byte)-1);
			}
			airship.setFuel(fuelsR);
			vessel.setVesselType(airship);
		}
		return vessel;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Vessel loadFromNewVesselFile(Vessel vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof AirShip){
			AirShip air = (AirShip)type;
			int percent = config.getInt("ShipsData.Config.Block.Percent");
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			List<String> fuelsL = config.getStringList("ShipsData.Config.Fuel.Fuels");
			air.setPercent(percent);
			air.setFuelTakeAmount(consumption);
			if (fuelsL.size() != 0){
				Map<Material, Byte> fuels = new HashMap<Material, Byte>();
				for (String fuelS : fuelsL){
					String[] fuelM = fuelS.split(",");
					fuels.put(Material.getMaterial(Integer.parseInt(fuelM[0])), Byte.parseByte(fuelM[1]));
				}
				air.setFuel(fuels);
			}
			vessel.setVesselType(air);
		}
		return vessel;
	}
}

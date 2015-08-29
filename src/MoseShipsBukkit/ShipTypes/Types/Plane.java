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
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.MovingShip.MovingBlock;
import MoseShipsBukkit.ShipTypes.VesselType;
import MoseShipsBukkit.ShipTypes.NormalRequirements.RequiredBlock;
import MoseShipsBukkit.ShipTypes.NormalRequirements.RequiredBlockPercent;
import MoseShipsBukkit.ShipTypes.SubType.FuelVesselType;
import MoseShipsBukkit.StillShip.Vessel;
import MoseShipsBukkit.Utils.ConfigLinks.Messages;

public class Plane extends FuelVesselType implements RequiredBlock, RequiredBlockPercent{

	int MAXBLOCKS;
	int MINBLOCKS;
	int PERCENT;
	List<Material> REQUIREDBLOCKS = new ArrayList<Material>();
	
	public Plane() {
		super("Plane", 5, 6, true, true);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void save(Vessel vessel) {
		File file = new File("plugins/Ships/VesselData/" + vessel.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("ShipsData.Player.Name", vessel.getOwner().getUniqueId().toString());
		config.set("ShipsData.Type", "Plane");
		config.set("ShipsData.Protected", vessel.isProtected());
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
		config.set("Speed.Engine", 5);
		config.set("Speed.Boost", 6);
		config.set("Blocks.Max", 3750);
		config.set("Blocks.Min", 1);
		config.set("Blocks.requiredPercent", 50);
		List<Integer> requiredBlocks = new ArrayList<Integer>();
		requiredBlocks.add(42);
		config.set("Blocks.requiredBlocks", requiredBlocks);
		List<String> fuel = new ArrayList<String>();
		fuel.add(Material.COAL_BLOCK.getId() + ",-1");
		config.set("Fuel.Fuels", fuel);
		config.set("Fuel.TakeAmount", 2);
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
		moveIn.add(Material.AIR);
		this.setMoveInMaterials(moveIn);
	}

	@Override
	public File getFile() {
		File file = new File("plugins/Ships/Configuration/VesselTypes/Plane.yml");
		return file;
	}

	@Override
	public Vessel loadFromClassicVesselFile(Vessel vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Plane){
			Plane plane = (Plane)type;
			List<Integer> fuels = config.getIntegerList("ShipsData.Config.Fuel.Fuels");
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			int percent = config.getInt("ShipsData.Config.Block.Percent");
			plane.setPercent(percent);
			plane.setFuelTakeAmount(consumption);
			HashMap<Material, Byte> fuelsR = new HashMap<Material, Byte>();
			for (int id : fuels){
				@SuppressWarnings("deprecation")
				Material material = Material.getMaterial(id);
				fuelsR.put(material, (byte)-1);
			}
			plane.setFuel(fuelsR);
			vessel.setVesselType(plane);
		}
		return vessel;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Vessel loadFromNewVesselFile(Vessel vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Plane){
			Plane plane = (Plane)type;
			int percent = config.getInt("ShipsData.Config.Block.Percent");
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			List<String> fuelsL = config.getStringList("ShipsData.Config.Fuel.Fuels");
			plane.setPercent(percent);
			plane.setFuelTakeAmount(consumption);
			if (fuelsL.size() != 0){
				Map<Material, Byte> fuels = new HashMap<Material, Byte>();
				for (String fuelS : fuelsL){
					String[] fuelM = fuelS.split(",");
					fuels.put(Material.getMaterial(Integer.parseInt(fuelM[0])), Byte.parseByte(fuelM[1]));
				}
				plane.setFuel(fuels);
			}
			vessel.setVesselType(plane);
		}
		return vessel;
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
	public boolean CheckRequirements(Vessel vessel, MovementMethod move, List<MovingBlock> blocks, Player player) {
		if (blocks.size() <= getMaxBlocks()){
			if (blocks.size() >= getMinBlocks()){
				if (isMovingInto(blocks, getMoveInMaterials())){
					if (isPercentInMovingFrom(blocks, getRequiredBlock(), getPercent())){
						//if (isMaterialInMovingFrom(blocks, Material.DROPPER)){
							if (move.equals(MovementMethod.MOVE_DOWN)){
								return true;
							}else{
								if (this.checkFuel(getFuel(), vessel, getFuelTakeAmount())){
									this.takeFuel(getFuel(), vessel, getFuelTakeAmount());
									return true;
								}else{
									if (player != null){
										if (Messages.isEnabled()){
											player.sendMessage(Ships.runShipsMessage(Messages.getOutOfFuel("fuel"), true));
										}
									}
									return false;
								}
							}
						/*}else{
							if (player != null){
								player.sendMessage(Ships.runShipsMessage("Needs engine", true));
							}
							return false;
						}*/
					}else{
						List<String> materials = new ArrayList<String>();
						for(Material material : getRequiredBlock()){
							materials.add(material.name());
						}
						if (player != null){
							if (Messages.isEnabled()){
								player.sendMessage(Ships.runShipsMessage(Messages.getOffBy(getOffBy(blocks,  getRequiredBlock(), getPercent()), materials.toString()), true));
							}
						}
						return false;
					}
				}else{
					if (player != null){
						if (Messages.isEnabled()){
							player.sendMessage(Ships.runShipsMessage(Messages.getMustBeIn("Air"), true));
						}
					}
					return false;
				}
			}else{
				if (player != null){
					if (Messages.isEnabled()){
						player.sendMessage(Ships.runShipsMessage(Messages.getShipTooBig(blocks.size(), getMaxBlocks()), true));
					}
				}
				return false;
			}
		}else{
			if (player != null){
				if (Messages.isEnabled()){
					player.sendMessage(Ships.runShipsMessage(Messages.getShipTooSmall(blocks.size(), getMinBlocks()), true));
				}
			}
			return false;
		}
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
		return REQUIREDBLOCKS;
	}

	@Override
	public void setRequiredBlock(List<Material> material) {
		REQUIREDBLOCKS = material;
	}
}

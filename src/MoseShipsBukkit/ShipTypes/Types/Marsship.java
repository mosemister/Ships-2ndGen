package MoseShipsBukkit.ShipTypes.Types;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import MoseShipsBukkit.StillShip.Vessel;
import MoseShipsBukkit.Utils.ConfigLinks.Messages;

public class Marsship extends VesselType implements RequiredBlock, RequiredBlockPercent{
	
	int MAX;
	int MIN;
	List<Material> REQUIREDBLOCKS;
	int PERCENT;

	public Marsship() {
		super("Marsship", 2, 3, true, true);
	}

	@Override
	public void save(Vessel vessel) {
		File file = new File("plugins/Ships/VesselData/" + vessel.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("ShipsData.Player.Name", vessel.getOwner().getUniqueId().toString());
		config.set("ShipsData.Type", "Marsship");
		config.set("ShipsData.Protected", vessel.isProtected());
		config.set("ShipsData.Config.Block.Percent", getPercent());
		config.set("ShipsData.Config.Block.Max", getMaxBlocks());
		config.set("ShipsData.Config.Block.Min", getMinBlocks());
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

	@Override
	public void createConfig() {
		File file = getFile();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("Speed.Engine", 2);
		config.set("Speed.Boost", 3);
		config.set("Blocks.Max", 3750);
		config.set("Blocks.Min", 1);
		config.set("Blocks.requiredPercent", 10);
		List<Integer> requiredBlocks = new ArrayList<Integer>();
		requiredBlocks.add(151);
		requiredBlocks.add(178);
		config.set("Blocks.requiredBlocks", requiredBlocks);
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
		this.setMaxBlocks(config.getInt("Blocks.Max"));
		this.setMinBlocks(config.getInt("Blocks.Min"));
		this.setPercent(config.getInt("Blocks.requiredPercent"));
		List<Material> requiredmaterials = new ArrayList<Material>();
		for(int id : config.getIntegerList("Blocks.requiredBlocks")){
			Material material = Material.getMaterial(id);
			requiredmaterials.add(material);
		}
		this.setRequiredBlock(requiredmaterials);
		List<Material> moveIn = new ArrayList<Material>();
		moveIn.add(Material.AIR);
		this.setMoveInMaterials(moveIn);
	}

	@Override
	public File getFile() {
		File file = new File("plugins/Ships/Configuration/VesselTypes/Marsship.yml");
		return file;
	}
	
	@Override
	public Vessel loadFromClassicVesselFile(Vessel vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Marsship){
			Marsship mars = (Marsship)type;
			int blockPercent = config.getInt("ShipsData.Config.Block.Percent");
			mars.setPercent(blockPercent);
			vessel.setVesselType(mars);
		}
		return vessel;
	}

	@Override
	public Vessel loadFromNewVesselFile(Vessel vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Marsship){
			Marsship mars = (Marsship)type;
			int percent = config.getInt("ShipsData.Config.Block.Percent");
			mars.setPercent(percent);
			vessel.setVesselType(mars);
		}
		return null;
	}

	@Override
	public int getMaxBlocks() {
		return MAX;
	}

	@Override
	public int getMinBlocks() {
		return MIN;
	}

	@Override
	public void setMaxBlocks(int amount) {
		MAX = amount;
		
	}

	@Override
	public void setMinBlocks(int amount) {
		MIN = amount;
	}

	@Override
	public boolean CheckRequirements(Vessel vessel, MovementMethod move, List<MovingBlock> blocks, Player player) {
		if (blocks.size() >= getMinBlocks()){
			if (blocks.size() <= getMaxBlocks()){
				if (isMovingInto(blocks, getMoveInMaterials())){
					if (isPercentInMovingFrom(blocks, getRequiredBlock(), getPercent())){
						if (move.equals(MovementMethod.MOVE_DOWN)){
							return true;
						}else{
							long time = vessel.getTeleportLocation().getWorld().getTime();
							if ((time >= 0) && (time <= 13000)){
								return true;
							}else{
								if (player != null){
									if (Messages.isEnabled()){
										player.sendMessage(Ships.runShipsMessage(Messages.getNeeds("Light"), true));
									}
								}
								return false;
							}
						}
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

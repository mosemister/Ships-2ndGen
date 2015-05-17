package MoseShipsBukkit.ShipTypes.Types;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
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

public class WaterShip extends VesselType implements RequiredBlock, RequiredBlockPercent{
	
	int PERCENT;
	List<Material> REQUIREDBLOCK = new ArrayList<Material>();;
	List<Material> MOVEINBLOCK = new ArrayList<Material>();
	int MAXBLOCKS;
	int MINBLOCKS;
	
	public WaterShip() {
		super("Ship", 5, 6, false);
		REQUIREDBLOCK.add(Material.WOOL);
		MOVEINBLOCK.add(Material.WATER);
	}

	@Override
	public boolean CheckRequirements(Vessel vessel, MovementMethod move, List<MovingBlock> blocks, Player player) {
		if (this.isMaterialInMovingTo(blocks, getMoveInMaterials())){
			if (this.isPercentInMovingFrom(blocks, getRequiredBlock(), getPercent())){
				return true;
			}else{
				if (player != null){
					if (Messages.isEnabled()){
						player.sendMessage(Ships.runShipsMessage(Messages.getNeeds(getRequiredBlock().get(0).name()), true));
					}
				}
				return false;
			}
		}else{
			if (player != null){
				if (Messages.isEnabled()){
					player.sendMessage(Ships.runShipsMessage(Messages.getMustBeIn("Water"), true));
				}
			}
			return false;
		}
	}

	@Override
	public List<Material> getRequiredBlock() {
		return REQUIREDBLOCK;
	}

	@Override
	public void setRequiredBlock(List<Material> material) {
		MOVEINBLOCK = material;
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
	public void save(Vessel vessel) {
		File file = new File("plugins/Ships/VesselData/" + vessel.getName());
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection config = configuration.createSection("ShipsData");
		config.set("Player.Name", vessel.getOwner().getUniqueId().toString());
		config.set("Type", "Submarine");
		config.set("Protected", vessel.isProtected());
		config.set("Config.Block.Percent", getPercent());
		config.set("Config.Block.Max", getMaxBlocks());
		config.set("Config.Block.Min", getMinBlocks());
		config.set("Config.Speed.Engine", getDefaultSpeed());
		try{
			configuration.save(file);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void createConfig() {
		File file = getFile();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("Speed.Engine", 4);
		config.set("Speed.Boost", 5);
		config.set("Blocks.Max", 4500);
		config.set("Blocks.Min", 1);
		config.set("Blocks.requiredPercent", 65);
		List<Integer> requiredBlocks = new ArrayList<Integer>();
		requiredBlocks.add(35);
		config.set("Blocks.requiredBlocks", requiredBlocks);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public File getFile() {
		File file = new File("plugins/Ships/Configuration/VesselTypes/WaterShip.yml");
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
	public void setMaxBlocks(int Amount) {
		MAXBLOCKS = Amount;
	}

	@Override
	public void setMinBlocks(int Amount) {
		MINBLOCKS = Amount;
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
		List<Material> moveIn = new ArrayList<Material>();
		moveIn.add(Material.WATER);
		moveIn.add(Material.STATIONARY_WATER);
		this.setMoveInMaterials(moveIn);
		
	}

	@Override
	public Vessel loadFromClassicVesselFile(Vessel vessel, File file) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		VesselType type = vessel.getVesselType();
		if (type instanceof WaterShip){
			WaterShip ship = (WaterShip)type;
			int percent = config.getInt("ShipsData.Block.Percent");
			ship.setPercent(percent);
			vessel.setVesselType(ship);
		}
		return vessel;
	}

	@Override
	public Vessel loadFromNewVesselFile(Vessel vessel, File file) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		VesselType type = vessel.getVesselType();
		if (type instanceof WaterShip){
			WaterShip ship = (WaterShip)type;
			int percent = config.getInt("ShipsData.Block.Percent");
			ship.setPercent(percent);
			vessel.setVesselType(ship);
		}
		return vessel;
	}

}

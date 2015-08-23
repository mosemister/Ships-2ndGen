package MoseShipsBukkit.ShipsTypes.Types;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.MovingShip.MovingBlock;
import MoseShipsBukkit.ShipsTypes.VesselType;
import MoseShipsBukkit.ShipsTypes.VesselTypeUtils;
import MoseShipsBukkit.ShipsTypes.HookTypes.ClassicVessel;
import MoseShipsBukkit.ShipsTypes.HookTypes.RequiredMaterial;
import MoseShipsBukkit.StillShip.Vessel;
import MoseShipsBukkit.Utils.ConfigLinks.Messages;

public class Watership extends VesselType implements RequiredMaterial, ClassicVessel{
	
	int PERCENT;
	List<Material> MATERIALS;

	public Watership() {
		super("Ship", new ArrayList<Material>(Arrays.asList(Material.WATER, Material.AIR, Material.STATIONARY_WATER)), 2, 3, false);
		loadDefault();
	}

	@Override
	public boolean checkRequirements(Vessel vessel, MovementMethod move, List<MovingBlock> blocks, Player player) {
		VesselTypeUtils util = new VesselTypeUtils();
		if (util.isMaterialInMovingTo(blocks, getMoveInMaterials())){
			if (util.isPercentInMovingFrom(blocks, getRequiredMaterial(), getRequiredPercent())){
				return true;
			}else{
				if (player != null){
					if (Messages.isEnabled()){
						player.sendMessage(Ships.runShipsMessage(Messages.getNeeds(getRequiredMaterial().get(0).name()), true));
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
	public boolean shouldFall(Vessel vessel) {
		return false;
	}

	@Override
	public File getTypeFile() {
		File file = new File("plugins/Ships/Configuration/VesselTypes/WaterShip.yml");
		return file;
	}

	@Override
	public VesselType clone() {
		Watership ship = new Watership();
		
		return ship;
	}

	@Override
	public void loadVesselFromClassicFile(Vessel vessel, File file) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		VesselType type = vessel.getVesselType();
		if (type instanceof Watership){
			Watership ship = (Watership)type;
			int percent = config.getInt("ShipsData.Block.Percent");
			ship.PERCENT = percent;
			vessel.setVesselType(ship);
		}
	}

	@Override
	public void loadVesselFromFiveFile(Vessel vessel, File file) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		VesselType type = vessel.getVesselType();
		if (type instanceof Watership){
			Watership ship = (Watership)type;
			int percent = config.getInt("ShipsData.Block.Percent");
			ship.PERCENT = percent;
			vessel.setVesselType(ship);
		}
	}

	@Override
	public void createConfig() {
		File file = getTypeFile();
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

	@SuppressWarnings("deprecation")
	@Override
	public void loadDefault() {
		File file = getTypeFile();
		if (!file.exists()){
			createConfig();
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		this.setDefaultSpeed(config.getInt("Speed.Engine"));
		this.setDefaultBoostSpeed(config.getInt("Speed.Boost"));
		this.PERCENT = config.getInt("Blocks.requiredPercent");
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
		moveIn.add(Material.AIR);
		this.setMoveInMaterials(moveIn);
	}

	@Override
	public void save(Vessel vessel) {
		File file = new File("plugins/Ships/VesselData/" + vessel.getName() + ".yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection config = configuration.createSection("ShipsData");
		config.set("Player.Name", vessel.getOwner().getUniqueId().toString());
		config.set("Type", "Ship");
		config.set("Protected", vessel.isProtected());
		config.set("Config.Block.Percent", getRequiredPercent());
		config.set("Config.Block.Max", getMaxBlocks());
		config.set("Config.Block.Min", getMinBlocks());
		config.set("Config.Speed.Engine", getDefaultSpeed());
		Sign sign = vessel.getSign();
		Location loc = vessel.getTeleportLocation();
		config.set("Location.Sign", sign.getLocation().getX() + "," + sign.getLocation().getY() + "," + sign.getLocation().getZ() + "," + sign.getLocation().getWorld().getName());
		config.set("Location.Teleport", loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getWorld().getName());
		try{
			configuration.save(file);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public List<Material> getRequiredMaterial() {
		return MATERIALS;
	}

	@Override
	public int getRequiredPercent() {
		return PERCENT;
	}

}

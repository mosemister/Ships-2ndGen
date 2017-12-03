package MoseShipsBukkit.ShipsTypes.Types;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.Events.ShipsWriteEvent;
import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.MovingShip.MovingBlock;
import MoseShipsBukkit.ShipsTypes.VesselType;
import MoseShipsBukkit.ShipsTypes.VesselTypeUtils;
import MoseShipsBukkit.ShipsTypes.HookTypes.ClassicVessel;
import MoseShipsBukkit.ShipsTypes.HookTypes.Fuel;
import MoseShipsBukkit.ShipsTypes.HookTypes.RequiredMaterial;
import MoseShipsBukkit.StillShip.Vessel.BaseVessel;
import MoseShipsBukkit.StillShip.Vessel.MovableVessel;
import MoseShipsBukkit.StillShip.Vessel.ProtectedVessel;
import MoseShipsBukkit.Utils.ConfigLinks.Messages;

public class Submarine extends VesselType implements Fuel, RequiredMaterial, ClassicVessel {

	int PERCENT;
	int TAKE;
	List<Material> REQUIREDBLOCK;
	Map<Material, Byte> FUEL;

	public Submarine() {
		super("Submarine",
				new ArrayList<Material>(Arrays.asList(Material.WATER, Material.AIR, Material.STATIONARY_WATER)), 2, 3,
				false);
		loadDefault();
	}

	@Override
	public boolean removeFuel(BaseVessel vessel) {
		VesselTypeUtils util = new VesselTypeUtils();
		boolean ret = util.takeFuel(FUEL, vessel, TAKE);
		return ret;
	}

	@Override
	public int getTotalFuel(BaseVessel vessel) {
		VesselTypeUtils util = new VesselTypeUtils();
		int ret = util.getTotalAmountOfFuel(FUEL, vessel);
		return ret;
	}

	@Override
	public Map<Material, Byte> getFuel() {
		return FUEL;
	}

	@Override
	public boolean checkRequirements(MovableVessel vessel, MovementMethod move, List<MovingBlock> blocks,
			Player player) {
		VesselTypeUtils util = new VesselTypeUtils();
		if (util.isMaterialInMovingTo(blocks, getMoveInMaterials())) {
			if (util.isPercentInMovingFrom(blocks, getRequiredMaterial(), getRequiredPercent())) {
				if (move.equals(MovementMethod.MOVE_DOWN)) {
					return true;
				} else {
					if (util.checkFuel(getFuel(), vessel, TAKE)) {
						util.takeFuel(getFuel(), vessel, TAKE);
						return true;
					} else {
						if (player != null) {
							if (Messages.isEnabled()) {
								player.sendMessage(Ships.runShipsMessage(Messages.getOutOfFuel("fuel"), true));
							}
						}
						return false;
					}
				}
			} else {
				if (player != null) {
					List<String> materials = new ArrayList<String>();
					for (Material material : getRequiredMaterial()) {
						materials.add(material.name());
					}
					if (Messages.isEnabled()) {
						player.sendMessage(Ships.runShipsMessage(
								Messages.getOffBy(util.getOffByPercent(blocks, getRequiredMaterial(), getRequiredPercent()),
										"(of either) " + materials.toString()),
								true));
					}
				}
				return false;
			}
		} else {
			if (player != null) {
				if (Messages.isEnabled()) {
					player.sendMessage(Ships.runShipsMessage(Messages.getMustBeIn("Water"), true));
				}
			}
			return false;
		}

	}

	@Override
	public boolean shouldFall(ProtectedVessel vessel) {
		return false;
	}

	@Override
	public File getTypeFile() {
		File file = new File("plugins/Ships/Configuration/VesselTypes/Submarine.yml");
		return file;
	}

	@Override
	public VesselType clone() {
		Submarine sub = new Submarine();
		cloneVesselTypeData(sub);
		sub.FUEL = this.FUEL;
		sub.PERCENT = this.PERCENT;
		sub.REQUIREDBLOCK = this.REQUIREDBLOCK;
		sub.TAKE = this.TAKE;
		return sub;
	}

	@Override
	public void loadVesselFromClassicFile(ProtectedVessel vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Submarine) {
			Submarine submarine = (Submarine) type;
			List<Integer> fuels = config.getIntegerList("ShipsData.Config.Fuel.Fuels");
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			int percent = config.getInt("ShipsData.Config.Block.Percent");
			submarine.PERCENT = percent;
			submarine.TAKE = consumption;
			HashMap<Material, Byte> fuelsR = new HashMap<Material, Byte>();
			for (int id : fuels) {
				@SuppressWarnings("deprecation")
				Material material = Material.getMaterial(id);
				fuelsR.put(material, (byte) -1);
			}
			submarine.FUEL = fuelsR;
			vessel.setVesselType(submarine);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void loadVesselFromFiveFile(ProtectedVessel vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Submarine) {
			Submarine sub = (Submarine) type;
			int percent = config.getInt("ShipsData.Config.Block.Percent");
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			List<String> fuelsL = config.getStringList("ShipsData.Config.Fuel.Fuels");
			sub.PERCENT = percent;
			sub.TAKE = consumption;
			if (fuelsL.size() != 0) {
				Map<Material, Byte> fuels = new HashMap<Material, Byte>();
				for (String fuelS : fuelsL) {
					String[] fuelM = fuelS.split(",");
					fuels.put(Material.getMaterial(Integer.parseInt(fuelM[0])), Byte.parseByte(fuelM[1]));
				}
				sub.FUEL = fuels;
			}
			vessel.setVesselType(sub);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void createConfig() {
		File file = getTypeFile();
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
		File file = getTypeFile();
		if (!file.exists()) {
			createConfig();
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		this.setDefaultSpeed(config.getInt("Speed.Engine"));
		this.setDefaultBoostSpeed(config.getInt("Speed.Boost"));
		this.PERCENT = config.getInt("Blocks.requiredPercent");
		this.setMaxBlocks(config.getInt("Blocks.Max"));
		this.setMinBlocks(config.getInt("Blocks.Min"));
		List<Material> requiredmaterials = new ArrayList<Material>();
		for (int id : config.getIntegerList("Blocks.requiredBlocks")) {
			Material material = Material.getMaterial(id);
			requiredmaterials.add(material);
		}
		int take = config.getInt("Fuel.TakeAmount");
		String[] fuel = config.getString("Fuel.Fuels").split(",");
		Material material = Material.getMaterial(Integer.parseInt(fuel[0].replace("[", "")));
		byte data = Byte.parseByte(fuel[1].replace("]", ""));
		Map<Material, Byte> fuels = new HashMap<Material, Byte>();
		fuels.put(material, data);
		this.FUEL = fuels;
		this.REQUIREDBLOCK = requiredmaterials;
		this.TAKE = take;
		List<Material> moveIn = new ArrayList<Material>();
		moveIn.add(Material.WATER);
		moveIn.add(Material.STATIONARY_WATER);
		this.setMoveInMaterials(moveIn);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void save(ProtectedVessel vessel) {
		File file = new File("plugins/Ships/VesselData/" + vessel.getName() + ".yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection config = configuration.createSection("ShipsData");
		List<String> fuels = new ArrayList<String>();
		for (Entry<Material, Byte> fuels2 : this.getFuel().entrySet()) {
			fuels.add(fuels2.getKey().getId() + "," + fuels2.getValue());
		}
		ShipsWriteEvent event = new ShipsWriteEvent(file, "Submarine", getRequiredPercent(), PERCENT, getMaxBlocks(),
				getMinBlocks(), getDefaultSpeed(), fuels, TAKE);
		if (!event.isCancelled()) {
			config.set("Player.Name", vessel.getOwner().getUniqueId().toString());
			config.set("Type", "Submarine");
			config.set("Config.Block.Percent", PERCENT);
			config.set("Config.Block.Max", getMaxBlocks());
			config.set("Config.Block.Min", getMinBlocks());
			config.set("Config.Fuel.Fuels", fuels);
			config.set("Config.Fuel.Consumption", TAKE);
			config.set("Config.Speed.Engine", getDefaultSpeed());
			Sign sign = vessel.getSign();
			Location loc = vessel.getTeleportLocation();
			config.set("Location.Sign", sign.getLocation().getX() + "," + sign.getLocation().getY() + ","
					+ sign.getLocation().getZ() + "," + sign.getLocation().getWorld().getName());
			config.set("Location.Teleport",
					loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getWorld().getName());
			try {
				configuration.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Material> getRequiredMaterial() {
		return REQUIREDBLOCK;
	}

	@Override
	public int getRequiredPercent() {
		return PERCENT;
	}

}

package MoseShipsBukkit.ShipsTypes.Types;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.ships.configuration.Messages;
import org.ships.event.custom.ShipsWriteEvent;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.MovingShip.MovingBlock;
import MoseShipsBukkit.ShipsTypes.AbstractShipType;
import MoseShipsBukkit.ShipsTypes.VesselType;
import MoseShipsBukkit.ShipsTypes.VesselTypeUtils;
import MoseShipsBukkit.ShipsTypes.HookTypes.ClassicVessel;
import MoseShipsBukkit.ShipsTypes.HookTypes.Fuel;
import MoseShipsBukkit.ShipsTypes.HookTypes.RequiredMaterial;
import MoseShipsBukkit.StillShip.Vessel.Ship;

public class Submarine extends AbstractShipType implements Fuel, RequiredMaterial, ClassicVessel {

	protected float percent;
	int takeAmount;
	List<Material> requiredMaterials = new ArrayList<>();
	List<Material> fuelTypes = new ArrayList<>();

	public Submarine() {
		super(new File(""), "Submarine", 200, 1000, 2, 3, false, Material.WATER, Material.AIR, Material.FLOWING_WATER);
		loadDefault();
	}

	@Override
	public boolean removeFuel(Ship vessel) {
		VesselTypeUtils util = new VesselTypeUtils();
		boolean ret = util.takeFuel(vessel, getFuelConsumption(), getFuelTypes());
		return ret;
	}

	@Override
	public int getTotalFuel(Ship vessel) {
		VesselTypeUtils util = new VesselTypeUtils();
		int ret = util.getTotalAmountOfFuel(vessel, getFuelTypes());
		return ret;
	}

	@Override
	public Set<Material> getFuelTypes() {
		return new HashSet<>(fuelTypes);
	}

	@Override
	public boolean checkRequirements(Ship vessel, MovementMethod move, Collection<MovingBlock> blocks,
			Player player) {
		VesselTypeUtils util = new VesselTypeUtils();
		if (util.isMaterialInMovingTo(blocks, getMoveInMaterials())) {
			if (util.isPercentInMovingFrom(blocks, getRequiredMaterials(), getRequiredPercent())) {
				if (move.equals(MovementMethod.MOVE_DOWN)) {
					return true;
				} else {
					if (util.checkFuel(vessel, getFuelConsumption(), getFuelTypes())) {
						util.takeFuel(vessel, getFuelConsumption(), getFuelTypes());
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
					for (Material material : getRequiredMaterials()) {
						materials.add(material.name());
					}
					if (Messages.isEnabled()) {
						player.sendMessage(Ships.runShipsMessage(
								Messages.getOffBy(util.getOffByPercent(blocks, getRequiredMaterials(), getRequiredPercent()),
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
	public boolean shouldFall(Ship vessel) {
		return false;
	}

	@Override
	public File getTypeFile() {
		File file = new File("plugins/Ships/Configuration/VesselTypes/Submarine.yml");
		return file;
	}

	@Override
	public VesselType createClone() {
		Submarine sub = new Submarine();
		sub.fuelTypes = this.fuelTypes;
		sub.percent = this.percent;
		sub.requiredMaterials = this.requiredMaterials;
		sub.takeAmount = this.takeAmount;
		return sub;
	}

	@Override
	public void loadVesselFromClassicFile(Ship vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Submarine) {
			Submarine sub = (Submarine) type;
			int percent = config.getInt("ShipsData.Config.Block.Percent");
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			//List<String> fuelsL = config.getStringList("ShipsData.Config.Fuel.Fuels");
			sub.setRequiredPercent(percent);
			sub.setFuelConsumption(consumption);
			/*if (fuelsL.size() != 0) {
				Map<Material, Byte> fuels = new HashMap<Material, Byte>();
				for (String fuelS : fuelsL) {
					String[] fuelM = fuelS.split(",");
					fuels.put(Material.getMaterial(Integer.parseInt(fuelM[0])), Byte.parseByte(fuelM[1]));
				}
				sub.FUEL = fuels;
			}*/
			vessel.setVesselType(sub);
		}
	}

	@Override
	public void loadVesselFromFiveFile(Ship vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Submarine) {
			Submarine sub = (Submarine) type;
			int percent = config.getInt("ShipsData.Config.Block.Percent");
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			List<String> fuelsL = config.getStringList("ShipsData.Config.Fuel.Fuels");
			sub.setRequiredPercent(percent);
			sub.setFuelConsumption(consumption);
			if (fuelsL.size() != 0) {
				for (String fuelS : fuelsL) {
					Material material = Material.getMaterial(fuelS);
					this.fuelTypes.add(material);
				}
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

	@Override
	public void loadDefault() {
		File file = getTypeFile();
		if (!file.exists()) {
			createConfig();
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		this.setDefaultSpeed(config.getInt("Speed.Engine"));
		this.setDefaultBoostSpeed(config.getInt("Speed.Boost"));
		this.setRequiredPercent((float)config.getDouble("Blocks.requiredPercent"));
		this.setMaxBlocks(config.getInt("Blocks.Max"));
		this.setMinBlocks(config.getInt("Blocks.Min"));
		List<Material> requiredmaterials = new ArrayList<Material>();
		for (String id : config.getStringList("Blocks.requiredBlocks")) {
			Material material = Material.getMaterial(id);
			requiredmaterials.add(material);
		}
		int take = config.getInt("Fuel.TakeAmount");
		List<String> fuel = config.getStringList("Fuel.Fuels");
		List<Material> fuels = new ArrayList<>();
		fuel.stream().forEach(f -> fuels.add(Material.getMaterial(f)));
		this.setFuelTypes(fuels);
		this.setRequiredMaterials(requiredmaterials);
		this.setFuelConsumption(take);
		List<Material> moveIn = new ArrayList<Material>();
		moveIn.add(Material.WATER);
		moveIn.add(Material.FLOWING_WATER);
		this.setMoveInMaterials(moveIn);
	}

	@Override
	public void save(Ship vessel) {
		File file = new File("plugins/Ships/VesselData/" + vessel.getName() + ".yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection config = configuration.createSection("ShipsData");
		List<String> fuels = new ArrayList<String>();
		for (Material fuels2 : this.getFuelTypes()) {
			fuels.add(fuels2.name());
		}
		ShipsWriteEvent event = new ShipsWriteEvent(file, "Submarine", getRequiredPercent(), getRequiredPercent(), getMaxBlocks(),
				getMinBlocks(), getDefaultSpeed(), fuels, getFuelConsumption());
		if (!event.isCancelled()) {
			config.set("Player.Name", vessel.getOwner().getUniqueId().toString());
			config.set("Type", "Submarine");
			config.set("Config.Block.Percent", getRequiredPercent());
			config.set("Config.Block.Max", getMaxBlocks());
			config.set("Config.Block.Min", getMinBlocks());
			config.set("Config.Fuel.Fuels", fuels);
			config.set("Config.Fuel.Consumption", getFuelConsumption());
			config.set("Config.Speed.Engine", getDefaultSpeed());
			Block block = vessel.getLocation().getBlock();
			Location loc = vessel.getTeleportLocation();
			config.set("Location.Sign", block.getX() + "," + block.getY() + ","
					+ block.getZ() + "," + block.getWorld().getName());
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
	public Set<Material> getRequiredMaterials() {
		return new HashSet<>(requiredMaterials);
	}

	@Override
	public float getRequiredPercent() {
		return percent;
	}

	@Override
	public void setRequiredMaterials(Collection<Material> collection) {
		requiredMaterials.clear();
		requiredMaterials.addAll(collection);
	}

	@Override
	public void setRequiredPercent(float percent) {
		this.percent = percent;
	}

	@Override
	public int getFuelConsumption() {
		return this.takeAmount;
	}

	@Override
	public void setFuelTypes(Collection<Material> fuels) {
		this.fuelTypes.clear();
		this.fuelTypes.addAll(fuels);
	}

	@Override
	public void setFuelConsumption(int consumption) {
		this.takeAmount = consumption;
	}

}

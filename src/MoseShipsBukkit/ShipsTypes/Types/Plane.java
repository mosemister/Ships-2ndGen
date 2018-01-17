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
import MoseShipsBukkit.StillShip.Vessel.BaseVessel;
import MoseShipsBukkit.StillShip.Vessel.MovableVessel;
import MoseShipsBukkit.StillShip.Vessel.ProtectedVessel;

public class Plane extends AbstractShipType implements Fuel, RequiredMaterial, ClassicVessel {

	protected int takeAmount;
	protected float percent;
	protected List<Material> fuelTypes = new ArrayList<>();
	protected List<Material> requiredMaterials = new ArrayList<Material>();

	public Plane() {
		super(new File("plugins/Ships/Configuration/VesselTypes/Plane.yml"), "Plane", 4, 5, 100, 2500, true, Material.AIR);
		loadDefault();
	}
	
	@Override
	public void setRequiredMaterials(Collection<Material> collection) {
		this.requiredMaterials.clear();
		this.requiredMaterials.addAll(collection);
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

	@Override
	public Set<Material> getRequiredMaterials() {
		return new HashSet<>(requiredMaterials);
	}

	@Override
	public float getRequiredPercent() {
		return percent;
	}

	@Override
	public boolean removeFuel(BaseVessel vessel) {
		VesselTypeUtils util = new VesselTypeUtils();
		boolean ret = util.takeFuel(vessel, takeAmount, fuelTypes);
		return ret;
	}

	@Override
	public int getTotalFuel(BaseVessel vessel) {
		VesselTypeUtils util = new VesselTypeUtils();
		int ret = util.getTotalAmountOfFuel(vessel, fuelTypes);
		return ret;
	}

	@Override
	public Set<Material> getFuelTypes() {
		return new HashSet<>(fuelTypes);
	}

	@Override
	public boolean checkRequirements(MovableVessel vessel, MovementMethod move, Collection<MovingBlock> blocks,
			Player player) {
		VesselTypeUtils util = new VesselTypeUtils();
		if (blocks.size() <= getMaxBlocks()) {
			if (blocks.size() >= getMinBlocks()) {
				if (util.isMovingInto(blocks, getMoveInMaterials())) {
					if (util.isPercentInMovingFrom(blocks, requiredMaterials, percent)) {
						// if (isMaterialInMovingFrom(blocks,
						// Material.DROPPER)){
						if (move.equals(MovementMethod.MOVE_DOWN)) {
							return true;
						} else {
							if (util.checkFuel(vessel, takeAmount, getFuelTypes())) {
								util.takeFuel(vessel, takeAmount, getFuelTypes());
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
						/*
						 * }else{ if (player != null){
						 * player.sendMessage(Ships.runShipsMessage(
						 * "Needs engine", true)); } return false; }
						 */
					} else {
						List<String> materials = new ArrayList<String>();
						for (Material material : getRequiredMaterials()) {
							materials.add(material.name());
						}
						if (player != null) {
							if (Messages.isEnabled()) {
								player.sendMessage(Ships.runShipsMessage(Messages.getOffBy(
										util.getOffByPercent(blocks, getRequiredMaterials(), getRequiredPercent()), materials.toString()), true));
							}
						}
						return false;
					}
				} else {
					if (player != null) {
						if (Messages.isEnabled()) {
							player.sendMessage(Ships.runShipsMessage(Messages.getMustBeIn("Air"), true));
						}
					}
					return false;
				}
			} else {
				if (player != null) {
					if (Messages.isEnabled()) {
						player.sendMessage(
								Ships.runShipsMessage(Messages.getShipTooBig(blocks.size(), getMaxBlocks()), true));
					}
				}
				return false;
			}
		} else {
			if (player != null) {
				if (Messages.isEnabled()) {
					player.sendMessage(
							Ships.runShipsMessage(Messages.getShipTooSmall(blocks.size(), getMinBlocks()), true));
				}
			}
			return false;
		}
	}

	@Override
	public void loadVesselFromClassicFile(ProtectedVessel vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Plane) {
			Plane plane = (Plane) type;
			int percent = config.getInt("ShipsData.Config.Block.Percent");
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			//List<String> fuelsL = config.getStringList("ShipsData.Config.Fuel.Fuels");
			plane.setRequiredPercent(percent);
			plane.setFuelConsumption(consumption);
			/*if (fuelsL.size() != 0) {
				Map<Material, Byte> fuels = new HashMap<Material, Byte>();
				for (String fuelS : fuelsL) {
					String[] fuelM = fuelS.split(",");
					fuels.put(Material.getMaterial(Integer.parseInt(fuelM[0])), Byte.parseByte(fuelM[1]));
				}
				plane.FUELS = fuels;
			}*/
			vessel.setVesselType(plane);
		}
	}

	@Override
	public void loadVesselFromFiveFile(ProtectedVessel vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Plane) {
			Plane plane = (Plane) type;
			int percent = config.getInt("ShipsData.Config.Block.Percent");
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			List<String> fuelsL = config.getStringList("ShipsData.Config.Fuel.Fuels");
			plane.setRequiredPercent(percent);
			plane.setFuelConsumption(consumption);
			if (fuelsL.size() != 0) {
				for (String fuelS : fuelsL) {
					Material material = Material.getMaterial(fuelS);
					plane.fuelTypes.add(material);
				}
			}
			vessel.setVesselType(plane);
		}
	}

	@Override
	public void createConfig() {
		File file = getTypeFile();
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
		fuel.add(Material.COAL_BLOCK.name());
		config.set("Fuel.Fuels", fuel);
		config.set("Fuel.TakeAmount", 2);
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
		this.setRequiredPercent(config.getInt("Blocks.requiredPercent"));
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
		moveIn.add(Material.AIR);
		this.setMoveInMaterials(moveIn);
	}

	@Override
	public void save(ProtectedVessel vessel) {
		File file = new File("plugins/Ships/VesselData/" + vessel.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		List<String> fuels = new ArrayList<String>();
		for (Material fuels2 : this.getFuelTypes()) {
			fuels.add(fuels2.name());
		}
		ShipsWriteEvent event = new ShipsWriteEvent(file, "Plane", getRequiredPercent(), getRequiredPercent(), getMaxBlocks(),
				getMinBlocks(), getDefaultSpeed(), fuels, getFuelConsumption());
		if (!event.isCancelled()) {
			config.set("ShipsData.Player.Name", vessel.getOwner().getUniqueId().toString());
			config.set("ShipsData.Type", "Plane");
			config.set("ShipsData.Config.Block.Percent", getRequiredPercent());
			config.set("ShipsData.Config.Block.Max", getMaxBlocks());
			config.set("ShipsData.Config.Block.Min", getMinBlocks());
			config.set("ShipsData.Config.Fuel.Fuels", fuels);
			config.set("ShipsData.Config.Fuel.Consumption", getFuelConsumption());
			config.set("ShipsData.Config.Speed.Engine", getDefaultSpeed());
			Block block = vessel.getLocation().getBlock();
			Location loc = vessel.getTeleportLocation();
			config.set("ShipsData.Location.Sign", block.getX() + "," + block.getY() + ","
					+ block.getZ() + "," + block.getWorld().getName());
			config.set("ShipsData.Location.Teleport",
					loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getWorld().getName());
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public VesselType createClone() {
		Plane plane = new Plane();
		plane.takeAmount = takeAmount;
		plane.percent = percent;
		plane.fuelTypes = fuelTypes;
		plane.requiredMaterials = requiredMaterials;
		return plane;
	}
	
}

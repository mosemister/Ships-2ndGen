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

public class Plane extends VesselType implements Fuel, RequiredMaterial, ClassicVessel {

	int TAKE;
	int PERCENT;
	Map<Material, Byte> FUELS = new HashMap<Material, Byte>();
	List<Material> REQUIREDBLOCKS = new ArrayList<Material>();

	public Plane() {
		super("Plane", new ArrayList<Material>(Arrays.asList(Material.AIR)), 4, 5, true);
		loadDefault();
	}

	@Override
	public List<Material> getRequiredMaterial() {
		return REQUIREDBLOCKS;
	}

	@Override
	public int getRequiredPercent() {
		return PERCENT;
	}

	@Override
	public boolean removeFuel(BaseVessel vessel) {
		VesselTypeUtils util = new VesselTypeUtils();
		boolean ret = util.takeFuel(FUELS, vessel, TAKE);
		return ret;
	}

	@Override
	public int getTotalFuel(BaseVessel vessel) {
		VesselTypeUtils util = new VesselTypeUtils();
		int ret = util.getTotalAmountOfFuel(FUELS, vessel);
		return ret;
	}

	@Override
	public Map<Material, Byte> getFuel() {
		return FUELS;
	}

	@Override
	public boolean checkRequirements(MovableVessel vessel, MovementMethod move, List<MovingBlock> blocks,
			Player player) {
		VesselTypeUtils util = new VesselTypeUtils();
		if (blocks.size() <= getMaxBlocks()) {
			if (blocks.size() >= getMinBlocks()) {
				if (util.isMovingInto(blocks, getMoveInMaterials())) {
					if (util.isPercentInMovingFrom(blocks, REQUIREDBLOCKS, PERCENT)) {
						// if (isMaterialInMovingFrom(blocks,
						// Material.DROPPER)){
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
						/*
						 * }else{ if (player != null){
						 * player.sendMessage(Ships.runShipsMessage(
						 * "Needs engine", true)); } return false; }
						 */
					} else {
						List<String> materials = new ArrayList<String>();
						for (Material material : REQUIREDBLOCKS) {
							materials.add(material.name());
						}
						if (player != null) {
							if (Messages.isEnabled()) {
								player.sendMessage(Ships.runShipsMessage(Messages.getOffBy(
										util.getOffByPercent(blocks, REQUIREDBLOCKS, PERCENT), materials.toString()), true));
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
	public boolean shouldFall(ProtectedVessel vessel) {
		int fuel = getTotalFuel(vessel);
		if (fuel == 0) {
			return true;
		}
		return false;
	}

	@Override
	public File getTypeFile() {
		File file = new File("plugins/Ships/Configuration/VesselTypes/Plane.yml");
		return file;
	}

	@Override
	public VesselType clone() {
		Plane plane = new Plane();
		cloneVesselTypeData(plane);
		plane.TAKE = TAKE;
		plane.PERCENT = PERCENT;
		plane.FUELS = FUELS;
		plane.REQUIREDBLOCKS = REQUIREDBLOCKS;
		return plane;
	}

	@Override
	public void loadVesselFromClassicFile(ProtectedVessel vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Plane) {
			Plane plane = (Plane) type;
			List<Integer> fuels = config.getIntegerList("ShipsData.Config.Fuel.Fuels");
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			int percent = config.getInt("ShipsData.Config.Block.Percent");
			plane.PERCENT = percent;
			plane.TAKE = consumption;
			HashMap<Material, Byte> fuelsR = new HashMap<Material, Byte>();
			for (int id : fuels) {
				@SuppressWarnings("deprecation")
				Material material = Material.getMaterial(id);
				fuelsR.put(material, (byte) -1);
			}
			plane.FUELS = fuelsR;
			vessel.setVesselType(plane);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void loadVesselFromFiveFile(ProtectedVessel vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Plane) {
			Plane plane = (Plane) type;
			int percent = config.getInt("ShipsData.Config.Block.Percent");
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			List<String> fuelsL = config.getStringList("ShipsData.Config.Fuel.Fuels");
			plane.PERCENT = percent;
			plane.TAKE = consumption;
			if (fuelsL.size() != 0) {
				Map<Material, Byte> fuels = new HashMap<Material, Byte>();
				for (String fuelS : fuelsL) {
					String[] fuelM = fuelS.split(",");
					fuels.put(Material.getMaterial(Integer.parseInt(fuelM[0])), Byte.parseByte(fuelM[1]));
				}
				plane.FUELS = fuels;
			}
			vessel.setVesselType(plane);
		}
	}

	@SuppressWarnings("deprecation")
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
		this.FUELS = fuels;
		this.REQUIREDBLOCKS = requiredmaterials;
		this.TAKE = take;
		List<Material> moveIn = new ArrayList<Material>();
		moveIn.add(Material.AIR);
		this.setMoveInMaterials(moveIn);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void save(ProtectedVessel vessel) {
		File file = new File("plugins/Ships/VesselData/" + vessel.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		List<String> fuels = new ArrayList<String>();
		for (Entry<Material, Byte> fuels2 : this.getFuel().entrySet()) {
			fuels.add(fuels2.getKey().getId() + "," + fuels2.getValue());
		}
		ShipsWriteEvent event = new ShipsWriteEvent(file, "Plane", getRequiredPercent(), PERCENT, getMaxBlocks(),
				getMinBlocks(), getDefaultSpeed(), fuels, TAKE);
		if (!event.isCancelled()) {
			config.set("ShipsData.Player.Name", vessel.getOwner().getUniqueId().toString());
			config.set("ShipsData.Type", "Plane");
			config.set("ShipsData.Config.Block.Percent", getRequiredPercent());
			config.set("ShipsData.Config.Block.Max", getMaxBlocks());
			config.set("ShipsData.Config.Block.Min", getMinBlocks());
			config.set("ShipsData.Config.Fuel.Fuels", fuels);
			config.set("ShipsData.Config.Fuel.Consumption", TAKE);
			config.set("ShipsData.Config.Speed.Engine", getDefaultSpeed());
			Sign sign = vessel.getSign();
			Location loc = vessel.getTeleportLocation();
			config.set("ShipsData.Location.Sign", sign.getLocation().getX() + "," + sign.getLocation().getY() + ","
					+ sign.getLocation().getZ() + "," + sign.getLocation().getWorld().getName());
			config.set("ShipsData.Location.Teleport",
					loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getWorld().getName());
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

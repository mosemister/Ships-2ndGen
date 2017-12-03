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
import MoseShipsBukkit.MovingShip.MovingStructure;
import MoseShipsBukkit.ShipsTypes.VesselType;
import MoseShipsBukkit.ShipsTypes.VesselTypeUtils;
import MoseShipsBukkit.ShipsTypes.HookTypes.ClassicVessel;
import MoseShipsBukkit.ShipsTypes.HookTypes.Fuel;
import MoseShipsBukkit.ShipsTypes.HookTypes.RequiredMaterial;
import MoseShipsBukkit.StillShip.Vessel.BaseVessel;
import MoseShipsBukkit.StillShip.Vessel.MovableVessel;
import MoseShipsBukkit.StillShip.Vessel.ProtectedVessel;
import MoseShipsBukkit.Utils.ConfigLinks.Messages;

public class Airship extends VesselType implements Fuel, RequiredMaterial, ClassicVessel {

	int PERCENT;
	int TAKE;
	List<Material> REQUIREDBLOCK;
	Map<Material, Byte> FUEL;

	public Airship() {
		super("Airship", new ArrayList<Material>(Arrays.asList(Material.AIR)), 2, 3, true);
		loadDefault();
	}

	public int getPercent() {
		return PERCENT;
	}

	public List<Material> getRequiredBlock() {
		return REQUIREDBLOCK;
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
		if (util.isMovingInto(blocks, getMoveInMaterials())) {
			if (util.isPercentInMovingFrom(blocks, getRequiredBlock(), getPercent())) {
				if (util.isMaterialInMovingFrom(blocks, Material.FIRE)) {
					if (move.equals(MovementMethod.MOVE_DOWN)) {
						return true;
					} else {
						if (getTotalFuel(vessel) >= TAKE) {
							removeFuel(vessel);
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
						if (Messages.isEnabled()) {
							player.sendMessage(Ships.runShipsMessage(Messages.getNeeds("Burner"), true));
						}
					}
					return false;
				}
			} else {
				List<String> materials = new ArrayList<String>();
				for (Material material : getRequiredBlock()) {
					materials.add(material.name());
				}
				if (player != null) {
					if (Messages.isEnabled()) {
						player.sendMessage(Ships.runShipsMessage(
								Messages.getOffBy(util.getOffByPercent(blocks, getRequiredBlock(), getPercent()),
										"(of either) " + materials.toString()),
								true));
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
	}

	@Override
	public boolean shouldFall(ProtectedVessel vessel) {
		MovingStructure stru = (MovingStructure) vessel.getStructure();
		if (checkRequirements((MovableVessel) vessel, MovementMethod.MOVE_DOWN, stru.getAllMovingBlocks(), null)) {
			return true;
		}
		return false;
	}

	@Override
	public File getTypeFile() {
		File file = new File("plugins/Ships/Configuration/VesselTypes/Airship.yml");
		return file;
	}

	@Override
	public void loadVesselFromClassicFile(ProtectedVessel vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Airship) {
			Airship airship = (Airship) type;
			List<Integer> fuels = config.getIntegerList("ShipsData.Config.Fuel.Fuels");
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			int percent = config.getInt("ShipsData.Config.Block.Percent");
			airship.PERCENT = percent;
			airship.TAKE = consumption;
			Map<Material, Byte> fuelsR = new HashMap<Material, Byte>();
			for (int id : fuels) {
				@SuppressWarnings("deprecation")
				Material material = Material.getMaterial(id);
				fuelsR.put(material, (byte) -1);
			}
			airship.FUEL = fuelsR;
			vessel.setVesselType(airship);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void loadVesselFromFiveFile(ProtectedVessel vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Airship) {
			Airship air = (Airship) type;
			int percent = config.getInt("ShipsData.Config.Block.Percent");
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			List<String> fuelsL = config.getStringList("ShipsData.Config.Fuel.Fuels");
			air.setMaxBlocks(config.getInt("ShipsData.Config.Block.Max"));
			air.setMinBlocks(config.getInt("ShipsData.Config.Block.Min"));
			air.PERCENT = percent;
			air.TAKE = consumption;
			if (fuelsL.size() != 0) {
				Map<Material, Byte> fuels = new HashMap<Material, Byte>();
				for (String fuelS : fuelsL) {
					String[] fuelM = fuelS.split(",");
					fuels.put(Material.getMaterial(Integer.parseInt(fuelM[0])), Byte.parseByte(fuelM[1]));
				}
				air.FUEL = fuels;
			}
			vessel.setVesselType(air);
		}
	}

	@Override
	public VesselType clone() {
		Airship air = new Airship();
		cloneVesselTypeData(air);
		air.FUEL = this.FUEL;
		air.PERCENT = this.PERCENT;
		air.REQUIREDBLOCK = this.REQUIREDBLOCK;
		air.TAKE = this.TAKE;
		return air;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void createConfig() {
		File file = getTypeFile();
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
		moveIn.add(Material.AIR);
		this.setMoveInMaterials(moveIn);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void save(ProtectedVessel vessel) {
		File file = new File("plugins/Ships/VesselData/" + vessel.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		List<String> fuels = new ArrayList<String>();
		for (Entry<Material, Byte> fuels2 : this.FUEL.entrySet()) {
			fuels.add(fuels2.getKey().getId() + "," + fuels2.getValue());
		}
		ShipsWriteEvent event = new ShipsWriteEvent(file, "Airship", PERCENT, getMaxBlocks(), getMinBlocks(),
				getDefaultSpeed(), fuels, TAKE);
		if (!event.isCancelled()) {
			config.set("ShipsData.Player.Name", vessel.getOwner().getUniqueId().toString());
			config.set("ShipsData.Type", "Airship");
			config.set("ShipsData.Config.Block.Percent", getPercent());
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

	@Override
	public List<Material> getRequiredMaterial() {
		return REQUIREDBLOCK;
	}

	@Override
	public int getRequiredPercent() {
		return PERCENT;
	}
}

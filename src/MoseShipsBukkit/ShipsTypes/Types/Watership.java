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
import MoseShipsBukkit.ShipsTypes.HookTypes.RequiredMaterial;
import MoseShipsBukkit.StillShip.Vessel.MovableVessel;
import MoseShipsBukkit.StillShip.Vessel.ProtectedVessel;

public class Watership extends AbstractShipType implements RequiredMaterial, ClassicVessel {

	protected float percent;
	protected List<Material> requiredMaterials;

	public Watership() {
		super(new File("plugins/Ships/Configuration/VesselTypes/WaterShip.yml"), "Ship", 10, 2600, 2, 3, false, Material.AIR, Material.WATER, Material.FLOWING_WATER);
		loadDefault();
	}

	@Override
	public boolean checkRequirements(MovableVessel vessel, MovementMethod move, Collection<MovingBlock> blocks,
			Player player) {
		int waterLevel = vessel.getWaterLevel(blocks);
		if(waterLevel == 0) {
			if(!move.equals(MovementMethod.MOVE_DOWN)) {
				if(player != null) {
					if(Messages.isEnabled()) {
						player.sendMessage(Ships.runShipsMessage("Ship can only move down into water.", true));
					}
				}
				return false;
			}
		}
		if (((move.equals(MovementMethod.MOVE_DOWN) && (waterLevel != 0)) || (move.equals(MovementMethod.MOVE_UP)))) {
			return false;
		}
		VesselTypeUtils util = new VesselTypeUtils();
		if (util.isMaterialInMovingTo(blocks, getMoveInMaterials())) {
			if (util.isPercentInMovingFrom(blocks, getRequiredMaterials(), getRequiredPercent())) {
				return true;
			} else {
				if (player != null) {
					if (Messages.isEnabled()) {
						player.sendMessage(
								Ships.runShipsMessage(Messages.getNeeds(requiredMaterials.get(0).name()), true));
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
	public boolean shouldFall(MovableVessel vessel) {
		return false;
	}

	@Override
	public VesselType createClone() {
		Watership ship = new Watership();
		ship.percent = this.percent;
		ship.requiredMaterials = this.requiredMaterials;
		return ship;
	}

	@Override
	public void loadVesselFromClassicFile(ProtectedVessel vessel, File file) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		VesselType type = vessel.getVesselType();
		if (type instanceof Watership) {
			Watership ship = (Watership) type;
			int percent = config.getInt("ShipsData.Block.Percent");
			ship.percent = percent;
			vessel.setVesselType(ship);
		}
	}

	@Override
	public void loadVesselFromFiveFile(ProtectedVessel vessel, File file) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		VesselType type = vessel.getVesselType();
		if (type instanceof Watership) {
			Watership ship = (Watership) type;
			int percent = config.getInt("ShipsData.Block.Percent");
			ship.percent = percent;
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

	@Override
	public void loadDefault() {
		File file = getTypeFile();
		if (!file.exists()) {
			createConfig();
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		this.setDefaultSpeed(config.getInt("Speed.Engine"));
		this.setDefaultBoostSpeed(config.getInt("Speed.Boost"));
		this.percent = config.getInt("Blocks.requiredPercent");
		this.setMaxBlocks(config.getInt("Blocks.Max"));
		this.setMinBlocks(config.getInt("Blocks.Min"));
		List<Material> requiredmaterials = new ArrayList<Material>();
		for (String id : config.getStringList("Blocks.requiredBlocks")) {
			Material material = Material.getMaterial(id);
			requiredmaterials.add(material);
		}
		List<Material> moveIn = new ArrayList<Material>();
		moveIn.add(Material.WATER);
		moveIn.add(Material.FLOWING_WATER);
		moveIn.add(Material.AIR);
		this.setMoveInMaterials(moveIn);
	}

	@Override
	public void save(ProtectedVessel vessel) {
		File file = new File("plugins/Ships/VesselData/" + vessel.getName() + ".yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		ShipsWriteEvent event = new ShipsWriteEvent(file, "Ship", percent, getMaxBlocks(), getMinBlocks(),
				getDefaultSpeed());
		if (!event.isCancelled()) {
			ConfigurationSection config = configuration.createSection("ShipsData");
			config.set("Player.Name", vessel.getOwner().getUniqueId().toString());
			config.set("Type", "Ship");
			config.set("Config.Block.Percent", getRequiredPercent());
			config.set("Config.Block.Max", getMaxBlocks());
			config.set("Config.Block.Min", getMinBlocks());
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

}

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
import MoseShipsBukkit.ShipsTypes.HookTypes.RequiredMaterial;
import MoseShipsBukkit.StillShip.Vessel.Ship;

public class Marsship extends AbstractShipType implements RequiredMaterial, ClassicVessel {

	protected float percent;
	protected List<Material> requiredMaterials = new ArrayList<Material>();

	public Marsship() {
		super(new File("plugins/Ships/Configuration/VesselTypes/Marsship.yml"), "Marsship", 2, 3, 100, 2500, true, Material.AIR);
	}
	
	@Override
	public Set<Material> getRequiredMaterials() {
		return new HashSet<>(requiredMaterials);
	}
	
	@Override
	public void setRequiredMaterials(Collection<Material> collection) {
		this.requiredMaterials.clear();
		this.requiredMaterials.addAll(collection);
	}

	@Override
	public float getRequiredPercent() {
		return percent;
	}
	
	@Override
	public void setRequiredPercent(float percent) {
		this.percent = percent;
	}

	@Override
	public boolean checkRequirements(Ship vessel, MovementMethod move, Collection<MovingBlock> blocks,
			Player player) {
		VesselTypeUtils util = new VesselTypeUtils();
		if (blocks.size() >= getMinBlocks()) {
			if (blocks.size() <= getMaxBlocks()) {
				if (util.isMovingInto(blocks, getMoveInMaterials())) {
					if (util.isPercentInMovingFrom(blocks, getRequiredMaterials(), getRequiredPercent())) {
						if (move.equals(MovementMethod.MOVE_DOWN)) {
							return true;
						} else {
							long time = vessel.getTeleportLocation().getWorld().getTime();
							if ((time >= 0) && (time <= 13000)) {
								return true;
							} else {
								if (player != null) {
									if (Messages.isEnabled()) {
										player.sendMessage(Ships.runShipsMessage(Messages.getNeeds("Light"), true));
									}
								}
								return false;
							}
						}
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
	public boolean shouldFall(Ship vessel) {
		return false;
	}

	@Override
	public void loadVesselFromClassicFile(Ship vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Marsship) {
			Marsship mars = (Marsship) type;
			float percent = (float)config.getInt("ShipsData.Config.Block.Percent");
			mars.percent = percent;
			vessel.setVesselType(mars);
		}
	}

	@Override
	public void loadVesselFromFiveFile(Ship vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Marsship) {
			Marsship mars = (Marsship) type;
			float percent = (float)config.getDouble("ShipsData.Config.Block.Percent");
			mars.percent = percent;
			vessel.setVesselType(mars);
		}
	}

	@Override
	public void createConfig() {
		File file = getTypeFile();
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

	@Override
	public void loadDefault() {
		File file = getTypeFile();
		if (!file.exists()) {
			createConfig();
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		this.setDefaultSpeed(config.getInt("Speed.Engine"));
		this.setDefaultBoostSpeed(config.getInt("Speed.Boost"));
		this.setMaxBlocks(config.getInt("Blocks.Max"));
		this.setMinBlocks(config.getInt("Blocks.Min"));
		this.setRequiredPercent(config.getInt("Blocks.requiredPercent"));
		List<Material> requiredmaterials = new ArrayList<Material>();
		for (String materialS : config.getStringList("Blocks.requiredBlocks")) {
			Material material = Material.getMaterial(materialS);
			requiredmaterials.add(material);
		}
		this.setRequiredMaterials(requiredmaterials);
		List<Material> moveIn = new ArrayList<Material>();
		moveIn.add(Material.AIR);
		this.setMoveInMaterials(moveIn);
	}

	@Override
	public void save(Ship vessel) {
		File file = new File("plugins/Ships/VesselData/" + vessel.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		ShipsWriteEvent event = new ShipsWriteEvent(file, "Marsship", getRequiredPercent(), getMaxBlocks(), getMinBlocks(),
				getDefaultSpeed());
		if (!event.isCancelled()) {
			config.set("ShipsData.Player.Name", vessel.getOwner().getUniqueId().toString());
			config.set("ShipsData.Type", "Marsship");
			config.set("ShipsData.Config.Block.Percent", getRequiredPercent());
			config.set("ShipsData.Config.Block.Max", getMaxBlocks());
			config.set("ShipsData.Config.Block.Min", getMinBlocks());
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
		Marsship mars = new Marsship();
		mars.requiredMaterials = this.requiredMaterials;
		mars.percent = this.percent;
		return mars;
	}
}

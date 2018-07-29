package org.ships.ship.type.types;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.ships.block.MovingBlock;
import org.ships.configuration.Messages;
import org.ships.configuration.parsers.LocationParser;
import org.ships.configuration.parsers.MaterialSetParser;
import org.ships.configuration.parsers.OfflinePlayerParser;
import org.ships.event.custom.ShipsWriteEvent;
import org.ships.plugin.Ships;
import org.ships.ship.Ship;
import org.ships.ship.movement.MovementMethod;
import org.ships.ship.type.AbstractShipType;
import org.ships.ship.type.VesselType;
import org.ships.ship.type.VesselTypeUtils;
import org.ships.ship.type.hooks.ClassicVessel;
import org.ships.ship.type.hooks.RequiredMaterial;

public class Marsship extends AbstractShipType implements RequiredMaterial, ClassicVessel {
	protected float percent;
	protected List<Material> requiredMaterials = new ArrayList<Material>();

	public Marsship() {
		super(new File("plugins/Ships/Configuration/VesselTypes/Marsship.yml"), "Marsship", 2, 3, 100, 2500, true, Material.AIR);
	}

	@Override
	public Set<Material> getRequiredMaterials() {
		return new HashSet<Material>(this.requiredMaterials);
	}

	@Override
	public void setRequiredMaterials(Collection<Material> collection) {
		this.requiredMaterials.clear();
		this.requiredMaterials.addAll(collection);
	}

	@Override
	public float getRequiredPercent() {
		return this.percent;
	}

	@Override
	public void setRequiredPercent(float percent) {
		this.percent = percent;
	}

	@Override
	public boolean checkRequirements(Ship vessel, MovementMethod move, Collection<MovingBlock> blocks, Player player) {
		VesselTypeUtils util = new VesselTypeUtils();
		if (blocks.size() >= this.getMinBlocks()) {
			if (blocks.size() <= this.getMaxBlocks()) {
				if (util.isMovingInto(blocks, this.getMoveInMaterials())) {
					if (util.isPercentInMovingFrom(blocks, this.getRequiredMaterials(), this.getRequiredPercent())) {
						if (move.equals(MovementMethod.MOVE_DOWN)) {
							return true;
						}
						long time = vessel.getTeleportLocation().getWorld().getTime();
						if (time >= 0 && time <= 13000) {
							return true;
						}
						if (player != null && Messages.isEnabled()) {
							player.sendMessage(Ships.runShipsMessage(Messages.getNeeds("Light"), true));
						}
						return false;
					}
					ArrayList<String> materials = new ArrayList<String>();
					for (Material material : this.getRequiredMaterials()) {
						materials.add(material.name());
					}
					if (player != null && Messages.isEnabled()) {
						player.sendMessage(Ships.runShipsMessage(Messages.getOffBy(util.getOffByPercent(blocks, this.getRequiredMaterials(), this.getRequiredPercent()), materials.toString()), true));
					}
					return false;
				}
				if (player != null && Messages.isEnabled()) {
					player.sendMessage(Ships.runShipsMessage(Messages.getMustBeIn("Air"), true));
				}
				return false;
			}
			if (player != null && Messages.isEnabled()) {
				player.sendMessage(Ships.runShipsMessage(Messages.getShipTooBig(blocks.size(), this.getMaxBlocks()), true));
			}
			return false;
		}
		if (player != null && Messages.isEnabled()) {
			player.sendMessage(Ships.runShipsMessage(Messages.getShipTooSmall(blocks.size(), this.getMinBlocks()), true));
		}
		return false;
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
			mars.percent = config.getInt("ShipsData.Config.Block.Percent");
			vessel.setVesselType(mars);
		}
	}

	@Override
	public void loadVesselFromFiveFile(Ship vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Marsship) {
			Marsship mars = (Marsship) type;
			mars.percent = (float) config.getDouble("ShipsData.Config.Block.Percent");
			vessel.setVesselType(mars);
		}
	}

	@Override
	public void createConfig() {
		File file = this.getTypeFile();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("Speed.Engine", 2);
		config.set("Blocks.Max", 3750);
		config.set("Blocks.Min", 1);
		config.set("Blocks.requiredPercent", 10);
		config.set("Blocks.requiredBlocks", new MaterialSetParser().toString(Arrays.asList(new Material[] { Material.DAYLIGHT_DETECTOR })));
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadDefault() {
		File file = this.getTypeFile();
		if (!file.exists()) {
			this.createConfig();
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		this.setDefaultSpeed(config.getInt("Speed.Engine"));
		this.setDefaultBoostSpeed(config.getInt("Speed.Boost"));
		this.setMaxBlocks(config.getInt("Blocks.Max"));
		this.setMinBlocks(config.getInt("Blocks.Min"));
		this.setRequiredPercent(config.getInt("Blocks.requiredPercent"));
		List<Material> requiredmaterials = new MaterialSetParser().parse(config.getStringList("Blocks.requiredPercent")).get();
		this.setRequiredMaterials(requiredmaterials);
		ArrayList<Material> moveIn = new ArrayList<Material>();
		moveIn.add(Material.AIR);
		this.setMoveInMaterials(moveIn);
	}

	@Override
	public void save(Ship vessel) {
		File file = new File("plugins/Ships/VesselData/" + vessel.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		ShipsWriteEvent event = new ShipsWriteEvent(file, "Marsship", Float.valueOf(this.getRequiredPercent()), this.getMaxBlocks(), this.getMinBlocks(), this.getDefaultSpeed());
		if (!event.isCancelled()) {
			config.set("ShipsData.Player.Name", new OfflinePlayerParser().parse(vessel.getOwner().getUniqueId().toString()));
			config.set("ShipsData.Type", "Marsship");
			config.set("ShipsData.Config.Block.Percent", Float.valueOf(this.getRequiredPercent()));
			config.set("ShipsData.Config.Block.Max", this.getMaxBlocks());
			config.set("ShipsData.Config.Block.Min", this.getMinBlocks());
			config.set("ShipsData.Config.Speed.Engine", this.getDefaultSpeed());
			Block block = vessel.getLocation().getBlock();
			Location loc = vessel.getTeleportLocation();
			config.set("ShipsData.Location.Sign", new LocationParser().toString(block.getLocation()));
			config.set("ShipsData.Location.Teleport", new LocationParser().toString(loc));
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

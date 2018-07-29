package org.ships.ship.type.types;

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
import org.ships.block.MovingBlock;
import org.ships.configuration.Messages;
import org.ships.event.custom.ShipsWriteEvent;
import org.ships.plugin.Ships;
import org.ships.ship.Ship;
import org.ships.ship.movement.MovementMethod;
import org.ships.ship.type.AbstractShipType;
import org.ships.ship.type.VesselType;
import org.ships.ship.type.VesselTypeUtils;
import org.ships.ship.type.hooks.ClassicVessel;
import org.ships.ship.type.hooks.RequiredMaterial;

public class Watership extends AbstractShipType implements RequiredMaterial, ClassicVessel {
	protected float percent;
	protected List<Material> requiredMaterials;

	public Watership() {
		super(new File("plugins/Ships/Configuration/VesselTypes/WaterShip.yml"), "Ship", 10, 2600, 2, 3, false, Material.AIR, Material.WATER);
		this.loadDefault();
	}

	@Override
	public boolean checkRequirements(Ship vessel, MovementMethod move, Collection<MovingBlock> blocks, Player player) {
		int waterLevel = vessel.getWaterLevel(blocks);
		if (waterLevel == 0 && !move.equals(MovementMethod.MOVE_DOWN)) {
			if (player != null && Messages.isEnabled()) {
				player.sendMessage(Ships.runShipsMessage("Ship can only move down into water.", true));
			}
			return false;
		}
		if (move.equals(MovementMethod.MOVE_DOWN) && waterLevel != 0 || move.equals(MovementMethod.MOVE_UP)) {
			return false;
		}
		VesselTypeUtils util = new VesselTypeUtils();
		if (util.isMaterialInMovingTo(blocks, this.getMoveInMaterials())) {
			if (util.isPercentInMovingFrom(blocks, this.getRequiredMaterials(), this.getRequiredPercent())) {
				return true;
			}
			if (player != null && Messages.isEnabled()) {
				player.sendMessage(Ships.runShipsMessage(Messages.getNeeds(this.requiredMaterials.get(0).name()), true));
			}
			return false;
		}
		if (player != null && Messages.isEnabled()) {
			player.sendMessage(Ships.runShipsMessage(Messages.getMustBeIn("Water"), true));
		}
		return false;
	}

	@Override
	public boolean shouldFall(Ship vessel) {
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
	public void loadVesselFromClassicFile(Ship vessel, File file) {
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
	public void loadVesselFromFiveFile(Ship vessel, File file) {
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
		File file = this.getTypeFile();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("Speed.Engine", 4);
		config.set("Speed.Boost", 5);
		config.set("Blocks.Max", 4500);
		config.set("Blocks.Min", 1);
		config.set("Blocks.requiredPercent", 65);
		ArrayList<Integer> requiredBlocks = new ArrayList<Integer>();
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
		File file = this.getTypeFile();
		if (!file.exists()) {
			this.createConfig();
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		this.setDefaultSpeed(config.getInt("Speed.Engine"));
		this.setDefaultBoostSpeed(config.getInt("Speed.Boost"));
		this.percent = config.getInt("Blocks.requiredPercent");
		this.setMaxBlocks(config.getInt("Blocks.Max"));
		this.setMinBlocks(config.getInt("Blocks.Min"));
		ArrayList<Material> requiredmaterials = new ArrayList<Material>();
		for (String id : config.getStringList("Blocks.requiredBlocks")) {
			Material material = Material.getMaterial(id);
			requiredmaterials.add(material);
		}
		ArrayList<Material> moveIn = new ArrayList<Material>();
		moveIn.add(Material.WATER);
		moveIn.add(Material.AIR);
		this.setMoveInMaterials(moveIn);
	}

	@Override
	public void save(Ship vessel) {
		File file = new File("plugins/Ships/VesselData/" + vessel.getName() + ".yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		ShipsWriteEvent event = new ShipsWriteEvent(file, "Ship", Float.valueOf(this.percent), this.getMaxBlocks(), this.getMinBlocks(), this.getDefaultSpeed());
		if (!event.isCancelled()) {
			ConfigurationSection config = configuration.createSection("ShipsData");
			config.set("Player.Name", vessel.getOwner().getUniqueId().toString());
			config.set("Type", "Ship");
			config.set("Config.Block.Percent", Float.valueOf(this.getRequiredPercent()));
			config.set("Config.Block.Max", this.getMaxBlocks());
			config.set("Config.Block.Min", this.getMinBlocks());
			config.set("Config.Speed.Engine", this.getDefaultSpeed());
			Block block = vessel.getLocation().getBlock();
			Location loc = vessel.getTeleportLocation();
			config.set("Location.Sign", "" + block.getX() + "," + block.getY() + "," + block.getZ() + "," + block.getWorld().getName());
			config.set("Location.Teleport", "" + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getWorld().getName());
			try {
				configuration.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Set<Material> getRequiredMaterials() {
		return new HashSet<Material>(this.requiredMaterials);
	}

	@Override
	public float getRequiredPercent() {
		return this.percent;
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
}

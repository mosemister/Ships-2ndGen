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
import org.ships.ship.type.hooks.Fuel;
import org.ships.ship.type.hooks.RequiredMaterial;

public class Plane extends AbstractShipType implements Fuel, RequiredMaterial, ClassicVessel {
	protected int takeAmount;
	protected float percent;
	protected List<Material> fuelTypes = new ArrayList<Material>();
	protected List<Material> requiredMaterials = new ArrayList<Material>();

	public Plane() {
		super(new File("plugins/Ships/Configuration/VesselTypes/Plane.yml"), "Plane", 4, 5, 100, 2500, true, Material.AIR);
		this.loadDefault();
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
		return new HashSet<Material>(this.requiredMaterials);
	}

	@Override
	public float getRequiredPercent() {
		return this.percent;
	}

	@Override
	public boolean removeFuel(Ship vessel) {
		VesselTypeUtils util = new VesselTypeUtils();
		boolean ret = util.takeFuel(vessel, this.takeAmount, this.fuelTypes);
		return ret;
	}

	@Override
	public int getTotalFuel(Ship vessel) {
		VesselTypeUtils util = new VesselTypeUtils();
		int ret = util.getTotalAmountOfFuel(vessel, this.fuelTypes);
		return ret;
	}

	@Override
	public Set<Material> getFuelTypes() {
		return new HashSet<Material>(this.fuelTypes);
	}

	@Override
	public boolean checkRequirements(Ship vessel, MovementMethod move, Collection<MovingBlock> blocks, Player player) {
		VesselTypeUtils util = new VesselTypeUtils();
		if (blocks.size() <= this.getMaxBlocks()) {
			if (blocks.size() >= this.getMinBlocks()) {
				if (util.isMovingInto(blocks, this.getMoveInMaterials())) {
					if (util.isPercentInMovingFrom(blocks, this.requiredMaterials, this.percent)) {
						if (move.equals(MovementMethod.MOVE_DOWN)) {
							return true;
						}
						if (util.checkFuel(vessel, this.takeAmount, this.getFuelTypes())) {
							util.takeFuel(vessel, this.takeAmount, this.getFuelTypes());
							return true;
						}
						if (player != null && Messages.isEnabled()) {
							player.sendMessage(Ships.runShipsMessage(Messages.getOutOfFuel("fuel"), true));
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
	public void loadVesselFromClassicFile(Ship vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Plane) {
			Plane plane = (Plane) type;
			int percent = config.getInt("ShipsData.Config.Block.Percent");
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			plane.setRequiredPercent(percent);
			plane.setFuelConsumption(consumption);
			vessel.setVesselType(plane);
		}
	}

	@Override
	public void loadVesselFromFiveFile(Ship vessel, File file) {
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
		File file = this.getTypeFile();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("Speed.Engine", 5);
		config.set("Speed.Boost", 6);
		config.set("Blocks.Max", 3750);
		config.set("Blocks.Min", 1);
		config.set("Blocks.requiredPercent", 50);
		ArrayList<Integer> requiredBlocks = new ArrayList<Integer>();
		requiredBlocks.add(42);
		config.set("Blocks.requiredBlocks", requiredBlocks);
		ArrayList<String> fuel = new ArrayList<String>();
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
		File file = this.getTypeFile();
		if (!file.exists()) {
			this.createConfig();
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		this.setDefaultSpeed(config.getInt("Speed.Engine"));
		this.setDefaultBoostSpeed(config.getInt("Speed.Boost"));
		this.setRequiredPercent(config.getInt("Blocks.requiredPercent"));
		this.setMaxBlocks(config.getInt("Blocks.Max"));
		this.setMinBlocks(config.getInt("Blocks.Min"));
		ArrayList<Material> requiredmaterials = new ArrayList<Material>();
		for (String id : config.getStringList("Blocks.requiredBlocks")) {
			Material material = Material.getMaterial(id);
			requiredmaterials.add(material);
		}
		int take = config.getInt("Fuel.TakeAmount");
		List<String> fuel = config.getStringList("Fuel.Fuels");
		ArrayList<Material> fuels = new ArrayList<Material>();
		fuel.stream().forEach(f -> {
			fuels.add(Material.getMaterial(f));
		});
		this.setFuelTypes(fuels);
		this.setRequiredMaterials(requiredmaterials);
		this.setFuelConsumption(take);
		ArrayList<Material> moveIn = new ArrayList<Material>();
		moveIn.add(Material.AIR);
		this.setMoveInMaterials(moveIn);
	}

	@Override
	public void save(Ship vessel) {
		File file = new File("plugins/Ships/VesselData/" + vessel.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		ArrayList<String> fuels = new ArrayList<String>();
		for (Material fuels2 : this.getFuelTypes()) {
			fuels.add(fuels2.name());
		}
		ShipsWriteEvent event = new ShipsWriteEvent(file, "Plane", Float.valueOf(this.getRequiredPercent()), Float.valueOf(this.getRequiredPercent()), this.getMaxBlocks(), this.getMinBlocks(), this.getDefaultSpeed(), fuels, this.getFuelConsumption());
		if (!event.isCancelled()) {
			config.set("ShipsData.Player.Name", vessel.getOwner().getUniqueId().toString());
			config.set("ShipsData.Type", "Plane");
			config.set("ShipsData.Config.Block.Percent", Float.valueOf(this.getRequiredPercent()));
			config.set("ShipsData.Config.Block.Max", this.getMaxBlocks());
			config.set("ShipsData.Config.Block.Min", this.getMinBlocks());
			config.set("ShipsData.Config.Fuel.Fuels", fuels);
			config.set("ShipsData.Config.Fuel.Consumption", this.getFuelConsumption());
			config.set("ShipsData.Config.Speed.Engine", this.getDefaultSpeed());
			Block block = vessel.getLocation().getBlock();
			Location loc = vessel.getTeleportLocation();
			config.set("ShipsData.Location.Sign", "" + block.getX() + "," + block.getY() + "," + block.getZ() + "," + block.getWorld().getName());
			config.set("ShipsData.Location.Teleport", "" + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getWorld().getName());
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
		plane.takeAmount = this.takeAmount;
		plane.percent = this.percent;
		plane.fuelTypes = this.fuelTypes;
		plane.requiredMaterials = this.requiredMaterials;
		return plane;
	}
}

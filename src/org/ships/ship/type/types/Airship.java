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
import org.bukkit.Tag;
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
import org.ships.ship.type.hooks.Fuel;
import org.ships.ship.type.hooks.RequiredMaterial;

public class Airship extends AbstractShipType implements Fuel, RequiredMaterial, ClassicVessel {
	protected float percent;
	protected int takeAmount;
	protected List<Material> requiredMaterials = new ArrayList<Material>();
	protected List<Material> fuelTypes = new ArrayList<Material>();

	public Airship() {
		super(new File("plugins/Ships/Configuration/VesselTypes/Airship.yml"), "Airship", 2, 3, 100, 2500, true, Material.AIR);
		this.loadDefault();
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
	public int getFuelConsumption() {
		return this.takeAmount;
	}

	@Override
	public void setFuelConsumption(int consumption) {
		this.takeAmount = consumption;
	}

	@Override
	public Set<Material> getFuelTypes() {
		return new HashSet<Material>(this.fuelTypes);
	}

	@Override
	public void setFuelTypes(Collection<Material> collection) {
		this.fuelTypes.clear();
		this.fuelTypes.addAll(collection);
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
	public boolean checkRequirements(Ship vessel, MovementMethod move, Collection<MovingBlock> blocks, Player player) {
		VesselTypeUtils util = new VesselTypeUtils();
		if (util.isMovingInto(blocks, this.getMoveInMaterials())) {
			if (util.isPercentInMovingFrom(blocks, this.getRequiredMaterials(), this.getRequiredPercent())) {
				if (util.isMaterialInMovingFrom(blocks, Material.FIRE)) {
					if (move.equals(MovementMethod.MOVE_DOWN)) {
						return true;
					}
					if (this.getTotalFuel(vessel) >= this.takeAmount) {
						this.removeFuel(vessel);
						return true;
					}
					if (player != null && Messages.isEnabled()) {
						player.sendMessage(Ships.runShipsMessage(Messages.getOutOfFuel("fuel"), true));
					}
					return false;
				}
				if (player != null && Messages.isEnabled()) {
					player.sendMessage(Ships.runShipsMessage(Messages.getNeeds("Burner"), true));
				}
				return false;
			}
			ArrayList<String> materials = new ArrayList<String>();
			for (Material material : this.getRequiredMaterials()) {
				materials.add(material.name());
			}
			if (player != null && Messages.isEnabled()) {
				player.sendMessage(Ships.runShipsMessage(Messages.getOffBy(util.getOffByPercent(blocks, this.getRequiredMaterials(), this.getRequiredPercent()), "(of either) " + materials.toString()), true));
			}
			return false;
		}
		if (player != null && Messages.isEnabled()) {
			player.sendMessage(Ships.runShipsMessage(Messages.getMustBeIn("Air"), true));
		}
		return false;
	}

	@Override
	public void loadVesselFromClassicFile(Ship vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Airship) {
			Airship air = (Airship) type;
			float percent = config.getInt("ShipsData.Config.Block.Percent");
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			air.setMaxBlocks(config.getInt("ShipsData.Config.Block.Max"));
			air.setMinBlocks(config.getInt("ShipsData.Config.Block.Min"));
			air.percent = percent;
			air.takeAmount = consumption;
			vessel.setVesselType(air);
		}
	}

	@Override
	public void loadVesselFromFiveFile(Ship vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Airship) {
			Airship air = (Airship) type;
			int percent = config.getInt("ShipsData.Config.Block.Percent");
			int consumption = config.getInt("ShipsData.Config.Fuel.Consumption");
			List<String> fuelsL = config.getStringList("ShipsData.Config.Fuel.Fuels");
			air.setMaxBlocks(config.getInt("ShipsData.Config.Block.Max"));
			air.setMinBlocks(config.getInt("ShipsData.Config.Block.Min"));
			air.percent = percent;
			air.takeAmount = consumption;
			if (fuelsL.size() != 0) {
				air.fuelTypes = new MaterialSetParser().parse(fuelsL).get();
			}
			vessel.setVesselType(air);
		}
	}

	@Override
	public void createConfig() {
		File file = this.getTypeFile();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("Speed.Engine", 2);
		config.set("Blocks.Max", 3750);
		config.set("Blocks.Min", 1);
		config.set("Blocks.requiredPercent", 60);
		config.set("Blocks.requiredBlocks", new MaterialSetParser().toString(new ArrayList<Material>(Tag.WOOL.getValues())));
		ArrayList<Material> fuel = new ArrayList<Material>();
		fuel.add(Material.COAL);
		config.set("Fuel.Fuels", new MaterialSetParser().toString(fuel));
		config.set("Fuel.TakeAmount", 1);
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
		this.setRequiredPercent((float) config.getDouble("Blocks.requiredPercent"));
		this.setMaxBlocks(config.getInt("Blocks.Max"));
		this.setMinBlocks(config.getInt("Blocks.Min"));
		List<Material> requiredmaterials = new MaterialSetParser().parse(config.getStringList("Blocks.requiredBlocks")).get();
		int take = config.getInt("Fuel.TakeAmount");
		List<Material> fuels = new MaterialSetParser().parse(config.getStringList("Fuel.Fuels")).get();
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
		List<String> fuels = new MaterialSetParser().toString(this.fuelTypes);
		ShipsWriteEvent event = new ShipsWriteEvent(file, "Airship", Float.valueOf(this.getRequiredPercent()), this.getMaxBlocks(), this.getMinBlocks(), this.getDefaultSpeed(), fuels, this.getFuelConsumption());
		if (!event.isCancelled()) {
			config.set("ShipsData.Player.Name", new OfflinePlayerParser().toString(vessel.getOwner()));
			config.set("ShipsData.Type", "Airship");
			config.set("ShipsData.Config.Block.Percent", Float.valueOf(this.getRequiredPercent()));
			config.set("ShipsData.Config.Block.Max", this.getMaxBlocks());
			config.set("ShipsData.Config.Block.Min", this.getMinBlocks());
			config.set("ShipsData.Config.Fuel.Fuels", fuels);
			config.set("ShipsData.Config.Fuel.Consumption", this.getFuelConsumption());
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
	public Airship createClone() {
		Airship air = new Airship();
		air.fuelTypes = this.fuelTypes;
		air.percent = this.percent;
		air.requiredMaterials = this.requiredMaterials;
		air.takeAmount = this.takeAmount;
		return air;
	}
}

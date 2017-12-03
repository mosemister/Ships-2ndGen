package MoseShipsBukkit.ShipsTypes.Types;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

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
import MoseShipsBukkit.ShipsTypes.HookTypes.RequiredMaterial;
import MoseShipsBukkit.StillShip.Vessel.MovableVessel;
import MoseShipsBukkit.StillShip.Vessel.ProtectedVessel;
import MoseShipsBukkit.Utils.ConfigLinks.Messages;

public class Marsship extends VesselType implements RequiredMaterial, ClassicVessel {

	int PERCENT;
	List<Material> REQUIREDBLOCKS = new ArrayList<Material>();

	public Marsship() {
		super("Marsship", new ArrayList<Material>(Arrays.asList(Material.AIR)), 2, 3, true);
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
	public boolean checkRequirements(MovableVessel vessel, MovementMethod move, List<MovingBlock> blocks,
			@Nullable Player player) {
		VesselTypeUtils util = new VesselTypeUtils();
		if (blocks.size() >= getMinBlocks()) {
			if (blocks.size() <= getMaxBlocks()) {
				if (util.isMovingInto(blocks, getMoveInMaterials())) {
					if (util.isPercentInMovingFrom(blocks, REQUIREDBLOCKS, PERCENT)) {
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
		return false;
	}

	@Override
	public File getTypeFile() {
		File file = new File("plugins/Ships/Configuration/VesselTypes/Marsship.yml");
		return file;
	}

	@Override
	public VesselType clone() {
		Marsship mars = new Marsship();
		cloneVesselTypeData(mars);
		mars.REQUIREDBLOCKS = REQUIREDBLOCKS;
		mars.PERCENT = PERCENT;
		return mars;
	}

	@Override
	public void loadVesselFromClassicFile(ProtectedVessel vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Marsship) {
			Marsship mars = (Marsship) type;
			int blockPercent = config.getInt("ShipsData.Config.Block.Percent");
			mars.PERCENT = blockPercent;
			vessel.setVesselType(mars);
		}

	}

	@Override
	public void loadVesselFromFiveFile(ProtectedVessel vessel, File file) {
		VesselType type = vessel.getVesselType();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (type instanceof Marsship) {
			Marsship mars = (Marsship) type;
			int percent = config.getInt("ShipsData.Config.Block.Percent");
			mars.PERCENT = percent;
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
		this.PERCENT = config.getInt("Blocks.requiredPercent");
		List<Material> requiredmaterials = new ArrayList<Material>();
		for (int id : config.getIntegerList("Blocks.requiredBlocks")) {
			@SuppressWarnings("deprecation")
			Material material = Material.getMaterial(id);
			requiredmaterials.add(material);
		}
		this.REQUIREDBLOCKS = requiredmaterials;
		List<Material> moveIn = new ArrayList<Material>();
		moveIn.add(Material.AIR);
		this.setMoveInMaterials(moveIn);
	}

	@Override
	public void save(ProtectedVessel vessel) {
		File file = new File("plugins/Ships/VesselData/" + vessel.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		ShipsWriteEvent event = new ShipsWriteEvent(file, "Marsship", PERCENT, getMaxBlocks(), getMinBlocks(),
				getDefaultSpeed());
		if (!event.isCancelled()) {
			config.set("ShipsData.Player.Name", vessel.getOwner().getUniqueId().toString());
			config.set("ShipsData.Type", "Marsship");
			config.set("ShipsData.Config.Block.Percent", PERCENT);
			config.set("ShipsData.Config.Block.Max", getMaxBlocks());
			config.set("ShipsData.Config.Block.Min", getMinBlocks());
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

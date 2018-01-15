package MoseShipsBukkit.StillShip.Vessel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.ships.block.structure.ShipsStructure;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.ShipsTypes.VesselType;
import MoseShipsBukkit.Utils.VesselLoader;
import MoseShipsBukkit.Utils.Exceptions.InvalidSignException;
import MoseShipsBukkit.Utils.MoseUtils.LimitedList;

public class Vessel extends MovableVessel {

	static List<Vessel> LOADEDVESSELS = new ArrayList<Vessel>();

	public Vessel(Sign sign, OfflinePlayer player, Location loc) throws InvalidSignException {
		super(sign, player, loc);
		LOADEDVESSELS.add(this);
		TYPE.save(this);
	}

	public Vessel(Sign sign, String name, VesselType type, Player player) {
		super(sign, name, type, player);
		LOADEDVESSELS.add(this);
		type.save(this);
	}

	public Vessel(Sign sign, String name, VesselType type, Player player, boolean save) {
		super(sign, name, type, player);
		LOADEDVESSELS.add(this);
		if (save) {
			type.save(this);
		}
	}

	public Vessel(Sign sign, String name, VesselType type, OfflinePlayer player, Location loc) {
		super(sign, name, type, player, loc);
		LOADEDVESSELS.add(this);
		type.save(this);
	}

	public Vessel(Sign sign, String name, VesselType type, OfflinePlayer player, Location loc, boolean save) {
		super(sign, name, type, player, loc);
		LOADEDVESSELS.add(this);
		if (save) {
			type.save(this);
		}
	}

	public void remove() {
		LOADEDVESSELS.remove(this);
		File file = getFile();
		file.delete();
	}

	public void reload() {
		LOADEDVESSELS.remove(this);
		final File file = getFile();
		Ships.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(Ships.getPlugin(), new Runnable() {

			@Override
			public void run() {
				VesselLoader.newLoader(file);
			}

		}, 0);
	}

	public static List<Vessel> getVessels() {
		return LOADEDVESSELS;
	}

	public static LimitedList<Vessel> getVessels(OfflinePlayer player) {
		List<Vessel> vessels = new ArrayList<Vessel>();
		for (Vessel vessel : getVessels()) {
			if (vessel.getOwner().equals(player)) {
				vessels.add(vessel);
			}
		}
		return new LimitedList<Vessel>(vessels);
	}

	public static LimitedList<Vessel> getVessels(World world) {
		List<Vessel> vessels = new ArrayList<Vessel>();
		for (Vessel vessel : getVessels()) {
			if (vessel.getLocation().getWorld().equals(world)) {
				vessels.add(vessel);
			}
		}
		return new LimitedList<Vessel>(vessels);
	}

	public static Vessel getVessel(String name) {
		for (Vessel vessel : getVessels()) {
			if (vessel.getName().equalsIgnoreCase(name)) {
				return vessel;
			}
		}
		return null;
	}

	public static Vessel getVessel(Sign sign) {
		Block block = sign.getBlock();
		for (Vessel vessel : getVessels()) {
			if (block.equals(vessel.getLocation().getBlock())) {
				return vessel;
			}
		}
		return null;
	}

	public static Vessel getVessel(Block block, boolean newStructure) {
		if (newStructure) {
			List<Block> blocks = Ships.getBaseStructure(block);
			for (Block block2 : blocks) {
				if (block2.getState() instanceof Sign) {
					Sign sign = (Sign) block2.getState();
					if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")) {
						Vessel vessel = getVessel(sign);
						if (vessel == null) {
							return null;
						} else {
							ShipsStructure structure = new ShipsStructure(blocks);
							vessel.setStructure(structure);
							return vessel;
						}
					}
				}
			}
		} else {
			for (Vessel vessel : LOADEDVESSELS) {
				for (Block block2 : vessel.getStructure().getAllBlocks()) {
					if (block2.equals(block)) {
						return vessel;
					}
				}
			}
		}
		return null;
	}
}

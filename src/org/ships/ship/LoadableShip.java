package org.ships.ship;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.block.SignChangeEvent;
import org.ships.block.BlockStack;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.structure.BasicStructure;
import org.ships.block.structure.ShipsStructure;
import org.ships.plugin.InvalidSignException;
import org.ships.plugin.Ships;
import org.ships.ship.loader.VesselLoader;
import org.ships.ship.type.VesselType;

public class LoadableShip extends AbstractShipBlockListable implements Ship {
	private static final List<LoadableShip> SHIPS = new ArrayList<LoadableShip>();

	public LoadableShip(VesselType type, SignChangeEvent event) {
		this(type, event, event.getPlayer().getLocation(), event.getPlayer());
	}

	public LoadableShip(VesselType type, SignChangeEvent event, Location teleportLocation, OfflinePlayer player) {
		this(type, event, teleportLocation, player, new BasicStructure());
		this.updateStructure();
	}

	public LoadableShip(VesselType type, Sign loc, Location teleportLocation, OfflinePlayer player) throws InvalidSignException {
		this(type, loc, teleportLocation, player, new BasicStructure());
		this.updateStructure();
	}

	public LoadableShip(VesselType type, SignChangeEvent event, Location teleportLocation, OfflinePlayer player, ShipsStructure structure) {
		this(type, event, teleportLocation, player, structure, new File("Ships/VesselData/" + LoadableShip.getName(event.getBlock().getLocation()) + ".yml"));
	}

	public LoadableShip(VesselType type, Sign loc, Location teleportLocation, OfflinePlayer player, ShipsStructure structure) throws InvalidSignException {
		this(type, loc.getLocation(), teleportLocation, player, structure, new File("Ships/VesselData/" + LoadableShip.getName(loc.getLocation()) + ".yml"));
	}

	public LoadableShip(VesselType type, SignChangeEvent event, Location teleportLocation, OfflinePlayer player, ShipsStructure structure, File file) {
		super(type, event.getBlock().getLocation(), teleportLocation, player, structure, file);
		SHIPS.add(this);
		this.getVesselType().save(this);
	}

	public LoadableShip(VesselType type, Location loc, Location teleportLocation, OfflinePlayer player, ShipsStructure structure, File file) throws InvalidSignException {
		super(type, loc, teleportLocation, player, structure, file);
		BlockState state = loc.getBlock().getState();
		if (!(state instanceof Sign)) {
			throw new InvalidSignException();
		}
		Sign sign = (Sign) state;
		String line1 = sign.getLine(0);
		String line2 = sign.getLine(1);
		String line3 = sign.getLine(2);
		if (!line1.equals(ChatColor.YELLOW + "[Ships]")) {
			throw new InvalidSignException(1, sign);
		}
		if (!line2.startsWith(ChatColor.BLUE + "")) {
			throw new InvalidSignException(2, sign);
		}
		if (!line3.startsWith(ChatColor.GREEN + "")) {
			throw new InvalidSignException(3, sign);
		}
		SHIPS.add(this);
		this.getVesselType().save(this);
	}

	@Override
	public void save() {
	}

	@Override
	public void delete() {
		SHIPS.remove(this);
		File file = this.getFile();
		file.delete();
	}

	@Override
	public void reload() {
		SHIPS.remove(this);
		File file = this.getFile();
		Ships.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(Ships.getPlugin(), () -> {
			VesselLoader.newLoader(file);
		}, 0);
	}

	private static String getName(Location loc) {
		BlockState state = loc.getBlock().getState();
		if (!(state instanceof Sign)) {
			return null;
		}
		Sign sign = (Sign) state;
		String name = sign.getLine(2);
		return ChatColor.stripColor(name);
	}

	public static Set<LoadableShip> getShips() {
		return new HashSet<LoadableShip>(SHIPS);
	}

	public static HashSet<LoadableShip> getShips(OfflinePlayer player) {
		ArrayList<LoadableShip> vessels = new ArrayList<LoadableShip>();
		for (LoadableShip vessel : LoadableShip.getShips()) {
			if (!vessel.getOwner().equals(player))
				continue;
			vessels.add(vessel);
		}
		return new HashSet<LoadableShip>(vessels);
	}

	public static Set<LoadableShip> getShips(World world) {
		ArrayList<LoadableShip> vessels = new ArrayList<LoadableShip>();
		for (LoadableShip vessel : LoadableShip.getShips()) {
			if (!vessel.getLocation().getWorld().equals(world))
				continue;
			vessels.add(vessel);
		}
		return new HashSet<LoadableShip>(vessels);
	}

	public static LoadableShip getShip(String name) {
		for (LoadableShip vessel : LoadableShip.getShips()) {
			if (!vessel.getName().equalsIgnoreCase(name))
				continue;
			return vessel;
		}
		return null;
	}

	public static LoadableShip getShip(Sign sign) {
		Block block = sign.getBlock();
		for (LoadableShip vessel : LoadableShip.getShips()) {
			if (!block.equals(vessel.getLocation().getBlock()))
				continue;
			return vessel;
		}
		return null;
	}

	public static LoadableShip getShip(Block block, boolean newStructure) {
		if (newStructure) {
			BlockStack blocks = Ships.getBaseStructure(block);
			for (Block block2 : blocks) {
				Sign sign;
				if (!(block2.getState() instanceof Sign) || !(sign = (Sign) block2.getState()).getLine(0).equals(ChatColor.YELLOW + "[Ships]"))
					continue;
				LoadableShip vessel = LoadableShip.getShip(sign);
				if (vessel == null) {
					return null;
				}
				BasicStructure structure = new BasicStructure(BlockHandler.convert(blocks));
				vessel.updateToMovingStructure(structure);
				return vessel;
			}
		} else {
			for (LoadableShip vessel : LoadableShip.getShips()) {
				for (BlockHandler<? extends BlockState> block2 : vessel.getStructure().getAllBlocks()) {
					if (!block2.getBlock().equals(block))
						continue;
					return vessel;
				}
			}
		}
		return null;
	}
}

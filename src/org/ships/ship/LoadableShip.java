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
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.structure.BasicStructure;
import org.ships.block.structure.ShipsStructure;
import org.ships.plugin.InvalidSignException;
import org.ships.plugin.Ships;
import org.ships.ship.loader.VesselLoader;
import org.ships.ship.type.VesselType;

public class LoadableShip extends AbstractShipBlockListable implements Ship{

	private static final List<LoadableShip> SHIPS = new ArrayList<>(); 
	
	public LoadableShip(VesselType type, Sign loc, Location teleportLocation, OfflinePlayer player) throws InvalidSignException {
		this(type, loc, teleportLocation, player, new BasicStructure());
		updateStructure();
	}
	
	public LoadableShip(VesselType type, Sign loc, Location teleportLocation, OfflinePlayer player,
			ShipsStructure structure) throws InvalidSignException {
		this(type, loc.getLocation(), teleportLocation, player, structure, new File("Ships/VesselData/" + getName(loc.getLocation()) + ".yml"));
	}
	
	public LoadableShip(VesselType type, Location loc, Location teleportLocation, OfflinePlayer player,
			ShipsStructure structure, File file) throws InvalidSignException {
		super(type, loc, teleportLocation, player, structure, file);
		BlockState state = loc.getBlock().getState();
		if (!(state instanceof Sign)) {
			throw new InvalidSignException();
		}
			Sign sign = (Sign)state;
			String line1 = sign.getLine(0);
			String line2 = sign.getLine(1);
			String line3 = sign.getLine(2);
			if(!line1.equals(ChatColor.YELLOW + "[Ships]")) {
				throw new InvalidSignException(1, sign);
			}
			if(!line2.startsWith(ChatColor.BLUE + "")) {
				throw new InvalidSignException(2, sign);
			}
			if(!line3.startsWith(ChatColor.GREEN + "")) {
				throw new InvalidSignException(3, sign);
			}
		SHIPS.add(this);
		getVesselType().save(this);
	}
	
	@Override
	public void save() {
		// TODO Auto-generated method stub
		getBlockList().updateAll(getBlockListFile(), getBlockListConfiguration(), true);
	}

	@Override
	public void delete() {
		SHIPS.remove(this);
		File file = getFile();
		file.delete();
	}
	
	public void reload() {
		SHIPS.remove(this);
		final File file = getFile();
		Ships.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(Ships.getPlugin(), new Runnable() {

			@Override
			public void run() {
				VesselLoader.newLoader(file);
			}

		}, 0);
	}
	
	private static String getName(Location loc) {
		BlockState state = loc.getBlock().getState();
		if(!(state instanceof Sign)) {
			return null;
		}
		Sign sign = (Sign)state;
		String name = sign.getLine(2);
		return ChatColor.stripColor(name);
	}
	
	public static Set<LoadableShip> getShips() {
		return new HashSet<>(SHIPS);
	}
	
	public static HashSet<LoadableShip> getShips(OfflinePlayer player) {
		List<LoadableShip> vessels = new ArrayList<>();
		for (LoadableShip vessel : getShips()) {
			if (vessel.getOwner().equals(player)) {
				vessels.add(vessel);
			}
		}
		return new HashSet<>(vessels);
	}
	
	public static Set<LoadableShip> getShips(World world) {
		List<LoadableShip> vessels = new ArrayList<>();
		for (LoadableShip vessel : getShips()) {
			if (vessel.getLocation().getWorld().equals(world)) {
				vessels.add(vessel);
			}
		}
		return new HashSet<>(vessels);
	}
	
	public static LoadableShip getShip(String name) {
		for (LoadableShip vessel : getShips()) {
			if (vessel.getName().equalsIgnoreCase(name)) {
				return vessel;
			}
		}
		return null;
	}
	
	public static LoadableShip getShip(Sign sign) {
		Block block = sign.getBlock();
		for (LoadableShip vessel : getShips()) {
			if (block.equals(vessel.getLocation().getBlock())) {
				return vessel;
			}
		}
		return null;
	}
	
	public static LoadableShip getShip(Block block, boolean newStructure) {
		if (newStructure) {
			List<Block> blocks = Ships.getBaseStructure(block);
			for (Block block2 : blocks) {
				if (block2.getState() instanceof Sign) {
					Sign sign = (Sign) block2.getState();
					if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")) {
						LoadableShip vessel = getShip(sign);
						if (vessel == null) {
							return null;
						} else {
							ShipsStructure structure = new BasicStructure(BlockHandler.convert(blocks));
							vessel.updateToMovingStructure(structure);
							return vessel;
						}
					}
				}
			}
		} else {
			for (LoadableShip vessel : getShips()) {
				for (BlockHandler<? extends BlockState> block2 : vessel.getStructure().getAllBlocks()) {
					if (block2.getBlock().equals(block)) {
						return vessel;
					}
				}
			}
		}
		return null;
	}

}

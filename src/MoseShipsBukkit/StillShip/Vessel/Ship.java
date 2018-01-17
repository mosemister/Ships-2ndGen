package MoseShipsBukkit.StillShip.Vessel;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.structure.ShipsStructure;

import MoseShipsBukkit.MovingShip.AutoPilotData;
import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.MovingShip.MovingBlock;
import MoseShipsBukkit.ShipsTypes.VesselType;
import MoseShipsBukkit.StillShip.Vectors.BlockVector;

public interface Ship {
	
	public OfflinePlayer getOwner();
	public VesselType getVesselType();
	public Location getLocation();
	public Location getTeleportLocation();
	public AutoPilotData getAutoPilotData();
	public ShipsStructure getStructure();
	public File getFile();
	public Map<OfflinePlayer, BlockVector> getBlockLocation();
	public boolean isMoving();
	public boolean isInvincible();
	public void updateLocation(Location teleport, Sign sign);
	public void updateStructure();
	public void save();
	public void delete();
	public void reload();
	
	public boolean transform(Location loc, boolean force);
	public boolean moveTowards(MovementMethod move, int speed, OfflinePlayer player, boolean fireEvents);
	public boolean moveTowardsLocation(Location moveTo, int speed, OfflinePlayer player);
	public boolean moveTowardsForcefully(MovementMethod move, int speed, OfflinePlayer player);
	public boolean moveTo(Location loc, OfflinePlayer player, boolean fireEvents);
	public boolean moveToForcefully(Location loc);
	
	public default Sign getSign() {
		BlockState state = getLocation().getBlock().getState();
		if(state instanceof Sign) {
			return (Sign)state;
		}
		return null;
	}
	
	public default String getName() {
		Sign sign = getSign();
		if(sign == null) {
			return null;
		}
		String name = sign.getLine(2);
		return ChatColor.stripColor(name);
	}
	
	public default int getWaterLevel() {
		int level = 0;
		for (BlockHandler handler : getStructure().getAllBlocks()){
			int y = handler.getBlock().getY();
			if(level < y) {
				level = y;
			}
		}
		return level;
	}
	
	public default int getWaterLevel(Collection<MovingBlock> blocks) {
		int level = 0;
		for (MovingBlock block : blocks){
			int y = block.getMovingTo().getBlockY();
			if(level < y) {
				level = y;
			}
		}
		return level;
	}
	
	public default Set<Entity> getEntities() {
		return getLocation().getWorld().getEntities().stream().filter(e -> {
			Block block = e.getLocation().getBlock().getRelative(BlockFace.DOWN);
			return getStructure().getAllBlocks().stream().anyMatch(b -> b.getBlock().equals(block));
		}).collect(Collectors.toSet());
	}
	
	public default boolean hasPermissionToMove(Player player) {
		if (player.hasPermission("ships." + getName() + ".use")) {
			return true;
		} else if (player.hasPermission("ships." + getVesselType().getName() + ".use")) {
			return true;
		} else if (player.hasPermission("ships.*.use")) {
			return true;
		}
		return false;
	}
	
	public default boolean moveTowards(MovementMethod move, int speed, OfflinePlayer player) {
		return moveTowards(move, speed, player, true);
	}
	
	public default boolean moveTo(Location loc, OfflinePlayer player) {
		return moveTo(loc, player, true);
	}
}

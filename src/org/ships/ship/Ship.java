package org.ships.ship;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
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
import org.ships.block.MovingBlock;
import org.ships.block.Vector.BlockVector;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.structure.ShipsStructure;
import org.ships.ship.movement.AutoPilotData;
import org.ships.ship.movement.MovementMethod;
import org.ships.ship.type.VesselType;

public interface Ship {
	
	public OfflinePlayer getOwner();
	public Ship setOwner(OfflinePlayer player);
	public Set<UUID> getSubPilots();
	public Ship registerPilot(UUID uuid);
	public Ship deregisterPilot(UUID uuid);
	public VesselType getVesselType();
	public Ship setVesselType(VesselType type);
	public Location getLocation();
	public Location getTeleportLocation();
	public Ship setTeleportLocation(Location loc);
	public AutoPilotData getAutoPilotData();
	public Ship setAutoPilotData(AutoPilotData data);
	public ShipsStructure getStructure();
	public Ship setShipsStructure(Sign licence, Location teleport, ShipsStructure structure);
	public File getFile();
	public Ship setFile(File ship);
	public Map<OfflinePlayer, BlockVector> getBlockLocation();
	public boolean isMoving();
	public Ship setMoving(boolean check);
	public boolean isInvincible();
	public Ship setInvincible(boolean check);
	public void updateLocation(Location teleport, Sign sign);
	public boolean updateStructure();
	public boolean updateStructure(Location anyBlock);
	public boolean updateToMovingStructure(ShipsStructure structure);
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
	
	public default void setName(String name) {
		Sign sign = getSign();
		if(sign == null) {
			return;
		}
		sign.setLine(2, ChatColor.GREEN + name);
		sign.update();
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
	
	public default Ship registerBlockLocation(Player player) {
		return registerBlockLocation(player, player.getLocation());
	}
	
	public default Ship registerBlockLocation(OfflinePlayer player, Location loc) {
		int x = getLocation().getBlockX() - loc.getBlockX();
		int y = getLocation().getBlockY() - loc.getBlockY();
		int z = getLocation().getBlockZ() - loc.getBlockZ();
		BlockVector vector = new BlockVector(x, y, z, this.getLocation().getBlock());
		getBlockLocation().put(player, vector);
		return this;
	}
	
	public default void deregisterBlockLocation(OfflinePlayer player) {
		getBlockLocation().remove(player);
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

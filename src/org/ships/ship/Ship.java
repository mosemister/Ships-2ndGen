package org.ships.ship;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
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

	public Ship setOwner(OfflinePlayer var1);

	public Set<UUID> getSubPilots();

	public Ship registerPilot(UUID var1);

	public Ship deregisterPilot(UUID var1);

	public VesselType getVesselType();

	public Ship setVesselType(VesselType var1);

	public Location getLocation();

	public Location getTeleportLocation();

	public Ship setTeleportLocation(Location var1);

	public AutoPilotData getAutoPilotData();

	public Ship setAutoPilotData(AutoPilotData var1);

	public ShipsStructure getStructure();

	public Ship setShipsStructure(Sign var1, Location var2, ShipsStructure var3);

	public File getFile();

	public Ship setFile(File var1);

	public Map<OfflinePlayer, BlockVector> getBlockLocation();

	public boolean isMoving();

	public Ship setMoving(boolean var1);

	public boolean isInvincible();

	public Ship setInvincible(boolean var1);

	public void updateLocation(Location var1, Sign var2);

	public boolean updateStructure();

	public boolean updateStructure(Location var1);

	public boolean updateToMovingStructure(ShipsStructure var1);

	public void save();

	public void delete();

	public void reload();

	public boolean transform(Location var1, boolean var2);

	public boolean moveTowards(MovementMethod var1, int var2, OfflinePlayer var3, boolean var4);

	public boolean moveTowardsLocation(Location var1, int var2, OfflinePlayer var3);

	public boolean moveTowardsForcefully(MovementMethod var1, int var2, OfflinePlayer var3);

	public boolean moveTo(Location var1, OfflinePlayer var2, boolean var3);

	public boolean moveToForcefully(Location var1);

	default public Sign getSign() {
		BlockState state = this.getLocation().getBlock().getState();
		if (state instanceof Sign) {
			return (Sign) state;
		}
		return null;
	}

	default public String getName() {
		Sign sign = this.getSign();
		if (sign == null) {
			return null;
		}
		String name = sign.getLine(2);
		return ChatColor.stripColor(name);
	}

	default public void setName(String name) {
		Sign sign = this.getSign();
		if (sign == null) {
			return;
		}
		sign.setLine(2, ChatColor.GREEN + name);
		sign.update();
	}

	default public int getWaterLevel() {
		int level = 0;
		for (BlockHandler<? extends BlockState> handler : this.getStructure().getAllBlocks()) {
			for (BlockFace face : new BlockFace[] { BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN }) {
				int y;
				Block block = handler.getBlock().getRelative(face);
				if (!block.getType().equals(Material.WATER) || level >= (y = block.getY()))
					continue;
				level = y;
			}
		}
		return level;
	}

	default public int getWaterLevel(Collection<MovingBlock> blocks) {
		int level = 0;
		for (MovingBlock mBlock : blocks) {
			for (BlockFace face : new BlockFace[] { BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN }) {
				int y;
				Block block = mBlock.getMovingTo().getBlock().getRelative(face);
				if (!block.getType().equals(Material.WATER) || level >= (y = block.getY()))
					continue;
				level = y;
			}
		}
		return level;
	}

	default public Ship registerBlockLocation(Player player) {
		return this.registerBlockLocation(player, player.getLocation());
	}

	default public Ship registerBlockLocation(OfflinePlayer player, Location loc) {
		int x = this.getLocation().getBlockX() - loc.getBlockX();
		int y = this.getLocation().getBlockY() - loc.getBlockY();
		int z = this.getLocation().getBlockZ() - loc.getBlockZ();
		BlockVector vector = new BlockVector(x, y, z, this.getLocation().getBlock());
		this.getBlockLocation().put(player, vector);
		return this;
	}

	default public void deregisterBlockLocation(OfflinePlayer player) {
		this.getBlockLocation().remove(player);
	}

	default public Set<Entity> getEntities() {
		return this.getLocation().getWorld().getEntities().stream().filter(e -> {
			Block block = e.getLocation().getBlock().getRelative(BlockFace.DOWN);
			return this.getStructure().getAllBlocks().stream().anyMatch(b -> b.getBlock().equals(block));
		}).collect(Collectors.toSet());
	}

	default public boolean hasPermissionToMove(Player player) {
		if (player.hasPermission("ships." + this.getName() + ".use")) {
			return true;
		}
		if (player.hasPermission("ships." + this.getVesselType().getName() + ".use")) {
			return true;
		}
		if (player.hasPermission("ships.*.use")) {
			return true;
		}
		return false;
	}

	default public boolean moveTowards(MovementMethod move, int speed, OfflinePlayer player) {
		return this.moveTowards(move, speed, player, true);
	}

	default public boolean moveTo(Location loc, OfflinePlayer player) {
		return this.moveTo(loc, player, true);
	}
}

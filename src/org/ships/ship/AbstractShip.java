package org.ships.ship;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.ships.block.BlockStack;
import org.ships.block.MovingBlock;
import org.ships.block.Vector.BlockVector;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.blockhandler.types.Sign;
import org.ships.block.configuration.MovementInstruction;
import org.ships.block.structure.BasicStructure;
import org.ships.block.structure.MovingStructure;
import org.ships.block.structure.ShipsStructure;
import org.ships.configuration.BlockList;
import org.ships.configuration.Config;
import org.ships.configuration.Messages;
import org.ships.event.custom.ShipAboutToMoveEvent;
import org.ships.event.custom.ShipFinishedMovingEvent;
import org.ships.event.custom.ShipMovingEvent;
import org.ships.plugin.BlockHandlerNotReady;
import org.ships.plugin.Ships;
import org.ships.ship.movement.AutoPilotData;
import org.ships.ship.movement.MovementMethod;
import org.ships.ship.type.VesselType;
import org.ships.utils.Sorts;

public abstract class AbstractShip implements Ship {
	protected OfflinePlayer owner;
	protected VesselType type;
	protected Location loc;
	protected Location teleportLoc;
	protected ShipsStructure structure;
	protected File file;
	protected AutoPilotData autoPilot;
	protected Map<OfflinePlayer, BlockVector> blockVectors = new HashMap<OfflinePlayer, BlockVector>();
	protected boolean isMoving;
	protected boolean isInvicible;
	protected List<UUID> subPilots = new ArrayList<UUID>();

	public AbstractShip(VesselType type, Location loc, Location teleportLocation, OfflinePlayer player, ShipsStructure structure, File file) {
		this.type = type;
		this.loc = loc;
		this.teleportLoc = teleportLocation;
		this.owner = player;
		this.structure = structure;
		this.file = file;
	}

	@Override
	public OfflinePlayer getOwner() {
		return this.owner;
	}

	@Override
	public Ship setOwner(OfflinePlayer player) {
		this.owner = player;
		return this;
	}

	@Override
	public Set<UUID> getSubPilots() {
		return new HashSet<UUID>(this.subPilots);
	}

	@Override
	public Ship registerPilot(UUID uuid) {
		this.subPilots.add(uuid);
		return this;
	}

	@Override
	public Ship deregisterPilot(UUID uuid) {
		this.subPilots.remove(uuid);
		return this;
	}

	@Override
	public VesselType getVesselType() {
		return this.type;
	}

	@Override
	public Ship setVesselType(VesselType type) {
		this.type = type;
		return this;
	}

	@Override
	public Location getLocation() {
		return this.loc;
	}

	@Override
	public Location getTeleportLocation() {
		return this.teleportLoc;
	}

	@Override
	public Ship setTeleportLocation(Location loc) {
		this.teleportLoc = loc;
		return this;
	}

	@Override
	public AutoPilotData getAutoPilotData() {
		return this.autoPilot;
	}

	@Override
	public Ship setAutoPilotData(AutoPilotData data) {
		this.autoPilot = data;
		return this;
	}

	@Override
	public ShipsStructure getStructure() {
		return this.structure;
	}

	@Override
	public Ship setShipsStructure(org.bukkit.block.Sign licence, Location teleport, ShipsStructure structure) {
		this.loc = licence.getLocation();
		this.teleportLoc = teleport;
		this.structure = structure;
		return this;
	}

	@Override
	public File getFile() {
		return this.file;
	}

	@Override
	public Ship setFile(File file) {
		this.file = file;
		return this;
	}

	@Override
	public Map<OfflinePlayer, BlockVector> getBlockLocation() {
		return this.blockVectors;
	}

	@Override
	public boolean isMoving() {
		return this.isMoving;
	}

	@Override
	public Ship setMoving(boolean check) {
		this.isMoving = check;
		return this;
	}

	@Override
	public boolean isInvincible() {
		return this.isInvicible;
	}

	@Override
	public Ship setInvincible(boolean check) {
		this.isInvicible = check;
		return this;
	}

	@Override
	public boolean updateToMovingStructure(ShipsStructure structure) {
		boolean check = this.getStructure().getAllBlocks().stream().allMatch(b -> structure.getAllBlocks().stream().anyMatch(e -> e.getBlock().equals(b.getBlock())));
		if (check) {
			this.structure = structure;
			return true;
		}
		return false;
	}

	@Override
	public void updateLocation(Location teleport, org.bukkit.block.Sign sign) {
		Block block = sign.getBlock();
		BlockStack structure = Ships.getBaseStructure(block);
		Set<BlockHandler<? extends BlockState>> structureSet = BlockHandler.convert(structure);
		this.structure = new BasicStructure(structureSet);
		this.loc = sign.getLocation();
		this.teleportLoc = teleport;
	}

	@Override
	public boolean updateStructure() {
		return this.updateStructure(this.loc);
	}

	@Override
	public boolean updateStructure(Location anyBlock) {
		BlockStack stack = Ships.getBaseStructure(anyBlock.getBlock());
		int x = this.loc.getBlockX() - this.teleportLoc.getBlockX();
		int y = this.loc.getBlockY() - this.teleportLoc.getBlockY();
		int z = this.loc.getBlockZ() - this.teleportLoc.getBlockZ();
		if (!stack.isVaild()) {
			return false;
		}
		org.bukkit.block.Sign sign = stack.getLicence();
		this.loc = sign.getLocation();
		this.teleportLoc = this.loc.clone().add(x, y, z);
		return true;
	}

	@Override
	public boolean transform(Location loc, boolean force) {
		int x = loc.getBlockX() - loc.getBlockX();
		int y = loc.getBlockY() - loc.getBlockY();
		int z = loc.getBlockZ() - loc.getBlockZ();
		ArrayList<MovingBlock> blocks = new ArrayList<MovingBlock>();
		for (BlockHandler<? extends BlockState> blockHandler : this.getStructure().getAllBlocks()) {
			Location loc2 = blockHandler.getBlock().getRelative(x, y, z).getLocation();
			loc2.setWorld(loc.getWorld());
			blocks.add(new MovingBlock(blockHandler, loc2));
		}
		if (!force) {
			for (MovingBlock block : blocks) {
				if (!this.isBlocked(block))
					continue;
				return false;
			}
		}
		this.forceMove(MovementMethod.TELEPORT, blocks);
		return true;
	}

	@Override
	public boolean moveTowards(MovementMethod move, int speed, OfflinePlayer player, boolean fireEvents) {
		if (player == null || player.isOnline() && this.hasPermissionToMove(player.getPlayer())) {
			if (move == null) {
				if (player != null && player.isOnline()) {
					player.getPlayer().sendMessage(Ships.runShipsMessage("Can not translate block into move", true));
				}
				return false;
			}
			if (speed != 0) {
				move.setSpeed(speed);
			}
			if (fireEvents) {
				ShipAboutToMoveEvent event2 = new ShipAboutToMoveEvent(move, speed, this, player);
				Bukkit.getPluginManager().callEvent(event2);
				if (event2.isCancelled()) {
					return false;
				}
				move = event2.getMethod();
				speed = event2.getSpeed();
				player = event2.getPlayer();
			}
			if (this.safeMove(move, player, fireEvents)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean moveTowardsLocation(Location moveTo, int speed, OfflinePlayer player) {
		Block block = this.getLocation().getBlock();
		BlockData data = block.getBlockData();
		if (!(data instanceof Rotatable)) {
			return false;
		}
		Rotatable data2 = (Rotatable) data;
		MovementMethod move = MovementMethod.getMovementDirection(data2.getRotation().getOppositeFace());
		boolean value = false;
		if (this.loc.getY() == moveTo.getY()) {
			if (this.loc.getX() == moveTo.getX()) {
				if (this.loc.getZ() == moveTo.getZ()) {
					return false;
				}
				value = this.loc.getZ() > moveTo.getZ() ? (this.loc.getZ() + speed > moveTo.getZ() ? this.moveTowards(MovementMethod.MOVE_NEGATIVE_Z, 1, player) : (move.equals(MovementMethod.MOVE_NEGATIVE_Z) ? this.moveTowards(MovementMethod.MOVE_NEGATIVE_Z, speed, player) : this.moveTowards(MovementMethod.ROTATE_RIGHT, 90, player))) : (this.loc.getZ() - speed < moveTo.getZ() ? this.moveTowards(MovementMethod.MOVE_POSITIVE_Z, 1, player) : (move.equals(MovementMethod.MOVE_POSITIVE_Z) ? this.moveTowards(MovementMethod.MOVE_POSITIVE_Z, speed, player) : this.moveTowards(MovementMethod.ROTATE_RIGHT, 90, player)));
			} else {
				value = this.loc.getX() > moveTo.getX() ? (this.loc.getX() + speed > moveTo.getX() ? this.moveTowards(MovementMethod.MOVE_NEGATIVE_X, 1, player) : (move.equals(MovementMethod.MOVE_NEGATIVE_X) ? this.moveTowards(MovementMethod.MOVE_NEGATIVE_X, speed, player) : this.moveTowards(MovementMethod.ROTATE_RIGHT, 90, player))) : (this.loc.getX() - speed < moveTo.getX() ? this.moveTowards(MovementMethod.MOVE_POSITIVE_X, 1, player) : (move.equals(MovementMethod.MOVE_POSITIVE_X) ? this.moveTowards(MovementMethod.MOVE_POSITIVE_X, speed, player) : this.moveTowards(MovementMethod.ROTATE_RIGHT, 90, player)));
			}
		} else {
			value = this.loc.getY() < moveTo.getY() ? (this.loc.getY() + speed > moveTo.getY() ? this.moveTowards(MovementMethod.MOVE_UP, 1, player) : this.moveTowards(MovementMethod.MOVE_UP, speed, player)) : (this.loc.getY() - speed > moveTo.getY() ? this.moveTowards(MovementMethod.MOVE_DOWN, 1, player) : this.moveTowards(MovementMethod.MOVE_DOWN, speed, player));
		}
		return value;
	}

	@Override
	public boolean moveTowardsForcefully(MovementMethod move, int speed, OfflinePlayer player) {
		ArrayList<MovingBlock> blocks = new ArrayList<MovingBlock>();
		this.getStructure().getAllBlocks().stream().forEach(h -> {
			MovingBlock block2 = new MovingBlock(h, this, move);
			blocks.add(block2);
		});
		return this.forceMove(move, blocks);
	}

	@Override
	public boolean moveTo(Location loc, OfflinePlayer player, boolean fireEvents) {
		if (player == null || player.isOnline() && this.hasPermissionToMove(player.getPlayer())) {
			MovementMethod move = MovementMethod.TELEPORT;
			ShipAboutToMoveEvent event2 = new ShipAboutToMoveEvent(move, 1, this, player);
			Bukkit.getPluginManager().callEvent(event2);
			if (event2.isCancelled()) {
				return false;
			}
			player = event2.getPlayer();
			if (this.safeMove(move, player, fireEvents)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean moveToForcefully(Location loc) {
		return this.transform(loc, true);
	}

	protected boolean isMoveInBlock(Location loc) {
		for (Material material : this.getVesselType().getMoveInMaterials()) {
			if (!material.equals(loc.getBlock().getType()))
				continue;
			return true;
		}
		return false;
	}

	protected boolean isPartOfVessel(Location loc) {
		for (BlockHandler<? extends BlockState> block : this.getStructure().getAllBlocks()) {
			if (!block.getBlock().equals(loc.getBlock()))
				continue;
			return true;
		}
		return false;
	}

	protected boolean isBlocked(MovingBlock block) {
		Location loc = block.getMovingTo();
		Block block2 = loc.getBlock();
		if (!this.isPartOfVessel(loc)) {
			BlockList matList = BlockList.BLOCK_LIST;
			if (matList.getCurrentWith(block2.getType()).getInstruction().equals(MovementInstruction.RAM)) {
				return false;
			}
			if (this.isMoveInBlock(loc)) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	protected List<MovingBlock> sortMovingBlocks(MovementMethod move, OfflinePlayer player) {
		ArrayList<MovingBlock> blocks = new ArrayList<MovingBlock>();
		String foundInWay = null;
		ArrayList<BlockHandler<? extends BlockState>> blocks2 = new ArrayList<BlockHandler<? extends BlockState>>(this.getStructure().getAllBlocks());
		Collections.sort(blocks2, Sorts.SORT_BLOCK_PRIORITY_ON_BLOCK_HANDLER);
		for (BlockHandler<? extends BlockState> handler : blocks2) {
			MovingBlock block2 = new MovingBlock(handler, this, move);
			if (this.isBlocked(block2)) {
				if (foundInWay == null) {
					foundInWay = block2.getHandle().getBlock().getType().name();
					continue;
				}
				foundInWay = foundInWay + ", " + block2.getHandle().getBlock().getType().name();
				continue;
			}
			blocks.add(block2);
		}
		if (foundInWay != null) {
			if (player != null && Messages.isEnabled() && player.isOnline()) {
				player.getPlayer().sendMessage(Ships.runShipsMessage(Messages.getFoundInWay(foundInWay), true));
			}
			return null;
		}
		return blocks;
	}

	protected void moveEntitys(MovementMethod move) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		for (Entity entity : this.getEntities()) {
			Inventory inv = null;
			if (entity instanceof Player) {
				Inventory inv2;
				Player player = (Player) entity;
				if (config.getBoolean("Inventory.keepInventorysOpen") && !(inv2 = player.getOpenInventory().getTopInventory()).getTitle().equals("container.crafting")) {
					inv = inv2;
					player.closeInventory();
				}
			}
			Location loc = entity.getLocation();
			MovingBlock block = new MovingBlock(loc.getBlock(), this, move);
			Location loc2 = block.getMovingTo();
			double X = loc.getX() - Math.floor(loc.getX());
			double Y = loc.getY() - Math.floor(loc.getY()) + 0.5;
			double Z = loc.getZ() - Math.floor(loc.getZ());
			loc2.add(X, Y, Z);
			loc2.setPitch(loc.getPitch());
			if (move.equals(MovementMethod.ROTATE_LEFT)) {
				loc2.setYaw(loc.getYaw() + 270.0f);
			} else if (move.equals(MovementMethod.ROTATE_RIGHT)) {
				loc2.setYaw(loc.getYaw() + 90.0f);
			} else {
				loc2.setYaw(loc.getYaw());
			}
			entity.teleport(loc2);
			if (!(entity instanceof Player))
				continue;
			Player player = (Player) entity;
			if (!config.getBoolean("Inventory.keepInventorysOpen") || inv == null)
				continue;
			player.openInventory(inv);
		}
	}

	protected boolean forceMove(MovementMethod move, Collection<MovingBlock> blocks) {
		int A;
		MovingBlock block;
		ArrayList<MovingBlock> blocks1 = new ArrayList<MovingBlock>(blocks);
		this.moveEntitys(move);
		int level = this.getWaterLevel(blocks);
		for (A = 0; A < blocks.size(); ++A) {
			block = blocks1.get(A);
			try {
				block.getHandle().save(true);
			} catch (BlockHandlerNotReady e) {
				e.printStackTrace();
				return false;
			}
			if (block.getBlock().getLocation().getY() > level) {
				block.getHandle().remove(Material.AIR);
				continue;
			}
			block.getHandle().remove(Material.WATER);
		}
		for (A = blocks.size() - 1; A >= 0; --A) {
			block = blocks1.get(A);
			block.getHandle().setBlock(block.getMovingTo().getBlock());
			block.getHandle().rotate(move);
			block.getHandle().applyBlockData();
		}
		for (A = blocks.size() - 1; A >= 0; --A) {
			block = blocks1.get(A);
			block.getHandle().apply(true);
		}
		MovingBlock mBlock = new MovingBlock(this.getTeleportLocation().getBlock(), this, move);
		this.updateStructure();
		org.bukkit.block.Sign sign = null;
		Optional<BlockHandler<? extends BlockState>> opSign = this.getStructure().getAllBlocks().stream().filter(b -> b instanceof Sign).filter(b -> ((org.bukkit.block.Sign) b.getState()).getLine(0).equals(ChatColor.YELLOW + "[Ships]")).findAny();
		if (opSign.isPresent()) {
			sign = (org.bukkit.block.Sign) opSign.get().getBlock().getState();
		} else {
			new IOException("Could not refind licence sign").printStackTrace();
		}
		this.updateLocation(mBlock.getMovingTo(), sign);
		ShipFinishedMovingEvent event = new ShipFinishedMovingEvent(this);
		Bukkit.getServer().getPluginManager().callEvent(event);
		return true;
	}

	protected boolean safeMove(MovementMethod move, OfflinePlayer player, boolean sendEvent) {
		List<MovingBlock> blocks = this.sortMovingBlocks(move, player);
		if (blocks == null) {
			return false;
		}
		if (this.getVesselType().attemptToMove(this, move, blocks, player)) {
			MovingStructure structure = new MovingStructure(blocks);
			this.updateToMovingStructure(structure);
			if (sendEvent) {
				ShipMovingEvent event = new ShipMovingEvent(player, this, move, structure);
				Bukkit.getPluginManager().callEvent(event);
				if (event.isCancelled()) {
					return false;
				}
				structure = event.getStructure();
			}
			this.forceMove(move, structure.getMovingBlocksOriginalOrder());
			return true;
		}
		return false;
	}
}

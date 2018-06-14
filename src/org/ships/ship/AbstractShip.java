package org.ships.ship;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
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
import org.ships.block.configuration.MovementInstruction;
import org.ships.block.structure.BasicStructure;
import org.ships.block.structure.MovingStructure;
import org.ships.block.structure.ShipsStructure;
import org.ships.configuration.Config;
import org.ships.configuration.MaterialsList;
import org.ships.configuration.Messages;
import org.ships.event.custom.ShipAboutToMoveEvent;
import org.ships.event.custom.ShipFinishedMovingEvent;
import org.ships.event.custom.ShipMovingEvent;
import org.ships.plugin.Ships;
import org.ships.ship.movement.AutoPilotData;
import org.ships.ship.movement.MovementMethod;
import org.ships.ship.type.VesselType;

public abstract class AbstractShip implements Ship {

	protected OfflinePlayer owner;
	protected VesselType type;
	protected Location loc;
	protected Location teleportLoc;
	protected ShipsStructure structure;
	protected File file;
	protected AutoPilotData autoPilot;
	protected Map<OfflinePlayer, BlockVector> blockVectors = new HashMap<>();
	protected boolean isMoving;
	protected boolean isInvicible;
	protected List<UUID> subPilots = new ArrayList<>();
	
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
		return owner;
	}
	
	@Override
	public Ship setOwner(OfflinePlayer player) {
		this.owner = player;
		return this;
	}
	
	@Override
	public Set<UUID> getSubPilots() {
		return new HashSet<>(subPilots);
	}
	
	@Override
	public Ship registerPilot(UUID uuid) {
		subPilots.add(uuid);
		return this;
	}
	
	@Override
	public Ship deregisterPilot(UUID uuid) {
		subPilots.remove(uuid);
		return this;
	}

	@Override
	public VesselType getVesselType() {
		return type;
	}
	
	@Override
	public Ship setVesselType(VesselType type) {
		this.type = type;
		return this;
	}

	@Override
	public Location getLocation() {
		return loc;
	}

	@Override
	public Location getTeleportLocation() {
		return teleportLoc;
	}
	
	@Override
	public Ship setTeleportLocation(Location loc) {
		teleportLoc = loc;
		return this;
	}

	@Override
	public AutoPilotData getAutoPilotData() {
		return autoPilot;
	}
	
	@Override
	public Ship setAutoPilotData(AutoPilotData data) {
		this.autoPilot = data;
		return this;
	}

	@Override
	public ShipsStructure getStructure() {
		return structure;
	}
	
	@Override
	public Ship setShipsStructure(Sign licence, Location teleport, ShipsStructure structure) {
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
		return isMoving;
	}
	
	@Override
	public Ship setMoving(boolean check) {
		this.isMoving = check;
		return this;
	}

	@Override
	public boolean isInvincible() {
		return isInvicible;
	}
	
	@Override
	public Ship setInvincible(boolean check) {
		this.isInvicible = check;
		return this;
	}
	
	@Override
	public boolean updateToMovingStructure(ShipsStructure structure) {
		boolean check = getStructure().getAllBlocks().stream().allMatch(b -> structure.getAllBlocks().stream().anyMatch(e -> e.getBlock().equals(b.getBlock())));
		if(check) {
			this.structure = structure;
			return true;
		}
		return false;
	}

	@Override
	public void updateLocation(Location teleport, Sign sign) {
		structure = new BasicStructure(BlockHandler.convert(Ships.getBaseStructure(sign.getBlock())));
		loc = sign.getLocation();
		teleportLoc = teleport;
	}

	@Override
	public boolean updateStructure() {
		return updateStructure(loc);
	}
	
	@Override
	public boolean updateStructure(Location anyBlock) {
		BlockStack stack = Ships.getBaseStructure(anyBlock.getBlock());
		int x = loc.getBlockX() - teleportLoc.getBlockX();
		int y = loc.getBlockY() - teleportLoc.getBlockY();
		int z = loc.getBlockZ() - teleportLoc.getBlockZ();
		if(!stack.isVaild()) {
			return false;
		}
		Sign sign = stack.getLicence();
		loc = sign.getLocation();
		teleportLoc = loc.clone().add(x, y, z);
		return true;
	}

	@Override
	public boolean transform(Location loc, boolean force) {
		int x = loc.getBlockX() - loc.getBlockX();
		int y = loc.getBlockY() - loc.getBlockY();
		int z = loc.getBlockZ() - loc.getBlockZ();
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		for (BlockHandler<? extends BlockState> blockHandler : getStructure().getAllBlocks()) {
			Location loc2 = blockHandler.getBlock().getRelative(x, y, z).getLocation();
			loc2.setWorld(loc.getWorld());
			blocks.add(new MovingBlock(blockHandler, loc2));
		}

		if (!force) {
			for (MovingBlock block : blocks) {
				if (isBlocked(block)) {
					return false;
				}
			}
		}
		forceMove(MovementMethod.TELEPORT, blocks);
		return true;
	}

	@Override
	public boolean moveTowards(MovementMethod move, int speed, OfflinePlayer player, boolean fireEvents) {
		if ((player == null) || (player.isOnline() && hasPermissionToMove(player.getPlayer()))) {
			if (move == null) {
				if (player != null) {
					if (player.isOnline()) {
						player.getPlayer()
								.sendMessage(Ships.runShipsMessage("Can not translate block into move", true));
					}
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
				} else {
					move = event2.getMethod();
					speed = event2.getSpeed();
					player = event2.getPlayer();
				}
			}
			if (safeMove(move, player, fireEvents)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean moveTowardsLocation(Location moveTo, int speed, OfflinePlayer player) {
		Block block = getLocation().getBlock();
		BlockData data = block.getBlockData();
		if(!(data instanceof Rotatable)) {
			return false;
		}
		Rotatable data2 = (Rotatable)data;
		MovementMethod move = MovementMethod.getMovementDirection(data2.getRotation().getOppositeFace());
		boolean value = false;
		if (loc.getY() == moveTo.getY()) {
			if (loc.getX() == moveTo.getX()) {
				// try Z
				if (loc.getZ() == moveTo.getZ()) {
					return false;
				}
				if (loc.getZ() > moveTo.getZ()) {
					if ((loc.getZ() + speed) > moveTo.getZ()) {
						value = moveTowards(MovementMethod.MOVE_NEGATIVE_Z, 1, player);
					} else {
						if (move.equals(MovementMethod.MOVE_NEGATIVE_Z)) {
							value = moveTowards(MovementMethod.MOVE_NEGATIVE_Z, speed, player);
						} else {
							value = moveTowards(MovementMethod.ROTATE_RIGHT, 90, player);
						}
					}
				} else {
					if ((loc.getZ() - speed) < moveTo.getZ()) {
						value = moveTowards(MovementMethod.MOVE_POSITIVE_Z, 1, player);
					} else {
						if (move.equals(MovementMethod.MOVE_POSITIVE_Z)) {
							value = moveTowards(MovementMethod.MOVE_POSITIVE_Z, speed, player);
						} else {
							value = moveTowards(MovementMethod.ROTATE_RIGHT, 90, player);
						}
					}
				}
			} else {
				// try X
				if (loc.getX() > moveTo.getX()) {
					if ((loc.getX() + speed) > moveTo.getX()) {
						value = moveTowards(MovementMethod.MOVE_NEGATIVE_X, 1, player);
					} else {
						if (move.equals(MovementMethod.MOVE_NEGATIVE_X)) {
							value = moveTowards(MovementMethod.MOVE_NEGATIVE_X, speed, player);
						} else {
							value = moveTowards(MovementMethod.ROTATE_RIGHT, 90, player);
						}
					}
				} else {
					if ((loc.getX() - speed) < moveTo.getX()) {
						value = moveTowards(MovementMethod.MOVE_POSITIVE_X, 1, player);
					} else {
						if (move.equals(MovementMethod.MOVE_POSITIVE_X)) {
							value = moveTowards(MovementMethod.MOVE_POSITIVE_X, speed, player);
						} else {
							value = moveTowards(MovementMethod.ROTATE_RIGHT, 90, player);
						}
					}
				}
			}
		} else {
			// try Y
			if (loc.getY() < moveTo.getY()) {
				if ((loc.getY() + speed) > moveTo.getY()) {
					value = moveTowards(MovementMethod.MOVE_UP, 1, player);
				} else {
					value = moveTowards(MovementMethod.MOVE_UP, speed, player);
				}
			} else {
				if ((loc.getY() - speed) > moveTo.getY()) {
					value = moveTowards(MovementMethod.MOVE_DOWN, 1, player);
				} else {
					value = moveTowards(MovementMethod.MOVE_DOWN, speed, player);
				}
			}
		}
		return value;
	}

	@Override
	public boolean moveTowardsForcefully(MovementMethod move, int speed, OfflinePlayer player) {
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		for (BlockHandler<? extends BlockState> block : getStructure().getPriorityBlocks()) {
			MovingBlock block2 = new MovingBlock(block, this, move);
			blocks.add(block2);
		}
		for (BlockHandler<? extends BlockState> block : getStructure().getSpecialBlocks()) {
			MovingBlock block2 = new MovingBlock(block, this, move);
			blocks.add(block2);
		}
		for (BlockHandler<? extends BlockState> block : getStructure().getStandardBlocks()) {
			MovingBlock block2 = new MovingBlock(block, this, move);
			blocks.add(block2);
		}
		for (BlockHandler<? extends BlockState> block : getStructure().getAirBlocks()) {
			MovingBlock block2 = new MovingBlock(block, this, move);
			blocks.add(block2);
		}
		return forceMove(move, blocks);
	}

	@Override
	public boolean moveTo(Location loc, OfflinePlayer player, boolean fireEvents) {
		if ((player == null) || (player.isOnline() && hasPermissionToMove(player.getPlayer()))) {
			MovementMethod move = MovementMethod.TELEPORT;
			ShipAboutToMoveEvent event2 = new ShipAboutToMoveEvent(move, 1, this, player);
			Bukkit.getPluginManager().callEvent(event2);
			if (event2.isCancelled()) {
				return false;
			} else {
				player = event2.getPlayer();
			}
			if (safeMove(move, player, fireEvents)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean moveToForcefully(Location loc) {
		return transform(loc, true);
	}
	
	private boolean isMoveInBlock(Location loc) {
		for (Material material : this.getVesselType().getMoveInMaterials()) {
			if (material.equals(loc.getBlock().getType())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isPartOfVessel(Location loc) {
		for (BlockHandler<? extends BlockState> block : getStructure().getAllBlocks()) {
			if (block.getBlock().equals(loc.getBlock())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isBlocked(MovingBlock block) {
		Location loc = block.getMovingTo();
		Block block2 = loc.getBlock();
		if (!isPartOfVessel(loc)) {
			MaterialsList matList = MaterialsList.getMaterialsList();
			if (matList.contains(block2.getType(), MovementInstruction.RAM)) {
				return false;
			} else if (isMoveInBlock(loc)) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}
	
	private List<MovingBlock> sortMovingBlocks(MovementMethod move, OfflinePlayer player) {
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		for (BlockHandler<? extends BlockState> block : getStructure().getPriorityBlocks()) {
			MovingBlock block2 = new MovingBlock(block, this, move);
			if (isBlocked(block2)) {
				if (player != null) {
					if (Messages.isEnabled()) {
						if (player.isOnline()) {
							player.getPlayer().sendMessage(Ships.runShipsMessage(
									Messages.getFoundInWay(block2.getMovingTo().getBlock().getType().name()), true));
						}
					}
				}
				return null;
			} else {
				blocks.add(block2);
			}
		}
		for (BlockHandler<? extends BlockState> block : getStructure().getSpecialBlocks()) {
			MovingBlock block2 = new MovingBlock(block, this, move);
			if (isBlocked(block2)) {
				if (player != null) {
					if (Messages.isEnabled()) {
						if (player.isOnline()) {
							player.getPlayer().sendMessage(Ships.runShipsMessage(
									Messages.getFoundInWay(block2.getMovingTo().getBlock().getType().name()), true));
						}
					}
				}
				return null;
			} else {
				blocks.add(block2);
			}
		}
		for (BlockHandler<? extends BlockState> block : getStructure().getStandardBlocks()) {
			MovingBlock block2 = new MovingBlock(block, this, move);
			if (isBlocked(block2)) {
				if (player != null) {
					if (Messages.isEnabled()) {
						if (player.isOnline()) {
							player.getPlayer().sendMessage(Ships.runShipsMessage(
									Messages.getFoundInWay(block2.getMovingTo().getBlock().getType().name()), true));
						}
					}
				}
				return null;
			} else {
				blocks.add(block2);
			}
		}
		for (BlockHandler<? extends BlockState> block : getStructure().getAirBlocks()) {
			MovingBlock block2 = new MovingBlock(block, this, move);
			if (isBlocked(block2)) {
				if (player != null) {
					if (Messages.isEnabled()) {
						if (player.isOnline()) {
							player.getPlayer().sendMessage(Ships.runShipsMessage(
									Messages.getFoundInWay(block2.getMovingTo().getBlock().getType().name()), true));
						}
					}
				}
				return null;
			} else {
				blocks.add(block2);
			}
		}
		return blocks;
	}
	
	private void moveEntitys(MovementMethod move) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		for (Entity entity : getEntities()) {
			Inventory inv = null;
			if (entity instanceof Player) {
				Player player = (Player) entity;
				if (config.getBoolean("Inventory.keepInventorysOpen")) {
					Inventory inv2 = player.getOpenInventory().getTopInventory();
					if (!inv2.getTitle().equals("container.crafting")) {
						inv = inv2;
						player.closeInventory();
					}
				}
			}
			Location loc = entity.getLocation();
			MovingBlock block = new MovingBlock(loc.getBlock(), this, move);
			Location loc2 = block.getMovingTo();
			double X = loc.getX() - Math.floor(loc.getX());
			double Y = loc.getY() - Math.floor(loc.getY());
			double Z = loc.getZ() - Math.floor(loc.getZ());
			loc2.add(X, Y, Z);
			loc2.setPitch(loc.getPitch());
			if (move.equals(MovementMethod.ROTATE_LEFT)) {
				loc2.setYaw(loc.getYaw() + 270);
			} else if (move.equals(MovementMethod.ROTATE_RIGHT)) {
				loc2.setYaw(loc.getYaw() + 90);
			} else {
				loc2.setYaw(loc.getYaw());
			}
			entity.teleport(loc2);
			if (entity instanceof Player) {
				Player player = (Player) entity;
				if (config.getBoolean("Inventory.keepInventorysOpen")) {
					if (inv != null) {
						player.openInventory(inv);
					}
				}
			}
		}
	}
	
	/*private void setInventory(MovingBlock block) {
		BlockHandler sBlock = block.getHandle();
		Block nBlock = block.getMovingTo().getBlock();
		if(sBlock instanceof InventoryHandler) {
			InventoryHandler handler = (InventoryHandler) sBlock;
			handler.applyInventory();
		}
		/*if (nBlock.getState() instanceof Furnace) {
			Furnace furn = (Furnace) nBlock.getState();
			FurnaceInventory inv = furn.getInventory();
			Map<Integer, ItemStack> stack = sBlock.getItems();
			for (Entry<Integer, ItemStack> entry : stack.entrySet()) {
				if (entry.getKey() == 1) {
					inv.setFuel(entry.getValue());
				}
				if (entry.getKey() == 2) {
					inv.setResult(entry.getValue());
				}
				if (entry.getKey() == 3) {
					inv.setSmelting(entry.getValue());
				}
			}
		}
		if (nBlock.getState() instanceof Chest) {
			Chest chest = (Chest) nBlock.getState();
			Inventory inv = chest.getBlockInventory();
			for (Entry<Integer, ItemStack> entry : sBlock.getItems().entrySet()) {
				int A = entry.getKey();
				if (A < inv.getSize()) {
					inv.setItem(A, entry.getValue());
				}
			}
		}
		if (nBlock.getState() instanceof Hopper) {
			Hopper hop = (Hopper) nBlock.getState();
			Inventory inv = hop.getInventory();
			for (Entry<Integer, ItemStack> entry : sBlock.getItems().entrySet()) {
				inv.setItem(entry.getKey(), entry.getValue());
			}
		}
		if (nBlock.getState() instanceof Dropper) {
			Dropper drop = (Dropper) nBlock.getState();
			Inventory inv = drop.getInventory();
			for (Entry<Integer, ItemStack> entry : sBlock.getItems().entrySet()) {
				inv.setItem(entry.getKey(), entry.getValue());
			}
		}
		if (nBlock.getState() instanceof Dispenser) {
			Dispenser disp = (Dispenser) nBlock.getState();
			Inventory inv = disp.getInventory();
			for (Entry<Integer, ItemStack> entry : sBlock.getItems().entrySet()) {
				inv.setItem(entry.getKey(), entry.getValue());
			}
		}
		if (nBlock.getState() instanceof Sign) {
			Sign sign = (Sign) nBlock.getState();
			sign.setLine(0, sBlock.getLine(1));
			sign.setLine(1, sBlock.getLine(2));
			sign.setLine(2, sBlock.getLine(3));
			sign.setLine(3, sBlock.getLine(4));
			sign.update();
			if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")) {
				SIGN = nBlock;
				updateLocation(getTeleportLocation(), sign);
				YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
				if (config.getBoolean("Signs.ForceUsernameOnLicenceSign")) {
					sign.setLine(3, ChatColor.GREEN + this.getOwner().getName());
				}
			}
		}*/
		/*if(sBlock instanceof TextHandler) {
			TextHandler handler = (TextHandler)sBlock;
			handler.applyText();
			Sign sign = (Sign)nBlock.getState();
			if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")) {
				updateLocation(getTeleportLocation(), sign);
				YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
				if (config.getBoolean("Signs.ForceUsernameOnLicenceSign")) {
					sign.setLine(3, ChatColor.GREEN + this.getOwner().getName());
				}
			}
		}
	}*/
		
	private boolean forceMove(MovementMethod move, Collection<MovingBlock> blocks) {
		List<MovingBlock> blocks1 = new ArrayList<>(blocks);
		// remove all blocks (priority first)
		for (int A = 0; A < blocks.size(); A++) {
			MovingBlock block = blocks1.get(A);
			if (block.getBlock().getLocation().getY() > getWaterLevel(blocks)) {
				block.getHandle().remove(Material.AIR);
				/*if (block.getSpecialBlock() != null) {
					block.getSpecialBlock().removeBlock(false);
				} else {
					block.getBlock().setType(Material.AIR);
				}*/
			} else {
				block.getHandle().remove(Material.WATER);
				/*if (block.getSpecialBlock() != null) {
					block.getSpecialBlock().removeBlock(true);
				} else {
					block.getBlock().setType(Material.WATER);
				}*/
			}
		}
		// place all blocks (priority last)
		for (int A = blocks.size() - 1; A >= 0; A--) {
			MovingBlock block = blocks1.get(A);
			/*Block bBlock = block.getMovingTo().getBlock();
			if ((move.equals(MovementMethod.ROTATE_LEFT) || (move.equals(MovementMethod.ROTATE_RIGHT)))) {
				
				byte data = BlockConverter.convertRotation(move, block, block.getData());
				bBlock.setTypeIdAndData(block.getId(), data, false);
				if (block.getSpecialBlock() != null) {
					if (block.getSpecialBlock().getType().equals("door")) {
						bBlock.getRelative(0, -1, 0).setTypeIdAndData(block.getId(), data, false);
					}
				}
			} else {
				bBlock.setTypeIdAndData(block.getId(), block.getData(), false);
				if (block.getSpecialBlock() != null) {
					if (block.getSpecialBlock().getType().equals("door")) {
						bBlock.getRelative(0, -1, 0).setTypeIdAndData(block.getId(), block.getData(), false);
					}
				}
			}
			if (block.getSpecialBlock() != null) {
				setInventory(block);
			}*/
			block.getHandle().rotate(move);
		}
		MovingBlock mBlock = new MovingBlock(getTeleportLocation().getBlock(), this, move);
		//TELEPORTLOCATION = mBlock.getMovingTo();
		updateLocation(mBlock.getMovingTo(), getSign());
		moveEntitys(move);
		updateStructure();
		ShipFinishedMovingEvent event = new ShipFinishedMovingEvent(this);
		Bukkit.getServer().getPluginManager().callEvent(event);
		return true;
	}
	
	private boolean safeMove(MovementMethod move, OfflinePlayer player, boolean sendEvent) {
		List<MovingBlock> blocks = sortMovingBlocks(move, player);
		if (blocks == null) {
			return false;
		}
		//YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
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
			forceMove(move, structure.getMovingBlocks());
			return true;
		}
		return false;
	}

}

package MoseShipsBukkit.StillShip.Vessel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.block.Sign;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.cnaude.chairs.api.ChairsAPI;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.Events.ShipAboutToMoveEvent;
import MoseShipsBukkit.Events.ShipMovingEvent;
import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.MovingShip.MovingBlock;
import MoseShipsBukkit.MovingShip.MovingStructure;
import MoseShipsBukkit.ShipsTypes.VesselType;
import MoseShipsBukkit.StillShip.ShipsStructure;
import MoseShipsBukkit.StillShip.SpecialBlock;
import MoseShipsBukkit.Utils.BlockConverter;
import MoseShipsBukkit.Utils.ConfigLinks.Config;
import MoseShipsBukkit.Utils.ConfigLinks.MaterialsList;
import MoseShipsBukkit.Utils.ConfigLinks.Messages;
import MoseShipsBukkit.Utils.Exceptions.InvalidSignException;
import MoseShipsBukkit.Utils.OtherPlugins.OtherPlugins;

public class MovableVessel extends ProtectedVessel{

	MovableVessel(Sign sign, OfflinePlayer owner, Location teleport) throws InvalidSignException{
		super(sign, owner, teleport);
	}
	
	MovableVessel(Sign sign, String name, VesselType type, Player player) {
		super(sign, name, type, player);
	}
	
	MovableVessel(Sign sign, String name, VesselType type, OfflinePlayer player, Location loc){
		super(sign, name, type, player, loc);
	}
	
	private List<MovingBlock> getTeleportStructure(Location loc){
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		List<Block> structure = getStructure().getAllBlocks();
		Location loc2 = getLocation();
		for(Block block : structure){
			int X = (int)(block.getX() - loc2.getX());
			int Y = (int)(block.getY() - loc2.getY());
			int Z = (int)(block.getZ() - loc2.getZ());
			Block teleport = loc.getBlock().getRelative(X, Y, Z);
			MovingBlock block2 = new MovingBlock(teleport, this, MovementMethod.TELEPORT);
			blocks.add(block2);
		}
		return blocks;
	}
	
	private boolean isMoveInBlock(Location loc){
		for(Material material : this.getVesselType().getMoveInMaterials()){
			if (material.equals(loc.getBlock().getType())){
				return true;
			}
		}
		return false;
	}
	
	private boolean isPartOfVessel(Location loc){
		ShipsStructure str = STRUCTURE;
		for (Block block : str.getAllBlocks()){
			if (block.getLocation().equals(loc)){
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	private boolean isBlocked(MovingBlock block){
		Location loc = block.getMovingTo();
		Block block2 = loc.getBlock();
		if (!isPartOfVessel(loc)){
			MaterialsList matList = MaterialsList.getMaterialsList();
			if (matList.contains(block2.getType(), block2.getData(), false)){
				return false;
			}else if (isMoveInBlock(loc)){
				return false;
			}
		}else{
			return false;
		}
		return true;
	}
	
	private boolean factionSupport(YamlConfiguration config, MovingBlock block, @Nullable OfflinePlayer player){
		ConsoleCommandSender console = Bukkit.getConsoleSender();
		if (config.getBoolean("FactionsSupport.enabled")){
			if (OtherPlugins.isFactionsLoaded()){
				if (OtherPlugins.isLocationOnFactionsLand(block.getMovingTo())){
					if (player != null){
						if (Messages.isEnabled()){
							if (player.isOnline()){
								player.getPlayer().sendMessage(Ships.runShipsMessage("Faction in way.", true));
							}
						}
					}
					return true;
				}
			}else{
				console.sendMessage(Ships.runShipsMessage("Ships has Factions enabled but can not hook into it.", true));
			}
		}
		return false;
	}
	
	public int getWaterLevel(List<MovingBlock> blocks){
		int maxValue = 0;
		for(MovingBlock block : blocks){
			Block block2 = block.getMovingTo().getBlock();
			if ((block2.getType().equals(Material.STATIONARY_WATER)) || (block2.getType().equals(Material.WATER))){
				if (block2.getY() > maxValue){
					maxValue = block2.getY();
				}
			}
		}
		return maxValue;
	}
	
	private boolean worldGuardSupport(YamlConfiguration config, MovingBlock block, @Nullable OfflinePlayer player){
		ConsoleCommandSender console = Bukkit.getConsoleSender();
		if (config.getBoolean("WorldGuardSupport.enabled")){
			if (OtherPlugins.isWorldGuardLoaded()){
				if (OtherPlugins.isLocationInWorldGuardRegion(block.getMovingTo())){
					if (player != null){
						if (Messages.isEnabled()){
							if (player.isOnline()){
								player.getPlayer().sendMessage(Ships.runShipsMessage("Region in way.", true));
							}
						}
					}
					return true;
				}
			}else{
				console.sendMessage(Ships.runShipsMessage("Ships has WorldGuard enabled but can not hook into it.", true));
			}
		}
		return false;
	}
	
	private boolean worldBorderSupport(YamlConfiguration config, MovingBlock block, @Nullable OfflinePlayer player){
		ConsoleCommandSender console = Bukkit.getConsoleSender();
		if (config.getBoolean("WorldBorderSupport.enabled")){
			if (OtherPlugins.isWorldBorderLoaded()){
				if (OtherPlugins.isLocationOutOfWorldBorder(block.getMovingTo())){
					if (player != null){
						if (Messages.isEnabled()){
							if (player.isOnline()){
								player.getPlayer().sendMessage(Ships.runShipsMessage("Edge of map.", true));
							}
						}
					}
					return true;
				}
			}else{
				console.sendMessage(Ships.runShipsMessage("Ships has WorldBorder enabled but can not hook into it.", true));
			}
		}
		return false;
	}
	
	private boolean griefProtectionSupport(YamlConfiguration config, MovingBlock block, @Nullable OfflinePlayer player){
		if (config.getBoolean("GriefPreventionPluginSupport.enabled")){
			if (OtherPlugins.isGriefPreventionLoaded()){
				if (OtherPlugins.isLocationInGriefPreventionClaim(block.getMovingTo(), this)){
					if (player != null){
						if (Messages.isEnabled()){
							if (player.isOnline()){
								player.getPlayer().sendMessage(Ships.runShipsMessage("Claim in way.", true));
							}
						}
					}
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean townySupport(YamlConfiguration config, MovingBlock block, @Nullable OfflinePlayer player){
		ConsoleCommandSender console = Bukkit.getConsoleSender();
		if (config.getBoolean("TownySupport.enabled")){
			if (OtherPlugins.isTownyLoaded()){
				if (OtherPlugins.isTownyHookLoaded()){
					if (OtherPlugins.isLocationInTownyLand(block.getMovingTo())){
						if (player != null){
							if (Messages.isEnabled()){
								if (player.isOnline()){
									player.getPlayer().sendMessage(Ships.runShipsMessage("Town in way.", true));
								}
							}
						}
						return true;
					}
				}else{
					console.sendMessage(Ships.runShipsMessage("Needs ShipsTownyHook.jar for compatibility with Towny. Download at dev.bukkit.org/bukkit-plugins/ships/files/.", true));
				}
			}else{
				console.sendMessage(Ships.runShipsMessage("Ships has Towny enabled but can not hook into it.", true));
			}
		}
		return false;
	}
	
	private boolean hasMovePermissions(@Nullable OfflinePlayer player){
		if (player == null){
			return true;
		}else if (player.isOnline()){
			if (player.getPlayer().hasPermission("ships." + getName() + ".use")){
				return true;
			}else if (player.getPlayer().hasPermission("ships." + getVesselType().getName() + ".use")){
				return true;
			}else if (player.getPlayer().hasPermission("ships.*.use")){
				return true;
			}
		}
		return false;
	}
	
	private List<MovingBlock> sortMovingBlocks(MovementMethod move, @Nullable OfflinePlayer player){
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		for (Block block : STRUCTURE.getPriorityBlocks()){
			MovingBlock block2 = new MovingBlock(block, this, move);
			if (isBlocked(block2)){
				if (player != null){
					if (Messages.isEnabled()){
						if (player.isOnline()){
							player.getPlayer().sendMessage(Ships.runShipsMessage(Messages.getFoundInWay(block2.getMovingTo().getBlock().getType().name()), true));
						}
					}
				}
				return null;
			}else{
				blocks.add(block2);
			}
		}
		for (SpecialBlock block : STRUCTURE.getSpecialBlocks()){
			MovingBlock block2 = new MovingBlock(block, this, move);
			if (isBlocked(block2)){
				if (player != null){
					if (Messages.isEnabled()){
						if (player.isOnline()){
							player.getPlayer().sendMessage(Ships.runShipsMessage(Messages.getFoundInWay(block2.getMovingTo().getBlock().getType().name()), true));
						}
					}
				}
				return null;
			}else{
				blocks.add(block2);
			}
		}
		for (Block block : STRUCTURE.getStandardBlocks()){
			MovingBlock block2 = new MovingBlock(block, this, move);
			if (isBlocked(block2)){
				if (player != null){
					if (Messages.isEnabled()){
						if (player.isOnline()){
							player.getPlayer().sendMessage(Ships.runShipsMessage(Messages.getFoundInWay(block2.getMovingTo().getBlock().getType().name()), true));
						}
					}
				}
				return null;
			}else{
				blocks.add(block2);
			}
		}
		for (Block block : STRUCTURE.getAirBlocks()){
			MovingBlock block2 = new MovingBlock(block, this, move);
			if (isBlocked(block2)){
				if (player != null){
					if (Messages.isEnabled()){
						if (player.isOnline()){
							player.getPlayer().sendMessage(Ships.runShipsMessage(Messages.getFoundInWay(block2.getMovingTo().getBlock().getType().name()), true));
						}
					}
				}
				return null;
			}else{
				blocks.add(block2);
			}
		}
		return blocks;
	}
	
	private void moveEntitys(MovementMethod move){
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		for	(Entity entity : getEntities()){
			Inventory inv = null;
			boolean isSitting = false;
			if (entity instanceof Player){
				Player player = (Player)entity;
				if (config.getBoolean("Inventory.keepInventorysOpen")){
					Inventory inv2 = player.getOpenInventory().getTopInventory();
					if (!inv2.getTitle().equals("container.crafting")){
						inv = inv2;
						player.closeInventory();
					}
				}
				if (config.getBoolean("ChairsReloadedSupport.enabled")){
					if (OtherPlugins.isChairsLoaded()){
						isSitting = ChairsAPI.isSitting(player);
					}else{
						Bukkit.getConsoleSender().sendMessage(Ships.runShipsMessage("Chairs support has been enabled but can not find ChairsReloaded", true));
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
			loc2.setYaw(loc.getYaw());
			entity.teleport(loc2);
			if (entity instanceof Player){
				Player player = (Player)entity;
				if (config.getBoolean("Inventory.keepInventorysOpen")){
					if (inv != null){
						player.openInventory(inv);
					}
				}
				if (isSitting){
					OtherPlugins.sit(player, loc2);
				}
			}
		}
	}
	
	private void setInventory(MovingBlock block){
		SpecialBlock sBlock = block.getSpecialBlock();
		Block nBlock = block.getMovingTo().getBlock();
		if (nBlock.getState() instanceof Furnace){
			Furnace furn = (Furnace)nBlock.getState();
			FurnaceInventory inv = furn.getInventory();
			Map<Integer, ItemStack> stack = sBlock.getItems();
			for(Entry<Integer, ItemStack> entry : stack.entrySet()){
				if (entry.getKey() == 1){
					inv.setFuel(entry.getValue());
				}
				if (entry.getKey() == 2){
					inv.setResult(entry.getValue());
				}
				if (entry.getKey() == 3){
					inv.setSmelting(entry.getValue());
				}
			}
		}
		if (nBlock.getState() instanceof Chest){
			Chest chest = (Chest) nBlock.getState();
			Inventory inv = chest.getInventory();
			for(Entry<Integer, ItemStack> entry : sBlock.getItems().entrySet()){
				if (sBlock.isRightChest()){
					int A = entry.getKey();
					if ((A > 26) && (A <= 64)){
						inv.setItem(A - 27, entry.getValue());
					}
				}else{
					int A = entry.getKey(); 
					if (A <= 26){
						inv.setItem(A, entry.getValue());
					}
				}
			}
		}
		if (nBlock.getState() instanceof Hopper){
			Hopper hop = (Hopper)nBlock.getState();
			Inventory inv = hop.getInventory();
			for(Entry<Integer, ItemStack> entry : sBlock.getItems().entrySet()){
				inv.setItem(entry.getKey(), entry.getValue());
			}
		}
		if (nBlock.getState() instanceof Dropper){
			Dropper drop = (Dropper)nBlock.getState();
			Inventory inv = drop.getInventory();
			for(Entry<Integer, ItemStack> entry : sBlock.getItems().entrySet()){
				inv.setItem(entry.getKey(), entry.getValue());
			}
		}
		if (nBlock.getState() instanceof Dispenser){
			Dispenser disp = (Dispenser)nBlock.getState();
			Inventory inv = disp.getInventory();
			for(Entry<Integer, ItemStack> entry : sBlock.getItems().entrySet()){
				inv.setItem(entry.getKey(), entry.getValue());
			}
		}
		if (nBlock.getState() instanceof Sign){
			Sign sign = (Sign) nBlock.getState();
			sign.setLine(0, sBlock.getLine(1));
			sign.setLine(1, sBlock.getLine(2));
			sign.setLine(2, sBlock.getLine(3));
			sign.setLine(3, sBlock.getLine(4));
			sign.update();
			if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")){
				SIGN = sign;
				updateLocation(getTeleportLocation(), sign);
				YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
				if (config.getBoolean("Signs.ForceUsernameOnLicenceSign")){
					sign.setLine(3, ChatColor.GREEN + this.getOwner().getName());
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private void forceMove(MovementMethod move, List<MovingBlock> blocks){
		//remove all blocks (priority first)
		for(int A = 0; A < blocks.size(); A++){
			MovingBlock block = blocks.get(A);
			if (block.getBlock().getLocation().getY() > getWaterLevel(blocks)){
				if (block.getSpecialBlock() != null){
					block.getSpecialBlock().removeBlock(false);
				}else{
					block.getBlock().setType(Material.AIR);
				}
			}else{
				if (block.getSpecialBlock() != null){
					block.getSpecialBlock().removeBlock(true);
				}else{
					block.getBlock().setType(Material.STATIONARY_WATER);
				}
			}
			OtherPlugins.moveCannon(block, this);
		}
		//place all blocks (priority last)
		for(int A = blocks.size() - 1;A >= 0; A--){
			MovingBlock block = blocks.get(A);
			Block bBlock = block.getMovingTo().getBlock();
			if ((move.equals(MovementMethod.ROTATE_LEFT) || (move.equals(MovementMethod.ROTATE_RIGHT)))){
				byte data = BlockConverter.convertRotation(move, block, block.getData());
				bBlock.setTypeIdAndData(block.getId(), data, false);
				if (block.getSpecialBlock() != null){
					if (block.getSpecialBlock().getType().equals("door")){
						bBlock.getRelative(0, -1, 0).setTypeIdAndData(block.getId(), data, false);
					}
				}
			}else{
				bBlock.setTypeIdAndData(block.getId(), block.getData(), false);
				if (block.getSpecialBlock() != null){
					if (block.getSpecialBlock().getType().equals("door")){
						bBlock.getRelative(0, -1, 0).setTypeIdAndData(block.getId(), block.getData(), false);
					}
				}
			}
			if (block.getSpecialBlock() != null){
				setInventory(block);
			}
		}
		MovingBlock mBlock = new MovingBlock(getTeleportLocation().getBlock(), this, move);
		TELEPORTLOCATION = mBlock.getMovingTo();
		updateLocation(mBlock.getMovingTo(), getSign());
		moveEntitys(move);
		updateStructure();
	}
	
	private boolean safeMove(MovementMethod move, @Nullable OfflinePlayer player, boolean sendEvent){
		List<MovingBlock> blocks = sortMovingBlocks(move, player);
		if (blocks == null){
			return false;
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		for(MovingBlock block : blocks){
			if (factionSupport(config, block, player)){
				return false;
			}
			if (worldGuardSupport(config, block, player)){
				return false;
			}
			if (worldBorderSupport(config, block, player)){
				return false;
			}
			if (griefProtectionSupport(config, block, player)){
				return false;
			}
			if (townySupport(config, block, player)){
				return false;
			}
		}
		if (this.getVesselType().attemptToMove(this, move, blocks, player)){
			MovingStructure structure = new MovingStructure(blocks);
			this.setStructure(structure);
			if (sendEvent){
				ShipMovingEvent event = new ShipMovingEvent(player, this, move, structure);
				Bukkit.getPluginManager().callEvent(event);
				if (event.isCancelled()){
					return false;
				}
				structure = event.getStructure();
			}
			forceMove(move, structure.getAllMovingBlocks());
			return true;
		}
		return false;
	}
	
	public boolean moveVessel(MovementMethod move, int speed, OfflinePlayer player){
		return moveVessel(move, speed, player, true);
	}
	
	public boolean moveVessel(MovementMethod move, int speed, OfflinePlayer player, boolean event){
		if (hasMovePermissions(player)){
			if (move == null){
				if (player != null){
					if (player.isOnline()){
						player.getPlayer().sendMessage(Ships.runShipsMessage("Can not translate block into move", true));
					}
				}
				return false;
			}
			if (speed != 0){
				move.setSpeed(speed);
			}
			
			if (event){
				ShipAboutToMoveEvent event2 = new ShipAboutToMoveEvent(move, speed, this, player);
				Bukkit.getPluginManager().callEvent(event2);
				if (event2.isCancelled()){
					return false;
				}else{
					move = event2.getMethod();
					speed = event2.getSpeed();
					player = event2.getPlayer();
				}
			}
			if (safeMove(move, player, event)){
				return true;
			}
		}
		return false;
	}
	
	public boolean safelyMoveTowardsLocation(Location moveTo, int speed, Player player){
		Location loc = this.getSign().getLocation();
		BlockFace face = this.getFacingDirection();
		MovementMethod move = MovementMethod.getMovementDirection(face);
		boolean value = false;
		if (loc.getY() == moveTo.getY()){
			if (loc.getX() == moveTo.getX()){
				//try Z
				if (loc.getZ() == moveTo.getZ()){
					return false;
				}
				if (loc.getZ() > moveTo.getZ()){
					if ((loc.getZ() + speed) > moveTo.getZ()){
						value = moveVessel(MovementMethod.MOVE_NEGATIVE_Z, 1, player);
					}else{
						if (move.equals(MovementMethod.MOVE_NEGATIVE_Z)){
							value = moveVessel(MovementMethod.MOVE_NEGATIVE_Z, speed, player);
						}else{
							value = moveVessel(MovementMethod.ROTATE_RIGHT, 90, player);
						}
					}
				}else{
					if ((loc.getZ() - speed) < moveTo.getZ()){
						value = moveVessel(MovementMethod.MOVE_POSITIVE_Z, 1, player);
					}else{
						if (move.equals(MovementMethod.MOVE_POSITIVE_Z)){
							value = moveVessel(MovementMethod.MOVE_POSITIVE_Z, speed, player);
						}else{
							value = moveVessel(MovementMethod.ROTATE_RIGHT, 90, player);
						}
					}
				}
			}else{
				//try X
				if (loc.getX() > moveTo.getX()){
					if ((loc.getX() + speed) > moveTo.getX()){
						value = moveVessel(MovementMethod.MOVE_NEGATIVE_X, 1, player);
					}else{
						if (move.equals(MovementMethod.MOVE_NEGATIVE_X)){
							value = moveVessel(MovementMethod.MOVE_NEGATIVE_X, speed, player);
						}else{
							value = moveVessel(MovementMethod.ROTATE_RIGHT, 90, player);
						}
					}
				}else{
					if ((loc.getX() - speed) < moveTo.getX()){
						value = moveVessel(MovementMethod.MOVE_POSITIVE_X, 1, player);
					}else{
						if (move.equals(MovementMethod.MOVE_POSITIVE_X)){
							value = moveVessel(MovementMethod.MOVE_POSITIVE_X, speed, player);
						}else{
							value = moveVessel(MovementMethod.ROTATE_RIGHT, 90, player);
						}
					}
				}
			}
		}else{
			//try Y
			if (loc.getY() > moveTo.getY()){
				if ((loc.getY() + speed) > moveTo.getY()){
					value = moveVessel(MovementMethod.MOVE_UP, 1, player);
				}else{
					value = moveVessel(MovementMethod.MOVE_UP, speed, player);
				}
			}else{
				if ((loc.getY() - speed) > moveTo.getY()){
					value = moveVessel(MovementMethod.MOVE_DOWN, 1, player);
				}else{
					value = moveVessel(MovementMethod.MOVE_DOWN, speed, player);
				}
			}
		}
		return value;
	}
	
	public boolean teleportVessel(Location loc, @Nullable OfflinePlayer player){
		return teleportVessel(loc, player, true);
	}
	
	public boolean teleportVessel(Location loc, @Nullable OfflinePlayer player, boolean event){
		if (hasMovePermissions(player)){
			MovementMethod move = MovementMethod.TELEPORT;
			ShipAboutToMoveEvent event2 = new ShipAboutToMoveEvent(move, 1, this, player);
			Bukkit.getPluginManager().callEvent(event2);
			if (event2.isCancelled()){
				return false;
			}else{
				player = event2.getPlayer();
			}
			if (safeMove(move, player, event)){
				return true;
			}
		}
		return false;
	}
	
	public void forceTeleport(Location loc){
		List<MovingBlock> blocks = getTeleportStructure(loc);
		forceMove(MovementMethod.TELEPORT, blocks);
	}
	
	public void forceMove(MovementMethod move, int speed, Player player){
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		for (Block block : STRUCTURE.getPriorityBlocks()){
			MovingBlock block2 = new MovingBlock(block, this, move);
			blocks.add(block2);
		}
		for (SpecialBlock block : STRUCTURE.getSpecialBlocks()){
			MovingBlock block2 = new MovingBlock(block, this, move);
			blocks.add(block2);
		}
		for (Block block : STRUCTURE.getStandardBlocks()){
			MovingBlock block2 = new MovingBlock(block, this, move);
			blocks.add(block2);
		}
		for (Block block : STRUCTURE.getAirBlocks()){
			MovingBlock block2 = new MovingBlock(block, this, move);
			blocks.add(block2);
		}
		forceMove(move, blocks);
	}

}

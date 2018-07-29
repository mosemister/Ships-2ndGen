package org.ships.event.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.material.Sign;
import org.ships.block.Vector.BlockVector;
import org.ships.block.sign.ShipSign;
import org.ships.configuration.Config;
import org.ships.event.commands.gui.ShipsGUICommand;
import org.ships.plugin.Ships;
import org.ships.ship.LoadableShip;
import org.ships.ship.loader.VesselLoader;
import org.ships.ship.movement.MovementMethod;
import org.ships.ship.movement.ShipsAutoRuns;
import org.ships.ship.type.VesselType;

public class BukkitListeners implements Listener {
	public static void playerLeave(Player player) {
		for (LoadableShip vessel : LoadableShip.getShips()) {
			if (!vessel.getEntities().contains(player))
				continue;
			Block block = player.getLocation().getBlock();
			Block loc = vessel.getLocation().getBlock();
			BlockVector vector = new BlockVector(loc, block);
			File file = vessel.getFile();
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			List<String> list = config.getStringList("PlayerLocation");
			if (list == null) {
				list = new ArrayList<>();
			}
			list.add(player.getUniqueId().toString() + "," + vector.toString());
		}
	}

	@EventHandler
	public static void playerQuit(PlayerQuitEvent event) {
		BukkitListeners.playerLeave(event.getPlayer());
	}

	@EventHandler
	public static void playerKickEvent(PlayerKickEvent event) {
		BukkitListeners.playerLeave(event.getPlayer());
	}

	@EventHandler
	public static void playerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		for (LoadableShip vessel : LoadableShip.getShips()) {
			for (Map.Entry<OfflinePlayer, BlockVector> entry : vessel.getBlockLocation().entrySet()) {
				if (!entry.getKey().equals(player))
					continue;
				Location loc = entry.getValue().getBlock(vessel.getLocation().getBlock()).getLocation();
				player.teleport(loc);
			}
		}
	}

	@EventHandler
	public static void blockBurn(BlockIgniteEvent event) {
		LoadableShip vessel;
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		Block block = event.getIgnitingBlock();
		if (block != null && block.getType().equals(Material.NETHERRACK) && (vessel = LoadableShip.getShip(block, false)) != null && (config.getBoolean("World.ProtectedVessels.FireProtect2") || vessel.isInvincible())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public static void endermanProtect(EntityChangeBlockEvent event) {
		LoadableShip vessel;
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		if (event.getEntity() instanceof Enderman && (vessel = LoadableShip.getShip(event.getBlock(), false)) != null && (config.getBoolean("World.ProtectedVessels.EntityProtect.EnderMan") || vessel.isInvincible())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public static void entityProtect(EntityExplodeEvent event) {
		LoadableShip vessel;
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		if (config.getBoolean("World.ProtectedVessels.ExplodeProtect.Creeper") && event.getEntity() instanceof Creeper) {
			for (Block block : event.blockList()) {
				vessel = LoadableShip.getShip(block, false);
				if (vessel == null || !vessel.isInvincible())
					continue;
				event.blockList().remove(block);
			}
		}
		if (event.getEntityType().equals(EntityType.PRIMED_TNT)) {
			for (Block block : event.blockList()) {
				vessel = LoadableShip.getShip(block, false);
				if (vessel == null || !config.getBoolean("World.ProtectedVessels.ExplodeProtect.TNT") && !vessel.isInvincible())
					continue;
				event.blockList().remove(block);
			}
		}
		if (event.getEntity() instanceof EnderDragon) {
			for (Block block : event.blockList()) {
				vessel = LoadableShip.getShip(block, false);
				if (vessel == null || !config.getBoolean("World.ProtectedVessels.EntityProtect.EnderDragon") && !vessel.isInvincible())
					continue;
				event.blockList().remove(block);
			}
		}
		if (event.getEntity() instanceof Wither) {
			for (Block block : event.blockList()) {
				vessel = LoadableShip.getShip(block, false);
				if (vessel == null || !config.getBoolean("World.ProtectedVessels.EntityProtect.Wither") && !vessel.isInvincible())
					continue;
				event.blockList().remove(block);
			}
		}
	}

	@EventHandler
	public static void signBreak(BlockBreakEvent event) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		LoadableShip vessel = LoadableShip.getShip(event.getBlock(), false);
		if (vessel != null) {
			if (config.getBoolean("World.ProtectedVessels.BlockBreak")) {
				if (!event.getPlayer().equals(vessel.getOwner())) {
					event.setCancelled(true);
					event.getPlayer().sendMessage(Ships.runShipsMessage("This Ship has protection on", true));
				}
			} else if (vessel.isInvincible()) {
				event.setCancelled(true);
			}
		}
		if (event.getBlock().getState() instanceof org.bukkit.block.Sign) {
			org.bukkit.block.Sign sign = (org.bukkit.block.Sign) event.getBlock().getState();
			BukkitListeners.signBreakEvent(sign, event);
		}
		for (BlockFace face : new BlockFace[] { BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.UP, BlockFace.WEST }) {
			Sign sign;
			Block block = event.getBlock().getRelative(face);
			if (!(block.getState() instanceof org.bukkit.block.Sign) || !(sign = (Sign) block.getState().getData()).isWallSign() || !block.getRelative(sign.getAttachedFace()).equals(event.getBlock()))
				continue;
			BukkitListeners.signBreakEvent((org.bukkit.block.Sign) block.getState(), event);
		}
	}

	public static void signBreakEvent(org.bukkit.block.Sign sign, BlockBreakEvent event) {
		if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")) {
			LoadableShip vessel = LoadableShip.getShip(sign);
			if (vessel == null) {
				event.getPlayer().sendMessage(Ships.runShipsMessage("Unknown ship", true));
			} else if (event.getPlayer().equals(vessel.getOwner()) || event.getPlayer().hasPermission("ships.break.bypass") || event.getPlayer().hasPermission("ships.*")) {
				vessel.delete();
				event.getPlayer().sendMessage(Ships.runShipsMessage(vessel.getName() + " has been removed.", false));
			} else {
				event.getPlayer().sendMessage(Ships.runShipsMessage(vessel.getName() + "does not belong to you.", true));
				event.setCancelled(true);
			}
		} else if (sign.getLine(0).equals(ChatColor.YELLOW + "[E.O.T]")) {
			LoadableShip vessel = LoadableShip.getShip(sign.getBlock(), false);
			if (vessel == null) {
				event.getPlayer().sendMessage(Ships.runShipsMessage("Unknown ship", true));
			} else {
				ShipsAutoRuns.EOTAUTORUN.remove(vessel);
			}
		}
	}

	@EventHandler
	public static void signCreation(SignChangeEvent event) {
		String line = event.getLine(0);
		Optional<ShipSign> opSign = Ships.getPlugin().getShipSigns().stream().filter(s -> {
			if (ChatColor.stripColor(s.getLines()[0]).equalsIgnoreCase(line))
				return true;
			if (!Arrays.asList(s.getAliases()).stream().anyMatch(a -> a.equalsIgnoreCase(line)))
				return false;
			return true;
		}).findAny();
		if (!opSign.isPresent()) {
			return;
		}
		if (opSign.get().onCreate(event)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public static void signClick(PlayerInteractEvent event) {
		if (!event.getPlayer().isSneaking()) {
			if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
				if (event.getClickedBlock().getState() instanceof org.bukkit.block.Sign) {
					org.bukkit.block.Sign sign = (org.bukkit.block.Sign) event.getClickedBlock().getState();
					if (sign.getLine(0).equals(ChatColor.YELLOW + "[Move]")) {
						if (sign.getLine(1).equals("{" + ChatColor.GREEN + "Engine" + ChatColor.BLACK + "}")) {
							sign.setLine(1, ChatColor.GREEN + "Engine");
							sign.setLine(2, "{Boost}");
							sign.update();
						} else {
							sign.setLine(1, "{" + ChatColor.GREEN + "Engine" + ChatColor.BLACK + "}");
							sign.setLine(2, "Boost");
							sign.update();
						}
					} else if (sign.getLine(0).equals(ChatColor.YELLOW + "[Wheel]")) {
						LoadableShip vessel = LoadableShip.getShip(sign.getBlock(), true);
						if (vessel == null) {
							event.getPlayer().sendMessage(Ships.runShipsMessage("Ships sign can not be found", true));
						} else {
							vessel.moveTowards(MovementMethod.ROTATE_LEFT, 0, event.getPlayer());
						}
					} else if (sign.getLine(0).equals(ChatColor.YELLOW + "[Altitude]")) {
						LoadableShip vessel = LoadableShip.getShip(sign.getBlock(), true);
						if (vessel == null) {
							event.getPlayer().sendMessage(Ships.runShipsMessage("Ships sign can not be found", true));
						} else {
							vessel.moveTowards(MovementMethod.MOVE_DOWN, 1, event.getPlayer());
						}
					} else if (sign.getLine(0).equals(ChatColor.YELLOW + "[E.O.T]")) {
						if (sign.getLine(1).equals("-[" + ChatColor.GREEN + "AHEAD" + ChatColor.BLACK + "]-")) {
							sign.setLine(1, ChatColor.GREEN + "AHEAD");
							sign.setLine(2, "-[" + ChatColor.WHITE + "STOP" + ChatColor.BLACK + "]-");
							sign.update();
							ShipsAutoRuns.EOTAUTORUN.remove(LoadableShip.getShip(sign.getBlock(), true));
						} else if (sign.getLine(1).equals(ChatColor.GREEN + "AHEAD")) {
							sign.setLine(1, "-[" + ChatColor.GREEN + "AHEAD" + ChatColor.BLACK + "]-");
							sign.setLine(2, ChatColor.WHITE + "STOP");
							sign.update();
							ShipsAutoRuns.EOTAUTORUN.put(LoadableShip.getShip(sign.getBlock(), true), event.getPlayer());
						}
					}
				}
			} else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				LoadableShip vessel;
				YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
				if (config.getBoolean("World.ProtectedVessels.InventoryOpen") && (event.getClickedBlock().getState() instanceof Chest || event.getClickedBlock().getState() instanceof Furnace || event.getClickedBlock().getState() instanceof Hopper || event.getClickedBlock().getState() instanceof Dropper || event.getClickedBlock().getState() instanceof Dispenser) && (vessel = LoadableShip.getShip(event.getClickedBlock(), false)) != null && !vessel.getOwner().equals(event.getPlayer())) {
					event.setCancelled(true);
				}
				if (event.getClickedBlock().getState() instanceof org.bukkit.block.Sign) {
					org.bukkit.block.Sign sign = (org.bukkit.block.Sign) event.getClickedBlock().getState();
					if (sign.getLine(0).equals(ChatColor.YELLOW + "[Move]")) {
						LoadableShip vessel2 = LoadableShip.getShip(sign.getBlock(), true);
						if (vessel2 == null) {
							event.getPlayer().sendMessage(Ships.runShipsMessage("Ships sign can not be found", true));
						} else {
							Sign sign2 = (Sign) sign.getData();
							BlockFace face = sign2.isWallSign() ? sign2.getAttachedFace() : sign2.getFacing().getOppositeFace();
							if (sign.getLine(1).equals("{" + ChatColor.GREEN + "Engine" + ChatColor.BLACK + "}")) {
								vessel2.moveTowards(MovementMethod.getMovingDirection(vessel2, face).get(), vessel2.getVesselType().getDefaultSpeed(), event.getPlayer());
							} else {
								vessel2.moveTowards(MovementMethod.getMovingDirection(vessel2, face).get(), vessel2.getVesselType().getDefaultSpeed(), event.getPlayer());
							}
						}
					} else if (sign.getLine(0).equals(ChatColor.YELLOW + "[Wheel]")) {
						LoadableShip vessel3 = LoadableShip.getShip(sign.getBlock(), true);
						if (vessel3 == null) {
							event.getPlayer().sendMessage(Ships.runShipsMessage("Ships sign can not be found", true));
						} else {
							vessel3.moveTowards(MovementMethod.ROTATE_RIGHT, 0, event.getPlayer());
						}
					} else if (sign.getLine(0).equals(ChatColor.YELLOW + "[Altitude]")) {
						LoadableShip vessel4 = LoadableShip.getShip(sign.getBlock(), true);
						if (vessel4 == null) {
							event.getPlayer().sendMessage(Ships.runShipsMessage("Ships sign can not be found", true));
						} else {
							vessel4.moveTowards(MovementMethod.MOVE_UP, 1, event.getPlayer());
						}
					} else if (sign.getLine(0).equals(ChatColor.YELLOW + "[E.O.T]")) {
						event.getPlayer().sendMessage(Ships.runShipsMessage("Moves the vessel in the same direction until this E.O.T sign is changed.", false));
					} else if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")) {
						LoadableShip vessel5 = LoadableShip.getShip(sign);
						if (vessel5 == null) {
							LoadableShip vessel2 = VesselLoader.loadUnloadedVessel(sign);
							if (vessel2 == null) {
								event.getPlayer().sendMessage(Ships.runShipsMessage("A issue has occured. Sign is not licenced", false));
								sign.getBlock().breakNaturally();
							} else {
								event.getPlayer().sendMessage(Ships.runShipsMessage("Recoved losted vessel, click again to get stats.", false));
								vessel2.updateLocation(vessel2.getTeleportLocation(), sign);
							}
						} else if (vessel5.getOwner().equals(event.getPlayer())) {
							Player player = event.getPlayer();
							VesselType type = vessel5.getVesselType();
							player.sendMessage(ChatColor.BLUE + "[Name] " + vessel5.getName());
							player.sendMessage(ChatColor.BLUE + "[Type] " + type.getName());
							player.sendMessage(ChatColor.BLUE + "[Owner] " + vessel5.getOwner().getName());
							player.sendMessage(ChatColor.BLUE + "[Speed] " + type.getDefaultSpeed());
							player.sendMessage(ChatColor.BLUE + "[Max Size] " + type.getMaxBlocks());
							player.sendMessage(ChatColor.BLUE + "[Current Size] " + vessel5.getStructure().getAllBlocks().size());
							player.sendMessage(ChatColor.BLUE + "[Min Size] " + type.getMinBlocks());
							player.sendMessage(ChatColor.BLUE + "[Boost Speed] " + type.getDefaultBoostSpeed());
							player.sendMessage(ChatColor.BLUE + "[Water Level]" + vessel5.getWaterLevel());
							player.sendMessage(ChatColor.BLUE + "[Entity Count] " + vessel5.getEntities().size());
						} else {
							event.getPlayer().sendMessage(Ships.runShipsMessage("Vessel is owned by " + vessel5.getOwner().getName(), false));
						}
					}
				}
			}
		}
	}

	@EventHandler
	public static void inventoryClick(InventoryClickEvent event) {
		Inventory inv = event.getInventory();
		for (ShipsGUICommand command : ShipsGUICommand.getInterfaces()) {
			if (!inv.getName().equals(command.getInventoryName()))
				continue;
			command.onScreenClick(event.getWhoClicked(), event.getCurrentItem(), inv, event.getSlot(), event.getClick());
			event.setCancelled(true);
		}
	}

	@EventHandler
	public static void inventoryDrag(InventoryDragEvent event) {
		Inventory inv = event.getInventory();
		for (ShipsGUICommand command : ShipsGUICommand.getInterfaces()) {
			if (!inv.getName().equals(command.getInventoryName()))
				continue;
			event.setCancelled(true);
		}
	}
}

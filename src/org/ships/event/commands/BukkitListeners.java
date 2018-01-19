package org.ships.event.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

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
import org.ships.configuration.Config;
import org.ships.event.commands.gui.ShipsGUICommand;
import org.ships.event.custom.ShipCreateEvent;
import org.ships.event.custom.ShipsSignCreation;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.ShipsTypes.VesselType;
import MoseShipsBukkit.StillShip.Vectors.BlockVector;
import MoseShipsBukkit.StillShip.Vessel.LoadableShip;
import MoseShipsBukkit.Utils.ShipsAutoRuns;
import MoseShipsBukkit.Utils.VesselLoader;
import MoseShipsBukkit.Utils.Exceptions.InvalidSignException;
import MoseShipsBukkit.World.Wind.Direction;

public class BukkitListeners implements Listener {

	public static void playerLeave(Player player) {
		for (LoadableShip vessel : LoadableShip.getShips()) {
			if (vessel.getEntities().contains(player)) {
				Block block = player.getLocation().getBlock();
				Block loc = vessel.getLocation().getBlock();
				BlockVector vector = new BlockVector(loc, block);
				File file = vessel.getFile();
				YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
				List<String> list = config.getStringList("PlayerLocation");
				if (list == null) {
					list = new ArrayList<String>();
				}
				list.add(player.getUniqueId().toString() + "," + vector.toString());
			}
		}
	}

	@EventHandler
	public static void playerQuit(PlayerQuitEvent event) {
		playerLeave(event.getPlayer());
	}

	@EventHandler
	public static void playerKickEvent(PlayerKickEvent event) {
		playerLeave(event.getPlayer());
	}

	@EventHandler
	public static void playerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		for (LoadableShip vessel : LoadableShip.getShips()) {
			for (Entry<OfflinePlayer, BlockVector> entry : vessel.getBlockLocation().entrySet()) {
				if (entry.getKey().equals(player)) {
					
					Location loc = entry.getValue().getBlock(vessel.getLocation().getBlock()).getLocation();
					player.teleport(loc);
				}
			}
		}
	}

	@EventHandler
	public static void blockBurn(BlockIgniteEvent event) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		Block block = event.getIgnitingBlock();
		if (block != null) {
			if (block.getType().equals(Material.NETHERRACK)) {
				LoadableShip vessel = LoadableShip.getShip(block, false);
				if (vessel != null) {
					if ((config.getBoolean("World.ProtectedVessels.FireProtect2")) || (vessel.isInvincible())) {
						event.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public static void endermanProtect(EntityChangeBlockEvent event) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		if (event.getEntity() instanceof Enderman) {
			LoadableShip vessel = LoadableShip.getShip(event.getBlock(), false);
			if (vessel != null) {
				if ((config.getBoolean("World.ProtectedVessels.EntityProtect.EnderMan")) || (vessel.isInvincible())) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public static void entityProtect(EntityExplodeEvent event) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		if (config.getBoolean("World.ProtectedVessels.ExplodeProtect.Creeper")) {
			if (event.getEntity() instanceof Creeper) {
				for (Block block : event.blockList()) {
					LoadableShip vessel = LoadableShip.getShip(block, false);
					if (vessel != null) {
						if (((vessel.isInvincible()))) {
							event.blockList().remove(block);
						}
					}
				}
			}
		}
		if (event.getEntityType().equals(EntityType.PRIMED_TNT)) {
			for (Block block : event.blockList()) {
				LoadableShip vessel = LoadableShip.getShip(block, false);
				if (vessel != null) {
					if ((config.getBoolean("World.ProtectedVessels.ExplodeProtect.TNT")) || (vessel.isInvincible())) {
						event.blockList().remove(block);
					}
				}
			}
		}
		if (event.getEntity() instanceof EnderDragon) {
			for (Block block : event.blockList()) {
				LoadableShip vessel = LoadableShip.getShip(block, false);
				if (vessel != null) {
					if ((((config.getBoolean("World.ProtectedVessels.EntityProtect.EnderDragon")))
							|| (vessel.isInvincible()))) {
						event.blockList().remove(block);
					}
				}
			}
		}
		if (event.getEntity() instanceof Wither) {
			for (Block block : event.blockList()) {
				LoadableShip vessel = LoadableShip.getShip(block, false);
				if (vessel != null) {
					if ((((config.getBoolean("World.ProtectedVessels.EntityProtect.Wither")))
							|| (vessel.isInvincible()))) {
						event.blockList().remove(block);
					}
				}
			}
		}
	}

	@EventHandler
	public static void signBreak(BlockBreakEvent event) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		LoadableShip vessel = LoadableShip.getShip(event.getBlock(), false);
		if (vessel != null) {
			if (((config.getBoolean("World.ProtectedVessels.BlockBreak")))) {
				if (!event.getPlayer().equals(vessel.getOwner())) {
					event.setCancelled(true);
					event.getPlayer().sendMessage(Ships.runShipsMessage("This Ship has protection on", true));
				}
			} else if (vessel.isInvincible()) {
				event.setCancelled(true);
			}
		}
		if (event.getBlock().getState() instanceof Sign) {
			Sign sign = (Sign) event.getBlock().getState();
			signBreakEvent(sign, event);
		}
		BlockFace[] faces = { BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.UP,
				BlockFace.WEST };
		for (BlockFace face : faces) {
			Block block = event.getBlock().getRelative(face);
			if (block.getState() instanceof Sign) {
				org.bukkit.material.Sign sign = (org.bukkit.material.Sign) block.getState().getData();
				if (sign.isWallSign()) {
					//if (block.getRelative(sign.getAttachedFace()).equals(block)) {
					if(block.getRelative(sign.getAttachedFace()).equals(event.getBlock())) {
						signBreakEvent((Sign) block.getState(), event);
					}
				}
			}
		}
	}

	public static void signBreakEvent(Sign sign, BlockBreakEvent event) {
		if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")) {
			LoadableShip vessel = LoadableShip.getShip(sign);
			if (vessel == null) {
				event.getPlayer().sendMessage(Ships.runShipsMessage("Unknown ship", true));
			} else {
				if ((event.getPlayer().equals(vessel.getOwner()))
						|| (event.getPlayer().hasPermission("ships.break.bypass"))
						|| (event.getPlayer().hasPermission("ships.*"))) {
					vessel.delete();
					event.getPlayer()
							.sendMessage(Ships.runShipsMessage(vessel.getName() + " has been removed.", false));
				} else {
					event.getPlayer()
							.sendMessage(Ships.runShipsMessage(vessel.getName() + "does not belong to you.", true));
					event.setCancelled(true);
				}
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
		// Ships sign
		Sign sign = (Sign) event.getBlock().getState();
		if (event.getLine(0).equalsIgnoreCase("[Ships]")) {
			String name = event.getLine(2);
			if(name.replace(" ", "").length() == 0) {
				event.getPlayer().sendMessage(Ships.runShipsMessage("Name must be specified on line 3", true));
				return;
			}
			VesselType type = VesselType.getTypeByName(event.getLine(1));
			if (type != null) {
				type = type.createClone();
				if ((event.getPlayer().hasPermission("ships." + type.getName() + ".make"))
						|| (event.getPlayer().hasPermission("ships.*.make"))
						|| (event.getPlayer().hasPermission("ships.*"))) {
					String[] signLines = { ChatColor.YELLOW + "[Ships]", ChatColor.BLUE + event.getLine(1),
							ChatColor.GREEN + event.getLine(2), ChatColor.GREEN + event.getLine(3) };
					ShipsSignCreation creation = new ShipsSignCreation(Ships.getPlugin(), signLines, sign,
							event.getPlayer(), event.getLines());
					Bukkit.getPluginManager().callEvent(creation);
					if (!creation.isCancelled()) {
						if (LoadableShip.getShip(event.getLine(2)) == null) {
							LoadableShip vessel;
							try {
								vessel = new LoadableShip(type, (Sign) event.getBlock().getState(), event.getPlayer().getLocation(), event.getPlayer());
							}catch(InvalidSignException e) {
								return;
							}
							ShipCreateEvent event2 = new ShipCreateEvent(event.getPlayer(),
									(Sign) event.getBlock().getState(), vessel);
							Bukkit.getPluginManager().callEvent(event2);
							if (!event2.isCancelled()) {
								event.setLine(0, creation.getReturnText()[0]);
								event.setLine(1, creation.getReturnText()[1]);
								event.setLine(2, creation.getReturnText()[2]);
								event.setLine(3, creation.getReturnText()[3]);
								YamlConfiguration config = YamlConfiguration
										.loadConfiguration(Config.getConfig().getFile());
								if (config.getBoolean("Signs.ForceUsernameOnLicenceSign")) {
									event.setLine(3, ChatColor.GREEN + event.getPlayer().getName());
								}
							} else {
								vessel.delete();
							}
							return;
						} else {
							event.getPlayer().sendMessage(Ships.runShipsMessage("Name taken", true));
							event.setCancelled(true);
							return;
						}
					}
				}
			} else {
				event.getPlayer().sendMessage(Ships.runShipsMessage("Vessel type does not exist", true));
				event.setCancelled(true);
				return;
			}
		}
		// Move sign
		if ((event.getLine(0).equalsIgnoreCase("[Move]")) || (event.getLine(0).equalsIgnoreCase("[Engine]"))) {
			String[] signText = { ChatColor.YELLOW + "[Move]", "{" + ChatColor.GREEN + "Engine" + ChatColor.BLACK + "}",
					"Boost", "" };
			ShipsSignCreation creation = new ShipsSignCreation(Ships.getPlugin(), signText, sign, event.getPlayer(),
					event.getLine(0));
			Bukkit.getPluginManager().callEvent(creation);
			if (!creation.isCancelled()) {
				event.setLine(0, creation.getReturnText()[0]);
				event.setLine(1, creation.getReturnText()[1]);
				event.setLine(2, creation.getReturnText()[2]);
				event.setLine(3, creation.getReturnText()[3]);
			}
			return;
		}
		// Wheel sign
		if (event.getLine(0).equalsIgnoreCase("[Wheel]")) {
			String[] signText = { ChatColor.YELLOW + "[Wheel]", ChatColor.RED + "\\\\ || //",
					ChatColor.RED + "==    ==", ChatColor.RED + "// || \\\\" };
			ShipsSignCreation creation = new ShipsSignCreation(Ships.getPlugin(), signText, sign, event.getPlayer(),
					event.getLine(0));
			Bukkit.getPluginManager().callEvent(creation);
			if (!creation.isCancelled()) {
				event.setLine(0, creation.getReturnText()[0]);
				event.setLine(1, creation.getReturnText()[1]);
				event.setLine(2, creation.getReturnText()[2]);
				event.setLine(3, creation.getReturnText()[3]);
			}
			return;
		}
		// Altitude
		if ((event.getLine(0).equalsIgnoreCase("[Burner]")) || (event.getLine(0).equalsIgnoreCase("[Altitude]"))) {
			String[] signText = { ChatColor.YELLOW + "[Altitude]", "[right] up", "[left] down", "" };
			ShipsSignCreation creation = new ShipsSignCreation(Ships.getPlugin(), signText, sign, event.getPlayer(),
					event.getLine(0));
			Bukkit.getPluginManager().callEvent(creation);
			if (!creation.isCancelled()) {
				event.setLine(0, creation.getReturnText()[0]);
				event.setLine(1, creation.getReturnText()[1]);
				event.setLine(2, creation.getReturnText()[2]);
				event.setLine(3, creation.getReturnText()[3]);
			}
			return;
		}
		// EOT
		if (event.getLine(0).equalsIgnoreCase("[EOT]")) {
			String[] signText = { ChatColor.YELLOW + "[E.O.T]", ChatColor.GREEN + "AHEAD",
					"-[" + ChatColor.WHITE + "STOP" + ChatColor.BLACK + "]-", "" };
			ShipsSignCreation creation = new ShipsSignCreation(Ships.getPlugin(), signText, sign, event.getPlayer(),
					event.getLine(0));
			Bukkit.getPluginManager().callEvent(creation);
			if (!creation.isCancelled()) {
				event.setLine(0, creation.getReturnText()[0]);
				event.setLine(1, creation.getReturnText()[1]);
				event.setLine(2, creation.getReturnText()[2]);
				event.setLine(3, creation.getReturnText()[3]);
			}
			return;
		}
		// CELL
		if (event.getLine(0).equalsIgnoreCase("[Cell]")) {
			String[] signText = { ChatColor.YELLOW + "[Cell]", "", ChatColor.GREEN + "0", "" };
			ShipsSignCreation creation = new ShipsSignCreation(Ships.getPlugin(), signText, sign, event.getPlayer(),
					event.getLine(0));
			Bukkit.getPluginManager().callEvent(creation);
			if (!creation.isCancelled()) {
				event.setLine(0, creation.getReturnText()[0]);
				event.setLine(1, creation.getReturnText()[1]);
				event.setLine(2, creation.getReturnText()[2]);
				event.setLine(3, creation.getReturnText()[3]);
			}
			return;
		}
	}

	@EventHandler
	public static void signClick(PlayerInteractEvent event) {
		if (!event.getPlayer().isSneaking()) {
			if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
				if (event.getClickedBlock().getState() instanceof Sign) {
					Sign sign = (Sign) event.getClickedBlock().getState();

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
						} else {
							if (sign.getLine(1).equals(ChatColor.GREEN + "AHEAD")) {
								sign.setLine(1, "-[" + ChatColor.GREEN + "AHEAD" + ChatColor.BLACK + "]-");
								sign.setLine(2, ChatColor.WHITE + "STOP");
								sign.update();
								ShipsAutoRuns.EOTAUTORUN.put(LoadableShip.getShip(sign.getBlock(), true),
										event.getPlayer());
							}
						}
					}
				}
			} else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
				if (config.getBoolean("World.ProtectedVessels.InventoryOpen")) {
					if ((event.getClickedBlock().getState() instanceof Chest)
							|| (event.getClickedBlock().getState() instanceof Furnace)
							|| (event.getClickedBlock().getState() instanceof Hopper)
							|| (event.getClickedBlock().getState() instanceof Dropper)
							|| (event.getClickedBlock().getState() instanceof Dispenser)) {
						LoadableShip vessel = LoadableShip.getShip(event.getClickedBlock(), false);
						if (vessel != null) {
							if (!vessel.getOwner().equals(event.getPlayer())) {
								event.setCancelled(true);
							}
						}
					}
				}
				if (event.getClickedBlock().getState() instanceof Sign) {
					Sign sign = (Sign) event.getClickedBlock().getState();

					if (sign.getLine(0).equals(ChatColor.YELLOW + "[Move]")) {
						LoadableShip vessel = LoadableShip.getShip(sign.getBlock(), true);
						if (vessel == null) {
							event.getPlayer().sendMessage(Ships.runShipsMessage("Ships sign can not be found", true));
						} else {
							org.bukkit.material.Sign sign2 = (org.bukkit.material.Sign) sign.getData();
							BlockFace face;
							if (sign2.isWallSign()) {
								face = sign2.getAttachedFace();
							} else {
								face = sign2.getFacing().getOppositeFace();
							}
							if (sign.getLine(1).equals("{" + ChatColor.GREEN + "Engine" + ChatColor.BLACK + "}")) {
								vessel.moveTowards(MovementMethod.getMovingDirection(vessel, face),
										vessel.getVesselType().getDefaultSpeed(), event.getPlayer());
							} else {
								if (Direction.getDirection(vessel.getLocation().getWorld()).getDirection().equals(face)) {
									vessel.moveTowards(MovementMethod.getMovingDirection(vessel, face),
											vessel.getVesselType().getDefaultBoostSpeed(), event.getPlayer());
								} else {
									vessel.moveTowards(MovementMethod.getMovingDirection(vessel, face),
											vessel.getVesselType().getDefaultSpeed(), event.getPlayer());
								}
							}
						}
					} else if (sign.getLine(0).equals(ChatColor.YELLOW + "[Wheel]")) {
						LoadableShip vessel = LoadableShip.getShip(sign.getBlock(), true);
						if (vessel == null) {
							event.getPlayer().sendMessage(Ships.runShipsMessage("Ships sign can not be found", true));
						} else {
							vessel.moveTowards(MovementMethod.ROTATE_RIGHT, 0, event.getPlayer());
						}
					} else if (sign.getLine(0).equals(ChatColor.YELLOW + "[Altitude]")) {
						LoadableShip vessel = LoadableShip.getShip(sign.getBlock(), true);
						if (vessel == null) {
							event.getPlayer().sendMessage(Ships.runShipsMessage("Ships sign can not be found", true));
						} else {
							vessel.moveTowards(MovementMethod.MOVE_UP, 1, event.getPlayer());
						}
					} else if (sign.getLine(0).equals(ChatColor.YELLOW + "[E.O.T]")) {
						event.getPlayer().sendMessage(Ships.runShipsMessage(
								"Moves the vessel in the same direction until this E.O.T sign is changed.", false));
					} else if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")) {
						LoadableShip vessel = LoadableShip.getShip(sign);
						if (vessel == null) {
							// check for invalid ships
							LoadableShip vessel2 = VesselLoader.loadUnloadedVessel(sign);
							if (vessel2 == null) {
								event.getPlayer().sendMessage(
										Ships.runShipsMessage("A issue has occured. Sign is not licenced", false));
								sign.getBlock().breakNaturally();
							} else {
								event.getPlayer().sendMessage(Ships
										.runShipsMessage("Recoved losted vessel, click again to get stats.", false));
								vessel2.updateLocation(vessel2.getTeleportLocation(), sign);
							}
						} else {
							if (vessel.getOwner().equals(event.getPlayer())) {
								Player player = event.getPlayer();
								VesselType type = vessel.getVesselType();
								player.sendMessage(ChatColor.BLUE + "[Name] " + vessel.getName());
								player.sendMessage(ChatColor.BLUE + "[Type] " + type.getName());
								player.sendMessage(ChatColor.BLUE + "[Owner] " + vessel.getOwner().getName());
								player.sendMessage(ChatColor.BLUE + "[Speed] " + type.getDefaultSpeed());
								player.sendMessage(ChatColor.BLUE + "[Max Size] " + type.getMaxBlocks());
								player.sendMessage(ChatColor.BLUE + "[Current Size] " + vessel.getStructure().getAllBlocks().size());
								player.sendMessage(ChatColor.BLUE + "[Min Size] " + type.getMinBlocks());
								player.sendMessage(ChatColor.BLUE + "[Boost Speed] " + type.getDefaultBoostSpeed());
								player.sendMessage(ChatColor.BLUE + "[Water Level]" + vessel.getWaterLevel());
								player.sendMessage(ChatColor.BLUE + "[Entity Count] " + vessel.getEntities().size());
							} else {
								event.getPlayer().sendMessage(Ships
										.runShipsMessage("Vessel is owned by " + vessel.getOwner().getName(), false));
							}
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
			if (inv.getName().equals(command.getInventoryName())) {
				command.onScreenClick(event.getWhoClicked(), event.getCurrentItem(), inv, event.getSlot(),
						event.getClick());
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public static void inventoryDrag(InventoryDragEvent event) {
		Inventory inv = event.getInventory();
		for (ShipsGUICommand command : ShipsGUICommand.getInterfaces()) {
			if (inv.getName().equals(command.getInventoryName())) {
				event.setCancelled(true);
			}
		}
	}
}

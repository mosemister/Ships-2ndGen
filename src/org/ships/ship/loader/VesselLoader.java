package org.ships.ship.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.ships.block.Vector.BlockVector;
import org.ships.configuration.Config;
import org.ships.event.custom.VesselAttemptToLoadEvent;
import org.ships.plugin.InvalidSignException;
import org.ships.plugin.Ships;
import org.ships.ship.LoadableShip;
import org.ships.ship.type.VesselType;
import org.ships.ship.type.hooks.ClassicVessel;

public class VesselLoader {
	public static void loadVessels() {
		ConsoleCommandSender sender = Bukkit.getConsoleSender();
		ArrayList<String> success = new ArrayList<String>();
		File folder = new File("plugins/Ships/VesselData/");
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.getName().contains(".yml~"))
					continue;
				if (VesselLoader.classicLoader(file) || VesselLoader.newLoader(file)) {
					VesselAttemptToLoadEvent event = new VesselAttemptToLoadEvent(file);
					Bukkit.getPluginManager().callEvent(event);
					if (event.isCancelled())
						continue;
					success.add(file.getName().replace(".yml", ""));
					continue;
				}
				YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
				if (!config.getBoolean("VesselLoading.DeleteFailedLoads"))
					continue;
				file.deleteOnExit();
			}
		}
		sender.sendMessage(Ships.runShipsMessage("Loaded the following vessels; " + success, false));
	}

	@SuppressWarnings("deprecation")
	public static boolean classicLoader(File file) {
		World world;
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		String sUUID = config.getString("ShipsData.Player.Name");
		int max = config.getInt("ShipsData.Config.Block.Max");
		int min = config.getInt("ShipsData.Config.Block.Min");
		int engine = config.getInt("ShipsData.Config.Speed.Engine");
		int boost = config.getInt("ShipsData.Config.Speed.Boost");
		double X = config.getDouble("ShipsLocation.X");
		double Y = config.getDouble("ShipsLocation.Y");
		double Z = config.getDouble("ShipsLocation.Z");
		String worldS = config.getString("ShipsLocation.world");
		if (worldS != null && (world = Bukkit.getWorld(worldS)) != null) {
			Location loc = null;
			for (int x = -boost; x < boost; ++x) {
				block3: for (int y = -boost; y < boost; ++y) {
					for (int z = -boost; z < boost; ++z) {
						Location loc2 = new Location(world, X + x, Y + y, Z + z);
						if (!(loc2.getBlock().getState() instanceof Sign) || !((Sign) loc2.getBlock().getState()).getLine(0).equals(ChatColor.YELLOW + "[Ships]"))
							continue;
						loc = loc2;
						continue block3;
					}
				}
			}
			if (loc != null && sUUID != null) {
				Sign sign;
				OfflinePlayer owner = Bukkit.getOfflinePlayer(sUUID);
				if (loc.getBlock().getState() instanceof Sign && (sign = (Sign) loc.getBlock().getState()).getLine(0).equals(ChatColor.YELLOW + "[Ships]")) {
					String typeString = sign.getLine(1).replace(ChatColor.BLUE + "", "");
					String typeStringFix = typeString.replace("2", "");
					VesselType type = VesselType.getTypeByName(typeStringFix);
					if (type != null) {
						if (type instanceof ClassicVessel) {
							type.setDefaultSpeed(engine);
							type.setMaxBlocks(max);
							type.setMinBlocks(min);
							LoadableShip ship = null;
							try {
								ship = new LoadableShip(type, sign, loc, owner);
							} catch (InvalidSignException e) {
								return false;
							}
							((ClassicVessel) (ship.getVesselType())).loadVesselFromClassicFile(ship, file);
							file.delete();
							ship.save();
							return true;
						}
					} else {
						Bukkit.getConsoleSender().sendMessage(ChatColor.RED + sign.getLine(1) + " is no longer supported in Ships 5");
					}
				}
			}
		}
		return false;
	}

	public static boolean newLoader(File file) {
		block13: {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			ConsoleCommandSender sender = Bukkit.getConsoleSender();
			try {
				String sUUID = config.getString("ShipsData.Player.Name");
				String vesselTypeS = config.getString("ShipsData.Type");
				int max = config.getInt("ShipsData.Config.Block.Max");
				int min = config.getInt("ShipsData.Config.Block.Min");
				int engine = config.getInt("ShipsData.Config.Speed.Engine");
				String locS = config.getString("ShipsData.Location.Sign");
				String teleportS = config.getString("ShipsData.Location.Teleport");
				List<String> uuidSL = config.getStringList("PlayerLocation");
				String name = file.getName().replace(".yml", "");
				if (vesselTypeS != null) {
					if (sUUID != null) {
						UUID uuid = UUID.fromString(sUUID);
						OfflinePlayer owner = Bukkit.getOfflinePlayer(uuid);
						if (locS != null) {
							if (teleportS != null) {
								String[] locM = locS.split(",");
								Location loc = new Location(Bukkit.getWorld(locM[3]), Double.parseDouble(locM[0]), Double.parseDouble(locM[1]), Double.parseDouble(locM[2]));
								if (loc.getWorld() != null) {
									if (loc.getBlock().getState() instanceof Sign) {
										Sign sign = (Sign) loc.getBlock().getState();
										String[] teleportM = teleportS.split(",");
										Location teleport = new Location(Bukkit.getWorld(teleportM[3]), Double.parseDouble(teleportM[0]), Double.parseDouble(teleportM[1]), Double.parseDouble(teleportM[2]));
										VesselType vesselType = VesselType.getTypeByName(vesselTypeS);
										if (vesselType != null) {
											LoadableShip ship;
											vesselType = vesselType.createClone();
											try {
												ship = new LoadableShip(vesselType, sign, teleport, owner);
											} catch (InvalidSignException e) {
												return false;
											}
											vesselType.setDefaultSpeed(engine);
											vesselType.setMaxBlocks(max);
											vesselType.setMinBlocks(min);
											if (uuidSL != null) {
												HashMap<OfflinePlayer, BlockVector> locationB = new HashMap<OfflinePlayer, BlockVector>();
												for (String uuidS : uuidSL) {
													String[] uuidArgs = uuidS.split(",");
													OfflinePlayer player2 = Bukkit.getOfflinePlayer(UUID.fromString(uuidArgs[0]));
													int X = Integer.parseInt(uuidArgs[1]);
													int Y = Integer.parseInt(uuidArgs[2]);
													int Z = Integer.parseInt(uuidArgs[3]);
													Block block1 = ship.getLocation().getBlock();
													Block block2 = block1.getRelative(X, Y, Z);
													BlockVector vector = new BlockVector(block1, block2);
													locationB.put(player2, vector);
												}
											}
											vesselType.loadVesselFromFiveFile(ship, file);
											ship.save();
											return true;
										}
										sender.sendMessage(Ships.runShipsMessage("Vessel Loader: " + name + " has not loaded. This is because " + vesselTypeS + " has not loaded into Ships VesselType data system. This is a programming issue", true));
										break block13;
									}
									sender.sendMessage(Ships.runShipsMessage("Vessel Loader: " + name + " sign can not be located", true));
									break block13;
								}
								sender.sendMessage(Ships.runShipsMessage("Vessel Loader: " + name + " world not loaded. Contact MoseMister with your world loading plugin", true));
								break block13;
							}
							sender.sendMessage(Ships.runShipsMessage("Vessel Loader: " + name + " teleport location has failed to load. Check config for 'Teleport' co-ods", true));
							break block13;
						}
						sender.sendMessage(Ships.runShipsMessage("Vessel Loader: " + name + " sign location has failed to load. Check config for 'Sign' co-ods", true));
						break block13;
					}
					sender.sendMessage(Ships.runShipsMessage("Vessel Loader:" + name + " failed reading player", true));
					break block13;
				}
				sender.sendMessage(Ships.runShipsMessage("VesselType Loader: " + name + " VesselType has not been found in data file. Check config for 'Type'", true));
			} catch (IllegalArgumentException sUUID) {
			}
		}
		return false;
	}

	public static List<File> getUnloadedVessels() {
		ArrayList<File> files2 = new ArrayList<File>();
		File[] files = new File("plugins/Ships/VesselData/").listFiles();
		if (files != null) {
			for (File file : files) {
				for (LoadableShip vessel : LoadableShip.getShips()) {
					if (vessel.getFile().equals(file))
						continue;
					files2.add(file);
				}
			}
		}
		return files2;
	}

	public static LoadableShip loadUnloadedVessel(Sign sign) {
		if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")) {
			File[] files;
			VesselType vesselType = VesselType.getTypeByName(sign.getLine(1).replace(ChatColor.BLUE + "", ""));
			String name = sign.getLine(2).replace(ChatColor.GREEN + "", "");
			if (vesselType != null && (files = new File("plugins/Ships/VesselData/").listFiles()) != null) {
				for (File file : files) {
					if (!file.getName().replace(".yml", "").equalsIgnoreCase(name))
						continue;
					YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
					String[] location = config.getString("ShipsData.Location.Teleport").split(",");
					Location teleport = new Location(Bukkit.getWorld(location[3]), Double.parseDouble(location[0]), Double.parseDouble(location[1]), Double.parseDouble(location[2]));
					OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(UUID.fromString(config.getString("ShipsData.Player.Name")));
					try {
						LoadableShip vessel = new LoadableShip(vesselType, sign, teleport, player);
						return vessel;
					} catch (InvalidSignException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}
}

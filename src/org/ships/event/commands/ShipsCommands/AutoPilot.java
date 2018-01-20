package org.ships.event.commands.ShipsCommands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.ships.event.commands.CommandLauncher;
import org.ships.plugin.Ships;
import org.ships.ship.LoadableShip;
import org.ships.ship.movement.AutoPilotData;

public class AutoPilot extends CommandLauncher {

	public AutoPilot() {
		super("AutoPilot", "<vessel name> <X> <Y> <Z>", "Move your ship to select co-ords", "ships.command.autopilot",
				true, false);
	}

	@Override
	public void playerCommand(Player player, String[] args) {
		if (args.length >= 4) {
			LoadableShip vessel = LoadableShip.getShip(args[1]);
			if (vessel == null) {
				player.sendMessage(Ships.runShipsMessage("No ship found by that name", true));
				return;
			} else {
				if (vessel.getVesselType().isAutoPilotAllowed()) {
					World world = vessel.getTeleportLocation().getWorld();
					if ((player.equals(vessel.getOwner())) || (vessel.getSubPilots().contains(player.getUniqueId()))) {
						try {
							int X = Integer.parseInt(args[2]);
							int Y = Integer.parseInt(args[3]);
							int Z = Integer.parseInt(args[4]);
							Location loc = new Location(world, X, Y, Z);
							AutoPilotData data = new AutoPilotData(vessel, loc,
									vessel.getVesselType().getDefaultSpeed(), player);
							vessel.setAutoPilotData(data);
							player.sendMessage(
									Ships.runShipsMessage(vessel.getName() + " is moving to " + loc.getWorld().getName()
											+ " " + loc.getX() + " " + loc.getY() + " " + loc.getZ(), false));
						} catch (NumberFormatException e) {
							player.sendMessage(Ships.runShipsMessage("X Y Z need to be whole numbers", true));
						}
					} else {
						player.sendMessage(Ships.runShipsMessage("Not your vessel", true));
					}
				} else {
					player.sendMessage(Ships.runShipsMessage("Only Air based ships can use AutoPilot", true));
				}
			}
		} else {
			player.sendMessage(ChatColor.GOLD + this.getCommand() + " " + this.getExtraArgs() + ChatColor.AQUA + ": "
					+ this.getDescription());
		}
	}

	@Override
	public void consoleCommand(ConsoleCommandSender sender, String[] args) {
		if (args.length >= 4) {
			LoadableShip vessel = LoadableShip.getShip(args[1]);
			if (vessel == null) {
				sender.sendMessage(Ships.runShipsMessage("No ship found by that name", true));
				return;
			} else {
				if (vessel.getVesselType().isAutoPilotAllowed()) {
					World world = vessel.getTeleportLocation().getWorld();
					try {
						int X = Integer.parseInt(args[2]);
						int Y = Integer.parseInt(args[3]);
						int Z = Integer.parseInt(args[4]);
						Location loc = new Location(world, X, Y, Z);
						AutoPilotData data = new AutoPilotData(vessel, loc, vessel.getVesselType().getDefaultSpeed(),
								null);
						vessel.setAutoPilotData(data);
						sender.sendMessage(
								Ships.runShipsMessage(vessel.getName() + " is moving to " + loc.getWorld().getName()
										+ " " + loc.getX() + " " + loc.getY() + " " + loc.getZ(), false));
					} catch (NumberFormatException e) {
						sender.sendMessage(Ships.runShipsMessage("X Y Z need to be whole numbers", true));
					}
				} else {
					sender.sendMessage(Ships.runShipsMessage("Only Air based ships can use AutoPilot", true));
				}
			}
		} else {
			sender.sendMessage(ChatColor.GOLD + this.getCommand() + " " + this.getExtraArgs() + ChatColor.AQUA + ": "
					+ this.getDescription());
		}
	}

}

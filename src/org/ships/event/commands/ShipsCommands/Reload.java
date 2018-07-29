package org.ships.event.commands.ShipsCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.ships.configuration.BlockList;
import org.ships.configuration.Config;
import org.ships.event.commands.CommandLauncher;
import org.ships.plugin.Ships;
import org.ships.ship.LoadableShip;
import org.ships.ship.type.VesselType;

public class Reload extends CommandLauncher {
	public Reload() {
		super("Reload", "<<vessel name>/configs>", "reloads something", "ships.command.reload", true, true);
	}

	@Override
	public void playerCommand(Player player, String[] args) {
		if (args.length == 1) {
			player.sendMessage(Ships.runShipsMessage("/Ships reload <<vesselname>/configs>", true));
		} else if (args[1].equalsIgnoreCase("configs")) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Ships.getPlugin(), () -> {
				new Config();
				BlockList.BLOCK_LIST.load(BlockList.BLOCK_LIST_FILE, BlockList.BLOCK_LIST_YAML);
				player.sendMessage(Ships.runShipsMessage("reload complete (config, materials list)", false));
			});
		} else if (VesselType.getTypeByName(args[1]) != null) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Ships.getPlugin(), () -> {
				VesselType.getTypeByName(args[1]).loadDefault();
				player.sendMessage(Ships.runShipsMessage("reload complete (" + args[1] + " vesseltype)", false));
			});
		} else {
			LoadableShip vessel = LoadableShip.getShip(args[1]);
			if (vessel == null) {
				player.sendMessage(Ships.runShipsMessage("No vessel by that name", true));
				return;
			}
			vessel.reload();
			player.sendMessage(Ships.runShipsMessage("reload complete (" + args[1] + " vessel)", false));
		}
	}

	@Override
	public void consoleCommand(ConsoleCommandSender sender, String[] args) {
		if (args.length == 1) {
			sender.sendMessage(Ships.runShipsMessage("/Ships reload <<vesselname>/configs>", true));
		} else if (args[1].equalsIgnoreCase("configs")) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Ships.getPlugin(), () -> {
				new Config();
				BlockList.BLOCK_LIST.load(BlockList.BLOCK_LIST_FILE, BlockList.BLOCK_LIST_YAML);
			});
		} else {
			LoadableShip vessel = LoadableShip.getShip(args[1]);
			if (vessel == null) {
				sender.sendMessage(Ships.runShipsMessage("No vessel by that name", true));
				return;
			}
			vessel.reload();
		}
	}
}

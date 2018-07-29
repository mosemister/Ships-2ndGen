package org.ships.event.commands.ShipsCommands;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.ships.event.commands.CommandLauncher;
import org.ships.plugin.Ships;
import org.ships.ship.LoadableShip;

public class Fixes extends CommandLauncher {
	public Fixes() {
		super("Fixes", "", "Some commands that fix some issues you maybe having", "ships.command.fixes", true, false);
	}

	@Override
	public void playerCommand(Player player, String[] args) {
		if (args.length == 1) {
			player.sendMessage(ChatColor.GOLD + "/ships fixes teleport" + ChatColor.AQUA + "; repositions the teleporting to ships to your current location");
			player.sendMessage(ChatColor.GOLD + "/ships fixes facing" + ChatColor.AQUA + "; points forward to your looking direction");
		} else if (args[1].equalsIgnoreCase("teleport")) {
			Block block = player.getLocation().getBlock().getRelative(0, -1, 0);
			LoadableShip vessel = LoadableShip.getShip(block, false);
			if (vessel == null) {
				player.sendMessage(Ships.runShipsMessage("must be standing on your vessel", true));
			} else if (vessel.getOwner().equals(player)) {
				vessel.setTeleportLocation(player.getLocation());
				player.sendMessage(Ships.runShipsMessage("new teleport location has been set for " + vessel.getName(), false));
			} else {
				player.sendMessage(Ships.runShipsMessage("must be your vessel", true));
			}
		}
	}

	@Override
	public void consoleCommand(ConsoleCommandSender sender, String[] args) {
	}
}

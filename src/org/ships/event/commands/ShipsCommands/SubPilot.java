package org.ships.event.commands.ShipsCommands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.ships.event.commands.CommandLauncher;
import org.ships.plugin.Ships;
import org.ships.ship.LoadableShip;

public class SubPilot extends CommandLauncher {

	public SubPilot() {
		super("SubPilot", "<add/remove/list> <player>", "Add or remove a player", "ships.command.subpilot", true,
				false);
	}

	@Override
	public void playerCommand(Player player, String[] args) {
		Block block = player.getLocation().getBlock().getRelative(0, -1, 0);
		LoadableShip vessel = LoadableShip.getShip(block, false);
		if (vessel == null) {
			player.sendMessage(Ships.runShipsMessage("must be standing on your vessel", true));
		} else {
			if (args.length >= 3) {
				if (vessel.getOwner().equals(player)) {
					@SuppressWarnings("deprecation")
					OfflinePlayer off = Bukkit.getOfflinePlayer(args[2]);
					if (args[1].equalsIgnoreCase("add")) {
						vessel.getSubPilots().add(off.getUniqueId());
					} else if (args[1].equalsIgnoreCase("remove")) {
						vessel.getSubPilots().remove(off.getUniqueId());
					} else if (args[1].equalsIgnoreCase("list")) {
						displayList(vessel, player);
					}
				} else {
					player.sendMessage(Ships.runShipsMessage("You are not the owner", true));
				}
			} else if (args.length == 2) {
				if (args[1].equalsIgnoreCase("list")) {
					displayList(vessel, player);
				}
			}
		}
	}

	public void displayList(LoadableShip vessel, Player player) {
		List<String> names = new ArrayList<String>();
		for (UUID pilot : vessel.getSubPilots()) {
			names.add(Bukkit.getOfflinePlayer(pilot).getName());
		}
		player.sendMessage(ChatColor.AQUA + "--------[" + vessel.getName() + "]--------");
		for (String name : names) {
			player.sendMessage(ChatColor.AQUA + name);
		}
		player.sendMessage(ChatColor.AQUA + "--------[Ships]--------");
	}

	@Override
	public void consoleCommand(ConsoleCommandSender sender, String[] args) {
		// TODO Auto-generated method stub

	}

}

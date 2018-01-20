package org.ships.event.commands.ShipsCommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.blockhandler.InventoryHandler;
import org.ships.block.blockhandler.TextHandler;
import org.ships.event.commands.CommandLauncher;
import org.ships.plugin.Ships;
import org.ships.ship.LoadableShip;

public class SignCommand extends CommandLauncher {

	public SignCommand() {
		super("sign", "", "Ships sign commands", "ships.command.sign", true, false);
	}

	@Override
	public void playerCommand(Player player, String[] args) {
		if (args.length == 1) {
			player.sendMessage(
					ChatColor.GOLD + "/Ships sign track [seconds]" + ChatColor.AQUA + "; shows sign connections.");
			player.sendMessage(ChatColor.GOLD + "/Ships sign transfer <new player>" + ChatColor.AQUA
					+ "; transfers the ownership of a vessel.");
		} else if (args[1].equalsIgnoreCase("track")) {
			Block block = player.getTargetBlock(new HashSet<Material>(Arrays.asList(Material.AIR)), 5);
			if (block.getState() instanceof Sign) {
				Sign sign = (Sign) block.getState();
				if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")) {
					List<Block> blocks = Ships.getBaseStructure(block);
					final List<BlockHandler> backup = new ArrayList<>();
					LoadableShip vessel = LoadableShip.getShip(sign);
					if (vessel != null) {
						for (Block block2 : blocks) {
							//MovingBlock mBlock = new MovingBlock(block2, vessel, MovementMethod.MOVE_FORWARD);
							BlockHandler sBlock = BlockHandler.getBlockHandler(block2);
							if (sBlock instanceof InventoryHandler) {
								((InventoryHandler)sBlock).saveInventory();
							}
							if(sBlock instanceof TextHandler) {
								((TextHandler)sBlock).saveText();
							}
							backup.add(sBlock);
							block2.setType(Material.BEDROCK);
						}
						if (args.length == 2) {
							Bukkit.getScheduler().scheduleSyncDelayedTask(Ships.getPlugin(), new Runnable() {

								@Override
								public void run() {
									for (BlockHandler block : backup) {
										block.apply();
									}
								}
							}, 20);
						} else if (args.length >= 3) {
							try {
								int A = Integer.parseInt(args[2]);
								Bukkit.getScheduler().scheduleSyncDelayedTask(Ships.getPlugin(), new Runnable() {

									@Override
									public void run() {
										for (BlockHandler block : backup) {
											block.apply();
										}
									}
								}, (A * 20));
							} catch (NumberFormatException e) {
								player.sendMessage(Ships.runShipsMessage("[seconds] must be whole number", true));
							}
						}
					} else {
						player.sendMessage(Ships.runShipsMessage("Vessel not found on sign", true));
					}
				} else {
					player.sendMessage(Ships.runShipsMessage("You must be looking at the ships sign", true));
				}
			} else {
				player.sendMessage(Ships.runShipsMessage("You must be looking at the ships sign", true));
			}
		} else if (args[1].equalsIgnoreCase("Transfer")) {
			if (args.length >= 3) {
				if ((player.hasPermission("ships.command.sign.transfer")) || (player.hasPermission("ships.*"))
						|| (player.hasPermission("ships.command.*"))) {
					Block block = player.getTargetBlock(new HashSet<Material>(Arrays.asList(Material.AIR)), 5);
					if (block.getState() instanceof Sign) {
						Sign sign = (Sign) block.getState();
						if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")) {
							LoadableShip vessel = LoadableShip.getShip(sign);
							if (vessel != null) {
								@SuppressWarnings("deprecation")
								OfflinePlayer user = Bukkit.getOfflinePlayer(args[2]);
								vessel.setOwner(user);
							} else {
								player.sendMessage(
										Ships.runShipsMessage("error occured, no licenced vessel to that sign", true));
								sign.getBlock().breakNaturally();
							}
						}
					} else {
						player.sendMessage(Ships.runShipsMessage("must be looking at Ships licence sign", true));
					}
				} else {
					player.sendMessage(Ships.runShipsMessage("lack required permissions", true));
				}
			} else {
				player.sendMessage(Ships.runShipsMessage("/ships sign transfer <new player>", true));
			}
		}
	}

	@Override
	public void consoleCommand(ConsoleCommandSender sender, String[] args) {
	}
}

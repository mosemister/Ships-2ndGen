package MoseShipsBukkit.Listeners.ShipsCommands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.Sets;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.Listeners.CommandLauncher;
import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.MovingShip.MovingBlock;
import MoseShipsBukkit.StillShip.SpecialBlock;
import MoseShipsBukkit.StillShip.Vessel.Vessel;

public class SignCommand extends CommandLauncher {

	public SignCommand() {
		super("sign", "", "Ships sign commands", "ships.command.sign", true, false);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void playerCommand(Player player, String[] args) {
		if (args.length == 1) {
			player.sendMessage(
					ChatColor.GOLD + "/Ships sign track [seconds]" + ChatColor.AQUA + "; shows sign connections.");
			player.sendMessage(ChatColor.GOLD + "/Ships sign transfer <new player>" + ChatColor.AQUA
					+ "; transfers the ownership of a vessel.");
		} else if (args[1].equalsIgnoreCase("track")) {
			Block block = player.getTargetBlock(Sets.newConcurrentHashSet(Arrays.asList(Material.AIR)), 5);
			if (block.getState() instanceof Sign) {
				Sign sign = (Sign) block.getState();
				if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")) {
					List<Block> blocks = Ships.getBaseStructure(block);
					final Map<MovingBlock, SpecialBlock> backupBlocks = new HashMap<MovingBlock, SpecialBlock>();
					Vessel vessel = Vessel.getVessel(sign);
					if (vessel != null) {
						for (Block block2 : blocks) {
							MovingBlock mBlock = new MovingBlock(block2, vessel, MovementMethod.MOVE_FORWARD);
							SpecialBlock sBlock = SpecialBlock.getSpecialBlock(block2);
							if (sBlock != null) {
								sBlock.clearInventory(block2);
							}
							backupBlocks.put(mBlock, sBlock);
							block2.setType(Material.BEDROCK);
						}
						if (args.length == 2) {
							Bukkit.getScheduler().scheduleSyncDelayedTask(Ships.getPlugin(), new Runnable() {

								@Override
								public void run() {
									for (Entry<MovingBlock, SpecialBlock> entry : backupBlocks.entrySet()) {
										entry.getKey().getBlock().setTypeIdAndData(entry.getKey().getId(),
												entry.getKey().getData(), false);
										if (entry.getValue() != null) {
											entry.getValue().setInventory(entry.getKey().getBlock());
										}
									}
								}
							}, 20);
						} else if (args.length >= 3) {
							try {
								int A = Integer.parseInt(args[2]);
								Bukkit.getScheduler().scheduleSyncDelayedTask(Ships.getPlugin(), new Runnable() {

									@Override
									public void run() {
										for (Entry<MovingBlock, SpecialBlock> entry : backupBlocks.entrySet()) {
											entry.getKey().getBlock().setTypeIdAndData(entry.getKey().getId(),
													entry.getKey().getData(), false);
											if (entry.getValue() != null) {
												entry.getValue().setInventory(entry.getKey().getBlock());
											}
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
					Block block = player.getTargetBlock(Sets.newConcurrentHashSet(Arrays.asList(Material.AIR)), 5);
					if (block.getState() instanceof Sign) {
						Sign sign = (Sign) block.getState();
						if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")) {
							Vessel vessel = Vessel.getVessel(sign);
							if (vessel != null) {
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

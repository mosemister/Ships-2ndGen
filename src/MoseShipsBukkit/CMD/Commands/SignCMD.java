package MoseShipsBukkit.CMD.Commands;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import MoseShipsBukkit.ShipsMain;
import MoseShipsBukkit.CMD.ShipsCMD;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Signs.ShipsSigns;

public class SignCMD implements ShipsCMD.ShipsPlayerCMD {

	public SignCMD() {
		ShipsCMD.SHIPS_COMMANDS.add(this);
	}

	@Override
	public String[] getAliases() {
		String[] args = { "sign" };
		return args;
	}

	@Override
	public String getDescription() {
		return "Sign tools";
	}

	@Override
	public String getPermission() {
		return null;
	}

	@Override
	public boolean execute(Player player, String... args) {
		if (args.length == 1) {
			player.sendMessage(ShipsMain.formatCMDHelp("/ships sign track [time]"));
			return true;
		} else if (args[1].equalsIgnoreCase("track")) {
			if (args.length == 2) {
				track(player, 3);
			} else {
				try {
					track(player, Integer.parseInt(args[2]));
				} catch (NumberFormatException e) {
					player.sendMessage(ShipsMain.format(args[2] + " is not a whole number", true));
				}
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public void track(final Player player, int sec) {
		Block loc = player.getTargetBlock(((HashSet<Byte>) null), 5);
		if (loc.getState() instanceof Sign) {
			Sign sign = (Sign) loc.getState();
			Optional<ShipsSigns.SignType> sSign = ShipsSigns.getSignType(sign);
			if (sSign.isPresent()) {
				Optional<LoadableShip> opShip = LoadableShip.getShip(loc, true);
				if (opShip.isPresent()) {
					LoadableShip ship = opShip.get();
					final List<Block> structure = ship.getBasicStructure();
					for (Block block : structure) {
						player.sendBlockChange(block.getLocation(), Material.BEDROCK, (byte) 0);
					}
					Bukkit.getScheduler().scheduleSyncDelayedTask(ShipsMain.getPlugin(), new Runnable() {

						@Override
						public void run() {
							for (Block block : structure) {
								player.sendBlockChange(block.getLocation(), block.getType(), block.getData());
							}
						}

					});
				}
			}
		}
	}

}

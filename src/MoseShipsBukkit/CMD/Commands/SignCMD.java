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
					return true;
				} catch (NumberFormatException e) {
					player.sendMessage(ShipsMain.format(args[2] + " is not a whole number", true));
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public void track(final Player player, int sec) {
		Block loc = player.getTargetBlock(((HashSet<Byte>) null), 5);
		System.out.println("targeted block found");
		if (loc.getState() instanceof Sign) {
			System.out.println("targeted block is Sign");
			Sign sign = (Sign) loc.getState();
			Optional<ShipsSigns.SignType> sSign = ShipsSigns.getSignType(sign);
			if (sSign.isPresent()) {
				System.out.println("is a ship sign");
				Optional<LoadableShip> opShip = LoadableShip.getShip(sSign.get(), sign, false);
				if (opShip.isPresent()) {
					System.out.println("Ship found");
					LoadableShip ship = opShip.get();
					final List<Block> structure = ship.updateBasicStructure();
					player.sendMessage("Now showing the structure of "+ ship.getName() + " (size of " + structure.size() + ") for " + sec + " seconds");
					for (Block block : structure) {
						player.sendBlockChange(block.getLocation(), Material.BEDROCK, (byte) 0);
					}
					Bukkit.getScheduler().scheduleSyncDelayedTask(ShipsMain.getPlugin(), new Runnable() {

						@Override
						public void run() {
							for (Block block : structure) {
								player.sendBlockChange(block.getLocation(), block.getType(), block.getData());
								if(block.getState() instanceof Sign){
									Sign sign = (Sign)block.getState();
									player.sendSignChange(block.getLocation(), sign.getLines());
								}
							}
						}

					}, (sec*20));
				}
			}
		}
	}

}

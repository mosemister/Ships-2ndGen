package MoseShipsBukkit.CMD.Commands;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import MoseShipsBukkit.ShipsMain;
import MoseShipsBukkit.CMD.ShipsCMD;
import MoseShipsBukkit.Events.Vessel.Command.ShipTrackEvent;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Signs.ShipsSigns;
import MoseShipsBukkit.Utils.State.BlockState;

public class SignCMD implements ShipsCMD.ShipsPlayerCMD {

	public SignCMD() {
		ShipsCMD.SHIPS_COMMANDS.add(this);
	}

	@Override
	public String[] getAliases() {
		String[] args = {
			"sign"
		};
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
		} else if (args[1].equalsIgnoreCase("iTrack") || args[1].equalsIgnoreCase("individualTrack")) {
			individualTrack(player);
			return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public void individualTrack(final Player player) {
		Block loc = player.getTargetBlock(((HashSet<Byte>) null), 5);
		if (loc.getState() instanceof Sign) {
			Sign sign = (Sign) loc.getState();
			Optional<ShipsSigns.SignType> sSign = ShipsSigns.getSignType(sign);
			if (sSign.isPresent()) {
				Optional<LoadableShip> opShip = LoadableShip.getShip(sSign.get(), sign, false);
				if (opShip.isPresent()) {
					LoadableShip ship = opShip.get();
					final ShipTrackEvent event = new ShipTrackEvent(ship);
					// Bukkit.getServer().getPluginManager().callEvent(event);
					int A = 0;
					for (final Entry<Location, BlockState> entry : event.getShowing().entrySet()) {
						A++;
						Bukkit.getScheduler().scheduleSyncDelayedTask(ShipsMain.getPlugin(), new Runnable() {

							@Override
							public void run() {
								player.sendBlockChange(entry.getKey(), entry.getValue().getMaterial(), entry.getValue().getData());
							}

						}, (A * 10));
						Bukkit.getScheduler().scheduleSyncDelayedTask(ShipsMain.getPlugin(), new Runnable() {

							@Override
							public void run() {
								Block block = entry.getKey().getBlock();
								player.sendBlockChange(entry.getKey(), block.getType(), block.getData());
								if (entry.getKey().getBlock().getState() instanceof Sign) {
									Sign sign = (Sign) entry.getKey().getBlock().getState();
									player.sendSignChange(entry.getKey(), sign.getLines());
								}
							}

						}, ((A + 1) * 10));
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void track(final Player player, int sec) {
		Block loc = player.getTargetBlock(((HashSet<Byte>) null), 5);
		if (loc.getState() instanceof Sign) {
			Sign sign = (Sign) loc.getState();
			Optional<ShipsSigns.SignType> sSign = ShipsSigns.getSignType(sign);
			if (sSign.isPresent()) {
				Optional<LoadableShip> opShip = LoadableShip.getShip(sSign.get(), sign, false);
				if (opShip.isPresent()) {
					LoadableShip ship = opShip.get();
					final ShipTrackEvent event = new ShipTrackEvent(ship);
					// Bukkit.getServer().getPluginManager().callEvent(event);
					player.sendMessage("Now showing the structure of " + ship.getName() + " (size of " + event.getShowing().size() + ") for " + sec + " seconds");
					for (Entry<Location, BlockState> entry : event.getShowing().entrySet()) {
						player.sendBlockChange(entry.getKey(), entry.getValue().getMaterial(), entry.getValue().getData());
					}
					Bukkit.getScheduler().scheduleSyncDelayedTask(ShipsMain.getPlugin(), new Runnable() {

						@Override
						public void run() {
							for (Entry<Location, BlockState> entry : event.getShowing().entrySet()) {
								player.sendBlockChange(entry.getKey(), entry.getValue().getMaterial(), entry.getValue().getData());
								if (entry.getKey().getBlock().getState() instanceof Sign) {
									Sign sign = (Sign) entry.getKey().getBlock().getState();
									player.sendSignChange(entry.getKey(), sign.getLines());
								}
							}
						}

					}, (sec * 20));
				}
			}
		}
	}

}

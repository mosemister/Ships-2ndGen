package MoseShipsBukkit.CMD.Commands;

import java.util.Arrays;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import MoseShipsBukkit.ShipsMain;
import MoseShipsBukkit.VersionChecking;
import MoseShipsBukkit.CMD.ShipsCMD;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Ships.VesselTypes.Loading.ShipLoader;
import MoseShipsBukkit.Ships.VesselTypes.Loading.ShipLoadingError;

public class InfoCMD implements ShipsCMD.ShipsConsoleCMD, ShipsCMD.ShipsPlayerCMD {

	public InfoCMD() {
		ShipsCMD.SHIPS_COMMANDS.add(this);
	}

	@Override
	public String[] getAliases() {
		String[] args = {
			"Info"
		};
		return args;
	}

	@Override
	public String getDescription() {
		return "This displays the basic information about ships";
	}

	@Override
	public String getPermission() {
		return null;
	}

	@Override
	public boolean execute(Player player, String... args) {
		if (args.length > 1) {
			displayInfo(player, args[1]);
		} else {
			basicInfo(player);
		}
		return true;
	}

	@Override
	public boolean execute(ConsoleCommandSender console, String... args) {
		if (args.length > 1) {
			displayInfo(console, args[1]);
		} else {
			basicInfo(console);
		}
		return true;
	}

	private void displayInfo(CommandSender source, String type) {
		if (type.equalsIgnoreCase("Basic")) {
			basicInfo(source);
		} else {
			Optional<LoadableShip> opShip = ShipLoader.loadShip(type);
			if (opShip.isPresent()) {
				shipInfo(source, opShip.get());
			} else {
				ShipLoadingError error = ShipLoader.getError(type);
				source.sendMessage(ShipsMain.format(
						"Ships failed to gain the information for that Ship. Error name of " + error.name(), true));
			}
		}
	}

	private void basicInfo(CommandSender source) {
		String MCVersion = VersionChecking.toString(VersionChecking.MINECRAFT_VERSION);
		String tMCVersion = null;
		String[] shipsVersion = ShipsMain.VERSION.split(Pattern.quote("|"));
		for (String mc : ShipsMain.TESTED_MC) {
			if (tMCVersion == null) {
				tMCVersion = mc;
			} else {
				tMCVersion = tMCVersion + ". " + mc;
			}
		}

		source.sendMessage(ShipsMain.formatCMDHelp("|----[Ships info]----|"));
		source.sendMessage(ShipsMain.formatCMDHelp("Ships Version: " + shipsVersion[0]));
		source.sendMessage(ShipsMain.formatCMDHelp("Ships Version Name: " + shipsVersion[1]));
		if (Arrays.asList(ShipsMain.TESTED_MC).contains(MCVersion)) {
			source.sendMessage(ShipsMain.formatCMDHelp("recommended MC version: (") + ChatColor.GREEN + MCVersion
					+ ShipsMain.formatCMDHelp(") " + tMCVersion));
		} else {
			source.sendMessage(ShipsMain.formatCMDHelp("recommended MC version: (") + ChatColor.RED + MCVersion
					+ ShipsMain.formatCMDHelp(") " + tMCVersion));
		}
	}

	private void shipInfo(CommandSender source, LoadableShip ship) {
		for (Entry<String, Object> entry : ship.getInfo().entrySet()) {
			String text = entry.getKey() + ShipsMain.formatCMDHelp(entry.getValue().toString());
			source.sendMessage(text);
		}
	}

}

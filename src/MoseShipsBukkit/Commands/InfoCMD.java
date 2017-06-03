package MoseShipsBukkit.Commands;

import java.util.Arrays;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Plugin.ShipsMain;
import MoseShipsBukkit.Utils.BlockFinderUtil;
import MoseShipsBukkit.Utils.MovementAlgorithmUtil;
import MoseShipsBukkit.Utils.SOptional;
import MoseShipsBukkit.Utils.VersionCheckingUtil;
import MoseShipsBukkit.Vessel.Common.OpenLoader.Loader;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;

public class InfoCMD implements ShipsCMD.ShipsConsoleCMD, ShipsCMD.ShipsPlayerCMD {

	public InfoCMD() {
		ShipsCMD.SHIPS_COMMANDS.add(this);
	}

	@Override
	public String[] getAliases() {
		String[] args = {
				"info" };
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
			SOptional<LiveShip> opShip = Loader.safeLoadShip(type);
			if (opShip.isPresent()) {
				shipInfo(source, opShip.get());
			} else {
				source.sendMessage(ShipsMain.format(
						"error when getting the ship. Type '/ships debug load <ship name>' to get the exact error",
						true));
			}
		}
	}

	private void basicInfo(CommandSender source) {
		String MCVersion = VersionCheckingUtil.toString(VersionCheckingUtil.MINECRAFT_VERSION);
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
		source.sendMessage(
				ShipsMain.formatCMDHelp("BlockFinder Algorithm: " + BlockFinderUtil.getConfigSelected().getName()));
		source.sendMessage(
				ShipsMain.formatCMDHelp("Movement Algorithm: " + MovementAlgorithmUtil.getConfig().getName()));
	}

	private void shipInfo(CommandSender source, LiveShip ship) {
		for (Entry<String, Object> entry : ship.getInfo().entrySet()) {
			String text = entry.getKey() + ShipsMain.formatCMDHelp(entry.getValue().toString());
			source.sendMessage(text);
		}
	}

}

package MoseShipsBukkit;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import MoseShipsBukkit.CMD.Commands.InfoCMD;
import MoseShipsBukkit.CMD.Commands.DebugCMD;
import MoseShipsBukkit.CMD.Commands.SignCMD;
import MoseShipsBukkit.Ships.VesselTypes.DefaultTypes.AirTypes.OpShip;
import MoseShipsBukkit.CMD.ShipsCMD;
import MoseShipsBukkit.Listeners.ShipsListeners;

public class ShipsMain extends JavaPlugin {

	public static String NAME;
	public static String VERSION;
	public static final String[] TESTED_MC = { "1.10.2" };

	static ShipsMain PLUGIN;

	private void registerCMDs() {
		new InfoCMD();
		new DebugCMD();
		new SignCMD();
	}

	private void registerShipTypes() {
		new OpShip.StaticOPShip();
	}

	public void onEnable() {
		PLUGIN = this;
		NAME = getName();
		VERSION = getDescription().getVersion();
		getServer().getPluginManager().registerEvents(new ShipsListeners(), this);
		getCommand("Ships").setExecutor(new ShipsCMD.Executer());
		registerCMDs();
		registerShipTypes();
		VersionChecking.VersionOutcome previous = null;
		for (String tested : TESTED_MC) {
			VersionChecking.VersionOutcome outcome = VersionChecking.isGreater(VersionChecking.MINECRAFT_VERSION,
					VersionChecking.convert(tested));
			if (outcome.equals(VersionChecking.VersionOutcome.EQUAL)) {
				previous = outcome;
				break;
			} else if ((previous != null) && (!outcome.equals(previous))) {
				previous = null;
				break;
			} else {
				previous = outcome;
			}
		}
		if (previous != null) {
			ConsoleCommandSender console = getServer().getConsoleSender();
			switch (previous) {
			case EQUAL:
				console.sendMessage(ChatColor.GREEN + "Your MC version has been tested with Ships");
				break;
			case GREATER:
				console.sendMessage(ChatColor.YELLOW
						+ "Your MC version is greater then the tested versions. Ships should be uneffected however please be keep in mind that you should look for updates as your MC version is unsupported");
				break;
			case LOWER:
				console.sendMessage(ChatColor.RED
						+ "Your MC version is lower then the tested versions. Ships is not supported at all, Please note that Ships may still work");
				break;
			}
		}else{
			ConsoleCommandSender console = getServer().getConsoleSender();
			console.sendMessage(ChatColor.RED + "Your MC version is between tested versions. It is unlikely there will be a upgrade for ships to your MC version. Please keep in mind that Ships should still work fine.");
		}
	}

	public static String formatCMDHelp(String message) {
		return ChatColor.AQUA + message;
	}

	public static String format(String message, boolean error) {
		if (error) {
			String ret = ChatColor.GOLD + "[Ships] " + ChatColor.RED + message;
			return ret;
		} else {
			String ret = ChatColor.GOLD + "[Ships] " + ChatColor.AQUA + message;
			return ret;
		}
	}

	public static ShipsMain getPlugin() {
		return PLUGIN;
	}

}

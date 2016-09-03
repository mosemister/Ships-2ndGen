package MoseShipsSponge;

import java.util.Optional;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import MoseShipsSponge.CMD.ShipsCMD;
import MoseShipsSponge.CMD.Commands.DebugCMD;
import MoseShipsSponge.CMD.Commands.InfoCMD;
import MoseShipsSponge.CMD.Commands.SignCMD;
import MoseShipsSponge.Listeners.ShipsListeners;
import MoseShipsSponge.Ships.VesselTypes.DefaultTypes.AirTypes.OpShip;

public class ShipsMain extends JavaPlugin{

	public static final String NAME = getPlugin().getName();
	public static final String VERSION = getPlugin().getVersion();
	public static final String[] TESTED_MC = {"1.10.2"};

	static ShipsMain PLUGIN;

	private void registerCMDs(){
		new InfoCMD();
		new DebugCMD();
		new SignCMD();
	}
	
	public void onEnable() {
		PLUGIN = this;
		new OpShip.StaticOPShip();
		getServer().getPluginManager().registerEvents(this, new ShipsListeners());
		getCommand("Ships").setExecutor(new ShipsCMD.Executer());
		registerCMDs();
	}
	
	public static String formatCMDHelp(String message){
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

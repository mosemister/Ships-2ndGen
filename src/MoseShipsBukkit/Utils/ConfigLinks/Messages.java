package MoseShipsBukkit.Utils.ConfigLinks;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import MoseShipsBukkit.Ships;

public class Messages {
	
	static String SHIPTOOSMALL;
	static String SHIPTOOBIG;
	static String OUTOFFUEL;
	static String MUSTBEIN;
	static String NEEDS;
	static String OFFBY;
	static String FOUNDINWAY;
	
	public static String getShipTooSmall(int currentSize, int min){
		String message = SHIPTOOSMALL;
		if (message != null){
			if (message.contains("%currentSize%")){
				message = message.replace("%currentSize%", currentSize + "");
			}
			if (message.contains("%minForVessel%")){
				message = message.replace("%minForVessel%", min + "");
			}
		}
		return message;
	}
	
	public static String getShipTooBig(int currentSize, int max){
		String message = SHIPTOOBIG;
		if (message != null){
			if (message.contains("%currentSize%")){
				message = message.replace("%currentSize%", currentSize + "");
			}
			if (message.contains("%maxForVessel%")){
				message = message.replace("%maxForVessel%", max + "");
			}
		}
		return message;
	}
	
	public static String getOutOfFuel(String fuel){
		String message = OUTOFFUEL;
		if (message.contains("%fuel%")){
			message = message.replace("%fuel%", fuel);
		}
		return message;
	}
	
	public static String getMustBeIn(String material){
		String message = MUSTBEIN;
		if (message != null){
			if (message.contains("%material%")){
				message = message.replace("%material%", material);
			}
		}
		return message;
	}
	
	public static String getNeeds(String needs){
		String message = NEEDS;
		if (message != null){
			if (message.contains("%material%")){
				message = message.replace("%material%", needs + "");
			}
		}
		return message;
	}
	
	public static String getOffBy(float f, String material){
		String message = OFFBY;
		if (message != null){
			if (message.contains("%amount%")){
				message = message.replace("%amount%", f + "");
			}
			if (message.contains("%material%")){
				message = message.replace("%material%", material);
			}
		}
		return message;
	}
	
	public static String getFoundInWay(String material){
		String message = FOUNDINWAY;
		if (message.contains("%material%")){
			message = message.replace("%material%", material);
		}
		return message;
	}
	
	public static boolean isEnabled(){
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		boolean result = config.getBoolean("Messages.enabled");
		return result;
	}
	
	public static void refreshMessages(){
		Bukkit.getScheduler().scheduleSyncDelayedTask(Ships.getPlugin(), new Runnable(){

			@Override
			public void run() {
				YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
				SHIPTOOSMALL = config.getString("Messages.ShipTooSmall");
				SHIPTOOBIG = config.getString("Messages.ShipTooBig");
				OUTOFFUEL = config.getString("Messages.OutOfFuel");
				MUSTBEIN = config.getString("Messages.MustBeIn");
				NEEDS = config.getString("Messages.Needs");
				OFFBY = config.getString("Messages.OffBy");
				FOUNDINWAY = config.getString("Messages.FoundInWay");
			}
			
		}, 0);
	}

}

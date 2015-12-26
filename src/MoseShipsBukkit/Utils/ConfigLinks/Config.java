package MoseShipsBukkit.Utils.ConfigLinks;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import MoseShipsBukkit.Ships;

public class Config {
	
	public static Config CONFIG = new Config();
	boolean NEEDEDUPDATE;
	
	public File createDefaultFile() throws IOException{
		File file = new File("plugins/Ships/Configuration/Config.yml");
		Ships.copy(Ships.getInputFromJar("Config.yml"), file);
		File returnFile = new File("plugins/Ships/Configuration/Config.yml");
		return returnFile;
	}
	
	public File getFile(){
		File file = new File("plugins/Ships/Configuration/Config.yml");
		return file;
	}
	
	public String[] getConfigVersion(){
		File file = getFile();
		if (file.exists()){
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			String version = config.getString("Version");
			String[] args = version.split(".");
			return args;
		}else{
			return null;
		}
	}
	
	public String getConfigVersionString(){
		File file = getFile();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		String version = config.getString("Version");
		return version;
	}
	
	public int getConfigVersionInt(){
		String versionString = getConfigVersionString().replace(".", "");
		
		System.out.println(versionString);
		int version = Integer.parseInt(versionString);
		return version;
	}
	
	public String[] getLatestVersion(){
		String version = Ships.getPlugin().getDescription().getVersion();
		String[] args = version.split(".");
		return args;
	}
	
	public String getLatestVersionString(){
		String version = Ships.getPlugin().getDescription().getVersion();
		return version;
	}
	
	public int getLatestVersionInt(){
		int version = Integer.parseInt(getLatestVersionString().replace(".", ""));
		return version;
	}
	
	public boolean containsIgnoreList(int lastest){
		Integer[] list = {5011, 5012};
		for (int A : list){
			if (lastest == A){
				return true;
			}
		}
		return false;
	}
	
	public boolean updateCheck(){
		String latest = getLatestVersionString().replace(".", "");
		String current = getConfigVersionString();
		if (NEEDEDUPDATE){
			return true;
		}
		if (current == null){
			update();
			NEEDEDUPDATE = true;
			return true;
		}
		current = current.replace(".", "");
		int latestN = Integer.parseInt(latest);
		int currentN = Integer.parseInt(current);
		int result = latestN - currentN;
		if ((result == 0) || (containsIgnoreList(latestN))){
			if (containsIgnoreList(latestN)){
				try{
					File file2 = getFile();
					YamlConfiguration config2 = YamlConfiguration.loadConfiguration(file2);
					config2.set("Version", getLatestVersionString());
					config2.save(file2);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return false;
		}else{
			Bukkit.getConsoleSender().sendMessage(Ships.runShipsMessage("Your config maybe out of date. \n"
					+ "Ships 5 found the current version of your config to be " + getConfigVersionString() + " and the latest to be " + getLatestVersionString() + ". \n"
					+ "Your config is now updating to the latest version, this new config may have some features that you wish to configure, reload the config after making changes if any are made.", true));
			update();
			NEEDEDUPDATE = true;
			return true;
		}
	}
	
	void update(){
		File file = getFile();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (file.exists()){
			int curVersion = getConfigVersionInt();
			if (curVersion > 5010){
				config.set("Structure.airCheckGap", 120);
				config.set("Structure.trackLimit", 5000);
			}
			//compare version then update
			config.set("Version", getLatestVersionString());
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				File file2 = createDefaultFile();
				YamlConfiguration config2 = YamlConfiguration.loadConfiguration(file2);
				config2.set("Version", getLatestVersionString());
				config2.save(file2);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Config getConfig(){
		return CONFIG;
	}

}

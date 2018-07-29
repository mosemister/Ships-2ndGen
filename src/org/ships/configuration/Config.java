package org.ships.configuration;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.ships.plugin.Ships;

public class Config {
	public static Config CONFIG = new Config();
	boolean NEEDEDUPDATE;

	public File createDefaultFile() throws IOException {
		File file = new File("plugins/Ships/Configuration/Config.yml");
		Ships.copy(Ships.getInputFromJar("Config.yml"), file);
		File returnFile = new File("plugins/Ships/Configuration/Config.yml");
		return returnFile;
	}

	public File getFile() {
		File file = new File("plugins/Ships/Configuration/Config.yml");
		return file;
	}

	public String[] getConfigVersion() {
		File file = this.getFile();
		if (file.exists()) {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			String version = config.getString("Version");
			String[] args = version.split(".");
			return args;
		}
		return null;
	}

	public String getConfigVersionString() {
		File file = this.getFile();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		String version = config.getString("Version");
		return version;
	}

	public int getConfigVersionInt() {
		String versionString = this.getConfigVersionString();
		if (versionString == null) {
			return 0;
		}
		versionString = versionString.replace(".", "");
		int version = Integer.parseInt(versionString);
		return version;
	}

	public String[] getLatestVersion() {
		String version = Ships.getPlugin().getDescription().getVersion();
		String[] args = version.split(".");
		return args;
	}

	public String getLatestVersionString() {
		String version = Ships.getPlugin().getDescription().getVersion();
		return version;
	}

	public int getLatestVersionInt() {
		int version = Integer.parseInt(this.getLatestVersionString().replace(".", ""));
		return version;
	}

	public boolean containsIgnoreList(int lastest) {
		Integer[] arrinteger = new Integer[] { 5017, 5018, 5019, 50110 };
		int n = arrinteger.length;
		for (int i = 0; i < n; ++i) {
			int A = arrinteger[i];
			if (lastest != A)
				continue;
			return true;
		}
		return false;
	}

	public boolean updateCheck() {
		int currentN;
		String current = this.getConfigVersionString();
		if (this.NEEDEDUPDATE) {
			return true;
		}
		if (current == null) {
			this.update();
			this.NEEDEDUPDATE = true;
			return true;
		}
		int latestN = this.getLatestVersionInt();
		int result = latestN - (currentN = this.getConfigVersionInt());
		if (result == 0) {
			Bukkit.getConsoleSender().sendMessage("Ships config detected with no issues");
			return false;
		}
		if (this.containsIgnoreList(currentN)) {
			try {
				Bukkit.getConsoleSender().sendMessage("New version of Ships detected. Config does not need a restart");
				File file2 = this.getFile();
				YamlConfiguration config2 = YamlConfiguration.loadConfiguration(file2);
				config2.set("Version", this.getLatestVersionString());
				config2.save(file2);
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		Bukkit.getConsoleSender().sendMessage(Ships.runShipsMessage("Your config maybe out of date. \nShips 5 found the current version of your config to be " + this.getConfigVersionString() + " and the latest to be " + this.getLatestVersionString() + ". \nYour config is now updating to the latest version, this new config may have some features that you wish to configure, reload the config after making changes if any are made.", true));
		this.update();
		this.NEEDEDUPDATE = true;
		return true;
	}

	void update() {
		File file = this.getFile();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		if (file.exists()) {
			int curVersion = this.getConfigVersionInt();
			int latVersion = this.getLatestVersionInt();
			if (curVersion <= 5011 && latVersion >= 5012) {
				config.set("Structure.StructureLimits.airCheckGap", 120);
				config.set("Structure.StructureLimits.trackLimit", 5000);
			}
			if (curVersion <= 5016 && latVersion >= 5017) {
				config.set("VesselLoading.DeleteFailedLoads", false);
			}
			config.set("Version", this.getLatestVersionString());
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				File file2 = this.createDefaultFile();
				YamlConfiguration config2 = YamlConfiguration.loadConfiguration(file2);
				config2.set("Version", this.getLatestVersionString());
				config2.save(file2);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Config getConfig() {
		return CONFIG;
	}
}

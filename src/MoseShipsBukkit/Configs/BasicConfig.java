package MoseShipsBukkit.Configs;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class BasicConfig {

	protected File file;
	protected YamlConfiguration config;

	public BasicConfig(String fileName) {
		file = new File("plugins/Ships/" + fileName + ".yml");
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		config = YamlConfiguration.loadConfiguration(file);
	}

	public BasicConfig(File file) {
		this.file = file;
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		config = YamlConfiguration.loadConfiguration(file);
	}

	public File getFile() {
		return file;
	}

	public YamlConfiguration getConfig() {
		return config;
	}

	public boolean set(Object object, String path) {
		if (path != null) {
			if (!has(path)) {
				config.set(path, object);
				return true;
			}
		}
		return false;
	}

	public BasicConfig setOverride(Object object, String path) {
		if (path != null) {
			config.set(path, object);
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> T get(Class<T> type, String path) {
		if (path != null) {
			Object obj = config.get(path);
			if (type.isInstance(obj)) {
				return (T) obj;
			}
		}
		return null;
	}

	public boolean has(String path) {
		if (path != null) {
			return (config.get(path) != null);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> List<T> getList(Class<T> type, String path) {
		return (List<T>) config.getList(path);
	}

	public BasicConfig save() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

}

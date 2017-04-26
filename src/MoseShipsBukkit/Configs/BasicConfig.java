package MoseShipsBukkit.Configs;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;


/**
 * this class allows ease of creating configuration files.
 * It allows ease due to the fact you do not need to create
 * a version config within the config and then check the values,
 * instead it will not override the original value unless forced.
 *
 */
public class BasicConfig {

	protected File file;
	protected YamlConfiguration config;

	/**
	 * this creates the file in the root directory for Ships
	 * configuration. 
	 * 
	 * If there is not a file it will create a new one, if there
	 * is a file in its location, it will use that file as the target
	 * file.
	 * 
	 * if you always want to create a new file, you must check before
	 * calling this constructor.
	 */
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

	/**
	 * this creates a YAML file in the targeted location,
	 * if there is already a file there, then it will
	 * just use that file instead of creating a new file.
	 */
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
	
	/**
	 * @return (the java root file)
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @return (the Bukkit root file)
	 */
	public YamlConfiguration getConfig() {
		return config;
	}

	/**
	 * this will set the value unless there is already a value in its place.
	 * this is mainly used for creating new values/files.
	 * 
	 * the save-able object needs to be supported by Bukkit's YAML support
	 * @param object = the value to save
	 * @param path = the path to save
	 * @return = if the values is actually set or not
	 */
	public boolean set(Object object, String path) {
		if (path != null) {
			if (!has(path)) {
				config.set(path, object);
				return true;
			}
		}
		return false;
	}

	/**
	 * this will set the value in the path, no matter if there is a value
	 * in the location or not.
	 * 
     * the save-able object needs to be supported by Bukkit's YAML support
	 * @param object = the value to save
	 * @param path = the path to save
	 * @return = if the values is actually set or not
	 */
	public BasicConfig setOverride(Object object, String path) {
		if (path != null) {
			config.set(path, object);
		}
		return this;
	}
	
	/**
	 * this will get the value that is in the location as the
	 * object type that specified, if the value can not be casted
	 * then it will throw a cast exception.
	 * @param type = the object type 
	 * @param path = the path
	 * @return = the object
	 */
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

	/**
	 * checks if the path as a value or not
	 * @param path = the path (use bukkit path format - I.E use '.')
	 * @return if the path has a value
	 */
	public boolean has(String path) {
		if (path != null) {
			return (config.get(path) != null);
		}
		return false;
	}

	/**
	 * gets a list of values within the targeted path. 
	 * @param type = the object type that the list should be
	 * @param path = the location (use bukkit format)
	 * @return = the list
	 */
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

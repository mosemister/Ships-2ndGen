package MoseShipsSponge.Configs;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.reflect.TypeToken;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class BasicConfig {

	protected File file;
	protected HoconConfigurationLoader loader;
	protected ConfigurationNode root;

	public BasicConfig(String fileName) {
		file = new File("config/Ships/" + fileName + ".conf");
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		loader = HoconConfigurationLoader.builder().setFile(file).build();
		root = loader.createEmptyNode();
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
		loader = HoconConfigurationLoader.builder().setFile(file).build();
		root = loader.createEmptyNode(ConfigurationOptions.defaults());
	}

	public File getFile() {
		return file;
	}

	public HoconConfigurationLoader getLoader() {
		return loader;
	}

	public ConfigurationNode getRoot() {
		return root;
	}

	public boolean set(Object object, Object... path) {
		if (path != null) {
			if (!has(path)) {
				root.getNode(path).setValue(object);
				return true;
			}
		}
		return false;
	}

	public BasicConfig setOverride(Object object, Object... path) {
		if (path != null) {
			root.getNode(path).setValue(object);
		}
		return this;
	}

	public <T extends Object> T get(Class<T> type, Object... path) {
		if (path != null) {
			ConfigurationNode key = root.getNode(path);
			try {
				return key.getValue(TypeToken.of(type));
			} catch (ObjectMappingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public boolean has(Object... path) {
		if (path != null) {
			ConfigurationNode key = root.getNode(path);
			try {
				Object obj = key.getValue(TypeToken.of(Object.class));
				if (obj != null) {
					return true;
				}
			} catch (ObjectMappingException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public <T extends Object> List<T> getList(Function<? super ConfigurationNode, T> type, Object... path) {
		return (List<T>) root.getNode(path).getChildrenList().stream().map(type).collect(Collectors.toList());
	}

	public BasicConfig save() {
		try {
			loader.save(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

}

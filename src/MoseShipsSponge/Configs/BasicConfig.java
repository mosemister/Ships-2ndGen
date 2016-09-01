package MoseShipsSponge.Configs;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.text.Text;

import MoseShipsSponge.ShipsMain;
import MoseShipsSponge.Configs.Files.BlockList;
import MoseShipsSponge.Configs.Files.ShipsConfig;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;

public class BasicConfig {

	protected File FILE;
	protected HoconConfigurationLoader LOADER;
	protected ConfigurationNode ROOT;
	protected boolean WAS_CREATED;

	public static final BlockList BLOCK_LIST = new BlockList();
	public static final ShipsConfig CONFIG = new ShipsConfig();

	public BasicConfig(String fileName) {
		FILE = new File("config/Ships/" + fileName + ".conf");
		if (!FILE.exists()) {
			try {
				FILE.getParentFile().mkdirs();
				FILE.createNewFile();
				WAS_CREATED = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		LOADER = HoconConfigurationLoader.builder().setFile(FILE).build();
		ROOT = LOADER.createEmptyNode();
	}

	public BasicConfig(File file) {
		FILE = file;
		if (!FILE.exists()) {
			try {
				FILE.getParentFile().mkdirs();
				FILE.createNewFile();
				WAS_CREATED = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		LOADER = HoconConfigurationLoader.builder().setFile(FILE).build();
		ROOT = LOADER.createEmptyNode();
	}

	public File getFile() {
		return FILE;
	}

	public HoconConfigurationLoader getLoader() {
		return LOADER;
	}

	public ConfigurationNode getRoot() {
		return ROOT;
	}

	public boolean set(Object object, Object... path) {
		if (path != null) {
			if (has(path)) {
				ROOT.getNode(path).setValue(object);
				return true;
			}
		}
		return false;
	}

	public BasicConfig setOverride(Object object, Object... path) {
		if (path != null) {
			ConsoleSource sender = ShipsMain.getPlugin().getGame().getServer().getConsole();
			if (path.equals(ShipsConfig.PATH_STRUCTURE_STRUCTURELIMITS_TRACKLIMIT)) {
				sender.sendMessage(Text.of("Override use"));
			}
			ROOT.getNode(path).setValue(object);
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> T get(Class<T> type, Object... path) {
		if (path != null) {
			ConfigurationNode key = ROOT.getNode(path);
			if (type.isAssignableFrom(String.class)) {
				return (T) key.getString();
			} else if (type.isAssignableFrom(Integer.class)) {
				return (T) (Integer) key.getInt();
			} else if (type.isAssignableFrom(Double.class)) {
				return (T) (Double) key.getDouble();
			} else if (type.isAssignableFrom(Boolean.class)) {
				return (T) (Boolean) key.getBoolean();
			} else if (type.isAssignableFrom(Float.class)) {
				return (T) (Float) key.getFloat();
			} else if (type.isAssignableFrom(Long.class)) {
				return (T) (Long) key.getLong();
			} else {
				return (T) key.getValue();
			}
		}
		return null;
	}

	public boolean has(Object... path) {
		if (path != null) {
			ConfigurationNode key = ROOT.getNode(path);
			return key.isVirtual();
		}
		return false;
	}

	public <T extends Object> List<T> getList(Function<? super ConfigurationNode, T> type, Object... path) {
		return (List<T>) ROOT.getNode(path).getChildrenList().stream().map(type).collect(Collectors.toList());
	}

	public BasicConfig save() {
		try {
			LOADER.save(ROOT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

}

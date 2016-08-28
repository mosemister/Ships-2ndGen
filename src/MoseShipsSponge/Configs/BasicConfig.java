package MoseShipsSponge.Configs;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
		FILE = new File("config/Ships/" + file.getPath() + ".conf");
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
		Object object2 = ROOT.getNode(path).getValue();
		System.out.println("config found \n " + object2);
		if(object2 == null){
			ROOT.getNode(path).setValue(object);
			return true;
		}
		return false;
	}
	
	public BasicConfig setOverride(Object object, Object... path){
		ROOT.getNode(path).setValue(object);
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> T get(Class<T> type, Object... path) {
		Object object = ROOT.getNode(path).getValue();
		if (type.isInstance(object)) {
			return (T) object;
		} else {
			return null;
		}
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

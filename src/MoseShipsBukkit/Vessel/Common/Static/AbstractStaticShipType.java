package MoseShipsBukkit.Vessel.Common.Static;

import java.io.File;

import org.bukkit.plugin.Plugin;

public abstract class AbstractStaticShipType implements StaticShipType {

	protected String name;
	protected int defaultSpeed;
	protected int defaultBoostSpeed;
	protected int defaultAltitudeSpeed;
	protected int defaultMaxSize;
	protected int defaultMinSize;
	protected Plugin plugin;
	
	public AbstractStaticShipType(String name, int min, int max, int speed, int boost, int altitude, Plugin plugin) {
		this.name = name;
		this.defaultAltitudeSpeed = altitude;
		this.defaultBoostSpeed = boost;
		this.defaultSpeed = speed;
		this.plugin = plugin;
		this.defaultMinSize = min;
		this.defaultMaxSize = max;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int getDefaultSpeed() {
		return defaultSpeed;
	}

	@Override
	public void setDefaultSpeed(int speed) {
		defaultSpeed = speed;
	}

	@Override
	public int getBoostSpeed() {
		return defaultBoostSpeed;
	}

	@Override
	public void setBoostSpeed(int speed) {
		defaultBoostSpeed = speed;
	}

	@Override
	public int getAltitudeSpeed() {
		return defaultAltitudeSpeed;
	}

	@Override
	public void setAltitudeSpeed(int speed) {
		defaultAltitudeSpeed = speed;
		
	}
	
	@Override
	public int getMaxSize() {
		return defaultMaxSize;
	}
	
	@Override
	public void setMaxSize(int defaultMaxSize) {
		this.defaultMaxSize = defaultMaxSize;
	}
	
	@Override
	public int getMinSize() {
		return defaultMinSize;
	}
	
	@Override
	public void setMinSize(int size) {
		this.defaultMinSize = size;
	}

	@Override
	public Plugin getPlugin() {
		return plugin;
	}
	
	@Override
	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public File getFile() {
		return new File("plugins/Ships/Configuration/ShipTypes/" + getName() + ".yml");
	}

}

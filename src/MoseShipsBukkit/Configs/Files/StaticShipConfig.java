package MoseShipsBukkit.Configs.Files;

import java.io.File;

import MoseShipsBukkit.Configs.BasicConfig;

public class StaticShipConfig extends BasicConfig {
	
	public static final String DATABASE_DEFAULT_SPEED = "Default.Speed.Speed";
	public static final String DATABASE_DEFAULT_BOOST = "Default.Speed.Boost";
	public static final String DATABASE_DEFAULT_ALTITUDE = "Default.Speed.Altitude";
	public static final String DATABASE_DEFAULT_MAX_SIZE = "Default.Size.Max";
	public static final String DATABASE_DEFAULT_MIN_SIZE = "Default.Size.Min";
	
	public static final String DATABASE_REQUIRED_BLOCKS = "Default.Requirement.Block.Blocks";
	public static final String DATABASE_REQUIRED_PERCENT = "Default.Requirement.Block.Percent";

	public StaticShipConfig(File file) {
		super(file);
	}
	
	public StaticShipConfig(String name) {
		super(new File("plugins/Ships/Configuration/ShipTypes/" + name + ".yml"));
	}
	
	public int getDefaultSpeed(){
		Integer speed = get(Integer.class, DATABASE_DEFAULT_SPEED);
		return speed;
	}
	
	public int getDefaultBoostSpeed(){
		return get(Integer.class, DATABASE_DEFAULT_BOOST);
	}
	
	public int getDefaultAltitudeSpeed(){
		return get(Integer.class, DATABASE_DEFAULT_ALTITUDE);
	}
	
	public int getDefaultMaxSize(){
		return get(Integer.class, DATABASE_DEFAULT_MAX_SIZE);
	}
	
	public int getDefaultMinSize(){
		return get(Integer.class, DATABASE_DEFAULT_MIN_SIZE);
	}

}

package MoseShipsBukkit.Configs.Files;

import java.io.File;
import java.io.IOException;

import MoseShipsBukkit.Configs.BasicConfig;

public class StaticShipConfig extends BasicConfig {
	
	public static final String DATABASE_DEFAULT_SPEED = "";
	public static final String DATABASE_DEFAULT_BOOST = "";
	public static final String DATABASE_DEFAULT_ALTITUDE = "";
	public static final String DATABASE_DEFAULT_MAX_SIZE = "";
	public static final String DATABASE_DEFAULT_MIN_SIZE = "";

	public StaticShipConfig(File file) {
		super(file);
		new IOException("File config " + file.getName()).printStackTrace();
	}
	
	public StaticShipConfig(String name) {
		super(new File("plugins/Ships/Configuration/ShipTypes/" + name + ".yml"));
		new IOException("File config " + name).printStackTrace();
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

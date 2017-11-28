package MoseShipsBukkit.Vessel.Common.Static;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Utils.SOptional;
import MoseShipsBukkit.Vessel.Common.OpenLoader.OpenRAWLoader;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.Common.RootTypes.ShipsData;

public interface StaticShipType {

	public static List<StaticShipType> TYPES = new ArrayList<StaticShipType>();

	public String getName();
	
	public void setName(String name);
	
	public int getMaxSize();
	
	public void setMaxSize(int size);
	
	public int getMinSize();
	
	public void setMinSize(int size);

	public int getDefaultSpeed();
	
	public void setDefaultSpeed(int speed);

	public int getBoostSpeed();
	
	public void setBoostSpeed(int speed);

	public int getAltitudeSpeed();
	
	public void setAltitudeSpeed(int speed);

	public Plugin getPlugin();
	
	public void setPlugin(Plugin plugin);
	
	public File getFile();

	public OpenRAWLoader[] getLoaders();
	
	public void saveDefaults(File file);
	
	public void loadDefaults(File file);
	
	public StaticShipType copy(String name, Plugin plugin);

	public SOptional<LiveShip> createVessel(String name, Block licence);

	public SOptional<LiveShip> loadVessel(ShipsData data, BasicConfig config);

}

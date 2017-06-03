package MoseShipsBukkit.Vessel.Common.Static;

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

	public int getDefaultSpeed();

	public int getBoostSpeed();

	public int getAltitudeSpeed();

	public Plugin getPlugin();

	public OpenRAWLoader[] getLoaders();

	public SOptional<LiveShip> createVessel(String name, Block licence);

	public SOptional<LiveShip> loadVessel(ShipsData data, BasicConfig config);

}

package MoseShipsBukkit.Ships.VesselTypes.Satic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Ships.AbstractShipsData;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveShip;

public interface StaticShipType {

	public static List<StaticShipType> TYPES = new ArrayList<StaticShipType>();

	public String getName();

	public int getDefaultSpeed();

	public int getBoostSpeed();

	public int getAltitudeSpeed();

	public boolean autoPilot();
	
	public Plugin getPlugin();

	public Optional<LiveShip> createVessel(String name, Block licence);

	public Optional<LiveShip> loadVessel(AbstractShipsData data, BasicConfig config);

}

package MoseShipsSponge.Ships.VesselTypes.Satic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.block.Block;

import MoseShipsSponge.Configs.BasicConfig;
import MoseShipsSponge.Ships.ShipsData;
import MoseShipsSponge.Ships.VesselTypes.LoadableShip;

public interface StaticShipType {

	List<StaticShipType> TYPES = new ArrayList<StaticShipType>();

	public String getName();

	public int getDefaultSpeed();

	public int getBoostSpeed();

	public int getAltitudeSpeed();

	public boolean autoPilot();

	public Optional<LoadableShip> createVessel(String name, Block licence);

	public Optional<LoadableShip> loadVessel(ShipsData data, BasicConfig config);

}

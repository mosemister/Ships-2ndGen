package MoseShipsSponge.Vessel.Common.Static;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Configs.BasicConfig;
import MoseShipsSponge.Vessel.Common.OpenLoader.OpenRAWLoader;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;
import MoseShipsSponge.Vessel.Common.RootTypes.ShipsData;

public interface StaticShipType {

	public static final List<StaticShipType> TYPES = new ArrayList<>();

	public String getName();

	public int getDefaultSpeed();

	public int getBoostSpeed();

	public int getAltitudeSpeed();

	public PluginContainer getPlugin();

	public OpenRAWLoader[] getLoaders();

	public Optional<LiveShip> createVessel(String name, Location<World> licence);

	public Optional<LiveShip> loadVessel(ShipsData data, BasicConfig config);

}

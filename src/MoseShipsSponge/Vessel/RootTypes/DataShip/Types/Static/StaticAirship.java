package MoseShipsSponge.Vessel.RootTypes.DataShip.Types.Static;

import java.util.Optional;

import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Configs.BasicConfig;
import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.Utils.StaticShipTypeUtil;
import MoseShipsSponge.Vessel.Common.OpenLoader.OpenRAWLoader;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;
import MoseShipsSponge.Vessel.Common.RootTypes.ShipsData;
import MoseShipsSponge.Vessel.Common.Static.StaticShipType;
import MoseShipsSponge.Vessel.RootTypes.DataShip.Loader.Types.Airship.Ships6AirshipLoader;
import MoseShipsSponge.Vessel.RootTypes.DataShip.Types.Airship;

public class StaticAirship implements StaticShipType {
	
	public StaticAirship(){
		StaticShipTypeUtil.inject(this);
	}

	@Override
	public String getName() {
		return "Airship";
	}

	@Override
	public int getDefaultSpeed() {
		return 2;
	}

	@Override
	public int getBoostSpeed() {
		return 3;
	}

	@Override
	public int getAltitudeSpeed() {
		return 2;
	}

	@Override
	public PluginContainer getPlugin() {
		return ShipsMain.getPlugin().getContainer();
	}

	@Override
	public OpenRAWLoader[] getLoaders() {
		OpenRAWLoader[] loaders = {new Ships6AirshipLoader()};
		return loaders;
	}

	@Override
	public Optional<LiveShip> createVessel(String name, Location<World> licence) {
		Airship ship = new Airship(name, licence, licence.getBlockPosition());
		return Optional.of(ship);
	}

	@Override
	public Optional<LiveShip> loadVessel(ShipsData data, BasicConfig config) {
		Airship ship = new Airship(data);
		return Optional.of(ship);
	}

}

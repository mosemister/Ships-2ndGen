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
import MoseShipsSponge.Vessel.RootTypes.DataShip.Loader.Types.Watership.Ships6WatershipLoader;
import MoseShipsSponge.Vessel.RootTypes.DataShip.Types.WaterShip;

public class StaticWaterShip implements StaticShipType{
	
	public StaticWaterShip() {
		StaticShipTypeUtil.inject(this);
	}

	@Override
	public String getName() {
		return "Ship";
	}

	@Override
	public int getDefaultSpeed() {
		return 2;
	}

	@Override
	public int getBoostSpeed() {
		return 4;
	}

	@Override
	public int getAltitudeSpeed() {
		return 1;
	}

	@Override
	public PluginContainer getPlugin() {
		return ShipsMain.getPlugin().getContainer();
	}

	@Override
	public OpenRAWLoader[] getLoaders() {
		OpenRAWLoader[] loaders = {new Ships6WatershipLoader()};
		return loaders;
	}

	@Override
	public Optional<LiveShip> createVessel(String name, Location<World> licence) {
		return Optional.of(new WaterShip(name, licence, licence.getBlockPosition()));
	}

	@Override
	public Optional<LiveShip> loadVessel(ShipsData data, BasicConfig config) {
		WaterShip ship = new WaterShip(data);
		return Optional.of(ship);
	}

}

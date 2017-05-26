package MoseShipsBukkit.Vessel.RootType.DataShip.Types.Static;

import java.util.Optional;

import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Plugin.ShipsMain;
import MoseShipsBukkit.Utils.StaticShipTypeUtil;
import MoseShipsBukkit.Vessel.Common.OpenLoader.OpenRAWLoader;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.Common.RootTypes.ShipsData;
import MoseShipsBukkit.Vessel.Common.Static.StaticShipType;
import MoseShipsBukkit.Vessel.RootType.DataShip.Loader.Types.OPShip.Ships6OPShipLoader;
import MoseShipsBukkit.Vessel.RootType.DataShip.Types.OPShip;

public class StaticOPShip implements StaticShipType {
	
	public StaticOPShip() {
		StaticShipTypeUtil.inject(this);
	}

	@Override
	public String getName() {
		return "OPShip";
	}

	@Override
	public int getDefaultSpeed() {
		return 2;
	}

	@Override
	public int getBoostSpeed() {
		return 10;
	}

	@Override
	public int getAltitudeSpeed() {
		return 1;
	}

	@Override
	public Plugin getPlugin() {
		return ShipsMain.getPlugin();
	}

	@Override
	public OpenRAWLoader[] getLoaders() {
		OpenRAWLoader[] loaders = {new Ships6OPShipLoader()};
		return loaders;
	}

	@Override
	public Optional<LiveShip> createVessel(String name, Block licence) {
		OPShip ship = new OPShip(name, licence, licence.getLocation());
		return Optional.of((LiveShip)ship);
	}

	@Override
	public Optional<LiveShip> loadVessel(ShipsData data, BasicConfig config) {
		OPShip ship = new OPShip(data);
		return Optional.of((LiveShip)ship);
	}
}
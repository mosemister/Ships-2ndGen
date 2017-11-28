package MoseShipsBukkit.Vessel.RootType.DataShip.Types.Static;

import java.io.File;

import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Plugin.ShipsMain;
import MoseShipsBukkit.Utils.SOptional;
import MoseShipsBukkit.Utils.StaticShipTypeUtil;
import MoseShipsBukkit.Vessel.Common.OpenLoader.OpenRAWLoader;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.Common.RootTypes.ShipsData;
import MoseShipsBukkit.Vessel.Common.Static.AbstractStaticShipType;
import MoseShipsBukkit.Vessel.Common.Static.StaticShipType;
import MoseShipsBukkit.Vessel.RootType.DataShip.Loader.Types.OPShip.Ships6OPShipLoader;
import MoseShipsBukkit.Vessel.RootType.DataShip.Types.OPShip;

public class StaticOPShip extends AbstractStaticShipType implements StaticShipType {
	
	public StaticOPShip() {
		super("OPShip", 0, Integer.MAX_VALUE, 2, 5, 2, ShipsMain.getPlugin());
		StaticShipTypeUtil.inject(this);
	}

	@Override
	public OpenRAWLoader[] getLoaders() {
		OpenRAWLoader[] loaders = {new Ships6OPShipLoader()};
		return loaders;
	}

	@Override
	public SOptional<LiveShip> createVessel(String name, Block licence) {
		OPShip ship = new OPShip(name, licence, licence.getLocation());
		return new SOptional<LiveShip>((LiveShip)ship);
	}

	@Override
	public SOptional<LiveShip> loadVessel(ShipsData data, BasicConfig config) {
		OPShip ship = new OPShip(data);
		return new SOptional<LiveShip>((LiveShip)ship);
	}

	@Override
	public void saveDefaults(File file) {
	}

	@Override
	public void loadDefaults(File file) {
	}

	@Override
	public StaticOPShip copy(String name, Plugin plugin) {
		StaticOPShip ship = new StaticOPShip();
		ship.setAltitudeSpeed(getAltitudeSpeed());
		ship.setBoostSpeed(getBoostSpeed());
		ship.setDefaultSpeed(getDefaultSpeed());
		ship.setMaxSize(getMaxSize());
		ship.setMinSize(getMinSize());
		ship.setName(name);
		ship.setPlugin(plugin);
		return ship;
	}
}
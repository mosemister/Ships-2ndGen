package MoseShipsBukkit.Vessel.RootType.DataShip.Loader.Types.WaterShip;

import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Utils.SOptional;
import MoseShipsBukkit.Utils.StaticShipTypeUtil;
import MoseShipsBukkit.Vessel.Common.OpenLoader.OpenLoader;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.Common.RootTypes.ShipsData;
import MoseShipsBukkit.Vessel.RootType.DataShip.Loader.DataLoader;
import MoseShipsBukkit.Vessel.RootType.DataShip.Types.Static.StaticWatership;

public class Ship6WaterShipLoader extends DataLoader{
	
	@Override
	public String getLoaderName() {
		return "Watership - Ships 6 - DataLoader";
	}

	@Override
	public int[] getLoaderVersion() {
		int[] version = {0, 0, 1, 0};
		return version;
	}

	@Override
	public SOptional<LiveShip> load(ShipsData data, BasicConfig config) {
		return StaticShipTypeUtil.getType(StaticWatership.class).get().loadVessel(data, config);
	}

	@Override
	public OpenLoader save(LiveShip ship, BasicConfig config) {
		return this;
	}

}

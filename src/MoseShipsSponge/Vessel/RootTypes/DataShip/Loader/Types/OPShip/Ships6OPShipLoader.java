package MoseShipsSponge.Vessel.RootTypes.DataShip.Loader.Types.OPShip;

import java.util.Optional;

import MoseShipsSponge.Configs.BasicConfig;
import MoseShipsSponge.Utils.StaticShipTypeUtil;
import MoseShipsSponge.Vessel.Common.OpenLoader.OpenLoader;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;
import MoseShipsSponge.Vessel.Common.RootTypes.ShipsData;
import MoseShipsSponge.Vessel.RootTypes.DataShip.Loader.DataLoader;
import MoseShipsSponge.Vessel.RootTypes.DataShip.Types.Static.StaticOPShip;

public class Ships6OPShipLoader extends DataLoader {

	@Override
	public String getLoaderName() {
		return "OPShip - Ships 6 - DataLoader";
	}

	@Override
	public int[] getLoaderVersion() {
		int[] version = {0, 0, 0, 1};
		return version;
	}

	@Override
	public Optional<LiveShip> load(ShipsData data, BasicConfig config) {
		return StaticShipTypeUtil.getType(StaticOPShip.class).get().loadVessel(data, config);
	}

	@Override
	public OpenLoader save(LiveShip ship, BasicConfig config) {
		return this;
	}

}

package MoseShipsSponge.Vessel.Common.OpenLoader;

import java.io.File;
import java.util.Optional;

import MoseShipsSponge.Configs.BasicConfig;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;
import MoseShipsSponge.Vessel.Common.RootTypes.ShipsData;

public abstract class OpenLoader implements OpenRAWLoader {

	public static final String ERROR_NO_WORLD_READ = "No World Read";
	public static final String ERROR_NO_WORLD_BY_NAME = "No World By Name";
	public static final String ERROR_NO_LOCATION_READ = "No Location Read";
	
	public abstract Optional<LiveShip> load(ShipsData data, BasicConfig config);
	public abstract OpenLoader save(LiveShip ship, BasicConfig config);
	
	protected String g_error = null;

	@Override
	public Optional<LiveShip> RAWLoad(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OpenRAWLoader RAWSave(LiveShip ship, File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getError(File file) {
		return g_error;
	}

}

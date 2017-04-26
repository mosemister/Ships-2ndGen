package MoseShipsBukkit.Vessel.OpenLoader;

import java.io.File;
import java.util.Optional;

import MoseShipsBukkit.Vessel.Data.LiveShip;

public interface OpenRAWLoader {

	public String getLoaderName();
	public int[] getLoaderVersion();
	public Optional<LiveShip> RAWLoad(File file);
	public boolean willLoad(File file);
	public OpenRAWLoader RAWSave(LiveShip ship, File file);
	public String getError(File file);
	
	
}

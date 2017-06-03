package MoseShipsBukkit.Vessel.Common.OpenLoader;

import java.io.File;

import MoseShipsBukkit.Utils.SOptional;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;

public interface OpenRAWLoader {

	public String getLoaderName();

	public int[] getLoaderVersion();

	public SOptional<LiveShip> RAWLoad(File file);

	public boolean willLoad(File file);

	public OpenRAWLoader RAWSave(LiveShip ship, File file);

	public String getError(File file);

}

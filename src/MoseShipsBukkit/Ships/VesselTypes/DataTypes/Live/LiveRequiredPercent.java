package MoseShipsBukkit.Ships.VesselTypes.DataTypes.Live;

import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveData;
import MoseShipsBukkit.Utils.State.BlockState;

public interface LiveRequiredPercent extends LiveData {

	public int getRequiredPercent();

	public int getAmountOfPercentBlocks();

	public BlockState[] getPercentBlocks();

}

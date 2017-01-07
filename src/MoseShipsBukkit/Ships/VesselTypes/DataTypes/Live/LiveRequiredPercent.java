package MoseShipsBukkit.Ships.VesselTypes.DataTypes.Live;

import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveShip;
import MoseShipsBukkit.Utils.State.BlockState;

public interface LiveRequiredPercent extends LiveShip {

	public static final String REQUIRED_PERCENT = "ShipsData.PercentBlocks.Percent";
	public static final String REQUIRED_BLOCKS = "ShipsData.PercentBlocks.Blocks";

	public int getRequiredPercent();

	public int getAmountOfPercentBlocks();

	public BlockState[] getPercentBlocks();

}

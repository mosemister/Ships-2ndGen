package MoseShipsBukkit.Vessel.DataProcessors.Live;

import MoseShipsBukkit.ShipBlock.BlockState;
import MoseShipsBukkit.Vessel.Data.LiveShip;

public interface LiveRequiredPercent extends LiveShip {

	public static final String REQUIRED_PERCENT = "ShipsData.PercentBlocks.Percent";
	public static final String REQUIRED_BLOCKS = "ShipsData.PercentBlocks.Blocks";

	public int getRequiredPercent();

	public int getAmountOfPercentBlocks();

	public BlockState[] getPercentBlocks();

}

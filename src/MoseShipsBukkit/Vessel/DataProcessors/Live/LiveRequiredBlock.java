package MoseShipsBukkit.Vessel.DataProcessors.Live;

import MoseShipsBukkit.ShipBlock.BlockState;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;

public interface LiveRequiredBlock extends LiveShip {

	public static final String REQUIRED_BLOCKS = "ShipsData.RequiredBlocks";

	public BlockState[] getRequiredBlocks();

	public boolean hasRequiredBlock();

}

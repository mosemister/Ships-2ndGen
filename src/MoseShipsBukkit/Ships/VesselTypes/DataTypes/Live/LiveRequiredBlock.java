package MoseShipsBukkit.Ships.VesselTypes.DataTypes.Live;

import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveShip;
import MoseShipsBukkit.Utils.State.BlockState;

public interface LiveRequiredBlock extends LiveShip {

	public static final String REQUIRED_BLOCKS = "ShipsData.RequiredBlocks";

	public BlockState[] getRequiredBlocks();

	public boolean hasRequiredBlock();

}

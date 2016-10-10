package MoseShipsBukkit.Ships.VesselTypes.DataTypes.Static;

import MoseShipsBukkit.Utils.State.BlockState;

public interface StaticRequiredPercent {

	public static final String DEFAULT_REQUIRED_PERCENT = "Default.Requirement.Block.Percent";
	public static final String DEFAULT_REQUIRED_BLOCKS = "Default.Requirement.Block.Blocks";

	public int getDefaultRequiredPercent();

	public BlockState[] getDefaultPersentBlocks();

}

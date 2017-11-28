package MoseShipsBukkit.Vessel.Common.Static.Types;

import MoseShipsBukkit.ShipBlock.BlockState;
import MoseShipsBukkit.Vessel.Common.Static.StaticShipType;

public interface StaticRequiredPercent extends StaticShipType {

	public static final String DEFAULT_REQUIRED_PERCENT = "Default.Requirement.Block.Percent";
	public static final String DEFAULT_REQUIRED_BLOCKS = "Default.Requirement.Block.Blocks";

	public int getDefaultRequiredPercent();
	public void setDefaultRequiredPercent(int percent);

	public BlockState[] getDefaultPercentBlocks();
	public void setDefaultPercentBlocks(BlockState... state);

}

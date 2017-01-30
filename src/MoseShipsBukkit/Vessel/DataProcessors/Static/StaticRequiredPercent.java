package MoseShipsBukkit.Vessel.DataProcessors.Static;

import MoseShipsBukkit.ShipBlock.BlockState;
import MoseShipsBukkit.Vessel.Static.StaticShipType;

public interface StaticRequiredPercent extends StaticShipType {

	public static final String DEFAULT_REQUIRED_PERCENT = "Default.Requirement.Block.Percent";
	public static final String DEFAULT_REQUIRED_BLOCKS = "Default.Requirement.Block.Blocks";

	public int getDefaultRequiredPercent();

	public BlockState[] getDefaultPercentBlocks();

}

package MoseShipsBukkit.Vessel.RootType.DataShip.Data.Required;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import MoseShips.Stores.TwoStore;
import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Movement.Result.MovementResult;
import MoseShipsBukkit.ShipBlock.BlockState;
import MoseShipsBukkit.Utils.Lists.MovingBlockList;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.RequirementData;

public class PercentageRequirement implements RequirementData {
	
	float g_percentage = 0;
	BlockState[] g_state = new BlockState[0];
	
	public String LOADER_STATES = "BlockRequirement.Blocks";
	public String LOADER_PERCENTAGE = "BlockRequirement.Percent";
	
	public double getPercentage(){
		return g_percentage;
	}
	
	public PercentageRequirement setPercentage(float per){
		g_percentage = per;
		return this;
	}
	
	public BlockState[] getStates(){
		return g_state;
	}
	
	public PercentageRequirement setStates(BlockState... states){
		g_state = states;
		return this;
	}
	
	public MovingBlockList getBlocks(MovingBlockList list){
		return list.filterBlocks(g_state);
	}

	@Override
	public Optional<FailedMovement> hasRequirements(LiveShip ship, MovingBlockList blocks) {
		int total = blocks.size();
		int size = getBlocks(blocks).size();
		float percentage = ((total/size)*100);
		if(percentage >= g_percentage){
			return Optional.empty();
		}
		return Optional.of(new FailedMovement(ship, MovementResult.NOT_ENOUGH_PERCENT, new TwoStore<BlockState, Float>(g_state[0], percentage)));
	}
	
	@Override
	public void applyFromShip(BasicConfig config) {
		g_state = BlockState.getStates(config.getList(String.class, LOADER_STATES));
		g_percentage = config.get(Integer.class, LOADER_PERCENTAGE);
	}
	
	@Override
	public void saveShip(BasicConfig config) {
		config.set(g_state, LOADER_STATES);
		config.set(g_percentage, LOADER_PERCENTAGE);
	}

	@Override
	public Map<String, Object> getInfo(LiveShip ship) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Required Percentage", g_percentage);
		return map;
	}

}

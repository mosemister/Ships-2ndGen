package MoseShipsBukkit.Vessel.RootType.DataShip.Data.Required;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.block.Block;

import MoseShips.Stores.TwoStore;
import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Movement.Result.MovementResult;
import MoseShipsBukkit.ShipBlock.BlockState;
import MoseShipsBukkit.Utils.SOptional;
import MoseShipsBukkit.Utils.Lists.MovingBlockList;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.RequirementData;

public class PercentageRequirement implements RequirementData {
	
	float g_percentage = 0;
	BlockState[] g_state = new BlockState[0];
	
	public String LOADER_STATES = "BlockRequirement.Blocks";
	public String LOADER_PERCENTAGE = "BlockRequirement.Percent";
	
	public float getPercentage(){
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
	
	private List<Block> getBlocks(List<Block> list){
		List<Block> list2 = new ArrayList<Block>();
		for(Block block : list){
			for(BlockState state : g_state){
				if (state.looseMatch(block)){
					list2.add(block);
				}
			}
		}
		return list2;
	}

	@Override
	public SOptional<FailedMovement> hasRequirements(LiveShip ship, MovingBlockList blocks) {
		int total = blocks.size();
		int size = getBlocks(blocks).size();
		if(size == 0){
			return new SOptional<FailedMovement>(new FailedMovement(ship, MovementResult.NOT_ENOUGH_PERCENT, new TwoStore<BlockState, Float>(g_state[0], getPercentage())));
		}
		float percentage = ((size*100)/total);
		if(percentage >= g_percentage){
			return new SOptional<FailedMovement>();
		}
		return new SOptional<FailedMovement>(new FailedMovement(ship, MovementResult.NOT_ENOUGH_PERCENT, new TwoStore<BlockState, Float>(g_state[0], (getPercentage() - percentage))));
	}
	
	@Override
	public void applyFromShip(BasicConfig config) {
		g_state = BlockState.getStates(config.getList(String.class, LOADER_STATES));
		g_percentage = (float)(double)config.get(Double.class, LOADER_PERCENTAGE);
	}
	
	@Override
	public void saveShip(BasicConfig config) {
		List<String> list = new ArrayList<String>();
		for(BlockState state : g_state){
			list.add(state.toNoString());
		}
		config.set(list, LOADER_STATES);
		config.set(g_percentage, LOADER_PERCENTAGE);
	}

	@Override
	public Map<String, Object> getInfo(LiveShip ship) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Block> structure = ship.getBasicStructure();
		map.put("Required Percentage", g_percentage);
		if(!structure.isEmpty()){
			List<Block> requiredBlocks = getBlocks(structure);
			if(!requiredBlocks.isEmpty()){
				map.put("Current Percentage", ((requiredBlocks.size()*100)/structure.size()));
			}
		}
		return map;
	}

}

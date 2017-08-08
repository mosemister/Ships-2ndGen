package MoseShipsSponge.Vessel.RootTypes.DataShip.Data.Required;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.api.block.BlockState;

import MoseShipsSponge.Configs.BasicConfig;
import MoseShipsSponge.Movement.Result.FailedMovement;
import MoseShipsSponge.Movement.Result.MovementResult;
import MoseShipsSponge.Utils.BlockUtil;
import MoseShipsSponge.Utils.Lists.MovingBlockList;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;
import MoseShipsSponge.Vessel.RootTypes.DataShip.Data.RequirementData;

public class BlockRequirement implements RequirementData {

	BlockState[] g_states = {};
	
	public String LOADER_STATES = "BlockRequirement.Blocks";
	
	public BlockRequirement setState(BlockState... state){
		g_states = state;
		return this;
	}
	
	public BlockState[] getStates(){
		return g_states;
	}
	
	@Override
	public Optional<FailedMovement> hasRequirement(LiveShip ship, MovingBlockList blocks) {
		System.out.println("Checking block requirements");
		for(BlockState state : g_states){
			MovingBlockList list = blocks.filterBlocks(state);
			if(list.isEmpty()){
				return Optional.of(new FailedMovement(ship, MovementResult.MISSING_REQUIRED_BLOCK, state));
			}
		}
		return Optional.empty();
	}

	@Override
	public void applyFromShip(BasicConfig config) {
		g_states = BlockUtil.deserialBlockState(config.getList(n -> n.getString(), LOADER_STATES));
	}

	@Override
	public void saveShip(BasicConfig config) {
		config.set(BlockUtil.serial(g_states), LOADER_STATES);
		
	}

	@Override
	public Map<String, Object> getInfo(LiveShip ship) {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}

}

package MoseShipsBukkit.Vessel.RootType.DataShip.Data.Required;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Movement.Result.MovementResult;
import MoseShipsBukkit.ShipBlock.BlockState;
import MoseShipsBukkit.Utils.Lists.MovingBlockList;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.RequirementData;

public class BlockRequirement implements RequirementData {

	public static final int REQUIRES_ALL = 1;
	public static final int REQUIRES_ANY = 0;
	
	public String LOADER_STATES = "BlockRequirement.Blocks";
	public String LOADER_MODE = "BlockRequirement.Mode";
	
	BlockState[] g_states;
	int g_mode = REQUIRES_ANY;
	
	public BlockState[] getState(){
		return g_states;
	}
	
	public BlockRequirement setState(BlockState... state){
		g_states = state;
		return this;
	}
	
	public int getMode() {
		return g_mode;
	}

	public BlockRequirement setMode(int mode) {
		g_mode = mode;
		return this;
	}
	
	@Override
	public Optional<FailedMovement> hasRequirements(LiveShip ship, MovingBlockList blocks) {
		if(g_mode == REQUIRES_ALL){
			for(BlockState state : g_states){
				MovingBlockList list = blocks.filterBlocks(state);
				if(list.isEmpty()){
					return Optional.of(new FailedMovement(ship, MovementResult.MISSING_REQUIRED_BLOCK, state));
				}
			}
		}else if(g_mode == REQUIRES_ANY){
			if(g_states.length == 0){
				return Optional.empty();
			}
			for(BlockState state : g_states){
				MovingBlockList list = blocks.filterBlocks(state);
				if(!list.isEmpty()){
					return Optional.empty();
				}
			}
			return Optional.of(new FailedMovement(ship, MovementResult.MISSING_REQUIRED_BLOCK, g_states[0]));
		}
		return Optional.empty();
	}
	
	@Override
	public void applyFromShip(BasicConfig config) {
		g_states = BlockState.getStates(config.getList(String.class, LOADER_STATES));
		g_mode = config.get(Integer.class, LOADER_MODE);
	}
	
	@Override
	public void saveShip(BasicConfig config) {
		config.set(g_states, LOADER_STATES);
		config.set(g_mode, LOADER_MODE);
	}

	@Override
	public Map<String, Object> getInfo(LiveShip ship) {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}

}

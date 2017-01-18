package MoseShipsBukkit.Tasks.Types;

import MoseShipsBukkit.Configs.ShipsConfig;
import MoseShipsBukkit.Tasks.ShipsTask;
import MoseShipsBukkit.Vessel.Data.LiveShip;

public class StructureCheckingTask implements ShipsTask{

	boolean g_can_update_structure = true;
	
	@Override
	public void onRun(LiveShip ship) {
		g_can_update_structure = true;
	}
	
	public void setUpdateStructure(boolean check){
		g_can_update_structure = check;
	}
	
	public boolean canUpdateStructure(){
		return g_can_update_structure;
	}

	@Override
	public long getDelay() {
		return ShipsConfig.CONFIG.get(Integer.class, ShipsConfig.PATH_SCHEDULER_STRUCTURE);
	}

}

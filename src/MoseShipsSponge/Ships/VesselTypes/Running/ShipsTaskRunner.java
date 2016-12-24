package MoseShipsSponge.Ships.VesselTypes.Running;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;

import MoseShipsSponge.ShipsMain;
import MoseShipsSponge.Configs.Files.ShipsConfig;
import MoseShipsSponge.Ships.VesselTypes.DataTypes.LiveData;
import MoseShipsSponge.Ships.VesselTypes.Running.Tasks.StructureCheckingTask;
import MoseShipsSponge.Ships.VesselTypes.Running.Tasks.UnloadTask;

public class ShipsTaskRunner {
	
	protected Task g_sche_id;
	protected int g_time_repeated = 0;
	protected Map<ShipsTask, Object> g_tasks = new HashMap<ShipsTask, Object>();
	protected LiveData g_ship;
	
	public ShipsTaskRunner(LiveData ship){
		g_ship = ship;
		g_tasks.put(new UnloadTask(), ShipsMain.getPlugin());
		g_tasks.put(new StructureCheckingTask(), ShipsMain.getPlugin());
		startScheduler();
	}
	
	public LiveData getAttachedShip(){
		return g_ship;
	}
	
	public int getSchedulerRepeatedCount() {
		return g_time_repeated;
	}
	
	public Set<ShipsTask> getTasks(){
		return g_tasks.keySet();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends ShipsTask> Set<T> getTasks(Class<T> type){
		List<T> list = new ArrayList<T>();
		for(ShipsTask task : getTasks()){
			if(type.isInstance(task)){
				list.add((T)task);
			}
		}
		return new HashSet<>(list);
	}
	
	public void register(Object plugin, ShipsTask task){
		g_tasks.put(task, plugin);
	}
	
	public void unregister(ShipsTask task){
		g_tasks.remove(task);
	}
	
	public ShipsTaskRunner pauseScheduler() {
		g_sche_id.cancel();
		g_sche_id = null;
		return this;
	}

	public boolean startScheduler() {
		long repeate = 1;
		if (ShipsConfig.CONFIG.get(Integer.class, ShipsConfig.PATH_SCHEDULER_REPEATE) != null) {
			Integer value = ShipsConfig.CONFIG.get(Integer.class, ShipsConfig.PATH_SCHEDULER_REPEATE);
			if(value != null){
				repeate = value.longValue();
			}
		} else {
			Double value = ShipsConfig.CONFIG.get(Double.class, ShipsConfig.PATH_SCHEDULER_REPEATE);
			if(value != null){
				repeate = value.longValue();
			}
		}
		if(g_sche_id == null){
		Task.Builder builder = Sponge.getScheduler().createTaskBuilder();
		builder.delayTicks(repeate);
		g_sche_id = builder.execute(new Runnable(){

			@Override
			public void run() {
				g_tasks.entrySet().stream().forEach(e -> {
					if(g_time_repeated % e.getKey().getDelay() == 0){
						e.getKey().onRun(getAttachedShip());
					}
				});
			}
			
		}).submit(ShipsMain.getPlugin());
			return true;
		} else {
			return false;
		}
}

}

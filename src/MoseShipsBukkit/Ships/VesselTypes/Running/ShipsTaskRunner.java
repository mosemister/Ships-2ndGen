package MoseShipsBukkit.Ships.VesselTypes.Running;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import MoseShipsBukkit.ShipsMain;
import MoseShipsBukkit.Configs.Files.ShipsConfig;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveShip;
import MoseShipsBukkit.Ships.VesselTypes.Running.Tasks.StructureCheckingTask;
import MoseShipsBukkit.Ships.VesselTypes.Running.Tasks.UnloadTask;

public class ShipsTaskRunner {
	
	protected int g_sche_id = -1;
	protected int g_time_repeated = 0;
	protected Map<ShipsTask, Plugin> g_tasks = new HashMap<ShipsTask, Plugin>();
	protected LiveShip g_ship;
	
	public ShipsTaskRunner(LiveShip ship){
		g_ship = ship;
		g_tasks.put(new UnloadTask(), ShipsMain.getPlugin());
		g_tasks.put(new StructureCheckingTask(), ShipsMain.getPlugin());
		startScheduler();
	}
	
	public LiveShip getAttachedShip(){
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
		return new HashSet<T>(list);
	}
	
	public void register(Plugin plugin, ShipsTask task){
		g_tasks.put(task, plugin);
	}
	
	public void unregister(ShipsTask task){
		g_tasks.remove(task);
	}
	
	public ShipsTaskRunner pauseScheduler() {
		Bukkit.getScheduler().cancelTask(g_sche_id);
		return this;
	}

	public boolean startScheduler() {
		long repeate = 1;
		if (ShipsConfig.CONFIG.get(Integer.class, ShipsConfig.PATH_SCHEDULER_REPEATE) != null) {
			repeate = ShipsConfig.CONFIG.get(Integer.class, ShipsConfig.PATH_SCHEDULER_REPEATE).longValue();
		} else {
			repeate = ShipsConfig.CONFIG.get(Double.class, ShipsConfig.PATH_SCHEDULER_REPEATE).longValue();
		}
		if (g_sche_id == -1) {
			g_sche_id = Bukkit.getScheduler().scheduleSyncRepeatingTask(ShipsMain.getPlugin(), new Runnable() {

				@Override
				public void run() {
					for(Entry<ShipsTask, Plugin> task : g_tasks.entrySet()){
						if(g_time_repeated % task.getKey().getDelay() == 0){
							task.getKey().onRun(getAttachedShip());
						}
					}
					g_time_repeated++;

				}

			}, 0, repeate);
			return true;
		} else {
			return false;
		}
	}

}

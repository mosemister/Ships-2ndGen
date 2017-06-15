package MoseShipsSponge.Tasks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;

import MoseShipsSponge.Configs.ShipsConfig;
import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class ShipsTaskRunner {

	protected Task g_task;
	protected int g_time_repeated = 0;
	protected Map<ShipsTask, Object> g_tasks = new HashMap<>();
	protected LiveShip g_ship;

	public ShipsTaskRunner(LiveShip ship) {
		g_ship = ship;
		//g_tasks.put(new UnloadTask(), ShipsMain.getPlugin());
		startScheduler();
	}

	public LiveShip getAttachedShip() {
		return g_ship;
	}

	public int getSchedulerRepeatedCount() {
		return g_time_repeated;
	}

	public Set<ShipsTask> getTasks() {
		return g_tasks.keySet();
	}

	@SuppressWarnings("unchecked")
	public <T extends ShipsTask> Set<T> getTasks(Class<T> type) {
		return new HashSet<>(
				(List<T>) getTasks().stream().filter(t -> type.isInstance(type)).collect(Collectors.toList()));
	}

	public void register(Object plugin, ShipsTask task) {
		g_tasks.put(task, plugin);
	}

	public void unregister(ShipsTask task) {
		g_tasks.remove(task);
	}

	public ShipsTaskRunner pauseScheduler() {
		g_task.cancel();
		return this;
	}

	public boolean startScheduler() {
		long repeate = 1;
		if (ShipsConfig.CONFIG.get(Integer.class, ShipsConfig.PATH_SCHEDULER_REPEATE) != null) {
			repeate = ShipsConfig.CONFIG.get(Integer.class, ShipsConfig.PATH_SCHEDULER_REPEATE).longValue();
		} else {
			repeate = ShipsConfig.CONFIG.get(Double.class, ShipsConfig.PATH_SCHEDULER_REPEATE).longValue();
		}
		if (g_task == null) {
			g_task = Sponge.getScheduler().createTaskBuilder().intervalTicks(repeate).execute(new Runnable() {

				@Override
				public void run() {
					g_tasks.entrySet().stream().forEach(e -> {
						if (g_time_repeated % e.getKey().getDelay() == 0) {
							e.getKey().onRun(getAttachedShip());
						}
					});
					g_time_repeated++;
				}

			}).submit(ShipsMain.getPlugin());
			return true;
		}
		return false;
	}

}

package MoseShipsSponge.Algorthum.BlockFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShips.Bypasses.FinalBypass;
import MoseShipsSponge.Configs.BlockList;
import MoseShipsSponge.Configs.BlockList.ListType;
import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.Utils.LocationUtils;

public class Ships6BlockFinder implements BasicBlockFinder {

	Task g_task;
	
	@Override
	public List<Location<World>> getConnectedBlocksOvertime(int limit, Location<World> loc) {
		FinalBypass<Integer> count = new FinalBypass<>(0);
		Direction[] faces = {
				Direction.NORTH,
				Direction.EAST,
				Direction.DOWN,
				Direction.SOUTH,
				Direction.UP,
				Direction.WEST };
		List<Location<World>> ret = new ArrayList<>();
		List<Location<World>> target = new ArrayList<>();
		List<Location<World>> process = new ArrayList<>();
		process.add(loc);
		g_task = Sponge.getScheduler().createTaskBuilder().interval(1, TimeUnit.MILLISECONDS).execute(new Runnable() {

			@Override
			public void run() {
				if(count.get() == limit) {
					g_task.cancel();
				}
				if (process.isEmpty()) {
					g_task.cancel();
				}
				for (int A = 0; A < process.size(); A++) {
					Location<World> proc = process.get(A);
					count.set(count.get() + 1);
					for (Direction dir : faces) {
						Location<World> block = proc.getBlockRelative(dir);
						if (!LocationUtils.blockWorldContains(ret, block)) {
							if (BlockList.BLOCK_LIST.contains(block.getBlock(), ListType.MATERIALS)) {
								ret.add(block);
								target.add(block);
							}
						}
					}
				}
				process.clear();
				process.addAll(target);
				target.clear();
			}
			
		}).submit(ShipsMain.getPlugin());
		return ret;
	}
	
	@Override
	public List<Location<World>> getConnectedBlocks(int limit, Location<World> loc) {
		int count = 0;
		Direction[] faces = {
				Direction.NORTH,
				Direction.EAST,
				Direction.DOWN,
				Direction.SOUTH,
				Direction.UP,
				Direction.WEST };
		List<Location<World>> ret = new ArrayList<>();
		List<Location<World>> target = new ArrayList<>();
		List<Location<World>> process = new ArrayList<>();
		process.add(loc);
		while (count != limit) {
			if (process.isEmpty()) {
				return ret;
			}
			for (int A = 0; A < process.size(); A++) {
				Location<World> proc = process.get(A);
				count++;
				for (Direction dir : faces) {
					Location<World> block = proc.getBlockRelative(dir);
					if (!LocationUtils.blockWorldContains(ret, block)) {
						if (BlockList.BLOCK_LIST.contains(block.getBlock(), ListType.MATERIALS)) {
							ret.add(block);
							target.add(block);
						}
					}
				}
			}
			process.clear();
			process.addAll(target);
			target.clear();
		}
		return ret;
	}

	@Override
	public String getName() {
		return "Ships 6";
	}

}

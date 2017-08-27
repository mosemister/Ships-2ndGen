package MoseShipsBukkit.Algorthum.Movement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import MoseShips.Stores.OneStore;
import MoseShipsBukkit.Configs.ShipsConfig;
import MoseShipsBukkit.Movement.MovingBlock;
import MoseShipsBukkit.Plugin.ShipsMain;
import MoseShipsBukkit.Vessel.Common.GeneralTypes.WaterType;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;

public class Ships6Movement implements MovementAlgorithm {

	@Override
	public boolean move(final LiveShip vessel, final List<MovingBlock> blocksUn, final List<Entity> onBoard) {
		if (blocksUn.isEmpty()) {
			return false;
		}
		final Map<Entity, Location> map = new HashMap<Entity, Location>();
		for (Entity entity : onBoard) {
			map.put(entity, entity.getLocation());
			entity.setGravity(false);
		}
		List<MovingBlock> blocks = MovingBlock.setPriorityOrder(blocksUn);
		ShipsConfig config = ShipsConfig.CONFIG;
		long repeate = 1;
		if (config.get(Integer.class, ShipsConfig.PATH_STRUCTURE_STRUCTURELIMITS_TRACKREPEATE) != null) {
			repeate = config.get(Integer.class, ShipsConfig.PATH_STRUCTURE_STRUCTURELIMITS_TRACKREPEATE).intValue();
		} else {
			Double ret = config.get(Double.class, ShipsConfig.PATH_STRUCTURE_STRUCTURELIMITS_TRACKREPEATE);
			repeate = ret.longValue();
		}
		int stackSize = 100;
		final List<List<MovingBlock>> list = new ArrayList<List<MovingBlock>>();
		List<MovingBlock> toAdd = new ArrayList<MovingBlock>();
		for (int A = 0; A < blocks.size(); A++) {
			MovingBlock block = blocks.get(A);
			toAdd.add(block);
			if (toAdd.size() == stackSize) {
				list.add(toAdd);
				toAdd = new ArrayList<MovingBlock>();
			}
		}
		list.add(toAdd);
		final Location licence = vessel.getLocation();
		int waterLevel = 0;
		if (vessel instanceof WaterType) {
			WaterType type2 = (WaterType) vessel;
			waterLevel = type2.getWaterLevel();
		}
		final int waterLevelFinal = waterLevel;
		long time = ((list.size() - 1) * repeate);
		final OneStore<Block> oneLic = new OneStore<Block>(null);
		final List<Block> newStructure = new ArrayList<Block>();

		for (int A = 0; A < list.size(); A++) {
			final int B = A;
			final long repe = (repeate * A);
			Bukkit.getScheduler().scheduleSyncDelayedTask(ShipsMain.getPlugin(), new Runnable() {

				@Override
				public void run() {
					List<MovingBlock> blocks = list.get(B);
					if (blocks.isEmpty()) {
						return;
					}
					for (MovingBlock block : blocks) {
						if (block.getOrigin().getBlockY() > waterLevelFinal) {
							block.clearOriginalBlock();
						} else {
							block.replaceOriginalBlock(Material.STATIONARY_WATER, (byte) 0);
						}
					}
				}

			}, repe);

			final int C = (list.size() - (A + 1));
			Bukkit.getScheduler().scheduleSyncDelayedTask(ShipsMain.getPlugin(), new Runnable() {
				@Override
				public void run() {
					List<MovingBlock> blocks = list.get(C);
					if (blocks.isEmpty()) {
						return;
					}
					// place all blocks
					for (int A = (blocks.size() - 1); A >= 0; A--) {
						MovingBlock block = blocks.get(A);
						newStructure.add(block.getMovingTo().getBlock());
						block.move();
						if (licence.getBlock().equals(block.getOrigin().getBlock())) {
							oneLic.setFirst(block.getMovingTo().getBlock());
						}
					}
				}
			}, (repe + list.size()) + time);
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(ShipsMain.getPlugin(), new Runnable() {

			@Override
			public void run() {
				Location loc = blocksUn.get(0).getMovingTo().clone().subtract(blocksUn.get(0).getOrigin());
				MovingBlock tBlock = new MovingBlock(vessel.getTeleportToLocation().getBlock(), loc.getBlockX(),
						loc.getBlockY(), loc.getBlockZ());
				if (oneLic.getFirst() == null) {
					MovingBlock lBlock = new MovingBlock(vessel.getLocation().getBlock(), loc.getBlockX(),
							loc.getBlockY(), loc.getBlockZ());
					vessel.setBasicStructure(newStructure, lBlock.getMovingTo().getBlock(), tBlock.getMovingTo());
				} else {
					vessel.setBasicStructure(newStructure, oneLic.getFirst(), tBlock.getMovingTo());
				}
				for (Entry<Entity, Location> entry : map.entrySet()) {
					Entity entity = entry.getKey();
					Location loc2 = entry.getValue();
					loc2.add(loc);
					entity.teleport(loc2);
					entity.setGravity(true);
				}

			}

		}, ((list.size()*repeate)+ time + 1));
		return false;
	}

	@Override
	public String getName() {
		return "Ships 6";
	}

}

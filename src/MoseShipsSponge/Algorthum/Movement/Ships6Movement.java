package MoseShipsSponge.Algorthum.Movement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.world.BlockChangeFlag;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShips.Stores.OneStore;
import MoseShipsSponge.Configs.ShipsConfig;
import MoseShipsSponge.Movement.MovingBlock;
import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.Utils.LocationUtils;
import MoseShipsSponge.Vessel.Common.GeneralTypes.WaterType;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class Ships6Movement implements MovementAlgorithm {

	@Override
	public boolean move(LiveShip vessel, List<MovingBlock> blocksUn, List<Entity> onBoard) {
		if(blocksUn.isEmpty()){
			return false;
		}
		final Map<Entity, Location<World>> map = new HashMap<>();
		onBoard.stream().forEach(e -> {
			map.put(e, e.getLocation());
			e.offer(Keys.HAS_GRAVITY, false);
		});
		List<MovingBlock> blocks = MovingBlock.setPriorityOrder(blocksUn);
		ShipsConfig config = ShipsConfig.CONFIG;
		long repeate = 1;
		if(config.get(Integer.class, ShipsConfig.PATH_STRUCTURE_STRUCTURELIMITS_TRACKREPEATE) != null){
			repeate = config.get(Integer.class, ShipsConfig.PATH_STRUCTURE_STRUCTURELIMITS_TRACKREPEATE);
		} else {
			Double ret = config.get(Double.class, ShipsConfig.PATH_STRUCTURE_STRUCTURELIMITS_TRACKREPEATE);
			repeate = ret.longValue();
		}
		int stackSize = 100;
		final List<List<MovingBlock>> list = new ArrayList<>();
		List<MovingBlock> toAdd = new ArrayList<>();
		for(int A = 0; A < blocks.size(); A++){
			MovingBlock block = blocks.get(A);
			toAdd.add(block);
			if(toAdd.size() == stackSize){
				list.add(toAdd);
				toAdd = new ArrayList<>();
			}
		}
		list.add(toAdd);
		final Location<World> licence = vessel.getLocation();
		int waterLevel = 63;
		if(vessel instanceof WaterType){
			WaterType type2 = (WaterType)vessel;
			waterLevel = type2.getWaterLevel();
		}
		final int waterLevelFinal = waterLevel;
		long time = ((list.size() - 1) * repeate);
		final OneStore<Location<World>> oneLic = new OneStore<>(null);
		final List<Location<World>> newStructure = new ArrayList<>();
		for(int A = 0; A < list.size(); A++){
			final int B = A;
			final long repe = (repeate * A);
			Sponge.getScheduler().createTaskBuilder().delayTicks(repe).execute(new Runnable(){

				@Override
				public void run() {
					List<MovingBlock> blocks = list.get(B);
					if(blocks.isEmpty()){
						return;
					}
					blocks.stream().forEach(b -> {
						if(b.getOrigin().getBlockY() > waterLevelFinal){
							b.clearOriginalBlock(BlockChangeFlag.NONE/*, Cause.source(ShipsMain.getPlugin().getContainer()).build()*/);
						}else{
							b.replaceOriginalBlock(BlockTypes.WATER, BlockChangeFlag.NONE/*, Cause.source(ShipsMain.getPlugin().getContainer()).build()*/);
						}
					});
				}
				
			}).submit(ShipsMain.getPlugin());
			final int C = (list.size() - (A + 1));
			Sponge.getScheduler().createTaskBuilder().delayTicks((repe + list.size()) + time).execute(new Runnable(){

				@Override
				public void run() {
					List<MovingBlock> blocks = list.get(C);
					if(blocks.isEmpty()){
						return;
					}
					for(int A = (blocks.size() - 1); A >= 0; A--){
						MovingBlock block = blocks.get(A);
						newStructure.add(block.getMovingTo());
						block.move(BlockChangeFlag.NONE);
						if(LocationUtils.blocksEqual(licence, block.getOrigin())){
							oneLic.setFirst(block.getOrigin());
						}
					}
				}
				
			}).submit(ShipsMain.getPlugin());
		}
		Sponge.getScheduler().createTaskBuilder().delayTicks((list.size() * repeate)+ time + 1).execute(new Runnable(){

			@Override
			public void run() {
				Location<World> loc = blocksUn.get(0).getMovingTo().copy().sub(blocksUn.get(0).getOrigin().getBlockPosition());
				MovingBlock tBlock = new MovingBlock(vessel.getTeleportToLocation(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
				if(oneLic.getFirst() == null){
					MovingBlock lBlock = new MovingBlock(vessel.getTeleportToLocation(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
					vessel.setBasicStructure(newStructure, lBlock.getMovingTo(), tBlock.getMovingTo());
				}else{
					vessel.setBasicStructure(newStructure, oneLic.getFirst(), tBlock.getMovingTo());
				}
				map.entrySet().stream().forEach(e -> {
					Entity entity = e.getKey();
					Location<World> loc2 = e.getValue();
					loc2.add(loc.getBlockPosition());
					entity.setLocation(loc2);
					entity.offer(Keys.HAS_GRAVITY, true);
				});
			}
			
		}).submit(ShipsMain.getPlugin().getContainer());
		
		return false;
	}

	@Override
	public String getName() {
		return "Ships 6";
	}

}

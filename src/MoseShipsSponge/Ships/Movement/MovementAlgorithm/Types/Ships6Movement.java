package MoseShipsSponge.Ships.Movement.MovementAlgorithm.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.BlockChangeFlag;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShips.Stores.OneStore;
import MoseShipsSponge.Causes.ShipsCause;
import MoseShipsSponge.Configs.Files.ShipsConfig;
import MoseShipsSponge.Ships.Movement.MovementAlgorithm.MovementAlgorithm;
import MoseShipsSponge.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsSponge.Ships.VesselTypes.LoadableShip;
import MoseShipsSponge.Ships.VesselTypes.DefaultTypes.WaterType;

public class Ships6Movement implements MovementAlgorithm {

	@Override
	public boolean move(LoadableShip vessel, List<MovingBlock> blocksUn, List<Entity> onBoard) {
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
		}else{
			repeate = config.get(Double.class, ShipsConfig.PATH_STRUCTURE_STRUCTURELIMITS_TRACKREPEATE).longValue();
		}
		int stackSize = 100;
		final List<List<MovingBlock>> list = new ArrayList<>();
		List<MovingBlock> toAdd = new ArrayList<>();
		blocks.stream().forEach(block -> {
			toAdd.add(block);
			if(toAdd.size() == stackSize){
				list.add(toAdd);
				toAdd.clear();
			}
		});
		list.add(toAdd);
		final Location<World> licence = vessel.getLocation();
		int waterLevel = 63;
		if(vessel instanceof WaterType){
			WaterType type = (WaterType)vessel;
			waterLevel = type.getWaterLevel();
		}
		final int waterLevelFinal = waterLevel;
		long time = ((list.size() - 1)*repeate);
		final OneStore<Location<World>> oneLic = new OneStore<>(null);
		final List<Location<World>> newStructure = new ArrayList<>();
		for(int A = 0; A < list.size(); A++){
			final int B = A;
			final long repe = (repeate * A);
			Task.Builder builder = Sponge.getScheduler().createTaskBuilder();
			builder.delayTicks(repe);
			builder.execute(new Runnable(){

				@Override
				public void run() {
					List<MovingBlock> blocks = list.get(B);
					if(blocks.isEmpty()){
						return;
					}
					blocks.stream().forEach(block -> {
						if(block.getOrigin().getBlockY() > waterLevelFinal){
							block.clearOriginalBlock(BlockChangeFlag.NONE, ShipsCause.BLOCK_MOVING.buildCause());
						}else{
							block.replaceOriginalBlock(BlockTypes.WATER, BlockChangeFlag.NONE, ShipsCause.BLOCK_MOVING.buildCause());
						}
					});
				}
				
			});
			
			final int C = (list.size() - (A + 1));
			Task.Builder builder2 = Sponge.getScheduler().createTaskBuilder();
			builder2.delayTicks(repe+time);
			builder2.execute(new Runnable(){

				@Override
				public void run() {
					List<MovingBlock> blocks = list.get(C);
					if(blocks.isEmpty()){
						return;
					}
					blocks.stream().forEach(block -> {
						newStructure.add(block.getMovingTo());
						block.move(BlockChangeFlag.NONE);
						if(licence.getBlock().equals(block.getOrigin())){
							oneLic.setFirst(block.getMovingTo());
						}
					});
				}
			});
			
			Task.Builder builder3 = Sponge.getScheduler().createTaskBuilder();
			builder3.delayTicks(((list.size() - 1) * 2) * repeate);
			builder3.execute(new Runnable(){

				@Override
				public void run() {
					Location<World> loc = blocksUn.get(0).getMovingTo().copy().sub(blocksUn.get(0).getOrigin().getBlockPosition());
					MovingBlock tBlock = new MovingBlock(vessel.getLocation(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
					if(oneLic.getFirst() == null){
						MovingBlock lBlock = new MovingBlock(vessel.getLocation(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
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
				
			});
		}
		return false;
	}

	@Override
	public String getName() {
		return "Ships 6";
	}

}

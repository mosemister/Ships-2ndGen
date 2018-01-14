package MoseShipsSponge.Algorthum.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.world.BlockChangeFlags;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;

import MoseShips.Stores.OneStore;
import MoseShipsSponge.Configs.ShipsConfig;
import MoseShipsSponge.Movement.MovingBlock;
import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.Utils.LocationUtils;
import MoseShipsSponge.Vessel.Common.GeneralTypes.WaterType;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class Ships6Movement implements MovementAlgorithm {

	@Override
	public boolean move(LiveShip vessel, List<MovingBlock> blocksUn, Map<Entity, MovingBlock> onBoard) {
		if(blocksUn.isEmpty()){
			return false;
		}
		List<MovingBlock> blocks = MovingBlock.setPriorityOrder(blocksUn);
		ShipsConfig config = ShipsConfig.CONFIG;
		long repeate = 1;
		if(config.get(Integer.class, "Advanced", "Structure", "StructureLimits", "TrackRepeate") != null) {
			repeate = ((Integer)config.get(Integer.class, "Advanced", "Structure", "StructureLimits", "TrackRepeate")).intValue();
		} else {
			Double ret = (Double)config.get(Double.class, "Advanced", "Structure", "StructureLimits", "TrackRepeate");
			repeate = ret.longValue();
		}
		int stackSize = 100;
		final List<List<MovingBlock>> list = new ArrayList<>();
		List<MovingBlock> toAdd = new ArrayList<>();
		for(int A = 0; A < blocks.size(); A++) {
			MovingBlock block = blocks.get(A);
			toAdd.add(block);
			if(toAdd.size() == stackSize) {
				list.add(toAdd);
				toAdd = new ArrayList<>();
			}
		}
		list.add(toAdd);
		final Location<World> lic = vessel.getLocation();
		int waterLevel = 0;
		if((vessel instanceof WaterType)) {
			WaterType type2 = (WaterType)vessel;
			waterLevel = type2.getWaterLevel();
		}
		final int waterLevelFinal = waterLevel;
		long time = (list.size() - 1) * repeate;
		final OneStore<Location<World>> oneLic = new OneStore<>(null);
		final List<Location<World>> newStructure = new ArrayList<>();
		for(int A = 0; A < list.size(); A++) {
			final int B = A;
			long repe = repeate * A;
			Sponge.getScheduler().createTaskBuilder().delayTicks(repe).execute(new Runnable() {

				@Override
				public void run() {
					List<MovingBlock> blocks = list.get(B);
					if(blocks.isEmpty()) {
						return;
					}
					blocks.stream().forEach(b -> {
						if(b.getOrigin().getBlockY() > waterLevelFinal) {
							b.clearOriginalBlock(BlockChangeFlags.NONE);
						}else {
							b.replaceOriginalBlock(BlockTypes.WATER, BlockChangeFlags.NONE);
						}
					});
				}
			}).submit(ShipsMain.getPlugin().getContainer());
			final int C = list.size() - (A + 1);
			Sponge.getScheduler().createTaskBuilder().delayTicks(repe + list.size() + time).execute(new Runnable() {

				@Override
				public void run() {
					List<MovingBlock> blocks = list.get(C);
					if(blocks.isEmpty()) {
						return;
					}
					for(int A = blocks.size() - 1; A >= 0; A--) {
						MovingBlock block = blocks.get(A);
						Map.Entry<Entity, MovingBlock> entry2 = null;
						for (Map.Entry<Entity, MovingBlock> entry : onBoard.entrySet()) {
							if(LocationUtils.blocksEqual(entry.getValue().getOrigin(), block.getOrigin())) {
								entry2 = entry;
							}
						}
						newStructure.add(block.getMovingTo());
						block.move(BlockChangeFlags.NONE);
						if(LocationUtils.blocksEqual(lic, block.getOrigin())) {
							oneLic.setFirst(block.getMovingTo());
						}
						if(entry2 != null) {
							Entity entity = entry2.getKey();
							Location<World> origin2 = entity.getLocation();
							double offsetX = origin2.getX() - origin2.getBlockX();
							double offsetY = origin2.getY() - origin2.getBlockY();
							double offsetZ = origin2.getZ() - origin2.getBlockZ();
							Vector3d vector = entity.getRotation();
							Location<World> teleport2 = entry2.getValue().getMovingTo().copy().add(offsetX, offsetY, offsetZ);
							entity.setLocationAndRotation(teleport2, vector);
						}
					}
				}
				
			}).submit(ShipsMain.getPlugin().getContainer());
		}
		Sponge.getScheduler().createTaskBuilder().delayTicks(list.size() * repeate + time + 1).execute(new Runnable() {

			@Override
			public void run() {
				Vector3i vector = blocksUn.get(0).getMovingTo().getBlockPosition().sub(blocksUn.get(0).getOrigin().getBlockPosition());
				MovingBlock tBlock = new MovingBlock(vessel.getTeleportToLocation(), vector.getX(), vector.getY(), vector.getZ());
				if(oneLic.getFirst() == null) {
					MovingBlock lBlock = new MovingBlock(vessel.getLocation(), vector.getX(), vector.getY(), vector.getZ());
					vessel.setBasicStructure(newStructure, lBlock.getMovingTo(), tBlock.getMovingTo());
				} else {
					vessel.setBasicStructure(newStructure, oneLic.getFirst(), tBlock.getMovingTo());
				}
			}
			
		}).submit(ShipsMain.getPlugin().getContainer());
		return false;
	}

	@Override
	public String getName() {
		return "Ships 6";
	}

}

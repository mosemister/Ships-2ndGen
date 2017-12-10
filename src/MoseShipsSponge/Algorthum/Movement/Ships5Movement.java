package MoseShipsSponge.Algorthum.Movement;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.world.BlockChangeFlag;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Movement.MovingBlock;
import MoseShipsSponge.Utils.LocationUtils;
import MoseShipsSponge.Vessel.Common.GeneralTypes.WaterType;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class Ships5Movement implements MovementAlgorithm {

	@Override
	public boolean move(LiveShip type, List<MovingBlock> blocksUn, List<Entity> onBoard) {
		List<MovingBlock> blocks = MovingBlock.setPriorityOrder(blocksUn);
		int waterLevel = 63;
		if (type instanceof WaterType) {
			WaterType type2 = (WaterType) type;
			waterLevel = type2.getWaterLevel();
		}
		final int waterLevelFinal = waterLevel;
		blocks.stream().forEach(b -> {
			//Cause cause = Cause.source(ShipsMain.getPlugin().getContainer()).named("moving", b).build();
			if (b.getOrigin().getBlockY() > waterLevelFinal) {
				b.clearOriginalBlock(BlockChangeFlag.NONE/*, cause*/);
			} else {
				b.replaceOriginalBlock(BlockTypes.WATER, BlockChangeFlag.ALL/*, cause*/);
			}
		});
		List<Location<World>> newStructure = new ArrayList<>();
		Location<World> lic = null;
		for (int A = (blocks.size() - 1); A >= 0; A--) {
			MovingBlock block = blocks.get(A);
			newStructure.add(block.getMovingTo());
			block.move(BlockChangeFlag.NONE);
			if (LocationUtils.blocksEqual(type.getLocation(), block.getOrigin())) {
				lic = block.getMovingTo();
			}
		}
		Location<World> loc = blocks.get(0).getMovingTo().copy().sub(blocks.get(0).getOrigin().getBlockPosition());
		MovingBlock tBlock = new MovingBlock(type.getTeleportToLocation(), loc.getBlockX(), loc.getBlockY(),
				loc.getBlockZ());
		if (lic == null) {
			MovingBlock lBlock = new MovingBlock(type.getLocation(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
			lic = lBlock.getMovingTo();
		}
		type.setBasicStructure(newStructure, lic, tBlock.getMovingTo());
		return true;
	}

	@Override
	public String getName() {
		return "Ships 5";
	}

}

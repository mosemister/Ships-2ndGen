package MoseShipsBukkit.Algorthum.Movement;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import MoseShipsBukkit.Movement.MovingBlock;
import MoseShipsBukkit.Vessel.Common.GeneralTypes.WaterType;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;

public class Ships5Movement implements MovementAlgorithm {

	@Override
	public boolean move(LiveShip type, List<MovingBlock> blocksUn, List<Entity> entities) {
		List<MovingBlock> blocks = MovingBlock.setPriorityOrder(blocksUn);
		int waterLevel = 0;
		if (type instanceof WaterType) {
			WaterType type2 = (WaterType) type;
			waterLevel = type2.getWaterLevel();
		}
		final int waterLevelFinal = waterLevel;
		for (MovingBlock block : blocks) {
			if (block.getOrigin().getBlockY() > waterLevelFinal) {
				block.clearOriginalBlock();
			} else {
				block.replaceOriginalBlock(Material.STATIONARY_WATER, (byte) 0);
			}
		}
		List<Block> newStructure = new ArrayList<Block>();
		Block lic = null;
		// place all blocks
		for (int A = (blocks.size() - 1); A >= 0; A--) {
			MovingBlock block = blocks.get(A);
			newStructure.add(block.getMovingTo().getBlock());
			block.move();
			if (type.getLocation().getBlock().equals(block.getOrigin().getBlock())) {
				lic = block.getMovingTo().getBlock();
			}
		}
		Location loc = blocks.get(0).getMovingTo().clone().subtract(blocks.get(0).getOrigin());
		MovingBlock tBlock = new MovingBlock(type.getTeleportToLocation().getBlock(), loc.getBlockX(), loc.getBlockY(),
				loc.getBlockZ());
		if (lic == null) {
			MovingBlock lBlock = new MovingBlock(type.getLocation().getBlock(), loc.getBlockX(), loc.getBlockY(),
					loc.getBlockZ());
			lic = lBlock.getMovingTo().getBlock();
		}
		type.setBasicStructure(newStructure, lic, tBlock.getMovingTo());
		return true;
	}

	@Override
	public String getName() {
		return "Ships 5";
	}

}

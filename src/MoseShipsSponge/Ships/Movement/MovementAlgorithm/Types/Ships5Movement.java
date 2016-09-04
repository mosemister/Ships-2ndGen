package MoseShipsSponge.Ships.Movement.MovementAlgorithm.Types;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import MoseShipsSponge.Ships.Movement.MovementAlgorithm.MovementAlgorithm;
import MoseShipsSponge.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsSponge.Ships.VesselTypes.LoadableShip;
import MoseShipsSponge.Ships.VesselTypes.DefaultTypes.WaterType;

public class Ships5Movement implements MovementAlgorithm {

	@Override
	public void move(LoadableShip type, List<MovingBlock> blocksUn) {
		List<MovingBlock> blocks = MovingBlock.setPriorityOrder(blocksUn);
		int waterLevel = 63;
		if(type instanceof WaterType){
			WaterType type2 = (WaterType)type;
			waterLevel = type2.getWaterLevel();
		}
		final int waterLevelFinal = waterLevel;
		for(MovingBlock block : blocks){
			if(block.getOrigin().getBlockY() > waterLevelFinal){
				block.clearOriginalBlock();
			}else{
				block.replaceOriginalBlock(Material.STATIONARY_WATER, (byte)0);
			}
		}
		List<Block> newStructure = new ArrayList<Block>();
		// place all blocks
		for(MovingBlock block : blocks){
			newStructure.add(block.getMovingTo().getBlock());
			block.move();
		}
		/*blocks.stream().forEach(block -> {
			newStructure.add(block.getMovingTo());
			block.move(BlockChangeFlag.NONE);
			MovementType mType = block.getMovementType();
			Optional<Direction> opConnected = block.getMovingTo().get(Keys.DIRECTION);
			switch (mType) {
				case ROTATE_LEFT:
					if (opConnected.isPresent()) {
						BlockRotate.getRotation(opConnected.get(), RotateType.LEFT);
					}
					break;
				case ROTATE_RIGHT:
					if (opConnected.isPresent()) {
						BlockRotate.getRotation(opConnected.get(), RotateType.LEFT);
					}
					break;
				default:
					break;

			}
		});*/
		Location loc = blocks.get(0).getMovingTo().clone().subtract(blocks.get(0).getOrigin());
		MovingBlock tBlock = new MovingBlock(type.getTeleportToLocation().getBlock(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		MovingBlock lBlock = new MovingBlock(type.getLocation().getBlock(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		type.setBasicStructure(newStructure, lBlock.getMovingTo().getBlock(), tBlock.getMovingTo());
		// moveEntitys(move);

	}

	@Override
	public String getName() {
		return "Ships 5";
	}

}

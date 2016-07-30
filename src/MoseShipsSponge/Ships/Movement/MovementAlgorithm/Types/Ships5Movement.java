package MoseShipsSponge.Ships.Movement.MovementAlgorithm.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.BlockChangeFlag;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

import MoseShipsSponge.Causes.ShipsCause;
import MoseShipsSponge.Ships.Movement.MovementType;
import MoseShipsSponge.Ships.Movement.MovementAlgorithm.MovementAlgorithm;
import MoseShipsSponge.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsSponge.Ships.Movement.Rotate.BlockRotate;
import MoseShipsSponge.Ships.Movement.Rotate.RotateType;
import MoseShipsSponge.Ships.VesselTypes.ShipType;
import MoseShipsSponge.Ships.VesselTypes.DefaultTypes.WaterType;

public class Ships5Movement implements MovementAlgorithm {

	@Override
	public void move(ShipType type, List<MovingBlock> blocksUn) {
		List<MovingBlock> blocks = MovingBlock.setPriorityOrder(blocksUn);
		System.out.println("moving blocks size: " + blocks.size());
		int waterLevel = 63;
		if(type instanceof WaterType){
			WaterType type2 = (WaterType)type;
			waterLevel = type2.getWaterLevel();
		}
		final int waterLevelFinal = waterLevel;
		blocks.stream().forEach(block -> {
			if (block.getOrigin().getBlockY() > waterLevelFinal) {
				block.clearOriginalBlock(BlockChangeFlag.NONE, ShipsCause.BLOCK_MOVING.buildCause());
			} else {
				block.replaceOriginalBlock(BlockTypes.WATER, BlockChangeFlag.NONE, ShipsCause.BLOCK_MOVING.buildCause());
			}
		});
		List<Location<World>> newStructure = new ArrayList<>();
		// place all blocks
		blocks.stream().forEach(block -> {
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
		});
		Vector3i vec = blocks.get(0).getMovingTo().getBlockPosition().sub(blocks.get(0).getOrigin().getBlockPosition());
		MovingBlock tBlock = new MovingBlock(type.getTeleportToLocation(), vec.getX(), vec.getY(), vec.getZ());
		MovingBlock lBlock = new MovingBlock(type.getLocation(), vec.getX(), vec.getY(), vec.getZ());
		type.setBasicStructure(newStructure, lBlock.getMovingTo(), tBlock.getMovingTo());
		// moveEntitys(move);

	}

	@Override
	public String getName() {
		return "Ships 5";
	}

}

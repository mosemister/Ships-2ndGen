package MoseShipsSponge.Ships.Movement.MovementAlgorithm.Types;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.util.Direction;

import com.flowpowered.math.vector.Vector3i;

import MoseShipsSponge.Ships.Movement.MovementType;
import MoseShipsSponge.Ships.Movement.MovementAlgorithm.MovementAlgorithm;
import MoseShipsSponge.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsSponge.Ships.Movement.Rotate.BlockRotate;
import MoseShipsSponge.Ships.Movement.Rotate.RotateType;
import MoseShipsSponge.Ships.VesselTypes.ShipType;

public class Ships5Movement implements MovementAlgorithm{

	@Override
	public void move(ShipType type, List<MovingBlock> blocks) {
		int waterLevel = 0;
		/*if(type instanceof WaterType){
			WaterType type2 = (WaterType)type;
			waterLevel = type2.getWaterLevel();
		}*/
		blocks.stream().forEach(block -> {
			block.clearOriginalBlock();
			if (block.getOrigin().getBlockY() > waterLevel) {
				block.clearOriginalBlock();
			} else {
				block.replaceOriginalBlock(BlockTypes.WATER);
			}
		});
		// place all blocks (priority last)
		blocks.stream().forEach(block -> {
			block.move(false);
			MovementType mType = block.getMovementType();
			Optional<Direction> opConnected = block.getMovingTo().get(Keys.DIRECTION);
			switch(mType){
				case ROTATE_LEFT:
					if(opConnected.isPresent()){
						BlockRotate.getRotation(opConnected.get(), RotateType.LEFT);
					}
					break;
				case ROTATE_RIGHT:
					if(opConnected.isPresent()){
						BlockRotate.getRotation(opConnected.get(), RotateType.LEFT);
					}
					break;
				default:
					break;
				
			}
		});
		Vector3i vec = blocks.get(0).getMovingTo().getBlockPosition().sub(blocks.get(0).getOrigin().getBlockPosition());
		MovingBlock mBlock = new MovingBlock(type.getTeleportToLocation(), vec.getX(), vec.getY(), vec.getZ());
		/*type.setTeleportToLocation(mBlock.getMovingTo());
		updateLocation(mBlock.getMovingTo(), getSign());
		moveEntitys(move);
		updateStructure();*/
		
	}

	@Override
	public String name() {
		return "Ships 5";
	}

}

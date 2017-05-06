package MoseShipsSponge.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import MoseShipsSponge.Algorthum.Movement.MovementAlgorithm;
import MoseShipsSponge.Causes.MovementResult;
import MoseShipsSponge.Causes.ShipsCause;
import MoseShipsSponge.Movement.Result.FailedMovement;
import MoseShipsSponge.Movement.StoredMovement.StoredMovement;
import MoseShipsSponge.Movement.Type.CollideType;
import MoseShipsSponge.Movement.Type.MovementType;
import MoseShipsSponge.Ships.VesselTypes.LoadableShip;
import MoseShipsSponge.Ships.VesselTypes.DefaultTypes.WaterType;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class Movement {

	public static Optional<FailedMovement> teleport(ShipsCause cause2, LiveShip ship, Location<World> tel, BlockState... movingTo){
		return move(ship, MovementType.TELEPORT, new StoredMovement.Builder().setTeleportTo(tel).build());
	}
	
	public static Optional<FailedMovement> move(LiveShip ship, MovementType type, StoredMovement movement, BlockState... movingTo){
		MovingBlockList blocks = new MovingBlockList();
		List<MovingBlock> collide = new ArrayList<>();
		List<Location<World>> structure = ship.getBasicStructure();
		if(structure.isEmpty()){
			return Optional.of(new FailedMovement(ship, MovementResult.NO_BLOCKS, true));
		}
		waterShipFix(ship, structure);
		for(Location<World> loc : structure){
			MovingBlock block = new MovingBlock(loc, movement.getEndResult(loc));
			CollideType collideType = block.getCollision(ship.getBasicStructure(), movingTo);
			if(collideType.equals(CollideType.COLLIDE)){
				collide.add(block);
			}else{
				blocks.add(block);
			}
		}
		if(!collide.isEmpty()){
			return Optional.of(new FailedMovement(ship, MovementResult.COLLIDE_WITH, collide));
		}
		ShipsCause cause3 = new ShipsCause(movement.getCause(), structure);
		ship.load(cause3);
		return move(ship, type, blocks, cause3);
	}

	private static Optional<FailedMovement> move(LoadableShip ship, List<MovingBlock> blocks, Cause intCase) {
		Optional<MovementResult> opFail = ship.hasRequirements(blocks, intCase);
		if (opFail.isPresent()) {
			return opFail;
		}
		final List<Entity> entities = ship.getEntities();
		if (MovementAlgorithm.getConfig().move(ship, blocks, entities)) {
			Location<World> origin = blocks.get(0).getOrigin();
			Location<World> to = blocks.get(0).getMovingTo();
			double X = to.getX() - origin.getX();
			double Y = to.getY() - origin.getY();
			double Z = to.getZ() - origin.getZ();
			for (Entity entity : entities) {
				Location<World> eLoc = entity.getLocation();
				double tX = eLoc.getX() + X;
				double tY = eLoc.getY() + Y;
				double tZ = eLoc.getZ() + Z;
				Location<World> eTo = new Location<>(eLoc.getExtent(), tX, tY, tZ);
				Vector3d rotation = entity.getRotation();
				entity.setLocationAndRotation(eTo, rotation);
			}
		}
		return Optional.empty();
	}

	private static void waterShipFix(WaterType ship, List<Location<World>> structure) {
		if(!(ship instanceof WaterType)){
			return;
		}
		int D = structure.size();
		for (int B = 0; B < D; B++) {
			for (int C = 0; C < D; C++) {
				if (B != C) {
					Location<World> loc = structure.get(B);
					Location<World> loc2 = structure.get(C);
					if ((loc.getBlockX() == loc2.getBlockX())) {
						Location<World> small = loc2;
						Location<World> large = loc;
						if (loc.getBlockZ() > loc2.getBlockZ()) {
							small = loc;
							large = loc2;
						}
						int def = large.getBlockZ() - small.getBlockZ();

						for (int A = 0; A < def; A++) {
							Location<World> loc3 = new Location<>(loc.getExtent(), small.getBlockX(), small.getBlockY(),
									small.getBlockZ() + A);
							if (loc3.getBlockType().equals(BlockTypes.AIR)) {
								structure.add(loc3);
							}
						}
					} else if (loc.getBlockZ() == loc2.getBlockZ()) {
						Location<World> small = loc2;
						Location<World> large = loc;
						if (loc.getBlockZ() > loc2.getBlockZ()) {
							small = loc;
							large = loc2;
						}
						int def = large.getBlockX() - small.getBlockX();
						for (int A = 0; A < def; A++) {
							Location<World> loc3 = new Location<>(loc.getExtent(), small.getBlockX() + A,
									small.getBlockY(), small.getBlockZ());
							if (loc.getBlockType().equals(BlockTypes.AIR)) {
								structure.add(loc3);
							}
						}
					}
				}
			}
		}
	}

}

package MoseShipsSponge.Event.Transform;

import java.util.List;

import org.spongepowered.api.event.cause.Cause;

import MoseShipsSponge.Event.ShipsEvent;
import MoseShipsSponge.Movement.MovingBlock;
import MoseShipsSponge.Movement.Type.MovementType;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public abstract class ShipTransformEvent extends ShipsEvent {

	List<MovingBlock> g_blocks;
	MovementType g_type;

	public ShipTransformEvent(Cause cause, LiveShip ship, MovementType type, List<MovingBlock> blocks) {
		super(ship, cause);
		g_blocks = blocks;
		g_type = type;
	}

	public MovementType getMovementType() {
		return g_type;
	}

	public List<MovingBlock> getMovingBlocks() {
		return g_blocks;
	}

	@Override
	public LiveShip getShip() {
		return (LiveShip) super.getShip();
	}

}

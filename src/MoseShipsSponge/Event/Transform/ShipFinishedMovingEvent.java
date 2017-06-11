package MoseShipsSponge.Event.Transform;

import java.util.List;

import org.spongepowered.api.event.cause.Cause;

import MoseShipsSponge.Movement.MovingBlock;
import MoseShipsSponge.Movement.Type.MovementType;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class ShipFinishedMovingEvent extends ShipTransformEvent{

	public ShipFinishedMovingEvent(Cause cause, LiveShip ship, MovementType type, List<MovingBlock> blocks) {
		super(cause, ship, type, blocks);
	}

}

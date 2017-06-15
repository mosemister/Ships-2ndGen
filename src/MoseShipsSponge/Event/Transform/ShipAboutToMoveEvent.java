package MoseShipsSponge.Event.Transform;

import java.util.List;

import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;

import MoseShipsSponge.Movement.MovingBlock;
import MoseShipsSponge.Movement.Type.MovementType;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class ShipAboutToMoveEvent extends ShipTransformEvent implements Cancellable {

	boolean g_cancelled;

	public ShipAboutToMoveEvent(Cause cause, LiveShip ship, MovementType type, List<MovingBlock> blocks) {
		super(cause, ship, type, blocks);
	}

	@Override
	public boolean isCancelled() {
		return g_cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		g_cancelled = cancel;
	}

}

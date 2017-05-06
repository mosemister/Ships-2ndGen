package MoseShipsBukkit.Events.Transform;

import java.util.List;

import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Movement.MovingBlock;
import MoseShipsBukkit.Movement.Type.MovementType;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;

public class ShipFinishedMovingEvent extends ShipTransformEvent {

	public ShipFinishedMovingEvent(ShipsCause cause, LiveShip ship, MovementType type, List<MovingBlock> blocks) {
		super(cause, ship, type, blocks);
	}

	public static HandlerList getHandlerList() {
		return g_handlers;
	}

}

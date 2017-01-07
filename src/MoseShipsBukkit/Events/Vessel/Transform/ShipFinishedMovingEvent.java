package MoseShipsBukkit.Events.Vessel.Transform;

import java.util.List;

import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Causes.ShipsCause;
import MoseShipsBukkit.Ships.Movement.MovementType;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveShip;

public class ShipFinishedMovingEvent extends ShipTransformEvent{

	public ShipFinishedMovingEvent(ShipsCause cause, LiveShip ship, MovementType type, List<MovingBlock> blocks) {
		super(cause, ship, type, blocks);
	}
	
	public static HandlerList getHandlerList(){
		return g_handlers;
	}

}

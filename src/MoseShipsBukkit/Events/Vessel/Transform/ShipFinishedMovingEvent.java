package MoseShipsBukkit.Events.Vessel.Transform;

import java.util.List;

import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Ships.Movement.MovementType;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveData;

public class ShipFinishedMovingEvent extends ShipTransformEvent{

	public ShipFinishedMovingEvent(LiveData ship, MovementType type, List<MovingBlock> blocks) {
		super(ship, type, blocks);
	}
	
	public static HandlerList getHandlerList(){
		return g_handlers;
	}

}

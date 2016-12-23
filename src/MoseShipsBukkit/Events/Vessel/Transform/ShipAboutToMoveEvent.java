package MoseShipsBukkit.Events.Vessel.Transform;

import java.util.List;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Ships.Movement.MovementType;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveData;

public class ShipAboutToMoveEvent extends ShipTransformEvent implements Cancellable{

	boolean g_cancelled;
	
	public ShipAboutToMoveEvent(LiveData ship, MovementType type, List<MovingBlock> blocks) {
		super(ship, type, blocks);
	}

	@Override
	public boolean isCancelled() {
		return g_cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		g_cancelled = arg0;
	}
	
	public static HandlerList getHandlerList(){
		return g_handlers;
	}

}

package MoseShipsBukkit.Events.Transform;

import java.util.List;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Movement.MovingBlock;
import MoseShipsBukkit.Movement.Type.MovementType;
import MoseShipsBukkit.Vessel.Data.LiveShip;

public class ShipAboutToMoveEvent extends ShipTransformEvent implements Cancellable{

	boolean g_cancelled;
	
	public ShipAboutToMoveEvent(ShipsCause cause, LiveShip ship, MovementType type, List<MovingBlock> blocks) {
		super(cause, ship, type, blocks);
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

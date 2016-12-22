package MoseShipsBukkit.Events.Vessel.Transform;

import java.util.List;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Ships.Movement.MovementType;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;

public class ShipsAboutToMoveEvent extends ShipsTransformEvent implements Cancellable{

	boolean g_cancelled;
	
	public ShipsAboutToMoveEvent(LoadableShip ship, MovementType type, List<MovingBlock> blocks) {
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

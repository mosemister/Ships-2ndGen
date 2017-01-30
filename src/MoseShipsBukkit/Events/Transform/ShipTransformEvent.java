package MoseShipsBukkit.Events.Transform;

import java.util.List;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.ShipEvent;
import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Movement.MovingBlock;
import MoseShipsBukkit.Movement.Type.MovementType;
import MoseShipsBukkit.Vessel.Data.LiveShip;

public class ShipTransformEvent extends Event implements ShipEvent {

	List<MovingBlock> g_blocks;
	MovementType g_type;
	LiveShip g_ship;
	ShipsCause g_cause;
	static HandlerList g_handlers = new HandlerList();

	public ShipTransformEvent(ShipsCause cause, LiveShip ship, MovementType type, List<MovingBlock> blocks) {
		g_ship = ship;
		g_blocks = blocks;
		g_type = type;
		g_cause = cause;
	}

	@Override
	public ShipsCause getCause() {
		return g_cause;
	}

	public MovementType getMovementType() {
		return g_type;
	}

	public List<MovingBlock> getMovingBlocks() {
		return g_blocks;
	}

	@Override
	public LiveShip getShip() {
		return g_ship;
	}

	@Override
	public HandlerList getHandlers() {
		return g_handlers;
	}

	public static HandlerList getHandlerList() {
		return g_handlers;
	}

}

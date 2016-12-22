package MoseShipsBukkit.Events.Vessel.Transform;

import java.util.List;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.Vessel.ShipsEvent;
import MoseShipsBukkit.Ships.Movement.MovementType;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;

public class ShipsTransformEvent extends Event implements ShipsEvent{
	
	List<MovingBlock> g_blocks;
	MovementType g_type;
	LoadableShip g_ship;
	static HandlerList g_handlers = new HandlerList();
	
	public ShipsTransformEvent(LoadableShip ship, MovementType type, List<MovingBlock> blocks){
		g_ship = ship;
		g_blocks = blocks;
		g_type = type;
	}
	
	public MovementType getMovementType(){
		return g_type;
	}
	
	public List<MovingBlock> getMovingBlocks(){
		return g_blocks;
	}

	@Override
	public LoadableShip getShip() {
		return g_ship;
	}

	@Override
	public HandlerList getHandlers() {
		return g_handlers;
	}
	
	public static HandlerList getHandlerList(){
		return g_handlers;
	}

}

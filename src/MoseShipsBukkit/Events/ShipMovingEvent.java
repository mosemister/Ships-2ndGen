package MoseShipsBukkit.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.MovingShip.MovingStructure;
import MoseShipsBukkit.StillShip.Vessel;

public class ShipMovingEvent extends Event implements Cancellable{
	
	Player PLAYER;
	Vessel VESSEL;
	MovementMethod MOVEMETHOD;
	MovingStructure MOVINGBLOCKS;
	boolean CANCELLED;
	static final HandlerList HANDLERS = new HandlerList();
	
	public ShipMovingEvent(Player player, Vessel vessel, MovementMethod method, MovingStructure blocks){
		PLAYER = player;
		VESSEL = vessel;
		MOVEMETHOD = method;
		MOVINGBLOCKS = blocks;
	}
	
	public Player getPlayer(){
		return PLAYER;
	}
	
	public Vessel getVessel(){
		return VESSEL;
	}
	
	public MovementMethod getMovementMethod(){
		return MOVEMETHOD;
	}
	
	public MovingStructure getStructure(){
		return MOVINGBLOCKS;
	}
	
	public void setMovingBlocks(MovingStructure structure){
		MOVINGBLOCKS = structure;
	}

	@Override
	public boolean isCancelled() {
		return CANCELLED;
	}

	@Override
	public void setCancelled(boolean arg0) {
		CANCELLED = arg0;
		
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static HandlerList getHandlerList(){
		return HANDLERS;
	}
}
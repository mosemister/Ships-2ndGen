package MoseShipsBukkit.Events;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.MovingShip.MovingBlock;
import MoseShipsBukkit.StillShip.Vessel;

public class ShipMoveEvent extends Event implements Cancellable{
	
	Player PLAYER;
	Vessel VESSEL;
	MovementMethod MOVEMETHOD;
	List<MovingBlock> MOVINGBLOCKS;
	boolean CANCELLED;
	static final HandlerList HANDLERS = new HandlerList();
	
	public ShipMoveEvent(Player player, Vessel vessel, MovementMethod method, List<MovingBlock> blocks){
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
	
	public List<MovingBlock> getMovingBlocks(){
		return MOVINGBLOCKS;
	}
	
	public void setMovingBlocks(List<MovingBlock> blocks){
		MOVINGBLOCKS = blocks;
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

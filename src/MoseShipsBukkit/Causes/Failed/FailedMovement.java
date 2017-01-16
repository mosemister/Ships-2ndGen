package MoseShipsBukkit.Causes.Failed;

import org.bukkit.entity.Player;

import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveShip;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.ShipsData;

public class FailedMovement {
	
	ContainedFailedMovement<? extends Object> g_movement;
	
	public <E extends Object> FailedMovement(LiveShip ship, MovementResult<E> result, E value){
		g_movement = new ContainedFailedMovement<E>(ship, result, value);
	}
	
	public MovementResult<? extends Object> getResult(){
		return g_movement.getResult();
	}
	
	public Object getValue(){
		return g_movement.getValue();
	}
	
	public ShipsData getShip(){
		return g_movement.getShip();
	}
	
	public void process(Player player){
		g_movement.process(player);
	}
	
	public class ContainedFailedMovement <E extends Object>{
	
		MovementResult<E> g_result;
		E g_data;
		LiveShip g_ship;
	
		public ContainedFailedMovement(LiveShip ship, MovementResult<E> result, E value){
			g_ship = ship;
			g_data = value;
			g_result = result;
		}
	
		public MovementResult<E> getResult(){
			return g_result;
		}
	
		public E getValue(){
			return g_data;
		}
	
		public ShipsData getShip(){
			return g_ship;
		}
	
		public void process(Player player){
			g_result.sendMessage(g_ship, player, g_data);
		}
	}
}

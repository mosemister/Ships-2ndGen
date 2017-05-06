package MoseShipsSponge.Movement.Result;

import org.spongepowered.api.command.CommandSource;

import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class FailedMovement {
	
	ContainedFailedMovement<? extends Object> g_movement;
	
	public <E extends Object> FailedMovement(LiveShip ship, MovementResult<E> result, E value){
		g_movement = new ContainedFailedMovement<E>(ship, result, value);
	}
	
	public Object getValue(){
		return g_movement.getValue();
	}
	
	public LiveShip getShip(){
		return g_movement.getShip();
	}
	
	public void process(CommandSource source){
		g_movement.process(source);
	}
	
	public class ContainedFailedMovement<E extends Object> {
		
		MovementResult<E> g_result;
		E g_data;
		LiveShip g_ship;
	
		public ContainedFailedMovement(LiveShip ship, MovementResult<E> result, E value) {
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
		
		public LiveShip getShip(){
			return g_ship;
		}
		
		public void process(CommandSource source){
			g_result.sendMessage(g_ship, source, g_data);
		}
		
	}

}

package MoseShipsSponge.Causes;

import java.util.Map;

import MoseShips.Maps.OrderedMap;

public class FailedCause {
	
	OrderedMap<Object, Object> CAUSES = new OrderedMap<>();
	
	public Map<Object, Object> getCauses(){
		return CAUSES;
	}
	
	public static enum FailedKeys{
		COLLIDE,
		AUTO_PILOT_OUT_OF_MOVES,
		OUT_OF_FUEL,
		MISSING_REQUIRED_BLOCK,
		NOT_ENOUGH_BLOCKS,
		TOO_MANY_BLOCKS,
		NOT_ENOUGH_PERCENT
	}
	
	public static class CauseKeys{
		public static final String MOVING_BLOCKS = "MovingBlock";
	}
}

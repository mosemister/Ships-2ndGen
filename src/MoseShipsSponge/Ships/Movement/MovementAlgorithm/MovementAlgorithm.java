package MoseShipsSponge.Ships.Movement.MovementAlgorithm;

import java.util.ArrayList;
import java.util.List;

import MoseShipsSponge.Ships.Movement.MovementAlgorithm.Types.Ships5Movement;
import MoseShipsSponge.Ships.Movement.MovementAlgorithm.Types.Ships6Movement;
import MoseShipsSponge.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsSponge.Ships.VesselTypes.ShipType;

public interface MovementAlgorithm {
	
	static List<MovementAlgorithm> MOVEMENT_ALGORITHMS = new ArrayList<>();
	
	public static Ships5Movement SHIPS5 = new Ships5Movement();
	public static MovementAlgorithm SHIPS6 = new Ships6Movement();
	
	public void move(ShipType vessel, List<MovingBlock> blocks);
	public String name();
	
	public static List<MovementAlgorithm> get(){
		List<MovementAlgorithm> list = new ArrayList<>(MOVEMENT_ALGORITHMS);
		list.add(SHIPS5);
		list.add(SHIPS6);
		return list;
	}

}

package MoseShipsBukkit.Ships.Movement.MovementAlgorithm;

import java.util.ArrayList;
import java.util.List;

import MoseShipsBukkit.Ships.Movement.MovementAlgorithm.Types.Ships5Movement;
import MoseShipsBukkit.Ships.Movement.MovementAlgorithm.Types.Ships6Movement;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;

public interface MovementAlgorithm {

	static List<MovementAlgorithm> MOVEMENT_ALGORITHMS = new ArrayList<MovementAlgorithm>();

	public static Ships5Movement SHIPS5 = new Ships5Movement();
	public static Ships6Movement SHIPS6 = new Ships6Movement();

	public void move(LoadableShip vessel, List<MovingBlock> blocks);

	public String getName();

}

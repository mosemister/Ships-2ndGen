package MoseShipsBukkit.Algorthum.Movement;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;

import MoseShipsBukkit.Movement.MovingBlock;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;

public interface MovementAlgorithm {

	static List<MovementAlgorithm> MOVEMENT_ALGORITHMS = new ArrayList<MovementAlgorithm>();

	public static Ships5Movement SHIPS5 = new Ships5Movement();
	public static Ships6Movement SHIPS6 = new Ships6Movement();

	/**
	 * This will called if your algorithm is activated and when a ship is
	 * attempting to move
	 * 
	 * @param vessel
	 *            = The targeted vessel attempting to move
	 * @param blocks
	 *            = The location of the block of the ship and where it wants to
	 *            be moved to
	 * @param onBoard
	 *            = a list of entities that have been found
	 * @return = if the entities should be teleported
	 */
	public boolean move(LiveShip vessel, List<MovingBlock> blocks, List<Entity> onBoard);

	/**
	 * this is the name of the algorithm for the owner to activate
	 * 
	 * @return = the name
	 */
	public String getName();

}

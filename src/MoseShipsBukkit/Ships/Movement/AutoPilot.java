package MoseShipsBukkit.Ships.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;

import MoseShips.Maps.OrderedMap;

import MoseShipsBukkit.Causes.MovementResult;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;

public class AutoPilot {

	List<StoredMovement> MOVEMENTS = new ArrayList<StoredMovement>();
	LoadableShip SHIP;
	OfflinePlayer USER;
	int TARGET;
	boolean SHOULD_REPEATE;

	public AutoPilot(LoadableShip type, List<StoredMovement> movements, boolean repeate, int start,
			@Nullable OfflinePlayer user) {
		MOVEMENTS = movements;
		SHIP = type;
		SHOULD_REPEATE = repeate;
		TARGET = start;
		USER = user;
	}

	public AutoPilot(LoadableShip type, List<StoredMovement> movements, boolean repeate, @Nullable OfflinePlayer user) {
		MOVEMENTS = movements;
		SHIP = type;
		SHOULD_REPEATE = repeate;
		USER = user;
	}

	public AutoPilot(LoadableShip type, Block moveTo, int speed, @Nullable OfflinePlayer user) {
		// TODO create path from ship to location
	}

	public List<StoredMovement> getMovements() {
		return MOVEMENTS;
	}

	public LoadableShip getTargetShip() {
		return SHIP;
	}

	public Optional<OfflinePlayer> getTargetPlayer() {
		return Optional.ofNullable(USER);
	}

	public boolean isRepeating() {
		return SHOULD_REPEATE;
	}

	public Optional<MovementResult> next() {
		if (MOVEMENTS.size() != 0) {
			if (MOVEMENTS.size() < TARGET) {
				StoredMovement movement = MOVEMENTS.get(TARGET);
				Optional<MovementResult> opFail = Movement.teleport(SHIP, movement);
				TARGET++;
				return opFail;
			} else if (SHOULD_REPEATE) {
				StoredMovement movement = MOVEMENTS.get(0);
				Optional<MovementResult> opFail = Movement.teleport(SHIP, movement);
				TARGET++;
				return opFail;
			}
		}
		MovementResult fail = new MovementResult();
		fail.put(MovementResult.CauseKeys.AUTO_PILOT_OUT_OF_MOVES, this);
		return Optional.of(fail);
	}

	public OrderedMap<Integer, Block> getPath() {
		OrderedMap<Integer, Block> map = new OrderedMap<Integer, Block>();
		map.put(0, SHIP.getLocation().getBlock());
		Block previousLoc = SHIP.getLocation().getBlock();
		for (int A = 1; A < MOVEMENTS.size(); A++) {
			StoredMovement movement = MOVEMENTS.get(A - 1);
			Block ret = movement.getEndResult(previousLoc);
			map.put(A, ret);
			previousLoc = ret;
		}
		return map;
	}

}

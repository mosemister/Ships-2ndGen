package MoseShipsSponge.Ships.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShips.Maps.OrderedMap;

import MoseShipsSponge.Causes.MovementResult;
import MoseShipsSponge.Ships.Movement.Movement;
import MoseShipsSponge.Ships.Movement.StoredMovement;
import MoseShipsSponge.Ships.VesselTypes.ShipType;

public class AutoPilot {

	List<StoredMovement> MOVEMENTS = new ArrayList<>();
	ShipType SHIP;
	@Nullable
	User USER;
	int TARGET;
	boolean SHOULD_REPEATE;

	public AutoPilot(ShipType type, List<StoredMovement> movements, boolean repeate, int start, @Nullable User user) {
		MOVEMENTS = movements;
		SHIP = type;
		SHOULD_REPEATE = repeate;
		TARGET = start;
		USER = user;
	}

	public AutoPilot(ShipType type, List<StoredMovement> movements, boolean repeate, @Nullable User user) {
		MOVEMENTS = movements;
		SHIP = type;
		SHOULD_REPEATE = repeate;
		USER = user;
	}

	public AutoPilot(ShipType type, Location<World> moveTo, int speed, @Nullable User user) {
		// TODO create path from ship to location
	}

	public List<StoredMovement> getMovements() {
		return MOVEMENTS;
	}

	public ShipType getTargetShip() {
		return SHIP;
	}

	public Optional<User> getTargetPlayer() {
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

	public OrderedMap<Integer, Location<World>> getPath() {
		OrderedMap<Integer, Location<World>> map = new OrderedMap<>();
		map.put(0, SHIP.getLocation());
		Location<World> previousLoc = SHIP.getLocation();
		for (int A = 1; A < MOVEMENTS.size(); A++) {
			StoredMovement movement = MOVEMENTS.get(A - 1);
			Location<World> ret = movement.getEndResult(previousLoc);
			map.put(A, ret);
			previousLoc = ret;
		}
		return map;
	}

}

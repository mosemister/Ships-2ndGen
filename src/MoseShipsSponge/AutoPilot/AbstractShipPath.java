package MoseShipsSponge.AutoPilot;

import java.util.Optional;

import org.spongepowered.api.util.Direction;

import com.flowpowered.math.vector.Vector3i;

import MoseShipsSponge.Movement.Type.RotateType;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class AbstractShipPath extends BasicShipPath {

	LiveShip g_ship;
	boolean g_rotated;

	public AbstractShipPath(LiveShip ship, Vector3i pos2, boolean upAndOver) {
		super(ship, pos2, upAndOver);
		g_ship = ship;
	}

	@Override
	public Optional<Vector3i> getNextPosition() {
		Optional<LinePath> opPath = getCurrentPath();
		if (!opPath.isPresent()) {
			return Optional.empty();
		}
		Direction direction = opPath.get().getDirection();
		Optional<Vector3i> ret = super.getNextPosition();
		if (!g_rotated) {
			if (g_current_stage_in_path != 0) {
				g_current_stage_in_path = g_current_stage_in_path - 1;
			} else {
				g_current_path = g_current_path - 1;
				g_current_stage_in_path = getCurrentPath().get().createLine(g_speed).size() - 1;
			}
		}
		if (!ret.isPresent()) {
			return ret;
		}
		LinePath path2 = getCurrentPath().get();
		if (path2.getDirection().equals(direction)) {
			return ret;
		}
		Optional<RotateType> opRotate = RotateType.getRotation(direction, path2.getDirection());
		if (!opRotate.isPresent()) {
			return ret;
		}
		g_rotated = true;
		g_ship.rotate(opRotate.get()/*, Cause.source(this).build()*/);
		return Optional.empty();
	}

}

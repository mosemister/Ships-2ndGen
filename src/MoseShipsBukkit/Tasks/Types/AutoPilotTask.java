package MoseShipsBukkit.Tasks.Types;

import java.util.Optional;

import MoseShipsBukkit.Configs.ShipsConfig;
import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Movement.StoredMovement.AutoPilot;
import MoseShipsBukkit.Movement.StoredMovement.StoredMovement;
import MoseShipsBukkit.Tasks.ShipsTask;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.Common.RootTypes.Implementations.AutoPilotableShip;

public class AutoPilotTask implements ShipsTask {

	@Override
	public void onRun(LiveShip ship) {
		AutoPilotableShip ship2 = (AutoPilotableShip)ship;
		Optional<AutoPilot> opData = ship2.getAutoPilotData();
		if (!opData.isPresent()) {
			AutoPilot data = opData.get();
			if (data.getMovesDone() == (data.getMovements().size() - 1)) {
				if (data.isRepeating()) {
					data.setMovesDone(0);
				} else {
					ship2.setAutoPilotData(null);
				}
			}
			data.setMovesDone(data.getMovesDone() + 1);

			StoredMovement movement = data.getMovements().get(data.getMovesDone());
			Optional<FailedMovement> move = ship.teleport(movement, new ShipsCause(data, movement));
			if (move.isPresent()) {
				FailedMovement result = move.get();
				if (data.getTargetPlayer().isPresent()) {
					if (data.getTargetPlayer().get().isOnline()) {
						result.process(data.getTargetPlayer().get().getPlayer());
					}
				}
				ship2.setAutoPilotData(null);
			}
		}
	}

	@Override
	public long getDelay() {
		return ShipsConfig.CONFIG.get(Integer.class, ShipsConfig.PATH_SCHEDULER_AUTOPILOT);
	}

}

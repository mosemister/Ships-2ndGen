package MoseShipsBukkit.Ships.VesselTypes.Running.Tasks;

import java.util.Optional;

import MoseShips.Stores.TwoStore;
import MoseShipsBukkit.Causes.ShipsCause;
import MoseShipsBukkit.Causes.Failed.MovementResult;
import MoseShipsBukkit.Causes.Failed.MovementResult.CauseKeys;
import MoseShipsBukkit.Configs.Files.ShipsConfig;
import MoseShipsBukkit.Ships.Movement.StoredMovement;
import MoseShipsBukkit.Ships.Movement.AutoPilot.AutoPilot;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveShip;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.Live.LiveAutoPilotable;
import MoseShipsBukkit.Ships.VesselTypes.Running.ShipsTask;

public class AutoPilotTask implements ShipsTask {

	@Override
	public void onRun(LiveShip ship) {
		LiveAutoPilotable ship2 = (LiveAutoPilotable)ship;
		Optional<AutoPilot> opData = ship2.getAutoPilotData();
		if (opData.isPresent()) {
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
			Optional<MovementResult> move = ship2.teleport(movement, new ShipsCause(data, movement));
			if (move.isPresent()) {
				MovementResult result = move.get();
				if (result.getFailedCause().isPresent()) {
					if (data.getTargetPlayer().isPresent()) {
						if (data.getTargetPlayer().get().isOnline()) {
							TwoStore<CauseKeys<Object>, Object> fail = result.getFailedCause().get();
							fail.getFirst().sendMessage(ship, data.getTargetPlayer().get().getPlayer(), fail.getSecond());
						}
					}
					ship2.setAutoPilotData(null);
				}
			}
		}
	}

	@Override
	public long getDelay() {
		return ShipsConfig.CONFIG.get(Integer.class, ShipsConfig.PATH_SCHEDULER_AUTOPILOT);
	}

}

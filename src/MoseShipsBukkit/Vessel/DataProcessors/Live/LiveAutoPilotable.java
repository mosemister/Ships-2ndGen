package MoseShipsBukkit.Vessel.DataProcessors.Live;

import java.util.Optional;

import MoseShipsBukkit.Movement.StoredMovement.AutoPilot;
import MoseShipsBukkit.Vessel.Data.LiveShip;

public interface LiveAutoPilotable extends LiveShip {

	public Optional<AutoPilot> getAutoPilotData();

	public LiveAutoPilotable setAutoPilotData(AutoPilot pilot);
}

package MoseShipsBukkit.Vessel.Common.RootTypes.Implementations;

import java.util.Optional;

import MoseShipsBukkit.Movement.StoredMovement.AutoPilot;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;

public interface AutoPilotableShip extends LiveShip {
	
	public Optional<AutoPilot> getAutoPilotData();
	public AutoPilotableShip setAutoPilotData(AutoPilot auto);

}

package MoseShipsSponge.Vessel.Common.RootTypes.Implementations;

import java.util.Optional;

import MoseShipsSponge.Movement.StoredMovement.AutoPilot;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public interface AutoPilotableShip extends LiveShip {

	public Optional<AutoPilot> getAutoPilotData();

	public AutoPilotableShip setAutoPilotData(AutoPilot auto);

}

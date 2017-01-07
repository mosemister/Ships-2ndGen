package MoseShipsBukkit.Ships.VesselTypes.DataTypes.Live;

import java.util.Optional;

import MoseShipsBukkit.Ships.Movement.AutoPilot.AutoPilot;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveShip;

public interface LiveAutoPilotable extends LiveShip {
	
	public Optional<AutoPilot> getAutoPilotData();
	public LiveAutoPilotable setAutoPilotData(AutoPilot pilot);	
}

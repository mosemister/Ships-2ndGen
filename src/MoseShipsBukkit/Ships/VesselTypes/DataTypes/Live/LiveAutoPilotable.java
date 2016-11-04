package MoseShipsBukkit.Ships.VesselTypes.DataTypes.Live;

import java.util.Optional;

import MoseShipsBukkit.Ships.Movement.AutoPilot.AutoPilot;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveData;

public interface LiveAutoPilotable extends LiveData {
	
	public Optional<AutoPilot> getAutoPilotData();
	public LiveAutoPilotable setAutoPilotData(AutoPilot pilot);	
}

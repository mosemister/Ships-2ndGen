package MoseShipsBukkit.Vessel.Common.RootTypes.Implementations;

import MoseShipsBukkit.Movement.StoredMovement.AutoPilot;
import MoseShipsBukkit.Utils.SOptional;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;

public interface AutoPilotableShip extends LiveShip {
	
	public SOptional<AutoPilot> getAutoPilotData();
	public AutoPilotableShip setAutoPilotData(AutoPilot auto);

}

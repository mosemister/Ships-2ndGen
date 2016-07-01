package MoseShipsBukkit.ShipsTypes;

import MoseShipsBukkit.ShipsTypes.Types.Airship;
import MoseShipsBukkit.ShipsTypes.Types.Marsship;
import MoseShipsBukkit.ShipsTypes.Types.Plane;
import MoseShipsBukkit.ShipsTypes.Types.Solarship;
import MoseShipsBukkit.ShipsTypes.Types.Submarine;
import MoseShipsBukkit.ShipsTypes.Types.Watership;

public enum VesselTypes {

	AIRSHIP(new Airship()), MARSSHIP(new Marsship()), PLANE(new Plane()), SOLARSHIP(new Solarship()), SUBMARINE(
			new Submarine()), WATERSHIP(new Watership());

	VesselType VESSELTYPE;

	VesselTypes(VesselType type) {
		VESSELTYPE = type;
	}

	public VesselType get() {
		return VESSELTYPE;
	}

}

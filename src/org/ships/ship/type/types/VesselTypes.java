package org.ships.ship.type.types;

import org.ships.ship.type.VesselType;

public enum VesselTypes {

	AIRSHIP(new Airship()), 
	MARSSHIP(new Marsship()), 
	PLANE(new Plane()),
	SUBMARINE(new Submarine()), 
	WATERSHIP(new Watership());

	VesselType VESSELTYPE;

	VesselTypes(VesselType type) {
		VESSELTYPE = type;
	}

	public VesselType get() {
		return VESSELTYPE;
	}

}

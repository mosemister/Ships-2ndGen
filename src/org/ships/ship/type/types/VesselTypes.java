package org.ships.ship.type.types;

import java.lang.reflect.Field;

import org.ships.ship.type.VesselType;

public class VesselTypes {
	public static final Airship AIRSHIP = new Airship();
	public static final Marsship MARSHIP = new Marsship();
	public static final Plane PLANE = new Plane();
	public static final Submarine SUBMARINE = new Submarine();
	public static final Watership WATERSHIP = new Watership();

	public static VesselType[] values() {
		Field[] fields = VesselTypes.class.getFields();
		VesselType[] types = new VesselType[fields.length];
		for (int A = 0; A < fields.length; ++A) {
			try {
				types[A] = (VesselType) fields[A].get(null);
				continue;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return types;
	}
}

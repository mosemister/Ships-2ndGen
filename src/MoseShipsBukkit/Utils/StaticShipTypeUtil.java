package MoseShipsBukkit.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import MoseShipsBukkit.Vessel.Common.Static.StaticShipType;

public class StaticShipTypeUtil {

	public static File DEFAULT_SHIP_TYPE_LOCATION = new File("plugins/Ships/Configuration/ShipTypes/");

	public static void inject(StaticShipType type) {
		StaticShipType.TYPES.add(type);
	}

	public static List<StaticShipType> getTypes() {
		return new ArrayList<StaticShipType>(StaticShipType.TYPES);
	}

	public static SOptional<StaticShipType> getType(String name) {
		for (StaticShipType type : StaticShipType.TYPES) {
			if (type.getName().equalsIgnoreCase(name)) {
				return new SOptional<StaticShipType>(type);
			}
		}
		return new SOptional<StaticShipType>();
	}

	@SuppressWarnings("unchecked")
	public static <T extends StaticShipType> SOptional<T> getType(Class<T> type) {
		for (StaticShipType type2 : StaticShipType.TYPES) {
			if (type.isInstance(type2)) {
				return new SOptional<T>((T) type2);
			}
		}
		return new SOptional<T>();
	}

}

package MoseShipsSponge.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import MoseShipsSponge.Vessel.Common.Static.StaticShipType;

public class StaticShipTypeUtil {

	public static File DEFAULT_SHIP_TYPE_LOCATION = new File("configuration/Ships/Configuration/ShipTypes/");

	public static void inject(StaticShipType type) {
		StaticShipType.TYPES.add(type);
	}

	public static List<StaticShipType> getTypes() {
		return new ArrayList<>(StaticShipType.TYPES);
	}

	public static Optional<StaticShipType> getType(String name) {
		return StaticShipType.TYPES.stream().filter(t -> t.getName().equalsIgnoreCase(name)).findAny();
	}

	@SuppressWarnings("unchecked")
	public static <T extends StaticShipType> Optional<T> getType(Class<T> type) {
		return (Optional<T>) StaticShipType.TYPES.stream().filter(t -> type.isInstance(t)).findAny();
	}

}

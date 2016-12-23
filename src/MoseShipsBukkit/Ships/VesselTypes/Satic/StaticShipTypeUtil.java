package MoseShipsBukkit.Ships.VesselTypes.Satic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StaticShipTypeUtil {

	public static File DEFAULT_SHIP_TYPE_LOCATION = new File("plugins/Ships/Configuration/ShipTypes/");
	
	public static void inject(StaticShipType type) {
		StaticShipType.TYPES.add(type);
	}

	public static List<StaticShipType> getTypes() {
		return new ArrayList<StaticShipType>(StaticShipType.TYPES);
	}

	public static Optional<StaticShipType> getType(String name) {
		for (StaticShipType type : StaticShipType.TYPES) {
			if (type.getName().equalsIgnoreCase(name)) {
				return Optional.of(type);
			}
		}
		return Optional.empty();
	}

	@SuppressWarnings("unchecked")
	public static <T extends StaticShipType> Optional<T> getType(Class<T> type) {
		for (StaticShipType type2 : StaticShipType.TYPES) {
			if (type.isInstance(type2)) {
				return Optional.of((T) type2);
			}
		}
		return Optional.empty();
	}

}

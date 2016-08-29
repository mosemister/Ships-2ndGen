package MoseShipsSponge.Ships.VesselTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Configs.BasicConfig;
import MoseShipsSponge.Ships.ShipsData;

public interface StaticShipType {

	List<StaticShipType> TYPES = new ArrayList<>();

	public String getName();

	public int getDefaultSpeed();

	public int getBoostSpeed();

	public int getAltitudeSpeed();

	public boolean autoPilot();

	public Optional<LoadableShip> createVessel(String name, Location<World> licence);

	public Optional<LoadableShip> loadVessel(ShipsData data, BasicConfig config);

	public static void inject(StaticShipType type) {
		TYPES.add(type);
	}

	public static List<StaticShipType> getTypes() {
		return new ArrayList<>(TYPES);
	}

	public static Optional<StaticShipType> getType(String name) {
		System.out.println("Ships types" + TYPES.size());
		return TYPES.stream().filter(t -> t.getName().equalsIgnoreCase(name)).findAny();
	}

	@SuppressWarnings("unchecked")
	public static <T extends StaticShipType> Optional<T> getType(Class<T> type) {
		return (Optional<T>) TYPES.stream().filter(t -> type.isInstance(t)).findAny();
	}

}

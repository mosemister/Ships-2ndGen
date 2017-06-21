package MoseShipsSponge.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import MoseShipsSponge.Algorthum.Movement.MovementAlgorithm;
import MoseShipsSponge.Configs.ShipsConfig;

public class MovementAlgorithmUtil {

	public static MovementAlgorithm getConfig() {
		String name = ShipsConfig.CONFIG.get(String.class, ShipsConfig.PATH_ALGORITHMS_MOVEMENT);
		Optional<MovementAlgorithm> opMove = get(name);
		if (opMove.isPresent()) {
			return opMove.get();
		}
		return MovementAlgorithm.SHIPS5;
	}

	public static List<MovementAlgorithm> get() {
		List<MovementAlgorithm> list = new ArrayList<>(MovementAlgorithm.MOVEMENT_ALGORITHMS);
		list.add(MovementAlgorithm.SHIPS5);
		//list.add(MovementAlgorithm.SHIPS6);
		return list;
	}

	public static Optional<MovementAlgorithm> get(String name) {
		return get().stream().filter(m -> m.getName().equalsIgnoreCase(name)).findAny();
	}

}

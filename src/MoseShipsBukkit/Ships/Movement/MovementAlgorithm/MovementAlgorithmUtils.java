package MoseShipsBukkit.Ships.Movement.MovementAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import MoseShipsBukkit.Configs.Files.ShipsConfig;

public class MovementAlgorithmUtils {

	public static MovementAlgorithm getConfig() {
		String name = ShipsConfig.CONFIG.get(String.class, ShipsConfig.PATH_ALGORITHMS_MOVEMENT);
		Optional<MovementAlgorithm> opMove = get(name);
		if (opMove.isPresent()) {
			return opMove.get();
		} else {
			// DEFAULTS TO SHIPS 5 AT THE MOMENT, WILL DEFAULT TO SHIPS 6 LATER
			return MovementAlgorithm.SHIPS5;
		}
	}

	public static List<MovementAlgorithm> get() {
		List<MovementAlgorithm> list = new ArrayList<MovementAlgorithm>(MovementAlgorithm.MOVEMENT_ALGORITHMS);
		list.add(MovementAlgorithm.SHIPS5);
		list.add(MovementAlgorithm.SHIPS6);
		return list;
	}

	public static Optional<MovementAlgorithm> get(String name) {
		for (MovementAlgorithm alg : get()) {
			if (alg.getName().equalsIgnoreCase(name)) {
				return Optional.of(alg);
			}
		}
		return Optional.empty();
	}

}

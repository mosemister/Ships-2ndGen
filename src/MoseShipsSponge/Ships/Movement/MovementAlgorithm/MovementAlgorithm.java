package MoseShipsSponge.Ships.Movement.MovementAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import MoseShipsSponge.Configs.Files.ShipsConfig;
import MoseShipsSponge.Ships.Movement.MovementAlgorithm.Types.Ships5Movement;
import MoseShipsSponge.Ships.Movement.MovementAlgorithm.Types.Ships6Movement;
import MoseShipsSponge.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsSponge.Ships.VesselTypes.ShipType;

public interface MovementAlgorithm {
	
	static List<MovementAlgorithm> MOVEMENT_ALGORITHMS = new ArrayList<>();
	
	public static Ships5Movement SHIPS5 = new Ships5Movement();
	public static MovementAlgorithm SHIPS6 = new Ships6Movement();
	
	public void move(ShipType vessel, List<MovingBlock> blocks);
	public String getName();
	
	public static MovementAlgorithm getConfig(){
		String name = ShipsConfig.CONFIG.get(String.class, ShipsConfig.PATH_ALGORITHMS_MOVEMENT);
		Optional<MovementAlgorithm> opMove = get(name);
		if(opMove.isPresent()){
			return opMove.get();
		}else {
			//DEFAULTS TO SHIPS 5 AT THE MOMENT, WILL DEFAULT TO SHIPS 6 LATER
			return SHIPS5;
		}
	}
	
	public static List<MovementAlgorithm> get(){
		List<MovementAlgorithm> list = new ArrayList<>(MOVEMENT_ALGORITHMS);
		list.add(SHIPS5);
		list.add(SHIPS6);
		return list;
	}
	
	public static Optional<MovementAlgorithm> get(String name){
		return get().stream().filter(m -> m.getName().equalsIgnoreCase(name)).findFirst();
	}

}

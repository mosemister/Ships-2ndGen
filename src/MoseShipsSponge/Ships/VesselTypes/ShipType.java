package MoseShipsSponge.Ships.VesselTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Causes.FailedCause;
import MoseShipsSponge.Ships.ShipsData;
import MoseShipsSponge.Ships.Movement.MovingBlock;
import MoseShipsSponge.Ships.VesselTypes.DataTypes.LiveData;

public abstract class ShipType extends ShipsData{

	public abstract Optional<FailedCause> hasRequirements(List<MovingBlock> blocks, @Nullable User user);
	public abstract boolean shouldFall(); 
	public abstract int getMaxBlocks();
	public abstract int getMinBlocks();
	
	static List<ShipType> SHIPS = new ArrayList<>();
	
	public ShipType(String name, User host, Location<World> sign, Location<World> teleport) {
		super(name, host, sign, teleport);
	}
	
	public static Optional<ShipType> getShip(String name){
		return SHIPS.stream().filter(s -> s.getName().equals(name)).findFirst();
	}
	
	public static List<ShipType> getShips(){
		return SHIPS;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends ShipType> List<T> getShips(Class<T> type){
		return (List<T>)SHIPS.stream().filter(s -> type.isInstance(s)).collect(Collectors.toList());
	}
	
	public static List<ShipType> getShipsByRequirements(Class<? extends LiveData> type){
		return SHIPS.stream().filter(s -> type.isInstance(s)).collect(Collectors.toList());
	}
	

}

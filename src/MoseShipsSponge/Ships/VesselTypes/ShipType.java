package MoseShipsSponge.Ships.VesselTypes;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Causes.FailedCause;
import MoseShipsSponge.Ships.ShipsData;
import MoseShipsSponge.Ships.Movement.MovingBlock;

public abstract class ShipType extends ShipsData{

	public abstract Optional<FailedCause> hasRequirements(List<MovingBlock> blocks, @Nullable User user);
	public abstract boolean shouldFall(); 
	
	public ShipType(String name, User host, Location<World> sign, Location<World> teleport) {
		super(name, host, sign, teleport);
	}

}

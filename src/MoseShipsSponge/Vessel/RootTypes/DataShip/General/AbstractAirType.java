package MoseShipsSponge.Vessel.RootTypes.DataShip.General;

import java.util.Optional;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

import MoseShipsSponge.Movement.Movement;
import MoseShipsSponge.Movement.Result.FailedMovement;
import MoseShipsSponge.Movement.StoredMovement.StoredMovement;
import MoseShipsSponge.Vessel.Common.GeneralTypes.AirType;
import MoseShipsSponge.Vessel.Common.RootTypes.ShipsData;
import MoseShipsSponge.Vessel.RootTypes.DataShip.DataVessel;

public abstract class AbstractAirType extends DataVessel implements AirType {

	public AbstractAirType(String name, Location<World> sign, Vector3i teleport) {
		super(name, sign, teleport);
	}

	public AbstractAirType(ShipsData data) {
		super(data);
	}

	@Override
	public Optional<FailedMovement> teleport(StoredMovement move) {
		return Movement.teleport(this, move, BlockTypes.AIR.getDefaultState());
	}
}

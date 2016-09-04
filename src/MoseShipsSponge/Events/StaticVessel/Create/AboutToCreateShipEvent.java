package MoseShipsSponge.Events.StaticVessel.Create;

import java.util.Optional;

import org.spongepowered.api.data.manipulator.immutable.tileentity.ImmutableSignData;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Events.StaticVessel.StaticShipEvent;
import MoseShipsSponge.Ships.VesselTypes.Satic.StaticShipType;

public class AboutToCreateShipEvent<S extends StaticShipType> extends StaticShipEvent<S> implements Cancellable {

	Location<World> SOURCE;
	SignData DATA;
	ImmutableSignData ORIGINAL_DATA;
	boolean CANCELLED = false;

	public AboutToCreateShipEvent(S type, Location<World> block, Cause cause) {
		super(type, cause);
		SOURCE = block;
	}

	public AboutToCreateShipEvent(S type, Location<World> block, SignData data, ImmutableSignData oriData, Cause cause) {
		super(type, cause);
		SOURCE = block;
		DATA = data;
		ORIGINAL_DATA = oriData;
	}

	public Location<World> getSourceBlock() {
		return SOURCE;
	}

	public Optional<SignData> getSignData() {
		return Optional.ofNullable(DATA);
	}

	public Optional<ImmutableSignData> getOriginalSignData() {
		return Optional.ofNullable(ORIGINAL_DATA);
	}

	@Override
	public boolean isCancelled() {
		return CANCELLED;
	}

	@Override
	public void setCancelled(boolean check) {
		CANCELLED = check;
	}

}

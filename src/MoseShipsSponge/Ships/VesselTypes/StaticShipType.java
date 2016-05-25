package MoseShipsSponge.Ships.VesselTypes;

import java.util.Optional;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Ships.ShipsData;

public interface StaticShipType {
	
	public int getDefaultSpeed();
	public int getBoostSpeed();
	public int getAltitudeSpeed();
	public boolean autoPilot();
	public Optional<? extends ShipsData> createVessel(String name, Location<World> world, Player owner);
	public Optional<? extends ShipsData> loadVessel(ShipsData data);
	

}

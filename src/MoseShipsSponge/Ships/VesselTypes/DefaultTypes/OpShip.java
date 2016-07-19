package MoseShipsSponge.Ships.VesselTypes.DefaultTypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Causes.FailedCause;
import MoseShipsSponge.Ships.ShipsData;
import MoseShipsSponge.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsSponge.Ships.VesselTypes.ShipType;
import MoseShipsSponge.Ships.VesselTypes.StaticShipType;

public class OpShip extends ShipType{

	public OpShip(String name, Location<World> sign, Location<World> teleport) {
		super(name, sign, teleport);
	}
	
	public OpShip(ShipsData ship){
		super(ship);
	}

	@Override
	public Optional<FailedCause> hasRequirements(List<MovingBlock> blocks, User user) {
		return Optional.empty();
	}

	@Override
	public boolean shouldFall() {
		return false;
	}

	@Override
	public int getMaxBlocks() {
		return 300;
	}

	@Override
	public int getMinBlocks() {
		return 0;
	}

	@Override
	public Map<Text, Object> getInfo() {
		return new HashMap<Text, Object>();
	}
	
	@Override
	public StaticShipType getStatic() {
		return StaticShipType.getType(StaticOPShip.class).get();
	}
	
	public static class StaticOPShip implements StaticShipType{

		public StaticOPShip(){
			StaticShipType.inject(this);
		}
		
		@Override
		public String getName(){
			return "OPShip";
		}
		
		@Override
		public int getDefaultSpeed() {
			return 2;
		}

		@Override
		public int getBoostSpeed() {
			return 2;
		}

		@Override
		public int getAltitudeSpeed() {
			return 2;
		}

		@Override
		public boolean autoPilot() {
			return true;
		}

		@Override
		public Optional<ShipType> createVessel(String name, Location<World> sign) {
			return Optional.of(new OpShip(name, sign, sign));
		}

		@Override
		public Optional<ShipType> loadVessel(ShipsData data) {
			return Optional.of(new OpShip(data));
		}
		
	}
}

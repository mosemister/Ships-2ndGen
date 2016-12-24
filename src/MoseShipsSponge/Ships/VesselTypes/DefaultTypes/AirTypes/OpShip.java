package MoseShipsSponge.Ships.VesselTypes.DefaultTypes.AirTypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Causes.MovementResult;
import MoseShipsSponge.Configs.BasicConfig;
import MoseShipsSponge.Ships.ShipsData;
import MoseShipsSponge.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsSponge.Ships.VesselTypes.LoadableShip;
import MoseShipsSponge.Ships.VesselTypes.StaticShipType;
import MoseShipsSponge.Ships.VesselTypes.DefaultTypes.AirTypes.MainTypes.AbstractAirType;
import MoseShipsSponge.Ships.VesselTypes.Loading.ShipsLocalDatabase;

public class OpShip extends AbstractAirType {

	public OpShip(String name, Location<World> sign, Location<World> teleport) {
		super(name, sign, teleport);
	}

	public OpShip(ShipsData ship) {
		super(ship);
	}

	@Override
	public Optional<MovementResult> hasRequirements(List<MovingBlock> blocks, Cause cause) {
		return Optional.empty();
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
		HashMap<Text, Object> map = new HashMap<>();
		if (USER == null) {
			map.put(Text.of("Owner"), "None");
		} else {
			map.put(Text.of("Owner"), USER.getName());
		}
		map.put(Text.of("size"), updateBasicStructure().size());
		map.put(Text.of("type"), "OPShip");
		map.put(Text.of("is loaded"), this.isLoaded());
		map.put(Text.of("is moving"), this.isMoving());
		return map;
	}

	@Override
	public void onSave(ShipsLocalDatabase database) {}

	@Override
	public void onRemove(Player player) {}

	@Override
	public StaticShipType getStatic() {
		return StaticShipType.getType(StaticOPShip.class).get();
	}

	public static class StaticOPShip implements StaticShipType {

		public StaticOPShip() {
			StaticShipType.inject(this);
		}

		@Override
		public String getName() {
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
		public Optional<LoadableShip> createVessel(String name, Location<World> sign) {
			return Optional.of(new OpShip(name, sign, sign));
		}

		@Override
		public Optional<LoadableShip> loadVessel(ShipsData data, BasicConfig config) {
			return Optional.of(new OpShip(data));
		}

	}
}

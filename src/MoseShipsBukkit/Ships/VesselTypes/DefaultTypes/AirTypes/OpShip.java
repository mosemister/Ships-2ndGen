package MoseShipsBukkit.Ships.VesselTypes.DefaultTypes.AirTypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.block.Block;

import MoseShipsBukkit.Causes.MovementResult;
import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Ships.ShipsData;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Ships.VesselTypes.DefaultTypes.AirType;
import MoseShipsBukkit.Ships.VesselTypes.Satic.StaticShipType;
import MoseShipsBukkit.Ships.VesselTypes.Satic.StaticShipTypeUtil;

public class OpShip extends AirType {

	public OpShip(String name, Block sign, Location teleport) {
		super(name, sign, teleport);
	}

	public OpShip(ShipsData ship) {
		super(ship);
	}

	@Override
	public Optional<MovementResult> hasRequirements(List<MovingBlock> blocks) {
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
	public Map<String, Object> getInfo() {
		return new HashMap<String, Object>();
	}

	@Override
	public StaticShipType getStatic() {
		return StaticShipTypeUtil.getType(StaticOPShip.class).get();
	}

	public static class StaticOPShip implements StaticShipType {

		public StaticOPShip() {
			StaticShipTypeUtil.inject(this);
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
		public Optional<LoadableShip> createVessel(String name, Block sign) {
			return Optional.of((LoadableShip)new OpShip(name, sign, sign.getLocation()));
		}

		@Override
		public Optional<LoadableShip> loadVessel(ShipsData data, BasicConfig config) {
			return Optional.of((LoadableShip)new OpShip(data));
		}

	}
}

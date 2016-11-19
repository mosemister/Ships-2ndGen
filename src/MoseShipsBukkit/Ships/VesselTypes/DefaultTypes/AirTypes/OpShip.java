package MoseShipsBukkit.Ships.VesselTypes.DefaultTypes.AirTypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Causes.MovementResult;
import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Ships.ShipsData;
import MoseShipsBukkit.Ships.Movement.AutoPilot.AutoPilot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.Live.LiveAutoPilotable;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.Live.LiveFallable;
import MoseShipsBukkit.Ships.VesselTypes.DefaultTypes.AirTypes.MainTypes.AbstractAirType;
import MoseShipsBukkit.Ships.VesselTypes.Loading.ShipsLocalDatabase;
import MoseShipsBukkit.Ships.VesselTypes.Satic.StaticShipType;
import MoseShipsBukkit.Ships.VesselTypes.Satic.StaticShipTypeUtil;

public class OpShip extends AbstractAirType implements LiveAutoPilotable, LiveFallable {

	AutoPilot g_auto_pilot;
	
	public OpShip(String name, Block sign, Location teleport) {
		super(name, sign, teleport);
	}

	public OpShip(ShipsData ship) {
		super(ship);
	}
	
	@Override
	public Optional<AutoPilot> getAutoPilotData() {
		return Optional.ofNullable(g_auto_pilot);
	}
	
	@Override
	public OpShip setAutoPilotData(AutoPilot pilot){
		g_auto_pilot = pilot;
		return this;
	}

	@Override
	public void onRemove(Player player) {
	}
	
	@Override
	public void onRepeate() {
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
	public void onSave(ShipsLocalDatabase database) {
	}

	@Override
	public int getMinBlocks() {
		return 0;
	}

	@Override
	public Map<String, Object> getInfo() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (g_user == null) {
			map.put("Owner", "None");
		} else {
			map.put("Owner", g_user.getName());
		}
		map.put("size", updateBasicStructure().size());
		map.put("type", "OPShip");
		map.put("is loaded", this.isLoaded());
		map.put("is moving", this.isMoving());
		return map;
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
			return Optional.of((LoadableShip) new OpShip(name, sign, sign.getLocation()));
		}

		@Override
		public Optional<LoadableShip> loadVessel(ShipsData data, BasicConfig config) {
			return Optional.of((LoadableShip) new OpShip(data));
		}

	}
}

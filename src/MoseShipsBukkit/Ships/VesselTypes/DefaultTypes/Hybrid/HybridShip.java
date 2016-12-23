package MoseShipsBukkit.Ships.VesselTypes.DefaultTypes.Hybrid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import MoseShipsBukkit.ShipsMain;
import MoseShipsBukkit.Causes.MovementResult;
import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Ships.ShipsData;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveData;
import MoseShipsBukkit.Ships.VesselTypes.DefaultTypes.AirTypes.MainTypes.AirType;
import MoseShipsBukkit.Ships.VesselTypes.DefaultTypes.WaterTypes.MainTypes.AbstractWaterType;
import MoseShipsBukkit.Ships.VesselTypes.Loading.ShipsLocalDatabase;
import MoseShipsBukkit.Ships.VesselTypes.Satic.StaticShipType;
import MoseShipsBukkit.Ships.VesselTypes.Satic.StaticShipTypeUtil;

public class HybridShip extends AbstractWaterType implements AirType{

	public HybridShip(String name, Block sign, Location teleport) {
		super(name, sign, teleport);
	}

	public HybridShip(ShipsData data) {
		super(data);
	}

	@Override
	public Optional<MovementResult> hasRequirements(List<MovingBlock> blocks) {
		return Optional.empty();
	}

	@Override
	public Map<String, Object> getInfo() {
		return new HashMap<String, Object>();
	}

	@Override
	public void onSave(ShipsLocalDatabase database) {
	}

	@Override
	public void onRemove(Player player) {
	}

	@Override
	public StaticShipType getStatic() {
		return StaticShipTypeUtil.getType(StaticHybridShip.class).get();
	}
	
	public static class StaticHybridShip implements StaticShipType{

		public StaticHybridShip(){
			StaticShipTypeUtil.inject(this);
		}
		
		@Override
		public String getName() {
			return "HybridShip";
		}

		@Override
		public int getDefaultSpeed() {
			return 2;
		}

		@Override
		public int getBoostSpeed() {
			return 3;
		}

		@Override
		public int getAltitudeSpeed() {
			return 2;
		}

		@Override
		public boolean autoPilot() {
			return false;
		}
		
		@Override
		public ShipsMain getPlugin(){
			return ShipsMain.getPlugin();
		}

		@Override
		public Optional<LiveData> createVessel(String name, Block licence) {
			LoadableShip ship = new HybridShip(name, licence, licence.getLocation());
			return Optional.of(ship);
		}

		@Override
		public Optional<LiveData> loadVessel(ShipsData data, BasicConfig config) {
			LoadableShip ship = new HybridShip(data);
			return Optional.of(ship);
		}
		
	}
}
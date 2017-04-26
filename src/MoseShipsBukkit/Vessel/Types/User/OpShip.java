package MoseShipsBukkit.Vessel.Types.User;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Movement.MovingBlock;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Movement.StoredMovement.AutoPilot;
import MoseShipsBukkit.Plugin.ShipsMain;
import MoseShipsBukkit.Utils.StaticShipTypeUtil;
import MoseShipsBukkit.Vessel.Data.AbstractShipsData;
import MoseShipsBukkit.Vessel.Data.LiveShip;
import MoseShipsBukkit.Vessel.Data.LoadableShip;
import MoseShipsBukkit.Vessel.Data.ShipsData;
import MoseShipsBukkit.Vessel.DataProcessors.Live.LiveAutoPilotable;
import MoseShipsBukkit.Vessel.DataProcessors.Live.LiveFallable;
import MoseShipsBukkit.Vessel.OpenLoader.Loader;
import MoseShipsBukkit.Vessel.OpenLoader.OpenLoader;
import MoseShipsBukkit.Vessel.OpenLoader.OpenRAWLoader;
import MoseShipsBukkit.Vessel.Static.StaticShipType;
import MoseShipsBukkit.Vessel.Types.AbstractAirType;

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
	public OpShip setAutoPilotData(AutoPilot pilot) {
		g_auto_pilot = pilot;
		return this;
	}

	@Override
	public void onRemove(Player player) {
	}

	@Override
	public Optional<FailedMovement> hasRequirements(List<MovingBlock> blocks) {
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
		public ShipsMain getPlugin() {
			return ShipsMain.getPlugin();
		}

		@Override
		public Optional<LiveShip> createVessel(String name, Block sign) {
			return Optional.of((LiveShip) new OpShip(name, sign, sign.getLocation()));
		}

		@Override
		public Optional<LiveShip> loadVessel(AbstractShipsData data, BasicConfig config) {
			return Optional.of((LiveShip) new OpShip(data));
		}

		@Override
		public OpenRAWLoader[] getLoaders() {
			OpenLoader ship6Loader = new OpenLoader(){

				@Override
				public String getLoaderName() {
					return "Ships 6 - OPShip";
				}

				@Override
				public int[] getLoaderVersion() {
					int[] values = {0, 0, 0, 1};
					return values;
				}

				@Override
				public boolean willLoad(File file) {
					BasicConfig config = new BasicConfig(file);
					String type = config.get(String.class, Loader.OPEN_LOADER_NAME);
					if(type == null){
						return false;
					}
					if(type.equals(getLoaderName())){
						return true;
					}
					return false;
				}

				@Override
				public Optional<LiveShip> load(ShipsData data) {
					LoadableShip ship = new OpShip(data);
					return Optional.of((LiveShip) ship);
				}

				@Override
				public OpenLoader save(LiveShip ship, BasicConfig config) {
					Loader.saveLoader(config, this);
					return this;
				}
				
			};
			
			OpenRAWLoader[] loaders = {ship6Loader};
			return loaders;
		}

	}
}

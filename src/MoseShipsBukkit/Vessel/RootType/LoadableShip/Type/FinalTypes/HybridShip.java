package MoseShipsBukkit.Vessel.RootType.LoadableShip.Type.FinalTypes;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Plugin.ShipsMain;
import MoseShipsBukkit.Utils.StaticShipTypeUtil;
import MoseShipsBukkit.Utils.Lists.MovingBlockList;
import MoseShipsBukkit.Vessel.Common.OpenLoader.Loader;
import MoseShipsBukkit.Vessel.Common.OpenLoader.OpenLoader;
import MoseShipsBukkit.Vessel.Common.OpenLoader.OpenRAWLoader;
import MoseShipsBukkit.Vessel.Common.RootTypes.AbstractShipsData;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.Common.RootTypes.ShipsData;
import MoseShipsBukkit.Vessel.Common.Static.StaticShipType;
import MoseShipsBukkit.Vessel.RootType.LoadableShip.LoadableShip;
import MoseShipsBukkit.Vessel.RootType.LoadableShip.Type.AbstractWaterType;
import MoseShipsBukkit.Vessel.RootType.LoadableShip.Type.AirType;

public class HybridShip extends AbstractWaterType implements AirType {

	public HybridShip(String name, Block sign, Location teleport) {
		super(name, sign, teleport);
	}

	public HybridShip(ShipsData data) {
		super(data);
	}

	@Override
	public Optional<FailedMovement> hasRequirements(MovingBlockList blocks) {
		return Optional.empty();
	}

	@Override
	public Map<String, Object> getInfo() {
		return new HashMap<String, Object>();
	}

	@Override
	public void onRemove(Player player) {
	}

	@Override
	public StaticShipType getStatic() {
		return StaticShipTypeUtil.getType(StaticHybridShip.class).get();
	}

	public static class StaticHybridShip implements StaticShipType {

		public StaticHybridShip() {
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
		public ShipsMain getPlugin() {
			return ShipsMain.getPlugin();
		}

		@Override
		public Optional<LiveShip> createVessel(String name, Block licence) {
			LoadableShip ship = new HybridShip(name, licence, licence.getLocation());
			return Optional.of((LiveShip) ship);
		}

		@Override
		public Optional<LiveShip> loadVessel(AbstractShipsData data, BasicConfig config) {
			LoadableShip ship = new HybridShip(data);
			return Optional.of((LiveShip) ship);
		}

		@Override
		public OpenRAWLoader[] getLoaders() {
			OpenLoader ship6Loader = new OpenLoader() {

				@Override
				public String getLoaderName() {
					return "Ships 6 - HybindShip";
				}

				@Override
				public int[] getLoaderVersion() {
					int[] values = {
							0,
							0,
							0,
							1 };
					return values;
				}

				@Override
				public boolean willLoad(File file) {
					BasicConfig config = new BasicConfig(file);
					String type = config.get(String.class, Loader.OPEN_LOADER_NAME);
					if (type == null) {
						return false;
					}
					if (type.equals(getLoaderName())) {
						return true;
					}
					return false;
				}

				@Override
				public Optional<LiveShip> load(ShipsData data) {
					LoadableShip ship = new HybridShip(data);
					return Optional.of((LiveShip) ship);
				}

				@Override
				public OpenLoader save(LiveShip ship, BasicConfig config) {
					return this;
				}

			};

			OpenRAWLoader[] loaders = {
					ship6Loader };
			return loaders;
		}

	}
}
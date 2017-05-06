package MoseShipsBukkit.Vessel.RootType.LoadableShip.Type.FinalTypes;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import MoseShips.Stores.TwoStore;
import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Configs.StaticShipConfig;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Movement.Result.MovementResult;
import MoseShipsBukkit.Plugin.ShipsMain;
import MoseShipsBukkit.ShipBlock.BlockState;
import MoseShipsBukkit.Utils.StaticShipTypeUtil;
import MoseShipsBukkit.Utils.Lists.MovingBlockList;
import MoseShipsBukkit.Vessel.Common.OpenLoader.Loader;
import MoseShipsBukkit.Vessel.Common.OpenLoader.OpenLoader;
import MoseShipsBukkit.Vessel.Common.OpenLoader.OpenRAWLoader;
import MoseShipsBukkit.Vessel.Common.OpenLoader.Ships5Loader;
import MoseShipsBukkit.Vessel.Common.RootTypes.AbstractShipsData;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.Common.RootTypes.ShipsData;
import MoseShipsBukkit.Vessel.Common.Static.StaticShipType;
import MoseShipsBukkit.Vessel.DataProcessors.Live.LiveRequiredPercent;
import MoseShipsBukkit.Vessel.DataProcessors.Static.StaticRequiredPercent;
import MoseShipsBukkit.Vessel.RootType.LoadableShip.LoadableShip;
import MoseShipsBukkit.Vessel.RootType.LoadableShip.Type.AbstractAirType;

public class Marsship extends AbstractAirType implements LiveRequiredPercent {

	int g_req_percent;
	BlockState[] g_req_p_blocks;

	public Marsship(String name, Block sign, Location teleport) {
		super(name, sign, teleport);
	}

	public Marsship(ShipsData data) {
		super(data);
	}

	@Override
	public Optional<FailedMovement> hasRequirements(MovingBlockList blocks) {
		if (blocks.size() > g_max_blocks) {
			return Optional.of(new FailedMovement(this, MovementResult.TOO_MANY_BLOCKS, blocks.size()));
		}
		if (blocks.size() < g_min_blocks) {
			return Optional.of(new FailedMovement(this, MovementResult.NOT_ENOUGH_BLOCKS, blocks.size()));
		}
		int percent = getAmountOfPercentBlocks();
		if (percent > g_req_percent) {
			return Optional.empty();
		}
		return Optional.of(new FailedMovement(this, MovementResult.NOT_ENOUGH_PERCENT,
				new TwoStore<BlockState, Float>(getPercentBlocks()[0], (float) percent)));
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
		return StaticShipTypeUtil.getType(StaticMarsship.class).get();
	}

	@Override
	public int getRequiredPercent() {
		return g_req_percent;
	}

	@Override
	public int getAmountOfPercentBlocks() {
		List<Block> structure = getBasicStructure();
		if (!structure.isEmpty()) {
			List<Block> blocks = new ArrayList<Block>();
			for (Block block : structure) {
				if (BlockState.contains(block, getPercentBlocks())) {
					blocks.add(block);
				}
			}
			int percent = (int) (blocks.size() * 100.0f) / structure.size();
			return percent;
		}
		return 0;
	}

	@Override
	public BlockState[] getPercentBlocks() {
		return g_req_p_blocks;
	}

	public static class StaticMarsship implements StaticShipType, StaticRequiredPercent {

		public StaticMarsship() {
			StaticShipTypeUtil.inject(this);
			File file = new File(StaticShipTypeUtil.DEFAULT_SHIP_TYPE_LOCATION, "Marsship.yml");
			if (!file.exists()) {
				StaticShipConfig config = new StaticShipConfig(file);
				config.setOverride(2, StaticShipConfig.DATABASE_DEFAULT_ALTITUDE);
				config.setOverride(3, StaticShipConfig.DATABASE_DEFAULT_BOOST);
				config.setOverride(4000, StaticShipConfig.DATABASE_DEFAULT_MAX_SIZE);
				config.setOverride(0, StaticShipConfig.DATABASE_DEFAULT_MIN_SIZE);
				config.setOverride(2, StaticShipConfig.DATABASE_DEFAULT_SPEED);
				config.setOverride(
						Arrays.asList(new BlockState(Material.DAYLIGHT_DETECTOR).toNoString(),
								new BlockState(Material.DAYLIGHT_DETECTOR_INVERTED).toNoString()),
						StaticRequiredPercent.DEFAULT_REQUIRED_BLOCKS);
				config.setOverride(20, StaticRequiredPercent.DEFAULT_REQUIRED_PERCENT);
				config.save();
			}
		}

		@Override
		public String getName() {
			return "Marsship";
		}

		@Override
		public int getDefaultSpeed() {
			File file = new File(StaticShipTypeUtil.DEFAULT_SHIP_TYPE_LOCATION, "Marsship.yml");
			StaticShipConfig config = new StaticShipConfig(file);
			int speed = config.getDefaultSpeed();
			return speed;
		}

		@Override
		public int getBoostSpeed() {
			File file = new File(StaticShipTypeUtil.DEFAULT_SHIP_TYPE_LOCATION, "Marsship.yml");
			StaticShipConfig config = new StaticShipConfig(file);
			int speed = config.getDefaultBoostSpeed();
			return speed;
		}

		@Override
		public int getAltitudeSpeed() {
			File file = new File(StaticShipTypeUtil.DEFAULT_SHIP_TYPE_LOCATION, "Marsship.yml");
			StaticShipConfig config = new StaticShipConfig(file);
			int speed = config.getDefaultAltitudeSpeed();
			return speed;
		}

		@Override
		public boolean autoPilot() {
			return false;
		}

		@Override
		public Plugin getPlugin() {
			return ShipsMain.getPlugin();
		}

		@Override
		public Optional<LiveShip> createVessel(String name, Block licence) {
			Marsship ship = new Marsship(name, licence, licence.getLocation());
			ship.g_req_p_blocks = getDefaultPercentBlocks();
			ship.g_req_percent = getDefaultRequiredPercent();
			return Optional.of((LiveShip) ship);
		}

		@Override
		public Optional<LiveShip> loadVessel(AbstractShipsData data, BasicConfig config) {
			Marsship ship = new Marsship(data);
			List<String> sStates = config.getList(String.class, LiveRequiredPercent.REQUIRED_BLOCKS);
			BlockState[] states = BlockState.getStates(sStates);
			int percent = config.get(Integer.class, LiveRequiredPercent.REQUIRED_PERCENT);

			ship.g_req_p_blocks = states;
			ship.g_req_percent = percent;
			return Optional.of((LiveShip) ship);
		}

		@Override
		public int getDefaultRequiredPercent() {
			File file = new File(StaticShipTypeUtil.DEFAULT_SHIP_TYPE_LOCATION, "Marsship.yml");
			StaticShipConfig config = new StaticShipConfig(file);
			int percent = config.get(Integer.class, StaticRequiredPercent.DEFAULT_REQUIRED_PERCENT);
			return percent;
		}

		@Override
		public BlockState[] getDefaultPercentBlocks() {
			File file = new File(StaticShipTypeUtil.DEFAULT_SHIP_TYPE_LOCATION, "Marsship.yml");
			StaticShipConfig config = new StaticShipConfig(file);
			List<String> sStates = config.getList(String.class, StaticRequiredPercent.DEFAULT_REQUIRED_BLOCKS);
			return BlockState.getStates(sStates);
		}

		@Override
		public OpenRAWLoader[] getLoaders() {
			OpenLoader ship6Loader = new OpenLoader() {

				@Override
				public String getLoaderName() {
					return "Ships 6 - Marsship";
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
					LoadableShip ship = new Marsship(data);
					return Optional.of((LiveShip) ship);
				}

				@Override
				public OpenLoader save(LiveShip ship, BasicConfig config) {
					Marsship ship2 = (Marsship) ship;
					List<String> reqBlocks = new ArrayList<String>();
					for (BlockState state : ship2.g_req_p_blocks) {
						reqBlocks.add(state.toNoString());
					}
					config.set(reqBlocks, LiveRequiredPercent.REQUIRED_BLOCKS);
					config.set(ship2.g_req_percent, LiveRequiredPercent.REQUIRED_PERCENT);
					return this;
				}

			};
			Ships5Loader ship5Loader = new Ships5Loader() {

				@Override
				public String getLoaderName() {
					return "Ships 5 Parser - Marsship";
				}

				@Override
				public int[] getLoaderVersion() {
					int[] version = {
							0,
							0,
							0,
							1 };
					return version;
				}

				@Override
				public boolean willLoad(File file) {
					BasicConfig config = new BasicConfig(file);
					String type = config.get(String.class, "ShipsData.Type");
					if ((type != null) && (type.equals(getName()))) {
						return true;
					}
					return false;
				}

				@Override
				public Optional<LiveShip> RAWLoad(File file) {
					BasicConfig config = new BasicConfig(file);
					Optional<LiveShip> opShip = super.RAWLoad(file);
					if (!opShip.isPresent()) {
						return opShip;
					}
					Marsship ship = (Marsship) opShip.get();
					int percent = config.get(Integer.class, "ShipsData.Config.Block.Percent");
					ship.g_req_percent = percent;
					return opShip;
				}

			};
			OpenRAWLoader[] loaders = {
					ship6Loader,
					ship5Loader };
			return loaders;
		}

	}

}

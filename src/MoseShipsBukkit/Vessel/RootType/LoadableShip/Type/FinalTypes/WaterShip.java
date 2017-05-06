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

import MoseShips.Stores.TwoStore;
import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Configs.StaticShipConfig;
import MoseShipsBukkit.Movement.MovingBlock;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Movement.Result.MovementResult;
import MoseShipsBukkit.Plugin.ShipsMain;
import MoseShipsBukkit.ShipBlock.BlockState;
import MoseShipsBukkit.Utils.StaticShipTypeUtil;
import MoseShipsBukkit.Utils.Lists.MovingBlockList;
import MoseShipsBukkit.Vessel.Common.OpenLoader.Loader;
import MoseShipsBukkit.Vessel.Common.OpenLoader.OpenLoader;
import MoseShipsBukkit.Vessel.Common.OpenLoader.OpenRAWLoader;
import MoseShipsBukkit.Vessel.Common.RootTypes.AbstractShipsData;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.Common.RootTypes.ShipsData;
import MoseShipsBukkit.Vessel.Common.Static.StaticShipType;
import MoseShipsBukkit.Vessel.DataProcessors.Live.LiveLockedAltitude;
import MoseShipsBukkit.Vessel.DataProcessors.Live.LiveRequiredPercent;
import MoseShipsBukkit.Vessel.DataProcessors.Static.StaticRequiredPercent;
import MoseShipsBukkit.Vessel.RootType.LoadableShip.LoadableShip;
import MoseShipsBukkit.Vessel.RootType.LoadableShip.Type.AbstractWaterType;

public class WaterShip extends AbstractWaterType implements LiveRequiredPercent, LiveLockedAltitude {

	int g_block_percent = getStatic().getDefaultRequiredPercent();
	BlockState[] g_materials = getStatic().getDefaultPercentBlocks();

	public WaterShip(ShipsData data) {
		super(data);
	}

	public WaterShip(String name, Block sign, Location teleport) {
		super(name, sign, teleport);
	}

	@Override
	public void onRemove(Player player) {
	}

	public WaterShip setRequiredPercent(int A) {
		g_block_percent = A;
		return this;
	}

	public WaterShip setPercentBlocks(BlockState... blocks) {
		g_materials = blocks;
		return this;
	}

	@Override
	public int getRequiredPercent() {
		return g_block_percent;
	}

	@Override
	public int getAmountOfPercentBlocks() {
		List<Block> structure = getBasicStructure();
		if (structure.isEmpty()) {
			return this.g_block_percent;
		}
		List<Block> blocks = new ArrayList<Block>();
		for (Block block : structure) {
			if (BlockState.contains(block, getPercentBlocks())) {
				blocks.add(block);
			}
		}
		int percent = (blocks.size() / structure.size()) * 100;
		return percent;
	}

	@Override
	public BlockState[] getPercentBlocks() {
		return g_materials;
	}

	@Override
	public Optional<FailedMovement> hasRequirements(MovingBlockList blocks) {
		int waterLevel = getWaterLevel();
		switch (waterLevel) {
		case -1:
			return Optional.of(new FailedMovement(this, MovementResult.NOT_IN_WATER_ERROR, true));
		default:
			int blockCount = 0;
			for (MovingBlock block : blocks) {
				if (BlockState.contains(block.getOrigin().getBlock(), g_materials)) {
					blockCount++;
				}
			}
			float percent = (blockCount * 100) / blocks.size();
			if (percent >= g_block_percent) {
				return Optional.empty();
			} else {
				return Optional.of(new FailedMovement(this, MovementResult.NOT_ENOUGH_PERCENT,
						new TwoStore<BlockState, Float>(g_materials[0], (g_block_percent - percent))));
			}

		}
	}

	@Override
	public int getMaxBlocks() {
		return g_max_blocks;
	}

	@Override
	public int getMinBlocks() {
		return g_min_blocks;
	}

	@Override
	public Map<String, Object> getInfo() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<String> requiredPBlocks = new ArrayList<String>();
		for (BlockState state : this.g_materials) {
			requiredPBlocks.add(state.toString());
		}
		if (g_user == null) {
			map.put("Owner", "None");
		} else {
			map.put("Owner", g_user.getName());
		}
		map.put("Size", updateBasicStructure().size());
		map.put("Type", "WaterShip");
		map.put("Is loaded", this.isLoaded());
		map.put("Needed percent (current/required)", this.getAmountOfPercentBlocks() + "/" + this.g_block_percent);
		map.put("Percent blocks (any)", requiredPBlocks);

		return map;
	}

	@Override
	public StaticWaterShip getStatic() {
		return StaticShipTypeUtil.getType(StaticWaterShip.class).get();
	}

	public static class StaticWaterShip implements StaticShipType, StaticRequiredPercent {

		public StaticWaterShip() {
			StaticShipTypeUtil.inject(this);
			File file = new File("plugins/Ships/Configuration/ShipTypes/WaterShip.yml");
			if (!file.exists()) {
				StaticShipConfig config = new StaticShipConfig(file);
				config.setOverride(2, StaticShipConfig.DATABASE_DEFAULT_ALTITUDE);
				config.setOverride(3, StaticShipConfig.DATABASE_DEFAULT_BOOST);
				config.setOverride(4000, StaticShipConfig.DATABASE_DEFAULT_MAX_SIZE);
				config.setOverride(0, StaticShipConfig.DATABASE_DEFAULT_MIN_SIZE);
				config.setOverride(2, StaticShipConfig.DATABASE_DEFAULT_SPEED);
				config.setOverride(Arrays.asList(new BlockState(Material.WOOL, (byte) -1).toNoString()),
						StaticRequiredPercent.DEFAULT_REQUIRED_BLOCKS);
				config.setOverride(40, StaticRequiredPercent.DEFAULT_REQUIRED_PERCENT);
				config.save();
			}
		}

		@Override
		public String getName() {
			return "Ship";
		}

		@Override
		public int getDefaultSpeed() {
			StaticShipConfig config = new StaticShipConfig("WaterShip");
			return config.getDefaultSpeed();
		}

		@Override
		public int getBoostSpeed() {
			StaticShipConfig config = new StaticShipConfig("WaterShip");
			return config.getDefaultBoostSpeed();
		}

		@Override
		public int getAltitudeSpeed() {
			StaticShipConfig config = new StaticShipConfig("WaterShip");
			return config.getDefaultAltitudeSpeed();
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
		public Optional<LiveShip> createVessel(String name, Block sign) {
			return Optional.of((LiveShip) new WaterShip(name, sign, sign.getLocation()));
		}

		@Override
		public Optional<LiveShip> loadVessel(AbstractShipsData data, BasicConfig config) {
			WaterShip ship = new WaterShip(data);
			int percent = config.get(Integer.class, LiveRequiredPercent.REQUIRED_PERCENT);
			ship.setRequiredPercent(percent);

			List<String> sStates = config.getList(String.class, LiveRequiredPercent.REQUIRED_BLOCKS);
			BlockState[] states = BlockState.getStates(sStates);
			ship.setPercentBlocks(states);

			return Optional.of((LiveShip) ship);
		}

		@Override
		public int getDefaultRequiredPercent() {
			StaticShipConfig config = new StaticShipConfig("WaterShip");
			return config.get(Integer.class, StaticRequiredPercent.DEFAULT_REQUIRED_PERCENT);
		}

		@Override
		public BlockState[] getDefaultPercentBlocks() {
			StaticShipConfig config = new StaticShipConfig("WaterShip");
			List<String> list = config.getList(String.class, StaticRequiredPercent.DEFAULT_REQUIRED_BLOCKS);
			BlockState[] states = BlockState.getStates(list);
			return states;
		}

		@Override
		public OpenRAWLoader[] getLoaders() {
			OpenLoader ship6Loader = new OpenLoader() {

				@Override
				public String getLoaderName() {
					return "Ships 6 - WaterShip";
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
					LoadableShip ship = new WaterShip(data);
					return Optional.of((LiveShip) ship);
				}

				@Override
				public OpenLoader save(LiveShip ship, BasicConfig config) {
					WaterShip ship2 = (WaterShip) ship;
					List<String> list = new ArrayList<String>();
					for (BlockState state : ship2.g_materials) {
						list.add(state.toNoString());
					}
					config.setOverride(ship2.g_block_percent, LiveRequiredPercent.REQUIRED_PERCENT);
					config.setOverride(list, LiveRequiredPercent.REQUIRED_BLOCKS);
					return this;
				}

			};

			OpenRAWLoader[] loaders = {
					ship6Loader };
			return loaders;
		}

	}
}

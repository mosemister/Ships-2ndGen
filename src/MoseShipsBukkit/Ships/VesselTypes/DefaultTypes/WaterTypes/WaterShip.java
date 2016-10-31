package MoseShipsBukkit.Ships.VesselTypes.DefaultTypes.WaterTypes;

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
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import MoseShips.Stores.TwoStore;
import MoseShipsBukkit.Causes.MovementResult;
import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Configs.Files.StaticShipConfig;
import MoseShipsBukkit.Ships.ShipsData;
import MoseShipsBukkit.Ships.Movement.StoredMovement;
import MoseShipsBukkit.Ships.Movement.Movement.Rotate;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.Live.LiveLockedAltitude;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.Live.LiveRequiredPercent;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.Static.StaticRequiredPercent;
import MoseShipsBukkit.Ships.VesselTypes.DefaultTypes.WaterType;
import MoseShipsBukkit.Ships.VesselTypes.Loading.ShipsLocalDatabase;
import MoseShipsBukkit.Ships.VesselTypes.Satic.StaticShipType;
import MoseShipsBukkit.Ships.VesselTypes.Satic.StaticShipTypeUtil;
import MoseShipsBukkit.Utils.State.BlockState;

public class WaterShip extends WaterType implements LiveRequiredPercent, LiveLockedAltitude {

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
		if(structure.isEmpty()){
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
	public Optional<MovementResult> hasRequirements(List<MovingBlock> blocks) {
		MovementResult result = new MovementResult();
		int waterLevel = getWaterLevel();
		switch (waterLevel) {
			case -1:
				result.put(MovementResult.CauseKeys.NOT_IN_WATER, true);
				return Optional.of(result);
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
					result.put(MovementResult.CauseKeys.NOT_ENOUGH_PERCENT, new TwoStore<BlockState, Float>(g_materials[0], (g_block_percent - percent)));
					return Optional.of(result);
				}

		}
	}

	@Override
	public boolean shouldFall() {
		return false;
	}

	@Override
	public void onSave(ShipsLocalDatabase database) {
		List<String> list = new ArrayList<String>();
		for (BlockState state : g_materials) {
			list.add(state.toNoString());
		}
		database.setOverride(g_block_percent, LiveRequiredPercent.REQUIRED_PERCENT);
		database.setOverride(list, LiveRequiredPercent.REQUIRED_BLOCKS);
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
		for(BlockState state : this.g_materials){
			requiredPBlocks.add(state.toString());
		}
		if(g_user == null){
			map.put("Owner", "None");
		}else{
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
	
	@Override
	public Optional<MovementResult> move(BlockFace dir, int speed, BlockState... movingTo) {
		return super.move(dir, speed, new BlockState(Material.AIR), new BlockState(Material.WATER), new BlockState(Material.STATIONARY_WATER));
	}
	
	@Override
	public Optional<MovementResult> rotate(Rotate type, BlockState... movingTo) {
		return super.rotate(type, new BlockState(Material.AIR), new BlockState(Material.WATER), new BlockState(Material.STATIONARY_WATER));
	}
	
	@Override
	public Optional<MovementResult> rotateRight(BlockState... movingTo) {
		return super.rotateRight(new BlockState(Material.AIR), new BlockState(Material.WATER), new BlockState(Material.STATIONARY_WATER));
	}
	
	@Override
	public Optional<MovementResult> rotateLeft(BlockState... movingTo) {
		return super.rotateLeft(new BlockState(Material.AIR), new BlockState(Material.WATER), new BlockState(Material.STATIONARY_WATER));
	}
	
	@Override
	public Optional<MovementResult> teleport(Location loc, BlockState... movingTo) {
		return super.teleport(loc, new BlockState(Material.AIR), new BlockState(Material.WATER), new BlockState(Material.STATIONARY_WATER));
	}
	
	@Override
	public Optional<MovementResult> teleport(StoredMovement move, BlockState... movingTo) {
		return super.teleport(move, new BlockState(Material.AIR), new BlockState(Material.WATER), new BlockState(Material.STATIONARY_WATER));
	}
	
	@Override
	public Optional<MovementResult> teleport(Location loc, int X, int Y, int Z, BlockState... movingTo) {
		return super.teleport(loc, X, Y, Z, new BlockState(Material.AIR), new BlockState(Material.WATER), new BlockState(Material.STATIONARY_WATER));
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
				config.setOverride(Arrays.asList(new BlockState(Material.WOOL, (byte) -1).toNoString()), StaticRequiredPercent.DEFAULT_REQUIRED_BLOCKS);
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
		public Optional<LoadableShip> createVessel(String name, Block sign) {
			return Optional.of((LoadableShip) new WaterShip(name, sign, sign.getLocation()));
		}

		@Override
		public Optional<LoadableShip> loadVessel(ShipsData data, BasicConfig config) {
			WaterShip ship = new WaterShip(data);
			int percent = config.get(Integer.class, LiveRequiredPercent.REQUIRED_PERCENT);
			ship.setRequiredPercent(percent);

			List<String> sStates = config.getList(String.class, LiveRequiredPercent.REQUIRED_BLOCKS);
			BlockState[] states = BlockState.getStates(sStates);
			ship.setPercentBlocks(states);

			return Optional.of((LoadableShip) ship);
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

	}

}

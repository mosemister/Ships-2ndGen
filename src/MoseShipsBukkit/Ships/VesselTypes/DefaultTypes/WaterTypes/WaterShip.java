package MoseShipsBukkit.Ships.VesselTypes.DefaultTypes.WaterTypes;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import MoseShips.Stores.TwoStore;
import MoseShipsBukkit.Causes.MovementResult;
import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Configs.Files.StaticShipConfig;
import MoseShipsBukkit.Ships.ShipsData;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.Live.LiveRequiredPercent;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.Static.StaticRequiredPercent;
import MoseShipsBukkit.Ships.VesselTypes.DefaultTypes.WaterType;
import MoseShipsBukkit.Ships.VesselTypes.Satic.StaticShipType;
import MoseShipsBukkit.Ships.VesselTypes.Satic.StaticShipTypeUtil;
import MoseShipsBukkit.Utils.State.BlockState;

public class WaterShip extends WaterType implements LiveRequiredPercent{

	public static final String BLOCK_PERCENT = "ShipsData.BlockPercent.Percent";
	public static final String BLOCK_MATERIAL = "ShipsData.BlockPercent.Material";
	
	int g_block_percent = getStatic().getDefaultRequiredPercent();
	BlockState[] g_materials = getStatic().getDefaultPersentBlocks();
	
	public WaterShip(ShipsData data) {
		super(data);
	}
	
	public WaterShip(String name, Block sign, Location teleport) {
		super(name, sign, teleport);
	}
	
	public WaterShip setRequiredPercent(int A){
		g_block_percent = A;
		return this;
	}
	
	public WaterShip setPercentBlocks(BlockState... blocks){
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
		List<Block> blocks = new ArrayList<Block>();
		for(Block block : structure){
			if(BlockState.contains(block, getPercentBlocks())){
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
		switch(waterLevel){
			case -1:
				result.put(MovementResult.CauseKeys.NOT_IN_WATER, true);
				return Optional.of(result);
			default:
				int blockCount = 0;
				for(MovingBlock block : blocks){
					if(BlockState.contains(block.getOrigin().getBlock(), g_materials)){
						blockCount++;
					}
				}
				float percent = (blockCount*100)/blocks.size();
				if(percent >= g_block_percent){
					return Optional.empty();
				}else{
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
	public int getMaxBlocks() {
		return g_max_blocks;
	}

	@Override
	public int getMinBlocks() {
		return g_min_blocks;
	}

	@Override
	public Map<String, Object> getInfo() {
		return null;
	}

	@Override
	public StaticWaterShip getStatic() {
		return StaticShipTypeUtil.getType(StaticWaterShip.class).get();
	}
	
	public static class StaticWaterShip implements StaticShipType, StaticRequiredPercent{
		
		public StaticWaterShip(){
			StaticShipTypeUtil.inject(this);
			File file = new File("plugins/Ships/Configuration/ShipTypes/WaterShip.yml");
			if(!file.exists()){
				System.out.println("file does not exist");
				StaticShipConfig config = new StaticShipConfig(file);
				config.setOverride(2, StaticShipConfig.DATABASE_DEFAULT_ALTITUDE);
				config.setOverride(3, StaticShipConfig.DATABASE_DEFAULT_BOOST);
				config.setOverride(4000, StaticShipConfig.DATABASE_DEFAULT_MAX_SIZE);
				config.setOverride(0, StaticShipConfig.DATABASE_DEFAULT_MIN_SIZE);
				config.setOverride(2, StaticShipConfig.DATABASE_DEFAULT_SPEED);
				config.setOverride(Arrays.asList(new BlockState(Material.WOOL, (byte)-1).toNoString()), StaticShipConfig.DATABASE_REQUIRED_BLOCKS);
				config.setOverride(40, StaticShipConfig.DATABASE_REQUIRED_PERCENT);
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
			int percent = config.get(Integer.class, BLOCK_PERCENT);
			ship.setRequiredPercent(percent);
			
			List<String> sStates = config.getList(String.class, BLOCK_MATERIAL);
			BlockState[] states = BlockState.getStates(sStates);
			ship.setPercentBlocks(states);
			
			return Optional.of((LoadableShip)ship);
		}

		@Override
		public int getDefaultRequiredPercent() {
			StaticShipConfig config = new StaticShipConfig("WaterShip");
			return config.get(Integer.class, StaticShipConfig.DATABASE_REQUIRED_PERCENT);
		}

		@Override
		public BlockState[] getDefaultPersentBlocks() {
			StaticShipConfig config = new StaticShipConfig("WaterShip");
			List<String> list = config.getList(String.class, StaticShipConfig.DATABASE_REQUIRED_BLOCKS);
			BlockState[] states = BlockState.getStates(list);
			return states;
		}
		
	}

}

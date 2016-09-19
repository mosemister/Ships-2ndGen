package MoseShipsBukkit.Ships.VesselTypes.DefaultTypes.WaterTypes;

import java.util.ArrayList;
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
		// TODO Auto-generated method stub
		return null;
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
		}

		@Override
		public String getName() {
			return "WaterShip";
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
			return false;
		}

		@Override
		public Optional<LoadableShip> createVessel(String name, Block licence) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<LoadableShip> loadVessel(ShipsData data, BasicConfig config) {
			WaterShip ship = new WaterShip(data);
			int percent = config.get(Integer.class, BLOCK_PERCENT);
			ship.setRequiredPercent(percent);
			
			List<String> sStates = config.getList(String.class, BLOCK_MATERIAL);
			BlockState[] states = BlockState.getStates(sStates);
			ship.setPercentBlocks(states);
			
			return Optional.of(ship);
		}

		@Override
		public int getDefaultRequiredPercent() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public BlockState[] getDefaultPersentBlocks() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

}

package MoseShipsBukkit.Ships.VesselTypes.Structure;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;

import MoseShipsBukkit.BlockFinder.BasicBlockFinder;
import MoseShipsBukkit.BlockFinder.BlockFinderUtils;
import MoseShipsBukkit.Configs.Files.ShipsConfig;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.ShipsData;

public class ShipStructure {
	
	ShipsData g_parent;
	List<Block> g_structure;
	
	public List<Block> getRaw(){
		return g_structure;
	}
	
	public List<ShipVector> getRawVectors(){
		List<ShipVector> vectors = new ArrayList<ShipVector>();
		Block base = g_parent.getLocation().getBlock();
		for(Block block : g_structure){
			vectors.add(new ShipVector(base, block));
		}
		return vectors;
	}
	
	public List<Block> getBlocks(Material material){
		List<Block> list = new ArrayList<Block>();
		for(Block block : g_structure){
			if(block.getType().equals(material)){
				list.add(block);
			}
		}
		return list;
	}
	
	@SuppressWarnings("deprecation")
	public List<Block> getBlocks(Material material, byte data){
		List<Block> list = new ArrayList<Block>();
		for(Block block : g_structure){
			if((block.getType().equals(material)) && (block.getData() == data)){
				list.add(block);
			}
		}
		return list;
	}
	
	public ShipVector createVector(Block block){
		return new ShipVector(g_parent.getLocation().getBlock(), block);
	}
	
	public ShipsData getShip(){
		return g_parent;
	}
	
	public ShipStructure updateStructure() {
		Integer trackLimit = ShipsConfig.CONFIG.get(Integer.class,
				ShipsConfig.PATH_STRUCTURE_STRUCTURELIMITS_TRACKLIMIT);
		if (trackLimit == null) {
			trackLimit = 5000;
		}
		List<Block> list = BlockFinderUtils.getConfigSelected().getConnectedBlocks(trackLimit, g_parent.getLocation().getBlock());
		g_structure = list;
		return this;
	}
	
	public ShipStructure updateStructure(Class<? extends BasicBlockFinder> finderType){
		Integer trackLimit = ShipsConfig.CONFIG.get(Integer.class,
				ShipsConfig.PATH_STRUCTURE_STRUCTURELIMITS_TRACKLIMIT);
		if (trackLimit == null) {
			trackLimit = 5000;
		}
		return updateStructure(finderType, trackLimit);
	}
	
	public ShipStructure updateStructure(Class<? extends BasicBlockFinder> finderType, int trackLimit){
		g_structure = BlockFinderUtils.getFinder(finderType).getConnectedBlocks(trackLimit, g_parent.getLocation().getBlock());
		return this;
	}
}

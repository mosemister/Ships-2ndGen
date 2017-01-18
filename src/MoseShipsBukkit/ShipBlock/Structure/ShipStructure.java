package MoseShipsBukkit.ShipBlock.Structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import MoseShipsBukkit.Algorthum.BlockFinder.BasicBlockFinder;
import MoseShipsBukkit.Configs.ShipsConfig;
import MoseShipsBukkit.ShipBlock.BlockState;
import MoseShipsBukkit.ShipBlock.ShipVector;
import MoseShipsBukkit.Utils.BlockFinderUtil;
import MoseShipsBukkit.Utils.ShipSignUtil;
import MoseShipsBukkit.Vessel.Data.ShipsData;

public class ShipStructure implements Iterable<Block>{
	
	ShipsData g_parent;
	List<Block> g_structure = new ArrayList<Block>();
	
	public ShipStructure(ShipsData parent){
		g_parent = parent;
	}
	
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
	
	public List<Block> getBlocks(BlockState state){
		List<Block> list = new ArrayList<Block>();
		for(Block block : g_structure){
			if(state.looseMatch(block)){
				list.add(block);
			}
		}
		return list;
	}
	
	public List<Block> getSigns(){
		List<Block> list = new ArrayList<Block>();
		for(Block block : g_structure){
			if((block instanceof Sign) && (ShipSignUtil.getSign((Sign)block.getState()).isPresent())){
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
		List<Block> list = BlockFinderUtil.getConfigSelected().getConnectedBlocks(trackLimit, g_parent.getLocation().getBlock());
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
		g_structure = BlockFinderUtil.getFinder(finderType).getConnectedBlocks(trackLimit, g_parent.getLocation().getBlock());
		return this;
	}
	
	public ShipStructure add(Block block){
		g_structure.add(block);
		return this;
	}
	
	public ShipStructure remove(Block block){
		g_structure.remove(block);
		return this;
	}
	
	public int getSize(){
		return g_structure.size();
	}

	@Override
	public Iterator<Block> iterator() {
		return g_structure.iterator();
	}
}

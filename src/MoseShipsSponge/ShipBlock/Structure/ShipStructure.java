package MoseShipsSponge.ShipBlock.Structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

import MoseShipsSponge.Algorthum.BlockFinder.BasicBlockFinder;
import MoseShipsSponge.Configs.ShipsConfig;
import MoseShipsSponge.Utils.BlockFinderUtil;
import MoseShipsSponge.Vessel.Common.RootTypes.ShipsData;

public class ShipStructure implements Iterable<Location<World>> {

	List<Location<World>> g_structure = new ArrayList<>();
	
	public List<Location<World>> getRaw(){
		return g_structure;
	}
	
	public List<Vector3i> getRawVectors(ShipsData data){
		List<Vector3i> vectors = new ArrayList<>();
		Vector3i base = data.getLocation().getBlockPosition().clone();
		for (Location<World> block : g_structure) {
			vectors.add(base.clone().add(block.getBlockPosition().clone()));
		}
		return vectors;
	}
	
	public Location<World> getBlock(ShipsData data, Vector3i vector){
		return data.getLocation().copy().add(vector);
	}
	
	public List<Location<World>> getBlocks(BlockType type){
		return g_structure.stream().filter(b -> b.getBlockType().equals(type)).collect(Collectors.toList());
	}
	
	public List<Location<World>> getBlocks(BlockState state){
		return g_structure.stream().filter(b -> b.getBlock().equals(state)).collect(Collectors.toList());
	}
	
	public List<Location<World>> getBlocks(Class<? extends TileEntity> tileEntity){
		return g_structure.stream().filter(b -> {
			Optional<TileEntity> opEntity = b.getTileEntity();
			if(opEntity.isPresent()){
				TileEntity entity = opEntity.get();
				if(tileEntity.isInstance(entity)){
					return true;
				}
			}
			return false;
		}).collect(Collectors.toList());
	}
	
	public List<Location<World>> getSigns(){
		return getBlocks(Sign.class);
	}
	
	public int getHeight(){
		if(g_structure.isEmpty()){
			return 0;
		}
		Location<World> first = g_structure.get(0);
		int min = first.getBlockY();
		int max = min;
		for(Location<World> loc : g_structure){
			int Y = loc.getBlockY();
			if(min > Y){
				min = Y;
			}
			if(max < Y){
				max = Y;
			}
		}
		return max - min;
	}
	
	public int getLength(){
		if(g_structure.isEmpty()){
			return 0;
		}
		Location<World> first = g_structure.get(0);
		int min = first.getBlockX();
		int max = min;
		for(Location<World> loc : g_structure){
			int X = loc.getBlockX();
			if(min > X){
				min = X;
			}
			if(max < X){
				max = X;
			}
		}
		return max - min;
	}
	
	public int getWidth(){
		if(g_structure.isEmpty()){
			return 0;
		}
		Location<World> first = g_structure.get(0);
		int min = first.getBlockZ();
		int max = min;
		for(Location<World> loc : g_structure){
			int Z = loc.getBlockZ();
			if(min > Z){
				min = Z;
			}
			if(max < Z){
				max = Z;
			}
		}
		return max - min;
	}
	
	public Vector3i createVector(ShipsData data, Location<World> loc){
		return data.getLocation().getBlockPosition().sub(loc.getBlockPosition());
	}
	
	public ShipStructure updateStructure(ShipsData data){
		Integer trackLimit = ShipsConfig.CONFIG.get(Integer.class, ShipsConfig.PATH_STRUCTURE_STRUCTURELIMITS_TRACKLIMIT);
		if(trackLimit == null){
			trackLimit = 5000;
		}
		g_structure = updateStructure(BlockFinderUtil.getConfigSelected(), data, trackLimit);
		return this;
	}
	
	public ShipStructure updateStructure(Class<? extends BasicBlockFinder> finderType, ShipsData data){
		Integer trackLimit = ShipsConfig.CONFIG.get(Integer.class, ShipsConfig.PATH_STRUCTURE_STRUCTURELIMITS_TRACKLIMIT);
		if(trackLimit == null){
			trackLimit = 5000;
		}
		return updateStructure(finderType, data, trackLimit);
	}
	
	public ShipStructure updateStructure(Class<? extends BasicBlockFinder> finderType, ShipsData data, int trackLimit){
		updateStructure(BlockFinderUtil.getFinder(finderType), data, trackLimit);
		return this;
	}
	
	private List<Location<World>> updateStructure(BasicBlockFinder finder, ShipsData data, int trackLimit){
		return finder.getConnectedBlocks(trackLimit, data.getLocation());
	}
	
	public ShipStructure setStructure(List<Location<World>> blocks){
		g_structure = blocks;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public ShipStructure add(Location<World>... locs){
		g_structure.addAll(Arrays.asList(locs));
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public ShipStructure remove(Location<World>... locs){
		g_structure.removeAll(Arrays.asList(locs));
		return this;
	}
	
	public int getSize(){
		return g_structure.size();
	}
	
	@Override
	public Iterator<Location<World>> iterator() {
		return g_structure.iterator();
	}
	
}

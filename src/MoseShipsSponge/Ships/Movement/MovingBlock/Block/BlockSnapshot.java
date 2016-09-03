package MoseShipsSponge.Ships.Movement.MovingBlock.Block;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;

public class BlockSnapshot{
	
	Location loc;
	Material material;
	MaterialData data;
	
	public static final Map<Material, Class<? extends BlockSnapshot>> VALUE_TYPES = new HashMap<Material, Class<? extends BlockSnapshot>>();
	
	protected BlockSnapshot(BlockState state){
		this.loc = state.getLocation();
		this.material = state.getType();
		this.data = state.getData();
	}
	
	public Location getLocation(){
		return loc;
	}
	
	public Material getMaterial(){
		return material;
	}
	
	public MaterialData getData(){
		return data;
	}
	
	public void placeBlock(){
		placeBlock(loc.getBlock());
	}
	
	@SuppressWarnings("deprecation")
	public void placeBlock(Block loc){
		placeBlock(loc, data.getData());
	}
	
	@SuppressWarnings("deprecation")
	public void placeBlock(Block loc, byte data){
		loc.setType(material, false);
		loc.setData(data, false);
	}
	
	public static BlockSnapshot createSnapshot(Block block){
		return createSnapshot(block.getState());
	}
	
	public static BlockSnapshot createSnapshot(BlockState state){
		for(Entry<Material, Class<? extends BlockSnapshot>> entry : VALUE_TYPES.entrySet()){
			if(entry.getKey().equals(state.getType())){
				try {
					BlockSnapshot snapshot = entry.getValue().getConstructor(state.getClass()).newInstance(state);
					return snapshot;
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
		}
		return new BlockSnapshot(state);
	}

}

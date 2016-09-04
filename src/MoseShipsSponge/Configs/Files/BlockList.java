package MoseShipsSponge.Configs.Files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.Material;

import MoseShipsSponge.ShipsMain;
import MoseShipsSponge.Configs.BasicConfig;
import MoseShipsSponge.Utils.State.BlockState;

public class BlockList extends BasicConfig {

	List<BlockState> MATERIALS = new ArrayList<BlockState>();
	List<BlockState> RAM = new ArrayList<BlockState>();
	
	public static final BlockList BLOCK_LIST = new BlockList();

	public BlockList() {
		super("/Configuration/MaterialsList");
		applyMissing();
		// code for testing purpose only
		MATERIALS.add(new BlockState(Material.WOOD));
		MATERIALS.add(new BlockState(Material.WALL_SIGN));

	}
	
	public BlockList applyMissing(){
		for(Material material : Material.values()){
			set(getDefaultValue(material).name(), material.name() + ".DataValue-1");
		}
		save();
		return this;
	}
	
	public List<BlockState> getDefaultMaterialsList(){
		List<BlockState> list = new ArrayList<BlockState>();
		list.add(new BlockState(Material.LOG));
		list.add(new BlockState(Material.LOG_2));
		list.add(new BlockState(Material.LEAVES));
		list.add(new BlockState(Material.LEAVES_2));
		list.add(new BlockState(Material.SPONGE));
		list.add(new BlockState(Material.GLASS));
		list.add(new BlockState(Material.LAPIS_BLOCK));
		list.add(new BlockState(Material.DISPENSER));
		list.add(new BlockState(Material.NOTE_BLOCK));
		list.add(new BlockState(Material.PISTON_STICKY_BASE));
		list.add(new BlockState(Material.PISTON_BASE));
		list.add(new BlockState(Material.PISTON_EXTENSION));
		list.add(new BlockState(Material.WOOL));
		list.add(new BlockState(Material.GOLD_BLOCK));
		list.add(new BlockState(Material.IRON_BLOCK));
		list.add(new BlockState(Material.DOUBLE_STEP));
		list.add(new BlockState(Material.STEP));
		list.add(new BlockState(Material.BRICK));
		list.add(new BlockState(Material.TNT));
		list.add(new BlockState(Material.BOOKSHELF));
		list.add(new BlockState(Material.MOSSY_COBBLESTONE));
		list.add(new BlockState(Material.OBSIDIAN));
		list.add(new BlockState(Material.TORCH));
		list.add(new BlockState(Material.FIRE));
		list.add(new BlockState(Material.MOB_SPAWNER));
		list.add(new BlockState(Material.WOOD_STAIRS));
		list.add(new BlockState(Material.CHEST));
		list.add(new BlockState(Material.REDSTONE_WIRE));
		list.add(new BlockState(Material.DIAMOND_BLOCK));
	}

	public List<BlockState> getMaterialsList() {
		return MATERIALS;
	}

	public List<BlockState> getRamMaterialsList() {
		return RAM;
	}
	
	public List<Material> getUnusedMaterialsList(){
		List<Material> materials = Arrays.asList(Material.values());
		for(BlockState state : getMaterialsList()){
			materials.remove(state.getMaterial());
		}
		for(BlockState state : getRamMaterialsList()){
			materials.remove(state.getMaterial());
		}
		return materials;
	}

	public boolean contains(Material material, byte data, ListType type) {
		switch (type) {
			case MATERIALS:
				for(BlockState state : MATERIALS){
					if(state.getMaterial().equals(material) && (data == state.getData())){
						return true;
					}
				}
				return false;
			case NONE:
				return getUnusedMaterialsList().contains(material);
			case RAM:
				for(BlockState state : RAM){
					if(state.getMaterial().equals(material) && (data == state.getData())){
						return true;
					}
				}
				return false;
		}
		return false;
	}

	public List<BlockState> getContains(Material material, ListType type) {
		List<BlockState> list = new ArrayList<BlockState>();
		switch (type) {
			case MATERIALS:
				for(BlockState state : MATERIALS){
					if(state.getMaterial().equals(material)){
						list.add(state);
					}
				}
				return list;
			case NONE:
				if (getUnusedMaterialsList().contains(material)){
					list.add(new BlockState(material));
					return list;
				}
			case RAM:
				for(BlockState state : RAM){
					if(state.getMaterial().equals(material)){
						list.add(state);
					}
				}
				return list;
		}
		return list;
	}

	public static enum ListType {
		MATERIALS,
		RAM,
		NONE;
	}

}

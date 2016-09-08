package MoseShipsBukkit.Configs.Files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Utils.State.BlockState;

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
	
	public ListType getDefaultValue(Material material){
		for(BlockState material2 : getDefaultMaterialsList()){
			if(material2.getMaterial().equals(material)){
				return ListType.MATERIALS;
			}
		}
		for(BlockState material2 : getDefaultRamList()){
			if(material2.getMaterial().equals(material)){
				return ListType.RAM;
			}
		}
		return ListType.NONE;
	}
	
	public List<BlockState> getDefaultMaterialsList(){
		List<BlockState> list = new ArrayList<BlockState>();
		list.add(new BlockState(Material.LOG));
		list.add(new BlockState(Material.LOG_2));
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
		list.add(new BlockState(Material.WORKBENCH));
		list.add(new BlockState(Material.FURNACE));
		list.add(new BlockState(Material.BURNING_FURNACE));
		list.add(new BlockState(Material.SIGN_POST));
		list.add(new BlockState(Material.WOODEN_DOOR));
		list.add(new BlockState(Material.LADDER));
		list.add(new BlockState(Material.WALL_SIGN));
		list.add(new BlockState(Material.LEVER));
		list.add(new BlockState(Material.STONE_PLATE));
		list.add(new BlockState(Material.IRON_DOOR_BLOCK));
		list.add(new BlockState(Material.WOOD_PLATE));
		list.add(new BlockState(Material.REDSTONE_TORCH_OFF));
		list.add(new BlockState(Material.REDSTONE_TORCH_ON));
		list.add(new BlockState(Material.STONE_BUTTON));
		list.add(new BlockState(Material.JUKEBOX));
		list.add(new BlockState(Material.FENCE));
		list.add(new BlockState(Material.NETHERRACK));
		list.add(new BlockState(Material.CAKE_BLOCK));
		list.add(new BlockState(Material.DIODE_BLOCK_OFF));
		list.add(new BlockState(Material.DIODE_BLOCK_ON));
		list.add(new BlockState(Material.STAINED_GLASS));
		list.add(new BlockState(Material.TRAP_DOOR));
		list.add(new BlockState(Material.MONSTER_EGGS));
		list.add(new BlockState(Material.SMOOTH_BRICK));
		list.add(new BlockState(Material.IRON_FENCE));
		list.add(new BlockState(Material.THIN_GLASS));
		list.add(new BlockState(Material.VINE));
		list.add(new BlockState(Material.FENCE_GATE));
		list.add(new BlockState(Material.BRICK_STAIRS));
		list.add(new BlockState(Material.SMOOTH_STAIRS));
		list.add(new BlockState(Material.ENCHANTMENT_TABLE));
		list.add(new BlockState(Material.BREWING_STAND));
		list.add(new BlockState(Material.CAULDRON));
		list.add(new BlockState(Material.REDSTONE_LAMP_OFF));
		list.add(new BlockState(Material.REDSTONE_LAMP_ON));
		list.add(new BlockState(Material.WOOD_DOUBLE_STEP));
		list.add(new BlockState(Material.WOOD_STEP));
		list.add(new BlockState(Material.SANDSTONE_STAIRS));
		list.add(new BlockState(Material.ENDER_CHEST));
		list.add(new BlockState(Material.TRIPWIRE_HOOK));
		list.add(new BlockState(Material.TRIPWIRE));
		list.add(new BlockState(Material.EMERALD_BLOCK));
		list.add(new BlockState(Material.SPRUCE_WOOD_STAIRS));
		list.add(new BlockState(Material.BIRCH_WOOD_STAIRS));
		list.add(new BlockState(Material.JUNGLE_WOOD_STAIRS));
		list.add(new BlockState(Material.BEACON));
		list.add(new BlockState(Material.COBBLE_WALL));
		list.add(new BlockState(Material.FLOWER_POT));
		list.add(new BlockState(Material.WOOD_BUTTON));
		list.add(new BlockState(Material.SKULL));
		list.add(new BlockState(Material.ANVIL));
		list.add(new BlockState(Material.TRAPPED_CHEST));
		list.add(new BlockState(Material.GOLD_PLATE));
		list.add(new BlockState(Material.IRON_PLATE));
		list.add(new BlockState(Material.REDSTONE_COMPARATOR_OFF));
		list.add(new BlockState(Material.REDSTONE_COMPARATOR_ON));
		list.add(new BlockState(Material.DAYLIGHT_DETECTOR));
		list.add(new BlockState(Material.REDSTONE_BLOCK));
		list.add(new BlockState(Material.HOPPER));
		list.add(new BlockState(Material.QUARTZ_BLOCK));
		list.add(new BlockState(Material.QUARTZ_STAIRS));
		list.add(new BlockState(Material.DROPPER));
		list.add(new BlockState(Material.STAINED_CLAY));
		list.add(new BlockState(Material.STAINED_GLASS_PANE));
		list.add(new BlockState(Material.ACACIA_STAIRS));
		list.add(new BlockState(Material.DARK_OAK_STAIRS));
		list.add(new BlockState(Material.SLIME_BLOCK));
		list.add(new BlockState(Material.BARRIER));
		list.add(new BlockState(Material.IRON_TRAPDOOR));
		list.add(new BlockState(Material.PRISMARINE));
		list.add(new BlockState(Material.SEA_LANTERN));
		list.add(new BlockState(Material.HAY_BLOCK));
		list.add(new BlockState(Material.CARPET));
		list.add(new BlockState(Material.HARD_CLAY));
		list.add(new BlockState(Material.COAL_BLOCK));
		list.add(new BlockState(Material.STANDING_BANNER));
		list.add(new BlockState(Material.WALL_BANNER));
		list.add(new BlockState(Material.DAYLIGHT_DETECTOR_INVERTED));
		list.add(new BlockState(Material.SPRUCE_FENCE_GATE));
		list.add(new BlockState(Material.BIRCH_FENCE_GATE));
		list.add(new BlockState(Material.JUNGLE_FENCE_GATE));
		list.add(new BlockState(Material.DARK_OAK_FENCE_GATE));
		list.add(new BlockState(Material.ACACIA_FENCE_GATE));
		list.add(new BlockState(Material.SPRUCE_FENCE));
		list.add(new BlockState(Material.BIRCH_FENCE));
		list.add(new BlockState(Material.JUNGLE_DOOR));
		list.add(new BlockState(Material.DARK_OAK_FENCE));
		list.add(new BlockState(Material.ACACIA_FENCE));
		return list;
	}
	
	public List<BlockState> getDefaultRamList(){
		List<BlockState> list = new ArrayList<BlockState>();
		list.add(new BlockState(Material.SAPLING));
		list.add(new BlockState(Material.LEAVES));
		list.add(new BlockState(Material.WEB));
		list.add(new BlockState(Material.LONG_GRASS));
		list.add(new BlockState(Material.DEAD_BUSH));
		list.add(new BlockState(Material.YELLOW_FLOWER));
		list.add(new BlockState(Material.RED_ROSE));
		list.add(new BlockState(Material.BROWN_MUSHROOM));
		list.add(new BlockState(Material.RED_MUSHROOM));
		list.add(new BlockState(Material.CROPS));
		list.add(new BlockState(Material.SUGAR_CANE_BLOCK));
		list.add(new BlockState(Material.PUMPKIN_STEM));
		list.add(new BlockState(Material.MELON_STEM));
		list.add(new BlockState(Material.WATER_LILY));
		list.add(new BlockState(Material.NETHER_WARTS));
		list.add(new BlockState(Material.CARROT));
		list.add(new BlockState(Material.POTATO));
		list.add(new BlockState(Material.LEAVES_2));
		list.add(new BlockState(Material.DOUBLE_PLANT));
		return list;
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

package MoseShipsSponge.Configs.Files;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.trait.BlockTrait;

import MoseShipsSponge.ShipsMain;
import MoseShipsSponge.Configs.BasicConfig;

public class BlockList extends BasicConfig {

	Map<BlockState, ListType> BLOCKS = new HashMap<BlockState, ListType>();
	
	public static final BlockList BLOCK_LIST = new BlockList();

	public BlockList() {
		super("/Configuration/MaterialsList");
		applyMissing();
		// code for testing purpose only
		BLOCKS.put(BlockTypes.WALL_SIGN.getDefaultState(), ListType.MATERIALS);
		BLOCKS.put(BlockTypes.PLANKS.getDefaultState(), ListType.MATERIALS);
	}
	
	public void reload(){
		BLOCKS = new HashMap<BlockState, ListType>();
		List<BlockState> list = new ArrayList<>();
		getAllPossibleStates().stream().forEach(s -> {
			String value = get(String.class, s.getId());
			if(value != null){
				ListType type = ListType.valueFrom(value);
				if(type == null){
					list.add(s);
				}else{
					BLOCKS.put(s, type);
				}
			}
		});
		if(list.size() != 0){
			//Sponge.getServer().getConsole().sendMessage(Text.of("The following failed to load \n" + list));
		}
	}
	
	public BlockList applyMissing(){
		getAllPossibleStates().stream().forEach(b -> {
			set(false, b.getId(), "enabled");
		});
		save();
		return this;
	}
	
	public ListType resetMaterial(BlockState state, ListType type){
		return BLOCKS.replace(state, type);
	}

	public List<BlockState> getBlocks(ListType type) {
		List<BlockState> states = new ArrayList<BlockState>();
		for (Entry<BlockState, ListType> entry : BLOCKS.entrySet()) {
			if (entry.getValue().equals(type)) {
				states.add(entry.getKey());
			}
		}
		return states;
	}

	public ListType getDefaultValue(BlockType type) {
		if(getDefaultMaterialList().stream().anyMatch(b -> b.equals(type))) {
				return ListType.MATERIALS;
		}
		if(getDefaultRamList().stream().anyMatch(b -> b.equals(type))){
			return ListType.RAM;
		}
		return ListType.NONE;
	}
	
	public List<BlockState> getDefaultMaterialList(){
		List<BlockState> list = new ArrayList<>();
		list.addAll(getAllPossibleStates(BlockTypes.LOG));
		list.addAll(getAllPossibleStates(BlockTypes.LOG2));
		list.addAll(getAllPossibleStates(BlockTypes.PLANKS));
		list.addAll(getAllPossibleStates(BlockTypes.SPONGE));
		list.addAll(getAllPossibleStates(BlockTypes.GLASS));
		list.addAll(getAllPossibleStates(BlockTypes.LAPIS_BLOCK));
		list.addAll(getAllPossibleStates(BlockTypes.DISPENSER));
		list.addAll(getAllPossibleStates(BlockTypes.NOTEBLOCK));
		list.addAll(getAllPossibleStates(BlockTypes.STICKY_PISTON));
		list.addAll(getAllPossibleStates(BlockTypes.PISTON));
		list.addAll(getAllPossibleStates(BlockTypes.PISTON_EXTENSION));
		list.addAll(getAllPossibleStates(BlockTypes.PISTON_HEAD));
		list.addAll(getAllPossibleStates(BlockTypes.WOOL));
		list.addAll(getAllPossibleStates(BlockTypes.GOLD_BLOCK));
		list.addAll(getAllPossibleStates(BlockTypes.IRON_BLOCK));
		list.addAll(getAllPossibleStates(BlockTypes.DOUBLE_STONE_SLAB));
		list.addAll(getAllPossibleStates(BlockTypes.STONE_SLAB));
		list.addAll(getAllPossibleStates(BlockTypes.BRICK_BLOCK));
		list.addAll(getAllPossibleStates(BlockTypes.TNT));
		list.addAll(getAllPossibleStates(BlockTypes.BOOKSHELF));
		list.addAll(getAllPossibleStates(BlockTypes.MOSSY_COBBLESTONE));
		list.addAll(getAllPossibleStates(BlockTypes.OBSIDIAN));
		list.addAll(getAllPossibleStates(BlockTypes.TORCH));
		list.addAll(getAllPossibleStates(BlockTypes.FIRE));
		list.addAll(getAllPossibleStates(BlockTypes.MOB_SPAWNER));
		list.addAll(getAllPossibleStates(BlockTypes.OAK_STAIRS));
		list.addAll(getAllPossibleStates(BlockTypes.CHEST));
		list.addAll(getAllPossibleStates(BlockTypes.REDSTONE_WIRE));
		list.addAll(getAllPossibleStates(BlockTypes.DIAMOND_BLOCK));
		list.addAll(getAllPossibleStates(BlockTypes.CRAFTING_TABLE));
		list.addAll(getAllPossibleStates(BlockTypes.FURNACE));
		list.addAll(getAllPossibleStates(BlockTypes.LIT_FURNACE));
		list.addAll(getAllPossibleStates(BlockTypes.WALL_SIGN));
		list.addAll(getAllPossibleStates(BlockTypes.STANDING_SIGN));
		list.addAll(getAllPossibleStates(BlockTypes.WOODEN_DOOR));
		list.addAll(getAllPossibleStates(BlockTypes.LEVER));
		list.addAll(getAllPossibleStates(BlockTypes.STONE_PRESSURE_PLATE));
		list.addAll(getAllPossibleStates(BlockTypes.IRON_DOOR));
		list.addAll(getAllPossibleStates(BlockTypes.WOODEN_PRESSURE_PLATE));
		list.addAll(getAllPossibleStates(BlockTypes.REDSTONE_TORCH));
		list.addAll(getAllPossibleStates(BlockTypes.UNLIT_REDSTONE_TORCH));
		list.addAll(getAllPossibleStates(BlockTypes.STONE_BUTTON));
		list.addAll(getAllPossibleStates(BlockTypes.JUKEBOX));
		list.addAll(getAllPossibleStates(BlockTypes.FENCE));
		list.addAll(getAllPossibleStates(BlockTypes.NETHERRACK));
		list.addAll(getAllPossibleStates(BlockTypes.CAKE));
		list.addAll(getAllPossibleStates(BlockTypes.POWERED_REPEATER));
		list.addAll(getAllPossibleStates(BlockTypes.UNPOWERED_REPEATER));
		list.addAll(getAllPossibleStates(BlockTypes.STAINED_GLASS));
		list.addAll(getAllPossibleStates(BlockTypes.TRAPDOOR));
		list.addAll(getAllPossibleStates(BlockTypes.MONSTER_EGG));
		list.addAll(getAllPossibleStates(BlockTypes.GLASS_PANE));
		list.addAll(getAllPossibleStates(BlockTypes.VINE));
		list.addAll(getAllPossibleStates(BlockTypes.FENCE_GATE));
		list.addAll(getAllPossibleStates(BlockTypes.BREWING_STAND));
		list.addAll(getAllPossibleStates(BlockTypes.ENCHANTING_TABLE));
		list.addAll(getAllPossibleStates(BlockTypes.CAULDRON));
		list.addAll(getAllPossibleStates(BlockTypes.LIT_REDSTONE_LAMP));
		list.addAll(getAllPossibleStates(BlockTypes.REDSTONE_LAMP));
		list.addAll(getAllPossibleStates(BlockTypes.DOUBLE_WOODEN_SLAB));
		list.addAll(getAllPossibleStates(BlockTypes.WOODEN_SLAB));
		list.addAll(getAllPossibleStates(BlockTypes.SANDSTONE_STAIRS));
		list.addAll(getAllPossibleStates(BlockTypes.ENDER_CHEST));
		list.addAll(getAllPossibleStates(BlockTypes.TRIPWIRE_HOOK));
		list.addAll(getAllPossibleStates(BlockTypes.EMERALD_BLOCK));
		list.addAll(getAllPossibleStates(BlockTypes.SPRUCE_STAIRS));
		list.addAll(getAllPossibleStates(BlockTypes.BIRCH_STAIRS));
		list.addAll(getAllPossibleStates(BlockTypes.JUNGLE_STAIRS));
		list.addAll(getAllPossibleStates(BlockTypes.BEACON));
		list.addAll(getAllPossibleStates(BlockTypes.COBBLESTONE_WALL));
		list.addAll(getAllPossibleStates(BlockTypes.FLOWER_POT));
		list.addAll(getAllPossibleStates(BlockTypes.WOODEN_BUTTON));
		list.addAll(getAllPossibleStates(BlockTypes.SKULL));
		list.addAll(getAllPossibleStates(BlockTypes.ANVIL));
		list.addAll(getAllPossibleStates(BlockTypes.TRAPPED_CHEST));
		list.addAll(getAllPossibleStates(BlockTypes.HEAVY_WEIGHTED_PRESSURE_PLATE));
		list.addAll(getAllPossibleStates(BlockTypes.LIGHT_WEIGHTED_PRESSURE_PLATE));
		list.addAll(getAllPossibleStates(BlockTypes.POWERED_COMPARATOR));
		list.addAll(getAllPossibleStates(BlockTypes.UNPOWERED_COMPARATOR));
		list.addAll(getAllPossibleStates(BlockTypes.DAYLIGHT_DETECTOR));
		list.addAll(getAllPossibleStates(BlockTypes.REDSTONE_BLOCK));
		list.addAll(getAllPossibleStates(BlockTypes.HOPPER));
		list.addAll(getAllPossibleStates(BlockTypes.QUARTZ_BLOCK));
		list.addAll(getAllPossibleStates(BlockTypes.QUARTZ_STAIRS));
		list.addAll(getAllPossibleStates(BlockTypes.DROPPER));
		list.addAll(getAllPossibleStates(BlockTypes.STAINED_HARDENED_CLAY));
		list.addAll(getAllPossibleStates(BlockTypes.STAINED_GLASS_PANE));
		list.addAll(getAllPossibleStates(BlockTypes.ACACIA_STAIRS));
		list.addAll(getAllPossibleStates(BlockTypes.DARK_OAK_STAIRS));
		list.addAll(getAllPossibleStates(BlockTypes.SLIME));
		list.addAll(getAllPossibleStates(BlockTypes.BARRIER));
		list.addAll(getAllPossibleStates(BlockTypes.IRON_TRAPDOOR));
		list.addAll(getAllPossibleStates(BlockTypes.PRISMARINE));
		list.addAll(getAllPossibleStates(BlockTypes.SEA_LANTERN));
		list.addAll(getAllPossibleStates(BlockTypes.HAY_BLOCK));
		list.addAll(getAllPossibleStates(BlockTypes.CARPET));
		list.addAll(getAllPossibleStates(BlockTypes.HARDENED_CLAY));
		list.addAll(getAllPossibleStates(BlockTypes.COAL_BLOCK));
		list.addAll(getAllPossibleStates(BlockTypes.STANDING_BANNER));
		list.addAll(getAllPossibleStates(BlockTypes.WALL_BANNER));
		list.addAll(getAllPossibleStates(BlockTypes.DAYLIGHT_DETECTOR_INVERTED));
		list.addAll(getAllPossibleStates(BlockTypes.SPRUCE_FENCE_GATE));
		list.addAll(getAllPossibleStates(BlockTypes.BIRCH_FENCE_GATE));
		list.addAll(getAllPossibleStates(BlockTypes.JUNGLE_FENCE_GATE));
		list.addAll(getAllPossibleStates(BlockTypes.DARK_OAK_FENCE_GATE));
		list.addAll(getAllPossibleStates(BlockTypes.ACACIA_FENCE_GATE));
		list.addAll(getAllPossibleStates(BlockTypes.SPRUCE_FENCE));
		list.addAll(getAllPossibleStates(BlockTypes.BIRCH_FENCE));
		list.addAll(getAllPossibleStates(BlockTypes.JUNGLE_DOOR));
		list.addAll(getAllPossibleStates(BlockTypes.DARK_OAK_FENCE));
		list.addAll(getAllPossibleStates(BlockTypes.ACACIA_FENCE));
		return list;
	}
	
	public List<BlockState> getDefaultRamList() {
		List<BlockState> list = new ArrayList<>();
		list.addAll(getAllPossibleStates(BlockTypes.SAPLING));
		list.addAll(getAllPossibleStates(BlockTypes.LEAVES));
		list.addAll(getAllPossibleStates(BlockTypes.WEB));
		list.addAll(getAllPossibleStates(BlockTypes.TALLGRASS));
		list.addAll(getAllPossibleStates(BlockTypes.DEADBUSH));
		list.addAll(getAllPossibleStates(BlockTypes.YELLOW_FLOWER));
		list.addAll(getAllPossibleStates(BlockTypes.RED_FLOWER));
		list.addAll(getAllPossibleStates(BlockTypes.BROWN_MUSHROOM));
		list.addAll(getAllPossibleStates(BlockTypes.RED_MUSHROOM));
		list.addAll(getAllPossibleStates(BlockTypes.PUMPKIN_STEM));
		list.addAll(getAllPossibleStates(BlockTypes.MELON_STEM));
		list.addAll(getAllPossibleStates(BlockTypes.WATERLILY));
		list.addAll(getAllPossibleStates(BlockTypes.NETHER_WART));
		list.addAll(getAllPossibleStates(BlockTypes.CARROTS));
		list.addAll(getAllPossibleStates(BlockTypes.POTATOES));
		list.addAll(getAllPossibleStates(BlockTypes.LEAVES2));
		list.addAll(getAllPossibleStates(BlockTypes.DOUBLE_PLANT));
		return list;
	}

	public List<BlockState> getList(ListType type){
		List<BlockState> list = new ArrayList<>();
		BLOCKS.entrySet().stream().filter(e -> e.getValue().equals(type)).forEach(e -> list.add(e.getKey()));
		return list;
	}
	
	public boolean contains(BlockState state, ListType type) {
		if (BLOCKS.entrySet().stream().anyMatch(b -> b.getKey().equals(state))){
			return true;
		}
		return false;
	}

	public List<BlockState> getContains(BlockType block, ListType type) {
		List<BlockState> list = new ArrayList<>();
		BLOCKS.entrySet().stream().filter(e -> e.getKey().getType().equals(block)).filter(e -> e.getValue().equals(type)).forEach(e -> list.add(e.getKey()));
		return list;
	}

	public static List<BlockState> getAllPossibleStates(BlockType type) {
		List<BlockState> states = new ArrayList<>();
		for (BlockTrait<?> trait : type.getTraits()) {
			for (Object value : trait.getPossibleValues()) {
				Optional<BlockState> state = type.getDefaultState().withTrait(trait, value);
				if (state.isPresent()) {
					states.add(state.get());
				}
			}
		}
		return states;
	}

	public static List<BlockState> getAllPossibleStates() {
		List<BlockState> states = new ArrayList<>();
		Collection<BlockType> types = ShipsMain.getPlugin().getGame().getRegistry().getAllOf(BlockType.class);
		types.forEach(b -> {
			states.addAll(getAllPossibleStates(b));
		});
		return states;
	}

	public static enum ListType {
		MATERIALS("-m", "m"),
		RAM("-r", "r"),
		NONE("-n", "n");

		String[] g_ali;

		private ListType(String... ali) {
			g_ali = ali;
		}

		public String[] getAlias() {
			return g_ali;
		}

		public static ListType valueFrom(String value) {
			for (ListType type : values()) {
				if (value.equalsIgnoreCase(type.name())) {
					return type;
				} else {
					for (String al : type.g_ali) {
						if (al.equalsIgnoreCase(value)) {
							return type;
						}
					}
				}
			}
			return null;
		}
	}

}

package MoseShipsSponge.Configs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.text.Text;

public class BlockList extends BasicConfig {

	Map<BlockType, ListType> BLOCKS = new HashMap<BlockType, ListType>();

	public static final BlockList BLOCK_LIST = new BlockList();

	public BlockList() {
		super("/Configuration/MaterialsList");
		applyMissing();
	}

	public void reload() {
		BLOCKS = new HashMap<BlockType, ListType>();
		List<BlockState> list = new ArrayList<>();
		Collection<BlockType> list2 = Sponge.getRegistry().getAllOf(BlockType.class);
		list2.stream().forEach(b -> {
				String value = get(String.class, s.getId());
				if (value != null) {
					ListType type = ListType.valueFrom(value);
					if (type == null) {
						list.add(s);
					} else {
						BLOCKS.put(s, type);
					}
				}
		});
		if (list.size() != 0) {
			ConsoleSource console = Sponge.getServer().getConsole();
			console.sendMessage(Text.of("The following failed to load"));
			list.stream().forEach(f -> console.sendMessage(Text.of(" - " + f.getName())));
		}
	}

	public BlockList applyMissing() {
		Collection<BlockType> list = Sponge.getRegistry().getAllOf(BlockType.class);
		list.stream().forEach(b -> {
				String name = getDefaultValue(s).name();
				String id = s.getId();
				if((name == null) || (id == null)) {
					return;
				}
				set(getDefaultValue(s).name(), s.getId());
		});
		save();
		return this;
	}
	
	public ListType resetMaterial(BlockType type, ListType list) {
		BLOCKS.replace(type, list);
		return list;
	}

	public List<BlockType> getBlocks(ListType type) {
		List<BlockType> states = new ArrayList<>();
		BLOCKS.entrySet().stream().filter(e -> e.equals(type)).forEach(e -> states.add(e.getKey()));
		return states;
	}

	public ListType getDefaultValue(BlockType type) {
		if (getDefaultMaterialList().stream().anyMatch(b -> b.getType().equals(type))) {
			return ListType.MATERIALS;
		}
		if (getDefaultRamList().stream().anyMatch(b -> b.getType().equals(type))) {
			return ListType.RAM;
		}
		return ListType.NONE;
	}

	public List<BlockType> getDefaultMaterialList() {
		List<BlockType> list = new ArrayList<>();
		list.addAll(BlockTypes.LOG);
		list.addAll(BlockTypes.LOG2);
		list.addAll(BlockTypes.PLANKS);
		list.addAll(BlockTypes.SPONGE);
		list.addAll(BlockTypes.GLASS);
		list.addAll(BlockTypes.LAPIS_BLOCK);
		list.addAll(BlockTypes.DISPENSER);
		list.addAll(BlockTypes.NOTEBLOCK);
		list.addAll(BlockTypes.STICKY_PISTON);
		list.addAll(BlockTypes.PISTON);
		list.addAll(BlockTypes.PISTON_EXTENSION);
		list.addAll(BlockTypes.PISTON_HEAD);
		list.addAll(BlockTypes.WOOL);
		list.addAll(BlockTypes.GOLD_BLOCK);
		list.addAll(BlockTypes.IRON_BLOCK);
		list.addAll(BlockTypes.DOUBLE_STONE_SLAB);
		list.addAll(BlockTypes.STONE_SLAB);
		list.addAll(BlockTypes.BRICK_BLOCK);
		list.addAll(BlockTypes.TNT);
		list.addAll(BlockTypes.BOOKSHELF);
		list.addAll(BlockTypes.MOSSY_COBBLESTONE);
		list.addAll(BlockTypes.OBSIDIAN);
		list.addAll(BlockTypes.TORCH);
		list.addAll(BlockTypes.FIRE);
		list.addAll(BlockTypes.MOB_SPAWNER);
		list.addAll(BlockTypes.OAK_STAIRS);
		list.addAll(BlockTypes.CHEST);
		list.addAll(BlockTypes.REDSTONE_WIRE);
		list.addAll(BlockTypes.DIAMOND_BLOCK);
		list.addAll(BlockTypes.CRAFTING_TABLE);
		list.addAll(BlockTypes.FURNACE);
		list.addAll(BlockTypes.LIT_FURNACE);
		list.addAll(BlockTypes.WALL_SIGN);
		list.addAll(BlockTypes.STANDING_SIGN);
		list.addAll(BlockTypes.WOODEN_DOOR);
		list.addAll(BlockTypes.LEVER);
		list.addAll(BlockTypes.STONE_PRESSURE_PLATE);
		list.addAll(BlockTypes.IRON_DOOR);
		list.addAll(BlockTypes.WOODEN_PRESSURE_PLATE);
		list.addAll(BlockTypes.REDSTONE_TORCH);
		list.addAll(BlockTypes.UNLIT_REDSTONE_TORCH);
		list.addAll(BlockTypes.STONE_BUTTON);
		list.addAll(BlockTypes.JUKEBOX);
		list.addAll(BlockTypes.FENCE);
		list.addAll(BlockTypes.NETHERRACK);
		list.addAll(BlockTypes.CAKE);
		list.addAll(BlockTypes.POWERED_REPEATER);
		list.addAll(BlockTypes.UNPOWERED_REPEATER);
		list.addAll(BlockTypes.STAINED_GLASS);
		list.addAll(BlockTypes.TRAPDOOR);
		list.addAll(BlockTypes.MONSTER_EGG);
		list.addAll(BlockTypes.GLASS_PANE);
		list.addAll(BlockTypes.VINE);
		list.addAll(BlockTypes.FENCE_GATE);
		list.addAll(BlockTypes.BREWING_STAND);
		list.addAll(BlockTypes.ENCHANTING_TABLE);
		list.addAll(BlockTypes.CAULDRON);
		list.addAll(BlockTypes.LIT_REDSTONE_LAMP);
		list.addAll(BlockTypes.REDSTONE_LAMP);
		list.addAll(BlockTypes.DOUBLE_WOODEN_SLAB);
		list.addAll(BlockTypes.WOODEN_SLAB);
		list.addAll(BlockTypes.SANDSTONE_STAIRS);
		list.addAll(BlockTypes.ENDER_CHEST);
		list.addAll(BlockTypes.TRIPWIRE_HOOK);
		list.addAll(BlockTypes.EMERALD_BLOCK);
		list.addAll(BlockTypes.SPRUCE_STAIRS);
		list.addAll(BlockTypes.BIRCH_STAIRS);
		list.addAll(BlockTypes.JUNGLE_STAIRS);
		list.addAll(BlockTypes.BEACON);
		list.addAll(BlockTypes.COBBLESTONE_WALL);
		list.addAll(BlockTypes.FLOWER_POT);
		list.addAll(BlockTypes.WOODEN_BUTTON);
		list.addAll(BlockTypes.SKULL);
		list.addAll(BlockTypes.ANVIL);
		list.addAll(BlockTypes.TRAPPED_CHEST);
		list.addAll(BlockTypes.HEAVY_WEIGHTED_PRESSURE_PLATE);
		list.addAll(BlockTypes.LIGHT_WEIGHTED_PRESSURE_PLATE);
		list.addAll(BlockTypes.POWERED_COMPARATOR);
		list.addAll(BlockTypes.UNPOWERED_COMPARATOR);
		list.addAll(BlockTypes.DAYLIGHT_DETECTOR);
		list.addAll(BlockTypes.REDSTONE_BLOCK);
		list.addAll(BlockTypes.HOPPER);
		list.addAll(BlockTypes.QUARTZ_BLOCK);
		list.addAll(BlockTypes.QUARTZ_STAIRS);
		list.addAll(BlockTypes.DROPPER);
		list.addAll(BlockTypes.STAINED_HARDENED_CLAY);
		list.addAll(BlockTypes.STAINED_GLASS_PANE);
		list.addAll(BlockTypes.ACACIA_STAIRS);
		list.addAll(BlockTypes.DARK_OAK_STAIRS);
		list.addAll(BlockTypes.SLIME);
		list.addAll(BlockTypes.BARRIER);
		list.addAll(BlockTypes.IRON_TRAPDOOR);
		list.addAll(BlockTypes.PRISMARINE);
		list.addAll(BlockTypes.SEA_LANTERN);
		list.addAll(BlockTypes.HAY_BLOCK);
		list.addAll(BlockTypes.CARPET);
		list.addAll(BlockTypes.HARDENED_CLAY);
		list.addAll(BlockTypes.COAL_BLOCK);
		list.addAll(BlockTypes.STANDING_BANNER);
		list.addAll(BlockTypes.WALL_BANNER);
		list.addAll(BlockTypes.DAYLIGHT_DETECTOR_INVERTED);
		list.addAll(BlockTypes.SPRUCE_FENCE_GATE);
		list.addAll(BlockTypes.BIRCH_FENCE_GATE);
		list.addAll(BlockTypes.JUNGLE_FENCE_GATE);
		list.addAll(BlockTypes.DARK_OAK_FENCE_GATE);
		list.addAll(BlockTypes.ACACIA_FENCE_GATE);
		list.addAll(BlockTypes.SPRUCE_FENCE);
		list.addAll(BlockTypes.BIRCH_FENCE);
		list.addAll(BlockTypes.JUNGLE_DOOR);
		list.addAll(BlockTypes.DARK_OAK_FENCE);
		list.addAll(BlockTypes.ACACIA_FENCE);
		return list;
	}

	public List<BlockType> getDefaultRamList() {
		List<BlockType> list = new ArrayList<>();
		list.addAll(BlockTypes.SAPLING.getAllBlockStates());
		list.addAll(BlockTypes.LEAVES.getAllBlockStates());
		list.addAll(BlockTypes.WEB.getAllBlockStates());
		list.addAll(BlockTypes.TALLGRASS.getAllBlockStates());
		list.addAll(BlockTypes.DEADBUSH.getAllBlockStates());
		list.addAll(BlockTypes.YELLOW_FLOWER.getAllBlockStates());
		list.addAll(BlockTypes.RED_FLOWER.getAllBlockStates());
		list.addAll(BlockTypes.BROWN_MUSHROOM.getAllBlockStates());
		list.addAll(BlockTypes.RED_MUSHROOM.getAllBlockStates());
		list.addAll(BlockTypes.PUMPKIN_STEM.getAllBlockStates());
		list.addAll(BlockTypes.MELON_STEM.getAllBlockStates());
		list.addAll(BlockTypes.WATERLILY.getAllBlockStates());
		list.addAll(BlockTypes.NETHER_WART.getAllBlockStates());
		list.addAll(BlockTypes.CARROTS.getAllBlockStates());
		list.addAll(BlockTypes.POTATOES.getAllBlockStates());
		list.addAll(BlockTypes.LEAVES2.getAllBlockStates());
		list.addAll(BlockTypes.DOUBLE_PLANT.getAllBlockStates());
		return list;
	}
	
	public ListType getList(BlockType type) {
		Optional<Entry<BlockState, ListType>> opList = BLOCKS.entrySet().stream().filter(e -> e.getKey().getType().equals(type)).findFirst();
		if(opList.isPresent()) {
			return opList.get().getValue();
		}
		return ListType.NONE;
	}

	public ListType getList(BlockState state) {
		return this.BLOCKS.get(state);
	}
	
	public List<BlockState> getList(ListType type) {
		List<BlockState> list = new ArrayList<>();
		BLOCKS.entrySet().stream().filter(e -> e.getValue().equals(type)).forEach(e -> list.add(e.getKey()));
		return list;
	}

	public boolean contains(BlockState state, ListType type) {
		if (BLOCKS.entrySet().stream().anyMatch(b -> (b.getKey().equals(state)) && (b.getValue().equals(type)))) {
			return true;
		}
		return false;
	}

	public List<BlockState> getContains(BlockType block, ListType type) {
		List<BlockState> list = new ArrayList<>();
		BLOCKS.entrySet().stream().filter(e -> e.getKey().getType().equals(block))
				.filter(e -> e.getValue().equals(type)).forEach(e -> list.add(e.getKey()));
		return list;
	}

	@Override
	public BasicConfig save() {
		BLOCKS.entrySet().stream().forEach(e -> {
			String name = e.getValue().name();
			String key = e.getKey().getId();
			if((name == null) || (key == null)) {
				System.err.println("Failed to apply " + e.getKey().getName());
				return;
			}
			set(e.getValue().name(), e.getKey().getId());
		});
		return super.save();
	}

	public static enum ListType {
		MATERIALS("-m", "m"), RAM("-r", "r"), NONE("-n", "n");

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

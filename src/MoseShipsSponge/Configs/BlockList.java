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

	Map<BlockState, ListType> BLOCKS = new HashMap<BlockState, ListType>();

	public static final BlockList BLOCK_LIST = new BlockList();

	public BlockList() {
		super("/Configuration/MaterialsList");
		applyMissing();
	}

	public void reload() {
		BLOCKS = new HashMap<BlockState, ListType>();
		List<BlockState> list = new ArrayList<>();
		Collection<BlockType> list2 = Sponge.getRegistry().getAllOf(BlockType.class);
		list2.stream().forEach(b -> {
			b.getAllBlockStates().stream().forEach(s -> {
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
			b.getAllBlockStates().stream().forEach(s -> {
				set(getDefaultValue(s).name(), s.getId());
			});
		});
		save();
		return this;
	}

	public ListType resetMaterial(BlockState state, ListType type) {
		return BLOCKS.replace(state, type);
	}
	
	public ListType resetMaterial(BlockType type, ListType list) {
		for(BlockState state : type.getAllBlockStates()) {
			BLOCKS.replace(state, list);
		}
		return list;
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
		if (getDefaultMaterialList().stream().anyMatch(b -> b.getType().equals(type))) {
			return ListType.MATERIALS;
		}
		if (getDefaultRamList().stream().anyMatch(b -> b.getType().equals(type))) {
			return ListType.RAM;
		}
		return ListType.NONE;
	}

	public ListType getDefaultValue(BlockState type) {
		if (getDefaultMaterialList().stream().anyMatch(b -> b.equals(type))) {
			return ListType.MATERIALS;
		}
		if (getDefaultRamList().stream().anyMatch(b -> b.equals(type))) {
			return ListType.RAM;
		}
		return ListType.NONE;
	}

	public List<BlockState> getDefaultMaterialList() {
		List<BlockState> list = new ArrayList<>();
		list.addAll(BlockTypes.LOG.getAllBlockStates());
		list.addAll(BlockTypes.LOG2.getAllBlockStates());
		list.addAll(BlockTypes.PLANKS.getAllBlockStates());
		list.addAll(BlockTypes.SPONGE.getAllBlockStates());
		list.addAll(BlockTypes.GLASS.getAllBlockStates());
		list.addAll(BlockTypes.LAPIS_BLOCK.getAllBlockStates());
		list.addAll(BlockTypes.DISPENSER.getAllBlockStates());
		list.addAll(BlockTypes.NOTEBLOCK.getAllBlockStates());
		list.addAll(BlockTypes.STICKY_PISTON.getAllBlockStates());
		list.addAll(BlockTypes.PISTON.getAllBlockStates());
		list.addAll(BlockTypes.PISTON_EXTENSION.getAllBlockStates());
		list.addAll(BlockTypes.PISTON_HEAD.getAllBlockStates());
		list.addAll(BlockTypes.WOOL.getAllBlockStates());
		list.addAll(BlockTypes.GOLD_BLOCK.getAllBlockStates());
		list.addAll(BlockTypes.IRON_BLOCK.getAllBlockStates());
		list.addAll(BlockTypes.DOUBLE_STONE_SLAB.getAllBlockStates());
		list.addAll(BlockTypes.STONE_SLAB.getAllBlockStates());
		list.addAll(BlockTypes.BRICK_BLOCK.getAllBlockStates());
		list.addAll(BlockTypes.TNT.getAllBlockStates());
		list.addAll(BlockTypes.BOOKSHELF.getAllBlockStates());
		list.addAll(BlockTypes.MOSSY_COBBLESTONE.getAllBlockStates());
		list.addAll(BlockTypes.OBSIDIAN.getAllBlockStates());
		list.addAll(BlockTypes.TORCH.getAllBlockStates());
		list.addAll(BlockTypes.FIRE.getAllBlockStates());
		list.addAll(BlockTypes.MOB_SPAWNER.getAllBlockStates());
		list.addAll(BlockTypes.OAK_STAIRS.getAllBlockStates());
		list.addAll(BlockTypes.CHEST.getAllBlockStates());
		list.addAll(BlockTypes.REDSTONE_WIRE.getAllBlockStates());
		list.addAll(BlockTypes.DIAMOND_BLOCK.getAllBlockStates());
		list.addAll(BlockTypes.CRAFTING_TABLE.getAllBlockStates());
		list.addAll(BlockTypes.FURNACE.getAllBlockStates());
		list.addAll(BlockTypes.LIT_FURNACE.getAllBlockStates());
		list.addAll(BlockTypes.WALL_SIGN.getAllBlockStates());
		list.addAll(BlockTypes.STANDING_SIGN.getAllBlockStates());
		list.addAll(BlockTypes.WOODEN_DOOR.getAllBlockStates());
		list.addAll(BlockTypes.LEVER.getAllBlockStates());
		list.addAll(BlockTypes.STONE_PRESSURE_PLATE.getAllBlockStates());
		list.addAll(BlockTypes.IRON_DOOR.getAllBlockStates());
		list.addAll(BlockTypes.WOODEN_PRESSURE_PLATE.getAllBlockStates());
		list.addAll(BlockTypes.REDSTONE_TORCH.getAllBlockStates());
		list.addAll(BlockTypes.UNLIT_REDSTONE_TORCH.getAllBlockStates());
		list.addAll(BlockTypes.STONE_BUTTON.getAllBlockStates());
		list.addAll(BlockTypes.JUKEBOX.getAllBlockStates());
		list.addAll(BlockTypes.FENCE.getAllBlockStates());
		list.addAll(BlockTypes.NETHERRACK.getAllBlockStates());
		list.addAll(BlockTypes.CAKE.getAllBlockStates());
		list.addAll(BlockTypes.POWERED_REPEATER.getAllBlockStates());
		list.addAll(BlockTypes.UNPOWERED_REPEATER.getAllBlockStates());
		list.addAll(BlockTypes.STAINED_GLASS.getAllBlockStates());
		list.addAll(BlockTypes.TRAPDOOR.getAllBlockStates());
		list.addAll(BlockTypes.MONSTER_EGG.getAllBlockStates());
		list.addAll(BlockTypes.GLASS_PANE.getAllBlockStates());
		list.addAll(BlockTypes.VINE.getAllBlockStates());
		list.addAll(BlockTypes.FENCE_GATE.getAllBlockStates());
		list.addAll(BlockTypes.BREWING_STAND.getAllBlockStates());
		list.addAll(BlockTypes.ENCHANTING_TABLE.getAllBlockStates());
		list.addAll(BlockTypes.CAULDRON.getAllBlockStates());
		list.addAll(BlockTypes.LIT_REDSTONE_LAMP.getAllBlockStates());
		list.addAll(BlockTypes.REDSTONE_LAMP.getAllBlockStates());
		list.addAll(BlockTypes.DOUBLE_WOODEN_SLAB.getAllBlockStates());
		list.addAll(BlockTypes.WOODEN_SLAB.getAllBlockStates());
		list.addAll(BlockTypes.SANDSTONE_STAIRS.getAllBlockStates());
		list.addAll(BlockTypes.ENDER_CHEST.getAllBlockStates());
		list.addAll(BlockTypes.TRIPWIRE_HOOK.getAllBlockStates());
		list.addAll(BlockTypes.EMERALD_BLOCK.getAllBlockStates());
		list.addAll(BlockTypes.SPRUCE_STAIRS.getAllBlockStates());
		list.addAll(BlockTypes.BIRCH_STAIRS.getAllBlockStates());
		list.addAll(BlockTypes.JUNGLE_STAIRS.getAllBlockStates());
		list.addAll(BlockTypes.BEACON.getAllBlockStates());
		list.addAll(BlockTypes.COBBLESTONE_WALL.getAllBlockStates());
		list.addAll(BlockTypes.FLOWER_POT.getAllBlockStates());
		list.addAll(BlockTypes.WOODEN_BUTTON.getAllBlockStates());
		list.addAll(BlockTypes.SKULL.getAllBlockStates());
		list.addAll(BlockTypes.ANVIL.getAllBlockStates());
		list.addAll(BlockTypes.TRAPPED_CHEST.getAllBlockStates());
		list.addAll(BlockTypes.HEAVY_WEIGHTED_PRESSURE_PLATE.getAllBlockStates());
		list.addAll(BlockTypes.LIGHT_WEIGHTED_PRESSURE_PLATE.getAllBlockStates());
		list.addAll(BlockTypes.POWERED_COMPARATOR.getAllBlockStates());
		list.addAll(BlockTypes.UNPOWERED_COMPARATOR.getAllBlockStates());
		list.addAll(BlockTypes.DAYLIGHT_DETECTOR.getAllBlockStates());
		list.addAll(BlockTypes.REDSTONE_BLOCK.getAllBlockStates());
		list.addAll(BlockTypes.HOPPER.getAllBlockStates());
		list.addAll(BlockTypes.QUARTZ_BLOCK.getAllBlockStates());
		list.addAll(BlockTypes.QUARTZ_STAIRS.getAllBlockStates());
		list.addAll(BlockTypes.DROPPER.getAllBlockStates());
		list.addAll(BlockTypes.STAINED_HARDENED_CLAY.getAllBlockStates());
		list.addAll(BlockTypes.STAINED_GLASS_PANE.getAllBlockStates());
		list.addAll(BlockTypes.ACACIA_STAIRS.getAllBlockStates());
		list.addAll(BlockTypes.DARK_OAK_STAIRS.getAllBlockStates());
		list.addAll(BlockTypes.SLIME.getAllBlockStates());
		list.addAll(BlockTypes.BARRIER.getAllBlockStates());
		list.addAll(BlockTypes.IRON_TRAPDOOR.getAllBlockStates());
		list.addAll(BlockTypes.PRISMARINE.getAllBlockStates());
		list.addAll(BlockTypes.SEA_LANTERN.getAllBlockStates());
		list.addAll(BlockTypes.HAY_BLOCK.getAllBlockStates());
		list.addAll(BlockTypes.CARPET.getAllBlockStates());
		list.addAll(BlockTypes.HARDENED_CLAY.getAllBlockStates());
		list.addAll(BlockTypes.COAL_BLOCK.getAllBlockStates());
		list.addAll(BlockTypes.STANDING_BANNER.getAllBlockStates());
		list.addAll(BlockTypes.WALL_BANNER.getAllBlockStates());
		list.addAll(BlockTypes.DAYLIGHT_DETECTOR_INVERTED.getAllBlockStates());
		list.addAll(BlockTypes.SPRUCE_FENCE_GATE.getAllBlockStates());
		list.addAll(BlockTypes.BIRCH_FENCE_GATE.getAllBlockStates());
		list.addAll(BlockTypes.JUNGLE_FENCE_GATE.getAllBlockStates());
		list.addAll(BlockTypes.DARK_OAK_FENCE_GATE.getAllBlockStates());
		list.addAll(BlockTypes.ACACIA_FENCE_GATE.getAllBlockStates());
		list.addAll(BlockTypes.SPRUCE_FENCE.getAllBlockStates());
		list.addAll(BlockTypes.BIRCH_FENCE.getAllBlockStates());
		list.addAll(BlockTypes.JUNGLE_DOOR.getAllBlockStates());
		list.addAll(BlockTypes.DARK_OAK_FENCE.getAllBlockStates());
		list.addAll(BlockTypes.ACACIA_FENCE.getAllBlockStates());
		return list;
	}

	public List<BlockState> getDefaultRamList() {
		List<BlockState> list = new ArrayList<>();
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

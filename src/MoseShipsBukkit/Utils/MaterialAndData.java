package MoseShipsBukkit.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;

@Deprecated
public enum MaterialAndData {

		AIR(Material.AIR, -1),
		GRASS(Material.GRASS, -1),
		COBBLESTONE(Material.COBBLESTONE, -1),
		BEDROCK(Material.BEDROCK, -1),
		GRAVEL(Material.GRAVEL, -1),
		GOLD_ORE(Material.GOLD_ORE, -1),
		IRON_ORE(Material.IRON_ORE, -1),
		COAL_ORE(Material.COAL_ORE, -1),
		LAPIS_ORE(Material.LAPIS_ORE, -1),
		LAPIS_BLOCK(Material.LAPIS_BLOCK, -1),
		DISPENCER(Material.DISPENSER, -1),
		NOTE_BLOCK(Material.NOTE_BLOCK, -1),
		POWERED_RAIL(Material.POWERED_RAIL, -1),
		DETECTOR_RAIL(Material.DETECTOR_RAIL, -1),
		WEB(Material.WEB, -1),
		DEAD_SHRUB(Material.LONG_GRASS, 0),
		LONG_GRASS(Material.LONG_GRASS, 1),
		FERN(Material.LONG_GRASS, 2),
		DEAD_BUSH(Material.DEAD_BUSH, -1),
		PISTON_MOVING_PIECE(Material.PISTON_MOVING_PIECE, -1),
		YELLOW_FLOWER(Material.YELLOW_FLOWER, -1),
		RED_ROSE(Material.RED_ROSE, -1),
		OAK_SAPLING(Material.SAPLING, 0),
		SPRUCE_SAPLING(Material.SAPLING, 1),
		BIRCH_SAPLING(Material.SAPLING, 2),
		JUNGLE_SAPLING(Material.SAPLING, 3),
		ACACIA_SAPLING(Material.SAPLING, 4),
		DARK_OAK_SAPLING(Material.SAPLING, 5),
		SPONGE(Material.SPONGE, -1),
		GLASS(Material.GLASS, -1),
		OAK_WOOD_PLANK(Material.WOOD, 0),
		SPRUCE_WOOD_PLANK(Material.WOOD, 1),
		BIRCH_WOOD_PLANK(Material.WOOD, 2),
		JUNGLE_WOOD_PLANK(Material.WOOD, 3),
		ACACIA_WOOD_PLANK(Material.WOOD, 4),
		DARK_WOOD_PLANK(Material.WOOD, 5),
		STONE(Material.STONE, 0),
		GRANITE(Material.STONE, 1),
		POLISHED_GRANITE(Material.STONE, 2),
		DIORITE(Material.STONE, 3),
		POLISHED_DIORITE(Material.STONE, 4),
		ANDESITE(Material.STONE, 5),
		POLISHED_ANDESITE(Material.STONE, 6),
		DIRT(Material.DIRT, 0),
		COARSE_DIRT(Material.DIRT, 1),
		PODZOL(Material.DIRT, 2),
		WATER(Material.WATER, -1),
		STILL_WATER(Material.STATIONARY_WATER, -1),
		LAVA(Material.LAVA, -1),
		STILL_LAVA(Material.STATIONARY_LAVA, -1),
		SAND(Material.SAND, 0),
		REDSAND(Material.SAND, 1),
		OAK_WOOD_UP(Material.LOG, 0),
		OAK_WOOD_EAST(Material.LOG, 4),
		OAK_WOOD_NORTH(Material.LOG, 8),
		OAK_WOOD_BARK(Material.LOG, 12),
		SPRUCE_WOOD_UP(Material.LOG, 1),
		SPRUCE_WOOD_EAST(Material.LOG, 5),
		SPRUCE_WOOD_NORTH(Material.LOG, 9),
		SPRUCE_WOOD_BARK(Material.LOG, 13),
		BIRCH_WOOD_UP(Material.LOG, 2),
		BIRCH_WOOD_EAST(Material.LOG, 6),
		BIRCH_WOOD_NORTH(Material.LOG, 10),
		BIRCH_WOOD_BARK(Material.LOG, 14),
		JUNGLE_WOOD_UP(Material.LOG, 3),
		JUNGLE_WOOD_EAST(Material.LOG, 7),
		JUNGLE_WOOD_NORTH(Material.LOG, 11),
		JUNGLE_WOOD_BARK(Material.LOG, 15),
		ACACIA_WOOD_UP(Material.LOG_2, 0),
		ACACIA_WOOD_EAST(Material.LOG_2, 4),
		ACACIA_WOOD_NORTH(Material.LOG_2, 8),
		ACACIA_WOOD_BARK(Material.LOG_2, 12),
		DARK_OAK_WOOD_UP(Material.LOG_2, 1),
		DARK_OAK_WOOD_EAST(Material.LOG_2, 5),
		DARK_OAK_WOOD_NORTH(Material.LOG_2, 9),
		DARK_OAK_WOOD_BARK(Material.LOG_2, 13),
		OAK_LEAVES(Material.LEAVES, 0),
		SPRUCE_LEAVES(Material.LEAVES, 1),
		BIRCH_LEAVES(Material.LEAVES, 2),
		JUNGLE_LEAVES(Material.LEAVES, 3),
		OAK_LEAVES_NO_DECAY(Material.LEAVES, 4),
		SPRUCE_LEAVES_NO_DECAY(Material.LEAVES, 5),
		BIRCH_LEAVES_NO_DECAY(Material.LEAVES, 6),
		JUNGLE_LEAVES_NO_DECAY(Material.LEAVES, 7),
		OAK_LEAVES_CHECK_DECAY(Material.LEAVES, 8),
		SPRUCE_LEAVES_CHECK_DECAY(Material.LEAVES, 9),
		BIRCH_LEAVES_CHECK_DECAY(Material.LEAVES, 10),
		JUNGLE_LEAVES_CHECK_DECAY(Material.LEAVES, 11),
		OAK_LEAVES_NO_DECAY_AND_CHECK(Material.LEAVES, 12),
		SPRUCE_LEAVES_NO_DECAY_AND_CHECK(Material.LEAVES, 13),
		BIRCH_LEAVES_NO_DECAY_AND_CHECK(Material.LEAVES, 14),
		JUNGLE_LEAVES_NO_DECAY_AND_CHECK(Material.LEAVES, 15),
		ACACIA_LEAVES(Material.LEAVES_2, 0),
		DARK_OAK_LEAVES(Material.LEAVES_2, 1),
		ACACIA_LEAVES_NO_DECAY(Material.LEAVES_2, 4),
		DARK_OAK_LEAVES_NO_DECAY(Material.LEAVES_2, 5),
		ACACIA_LEAVES_CHECK_DECAY(Material.LEAVES_2, 8),
		DARK_OAK_LEAVES_CHECK_DECAY(Material.LEAVES_2, 9),
		ACACIA_LEAVES_NO_DECAY_AND_CHECK(Material.LEAVES_2, 12),
		DARK_OAK_LEAVES_NO_DECAY_AND_CHECK(Material.LEAVES_2, 13),
		WOOL_WHITE(Material.WOOL, 0),
		WOOL_ORANGE(Material.WOOL, 1),
		WOOL_MAGENTA(Material.WOOL, 2),
		WOOL_LIGHT_BLUE(Material.WOOL, 3),
		WOOL_YELLOW(Material.WOOL, 4),
		WOOL_LIME(Material.WOOL, 5),
		WOOL_PINK(Material.WOOL, 6),
		WOOL_GRAY(Material.WOOL, 7),
		WOOL_LIGHT_GRAY(Material.WOOL, 8),
		WOOL_CYAN(Material.WOOL, 9),
		WOOL_PURPLE(Material.WOOL, 10),
		WOOL_BLUE(Material.WOOL, 11),
		WOOL_BROWN(Material.WOOL, 12),
		WOOL_GREEN(Material.WOOL, 13),
		WOOL_RED(Material.WOOL, 14),
		WOOL_BLACK(Material.WOOL, 15),
		CLAY_WHITE(Material.STAINED_CLAY, 0),
		CLAY_ORANGE(Material.STAINED_CLAY, 1),
		CLAY_MAGENTA(Material.STAINED_CLAY, 2),
		CLAY_LIGHT_BLUE(Material.STAINED_CLAY, 3),
		CLAY_YELLOW(Material.STAINED_CLAY, 4),
		CLAY_LIME(Material.STAINED_CLAY, 5),
		CLAY_PINK(Material.STAINED_CLAY, 6),
		CLAY_GRAY(Material.STAINED_CLAY, 7),
		CLAY_LIGHT_GRAY(Material.STAINED_CLAY, 8),
		CLAY_CYAN(Material.STAINED_CLAY, 9),
		CLAY_PURPLE(Material.STAINED_CLAY, 10),
		CLAY_BLUE(Material.STAINED_CLAY, 11),
		CLAY_BROWN(Material.STAINED_CLAY, 12),
		CLAY_GREEN(Material.STAINED_CLAY, 13),
		CLAY_RED(Material.STAINED_CLAY, 14),
		CLAY_BLACK(Material.STAINED_CLAY, 15),
		GLASS_WHITE(Material.STAINED_GLASS, 0),
		GLASS_ORANGE(Material.STAINED_GLASS, 1),
		GLASS_MAGENTA(Material.STAINED_GLASS, 2),
		GLASS_LIGHT_BLUE(Material.STAINED_GLASS, 3),
		GLASS_YELLOW(Material.STAINED_GLASS, 4),
		GLASS_LIME(Material.STAINED_GLASS, 5),
		GLASS_PINK(Material.STAINED_GLASS, 6),
		GLASS_GRAY(Material.STAINED_GLASS, 7),
		GLASS_LIGHT_GRAY(Material.STAINED_GLASS, 8),
		GLASS_CYAN(Material.STAINED_GLASS, 9),
		GLASS_PURPLE(Material.STAINED_GLASS, 10),
		GLASS_BLUE(Material.STAINED_GLASS, 11),
		GLASS_BROWN(Material.STAINED_GLASS, 12),
		GLASS_GREEN(Material.STAINED_GLASS, 13),
		GLASS_RED(Material.STAINED_GLASS, 14),
		GLASS_BLACK(Material.STAINED_GLASS, 15),
		TORCH(Material.TORCH, -1),
		TORCH_REDSTONE_ON(Material.REDSTONE_TORCH_ON, -1),
		TORCH_REDSTONE_OFF(Material.REDSTONE_TORCH_OFF, -1),
		STONE_SLAB_DOUBLE(Material.DOUBLE_STONE_SLAB2, 0),
		SANDSTONE_SLAB_DOUBLE(Material.DOUBLE_STONE_SLAB2, 1),
		WOODEN_SLAB_DOUBLE(Material.DOUBLE_STONE_SLAB2, 2),
		COBBLESTONE_SLAB_DOUBLE(Material.DOUBLE_STONE_SLAB2, 3),
		BRICK_SLAB_DOUBLE(Material.DOUBLE_STONE_SLAB2, 4),
		STONE_BRICK_SLAB_DOUBLE(Material.DOUBLE_STONE_SLAB2, 5),
		NETHER_SLAB_DOUBLE(Material.DOUBLE_STONE_SLAB2, 6),
		QUARTZ_SLAB_DOUBLE(Material.DOUBLE_STONE_SLAB2, 7),
		SMOOTH_STONE_SLAB_DOUBLE(Material.DOUBLE_STONE_SLAB2, 8),
		SMOOTH_STANDSTONE_SLAB_DOUBLE(Material.DOUBLE_STONE_SLAB2, 9),
		QUARTZ_TILE_SLAB_DOUBLE(Material.DOUBLE_STONE_SLAB2, 15),
		STONE_STONE_SLAB(Material.STONE_SLAB2, 0),
		SANDSTONE_STONE_SLAB(Material.STONE_SLAB2, 1),
		WOOD_STONE_SLAB(Material.STONE_SLAB2, 2),
		COBBLESTONE_STONE_SLAB(Material.STONE_SLAB2, 3),
		BRICK_STONE_SLAB(Material.STONE_SLAB2, 4),
		STONE_BRICK_STONE_SLAB(Material.STONE_SLAB2, 5),
		NETHER_STONE_SLAB(Material.STONE_SLAB2, 6),
		QUATZ_STONE_SLAB(Material.STONE_SLAB2, 7),
		STONE_SLAB_SOTNE_TOP(Material.STONE_SLAB2, 8),
		SANDSTONE_STONE_SLAB_TOP(Material.STONE_SLAB2, 9),
		WOOD_STONE_SLAB_TOP(Material.STONE_SLAB2, 10),
		COBBLESTONE_STONE_SLAB_TOP(Material.STONE_SLAB2, 11),
		BRICK_STONE_SLAB_TOP(Material.STONE_SLAB2, 12),
		STONE_BRICK_STONE_SLAB_TOP(Material.STONE_SLAB2, 13),
		NETHER_STONE_SLAB_TOP(Material.STONE_SLAB2, 14),
		QUARTZ_STONE_SLAB_TOP(Material.STONE_SLAB2, 15),
		OAK_SLAB_DOUBLE(Material.WOOD_DOUBLE_STEP, 0),
		SPRUCE_SLAB_DOUBLE(Material.WOOD_DOUBLE_STEP, 1),
		BIRCH_SLAB_DOUBLE(Material.WOOD_DOUBLE_STEP, 2),
		JUNGLE_SLAB_DOUBLE(Material.WOOD_DOUBLE_STEP, 3),
		ACACIA_SLAB_DOUBLE(Material.WOOD_DOUBLE_STEP, 4),
		DARK_OAK_SLAB_DOUBLE(Material.WOOD_DOUBLE_STEP, 5),
		OAK_SLAB(Material.WOOD_STEP, 0),
		SPRUCE_SLAB(Material.WOOD_STEP, 1),
		BIRCH_SLAB(Material.WOOD_STEP, 2),
		JUNGLE_SLAB(Material.WOOD_STEP, 3),
		ACACIA_SLAB(Material.WOOD_STEP, 4),
		DARK_OAK_SLAB(Material.WOOD_STEP, 5),
		OAK_SLAB_TOP(Material.WOOD_STEP, 8),
		SPRUCE_SLAB_TOP(Material.WOOD_STEP, 9),
		BIRCH_SLAB_TOP(Material.WOOD_STEP, 10),
		JUNGLE_SLAB_TOP(Material.WOOD_STEP, 11),
		ACACIA_SLAB_TOP(Material.WOOD_STEP, 12),
		DARK_OAK_SLAB_TOP(Material.WOOD_STEP, 13),
		FIRE(Material.FIRE, -1),
		SANDSTONE(Material.SANDSTONE, 0),
		CHISELED_SANDSTONE(Material.SANDSTONE, 1),
		SMOOTH_SANDSTONE(Material.SANDSTONE, 2),
		RED_STANDSTONE(Material.RED_SANDSTONE, 0),
		CHISELED_RED_STANDSTONE(Material.RED_SANDSTONE, 1),
		SMOOTH_RED_STANDSTONE(Material.RED_SANDSTONE, 2),
		BED(Material.BED_BLOCK, -1),
		PISTON(Material.PISTON_BASE, -1),
		PISTON_STICKY(Material.PISTON_STICKY_BASE, -1),
		PISTON_EXTENDED(Material.PISTON_EXTENSION, -1),
		OAK_WOOD_STAIRS(Material.WOOD_STAIRS, -1),
		SPRUCE_WOOD_STAIRS(Material.SPRUCE_WOOD_STAIRS, -1),
		DARK_OAK_DOOR(Material.DARK_OAK_DOOR, -1),
		ACACIA_DOOR(Material.ACACIA_DOOR, -1),
		JUNGLE_DOOR(Material.JUNGLE_DOOR, -1),
		BIRCH_DOOR(Material.BIRCH_DOOR, -1),
		SPRUCE_DOOR(Material.SPRUCE_DOOR, -1),
		ACACIA_FENCE(Material.ACACIA_FENCE, -1),
		DARK_OAK_FENCE(Material.DARK_OAK_FENCE, -1),
		JUNGLE_FENCE(Material.JUNGLE_FENCE, -1),
		BIRCH_FENCE(Material.BIRCH_FENCE, -1),
		SPRUCE_FENCE(Material.SPRUCE_FENCE, -1),
		WHITE_CARPET(Material.CARPET, 0),
		ORANGE_CARPET(Material.CARPET, 1),
		MAGENTA_CARPET(Material.CARPET, 2),
		LIGHT_BLUE_CARPET(Material.CARPET, 3),
		YELLOW_CARPET(Material.CARPET, 4),
		LIME_CARPET(Material.CARPET, 5),
		PINK_CARPET(Material.CARPET, 6),
		GREY_CARPET(Material.CARPET, 7),
		LIGHT_GREY_CARPET(Material.CARPET, 8),
		CYAN_CARPET(Material.CARPET, 9),
		PURPLE_CARPET(Material.CARPET, 10),
		BLUE_CARPET(Material.CARPET, 11),
		BROWN_CARPET(Material.CARPET, 12),
		GREEN_CARPET(Material.CARPET, 13),
		RED_CARPET(Material.CARPET, 14),
		BLACK_CARPET(Material.CARPET, 15),
		HOPPER(Material.HOPPER, -1),
		COAL_BLOCK(Material.COAL_BLOCK, -1),
		DAYLIGHT_SENSOR(Material.DAYLIGHT_DETECTOR, -1),
		DAYLIGHT_SENSOR_2(Material.DAYLIGHT_DETECTOR_INVERTED, -1),
		WALL_BANNER(Material.WALL_BANNER, -1),
		STANDING_BANNER(Material.STANDING_BANNER, -1),
		BARRIER(Material.BARRIER, -1),
		WOODEN_BUTTON(Material.WOOD_BUTTON, -1),
		REDSTONE_BLOCK(Material.REDSTONE_BLOCK, -1),
		TRAPPED_CHEST(Material.TRAPPED_CHEST, -1),
		FLOWER_POT(Material.FLOWER_POT, -1),
		DROPPER(Material.DROPPER, -1),
		CHISELED_QUARTZ_BLOCK(Material.QUARTZ_BLOCK, 1),
		PILLAR_QUARTZ_UP(Material.QUARTZ_BLOCK, 2),
		PILLER_QUARTZ_NORTH(Material.QUARTZ_BLOCK, 3),
		PILLER_QUARTZ_EAST(Material.QUARTZ_BLOCK, 4),
		QUARTZ_BLOCK(Material.QUARTZ_BLOCK, -1),
		WALL_SIGN(Material.WALL_SIGN, -1),
		REDSTONE_ORE(Material.REDSTONE_ORE, -1),
		SOIL(Material.SOIL, -1),
		CHEST(Material.CHEST, -1),
		LEVER(Material.LEVER, -1),
		MOB_SPAWNER(Material.MOB_SPAWNER, -1),
		CRAFTINGTABLE(Material.WORKBENCH, -1),
		DIAMOND_BLOCK(Material.DIAMOND_BLOCK, -1),
		DIAMOND_ORE(Material.DIAMOND_ORE, -1),
		RAILS(Material.RAILS, -1),
		LADDER(Material.LADDER, -1),
		STONE_BUTTON(Material.STONE_BUTTON, -1),
		FURNACE(Material.FURNACE, -1),
		BURNING_FURNACE(Material.BURNING_FURNACE, -1),
		REDSTONE_ORE_GLOWING(Material.GLOWING_REDSTONE_ORE, -1),
		SNOW(Material.SNOW, -1),
		SIGN_POST(Material.SIGN_POST, -1),
		TNT(Material.TNT, -1),
		BRICK(Material.BRICK, -1),
		IRON_BLOCK(Material.IRON_BLOCK, -1),
		GOLD_BLOCK(Material.GOLD_BLOCK, -1),
		ICE(Material.ICE, -1),
		MUSHROOM_RED(Material.RED_MUSHROOM, -1),
		MUSHROOM_BROWN(Material.BROWN_MUSHROOM, -1),
		BOOKSHELF(Material.BOOKSHELF, -1),
		OBSIDIAN(Material.OBSIDIAN, -1),
		REDSTONE_WIRE(Material.REDSTONE_WIRE, -1),
		CROPS(Material.CROPS, -1),
		WOODEN_DOOR(Material.WOODEN_DOOR, -1),
		IRON_DOOR(Material.IRON_DOOR_BLOCK, -1),
		SNOW_BLOCK(Material.SNOW_BLOCK, -1),
		CACTUS(Material.CACTUS, -1),
		CLAY(Material.CLAY, -1),
		SUGER_CANE_BLOCK(Material.SUGAR_CANE_BLOCK, -1),
		JUKEBOX(Material.JUKEBOX, -1),
		FENCE(Material.FENCE, -1),
		PUMPKIN(Material.PUMPKIN, -1),
		SOUL_SAND(Material.SOUL_SAND, -1),
		GLOWSTONE(Material.GLOWSTONE, -1),
		PORTAL(Material.PORTAL, -1),
		JACK_O_LANTERN(Material.JACK_O_LANTERN, -1),
		CAKE(Material.CAKE_BLOCK, -1),
		REDSTONE_REPEATER_OFF(Material.DIODE_BLOCK_OFF, -1),
		REDSTONE_REPEATER_ON(Material.DIODE_BLOCK_ON, -1),
		TRAP_DOOR(Material.TRAP_DOOR, -1),
		WATER_LILY(Material.WATER_LILY, -1),
		FENCE_GATE(Material.FENCE_GATE, -1),
		NETHER_BRICK(Material.NETHER_BRICK, -1),
		NETHER_FENCE(Material.NETHER_FENCE, -1),
		NETHER_WARTS(Material.NETHER_WARTS, -1),
		ENCHANTING_TABLE(Material.ENCHANTMENT_TABLE, -1),
		BREWING_STAND(Material.BREWING_STAND, -1),
		CAULDRON(Material.CAULDRON, -1),
		ENDER_PORTAL(Material.ENDER_PORTAL, -1),
		ENDER_PORTAL_FRAME(Material.ENDER_PORTAL_FRAME, -1),
		DRAGON_EGG(Material.DRAGON_EGG, -1),
		COMMAND_BLOCK(Material.COMMAND, -1),
		ANVIL(Material.ANVIL, -1),
		SEA_LANTERN(Material.SEA_LANTERN, -1),
		IRON_TRAP_DOOR(Material.IRON_TRAPDOOR, -1),
		TRIPWIRE(Material.TRIPWIRE, -1),
		TRIPWIRE_HOOK(Material.TRIPWIRE_HOOK, -1),
		PACKED_ICE(Material.PACKED_ICE, -1),
		HAY_BLOCK(Material.HAY_BLOCK, -1),
		ENDER_CHEST(Material.ENDER_CHEST, -1),
		PRISMARINE(Material.PRISMARINE, 0),
		PRISMARINE_BRICKS(Material.PRISMARINE, 1),
		DARK_PRISMARINE(Material.PRISMARINE, 2),
		EMERALD_ORE(Material.EMERALD_ORE, -1),
		EMERALD_BLOCK(Material.EMERALD_BLOCK, -1),
		BEACON(Material.BEACON, -1),
		REDSTONE_LANP_ON(Material.REDSTONE_LAMP_ON, -1),
		REDSTONE_LAMP_OFF(Material.REDSTONE_LAMP_OFF, -1),
		COBBLE_WALL(Material.COBBLE_WALL, -1),
		REDSTONE_COMPARATOR_ON(Material.REDSTONE_COMPARATOR_ON, -1),
		REDSTONE_COMPARATOR_OFF(Material.REDSTONE_COMPARATOR_OFF, -1),
		MELON_BLOCK(Material.MELON_BLOCK, -1),
		MUSHROOM_HUGE_1(Material.HUGE_MUSHROOM_1, -1),
		MUSHROOM_HUGE_2(Material.HUGE_MUSHROOM_2, -1),
		COCOA(Material.COCOA, -1),
		HARD_CLAY(Material.HARD_CLAY, -1),
		SLIME_BLOCK(Material.SLIME_BLOCK, -1),
		NETHERRACK(Material.NETHERRACK, -1),
		ACTIVATION_RAIL(Material.ACTIVATOR_RAIL, -1),
		CARROT(Material.CARROT, -1),
		POTATO(Material.POTATO, -1),
		SKULL(Material.SKULL, -1),
		GOLD_PLATE(Material.GOLD_PLATE, -1),
		IRON_PLATE(Material.IRON_PLATE, -1),
		QUARTZ_ORE(Material.QUARTZ_ORE, -1),
		ACACIA_FENCE_GATE(Material.ACACIA_FENCE_GATE, -1),
		DARK_OAK_FENCE_GATE(Material.DARK_OAK_FENCE_GATE, -1),
		JUNGLE_FENCE_GATE(Material.JUNGLE_FENCE_GATE, -1),
		BIRCH_FENCE_GATE(Material.BIRCH_FENCE_GATE, -1),
		SPRUCE_FENCE_GATE(Material.SPRUCE_FENCE_GATE, -1),
		IRON_FENCE(Material.IRON_FENCE, -1),
		STONE_PLATE(Material.STONE_PLATE, -1),
		WOOD_PLATE(Material.WOOD_PLATE, -1),
		VINE(Material.VINE, -1),
		MOSSY_COBBLESTONE(Material.MOSSY_COBBLESTONE, -1),
		MELON_STEM(Material.MELON_STEM, -1),
		PUMPKIN_STEM(Material.PUMPKIN_STEM, -1),
		RED_SANDSTONE_STAIRS(Material.RED_SANDSTONE_STAIRS, -1),
		DARK_OAK_STAIRS(Material.DARK_OAK_STAIRS, -1),
		BIRCH_WOOD_STAIRS(Material.BIRCH_WOOD_STAIRS, -1),
		ACACIA_STAIRS(Material.ACACIA_STAIRS, -1),
		ENDER_STONE(Material.ENDER_STONE, -1),
		BRICK_STAIRS(Material.BRICK_STAIRS, -1),
		COBBLESTONE_STAIRS(Material.COBBLESTONE_STAIRS, -1),
		SANDSTONE_STAIRS(Material.SANDSTONE_STAIRS, -1),
		JUNGLE_WOOD_STAIRS(Material.JUNGLE_WOOD_STAIRS, -1),
		NETHER_BRICK_STAIRS(Material.NETHER_BRICK_STAIRS, -1),
		QUARTZ_STAIRS(Material.QUARTZ_STAIRS, -1),
		SMOOTH_STAIRS(Material.SMOOTH_STAIRS, -1),
		THIN_GLASS(Material.THIN_GLASS, -1),
		MYCEL(Material.MYCEL, -1),
		WHITE_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, 0),
		ORANGE_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, 1),
		MAGENTA_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, 2),
		LIGHT_BLUE_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, 3),
		YELLOW_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, 4),
		LIME_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, 5),
		PINK_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, 6),
		GREY_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, 7),
		LIGHT_GREY_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, 8),
		CYAN_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, 9),
		PURPLE_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, 10),
		BLUE_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, 11),
		BROWN_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, 12),
		GREEN_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, 13),
		RED_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, 14),
		BLACK_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, 15),
		STONE_MONSTER_EGG(Material.MONSTER_EGGS, 0),
		COBBLESTONE_MONSTER_EGG(Material.MONSTER_EGGS, 1),
		STONE_BRICK_MONSTER_EGG(Material.MONSTER_EGGS, 2),
		MOSSY_STONE_MONSTER_EGG(Material.MONSTER_EGGS, 3),
		CRACKED_STONE_MONSTER_EGG(Material.MONSTER_EGGS, 4),
		CHISELED_STONE_MONSTER_EGG(Material.MONSTER_EGGS, 5),
		SUNFLOWER(Material.DOUBLE_PLANT, 0),
		LILAC(Material.DOUBLE_PLANT, 1),
		DOUBLE_TALL_GRASS(Material.DOUBLE_PLANT, 2),
		LARGE_FURN(Material.DOUBLE_PLANT, 3),
		ROSE_BUSH(Material.DOUBLE_PLANT, 4),
		PEONY(Material.DOUBLE_PLANT, 5),
		SMOOTH_BRICK(Material.SMOOTH_BRICK, 0),
		MOSSY_STONE_BRICK(Material.SMOOTH_BRICK, 1),
		CRACKED_STONE_BRICK(Material.SMOOTH_BRICK, 2),
		CHISELED_STONE_BRICK(Material.SMOOTH_BRICK, 3),
		STONE_SLAB(Material.STEP, 0),
		SANDSTONE_SLAB(Material.STEP, 1),
		WOODEN_SLAB(Material.STEP, 2),
		COBBLESTONE_SLAB(Material.STEP, 3),
		BRICK_SLAB(Material.STEP, 4),
		STONE_BRICK_SLAB(Material.STEP, 5),
		STONE_SLAB_TOP(Material.STEP, 6),
		SANDSTONE_SLAB_TOP(Material.STEP, 7),
		WOODEN_SLAB_TOP(Material.STEP, 8),
		COBBLESTONE_SLAB_TOP(Material.STEP, 9),
		BRICK_SLAB_TOP(Material.STEP, 10),
		STONE_BRICK_SLAB_TOP(Material.STEP, 11),
		STONE_DOUBLE_SLAB(Material.DOUBLE_STEP, 0),
		SANDSTONE_DOUBLE_SLAB(Material.DOUBLE_STEP, 1),
		WOODEN_DOUBLE_SLAB(Material.DOUBLE_STEP, 2),
		COBBLESTONE_DOUBLE_SLAB(Material.DOUBLE_STEP, 3),
		BRICK_DOUBLE_SLAB(Material.DOUBLE_STEP, 4),
		STONE_DOUBLE_BRICK(Material.DOUBLE_STEP, 5),
		END_ROD(Material.END_ROD, -1),
		CHORUS_PLANT(Material.CHORUS_PLANT, -1),
		CHORUS_FLOWER(Material.CHORUS_FLOWER, -1),
		PURPUR_BLOCK(Material.PURPUR_BLOCK, -1),
		PURPUR_PILLAR(Material.PURPUR_PILLAR, -1),
		PURPUR_STAIRS(Material.PURPUR_STAIRS, -1),
		PURPUR_DOUBLE_SLAB(Material.PURPUR_DOUBLE_SLAB, -1),
		PURPUR_SLAB(Material.PURPUR_SLAB, -1),
		END_BRICKS(Material.END_BRICKS, -1),
		BEETROOTS(Material.BEETROOT_BLOCK, -1),
		GRASS_PATH(Material.GRASS_PATH, -1),
		END_GATEWAY(Material.END_GATEWAY, -1),
		REPEATING_COMMAND_BLOCK(Material.COMMAND_REPEATING, -1),
		CHAIN_COMMAND_BLOCK(Material.COMMAND_CHAIN, -1),
		FROSTED_ICE(Material.FROSTED_ICE, -1),
		STRUCTURE_BLOCK_SAVE(Material.STRUCTURE_BLOCK, 0),
		STRUCTURE_BLOCK_LOAD(Material.STRUCTURE_BLOCK, 1),
		STRUCTURE_BLOCK_CORNER(Material.STRUCTURE_BLOCK, 2),
		STRUCTURE_BLOCK_DATA(Material.STRUCTURE_BLOCK, 3),
		MAGMA_BLOCK(Material.MAGMA, -1),
		NETHER_WART_BLOCK(Material.NETHER_WART_BLOCK, -1),
		RED_NETHER_BRICK(Material.RED_NETHER_BRICK, -1),
		BONE_BLOCK(Material.BONE_BLOCK, -1),
		STRUCTURE_VOID(Material.STRUCTURE_VOID, -1),
		OBSERVER(Material.OBSERVER, -1),
		WHITE_GLAZED_TERRACOTTA(Material.WHITE_GLAZED_TERRACOTTA, -1),
		ORANGE_GLAZED_TERRACOTTA(Material.ORANGE_GLAZED_TERRACOTTA, -1),
		PINK_GLAZED_TERRACOTTA(Material.PINK_GLAZED_TERRACOTTA, -1),
		MAGENTA_GLAZED_TERRACOTTA(Material.MAGENTA_GLAZED_TERRACOTTA, -1),
		LIGHT_BLUE_GLAZED_TERRACOTTA(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, -1),
		YELLOW_GLAZED_TERRACOTTA(Material.YELLOW_GLAZED_TERRACOTTA, -1),
		LIME_GLAZED_TERRACOTTA(Material.LIME_GLAZED_TERRACOTTA, -1),
		GRAY_GLAZED_TERRACOTTA(Material.GRAY_GLAZED_TERRACOTTA, -1),
		GREEN_GLAZED_TERRACOTTA(Material.GREEN_GLAZED_TERRACOTTA, -1),
		SILVER_GLAZED_TERRACOTTA(Material.SILVER_GLAZED_TERRACOTTA, -1),
		CYAN_GLAZED_TERRACOTTA(Material.CYAN_GLAZED_TERRACOTTA, -1),
		PURPLE_GLAZED_TERRACOTTA(Material.PURPLE_GLAZED_TERRACOTTA, -1),
		BLUE_GLAZED_TERRACOTTA(Material.BLUE_GLAZED_TERRACOTTA, -1),
		BROWN_GLAZED_TERRACOTTA(Material.BROWN_GLAZED_TERRACOTTA, -1),
		RED_GLAZED_TERRACOTTA(Material.RED_GLAZED_TERRACOTTA, -1),
		BLACK_GLAZED_TERRACOTTA(Material.BLACK_GLAZED_TERRACOTTA, -1),
		WHITE_CONCRETE(Material.CONCRETE, 0),
		ORANGE_CONCRETE(Material.CONCRETE, 1),
		MAGENTA_CONCRETE(Material.CONCRETE, 2),
		LIGHT_BLUE_CONCRETE(Material.CONCRETE, 3),
		YELLOW_CONCRETE(Material.CONCRETE, 4),
		LIME_CONCRETE(Material.CONCRETE, 5),
		PINK_CONCRETE(Material.CONCRETE, 6),
		GRAY_CONCRETE(Material.CONCRETE, 7),
		LIGHT_GRAY_CONCRETE(Material.CONCRETE, 8),
		CYAN_CONCRETE(Material.CONCRETE, 9),
		PURPLE_CONCRETE(Material.CONCRETE, 10),
		BLUE_CONCRETE(Material.CONCRETE, 11),
		BROWN_CONCRETE(Material.CONCRETE, 12),
		GREEN_CONCRETE(Material.CONCRETE, 13),
		RED_CONCRETE(Material.CONCRETE, 14),
		BLACK_CONCRETE(Material.CONCRETE, 15),
		WHITE_CONCRETE_POWDER(Material.CONCRETE_POWDER, 0),
		ORANGE_CONCRETE_POWDER(Material.CONCRETE_POWDER, 1),
		MAGENTA_CONCRETE_POWDER(Material.CONCRETE_POWDER, 2),
		LIGHT_BLUE_CONCRETE_POWDER(Material.CONCRETE_POWDER, 3),
		YELLOW_CONCRETE_POWDER(Material.CONCRETE_POWDER, 4),
		LIME_CONCRETE_POWDER(Material.CONCRETE_POWDER, 5),
		PINK_CONCRETE_POWDER(Material.CONCRETE_POWDER, 6),
		GRAY_CONCRETE_POWDER(Material.CONCRETE_POWDER, 7),
		LIGHT_GRAY_CONCRETE_POWDER(Material.CONCRETE_POWDER, 8),
		CYAN_CONCRETE_POWDER(Material.CONCRETE_POWDER, 9),
		PURPLE_CONCRETE_POWDER(Material.CONCRETE_POWDER, 10),
		BLUE_CONCRETE_POWDER(Material.CONCRETE_POWDER, 11),
		BROWN_CONCRETE_POWDER(Material.CONCRETE_POWDER, 12),
		GREEN_CONCRETE_POWDER(Material.CONCRETE_POWDER, 13),
		RED_CONCRETE_POWDER(Material.CONCRETE_POWDER, 14),
		BLACK_CONCRETE_POWDER(Material.CONCRETE_POWDER, 15),
		WHITE_SHULKER_BOX(Material.WHITE_SHULKER_BOX, -1),
		ORANGE_SHULKER_BOX(Material.ORANGE_SHULKER_BOX, -1),
		MAGENTA_SHULKER_BOX(Material.MAGENTA_SHULKER_BOX, -1),
		LIGHT_BLUE_SHULKER_BOX(Material.LIGHT_BLUE_SHULKER_BOX, -1),
		YELLOW_SHULKER_BOX(Material.YELLOW_SHULKER_BOX, -1),
		LIME_SHULKER_BOX(Material.LIME_SHULKER_BOX, -1),
		PINK_SHULKER_BOX(Material.PINK_SHULKER_BOX, -1),
		GRAY_SHULKER_BOX(Material.GRAY_SHULKER_BOX, -1),
		SILVER_SHULKER_BOX(Material.SILVER_SHULKER_BOX, -1),
		CYAN_SHULKER_BOX(Material.CYAN_SHULKER_BOX, -1),
		PURPLE_SHULKER_BOX(Material.PURPLE_SHULKER_BOX, -1),
		BLUE_SHULKER_BOX(Material.BLUE_SHULKER_BOX, -1),
		BROWN_SHULKER_BOX(Material.BROWN_SHULKER_BOX, -1),
		GREEN_SHULKER_BOX(Material.GREEN_SHULKER_BOX, -1),
		RED_SHULKER_BOX(Material.RED_SHULKER_BOX, -1),
		BLACK_SHULKER_BOX(Material.BLACK_SHULKER_BOX, -1);

	Material MATERIAL;
	byte DATA;

	private MaterialAndData(Material material, int dataNumber) {
		MATERIAL = material;
		DATA = (byte) dataNumber;
	}

	public Material getMaterial() {
		return MATERIAL;
	}

	public byte getData() {
		return DATA;
	}

	public static Set<MaterialItem> getAllBlocks() {
		List<MaterialItem> set = new ArrayList<MaterialItem>();
		for (Material material : Material.values()) {
			set.addAll(getAllBlocks(material));
		}
		return new HashSet<MaterialItem>(set);
	}

	public static Set<MaterialItem> getAllBlocks(Material material) {
		List<MaterialItem> list = new ArrayList<MaterialItem>();
		for (MaterialAndData data : values()) {
			if (data.getMaterial().equals(material)) {
				if (data.getMaterial().isBlock()) {
					list.add(new MaterialItem(data.getMaterial(), data.getData()));
				}
			}
		}
		return new HashSet<MaterialItem>(list);
	}

	public static Set<MaterialItem> getAllMaterials(Material material) {
		List<MaterialItem> list = new ArrayList<MaterialItem>();
		for (MaterialAndData data : values()) {
			if (data.getMaterial().equals(material)) {
				list.add(new MaterialItem(data.getMaterial(), data.getData()));
			}
		}
		return new HashSet<MaterialItem>(list);
	}

	public static Set<MaterialItem> getAllMaterials() {
		List<MaterialItem> set = new ArrayList<MaterialItem>();
		for (Material material : Material.values()) {
			set.addAll(getAllMaterials(material));
		}
		return new HashSet<MaterialItem>(set);
	}

}

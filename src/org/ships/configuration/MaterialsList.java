package org.ships.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.ships.block.configuration.MaterialConfiguration;
import org.ships.block.configuration.MovementInstruction;
import org.ships.plugin.Ships;

public class MaterialsList {

	List<MaterialConfiguration> configurationOptions = new ArrayList<>();
	static MaterialsList LIST;

	public MaterialsList() {
		File file = new File("plugins/Ships/Configuration/Materials.yml");
		if (!file.exists()) {
			// default
			// TODO
			//planks
			configurationOptions.add(new MaterialConfiguration(Material.ACACIA_PLANKS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BIRCH_PLANKS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.DARK_OAK_PLANKS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.JUNGLE_PLANKS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.OAK_PLANKS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.SPRUCE_PLANKS, MovementInstruction.MATERIAL));
			//logs
			configurationOptions.add(new MaterialConfiguration(Material.ACACIA_LOG, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BIRCH_LOG, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.DARK_OAK_LOG, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.JUNGLE_LOG, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.OAK_LOG, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.SPRUCE_LOG, MovementInstruction.MATERIAL));
			//glass
			configurationOptions.add(new MaterialConfiguration(Material.GLASS, MovementInstruction.MATERIAL));
			//lapis
			configurationOptions.add(new MaterialConfiguration(Material.LAPIS_BLOCK, MovementInstruction.MATERIAL));
			//dispenser
			configurationOptions.add(new MaterialConfiguration(Material.DISPENSER, MovementInstruction.MATERIAL));
			//noteblock
			configurationOptions.add(new MaterialConfiguration(Material.NOTE_BLOCK, MovementInstruction.MATERIAL));
			//bed
			configurationOptions.add(new MaterialConfiguration(Material.BLACK_BED, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BLUE_BED, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BROWN_BED, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.CYAN_BED, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.GRAY_BED, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.GREEN_BED, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIGHT_BLUE_BED, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIGHT_GRAY_BED, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIME_BED, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.MAGENTA_BED, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.ORANGE_BED, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PINK_BED, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PURPLE_BED, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.RED_BED, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.WHITE_BED, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.YELLOW_BED, MovementInstruction.MATERIAL));
			//rail
			configurationOptions.add(new MaterialConfiguration(Material.POWERED_RAIL, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.RAIL, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.ACTIVATOR_RAIL, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.DETECTOR_RAIL, MovementInstruction.MATERIAL));
			//piston
			configurationOptions.add(new MaterialConfiguration(Material.PISTON, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PISTON_HEAD, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.STICKY_PISTON, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.MOVING_PISTON, MovementInstruction.MATERIAL));
			//wool
			configurationOptions.add(new MaterialConfiguration(Material.BLACK_WOOL, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BLUE_WOOL, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BROWN_WOOL, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.CYAN_WOOL, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.GRAY_WOOL, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.GREEN_WOOL, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIME_WOOL, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.MAGENTA_WOOL, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.ORANGE_WOOL, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PINK_WOOL, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PURPLE_WOOL, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.RED_WOOL, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.WHITE_WOOL, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.YELLOW_WOOL, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIGHT_BLUE_WOOL, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIGHT_GRAY_WOOL, MovementInstruction.MATERIAL));
			//ore full blocks
			configurationOptions.add(new MaterialConfiguration(Material.GOLD_BLOCK, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.IRON_BLOCK, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.DIAMOND_BLOCK, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.EMERALD_BLOCK, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.QUARTZ_BLOCK, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.COAL_BLOCK, MovementInstruction.MATERIAL));
			//slabs
			configurationOptions.add(new MaterialConfiguration(Material.STONE_SLAB, MovementInstruction.MATERIAL));
			//bricks
			configurationOptions.add(new MaterialConfiguration(Material.BRICKS, MovementInstruction.MATERIAL));
			//tnt
			configurationOptions.add(new MaterialConfiguration(Material.TNT, MovementInstruction.MATERIAL));
			//bookshelf
			configurationOptions.add(new MaterialConfiguration(Material.BOOKSHELF, MovementInstruction.MATERIAL));
			//torch
			configurationOptions.add(new MaterialConfiguration(Material.TORCH, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.REDSTONE_TORCH, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.REDSTONE_WALL_TORCH, MovementInstruction.MATERIAL));
			//fire
			configurationOptions.add(new MaterialConfiguration(Material.FIRE, MovementInstruction.MATERIAL));
			//stairs
			configurationOptions.add(new MaterialConfiguration(Material.ACACIA_STAIRS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BIRCH_STAIRS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BRICK_STAIRS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.COBBLESTONE_STAIRS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.DARK_OAK_STAIRS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.JUNGLE_STAIRS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.NETHER_BRICK_STAIRS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.OAK_STAIRS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PURPUR_STAIRS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.QUARTZ_STAIRS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.RED_SANDSTONE_STAIRS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.SANDSTONE_STAIRS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.SPRUCE_STAIRS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.STONE_BRICK_STAIRS, MovementInstruction.MATERIAL));
			//chest
			configurationOptions.add(new MaterialConfiguration(Material.CHEST, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.TRAPPED_CHEST, MovementInstruction.MATERIAL));
			//redstone
			configurationOptions.add(new MaterialConfiguration(Material.REDSTONE_WIRE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.REDSTONE_BLOCK, MovementInstruction.MATERIAL));
			//crafting table
			configurationOptions.add(new MaterialConfiguration(Material.CRAFTING_TABLE, MovementInstruction.MATERIAL));
			//furnace
			configurationOptions.add(new MaterialConfiguration(Material.FURNACE, MovementInstruction.MATERIAL));
			//sign
			configurationOptions.add(new MaterialConfiguration(Material.SIGN, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.WALL_SIGN, MovementInstruction.MATERIAL));
			//door
			configurationOptions.add(new MaterialConfiguration(Material.IRON_DOOR, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.ACACIA_DOOR, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BIRCH_DOOR, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.JUNGLE_DOOR, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.OAK_DOOR, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.SPRUCE_DOOR, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.DARK_OAK_DOOR, MovementInstruction.MATERIAL));
			//lever
			configurationOptions.add(new MaterialConfiguration(Material.LEVER, MovementInstruction.MATERIAL));
			//pressure plate
			configurationOptions.add(new MaterialConfiguration(Material.ACACIA_PRESSURE_PLATE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BIRCH_PRESSURE_PLATE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.JUNGLE_PRESSURE_PLATE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.OAK_PRESSURE_PLATE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.SPRUCE_PRESSURE_PLATE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.STONE_PRESSURE_PLATE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.DARK_OAK_PRESSURE_PLATE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.HEAVY_WEIGHTED_PRESSURE_PLATE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIGHT_WEIGHTED_PRESSURE_PLATE, MovementInstruction.MATERIAL));
			//button
			configurationOptions.add(new MaterialConfiguration(Material.ACACIA_BUTTON, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BIRCH_BUTTON, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.JUNGLE_BUTTON, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.OAK_BUTTON, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.SPRUCE_BUTTON, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.STONE_BUTTON, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.DARK_OAK_BUTTON, MovementInstruction.MATERIAL));		
			//jukebox
			configurationOptions.add(new MaterialConfiguration(Material.JUKEBOX, MovementInstruction.MATERIAL));
			//fence
			configurationOptions.add(new MaterialConfiguration(Material.ACACIA_FENCE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BIRCH_FENCE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.JUNGLE_FENCE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.OAK_FENCE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.SPRUCE_FENCE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.DARK_OAK_FENCE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.NETHER_BRICK_FENCE, MovementInstruction.MATERIAL));
			///pumpkin
			configurationOptions.add(new MaterialConfiguration(Material.PUMPKIN, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.JACK_O_LANTERN, MovementInstruction.MATERIAL));
			//netherrack
			configurationOptions.add(new MaterialConfiguration(Material.NETHERRACK, MovementInstruction.MATERIAL));
			//glowstone
			configurationOptions.add(new MaterialConfiguration(Material.GLOWSTONE, MovementInstruction.MATERIAL));
			//cake
			configurationOptions.add(new MaterialConfiguration(Material.CAKE, MovementInstruction.MATERIAL));
			//redstone repeater
			configurationOptions.add(new MaterialConfiguration(Material.REPEATER, MovementInstruction.MATERIAL));
			//stained glass
			configurationOptions.add(new MaterialConfiguration(Material.BLACK_STAINED_GLASS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BLUE_STAINED_GLASS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BROWN_STAINED_GLASS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.CYAN_STAINED_GLASS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.GRAY_STAINED_GLASS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.GREEN_STAINED_GLASS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIGHT_BLUE_STAINED_GLASS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIGHT_GRAY_STAINED_GLASS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.MAGENTA_STAINED_GLASS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.ORANGE_STAINED_GLASS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PINK_STAINED_GLASS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PURPLE_STAINED_GLASS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.RED_STAINED_GLASS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.WHITE_STAINED_GLASS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.YELLOW_STAINED_GLASS, MovementInstruction.MATERIAL));			
			//trapdoor
			configurationOptions.add(new MaterialConfiguration(Material.ACACIA_TRAPDOOR, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BIRCH_TRAPDOOR, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.IRON_TRAPDOOR, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.JUNGLE_TRAPDOOR, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.OAK_TRAPDOOR, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.SPRUCE_TRAPDOOR, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.DARK_OAK_TRAPDOOR, MovementInstruction.MATERIAL));
			//iron bars
			configurationOptions.add(new MaterialConfiguration(Material.IRON_BARS, MovementInstruction.MATERIAL));
			//glass pane
			configurationOptions.add(new MaterialConfiguration(Material.BLACK_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BLUE_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BROWN_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.CYAN_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.GRAY_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.GREEN_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIGHT_BLUE_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIGHT_GRAY_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.MAGENTA_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.ORANGE_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PINK_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PURPLE_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.RED_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.WHITE_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.YELLOW_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));	
			//fence gate
			configurationOptions.add(new MaterialConfiguration(Material.ACACIA_FENCE_GATE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BIRCH_FENCE_GATE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.JUNGLE_FENCE_GATE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.OAK_FENCE_GATE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.SPRUCE_FENCE_GATE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.DARK_OAK_FENCE_GATE, MovementInstruction.MATERIAL));
			//enchantment table
			configurationOptions.add(new MaterialConfiguration(Material.ENCHANTING_TABLE, MovementInstruction.MATERIAL));
			//cauldron
			configurationOptions.add(new MaterialConfiguration(Material.CAULDRON, MovementInstruction.MATERIAL));
			//portal
			configurationOptions.add(new MaterialConfiguration(Material.END_PORTAL, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.END_PORTAL_FRAME, MovementInstruction.MATERIAL));
			//redstone lamp
			configurationOptions.add(new MaterialConfiguration(Material.REDSTONE_LAMP, MovementInstruction.MATERIAL));
			//ender chest
			configurationOptions.add(new MaterialConfiguration(Material.ENDER_CHEST, MovementInstruction.MATERIAL));
			//tripwire
			configurationOptions.add(new MaterialConfiguration(Material.TRIPWIRE, MovementInstruction.MATERIAL));			
			configurationOptions.add(new MaterialConfiguration(Material.TRIPWIRE_HOOK, MovementInstruction.MATERIAL));
			//command block
			configurationOptions.add(new MaterialConfiguration(Material.COMMAND_BLOCK, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.CHAIN_COMMAND_BLOCK, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.REPEATING_COMMAND_BLOCK, MovementInstruction.MATERIAL));
			//beacon
			configurationOptions.add(new MaterialConfiguration(Material.BEACON, MovementInstruction.MATERIAL));
			//wall
			configurationOptions.add(new MaterialConfiguration(Material.COBBLESTONE_WALL, MovementInstruction.MATERIAL));
			//flower pot
			configurationOptions.add(new MaterialConfiguration(Material.FLOWER_POT, MovementInstruction.MATERIAL));
			//mob head
			configurationOptions.add(new MaterialConfiguration(Material.CREEPER_HEAD, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.CREEPER_WALL_HEAD, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.DRAGON_HEAD, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.DRAGON_WALL_HEAD, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PLAYER_HEAD, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PLAYER_WALL_HEAD, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.ZOMBIE_HEAD, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.ZOMBIE_WALL_HEAD, MovementInstruction.MATERIAL));
			//anvil
			configurationOptions.add(new MaterialConfiguration(Material.ANVIL, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.CHIPPED_ANVIL, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.DAMAGED_ANVIL, MovementInstruction.MATERIAL));
			//comparator
			configurationOptions.add(new MaterialConfiguration(Material.COMPARATOR, MovementInstruction.MATERIAL));
			//daylight sensor
			configurationOptions.add(new MaterialConfiguration(Material.DAYLIGHT_DETECTOR, MovementInstruction.MATERIAL));
			//hopper
			configurationOptions.add(new MaterialConfiguration(Material.HOPPER, MovementInstruction.MATERIAL));
			//quartz
			configurationOptions.add(new MaterialConfiguration(Material.QUARTZ_PILLAR, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.CHISELED_QUARTZ_BLOCK, MovementInstruction.MATERIAL));
			//dropper
			configurationOptions.add(new MaterialConfiguration(Material.DROPPER, MovementInstruction.MATERIAL));
			//hardened clay
			//configurationOptions.add(new MaterialConfiguration(Material., MovementInstruction.MATERIAL));
			//slime
			configurationOptions.add(new MaterialConfiguration(Material.SLIME_BLOCK, MovementInstruction.MATERIAL));
			//barrier
			configurationOptions.add(new MaterialConfiguration(Material.BARRIER, MovementInstruction.MATERIAL));
			//prismarine
			configurationOptions.add(new MaterialConfiguration(Material.PRISMARINE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PRISMARINE_BRICKS, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.DARK_PRISMARINE, MovementInstruction.MATERIAL));
			//sea lantern
			configurationOptions.add(new MaterialConfiguration(Material.SEA_LANTERN, MovementInstruction.MATERIAL));
			//hay bale
			configurationOptions.add(new MaterialConfiguration(Material.HAY_BLOCK, MovementInstruction.MATERIAL));
			//carpet
			configurationOptions.add(new MaterialConfiguration(Material.BLACK_CARPET, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BLUE_CARPET, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BROWN_CARPET, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.CYAN_CARPET, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.GRAY_CARPET, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.GREEN_CARPET, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIGHT_BLUE_CARPET, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIGHT_GRAY_CARPET, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.MAGENTA_CARPET, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.ORANGE_CARPET, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PINK_CARPET, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PURPLE_CARPET, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.RED_CARPET, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.WHITE_CARPET, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.YELLOW_CARPET, MovementInstruction.MATERIAL));
			//banner
			configurationOptions.add(new MaterialConfiguration(Material.BLACK_BANNER, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BLACK_WALL_BANNER, MovementInstruction.MATERIAL));
			//end rod
			configurationOptions.add(new MaterialConfiguration(Material.END_ROD, MovementInstruction.MATERIAL));
			//purpur
			configurationOptions.add(new MaterialConfiguration(Material.PURPUR_BLOCK, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PURPUR_PILLAR, MovementInstruction.MATERIAL));
			//shulker box
			configurationOptions.add(new MaterialConfiguration(Material.BLACK_SHULKER_BOX, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BLUE_SHULKER_BOX, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BROWN_SHULKER_BOX, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.CYAN_SHULKER_BOX, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.GRAY_SHULKER_BOX, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.GREEN_SHULKER_BOX, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIGHT_BLUE_SHULKER_BOX, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIGHT_GRAY_SHULKER_BOX, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIME_SHULKER_BOX, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.MAGENTA_SHULKER_BOX, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.ORANGE_SHULKER_BOX, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PINK_SHULKER_BOX, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PURPLE_SHULKER_BOX, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.RED_SHULKER_BOX, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.WHITE_SHULKER_BOX, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.YELLOW_SHULKER_BOX, MovementInstruction.MATERIAL));
			//terracotta
			configurationOptions.add(new MaterialConfiguration(Material.TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BLACK_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BLACK_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BLUE_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BLUE_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BROWN_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BROWN_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.CYAN_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.CYAN_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.GRAY_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.GRAY_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.GREEN_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.GREEN_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIGHT_BLUE_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIGHT_GRAY_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIME_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIME_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.MAGENTA_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.MAGENTA_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.ORANGE_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.ORANGE_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PINK_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PINK_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PURPLE_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PURPLE_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.RED_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.RED_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.WHITE_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.WHITE_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.YELLOW_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.YELLOW_TERRACOTTA, MovementInstruction.MATERIAL));
			//concreate
			configurationOptions.add(new MaterialConfiguration(Material.BLACK_CONCRETE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BLUE_CONCRETE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.BROWN_CONCRETE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.CYAN_CONCRETE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.GRAY_CONCRETE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.GREEN_CONCRETE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIGHT_BLUE_CONCRETE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIGHT_GRAY_CONCRETE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.LIME_CONCRETE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.MAGENTA_CONCRETE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.ORANGE_CONCRETE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PINK_CONCRETE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.PURPLE_CONCRETE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.RED_CONCRETE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.WHITE_CONCRETE, MovementInstruction.MATERIAL));
			configurationOptions.add(new MaterialConfiguration(Material.YELLOW_CONCRETE, MovementInstruction.MATERIAL));
			//concrete powder
			configurationOptions.add(new MaterialConfiguration(Material.BLACK_CONCRETE_POWDER, MovementInstruction.MATERIAL));
			
			//RAM
			//sapling
			configurationOptions.add(new MaterialConfiguration(Material.ACACIA_SAPLING, MovementInstruction.RAM));
			//TODO
			
			//leaves
			configurationOptions.add(new MaterialConfiguration(Material.ACACIA_LEAVES, MovementInstruction.RAM));
			
			//cobweb
			configurationOptions.add(new MaterialConfiguration(Material.COBWEB, MovementInstruction.RAM));
			//grass
			configurationOptions.add(new MaterialConfiguration(Material.TALL_GRASS, MovementInstruction.RAM));
			configurationOptions.add(new MaterialConfiguration(Material.FERN, MovementInstruction.RAM));
			//dead bush
			configurationOptions.add(new MaterialConfiguration(Material.DEAD_BUSH, MovementInstruction.RAM));
			//flower
			configurationOptions.add(new MaterialConfiguration(Material.POPPY, MovementInstruction.RAM));
			configurationOptions.add(new MaterialConfiguration(Material.DANDELION_YELLOW, MovementInstruction.RAM));
			configurationOptions.add(new MaterialConfiguration(Material.BLUE_ORCHID, MovementInstruction.RAM));
			configurationOptions.add(new MaterialConfiguration(Material.ALLIUM, MovementInstruction.RAM));
			configurationOptions.add(new MaterialConfiguration(Material.AZURE_BLUET, MovementInstruction.RAM));
			configurationOptions.add(new MaterialConfiguration(Material.RED_TULIP, MovementInstruction.RAM));
			configurationOptions.add(new MaterialConfiguration(Material.ORANGE_TULIP, MovementInstruction.RAM));
			configurationOptions.add(new MaterialConfiguration(Material.WHITE_TULIP, MovementInstruction.RAM));
			configurationOptions.add(new MaterialConfiguration(Material.PINK_TULIP, MovementInstruction.RAM));
			configurationOptions.add(new MaterialConfiguration(Material.OXEYE_DAISY, MovementInstruction.RAM));
			//mushroom
			configurationOptions.add(new MaterialConfiguration(Material.BROWN_MUSHROOM_BLOCK, MovementInstruction.RAM));
			configurationOptions.add(new MaterialConfiguration(Material.RED_MUSHROOM_BLOCK, MovementInstruction.RAM));
			//wheat
			configurationOptions.add(new MaterialConfiguration(Material.WHEAT, MovementInstruction.RAM));
			//cactus
			configurationOptions.add(new MaterialConfiguration(Material.CACTUS, MovementInstruction.RAM));
			//canes
			configurationOptions.add(new MaterialConfiguration(Material.SUGAR_CANE, MovementInstruction.RAM));
			//stems
			configurationOptions.add(new MaterialConfiguration(Material.ATTACHED_MELON_STEM, MovementInstruction.RAM));
			configurationOptions.add(new MaterialConfiguration(Material.ATTACHED_PUMPKIN_STEM, MovementInstruction.RAM));
			configurationOptions.add(new MaterialConfiguration(Material.MELON_STEM, MovementInstruction.RAM));
			configurationOptions.add(new MaterialConfiguration(Material.MUSHROOM_STEM, MovementInstruction.RAM));
			configurationOptions.add(new MaterialConfiguration(Material.PUMPKIN_STEM, MovementInstruction.RAM));
			//vines
			configurationOptions.add(new MaterialConfiguration(Material.VINE, MovementInstruction.RAM));
			//lily pad
			configurationOptions.add(new MaterialConfiguration(Material.LILY_PAD, MovementInstruction.RAM));
			//wart
			configurationOptions.add(new MaterialConfiguration(Material.NETHER_WART_BLOCK, MovementInstruction.RAM));
			//food
			configurationOptions.add(new MaterialConfiguration(Material.CARROTS, MovementInstruction.RAM));
			configurationOptions.add(new MaterialConfiguration(Material.POTATOES, MovementInstruction.RAM));
			//large plants
			configurationOptions.add(new MaterialConfiguration(Material.LILAC, MovementInstruction.RAM));
			configurationOptions.add(new MaterialConfiguration(Material.GRASS_BLOCK, MovementInstruction.RAM));
			configurationOptions.add(new MaterialConfiguration(Material.LARGE_FERN, MovementInstruction.RAM));
			configurationOptions.add(new MaterialConfiguration(Material.ROSE_BUSH, MovementInstruction.RAM));
			configurationOptions.add(new MaterialConfiguration(Material.PEONY, MovementInstruction.RAM));
			LIST = this;
		} else {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			for (Material material : Material.values()) {
				String insS = config.getString("Materials." + material.name() + ".Instruction");
				if(insS == null) {
					Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "error reading 'Instruction' for " + material.name());
					configurationOptions.add(new MaterialConfiguration(material));
					continue;
				}
				MovementInstruction ins = MovementInstruction.valueOf(insS);
				if(ins == null) {
					Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "error reading 'Instruction' for " + material.name());
					configurationOptions.add(new MaterialConfiguration(material));
					continue;
				}
				configurationOptions.add(new MaterialConfiguration(material, ins));
			}
		}
		LIST = this;
	}

	private boolean needsUpdating() {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		String mcVersion = config.getString("MCVersion");
		if (mcVersion == null) {
			Config.getConfig().updateCheck();
			return true;
		}
		File file = new File("plugins/Ships/Configuration/Materials.yml");
		if(file.length() == 0) {
			return true;
		}
		int[] knownVersion = Ships.convertVersion(mcVersion);
		int[] latest = Ships.convertVersion(Ships.getMinecraftVersion());	
		if (Ships.compare(knownVersion, latest) == Ships.COMPARE_SECOND_VALUE_IS_GREATER) {
			return true;
		}
		return false;
	}

	public void save() {
		File file = new File("plugins/Ships/Configuration/Materials.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		List<Material> failedMaterials = new ArrayList<Material>();
		boolean check = false;
		for(MaterialConfiguration material : configurationOptions) {
			config.set("Materials." + material.getMaterial().name() + ".Instruction", material.getInstruction().name());
		}
		
		
		/*for (Material material : Material.values()) {
			if(!material.isBlock()) {
				continue;
			}
			Set<MaterialItem> materials = MaterialAndData.getAllBlocks(material);
			if (materials != null) {
				if (materials.size() == 0) {
					failedMaterials.add(new MaterialConfiguration(material);
					if (needsUpdating()) {
						check = true;
						config.set("Materials." + material.name() + ".dataValue_-1", 0);
						Bukkit.getConsoleSender().sendMessage(
								Ships.runShipsMessage(material.name() + " has been updated in materials list", false));
					}
				} else {
					if (needsUpdating()) {
						check = true;
						for (MaterialItem data : materials) {
							if (contains(material, true)) {
								if (config
										.getInt("Materials." + material.name() + ".dataValue_" + data.getData()) == 0) {
									config.set("Materials." + material.name() + ".dataValue_" + data.getData(), 1);
								}
							} else if (contains(material, false)) {
								if (config
										.getInt("Materials." + material.name() + ".dataValue_" + data.getData()) == 0) {
									config.set("Materials." + material.name() + ".dataValue_" + data.getData(), 2);
								}
							} else {
								config.set("Materials." + material.name() + ".dataValue_" + data.getData(), 0);
							}
						}
					}
				}
			}
		}*/
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (check) {
			Bukkit.getConsoleSender()
					.sendMessage(Ships.runShipsMessage(
							"A new minecraft version found. \n Attempting to update the materials list with the new blocks",
							false));
			YamlConfiguration config2 = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
			config2.set("MCVersion", Ships.getMinecraftVersion());
			try {
				config2.save(Config.getConfig().getFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (failedMaterials.size() != 0) {
			Bukkit.getConsoleSender()
					.sendMessage(Ships.runShipsMessage(
							"Ships currently does not support " + failedMaterials + "(found " + failedMaterials.size()
									+ " blocks). \n These blocks are in the materials list however they maynot work correctly",
							true));
			Bukkit.getConsoleSender()
					.sendMessage(Ships.runShipsMessage(
							"check http://dev.bukkit.org/bukkit-plugins/ships to see if you are using the latest version. This version is "
									+ Config.getConfig().getLatestVersionString(),
							false));
		}
	}
	
	public boolean contains(Material material, MovementInstruction instruction) {
		return configurationOptions.stream().anyMatch(e -> e.getMaterial().equals(material) && e.getInstruction().equals(instruction));
	}

	public Set<Material> getMaterials(MovementInstruction instruction) {
		List<Material> list = new ArrayList<>();
		configurationOptions.stream().filter(e -> e.getInstruction().equals(instruction)).forEach(e -> list.add(e.getMaterial()));
		return new HashSet<>(list);
	}

	public static MaterialsList getMaterialsList() {
		return LIST;
	}

}

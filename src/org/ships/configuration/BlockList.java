package org.ships.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.ships.block.configuration.MaterialConfiguration;
import org.ships.block.configuration.MovementInstruction;

public class BlockList {
	
	protected final Set<MaterialConfiguration> configuration = new HashSet<>();
	
	public static final File BLOCK_LIST_FILE = new File("plugins/Ships/Configuration/BlockList.yml");
	public static final YamlConfiguration BLOCK_LIST_YAML = YamlConfiguration.loadConfiguration(BLOCK_LIST_FILE);
	public static final BlockList BLOCK_LIST = new BlockList();
	
	public void set(YamlConfiguration yaml, MaterialConfiguration config, boolean safe) {
		String path = "Blocks." + config.getMaterial().name() + ".";
		if(!safe || yaml.getString(path + "Instruction") == null) {
			yaml.set(path + "Instruction", config.getInstruction().name());
		}
		setOrReplace(config);
	}
		
	public Set<MaterialConfiguration> getDefaults(){
		Set<MaterialConfiguration> set = getDefaultsWithoutNormal();
		getMaterials().stream().filter(m -> !set.stream().anyMatch(s -> s.getMaterial().equals(m))).forEach(m -> set.add(new MaterialConfiguration(m)));
		return set;	
	}
	
	public Set<MaterialConfiguration> getCurrent(){
		Set<MaterialConfiguration> set = getCurrentWithoutNormal();
		getMaterials().stream().filter(m -> !set.stream().anyMatch(s -> s.getMaterial().equals(m))).forEach(m -> set.add(new MaterialConfiguration(m)));
		return set;
	}
		
	public Set<MaterialConfiguration> getCurrentWith(MovementInstruction ins){
		return getCurrent().stream().filter(e -> e.getInstruction().equals(ins)).collect(Collectors.toSet());
	}
	
	public MaterialConfiguration getCurrentWith(Material material){
		return getCurrent().stream().filter(e -> e.getMaterial().equals(material)).findFirst().get();
	}
	
	public void updateAll(File file, YamlConfiguration yaml, boolean safe) {
		this.configuration.stream().forEach(c -> set(yaml, c, safe));
		try {
			yaml.save(file);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		this.configuration.clear();
		load(yaml);
	}
	
	public void load(YamlConfiguration yaml) {
		this.configuration.clear();
		getMaterials().stream().forEach(m -> {
			try {
			Optional<MaterialConfiguration> config = read(yaml, m);
			if(!config.isPresent()) {
				return;
			}
			this.configuration.add(config.get());
			}catch(IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	private Optional<MaterialConfiguration> read(YamlConfiguration yaml, Material material) throws IOException{
		MaterialConfiguration config = new MaterialConfiguration(material);
		String path = "Blocks." + config.getMaterial().name() + ".";
		String instruction = yaml.getString(path);
		if(instruction == null) {
			return Optional.empty();
		}
		MovementInstruction instruction1 = MovementInstruction.valueOf(instruction);
		if(instruction1 == null) {
			String ins = null;
			for(MovementInstruction ins1 : MovementInstruction.values()) {
				if(ins == null) {
					ins = ins1.name();
				}else {
					ins = ins + ", " + ins1.name();
				}
			}
			throw new IOException("BlockList Error: Material: " + material.name() + ": " + instruction + " is not a movement instruction. The following are ");
		}
		config.setInstruction(instruction1);
		return Optional.of(config);
	}
	
	@SuppressWarnings("deprecation")
	private Set<Material> getMaterials(){
		return Arrays.asList(Material.values()).stream().filter(m -> !m.isLegacy()).filter(m -> m.isBlock()).collect(Collectors.toSet());
	}
	
	private void setOrReplace(MaterialConfiguration config) {
		Optional<MaterialConfiguration> opConfig = getCurrentWith(config.getMaterial());
		if(opConfig.isPresent()) {
			this.configuration.remove(opConfig.get());
		}
		this.configuration.add(config);
	}
	
	private Set<MaterialConfiguration> getCurrentWithoutNormal(){
		return new HashSet<>(this.configuration);
	}
	
	private Set<MaterialConfiguration> getDefaultsWithoutNormal(){
		HashSet<MaterialConfiguration> hash = new HashSet<>();
		//planks
		hash.add(new MaterialConfiguration(Material.ACACIA_PLANKS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BIRCH_PLANKS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.DARK_OAK_PLANKS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.JUNGLE_PLANKS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.OAK_PLANKS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.SPRUCE_PLANKS, MovementInstruction.MATERIAL));
		//logs
		hash.add(new MaterialConfiguration(Material.ACACIA_LOG, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BIRCH_LOG, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.DARK_OAK_LOG, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.JUNGLE_LOG, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.OAK_LOG, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.SPRUCE_LOG, MovementInstruction.MATERIAL));
		//glass
		hash.add(new MaterialConfiguration(Material.GLASS, MovementInstruction.MATERIAL));
		//lapis
		hash.add(new MaterialConfiguration(Material.LAPIS_BLOCK, MovementInstruction.MATERIAL));
		//dispenser
		hash.add(new MaterialConfiguration(Material.DISPENSER, MovementInstruction.MATERIAL));
		//noteblock
		hash.add(new MaterialConfiguration(Material.NOTE_BLOCK, MovementInstruction.MATERIAL));
		//bed
		hash.add(new MaterialConfiguration(Material.BLACK_BED, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BLUE_BED, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BROWN_BED, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.CYAN_BED, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.GRAY_BED, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.GREEN_BED, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIGHT_BLUE_BED, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIGHT_GRAY_BED, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIME_BED, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.MAGENTA_BED, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.ORANGE_BED, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PINK_BED, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PURPLE_BED, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.RED_BED, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.WHITE_BED, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.YELLOW_BED, MovementInstruction.MATERIAL));
		//rail
		hash.add(new MaterialConfiguration(Material.POWERED_RAIL, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.RAIL, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.ACTIVATOR_RAIL, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.DETECTOR_RAIL, MovementInstruction.MATERIAL));
		//piston
		hash.add(new MaterialConfiguration(Material.PISTON, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PISTON_HEAD, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.STICKY_PISTON, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.MOVING_PISTON, MovementInstruction.MATERIAL));
		//wool
		hash.add(new MaterialConfiguration(Material.BLACK_WOOL, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BLUE_WOOL, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BROWN_WOOL, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.CYAN_WOOL, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.GRAY_WOOL, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.GREEN_WOOL, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIME_WOOL, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.MAGENTA_WOOL, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.ORANGE_WOOL, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PINK_WOOL, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PURPLE_WOOL, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.RED_WOOL, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.WHITE_WOOL, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.YELLOW_WOOL, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIGHT_BLUE_WOOL, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIGHT_GRAY_WOOL, MovementInstruction.MATERIAL));
		//ore full blocks
		hash.add(new MaterialConfiguration(Material.GOLD_BLOCK, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.IRON_BLOCK, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.DIAMOND_BLOCK, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.EMERALD_BLOCK, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.QUARTZ_BLOCK, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.COAL_BLOCK, MovementInstruction.MATERIAL));
		//slabs
		hash.add(new MaterialConfiguration(Material.STONE_SLAB, MovementInstruction.MATERIAL));
		//bricks
		hash.add(new MaterialConfiguration(Material.BRICKS, MovementInstruction.MATERIAL));
		//tnt
		hash.add(new MaterialConfiguration(Material.TNT, MovementInstruction.MATERIAL));
		//bookshelf
		hash.add(new MaterialConfiguration(Material.BOOKSHELF, MovementInstruction.MATERIAL));
		//torch
		hash.add(new MaterialConfiguration(Material.TORCH, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.REDSTONE_TORCH, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.REDSTONE_WALL_TORCH, MovementInstruction.MATERIAL));
		//fire
		hash.add(new MaterialConfiguration(Material.FIRE, MovementInstruction.MATERIAL));
		//stairs
		hash.add(new MaterialConfiguration(Material.ACACIA_STAIRS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BIRCH_STAIRS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BRICK_STAIRS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.COBBLESTONE_STAIRS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.DARK_OAK_STAIRS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.JUNGLE_STAIRS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.NETHER_BRICK_STAIRS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.OAK_STAIRS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PURPUR_STAIRS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.QUARTZ_STAIRS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.RED_SANDSTONE_STAIRS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.SANDSTONE_STAIRS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.SPRUCE_STAIRS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.STONE_BRICK_STAIRS, MovementInstruction.MATERIAL));
		//chest
		hash.add(new MaterialConfiguration(Material.CHEST, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.TRAPPED_CHEST, MovementInstruction.MATERIAL));
		//redstone
		hash.add(new MaterialConfiguration(Material.REDSTONE_WIRE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.REDSTONE_BLOCK, MovementInstruction.MATERIAL));
		//crafting table
		hash.add(new MaterialConfiguration(Material.CRAFTING_TABLE, MovementInstruction.MATERIAL));
		//furnace
		hash.add(new MaterialConfiguration(Material.FURNACE, MovementInstruction.MATERIAL));
		//sign
		hash.add(new MaterialConfiguration(Material.SIGN, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.WALL_SIGN, MovementInstruction.MATERIAL));
		//door
		hash.add(new MaterialConfiguration(Material.IRON_DOOR, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.ACACIA_DOOR, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BIRCH_DOOR, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.JUNGLE_DOOR, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.OAK_DOOR, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.SPRUCE_DOOR, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.DARK_OAK_DOOR, MovementInstruction.MATERIAL));
		//lever
		hash.add(new MaterialConfiguration(Material.LEVER, MovementInstruction.MATERIAL));
		//pressure plate
		hash.add(new MaterialConfiguration(Material.ACACIA_PRESSURE_PLATE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BIRCH_PRESSURE_PLATE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.JUNGLE_PRESSURE_PLATE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.OAK_PRESSURE_PLATE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.SPRUCE_PRESSURE_PLATE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.STONE_PRESSURE_PLATE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.DARK_OAK_PRESSURE_PLATE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.HEAVY_WEIGHTED_PRESSURE_PLATE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIGHT_WEIGHTED_PRESSURE_PLATE, MovementInstruction.MATERIAL));
		//button
		hash.add(new MaterialConfiguration(Material.ACACIA_BUTTON, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BIRCH_BUTTON, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.JUNGLE_BUTTON, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.OAK_BUTTON, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.SPRUCE_BUTTON, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.STONE_BUTTON, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.DARK_OAK_BUTTON, MovementInstruction.MATERIAL));		
		//jukebox
		hash.add(new MaterialConfiguration(Material.JUKEBOX, MovementInstruction.MATERIAL));
		//fence
		hash.add(new MaterialConfiguration(Material.ACACIA_FENCE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BIRCH_FENCE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.JUNGLE_FENCE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.OAK_FENCE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.SPRUCE_FENCE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.DARK_OAK_FENCE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.NETHER_BRICK_FENCE, MovementInstruction.MATERIAL));
		///pumpkin
		hash.add(new MaterialConfiguration(Material.PUMPKIN, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.JACK_O_LANTERN, MovementInstruction.MATERIAL));
		//netherrack
		hash.add(new MaterialConfiguration(Material.NETHERRACK, MovementInstruction.MATERIAL));
		//glowstone
		hash.add(new MaterialConfiguration(Material.GLOWSTONE, MovementInstruction.MATERIAL));
		//cake
		hash.add(new MaterialConfiguration(Material.CAKE, MovementInstruction.MATERIAL));
		//redstone repeater
		hash.add(new MaterialConfiguration(Material.REPEATER, MovementInstruction.MATERIAL));
		//stained glass
		hash.add(new MaterialConfiguration(Material.BLACK_STAINED_GLASS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BLUE_STAINED_GLASS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BROWN_STAINED_GLASS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.CYAN_STAINED_GLASS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.GRAY_STAINED_GLASS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.GREEN_STAINED_GLASS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIGHT_BLUE_STAINED_GLASS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIGHT_GRAY_STAINED_GLASS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.MAGENTA_STAINED_GLASS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.ORANGE_STAINED_GLASS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PINK_STAINED_GLASS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PURPLE_STAINED_GLASS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.RED_STAINED_GLASS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.WHITE_STAINED_GLASS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.YELLOW_STAINED_GLASS, MovementInstruction.MATERIAL));			
		//trapdoor
		hash.add(new MaterialConfiguration(Material.ACACIA_TRAPDOOR, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BIRCH_TRAPDOOR, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.IRON_TRAPDOOR, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.JUNGLE_TRAPDOOR, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.OAK_TRAPDOOR, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.SPRUCE_TRAPDOOR, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.DARK_OAK_TRAPDOOR, MovementInstruction.MATERIAL));
		//iron bars
		hash.add(new MaterialConfiguration(Material.IRON_BARS, MovementInstruction.MATERIAL));
		//glass pane
		hash.add(new MaterialConfiguration(Material.BLACK_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BLUE_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BROWN_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.CYAN_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.GRAY_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.GREEN_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIGHT_BLUE_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIGHT_GRAY_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.MAGENTA_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.ORANGE_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PINK_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PURPLE_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.RED_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.WHITE_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.YELLOW_STAINED_GLASS_PANE, MovementInstruction.MATERIAL));	
		//fence gate
		hash.add(new MaterialConfiguration(Material.ACACIA_FENCE_GATE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BIRCH_FENCE_GATE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.JUNGLE_FENCE_GATE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.OAK_FENCE_GATE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.SPRUCE_FENCE_GATE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.DARK_OAK_FENCE_GATE, MovementInstruction.MATERIAL));
		//enchantment table
		hash.add(new MaterialConfiguration(Material.ENCHANTING_TABLE, MovementInstruction.MATERIAL));
		//cauldron
		hash.add(new MaterialConfiguration(Material.CAULDRON, MovementInstruction.MATERIAL));
		//portal
		hash.add(new MaterialConfiguration(Material.END_PORTAL, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.END_PORTAL_FRAME, MovementInstruction.MATERIAL));
		//redstone lamp
		hash.add(new MaterialConfiguration(Material.REDSTONE_LAMP, MovementInstruction.MATERIAL));
		//ender chest
		hash.add(new MaterialConfiguration(Material.ENDER_CHEST, MovementInstruction.MATERIAL));
		//tripwire
		hash.add(new MaterialConfiguration(Material.TRIPWIRE, MovementInstruction.MATERIAL));			
		hash.add(new MaterialConfiguration(Material.TRIPWIRE_HOOK, MovementInstruction.MATERIAL));
		//command block
		hash.add(new MaterialConfiguration(Material.COMMAND_BLOCK, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.CHAIN_COMMAND_BLOCK, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.REPEATING_COMMAND_BLOCK, MovementInstruction.MATERIAL));
		//beacon
		hash.add(new MaterialConfiguration(Material.BEACON, MovementInstruction.MATERIAL));
		//wall
		hash.add(new MaterialConfiguration(Material.COBBLESTONE_WALL, MovementInstruction.MATERIAL));
		//flower pot
		hash.add(new MaterialConfiguration(Material.FLOWER_POT, MovementInstruction.MATERIAL));
		//mob head
		hash.add(new MaterialConfiguration(Material.CREEPER_HEAD, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.CREEPER_WALL_HEAD, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.DRAGON_HEAD, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.DRAGON_WALL_HEAD, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PLAYER_HEAD, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PLAYER_WALL_HEAD, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.ZOMBIE_HEAD, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.ZOMBIE_WALL_HEAD, MovementInstruction.MATERIAL));
		//anvil
		hash.add(new MaterialConfiguration(Material.ANVIL, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.CHIPPED_ANVIL, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.DAMAGED_ANVIL, MovementInstruction.MATERIAL));
		//comparator
		hash.add(new MaterialConfiguration(Material.COMPARATOR, MovementInstruction.MATERIAL));
		//daylight sensor
		hash.add(new MaterialConfiguration(Material.DAYLIGHT_DETECTOR, MovementInstruction.MATERIAL));
		//hopper
		hash.add(new MaterialConfiguration(Material.HOPPER, MovementInstruction.MATERIAL));
		//quartz
		hash.add(new MaterialConfiguration(Material.QUARTZ_PILLAR, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.CHISELED_QUARTZ_BLOCK, MovementInstruction.MATERIAL));
		//dropper
		hash.add(new MaterialConfiguration(Material.DROPPER, MovementInstruction.MATERIAL));
		//hardened clay
		//hash.add(new MaterialConfiguration(Material., MovementInstruction.MATERIAL));
		//slime
		hash.add(new MaterialConfiguration(Material.SLIME_BLOCK, MovementInstruction.MATERIAL));
		//barrier
		hash.add(new MaterialConfiguration(Material.BARRIER, MovementInstruction.MATERIAL));
		//prismarine
		hash.add(new MaterialConfiguration(Material.PRISMARINE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PRISMARINE_BRICKS, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.DARK_PRISMARINE, MovementInstruction.MATERIAL));
		//sea lantern
		hash.add(new MaterialConfiguration(Material.SEA_LANTERN, MovementInstruction.MATERIAL));
		//hay bale
		hash.add(new MaterialConfiguration(Material.HAY_BLOCK, MovementInstruction.MATERIAL));
		//carpet
		hash.add(new MaterialConfiguration(Material.BLACK_CARPET, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BLUE_CARPET, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BROWN_CARPET, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.CYAN_CARPET, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.GRAY_CARPET, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.GREEN_CARPET, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIGHT_BLUE_CARPET, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIGHT_GRAY_CARPET, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.MAGENTA_CARPET, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.ORANGE_CARPET, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PINK_CARPET, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PURPLE_CARPET, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.RED_CARPET, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.WHITE_CARPET, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.YELLOW_CARPET, MovementInstruction.MATERIAL));
		//banner
		hash.add(new MaterialConfiguration(Material.BLACK_BANNER, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BLACK_WALL_BANNER, MovementInstruction.MATERIAL));
		//end rod
		hash.add(new MaterialConfiguration(Material.END_ROD, MovementInstruction.MATERIAL));
		//purpur
		hash.add(new MaterialConfiguration(Material.PURPUR_BLOCK, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PURPUR_PILLAR, MovementInstruction.MATERIAL));
		//shulker box
		hash.add(new MaterialConfiguration(Material.BLACK_SHULKER_BOX, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BLUE_SHULKER_BOX, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BROWN_SHULKER_BOX, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.CYAN_SHULKER_BOX, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.GRAY_SHULKER_BOX, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.GREEN_SHULKER_BOX, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIGHT_BLUE_SHULKER_BOX, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIGHT_GRAY_SHULKER_BOX, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIME_SHULKER_BOX, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.MAGENTA_SHULKER_BOX, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.ORANGE_SHULKER_BOX, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PINK_SHULKER_BOX, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PURPLE_SHULKER_BOX, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.RED_SHULKER_BOX, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.WHITE_SHULKER_BOX, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.YELLOW_SHULKER_BOX, MovementInstruction.MATERIAL));
		//terracotta
		hash.add(new MaterialConfiguration(Material.TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BLACK_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BLACK_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BLUE_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BLUE_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BROWN_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BROWN_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.CYAN_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.CYAN_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.GRAY_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.GRAY_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.GREEN_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.GREEN_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIGHT_BLUE_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIGHT_GRAY_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIME_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIME_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.MAGENTA_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.MAGENTA_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.ORANGE_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.ORANGE_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PINK_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PINK_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PURPLE_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PURPLE_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.RED_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.RED_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.WHITE_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.WHITE_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.YELLOW_GLAZED_TERRACOTTA, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.YELLOW_TERRACOTTA, MovementInstruction.MATERIAL));
		//concreate
		hash.add(new MaterialConfiguration(Material.BLACK_CONCRETE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BLUE_CONCRETE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.BROWN_CONCRETE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.CYAN_CONCRETE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.GRAY_CONCRETE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.GREEN_CONCRETE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIGHT_BLUE_CONCRETE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIGHT_GRAY_CONCRETE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.LIME_CONCRETE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.MAGENTA_CONCRETE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.ORANGE_CONCRETE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PINK_CONCRETE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.PURPLE_CONCRETE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.RED_CONCRETE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.WHITE_CONCRETE, MovementInstruction.MATERIAL));
		hash.add(new MaterialConfiguration(Material.YELLOW_CONCRETE, MovementInstruction.MATERIAL));
		//concrete powder
		hash.add(new MaterialConfiguration(Material.BLACK_CONCRETE_POWDER, MovementInstruction.MATERIAL));
		
		//RAM
		//sapling
		hash.add(new MaterialConfiguration(Material.ACACIA_SAPLING, MovementInstruction.RAM));
		//TODO
		
		//leaves
		hash.add(new MaterialConfiguration(Material.ACACIA_LEAVES, MovementInstruction.RAM));
		
		//cobweb
		hash.add(new MaterialConfiguration(Material.COBWEB, MovementInstruction.RAM));
		//grass
		hash.add(new MaterialConfiguration(Material.TALL_GRASS, MovementInstruction.RAM));
		hash.add(new MaterialConfiguration(Material.FERN, MovementInstruction.RAM));
		//dead bush
		hash.add(new MaterialConfiguration(Material.DEAD_BUSH, MovementInstruction.RAM));
		//flower
		hash.add(new MaterialConfiguration(Material.POPPY, MovementInstruction.RAM));
		hash.add(new MaterialConfiguration(Material.DANDELION_YELLOW, MovementInstruction.RAM));
		hash.add(new MaterialConfiguration(Material.BLUE_ORCHID, MovementInstruction.RAM));
		hash.add(new MaterialConfiguration(Material.ALLIUM, MovementInstruction.RAM));
		hash.add(new MaterialConfiguration(Material.AZURE_BLUET, MovementInstruction.RAM));
		hash.add(new MaterialConfiguration(Material.RED_TULIP, MovementInstruction.RAM));
		hash.add(new MaterialConfiguration(Material.ORANGE_TULIP, MovementInstruction.RAM));
		hash.add(new MaterialConfiguration(Material.WHITE_TULIP, MovementInstruction.RAM));
		hash.add(new MaterialConfiguration(Material.PINK_TULIP, MovementInstruction.RAM));
		hash.add(new MaterialConfiguration(Material.OXEYE_DAISY, MovementInstruction.RAM));
		//mushroom
		hash.add(new MaterialConfiguration(Material.BROWN_MUSHROOM_BLOCK, MovementInstruction.RAM));
		hash.add(new MaterialConfiguration(Material.RED_MUSHROOM_BLOCK, MovementInstruction.RAM));
		//wheat
		hash.add(new MaterialConfiguration(Material.WHEAT, MovementInstruction.RAM));
		//cactus
		hash.add(new MaterialConfiguration(Material.CACTUS, MovementInstruction.RAM));
		//canes
		hash.add(new MaterialConfiguration(Material.SUGAR_CANE, MovementInstruction.RAM));
		//stems
		hash.add(new MaterialConfiguration(Material.ATTACHED_MELON_STEM, MovementInstruction.RAM));
		hash.add(new MaterialConfiguration(Material.ATTACHED_PUMPKIN_STEM, MovementInstruction.RAM));
		hash.add(new MaterialConfiguration(Material.MELON_STEM, MovementInstruction.RAM));
		hash.add(new MaterialConfiguration(Material.MUSHROOM_STEM, MovementInstruction.RAM));
		hash.add(new MaterialConfiguration(Material.PUMPKIN_STEM, MovementInstruction.RAM));
		//vines
		hash.add(new MaterialConfiguration(Material.VINE, MovementInstruction.RAM));
		//lily pad
		hash.add(new MaterialConfiguration(Material.LILY_PAD, MovementInstruction.RAM));
		//wart
		hash.add(new MaterialConfiguration(Material.NETHER_WART_BLOCK, MovementInstruction.RAM));
		//food
		hash.add(new MaterialConfiguration(Material.CARROTS, MovementInstruction.RAM));
		hash.add(new MaterialConfiguration(Material.POTATOES, MovementInstruction.RAM));
		//large plants
		hash.add(new MaterialConfiguration(Material.LILAC, MovementInstruction.RAM));
		hash.add(new MaterialConfiguration(Material.GRASS_BLOCK, MovementInstruction.RAM));
		hash.add(new MaterialConfiguration(Material.LARGE_FERN, MovementInstruction.RAM));
		hash.add(new MaterialConfiguration(Material.ROSE_BUSH, MovementInstruction.RAM));
		hash.add(new MaterialConfiguration(Material.PEONY, MovementInstruction.RAM));
		return hash;
	}

}

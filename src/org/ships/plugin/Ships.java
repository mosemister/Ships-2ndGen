package org.ships.plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.ships.block.BlockStack;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.blockhandler.types.Banner;
import org.ships.block.blockhandler.types.Beacon;
import org.ships.block.blockhandler.types.Brewing;
import org.ships.block.blockhandler.types.Chest;
import org.ships.block.blockhandler.types.CommandBlock;
import org.ships.block.blockhandler.types.Dispenser;
import org.ships.block.blockhandler.types.Dropper;
import org.ships.block.blockhandler.types.EndPortal;
import org.ships.block.blockhandler.types.Fire;
import org.ships.block.blockhandler.types.Furnace;
import org.ships.block.blockhandler.types.Head;
import org.ships.block.blockhandler.types.Hopper;
import org.ships.block.blockhandler.types.JukeBox;
import org.ships.block.blockhandler.types.MonsterSpawner;
import org.ships.block.blockhandler.types.ShulkerBox;
import org.ships.block.blockhandler.types.Sign;
import org.ships.block.configuration.MovementInstruction;
import org.ships.block.sign.AltitudeSign;
import org.ships.block.sign.EngineSign;
import org.ships.block.sign.LicenceSign;
import org.ships.block.sign.ShipSign;
import org.ships.block.sign.ThrustSign;
import org.ships.block.sign.WheelSign;
import org.ships.configuration.BlockList;
import org.ships.configuration.Config;
import org.ships.configuration.Messages;
import org.ships.event.commands.BukkitListeners;
import org.ships.event.commands.Commands;
import org.ships.event.commands.ShipsCommands.AutoPilot;
import org.ships.event.commands.ShipsCommands.Developer;
import org.ships.event.commands.ShipsCommands.Fixes;
import org.ships.event.commands.ShipsCommands.Help;
import org.ships.event.commands.ShipsCommands.Info;
import org.ships.event.commands.ShipsCommands.Reload;
import org.ships.event.commands.ShipsCommands.SignCommand;
import org.ships.event.commands.ShipsCommands.Teleport;
import org.ships.event.commands.ShipsCommands.VesselCommand;
import org.ships.event.commands.gui.ShipsGUICommand;
import org.ships.ship.LoadableShip;
import org.ships.ship.movement.ShipsAutoRuns;
import org.ships.ship.type.VesselType;

public class Ships extends JavaPlugin {
	private static Ships plugin;
	private final Set<ShipSign> shipSigns = new HashSet<ShipSign>();
	private static BlockStack STACK;
	private static int count;
	public static final int COMPARE_FIRST_VALUE_IS_GREATER = 1;
	public static final int COMPARE_SECOND_VALUE_IS_GREATER = 3;
	public static final int COMPARE_BOTH_VALUES_ARE_THE_SAME = 2;

	@Override
	public void onEnable() {
		plugin = this;
		this.getCommand("Ships").setExecutor(new Commands());
		this.getServer().getPluginManager().registerEvents(new BukkitListeners(), this);
		Config.getConfig().updateCheck();
		BlockList.BLOCK_LIST.load(BlockList.BLOCK_LIST_FILE, BlockList.BLOCK_LIST_YAML);
		this.registerHandlers();
		this.activateCommands();
		this.activateShipSigns();
		Messages.refreshMessages();
		this.removeOldFiles();
		ShipsGUICommand.setGUITools();
		for (VesselType type : VesselType.values()) {
			type.loadDefault();
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		if (config.getBoolean("Structure.Signs.AutoPilot.enabled")) {
			ShipsAutoRuns.AutoMove();
			new AutoPilot();
		}
		if (config.getBoolean("Structure.Signs.EOT.enabled")) {
			ShipsAutoRuns.EOTMove();
		}
		if (config.getBoolean("World.ProtectedVessels.VesselFallOutSky")) {
			ShipsAutoRuns.fallOutSky();
		}
	}

	@Override
	public void onDisable() {
		LoadableShip.getShips().stream().forEach(s -> {
			s.save();
		});
	}

	public Set<ShipSign> getShipSigns() {
		return new HashSet<ShipSign>(this.shipSigns);
	}

	private void removeOldFiles() {
		File config = new File("plugins/Ships/config.yml");
		File messages = new File("plugins/Ships/DebugOptions.yml");
		File materials = new File("plugins/Ships/Materials.yml");
		File debug = new File("plugins/Ships/Messages.yml");
		if (config.exists()) {
			config.delete();
		}
		if (messages.exists()) {
			messages.delete();
		}
		if (materials.exists()) {
			materials.delete();
		}
		if (debug.exists()) {
			debug.delete();
		}
	}

	private void activateShipSigns() {
		this.shipSigns.add(new LicenceSign());
		this.shipSigns.add(new ThrustSign());
		this.shipSigns.add(new WheelSign());
		this.shipSigns.add(new AltitudeSign());
		this.shipSigns.add(new EngineSign());
	}

	/*private void activateNewCommands() {
		CMDExecutor.register(new ShipsInfoCommand());
		CMDExecutor.register(new ShipsSignTrackCommand());
	}*/

	private void activateCommands() {
		new Reload();
		new Developer();
		new SignCommand();
		new Teleport();
		new Fixes();
		new Info();
		new VesselCommand();
		new Help();
	}

	private void registerHandlers() {
		BlockHandler.register(Fire.class, Material.FIRE);
		BlockHandler.register(Banner.class, Material.BLACK_BANNER, Material.BLACK_WALL_BANNER, Material.BLUE_BANNER, Material.BLUE_WALL_BANNER, Material.BROWN_BANNER, Material.BROWN_WALL_BANNER, Material.CYAN_BANNER, Material.CYAN_WALL_BANNER, Material.GRAY_BANNER, Material.GRAY_WALL_BANNER, Material.GREEN_BANNER, Material.GREEN_WALL_BANNER, Material.LIGHT_BLUE_BANNER, Material.LIGHT_BLUE_WALL_BANNER, Material.LIGHT_GRAY_BANNER, Material.LIGHT_GRAY_WALL_BANNER, Material.LIME_BANNER, Material.LIME_WALL_BANNER, Material.MAGENTA_BANNER, Material.MAGENTA_WALL_BANNER, Material.ORANGE_BANNER, Material.ORANGE_WALL_BANNER, Material.PINK_BANNER, Material.PINK_WALL_BANNER, Material.PURPLE_BANNER, Material.PURPLE_WALL_BANNER, Material.RED_BANNER, Material.RED_WALL_BANNER, Material.WHITE_BANNER, Material.WHITE_WALL_BANNER, Material.YELLOW_BANNER, Material.YELLOW_WALL_BANNER);
		BlockHandler.register(Beacon.class, Material.BEACON);
		BlockHandler.register(Brewing.class, Material.BREWING_STAND);
		BlockHandler.register(Chest.class, Material.CHEST, Material.TRAPPED_CHEST);
		BlockHandler.register(CommandBlock.class, Material.COMMAND_BLOCK, Material.CHAIN_COMMAND_BLOCK, Material.REPEATING_COMMAND_BLOCK);
		BlockHandler.register(Dispenser.class, Material.DISPENSER);
		BlockHandler.register(Dropper.class, Material.DROPPER);
		BlockHandler.register(EndPortal.class, Material.END_PORTAL);
		BlockHandler.register(Furnace.class, Material.FURNACE);
		BlockHandler.register(Head.class, Material.SKELETON_SKULL, Material.SKELETON_WALL_SKULL, Material.WITHER_SKELETON_SKULL, Material.WITHER_SKELETON_WALL_SKULL);
		BlockHandler.register(Hopper.class, Material.HOPPER);
		BlockHandler.register(JukeBox.class, Material.JUKEBOX);
		BlockHandler.register(MonsterSpawner.class, Material.SPAWNER);
		BlockHandler.register(ShulkerBox.class, Material.SHULKER_BOX, Material.BLACK_SHULKER_BOX, Material.BLUE_SHULKER_BOX, Material.BROWN_SHULKER_BOX, Material.CYAN_SHULKER_BOX, Material.GRAY_SHULKER_BOX, Material.GREEN_SHULKER_BOX);
		BlockHandler.register(Sign.class, Material.SIGN, Material.WALL_SIGN);
	}

	public static Ships getPlugin() {
		return plugin;
	}

	public static String runShipsMessage(String message, boolean error) {
		if (error) {
			return ChatColor.GOLD + "[Ships] " + ChatColor.RED + message;
		}
		return ChatColor.GOLD + "[Ships] " + ChatColor.AQUA + message;
	}

	public static BlockStack getBaseStructure(Block block) {
		STACK = new BlockStack();
		count = 0;
		STACK.add(block);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		int limit = config.getInt("Structure.StructureLimits.trackLimit");
		BlockFace[] faces = new BlockFace[] { BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.UP, BlockFace.WEST };
		Ships.prototype3(block, faces, limit);
		if (STACK.isVaild()) {
			return STACK;
		}
		return new BlockStack();
	}

	private static void prototype3(Block block, BlockFace[] faces, int limit) {
		if (count > limit) {
			return;
		}
		++count;
		for (BlockFace face : faces) {
			Block block2 = block.getRelative(face);
			if (!BlockList.BLOCK_LIST.getCurrentWith(MovementInstruction.MATERIAL).stream().anyMatch(c -> c.getMaterial().equals(block2.getType())) || STACK.contains(block2))
				continue;
			STACK.add(block2);
			Ships.prototype3(block2, faces, limit);
		}
	}

	public static BlockFace getPlayerFacingDirection(Player player) {
		float yaw = player.getLocation().getYaw();
		if (yaw >= 45.0f && yaw < 135.0f) {
			return BlockFace.WEST;
		}
		if (yaw >= 135.0f && yaw <= 180.0f || yaw >= -180.0f && yaw < -135.0f) {
			return BlockFace.NORTH;
		}
		if (yaw >= -135.0f && yaw < -45.0f) {
			return BlockFace.EAST;
		}
		return BlockFace.SOUTH;
	}

	public static BlockFace getSideFace(BlockFace face, boolean left) {
		BlockFace ret = null;
		switch (face) {
			case NORTH: {
				if (left) {
					ret = BlockFace.WEST;
					break;
				}
				ret = BlockFace.EAST;
				break;
			}
			case SOUTH: {
				if (left) {
					ret = BlockFace.EAST;
					break;
				}
				ret = BlockFace.WEST;
				break;
			}
			case EAST: {
				if (left) {
					ret = BlockFace.SOUTH;
					break;
				}
				ret = BlockFace.NORTH;
				break;
			}
			case WEST: {
				if (left) {
					ret = BlockFace.NORTH;
					break;
				}
				ret = BlockFace.SOUTH;
				break;
			}
			default: {
				new IOException("[SHIPS] Invalid direction: " + face.name());
			}
		}
		return ret;
	}

	public static String getMinecraftVersion() {
		String temp = Ships.getPlugin().getServer().getVersion();
		String[] part1 = temp.split(":");
		String part2 = part1[1].replace(")", "");
		String part3 = part2.replace(" ", "");
		return part3;
	}

	@Deprecated
	public static int getVersion(String version) {
		String mcVersion = version.replace(".", "");
		String[] splitMCVersion = version.split(".");
		for (int A = splitMCVersion.length; A < 4; ++A) {
			mcVersion = mcVersion + "0";
		}
		int version2 = Integer.parseInt(mcVersion);
		return version2;
	}

	public static int[] convertVersion(String version) {
		int[] mcVersion = new int[4];
		String[] splitMCVersion = version.split(".");
		for (int A = 0; A < splitMCVersion.length; ++A) {
			mcVersion[A] = Integer.parseInt(splitMCVersion[A]);
		}
		return mcVersion;
	}

	public static int compare(int[] version1, int[] version2) {
		int maxValue = version1.length;
		if (version2.length > version1.length) {
			maxValue = version2.length;
		}
		for (int A = 0; A < maxValue; ++A) {
			int value1 = 0;
			int value2 = 0;
			if (A <= version1.length) {
				value1 = version1[A];
			}
			if (A <= version1.length) {
				value1 = version2[A];
			}
			if (value1 > value2) {
				return 1;
			}
			if (value1 >= value2)
				continue;
			return 3;
		}
		return 2;
	}

	public static void copy(InputStream input, File target) throws IOException {
		int realLength;
		if (target.exists()) {
			Bukkit.getConsoleSender().sendMessage("Config file already exists.");
			return;
		}
		File parentDir = target.getParentFile();
		parentDir.mkdirs();
		if (!parentDir.isDirectory()) {
			throw new IOException("The parent of this file is no directory!?");
		}
		if (!target.createNewFile()) {
			throw new IOException("Failed at creating new empty file!");
		}
		byte[] buffer = new byte[1024];
		FileOutputStream output = new FileOutputStream(target);
		while ((realLength = input.read(buffer)) > 0) {
			output.write(buffer, 0, realLength);
		}
		output.flush();
		output.close();
	}

	public static InputStream getInputFromJar(String filename) throws IOException {
		if (filename == null) {
			throw new IllegalArgumentException("The path can not be null");
		}
		URL url = Ships.class.getResource(filename);
		if (url == null) {
			return null;
		}
		URLConnection connection = url.openConnection();
		connection.setUseCaches(false);
		return connection.getInputStream();
	}

}

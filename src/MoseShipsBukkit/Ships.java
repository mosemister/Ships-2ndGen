package MoseShipsBukkit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import MoseShipsBukkit.GUI.ShipsGUICommand;
import MoseShipsBukkit.Listeners.BukkitListeners;
import MoseShipsBukkit.Listeners.Commands;
import MoseShipsBukkit.Listeners.ShipsCommands.AutoPilot;
import MoseShipsBukkit.Listeners.ShipsCommands.Developer;
import MoseShipsBukkit.Listeners.ShipsCommands.Fixes;
import MoseShipsBukkit.Listeners.ShipsCommands.Help;
import MoseShipsBukkit.Listeners.ShipsCommands.Info;
import MoseShipsBukkit.Listeners.ShipsCommands.Reload;
import MoseShipsBukkit.Listeners.ShipsCommands.SignCommand;
import MoseShipsBukkit.Listeners.ShipsCommands.Teleport;
import MoseShipsBukkit.Listeners.ShipsCommands.VesselCommand;
import MoseShipsBukkit.ShipsTypes.VesselType;
import MoseShipsBukkit.StillShip.Vessel.Vessel;
import MoseShipsBukkit.Utils.BlockStack;
import MoseShipsBukkit.Utils.ShipsAutoRuns;
import MoseShipsBukkit.Utils.VesselLoader;
import MoseShipsBukkit.Utils.ConfigLinks.Config;
import MoseShipsBukkit.Utils.ConfigLinks.MaterialsList;
import MoseShipsBukkit.Utils.ConfigLinks.Messages;
import MoseShipsBukkit.World.Wind.Direction;

public class Ships extends JavaPlugin {

	static JavaPlugin plugin;
	static BlockStack STACK;
	static int count;

	public static final int COMPARE_FIRST_VALUE_IS_GREATER = 1;
	public static final int COMPARE_SECOND_VALUE_IS_GREATER = 3;
	public static final int COMPARE_BOTH_VALUES_ARE_THE_SAME = 2;
	
	public void onEnable() {
		plugin = this;
		getCommand("Ships").setExecutor(new Commands());
		getServer().getPluginManager().registerEvents(new BukkitListeners(), this);
		Config.getConfig().updateCheck();
		new MaterialsList();
		MaterialsList.getMaterialsList().save();
		activateCommands();
		Messages.refreshMessages();
		removeOldFiles();
		ShipsGUICommand.setGUITools();
		// FlyThrough.activateFlyThrough();
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
		afterBoot();
	}

	public void onDisable() {
		for (Vessel vessel : Vessel.getVessels()) {
			vessel.save();
		}
	}

	public void afterBoot() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable() {

			@Override
			public void run() {
				VesselLoader.loadVessels();
				for (World world : Bukkit.getWorlds()) {
					new Direction(world);
				}
			}

		}, 0);
	}

	public void removeOldFiles() {
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

	public static void activateCommands() {
		new Reload();
		new Developer();
		new SignCommand();
		new Teleport();
		new Fixes();
		new Info();
		new VesselCommand();
		new Help();
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}

	public static String runShipsMessage(String message, boolean error) {
		if (error) {
			return (ChatColor.GOLD + "[Ships] " + ChatColor.RED + message);
		} else {
			return (ChatColor.GOLD + "[Ships] " + ChatColor.AQUA + message);
		}
	}

	public static List<Block> getBaseStructure(Block block) {
		STACK = new BlockStack();
		count = 0;
		STACK.addBlock(block);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		int limit = config.getInt("Structure.StructureLimits.trackLimit");
		BlockFace[] faces = { BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.UP,
				BlockFace.WEST };
		prototype3(block, faces, limit);
		if (STACK.isVaild()) {
			List<Block> stack = STACK.getList();
			return stack;
		}
		return new ArrayList<Block>();
	}

	// This gets every block connected, It can be abnormal so the count attempts
	// to control it. I called it Prototype for a good reason.
	@SuppressWarnings("deprecation")
	static void prototype3(Block block, BlockFace[] faces, int limit) {
		// this /attempts/ to cap the variable scan at 500, I have seen that it
		// caps the block limit at 500 but the method is still ran afterwards
		// for a good while. If you find another way to do this that is more
		// efficient then this way, please contact me asap
		if (count > limit) {
			return;
		}
		count++;
		// List<String> material = new ArrayList<String>();
		for (BlockFace face : faces) {
			Block block2 = block.getRelative(face);
			if ((MaterialsList.getMaterialsList().contains(block2.getType(), block2.getData(), true))) {
				if (!STACK.contains(block2)) {
					STACK.addBlock(block2);
					prototype3(block2, faces, limit);
				}
			} /*
				 * else{ if (!material.contains(block2.getType().name())){
				 * material.add(block2.getType().name()); } }
				 */
		}
		/*
		 * if (material.size() != 0){ System.out.println(material); }
		 */
	}

	@SuppressWarnings("deprecation")
	static List<Block> prototype4(Block block, BlockFace[] faces, int limit) {
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		console.sendMessage("------------[Started]--------------");
		BlockStack stack = new BlockStack();
		int count = 0;
		Block previousBlock = block;
		Block viewingBlock = block;
		while (count <= limit) {
			console.sendMessage("Repeating: " + count);
			count++;
			for (BlockFace face : faces) {
				Block block2 = viewingBlock.getRelative(face);
				console.sendMessage(
						"facing: " + face.name() + " | type: " + block2.getType() + " | data: " + block2.getData());
				if ((MaterialsList.getMaterialsList().contains(block2.getType(), block2.getData(), true))) {
					console.sendMessage("materials accepted it");
					if (!stack.contains(block2)) {
						console.sendMessage("block not contained");
						stack.addBlock(block2);
						viewingBlock = block2;
						previousBlock = block;
					}
				}
			}
			if (previousBlock.equals(viewingBlock)) {
				break;
			}
		}
		return stack.getList();
	}

	// never know when it could be useful ;)
	public static BlockFace getPlayerFacingDirection(Player player) {
		float yaw = player.getLocation().getYaw();
		if ((yaw >= 45) && (yaw < 135)) {
			return BlockFace.WEST;
		} else {
			if (((yaw >= 135) && (yaw <= 180)) || ((yaw >= -180) && (yaw < -135))) {
				return BlockFace.NORTH;
			} else {
				if ((yaw >= -135) && (yaw < -45)) {
					return BlockFace.EAST;
				} else {
					return BlockFace.SOUTH;
				}
			}
		}
	}

	// fixes a missing part of the bukkit API
	public static BlockFace getSideFace(BlockFace face, boolean left) {
		BlockFace ret = null;
		switch (face) {
		case NORTH:
			if (left) {
				ret = BlockFace.WEST;
			} else {
				ret = BlockFace.EAST;
			}
			break;
		case SOUTH:
			if (left) {
				ret = BlockFace.EAST;
			} else {
				ret = BlockFace.WEST;
			}
			break;
		case EAST:
			if (left) {
				ret = BlockFace.SOUTH;
			} else {
				ret = BlockFace.NORTH;
			}
			break;
		case WEST:
			if (left) {
				ret = BlockFace.NORTH;
			} else {
				ret = BlockFace.SOUTH;
			}
			break;
		default:
			new IOException("[SHIPS] Invalid direction: " + face.name());
			break;
		}
		return ret;
	}

	public static String getMinecraftVersion() {
		String temp = getPlugin().getServer().getVersion();
		// String version = tempVersion.split("'(MC: ', ')'")[1];
		String[] part1 = temp.split(":");
		String part2 = part1[1].replace(")", "");
		String part3 = part2.replace(" ", "");
		return part3;
	}

	@Deprecated
	public static int getVersion(String version) {
		String mcVersion = version.replace(".", "");
		String[] splitMCVersion = version.split(".");
		for (int A = splitMCVersion.length; A < 4; A++) {
			mcVersion = (mcVersion + "0");
		}
		int version2 = Integer.parseInt(mcVersion);
		return version2;
	}
	
	public static int[] convertVersion(String version) {
		int[] mcVersion = new int[4]; 
		String[] splitMCVersion = version.split(".");
		for(int A = 0; A < splitMCVersion.length; A++) {
			mcVersion[A] = Integer.parseInt(splitMCVersion[A]);
		}
		return mcVersion;
	}
	
	public static int compare(int[] version1, int[] version2) {
		int maxValue = version1.length;
		if(version2.length > version1.length) {
			maxValue = version2.length;
		}
		for(int A = 0; A < maxValue; A++) {
			int value1 = 0;
			int value2 = 0;
			if(A <= version1.length) {
				value1 = version1[A];
			}
			if(A <= version1.length) {
				value1 = version2[A];
			}
			if(value1 > value2) {
				return COMPARE_FIRST_VALUE_IS_GREATER;
			}else if(value1 < value2) {
				return COMPARE_SECOND_VALUE_IS_GREATER;
			}
		}
		return COMPARE_BOTH_VALUES_ARE_THE_SAME;
	}

	// This is used by the config files to copy internal files to outside, I put
	// this in thinking it would keep the variable notes, sadly it does not
	public static void copy(InputStream input, File target) throws IOException {
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
		OutputStream output = new FileOutputStream(target);
		int realLength;
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

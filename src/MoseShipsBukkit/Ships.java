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
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import MoseShipsBukkit.Listeners.BukkitListeners;
import MoseShipsBukkit.Listeners.Commands;
import MoseShipsBukkit.Listeners.ShipsCommands.AutoPilot;
import MoseShipsBukkit.Listeners.ShipsCommands.Developer;
import MoseShipsBukkit.Listeners.ShipsCommands.Fixes;
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

public class Ships extends JavaPlugin{
	
	static JavaPlugin plugin;
	static BlockStack STACK;
	static int count;
	
	public void onEnable(){
		plugin = this;
		getCommand("Ships").setExecutor(new Commands());
		getServer().getPluginManager().registerEvents(new BukkitListeners(), this);
		Config.getConfig().updateCheck();
		new MaterialsList();
		MaterialsList.getMaterialsList().save();
		activateCommands();
		Messages.refreshMessages();
		removeOldFiles();
		//FlyThrough.activateFlyThrough();
		for (VesselType type : VesselType.values()){
			type.loadDefault();
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		
		if (config.getBoolean("Structure.Signs.AutoPilot.enabled")){
			ShipsAutoRuns.AutoMove();
			new AutoPilot();
		}
		if (config.getBoolean("Structure.Signs.EOT.enabled")){
			ShipsAutoRuns.EOTMove();
		}
		if (config.getBoolean("Structure.Sign.Cell.enabled")){
			ShipsAutoRuns.SolorCell();
		}
		if (config.getBoolean("World.Physics.VesselFallOutSky")){
			ShipsAutoRuns.fallOutSky();
		}
		afterBoot();
	}
	
	public void onDisable(){
		for (Vessel vessel : Vessel.getVessels()){
			vessel.save();
		}
	}
	
	public void afterBoot(){
		Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable(){

			@Override
			public void run() {
				VesselLoader.loadVessels();
				for (World world : Bukkit.getWorlds()){
					new Direction(world);
				}
			}
			
		}, 0);
	}
	
	public void removeOldFiles(){
		File config = new File("plugins/Ships/config.yml");
		File messages = new File("plugins/Ships/DebugOptions.yml");
		File materials = new File("plugins/Ships/Materials.yml");
		File debug = new File("plugins/Ships/Messages.yml");
		if (config.exists()){
			config.delete();
		}
		if (messages.exists()){
			messages.delete();
		}
		if (materials.exists()){
			materials.delete();
		}
		if (debug.exists()){
			debug.delete();
		}
	}
	
	public static void activateCommands(){
		new Reload();
		new Developer();
		new SignCommand();
		new Teleport();
		new Fixes();
		new Info();
		new VesselCommand();
	}
	
	public static JavaPlugin getPlugin(){
		return plugin;
	}
	
	public static String runShipsMessage(String message, boolean error){
		if (error){
			return (ChatColor.GOLD + "[Ships] " + ChatColor.RED + message);
		}else{
			return (ChatColor.GOLD + "[Ships] " + ChatColor.AQUA + message);
		}
	}
	
	public static List<Block> getBaseStructure(Block block){
		STACK = new BlockStack();
		count = 0;
		STACK.addBlock(block);
		prototype2(block);
		if (STACK.isVaild()){
			List<Block> stack = STACK.getList();		
			return stack;
		}
		return new ArrayList<Block>();
	}
	
	//This gets every block connected, It can be abnormal so the count attempts to control it. I called it Prototype for a good reason.
	@SuppressWarnings("deprecation")
	static void prototype2(Block block){
		//this /attempts/ to cap the variable scan at 500, I have seen that it caps the block limit at 500 but the method is still ran afterwards
		//for a good while. If you find another way to do this that is more efficient then this way, please contact me asap
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		int limit = config.getInt("Structure.StructureLimits.trackLimit");
		if (count > limit){
			return;
		}
		count++;
		BlockFace[] faces = {BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.UP, BlockFace.WEST};
		for(BlockFace face : faces){
			Block block2 = block.getRelative(face);
			if ((MaterialsList.getMaterialsList().contains(block2.getType(), block2.getData(), true))){
				if (!STACK.contains(block2)){
					STACK.addBlock(block2);
					prototype2(block2);
				}
			}	
		}
	}
	
	//never know when it could be useful ;)
	public static BlockFace getPlayerFacingDirection(Player player){
		float yaw = player.getLocation().getYaw();
		if ((yaw >= 45) && (yaw < 135)){
			return BlockFace.WEST;
		}else if (((yaw >= 135) && (yaw <= 180)) || ((yaw >= -180) && (yaw < -135))){
			return BlockFace.NORTH;
		}else if ((yaw >= -135) && (yaw < -45)){
			return BlockFace.EAST;
		}else{
			return BlockFace.SOUTH;
		}
	}
	
	//fixes a missing part of the bukkit API
	public static BlockFace getSideFace(BlockFace face, boolean left){
		if (face.equals(BlockFace.NORTH)){
			if (left){
				return BlockFace.WEST;
			}else{
				return BlockFace.EAST;
			}
		}
		if (face.equals(BlockFace.EAST)){
			if (left){
				return BlockFace.SOUTH;
			}else{
				return BlockFace.NORTH;
			}
		}
		if (face.equals(BlockFace.SOUTH)){
			if (left){
				return BlockFace.EAST;
			}else{
				return BlockFace.WEST;
			}
		}
		if (face.equals(BlockFace.WEST)){
			if (left){
				return BlockFace.NORTH;
			}else{
				return BlockFace.SOUTH;
			}
		}
		return face;
	}
	
	//This is used by the config files to copy internal files to outside, I put this in thinking it would keep the variable notes, sadly it does not
	public static void copy(InputStream input, File target) throws IOException{
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
	
	public static InputStream getInputFromJar(String filename) throws IOException{
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

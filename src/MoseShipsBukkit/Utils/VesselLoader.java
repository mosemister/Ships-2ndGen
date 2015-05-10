package MoseShipsBukkit.Utils;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.ShipTypes.VesselType;
import MoseShipsBukkit.StillShip.Vessel;

public class VesselLoader {
	
	public static void loadVessels(){
		File folder = new File("plugins/Ships/VesselData/");
		File[] files = folder.listFiles();
		if (files != null){
			for(File file : files){
				if (!file.getName().contains(".yml~")){
					CommandSender sender = (CommandSender)Bukkit.getConsoleSender();
					if (classicLoader(file)){
						sender.sendMessage(Ships.runShipsMessage("Classic vessel loaded (" + file.getName().replace(".yml", "") + ")", false));
					}else if (newLoader(file)){
						sender.sendMessage(Ships.runShipsMessage("vessel loaded (" + file.getName().replace(".yml", "") + ")", false));
					}else{
						Bukkit.getConsoleSender().sendMessage(Ships.runShipsMessage(file.getAbsolutePath() + " can not be loaded due to a unknown reason", true));
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static boolean classicLoader(File file){
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		OfflinePlayer owner = Bukkit.getOfflinePlayer(config.getString("ShipsData.Player.Name"));
		int max = config.getInt("ShipsData.Config.Block.Max");
		int min = config.getInt("ShipsData.Config.Block.Min");
		int engine = config.getInt("ShipsData.Config.Speed.Engine");
		int boost = config.getInt("ShipsData.Config.Speed.Boost");
		double X = config.getDouble("ShipsLocation.X");
		double Y = config.getDouble("ShipsLocation.Y");
		double Z = config.getDouble("ShipsLocation.Z");
		String worldS = config.getString("ShipsLocation.world");
		if	(worldS != null){
			World world = Bukkit.getWorld(worldS);
			String name = file.getName().replace(".yml", "");
			if (world != null){
				Location loc = null;
				for(int x = -boost; x < boost; x++){
					for(int y = -boost; y < boost; y++){
						for(int z = -boost; z < boost; z++){
							Location loc2 = new Location(world, X + x,Y + y, Z + z);
							if (loc2.getBlock().getState() instanceof Sign){
								Sign sign = (Sign)loc2.getBlock().getState();
								if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")){
									loc = loc2;
									break;
								}
							}
						}
					}
				}
				if (loc != null){
					if (loc.getBlock().getState() instanceof Sign){
						Sign sign = (Sign)loc.getBlock().getState();
						if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")){
							String typeString = sign.getLine(1).replace(ChatColor.BLUE + "", "");
							String typeStringFix = typeString.replace("2", "");
							VesselType type = VesselType.getTypeByName(typeStringFix);
							if (type != null){
								type.setDefaultSpeed(engine);
								type.setMaxBlocks(max);
								type.setMinBlocks(min);
								Vessel vessel = new Vessel(sign, name, type, owner, loc);
								vessel.getVesselType().loadFromClassicVesselFile(vessel, file);
								file.delete();
								vessel.save();
								return true;
							}else{
								Bukkit.getConsoleSender().sendMessage(ChatColor.RED + sign.getLine(1) + " is no longer supported in Ships 5");
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	public static boolean newLoader(File file){
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		try{
			UUID uuid = UUID.fromString(config.getString("ShipsData.Player.Name"));
			OfflinePlayer owner =  Bukkit.getOfflinePlayer(uuid);
			String vesselTypeS = config.getString("ShipsData.Type");
			int max = config.getInt("ShipsData.Config.Block.Max");
			int min = config.getInt("ShipsData.Config.Block.Min");
			int engine = config.getInt("ShipsData.Config.Speed.Engine");
			String locS = config.getString("ShipsData.Location.Sign");
			String teleportS = config.getString("ShipsData.Location.Teleport");
			String name = file.getName().replace(".yml", "");
			if (vesselTypeS != null){
				if (locS != null){
					if (teleportS != null){
						String[] locM = locS.split(",");
						Location loc = new Location(Bukkit.getWorld(locM[3]), Double.parseDouble(locM[0]), Double.parseDouble(locM[1]), Double.parseDouble(locM[2]));
						if (loc.getBlock().getState() instanceof Sign){
							Sign sign = (Sign)loc.getBlock().getState();
							String[] teleportM = teleportS.split(",");
							Location teleport = new Location(Bukkit.getWorld(teleportM[3]), Double.parseDouble(teleportM[0]), Double.parseDouble(teleportM[1]), Double.parseDouble(teleportM[2]));
							VesselType vesselType = VesselType.getTypeByName(vesselTypeS);
							if (vesselType != null){
								Vessel vessel = new Vessel(sign, name, vesselType, owner, teleport);
								VesselType type = vessel.getVesselType();
								type.setDefaultSpeed(engine);
								type.setMaxBlocks(max);
								type.setMinBlocks(min);
								type.loadFromNewVesselFile(vessel, file);
								return true;
							}
						}
					}
				}
			}
		}catch(IllegalArgumentException e){
		}
		return false;
	}
	
}

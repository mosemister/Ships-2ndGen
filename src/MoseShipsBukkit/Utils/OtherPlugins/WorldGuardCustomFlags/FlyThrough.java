package MoseShipsBukkit.Utils.OtherPlugins.WorldGuardCustomFlags;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import MoseShipsBukkit.Utils.ConfigLinks.Config;
import MoseShipsBukkit.Utils.OtherPlugins.OtherPlugins;

import com.mewin.WGCustomFlags.WGCustomFlagsPlugin;
import com.mewin.WGCustomFlags.flags.CustomFlag;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.StateFlag;

public class FlyThrough extends CustomFlag<FlyThrough>{
		
	public FlyThrough(String name, RegionGroup rg) {
		super(name, rg);
	}
	
	public FlyThrough(String name){
		super(name);
	}

	@Override
	public FlyThrough loadFromDb(String name) {
		return new FlyThrough(name);
	}

	@Override
	public String saveToDb(FlyThrough flag) {
		return flag.getName();
	}

	@Override
	public Object marshal(FlyThrough flag) {
		return flag.getName();
	}

	@Override
	public FlyThrough parseInput(WorldGuardPlugin plugin, CommandSender sender, String input) throws InvalidFlagFormat {
		if (input.equalsIgnoreCase("ShipsFlyThrough")){
			return new FlyThrough(input);
		}else{
			throw new InvalidFlagFormat("The input format is not valid: " + input);
		}
	}

	@Override
	public FlyThrough unmarshal(Object obj) {
		return new FlyThrough((String)obj);
	}
	
	public static void activateFlyThrough(){
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		if (OtherPlugins.isWorldGuardCustomFlagsLoaded()){
			if(config.getBoolean("WorldGuardSupport.enabled")){
				WGCustomFlagsPlugin plugin = (WGCustomFlagsPlugin) Bukkit.getPluginManager().getPlugin("WGCustomFlags");
				plugin.addCustomFlag(new StateFlag("ShipsFlyThrough", true));
			}
		}
	}
}

package MoseShipsBukkit.Utils.OtherPlugins;

import java.util.List;
import java.util.Map.Entry;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.wimbli.WorldBorder.BorderData;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.StillShip.Vessel;
import MoseShipsBukkit.Utils.ConfigLinks.Config;

public class OtherPlugins {
	
	public static Economy ECO = null;
	
	public static boolean isWorldGuardLoaded(){
		Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
		if (plugin == null){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean isWorldGuardCustomFlagsLoaded(){
		Plugin plugin = Bukkit.getPluginManager().getPlugin("WGCustomFlags");
		if (plugin == null){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean isFactionsLoaded(){
		Plugin plugin = Bukkit.getPluginManager().getPlugin("Factions");
		if (plugin == null){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean isTownyLoaded(){
		Plugin plugin = Bukkit.getPluginManager().getPlugin("Towny");
		if (plugin == null){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean isTownyHookLoaded(){
		Plugin plugin = Bukkit.getPluginManager().getPlugin("ShipsTownyHook");
		if (plugin == null){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean isWorldBorderLoaded(){
		Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldBorder");
		if (plugin == null){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean isVaultLoaded(){
		Plugin plugin = Bukkit.getPluginManager().getPlugin("Vault");
		if (plugin == null){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean isCannonsLoaded(){
		Plugin plugin = Bukkit.getPluginManager().getPlugin("Cannons");
		if (plugin == null){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean isGriefPreventionLoaded(){
		Plugin plugin = Bukkit.getPluginManager().getPlugin("GriefPrevention");
		if (plugin == null){
			return false;
		}else{
			return true;
		}
	}
	
	public void setupVault() {
	    Plugin vault =  Bukkit.getPluginManager().getPlugin("Vault");
	    if (vault != null && vault instanceof net.milkbowl.vault.Vault) {
	        if (setupEconomy()) {
	            Bukkit.getConsoleSender().sendMessage(Ships.runShipsMessage("Vault is enabled in config, however, vault failed to find Eco plugin.", true));
	        }
	    }
	}
	  
	private boolean setupEconomy(){
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if (economyProvider != null) {
			ECO = economyProvider.getProvider();
		} else {
			return false;
		}
		return (ECO != null);
	}
	
	public static double pay(Vessel vessel){
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		if (config.getBoolean("VaultSupport.enabled")){
			double money = vessel.getCost();
			if (ECO.getBalance(vessel.getOwner()) >= money){
				ECO.depositPlayer(vessel.getOwner(), money);
			}
			return money;
		}
		return 0;
	}
	
	public static boolean isLocationOnFactionsLand(Location loc){
		Faction land = BoardColl.get().getFactionAt(PS.valueOf(loc));
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		List<String> areas = config.getStringList("FactionsSupport.FlyAreas");
		for (String area : areas){
			if (ChatColor.stripColor(land.getName()).equalsIgnoreCase(area)){
				return false;
			}
		}
		return true;
	}
	
	public static boolean isLocationInGriefPreventionClaim(Location loc, Vessel vessel){
		Claim claim = GriefPrevention.instance.dataStore.getClaimAt(loc, false, null);
		if (claim != null){
			YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
			if (config.getBoolean("GriefPreventionPluginSupport.OwnerBypass")){
				if (claim.ownerID.equals(vessel.getOwner().getUniqueId())){
					return false;
				}else if (config.getLongList("GriefPreventionPluginSupport.FlyAreas").contains(claim.getID())){
					return false;
				}
				return true;
			}
			if (config.getLongList("GriefPreventionPluginSupport.FlyAreas").contains(claim.getID())){
				return false;
			}
			return true;
		}
		return false;
	}
	
	public static boolean isLocationInWorldGuardRegion(Location loc){
		WorldGuardPlugin plugin = (WorldGuardPlugin)Bukkit.getPluginManager().getPlugin("WorldGuard");
		RegionManager regionManger = plugin.getRegionManager(loc.getWorld());
		ApplicableRegionSet regionsSet = regionManger.getApplicableRegions(loc);
		if (isWorldGuardCustomFlagsLoaded()){
			int regionCount = 0;
			for(ProtectedRegion region : regionsSet.getRegions()){
				for(Entry<Flag<?>, Object> flag : region.getFlags().entrySet()){
					if (flag.getKey().getName().equals("ShipsFlyThrough")){
						if (flag.getValue().toString().equals("DENY")){
							return true;
						}else if (flag.getValue().toString().equals("ALLOW")){
							regionCount++;
						}else{
							Bukkit.getConsoleSender().sendMessage(flag.getValue().toString());
						}
					}
				}
			}
			if (regionsSet.getRegions().size() == regionCount){
				return false;
			}
			return true;
		}else{
			if (regionsSet.size() != 0){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isLocationInTownyLand(Location loc){
		if (ShipsTownyHook.Boot.towny(loc)){
			return true;
		}
		return false;
	}
	
	public static boolean isLocationOutOfWorldBorder(Location loc){
		BorderData worldB = com.wimbli.WorldBorder.Config.Border(loc.getWorld().getName());
		if (worldB == null){
			return false;
		}else{
			if (worldB.insideBorder(loc.getX(), loc.getZ())){
				return false;
			}else{
				return true;
			}
		}
	}

}

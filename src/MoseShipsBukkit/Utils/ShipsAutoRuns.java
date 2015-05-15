package MoseShipsBukkit.Utils;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.ShipTypes.Types.AirShip;
import MoseShipsBukkit.StillShip.Vessel;
import MoseShipsBukkit.Utils.ConfigLinks.Config;

public class ShipsAutoRuns {
	
	public static HashMap<Vessel, Player> EOTAUTORUN = new HashMap<Vessel, Player>();
	
	public static void fallOutSky(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Ships.getPlugin(), new Runnable(){

			@Override
			public void run() {
				for(Vessel vessel : Vessel.getVessels()){
					if (vessel.getVesselType() instanceof AirShip){
						AirShip airship = (AirShip)vessel.getVesselType();
						if (airship.getFuelCount(vessel) == 0){
							vessel.moveVessel(MovementMethod.MOVE_DOWN, 1, null);
						}
					}
				}
				
			}
			
		}, 0, 130);
	}
	
	public static void EOTMove(){
		final YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Ships.getPlugin(), new Runnable(){

			@Override
			public void run() {
				for (Entry<Vessel, Player> vessel : EOTAUTORUN.entrySet()){
					vessel.getKey().updateStructure();
					vessel.getKey().moveVessel(MovementMethod.MOVE_FORWARD, 2, vessel.getValue());
				}
			}
			
		}, 0, config.getLong("EOT.repeat"));
	}
	
	public static void AutoMove(){
		final YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Ships.getPlugin(), new Runnable(){
		
			@Override
			public void run() {
				for(Vessel vessel : Vessel.getVessels()){
					Location loc = vessel.getSign().getLocation();
					Location loc2 = vessel.getAutoPilotTo();
					YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
					if (loc2 != null){
						vessel.updateStructure();
						if (loc.getY() >= config.getInt("AutoPilot.height")){
							Location loc3 = new Location(loc2.getWorld(), loc2.getX(), loc.getY(), loc2.getZ());
							if ((loc3.getX() == loc.getX()) && (loc3.getZ() == loc.getZ())){
								if (!vessel.safelyMoveTowardsLocation(loc2, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
									vessel.getOwner().getPlayer().sendMessage(Ships.runShipsMessage("AutoPilot has stopped.", false));
									vessel.setAutoPilotTo(null);
								}
							}else{
								if (!vessel.safelyMoveTowardsLocation(loc3, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
									vessel.getOwner().getPlayer().sendMessage(Ships.runShipsMessage("AutoPilot has stopped.", false));
									vessel.setAutoPilotTo(null);
								}
							}
						}else{
							if (!vessel.safelyMoveTowardsLocation(loc2, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
								vessel.getOwner().getPlayer().sendMessage(Ships.runShipsMessage("AutoPilot has stopped.", false));
								vessel.setAutoPilotTo(null);	
							}
						}
					}
				}
			}
			
		}, 0, config.getInt("AutoPilot.repeat"));
	}

}

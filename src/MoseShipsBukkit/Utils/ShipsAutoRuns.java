package MoseShipsBukkit.Utils;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
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
					if (loc2 != null){
						vessel.updateStructure();
						BlockFace facing = vessel.getFacingDirection();
						Bukkit.getConsoleSender().sendMessage(vessel.getName() + " / " + loc.getX() + "," + loc.getZ() + " / " + loc2.getX() + "," + loc2.getZ());
						if (facing.equals(BlockFace.SOUTH)){
							if (loc.getX() > loc2.getX()){
								if (loc.getY() < config.getInt("AutoPilot.height")){
									if (!vessel.moveVessel(MovementMethod.MOVE_UP, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}else{
									if (!vessel.moveVessel(MovementMethod.ROTATE_RIGHT, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}
							}else if (loc2.getX() > loc.getX()){
								if (loc.getY() < config.getInt("AutoPilot.height")){
									if (!vessel.moveVessel(MovementMethod.MOVE_UP, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}else{
									if (!vessel.moveVessel(MovementMethod.ROTATE_RIGHT, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}
							}else if (loc.getZ() > loc2.getZ()){
								if (loc.getY() < config.getInt("AutoPilot.height")){
									if (!vessel.moveVessel(MovementMethod.MOVE_UP, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}else{
									if (!vessel.moveVessel(MovementMethod.MOVE_BACKWARD, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}
							}else if (loc2.getZ() > loc.getZ()){
								if (loc.getY() < config.getInt("AutoPilot.height")){
									if (!vessel.moveVessel(MovementMethod.MOVE_UP, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}else{
									if (!vessel.moveVessel(MovementMethod.MOVE_FORWARD, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}
							}else if (loc.getY() > loc2.getY()){
								if (!vessel.moveVessel(MovementMethod.MOVE_UP, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
									vessel.setAutoPilotTo(null);
								}
							}else if (loc.getY() < loc2.getY()){
								if (!vessel.moveVessel(MovementMethod.MOVE_DOWN, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
									vessel.setAutoPilotTo(null);
								}
							}else{
								vessel.setAutoPilotTo(null);
							}
						}else if (facing.equals(BlockFace.WEST)){
							if (loc.getZ() > loc2.getZ()){
								if (loc.getY() < config.getInt("AutoPilot.height")){
									if (!vessel.moveVessel(MovementMethod.MOVE_UP, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}else{
									if (!vessel.moveVessel(MovementMethod.ROTATE_LEFT, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}
							}else if (loc2.getZ() > loc.getZ()){
								if (loc.getY() < config.getInt("AutoPilot.height")){
									if (!vessel.moveVessel(MovementMethod.MOVE_UP, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}else{
									if (!vessel.moveVessel(MovementMethod.ROTATE_RIGHT, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}
							}else if (loc.getX() > loc2.getX()){
								if (loc.getY() < config.getInt("AutoPilot.height")){
									if (!vessel.moveVessel(MovementMethod.MOVE_UP, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}else{
									if (!vessel.moveVessel(MovementMethod.MOVE_FORWARD, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}
							}else if (loc2.getX() > loc.getX()){
								if (loc.getY() < config.getInt("AutoPilot.height")){
									if (!vessel.moveVessel(MovementMethod.MOVE_UP, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}else{
									if (!vessel.moveVessel(MovementMethod.MOVE_BACKWARD, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}
							}else if (loc.getY() > loc2.getY()){
								if (!vessel.moveVessel(MovementMethod.MOVE_UP, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
									vessel.setAutoPilotTo(null);
								}
							}else if (loc.getY() < loc2.getY()){
								if (!vessel.moveVessel(MovementMethod.MOVE_DOWN, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
									vessel.setAutoPilotTo(null);
								}
							}else{
								vessel.setAutoPilotTo(null);
							}
						}else if (facing.equals(BlockFace.EAST)){
							if (loc.getZ() > loc2.getZ()){
								if (loc.getY() < config.getInt("AutoPilot.height")){
									if (!vessel.moveVessel(MovementMethod.MOVE_UP, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}else{
									if (!vessel.moveVessel(MovementMethod.ROTATE_LEFT, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}
							}else if (loc2.getZ() > loc.getZ()){
								if (loc.getY() < config.getInt("AutoPilot.height")){
									if (!vessel.moveVessel(MovementMethod.MOVE_UP, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}else{
									if (!vessel.moveVessel(MovementMethod.ROTATE_RIGHT, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}
							}else if (loc.getX() > loc2.getX()){
								if (loc.getY() < config.getInt("AutoPilot.height")){
									if (!vessel.moveVessel(MovementMethod.MOVE_UP, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}else{
									if (!vessel.moveVessel(MovementMethod.MOVE_BACKWARD, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}
							}else if (loc2.getX() > loc.getX()){
								if (loc.getY() < config.getInt("AutoPilot.height")){
									if (!vessel.moveVessel(MovementMethod.MOVE_UP, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}else{
									if (!vessel.moveVessel(MovementMethod.MOVE_FORWARD, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}
							}else if (loc.getY() > loc2.getY()){
								if (!vessel.moveVessel(MovementMethod.MOVE_UP, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
									vessel.setAutoPilotTo(null);
								}
							}else if (loc.getY() < loc2.getY()){
								if (!vessel.moveVessel(MovementMethod.MOVE_DOWN, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
									vessel.setAutoPilotTo(null);
								}
							}else{
								vessel.setAutoPilotTo(null);
							}
						}else if (facing.equals(BlockFace.NORTH)){
							if (loc.getX() > loc2.getX()){
								if (loc.getY() < config.getInt("AutoPilot.height")){
									if (!vessel.moveVessel(MovementMethod.MOVE_UP, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}else{
									if (!vessel.moveVessel(MovementMethod.ROTATE_RIGHT, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}
							}else if (loc2.getX() > loc.getX()){
								if (loc.getY() < config.getInt("AutoPilot.height")){
									if (!vessel.moveVessel(MovementMethod.MOVE_UP, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}else{
									if (!vessel.moveVessel(MovementMethod.ROTATE_RIGHT, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}
							}else if (loc.getZ() > loc2.getZ()){
								if (loc.getY() < config.getInt("AutoPilot.height")){
									if (!vessel.moveVessel(MovementMethod.MOVE_UP, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}else{
									if (!vessel.moveVessel(MovementMethod.MOVE_FORWARD, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}
							}else if (loc2.getZ() > loc.getZ()){
								if (loc.getY() < config.getInt("AutoPilot.height")){
									if (!vessel.moveVessel(MovementMethod.MOVE_UP, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}else{
									if (!vessel.moveVessel(MovementMethod.MOVE_BACKWARD, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
										vessel.setAutoPilotTo(null);
									}
								}
							}else if (loc.getY() > loc2.getY()){
								if (!vessel.moveVessel(MovementMethod.MOVE_UP, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
									vessel.setAutoPilotTo(null);
								}
							}else if (loc.getY() < loc2.getY()){
								if (!vessel.moveVessel(MovementMethod.MOVE_DOWN, vessel.getVesselType().getDefaultSpeed(), vessel.getOwner().getPlayer())){
									vessel.setAutoPilotTo(null);
								}
							}else{
								vessel.setAutoPilotTo(null);
							}
						}
					}
				}
			}
			
		}, 0, config.getInt("AutoPilot.repeat"));
	}

}

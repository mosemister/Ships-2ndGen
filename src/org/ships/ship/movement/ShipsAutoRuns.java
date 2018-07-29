package org.ships.ship.movement;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.ships.configuration.Config;
import org.ships.plugin.Ships;
import org.ships.ship.LoadableShip;
import org.ships.ship.Ship;

public class ShipsAutoRuns {
	public static HashMap<Ship, OfflinePlayer> EOTAUTORUN = new HashMap<>();

	public static void fallOutSky() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Ships.getPlugin(), () -> {
			for (Ship vessel : LoadableShip.getShips()) {
				if (!vessel.getVesselType().shouldFall(vessel))
					continue;
				vessel.moveTowards(MovementMethod.MOVE_DOWN, 1, null);
			}
		}, 0, 130);
	}

	public static void EOTMove() {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Ships.getPlugin(), () -> {
			for (Map.Entry<Ship, OfflinePlayer> vessel : EOTAUTORUN.entrySet()) {
				vessel.getKey().updateStructure();
				vessel.getKey().moveTowards(MovementMethod.MOVE_FORWARD, 2, vessel.getValue());
			}
		}, 0, config.getLong("Structure.Signs.EOT.repeat"));
	}

	public static void AutoMove() {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Ships.getPlugin(), () -> {
			for (Ship vessel : LoadableShip.getShips()) {
				Location loc = vessel.getLocation();
				AutoPilotData data = vessel.getAutoPilotData();
				YamlConfiguration config1 = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
				if (data == null)
					continue;
				vessel.updateStructure();
				if (loc.getY() >= config1.getInt("Structure.Signs.AutoPilot.height")) {
					Location loc3 = new Location(data.getMovingTo().getWorld(), data.getMovingTo().getX(), loc.getY(), data.getMovingTo().getZ());
					if (loc3.getX() == loc.getX() && loc3.getZ() == loc.getZ()) {
						if (vessel.moveTowardsLocation(data.getMovingTo(), data.getSpeed(), data.getPlayer()))
							continue;
						vessel.getOwner().getPlayer().sendMessage(Ships.runShipsMessage("AutoPilot has stopped.", false));
						vessel.setAutoPilotData(null);
						continue;
					}
					if (vessel.moveTowardsLocation(loc3, data.getSpeed(), data.getPlayer()))
						continue;
					vessel.getOwner().getPlayer().sendMessage(Ships.runShipsMessage("AutoPilot has stopped.", false));
					vessel.setAutoPilotData(null);
					continue;
				}
				if (vessel.moveTowardsLocation(data.getMovingTo(), data.getSpeed(), data.getPlayer()))
					continue;
				vessel.getOwner().getPlayer().sendMessage(Ships.runShipsMessage("AutoPilot has stopped.", false));
				vessel.setAutoPilotData(null);
			}
		}, 0, config.getInt("Structure.Signs.AutoPilot.repeat"));
	}
}

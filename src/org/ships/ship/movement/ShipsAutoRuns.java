package org.ships.ship.movement;

import java.util.HashMap;
import java.util.Map.Entry;

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
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Ships.getPlugin(), new Runnable() {

			@Override
			public void run() {
				for (Ship vessel : LoadableShip.getShips()) {
					if (vessel.getVesselType().shouldFall(vessel)) {
						vessel.moveTowards(MovementMethod.MOVE_DOWN, 1, null);
					}
				}

			}

		}, 0, 130);
	}

	public static void EOTMove() {
		final YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Ships.getPlugin(), new Runnable() {

			@Override
			public void run() {
				for (Entry<Ship, OfflinePlayer> vessel : EOTAUTORUN.entrySet()) {
					vessel.getKey().updateStructure();
					vessel.getKey().moveTowards(MovementMethod.MOVE_FORWARD, 2, vessel.getValue());
				}
			}

		}, 0, config.getLong("Structure.Signs.EOT.repeat"));
	}

	public static void AutoMove() {
		final YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Ships.getPlugin(), new Runnable() {

			@Override
			public void run() {
				for (Ship vessel : LoadableShip.getShips()) {
					Location loc = vessel.getLocation();
					AutoPilotData data = vessel.getAutoPilotData();
					YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
					if (data != null) {
						vessel.updateStructure();
						if (loc.getY() >= config.getInt("Structure.Signs.AutoPilot.height")) {
							Location loc3 = new Location(data.getMovingTo().getWorld(), data.getMovingTo().getX(),
									loc.getY(), data.getMovingTo().getZ());
							if ((loc3.getX() == loc.getX()) && (loc3.getZ() == loc.getZ())) {
								if (!vessel.moveTowardsLocation(data.getMovingTo(), data.getSpeed(),
										data.getPlayer())) {
									vessel.getOwner().getPlayer()
											.sendMessage(Ships.runShipsMessage("AutoPilot has stopped.", false));
									vessel.setAutoPilotData(null);
								}
							} else {
								if (!vessel.moveTowardsLocation(loc3, data.getSpeed(), data.getPlayer())) {
									vessel.getOwner().getPlayer()
											.sendMessage(Ships.runShipsMessage("AutoPilot has stopped.", false));
									vessel.setAutoPilotData(null);
								}
							}
						} else {
							if (!vessel.moveTowardsLocation(data.getMovingTo(), data.getSpeed(),
									data.getPlayer())) {
								vessel.getOwner().getPlayer()
										.sendMessage(Ships.runShipsMessage("AutoPilot has stopped.", false));
								vessel.setAutoPilotData(null);
							}
						}
					}
				}
			}

		}, 0, config.getInt("Structure.Signs.AutoPilot.repeat"));
	}
}

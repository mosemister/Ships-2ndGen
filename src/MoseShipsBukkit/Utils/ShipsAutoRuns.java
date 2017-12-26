package MoseShipsBukkit.Utils;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.MovingShip.AutoPilotData;
import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.ShipsTypes.HookTypes.Cell;
import MoseShipsBukkit.StillShip.Vessel.Vessel;
import MoseShipsBukkit.Utils.ConfigLinks.Config;

public class ShipsAutoRuns {

	public static HashMap<Vessel, OfflinePlayer> EOTAUTORUN = new HashMap<Vessel, OfflinePlayer>();

	@Deprecated
	public static void SolorCell() {
		Bukkit.getConsoleSender().sendMessage(Ships.runShipsMessage("SolarCell active", false));
		final YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Ships.getPlugin(), new Runnable() {

			@Override
			public void run() {
				for (Vessel vessel : Vessel.getVessels()) {
					Bukkit.getConsoleSender()
							.sendMessage(Ships.runShipsMessage("vessel found" + vessel.getName(), false));
					if (vessel.getVesselType() instanceof Cell) {
						Bukkit.getConsoleSender().sendMessage(Ships.runShipsMessage("Vessel is cell type", false));
						Cell plates = (Cell) vessel.getVesselType();
						plates.addCellPower(vessel);
					}
				}

			}

		}, 0, config.getInt("Structure.Signs.Cell.repeat"));
	}

	public static void fallOutSky() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Ships.getPlugin(), new Runnable() {

			@Override
			public void run() {
				for (Vessel vessel : Vessel.getVessels()) {
					if (vessel.getVesselType().shouldFall(vessel)) {
						vessel.syncMoveVessel(MovementMethod.MOVE_DOWN, 1, null);
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
				for (Entry<Vessel, OfflinePlayer> vessel : EOTAUTORUN.entrySet()) {
					vessel.getKey().updateStructure();
					vessel.getKey().syncMoveVessel(MovementMethod.MOVE_FORWARD, 2, vessel.getValue());
				}
			}

		}, 0, config.getLong("Structure.Signs.EOT.repeat"));
	}

	public static void AutoMove() {
		final YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Ships.getPlugin(), new Runnable() {

			@Override
			public void run() {
				for (Vessel vessel : Vessel.getVessels()) {
					Location loc = vessel.getSign().getLocation();
					AutoPilotData data = vessel.getAutoPilotData();
					YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
					if (data != null) {
						vessel.updateStructure();
						if (loc.getY() >= config.getInt("Structure.Signs.AutoPilot.height")) {
							Location loc3 = new Location(data.getMovingTo().getWorld(), data.getMovingTo().getX(),
									loc.getY(), data.getMovingTo().getZ());
							if ((loc3.getX() == loc.getX()) && (loc3.getZ() == loc.getZ())) {
								if (!vessel.syncSafelyMoveTowardsLocation(data.getMovingTo(), data.getSpeed(),
										data.getPlayer())) {
									vessel.getOwner().getPlayer()
											.sendMessage(Ships.runShipsMessage("AutoPilot has stopped.", false));
									vessel.setAutoPilotTo(null);
								}
							} else {
								if (!vessel.syncSafelyMoveTowardsLocation(loc3, data.getSpeed(), data.getPlayer())) {
									vessel.getOwner().getPlayer()
											.sendMessage(Ships.runShipsMessage("AutoPilot has stopped.", false));
									vessel.setAutoPilotTo(null);
								}
							}
						} else {
							if (!vessel.syncSafelyMoveTowardsLocation(data.getMovingTo(), data.getSpeed(),
									data.getPlayer())) {
								vessel.getOwner().getPlayer()
										.sendMessage(Ships.runShipsMessage("AutoPilot has stopped.", false));
								vessel.setAutoPilotTo(null);
							}
						}
					}
				}
			}

		}, 0, config.getInt("Structure.Signs.AutoPilot.repeat"));
	}
}

package MoseShipsBukkit.Vessel.Common.OpenLoader;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.Common.Static.StaticShipType;

public abstract class Ships5Loader implements OpenRAWLoader {

	@Override
	public Optional<LiveShip> RAWLoad(File file) {
		BasicConfig config = new BasicConfig(file);
		String name = file.getName().substring(0, file.getName().length() - 4);
		String typeVersion = config.get(String.class, "ShipsData.Type");
		StaticShipType shipType = null;
		for (StaticShipType type : StaticShipType.TYPES) {
			if (type.getName().equals(typeVersion)) {
				shipType = type;
			}
		}
		if (shipType == null) {
			return Optional.empty();
		}
		String uuids = config.get(String.class, "ShipsData.Player.Name");
		OfflinePlayer player = null;
		int max = config.get(Integer.class, "ShipsData.Config.Block.Max");
		int min = config.get(Integer.class, "ShipsData.Config.Block.Min");
		int speed = config.get(Integer.class, "ShipsData.Config.Speed.Engine");
		String signLocation = config.get(String.class, "ShipsData.Location.Sign");
		double signX = 0;
		double signY = 0;
		double signZ = 0;
		World world = null;
		String teleportLocation = config.get(String.class, "ShipsData.Location.Teleport");
		double telX = 0;
		double telY = 0;
		double telZ = 0;
		Location sign = new Location(world, signX, signY, signZ);
		Location teleport = new Location(world, telX, telY, telZ);
		if (uuids == null) {
			return Optional.empty();
		} else {
			player = Bukkit.getOfflinePlayer(UUID.fromString(uuids));
		}
		if (signLocation == null) {
			return Optional.empty();
		} else {
			String[] locS = signLocation.split(",");
			signX = Double.parseDouble(locS[0]);
			signY = Double.parseDouble(locS[1]);
			signZ = Double.parseDouble(locS[2]);
			world = Bukkit.getWorld(locS[3]);
		}
		if (teleportLocation == null) {
			return Optional.empty();
		} else {
			String[] locS = teleportLocation.split(",");
			telX = Double.parseDouble(locS[0]);
			telY = Double.parseDouble(locS[1]);
			telZ = Double.parseDouble(locS[2]);
		}
		Optional<LiveShip> opShip = shipType.createVessel(name, sign.getBlock());
		if (opShip.isPresent()) {
			LiveShip ship = opShip.get();
			ship.setOwner(player);
			ship.setMaxBlocks(max);
			ship.setMinBlocks(min);
			ship.setTeleportToLocation(teleport);
			ship.setEngineSpeed(speed);
			ship.setBoostSpeed(speed + 1);
		}
		return Optional.empty();
	}

	@Override
	public OpenRAWLoader RAWSave(LiveShip ship, File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getError(File file) {
		// TODO Auto-generated method stub
		return null;
	}

}

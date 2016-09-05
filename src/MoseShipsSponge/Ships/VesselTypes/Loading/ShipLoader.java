package MoseShipsSponge.Ships.VesselTypes.Loading;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;

import MoseShips.Stores.OneStore;
import MoseShips.Stores.TwoStore;
import MoseShipsSponge.Configs.BasicConfig;
import MoseShipsSponge.Ships.ShipsData;
import MoseShipsSponge.Ships.VesselTypes.LoadableShip;
import MoseShipsSponge.Ships.VesselTypes.Satic.StaticShipType;
import MoseShipsSponge.Ships.VesselTypes.Satic.StaticShipTypeUtil;

public class ShipLoader {

	public static Optional<LoadableShip> loadShip(File file) {
		TwoStore<LoadableShip, ShipLoadingError> opShip = pLoadShip(file);
		return Optional.ofNullable(opShip.getFirst());
	}

	public static Optional<LoadableShip> loadShip(String name) {
		Optional<File> opFile = getFile(name);
		if (opFile.isPresent()) {
			return Optional.ofNullable(pLoadShip(opFile.get()).getFirst());
		}
		return Optional.empty();
	}

	public static ShipLoadingError getError(File file) {
		return pLoadShip(file).getSecond();
	}

	public static ShipLoadingError getError(String name) {
		Optional<File> opFile = getFile(name);
		if (opFile.isPresent()) {
			return pLoadShip(opFile.get()).getSecond();
		}
		return ShipLoadingError.MISSING_FILE;
	}

	private static Optional<File> getFile(String name) {
		File root = new File("/config/Ships/VesselData");
		OneStore<File> file = new OneStore<File>(null);
		for(StaticShipType type : StaticShipType.TYPES){
			File folder = new File(root, type.getName());
			Bukkit.getServer().getConsoleSender().sendMessage(folder.getAbsolutePath());
			File[] files = folder.listFiles();
			if (files != null) {
				Bukkit.getServer().getConsoleSender().sendMessage("files dont equal null");
				for (File sFile : files) {
					Bukkit.getServer().getConsoleSender().sendMessage(sFile.getAbsolutePath());
					if (sFile.getName().equals(name + ".conf")) {
						file.setFirst(sFile);
						break;
					}
				}
			}
		}
		return Optional.ofNullable(file.getFirst());
	}

	private static TwoStore<LoadableShip, ShipLoadingError> pLoadShip(File file) {
		BasicConfig config = new BasicConfig(file);

		String[] sLic = config.get(String.class, ShipsData.DATABASE_BLOCK).split(",");
		String name = config.get(String.class, ShipsData.DATABASE_NAME);
		String sType = config.get(String.class, ShipsData.DATABASE_TYPE);
		String sPilot = config.get(String.class, ShipsData.DATABASE_PILOT);
		String[] sStructure = config.get(String.class, ShipsData.DATABASE_STRUCTURE).split(",");
		String sSubPilots = config.get(String.class, ShipsData.DATABASE_SUB_PILOTS);
		String[] sTel = config.get(String.class, ShipsData.DATABASE_TELEPORT).split(",");
		String sWorld = config.get(String.class, ShipsData.DATABASE_WORLD);

		if (sWorld == null) {
			return new TwoStore<LoadableShip, ShipLoadingError>(null, ShipLoadingError.UNREADABLE_WORLD);
		}
		World world = Bukkit.getServer().getWorld(sWorld);
		if (world != null) {
			Location lic = null;
			try {
				int Xlic = Integer.parseInt(sLic[0]);
				int Ylic = Integer.parseInt(sLic[1]);
				int Zlic = Integer.parseInt(sLic[2]);
				lic = new Location(world, Xlic, Ylic, Zlic);
			} catch (NumberFormatException e) {
				return new TwoStore<LoadableShip, ShipLoadingError>(null, ShipLoadingError.UNREADABLE_LICENCE);
			}
			Location tel = null;
			try {
				int Xtel = Integer.parseInt(sTel[0]);
				int Ytel = Integer.parseInt(sTel[1]);
				int Ztel = Integer.parseInt(sTel[2]);
				lic = new Location(world, Xtel, Ytel, Ztel);
			} catch (NumberFormatException e) {
				tel = lic;
			}
			Optional<StaticShipType> opType = StaticShipTypeUtil.getType(sType);
			if (opType.isPresent()) {
				StaticShipType type = opType.get();
				ShipsData data = new ShipsData(name, lic.getBlock(), tel);
				if (sPilot != null) {
					OfflinePlayer user = Bukkit.getOfflinePlayer(UUID.fromString(sPilot));
					if (user != null) {
						data.setOwner(user);
					} else {
						return new TwoStore<LoadableShip, ShipLoadingError>(null, ShipLoadingError.UNKNOWN_PLAYER);
					}
				}

				List<Block> structure = new ArrayList<Block>();
				int posX = 0;
				int posY = 0;
				int target = 0;
				for (String value : sStructure) {
					try {
						int pos = Integer.parseInt(value);
						switch (target) {
						case 0:
							posX = pos;
						case 1:
							posY = pos;
						case 2:
							structure.add(new Location(world, posX, posY, pos).getBlock());
						}
					} catch (NumberFormatException e) {
						break;
					}
				}
				data.setBasicStructure(structure, lic.getBlock());

				if (sSubPilots != null) {
					List<OfflinePlayer> subPilots = new ArrayList<OfflinePlayer>();
					for (String sUuid : sSubPilots.split(",")) {
						subPilots.add(Bukkit.getOfflinePlayer(UUID.fromString(sUuid)));
					}
					data.getSubPilots().addAll(subPilots);
				}

				Optional<LoadableShip> opShip = type.loadVessel(data, config);
				if (opShip.isPresent()) {
					return new TwoStore<LoadableShip, ShipLoadingError>(opShip.get(), ShipLoadingError.NOT_CURRUPT);
				} else {
					return new TwoStore<LoadableShip, ShipLoadingError>(null, ShipLoadingError.LOADER_ISSUE);
				}
			} else {
				return new TwoStore<LoadableShip, ShipLoadingError>(null, ShipLoadingError.UNKNOWN_SHIP_TYPE);
			}
		} else {
			return new TwoStore<LoadableShip, ShipLoadingError>(null, ShipLoadingError.WORLD_MISSING);
		}
	}

}

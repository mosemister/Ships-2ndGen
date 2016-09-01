package MoseShipsSponge.Ships.VesselTypes.Loading;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShips.Stores.OneStore;
import MoseShips.Stores.TwoStore;
import MoseShipsSponge.ShipsMain;
import MoseShipsSponge.Configs.BasicConfig;
import MoseShipsSponge.Ships.ShipsData;
import MoseShipsSponge.Ships.VesselTypes.LoadableShip;
import MoseShipsSponge.Ships.VesselTypes.StaticShipType;

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
		OneStore<File> file = new OneStore<>(null);
		StaticShipType.TYPES.stream().forEach(t -> {
			File folder = new File(root, t.getName());
			ShipsMain.getPlugin().getGame().getServer().getConsole().sendMessage(Text.of(folder));
			File[] files = folder.listFiles();
			if (files != null) {
				ShipsMain.getPlugin().getGame().getServer().getConsole().sendMessage(Text.of("files dont equal null"));
				for (File sFile : files) {
					ShipsMain.getPlugin().getGame().getServer().getConsole().sendMessage(Text.of(sFile));
					if (sFile.getName().equals(name + ".conf")) {
						file.setFirst(sFile);
						break;
					}
				}
			}
		});
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
			return new TwoStore<>(null, ShipLoadingError.UNREADABLE_WORLD);
		}
		Optional<World> opWorld = ShipsMain.getPlugin().getGame().getServer().getWorld(sWorld);
		if (opWorld.isPresent()) {
			World world = opWorld.get();
			Location<World> lic = null;
			try {
				int Xlic = Integer.parseInt(sLic[0]);
				int Ylic = Integer.parseInt(sLic[1]);
				int Zlic = Integer.parseInt(sLic[2]);
				lic = world.getLocation(Xlic, Ylic, Zlic);
			} catch (NumberFormatException e) {
				return new TwoStore<>(null, ShipLoadingError.UNREADABLE_LICENCE);
			}
			Location<World> tel = null;
			try {
				int Xtel = Integer.parseInt(sTel[0]);
				int Ytel = Integer.parseInt(sTel[1]);
				int Ztel = Integer.parseInt(sTel[2]);
				lic = world.getLocation(Xtel, Ytel, Ztel);
			} catch (NumberFormatException e) {
				tel = lic;
			}
			Optional<StaticShipType> opType = StaticShipType.TYPES.stream().filter(t -> t.getName().equals(sType))
					.findFirst();
			if (opType.isPresent()) {
				StaticShipType type = opType.get();
				ShipsData data = new ShipsData(name, lic, tel);
				if (sPilot != null) {
					Optional<User> opUser = ShipsMain.getUser(UUID.fromString(sPilot));
					if (opUser.isPresent()) {
						data.setOwner(opUser.get());
					} else {
						return new TwoStore<>(null, ShipLoadingError.UNKNOWN_PLAYER);
					}
				}

				List<Location<World>> structure = new ArrayList<>();
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
							structure.add(world.getLocation(posX, posY, pos));
						}
					} catch (NumberFormatException e) {
						break;
					}
				}
				data.setBasicStructure(structure, lic);

				if (sSubPilots != null) {
					List<User> subPilots = new ArrayList<>();
					for (String sUuid : sSubPilots.split(",")) {
						ShipsMain.getUser(UUID.fromString(sUuid));
					}
					data.getSubPilots().addAll(subPilots);
				}

				Optional<LoadableShip> opShip = type.loadVessel(data, config);
				if (opShip.isPresent()) {
					return new TwoStore<>(opShip.get(), ShipLoadingError.NOT_CURRUPT);
				} else {
					return new TwoStore<>(null, ShipLoadingError.LOADER_ISSUE);
				}
			} else {
				return new TwoStore<>(null, ShipLoadingError.UNKNOWN_SHIP_TYPE);
			}
		} else {
			return new TwoStore<>(null, ShipLoadingError.WORLD_MISSING);
		}
	}

}

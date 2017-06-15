package MoseShipsSponge.Algorthum.BlockFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Configs.ShipsConfig;
import MoseShipsSponge.ShipBlock.Signs.ShipLicenceSign;
import MoseShipsSponge.ShipBlock.Signs.ShipSign;
import MoseShipsSponge.Utils.ShipSignUtil;

public interface BasicBlockFinder {

	static final List<BasicBlockFinder> LIST = new ArrayList<>();

	public static final BasicBlockFinder SHIPS5 = new Ships5BlockFinder();

	public List<Location<World>> getConnectedBlocks(int limit, Location<World> loc);

	public String getName();

	public static BasicBlockFinder getConfigSelected() {
		String name = ShipsConfig.CONFIG.get(String.class, ShipsConfig.PATH_ALGORITHMS_BLOCKFINDER);
		if (name == null) {
			return SHIPS5;
		}
		Optional<BasicBlockFinder> opBlock = getFinder(name);
		if (opBlock.isPresent()) {
			return opBlock.get();
		}
		return SHIPS5;
	}

	public static boolean isValid(List<Location<World>> list) {
		return list.stream().anyMatch(l -> {
			if (l.getTileEntity().isPresent()) {
				if (l.getTileEntity().get() instanceof Sign) {
					Sign sign = (Sign) l.getTileEntity().get();
					Optional<ShipSign> opSign = ShipSignUtil.getSign(sign);
					if (opSign.isPresent()) {
						ShipSign sign2 = opSign.get();
						if (sign2 instanceof ShipLicenceSign) {
							return true;
						}
					}
				}
			}
			return false;
		});
	}

	public static List<BasicBlockFinder> getFinders() {
		List<BasicBlockFinder> finder = new ArrayList<>(LIST);
		finder.add(SHIPS5);
		return finder;
	}

	public static Optional<BasicBlockFinder> getFinder(String name) {
		return getFinders().stream().filter(f -> f.getName().equalsIgnoreCase(name)).findFirst();
	}

}

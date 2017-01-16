package MoseShipsBukkit.BlockFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import MoseShipsBukkit.BlockFinder.Algorithms.Prototype3;
import MoseShipsBukkit.BlockFinder.Algorithms.Prototype4;
import MoseShipsBukkit.Configs.Files.ShipsConfig;
import MoseShipsBukkit.Signs.ShipSign;
import MoseShipsBukkit.Signs.ShipSignUtil;
import MoseShipsBukkit.Signs.Types.ShipLicenceSign;

public class BlockFinderUtils {

	public static final BasicBlockFinder SHIPS5 = new Prototype3();
	public static final BasicBlockFinder SHIPS6 = new Prototype4();

	public static BasicBlockFinder getConfigSelected() {
		String name = ShipsConfig.CONFIG.get(String.class, ShipsConfig.PATH_ALGORITHMS_BLOCKFINDER);
		Optional<BasicBlockFinder> opBlock = getFinder(name);
		if (opBlock.isPresent()) {
			return opBlock.get();
		}
		return SHIPS5;
	}

	public static boolean isValid(List<Block> list) {
		for (Block block : list) {
			if (block.getState() instanceof Sign) {
				Sign sign = (Sign) block.getState();
				Optional<ShipSign> opType = ShipSignUtil.getSign(sign);
				if (opType.isPresent()) {
					if (opType.get() instanceof ShipLicenceSign) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static List<BasicBlockFinder> getFinders() {
		List<BasicBlockFinder> finder = new ArrayList<BasicBlockFinder>(BasicBlockFinder.LIST);
		finder.add(SHIPS5);
		finder.add(SHIPS6);
		return finder;
	}

	public static Optional<BasicBlockFinder> getFinder(String name) {
		for (BasicBlockFinder finder : getFinders()) {
			if (finder.getName().equalsIgnoreCase(name)) {
				return Optional.of(finder);
			}
		}
		return Optional.empty();
	}
	
	public static BasicBlockFinder getFinder(Class<? extends BasicBlockFinder> finderType){
		for(BasicBlockFinder finder: getFinders()){
			if(finderType.isInstance(finder)){
				return finder;
			}
		}
		return null;
	}

}

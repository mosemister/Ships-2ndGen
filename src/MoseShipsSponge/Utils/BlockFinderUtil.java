package MoseShipsSponge.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Algorthum.BlockFinder.BasicBlockFinder;
import MoseShipsSponge.Algorthum.BlockFinder.Ships5BlockFinder;
import MoseShipsSponge.Algorthum.BlockFinder.Ships6BlockFinder;
import MoseShipsSponge.Configs.ShipsConfig;
import MoseShipsSponge.ShipBlock.Signs.ShipSign;

public class BlockFinderUtil {
	
	public static final BasicBlockFinder SHIPS5 = new Ships5BlockFinder();
	public static final BasicBlockFinder SHIPS6 = new Ships6BlockFinder();

	public static BasicBlockFinder getConfigSelected(){
		String name = ShipsConfig.CONFIG.get(String.class, ShipsConfig.PATH_ALGORITHMS_BLOCKFINDER);
		Optional<BasicBlockFinder> opBlock = getFinder(name);
		if(opBlock.isPresent()){
			return opBlock.get();
		}
		return SHIPS5;
	}
	
	public static boolean isValid(List<Location<World>> list){
		return list.stream().anyMatch(b -> {
			Optional<TileEntity> opEntity = b.getTileEntity();
			if((opEntity.isPresent()) && (opEntity.get() instanceof Sign)){
				Sign sign = (Sign)opEntity.get();
				Optional<ShipSign> opSign = ShipSignUtil.getSign(sign);
				if((opSign.isPresent()) && (opSign.get() instanceof ShipLicenceSign)){
					return true;
				}
			}
			return false;
		});
	}
	
	public static List<BasicBlockFinder> getFinders(){
		List<BasicBlockFinder> finder = new ArrayList<>(BasicBlockFinder.LIST);
		finder.add(SHIPS5);
		finder.add(SHIPS6);
		return finder;
	}
	
	public static Optional<BasicBlockFinder> getFinder(String name){
		return getFinders().stream().filter(f -> f.getName().equalsIgnoreCase(name)).findAny();
	}
	
	public static BasicBlockFinder getFinder(Class<? extends BasicBlockFinder> finder){
		return getFinders().stream().filter(f -> finder.isInstance(f)).findAny().get();
	}
}

package MoseShipsSponge.BlockFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.BlockFinder.Algorithms.Prototype3;
import MoseShipsSponge.Configs.Files.ShipsConfig;
import MoseShipsSponge.Signs.ShipsSigns;
import MoseShipsSponge.Signs.ShipsSigns.SignType;

public interface BasicBlockFinder {
	
	static final List<BasicBlockFinder> LIST = new ArrayList<>();
	
	public static final BasicBlockFinder SHIPS5 = new Prototype3();
	
	public List<Location<World>> getConnectedBlocks(int limit, Location<World> loc);
	public String getName();
	
	public static BasicBlockFinder getConfigSelected(){
		String name = ShipsConfig.CONFIG.get(String.class, ShipsConfig.PATH_ALGORITHMS_BLOCKFINDER);
		Optional<BasicBlockFinder> opBlock = getFinder(name);
		if(opBlock.isPresent()){
			return opBlock.get();
		}
		return SHIPS5;
	}
	
	public static boolean isValid(List<Location<World>> list){
		return list.stream().anyMatch(l ->{
			if(l.getTileEntity().isPresent()){
				if(l.getTileEntity().get() instanceof Sign){
					Sign sign = (Sign)l.getTileEntity().get();
					Optional<SignType> opType = ShipsSigns.getSignType(sign);
					if(opType.isPresent()){
						if(opType.get().equals(SignType.LICENCE)){
							return true;
						}
					}
				}
			}
			return false;
		});
	}
	
	public static List<BasicBlockFinder> getFinders(){
		List<BasicBlockFinder> finder = new ArrayList<>(LIST);
		finder.add(SHIPS5);
		return finder;
	}
	
	public static Optional<BasicBlockFinder> getFinder(String name){
		return getFinders().stream().filter(f -> f.getName().equalsIgnoreCase(name)).findFirst();
	}

}

package MoseShipsSponge.BlockFinder;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.BlockFinder.Algorithms.Prototype3;
import MoseShipsSponge.Signs.ShipsSigns;
import MoseShipsSponge.Signs.ShipsSigns.SignType;

public abstract class BasicBlockFinder {
	
	public static final BasicBlockFinder SHIPS5 = new Prototype3();
	
	public abstract List<Location<World>> getConnectedBlocks(int limit, Location<World> loc);
	
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

}

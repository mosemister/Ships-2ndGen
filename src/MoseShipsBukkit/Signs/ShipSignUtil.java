package MoseShipsBukkit.Signs;

import java.util.Optional;

import org.bukkit.block.Sign;

public class ShipSignUtil {
	
	public static Optional<ShipSign> getSign(String line){
		for(ShipSign sign : ShipSign.SHIP_SIGNS){
			if(sign.getFirstLine().equalsIgnoreCase(line)){
				return Optional.of(sign);
			}
		}
		return Optional.empty();
	}
	
	public static Optional<ShipSign> getSign(Sign sign2){
		for(ShipSign sign : ShipSign.SHIP_SIGNS){
			if(sign.isSign(sign2)){
				return Optional.of(sign);
			}
		}
		return Optional.empty();
	}

}

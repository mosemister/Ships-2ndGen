package MoseShipsBukkit.Utils;

import org.bukkit.block.Sign;

import MoseShipsBukkit.ShipBlock.Signs.ShipSign;

public class ShipSignUtil {

	public static SOptional<ShipSign> getSign(String line) {
		for (ShipSign sign : ShipSign.SHIP_SIGNS) {
			for(String first : sign.getFirstLine()){
				if(first.equalsIgnoreCase(line)){
					return new SOptional<ShipSign>(sign);
				}
			}
		}
		return new SOptional<ShipSign>();
	}

	public static SOptional<ShipSign> getSign(Sign sign2) {
		for (ShipSign sign : ShipSign.SHIP_SIGNS) {
			if (sign.isSign(sign2)) {
				return new SOptional<ShipSign>(sign);
			}
		}
		return new SOptional<ShipSign>();
	}

}

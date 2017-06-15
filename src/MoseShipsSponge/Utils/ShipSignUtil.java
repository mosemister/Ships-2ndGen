package MoseShipsSponge.Utils;

import java.util.Optional;

import org.spongepowered.api.block.tileentity.Sign;

import MoseShipsSponge.ShipBlock.Signs.ShipSign;

public class ShipSignUtil {

	public static Optional<ShipSign> getSign(String line) {
		return ShipSign.SHIP_SIGNS.stream()
				.filter(s -> s.getFirstLine().stream().anyMatch(l -> l.equalsIgnoreCase(line))).findAny();
	}

	public static Optional<ShipSign> getSign(Sign sign) {
		return ShipSign.SHIP_SIGNS.stream().filter(s -> s.isSign(sign)).findAny();
	}

}

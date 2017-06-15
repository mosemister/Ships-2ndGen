package MoseShipsSponge.ShipBlock.Signs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;

import MoseShipsSponge.Vessel.Common.OpenLoader.Loader;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public interface ShipSign {

	public static final List<ShipSign> SHIP_SIGNS = new ArrayList<>();

	public void onCreation(ChangeSignEvent event, Player player);

	public void onShiftRightClick(Player player, Sign sign, LiveShip ship);

	public void onRightClick(Player player, Sign sign, LiveShip ship);

	public void onLeftClick(Player player, Sign sign, LiveShip ship);

	public void onRemove(Player player, Sign sign);

	public List<String> getFirstLine();

	public boolean isSign(Sign sign);

	public void apply(Sign sign);

	public default Optional<LiveShip> getAttachedShip(Sign sign) {
		return Loader.safeLoadShip(this, sign, false);
	}

}

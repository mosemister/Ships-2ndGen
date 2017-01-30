package MoseShipsBukkit.ShipBlock.Signs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import MoseShipsBukkit.Vessel.Data.LiveShip;

public interface ShipSign {

	public static final List<ShipSign> SHIP_SIGNS = new ArrayList<ShipSign>();

	public void onCreation(SignChangeEvent event);

	public void onShiftRightClick(Player player, Sign sign, LiveShip ship);

	public void onRightClick(Player player, Sign sign, LiveShip ship);

	public void onLeftClick(Player player, Sign sign, LiveShip ship);

	public void onRemove(Player player, Sign sign);

	public String getFirstLine();

	public boolean isSign(Sign sign);

	public Optional<LiveShip> getAttachedShip(Sign sign);

}

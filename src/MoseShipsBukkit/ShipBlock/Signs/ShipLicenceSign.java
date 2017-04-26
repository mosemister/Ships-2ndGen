package MoseShipsBukkit.ShipBlock.Signs;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import MoseShipsBukkit.Configs.ShipsConfig;
import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Events.Create.ShipCreateEvent;
import MoseShipsBukkit.Events.Create.ShipSignCreateEvent;
import MoseShipsBukkit.Events.Create.Fail.ShipCreateFailedFromConflictingNames;
import MoseShipsBukkit.Events.Create.Fail.ShipCreateFailedFromMissingType;
import MoseShipsBukkit.Plugin.ShipsMain;
import MoseShipsBukkit.Utils.PermissionsUtil;
import MoseShipsBukkit.Utils.StaticShipTypeUtil;
import MoseShipsBukkit.Vessel.Data.AbstractShipsData;
import MoseShipsBukkit.Vessel.Data.LiveShip;
import MoseShipsBukkit.Vessel.OpenLoader.Loader;
import MoseShipsBukkit.Vessel.Static.StaticShipType;

public class ShipLicenceSign implements ShipSign {

	@Override
	public void onCreation(SignChangeEvent event) {
		if (event.getLines().length < 3) {
			return;
		}
		Player player = event.getPlayer();
		Optional<StaticShipType> opShipType = StaticShipTypeUtil.getType(event.getLine(1));

		if (!opShipType.isPresent()) {
			ShipsCause cause = new ShipsCause(event, player, this);
			ShipCreateFailedFromMissingType conflictType = new ShipCreateFailedFromMissingType(cause,
					new AbstractShipsData(event.getLine(2), event.getBlock(), player.getLocation()), player,
					event.getLine(1));
			Bukkit.getServer().getPluginManager().callEvent(conflictType);
			String message = conflictType.getMessage();
			if (message.contains("%Type%")) {
				message.replace("%Type%", event.getLine(1));
			}
			if (conflictType.shouldMessageDisplay()) {
				player.sendMessage(ShipsMain.format(message, true));
			}
			return;
		}

		StaticShipType type = opShipType.get();

		// PLAYER CAUSE
		if (!PermissionsUtil.hasPermissionToMake(player, type)) {
			return;
		}

		Optional<LiveShip> opConflict = Loader.getShip(event.getLine(2));
		if (opConflict.isPresent()) {
			ShipsCause cause = new ShipsCause(event, this, player, type);
			ShipCreateFailedFromConflictingNames conflictName = new ShipCreateFailedFromConflictingNames(cause,
					new AbstractShipsData(event.getLine(2), event.getBlock(), player.getLocation()), player,
					opConflict.get());
			Bukkit.getServer().getPluginManager().callEvent(conflictName);
			String message = conflictName.getMessage();
			if (message.contains("%Type%")) {
				message.replace("%Type%", event.getLine(1));
			}
			if (conflictName.shouldMessageDisplay()) {
				player.sendMessage(ShipsMain.format(message, true));
			}
			return;
		}
		Optional<LiveShip> opShip = type.createVessel(event.getLine(2), event.getBlock());
		if (opShip.isPresent()) {
			final LiveShip ship = opShip.get();
			ship.setOwner(player);
			ShipsCause cause = new ShipsCause(event, player, this, type, ship);
			ShipCreateEvent SCEvent = new ShipSignCreateEvent(cause, (AbstractShipsData) ship);
			Bukkit.getPluginManager().callEvent(SCEvent);
			if (!SCEvent.isCancelled()) {
				// PLAYER
				player.sendMessage(ShipsMain.format("Ship created", false));
				ship.load(cause);
				event.setLine(0, ChatColor.YELLOW + "[Ships]");
				event.setLine(1, ChatColor.BLUE + ship.getStatic().getName());
				event.setLine(2, ChatColor.GREEN + ship.getName());
				event.setLine(3, ChatColor.GREEN + event.getLine(3));
				ship.save();
			}
		}
		return;
	}

	@Override
	public String getFirstLine() {
		return "[Ships]";
	}

	@Override
	public boolean isSign(Sign sign) {
		if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")) {
			return true;
		}
		return false;
	}

	@Override
	public void onShiftRightClick(Player player, Sign sign, LiveShip ship) {
		List<Block> structure = ship.updateBasicStructure();
		ship.setBasicStructure(structure, sign.getBlock());
		ship.save();
		player.sendMessage(ShipsMain.format("Ship updated it's structure", false));

	}

	@Override
	public void onRightClick(Player player, Sign sign, LiveShip ship) {
		Map<String, Object> info = ship.getInfo();
		player.sendMessage(ShipsMain.format("Information about " + ship.getName(), false));
		for (Entry<String, String> data : ship.getBasicData().entrySet()) {
			player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + data.getKey() + ": " + ChatColor.RESET + ""
					+ ChatColor.AQUA + data.getValue());
		}
		for (Entry<String, Object> data : info.entrySet()) {
			player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + data.getKey() + ": " + ChatColor.RESET + ""
					+ ChatColor.AQUA + data.getValue());
		}
	}

	@Override
	public void onLeftClick(Player player, Sign sign, LiveShip ship) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRemove(Player player, Sign sign) {
		Optional<LiveShip> opShip = getAttachedShip(sign);
		if (opShip.isPresent()) {
			LiveShip ship = opShip.get();
			if (((ship.getOwner().isPresent()) && (ship.getOwner().get().getUniqueId().equals(player.getUniqueId())))
					|| (player.hasPermission(PermissionsUtil.REMOVE_SHIP_LICENCE_OTHER))) {
				ship.remove();
				player.sendMessage(ShipsMain.format(ship.getName() + " has been removed", false));
				if ((ship.getOwner().isPresent())
						&& (!player.getUniqueId().equals(ship.getOwner().get().getUniqueId()))) {
					OfflinePlayer oPlayer = ship.getOwner().get();
					if (oPlayer.isOnline()) {
						oPlayer.getPlayer().sendMessage(ShipsMain
								.format(ship.getName() + " has been removed by " + player.getDisplayName(), false));
					}
				}
				if (ShipsConfig.CONFIG.get(Boolean.class, ShipsConfig.PATH_ONSNEAK_REMOVE_SHIP)) {
					for (int A = 0; A < ship.getBasicStructure().size(); A++) {
						final Block target = ship.getBasicStructure().get(A);
						Bukkit.getScheduler().scheduleSyncDelayedTask(ShipsMain.getPlugin(), new Runnable() {

							@Override
							public void run() {
								target.breakNaturally();
							}

						}, (A * 4));
					}
				}
				return;
			}
		}
	}

	@Override
	public Optional<LiveShip> getAttachedShip(Sign sign) {
		Optional<LiveShip> opShip = Loader.getShip(this, sign, false);
		if (opShip.isPresent()) {
			return Optional.of((LiveShip) opShip.get());
		}
		return Optional.empty();
	}

}

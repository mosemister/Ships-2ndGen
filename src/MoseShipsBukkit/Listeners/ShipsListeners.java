package MoseShipsBukkit.Listeners;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import MoseShips.Stores.TwoStore;

import MoseShipsBukkit.ShipsMain;
import MoseShipsBukkit.Causes.MovementResult;
import MoseShipsBukkit.Causes.MovementResult.CauseKeys;
import MoseShipsBukkit.Configs.Files.ShipsConfig;
import MoseShipsBukkit.Events.Vessel.Create.ShipCreateEvent;
import MoseShipsBukkit.Events.Vessel.Create.Fail.Type.ShipCreateFailedFromConflictingNames;
import MoseShipsBukkit.Events.Vessel.Create.Fail.Type.ShipCreateFailedFromMissingType;
import MoseShipsBukkit.Ships.ShipsData;
import MoseShipsBukkit.Ships.Movement.MovementType.Rotate;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.Live.LiveLockedAltitude;
import MoseShipsBukkit.Ships.VesselTypes.Loading.ShipsLocalDatabase;
import MoseShipsBukkit.Ships.VesselTypes.Satic.StaticShipType;
import MoseShipsBukkit.Ships.VesselTypes.Satic.StaticShipTypeUtil;
import MoseShipsBukkit.Signs.ShipsSigns;
import MoseShipsBukkit.Signs.ShipsSigns.SignType;
import MoseShipsBukkit.Utils.LocationUtils;
import MoseShipsBukkit.Utils.Permissions;

public class ShipsListeners implements Listener {

	private boolean removeLicence(Sign sign, Player player) {
		Optional<SignType> opSignType = ShipsSigns.getSignType(sign);
		if (opSignType.isPresent()) {
			SignType type = opSignType.get();
			if (type.equals(SignType.LICENCE)) {
				Optional<LoadableShip> opShip = LoadableShip.getShip(type, sign, false);
				if (opShip.isPresent()) {
					LoadableShip ship = opShip.get();
					if (((ship.getOwner().isPresent()) && (ship.getOwner().get().getUniqueId().equals(player.getUniqueId()))) || (player.hasPermission(Permissions.REMOVE_SHIP_LICENCE_OTHER))) {
						ship.remove();
						player.sendMessage(ShipsMain.format(ship.getName() + " has been removed", false));
						if ((ship.getOwner().isPresent()) && (!player.getUniqueId().equals(ship.getOwner().get().getUniqueId()))) {
							OfflinePlayer oPlayer = ship.getOwner().get();
							if (oPlayer.isOnline()) {
								oPlayer.getPlayer().sendMessage(ShipsMain.format(ship.getName() + " has been removed by " + player.getDisplayName(), false));
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
						return true;
					}
				}
			}
		}
		return false;
	}

	@EventHandler
	public void blockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		Player player = event.getPlayer();
		if (block.getState() instanceof Sign) {
			Sign sign = (Sign) block.getState();
			removeLicence(sign, player);
		}
		for (Sign sign : LocationUtils.getAttachedSigns(block)) {
			removeLicence(sign, player);
		}
	}

	@EventHandler
	public void signCreate(SignChangeEvent event) {
		Optional<SignType> opSignType = ShipsSigns.getSignType(event.getLine(0));
		if (opSignType.isPresent()) {
			SignType signType = opSignType.get();
			if (signType.equals(SignType.LICENCE)) {
				if (event.getLines().length < 3) {
					return;
				}
				Player player = event.getPlayer();
				Optional<StaticShipType> opShipType = StaticShipTypeUtil.getType(event.getLine(1));

				if (!opShipType.isPresent()) {
					ShipCreateFailedFromMissingType conflictType = new ShipCreateFailedFromMissingType(new ShipsData(event.getLine(2), event.getBlock(), player.getLocation()), player, event
							.getLine(1));
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
				if (!Permissions.hasPermissionToMake(player, type)) {
					return;
				}

				Optional<LoadableShip> opConflict = LoadableShip.getShip(event.getLine(2));
				if (opConflict.isPresent()) {
					ShipCreateFailedFromConflictingNames conflictName = new ShipCreateFailedFromConflictingNames(new ShipsData(event.getLine(2), event.getBlock(),
							player.getLocation()), player,
							opConflict.get());
					Bukkit.getServer().getPluginManager().callEvent(
							conflictName);
					String message = conflictName.getMessage();
					if (message.contains("%Type%")) {
						message.replace("%Type%", event.getLine(1));
					}
					if (conflictName.shouldMessageDisplay()) {
						player.sendMessage(ShipsMain.format(message, true));
					}
					return;
				}
				Optional<LoadableShip> opShip = type.createVessel(event.getLine(2), event.getBlock());
				if (opShip.isPresent()) {
					final LoadableShip ship = opShip.get();
					ship.setOwner(player);
					ShipCreateEvent SCEvent = new ShipCreateEvent(ship);
					Bukkit.getPluginManager().callEvent(SCEvent);
					if (!SCEvent.isCancelled()) {
						// PLAYER
						player.sendMessage(ShipsMain.format("Ship created", false));
						LoadableShip.addToRam(ship);
						event.setLine(0, ChatColor.YELLOW + "[Ships]");
						event.setLine(1, ChatColor.BLUE + ship.getStatic().getName());
						event.setLine(2, ChatColor.GREEN + ship.getName());
						event.setLine(3, ChatColor.GREEN + event.getLine(3));
						ShipsLocalDatabase database = ship.getLocalDatabase();
						database.saveBasicShip(ship);
					}
				}
				return;
			} else {
				String[] lines = signType.getDefaultLines().get();
				for (int A = 0; A < lines.length; A++) {
					event.setLine(A, lines[A]);
				}
			}
		}
	}

	@EventHandler
	public void playerInteractEvent(PlayerInteractEvent event) {
		Block block = event.getClickedBlock();
		BlockFace direction = event.getBlockFace().getOppositeFace();
		Player player = event.getPlayer();
		if ((event.getAction().equals(Action.LEFT_CLICK_BLOCK))
				|| (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			if (block.getState() instanceof Sign) {
				Sign sign = (Sign) block.getState();
				Optional<SignType> signType = ShipsSigns.getSignType(ChatColor.stripColor(sign.getLine(0)));
				if (signType.isPresent()) {
					Optional<LoadableShip> opType = LoadableShip.getShip(signType.get(), sign, true);
					if (opType.isPresent()) {
						LoadableShip ship = opType.get();
						ship.load();
						if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
							switch (signType.get()) {
								case EOT:

									break;
								case LICENCE:
									Map<String, Object> info = ship.getInfo();
									player.sendMessage(ShipsMain.format("Information about " + ship.getName(), false));
									for (Entry<String, Object> data : info.entrySet()) {
										player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + data.getKey() + ": " + ChatColor.RESET + "" + ChatColor.AQUA + data.getValue());
									}
									break;
								case MOVE:
									if (sign.getLine(2).equals("{Boost}")) {
										Optional<MovementResult> cause = ship.move(direction,
												ship.getStatic().getBoostSpeed());
										if (cause.isPresent()) {
											MovementResult result = cause.get();
											Optional<TwoStore<CauseKeys<Object>, Object>> failed = result
													.getFailedCause();
											if (failed.isPresent()) {
												TwoStore<CauseKeys<Object>, Object> store = failed.get();
												store.getFirst().sendMessage(ship, player, store.getSecond());
											}
										}
									} else {
										Optional<MovementResult> cause = ship.move(direction,
												ship.getStatic().getDefaultSpeed());
										if (cause.isPresent()) {
											MovementResult result = cause.get();
											Optional<TwoStore<CauseKeys<Object>, Object>> failed = result
													.getFailedCause();
											if (failed.isPresent()) {
												TwoStore<CauseKeys<Object>, Object> store = failed.get();
												store.getFirst().sendMessage(ship, player, store.getSecond());
											}
										}
									}
									break;
								case WHEEL:
									Optional<MovementResult> causeRotate = ship.rotate(Rotate.RIGHT);
									if (causeRotate.isPresent()) {
										MovementResult result = causeRotate.get();
										Optional<TwoStore<CauseKeys<Object>, Object>> failed = result.getFailedCause();
										if (failed.isPresent()) {
											TwoStore<CauseKeys<Object>, Object> store = failed.get();
											store.getFirst().sendMessage(ship, player, store.getSecond());
										}
									}
									break;
								case ALTITUDE:
									if (ship instanceof LiveLockedAltitude) {
										return;
									}
									Optional<MovementResult> causeMove = ship.move(0, ship.getStatic().getAltitudeSpeed(),
											0);
									if (causeMove.isPresent()) {
										MovementResult result = causeMove.get();
										Optional<TwoStore<CauseKeys<Object>, Object>> failed = result.getFailedCause();
										if (failed.isPresent()) {
											TwoStore<CauseKeys<Object>, Object> store = failed.get();
											store.getFirst().sendMessage(ship, player, store.getSecond());
										}
									}

									break;
							}
						} else {
							switch (signType.get()) {
								case ALTITUDE:
									if (ship instanceof LiveLockedAltitude) {
										return;
									}
									Optional<MovementResult> causeMove = ship.move(0, -ship.getStatic().getAltitudeSpeed(),
											0);
									if (causeMove.isPresent()) {
										MovementResult result = causeMove.get();
										Optional<TwoStore<CauseKeys<Object>, Object>> failed = result.getFailedCause();
										if (failed.isPresent()) {
											TwoStore<CauseKeys<Object>, Object> store = failed.get();
											store.getFirst().sendMessage(ship, player, store.getSecond());
										}
									}
									break;
								case EOT:
									break;
								case LICENCE:
									break;
								case MOVE:
									BlockFace[] faces = {BlockFace.DOWN, BlockFace.UP};
									for(BlockFace face : faces){
										if(event.getBlockFace().equals(face)){
											return;
										}
									}
									if (sign.getLine(2).equals("{Boost}")) {
										Optional<MovementResult> cause = ship.move(direction,
												ship.getStatic().getBoostSpeed());
										if (cause.isPresent()) {
											MovementResult result = cause.get();
											Optional<TwoStore<CauseKeys<Object>, Object>> failed = result
													.getFailedCause();
											if (failed.isPresent()) {
												TwoStore<CauseKeys<Object>, Object> store = failed.get();
												store.getFirst().sendMessage(ship, player, store.getSecond());
											}
										}
									} else {
										Optional<MovementResult> cause = ship.move(direction,
												ship.getStatic().getDefaultSpeed());
										if (cause.isPresent()) {
											MovementResult result = cause.get();
											Optional<TwoStore<CauseKeys<Object>, Object>> failed = result
													.getFailedCause();
											if (failed.isPresent()) {
												TwoStore<CauseKeys<Object>, Object> store = failed.get();
												store.getFirst().sendMessage(ship, player, store.getSecond());
											}
										}
									}
									break;
								case WHEEL:
									Optional<MovementResult> causeRotate = ship.rotate(Rotate.LEFT);
									if (causeRotate.isPresent()) {
										MovementResult result = causeRotate.get();
										Optional<TwoStore<CauseKeys<Object>, Object>> failed = result.getFailedCause();
										if (failed.isPresent()) {
											TwoStore<CauseKeys<Object>, Object> store = failed.get();
											store.getFirst().sendMessage(ship, player, store.getSecond());
										}
									}
									break;
							}
						}
					}else{
						player.sendMessage(ShipsMain.format("Can not find the connected ship", true));
					}
				}
			}
		}
	}
}

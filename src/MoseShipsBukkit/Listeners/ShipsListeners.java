package MoseShipsBukkit.Listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import MoseShips.Stores.TwoStore;
import MoseShipsBukkit.ShipsMain;
import MoseShipsBukkit.Causes.MovementResult;
import MoseShipsBukkit.Causes.MovementResult.CauseKeys;
import MoseShipsBukkit.Events.StaticVessel.Create.AboutToCreateShipEvent;
import MoseShipsBukkit.Events.Vessel.Create.ShipsCreateEvent;
import MoseShipsBukkit.Ships.Movement.Movement.Rotate;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Ships.VesselTypes.Loading.ShipsLocalDatabase;
import MoseShipsBukkit.Ships.VesselTypes.Satic.StaticShipType;
import MoseShipsBukkit.Ships.VesselTypes.Satic.StaticShipTypeUtil;
import MoseShipsBukkit.Signs.ShipsSigns;
import MoseShipsBukkit.Signs.ShipsSigns.SignType;
import MoseShipsBukkit.Utils.Permissions;

public class ShipsListeners implements Listener {

	@EventHandler
	public void signCreate(SignChangeEvent event) {
		Map<String, Object> causes = new HashMap<String, Object>();
		Optional<SignType> opSignType = ShipsSigns.getSignType(event.getLine(0));
		if (opSignType.isPresent()) {
			SignType signType = opSignType.get();
			if (signType.equals(SignType.LICENCE)) {
				if (event.getLines().length >= 3) {
					Optional<StaticShipType> opShipType = StaticShipTypeUtil.getType(event.getLine(1));
					if (opShipType.isPresent()) {
						StaticShipType type = opShipType.get();

						// PLAYER CAUSE
						Player player = event.getPlayer();
						if (!Permissions.hasPermissionToMake(player, type)) {
							return;
						}
						causes.put("Player", player);

						AboutToCreateShipEvent<StaticShipType> ATCSEvent = new AboutToCreateShipEvent<StaticShipType>(
								type, event.getBlock());
						if (!ATCSEvent.isCancelled()) {
							Optional<LoadableShip> opShip = type.createVessel(event.getLine(2), event.getBlock());
							if (opShip.isPresent()) {
								final LoadableShip ship = opShip.get();
								ship.setOwner(player);
								ShipsCreateEvent<LoadableShip> SCEvent = new ShipsCreateEvent<LoadableShip>(ship);
								// Bukkit.getPluginManager().callEvent(SCEvent);
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
						}
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
		System.out.println("interact");
		if ((event.getAction().equals(Action.LEFT_CLICK_BLOCK))
				|| (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			System.out.println("is Block");
			if (block.getState() instanceof Sign) {
				System.out.println("is Sign");
				Sign sign = (Sign) block.getState();
				Optional<SignType> signType = ShipsSigns.getSignType(ChatColor.stripColor(sign.getLine(0)));
				if (signType.isPresent()) {
					System.out.println("is ships sign");
					Optional<LoadableShip> opType = LoadableShip.getShip(signType.get(), sign, true);
					if (opType.isPresent()) {
						LoadableShip ship = opType.get();
						System.out.println("Ship found " + ship.getName());
						LoadableShip.addToRam(ship);
						if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
							switch (signType.get()) {
								case EOT:

									break;
								case LICENCE:
									Map<String, Object> info = ship.getInfo();
									player.sendMessage(ShipsMain.format("Information about " + ship.getName(), false));
									for (Entry<String, Object> data : info.entrySet()) {
										player.sendMessage(
												ShipsMain.formatCMDHelp(data.getKey() + ": " + data.getValue()));
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
												store.getFirst().sendMessage(player, store.getSecond());
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
												store.getFirst().sendMessage(player, store.getSecond());
											}
										}
									}
									break;
								case WHEEL:
									Optional<MovementResult> causeRotate = ship.rotate(Rotate.RIGHT);
									if(causeRotate.isPresent()){
										MovementResult result = causeRotate.get();
										Optional<TwoStore<CauseKeys<Object>, Object>> failed = result.getFailedCause();
										if(failed.isPresent()){
											TwoStore<CauseKeys<Object>, Object> store = failed.get();
											store.getFirst().sendMessage(player, store.getSecond());
										}
									}
									break;
								case ALTITUDE:
									Optional<MovementResult> causeMove = ship.move(0, -ship.getStatic().getAltitudeSpeed(),
											0);
									if (causeMove.isPresent()) {
										MovementResult result = causeMove.get();
										Optional<TwoStore<CauseKeys<Object>, Object>> failed = result.getFailedCause();
										if (failed.isPresent()) {
											TwoStore<CauseKeys<Object>, Object> store = failed.get();
											store.getFirst().sendMessage(player, store.getSecond());
										}
									}
									break;
							}
						}else{
							switch(signType.get()){
								case ALTITUDE:
									Optional<MovementResult> causeMove = ship.move(0, -ship.getStatic().getAltitudeSpeed(),
											0);
									if (causeMove.isPresent()) {
										MovementResult result = causeMove.get();
										Optional<TwoStore<CauseKeys<Object>, Object>> failed = result.getFailedCause();
										if (failed.isPresent()) {
											TwoStore<CauseKeys<Object>, Object> store = failed.get();
											store.getFirst().sendMessage(player, store.getSecond());
										}
									}
									break;
								case EOT:
									break;
								case LICENCE:
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
												store.getFirst().sendMessage(player, store.getSecond());
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
												store.getFirst().sendMessage(player, store.getSecond());
											}
										}
									}
									break;
								case WHEEL:
									Optional<MovementResult> causeRotate = ship.rotate(Rotate.LEFT);
									if(causeRotate.isPresent()){
										MovementResult result = causeRotate.get();
										Optional<TwoStore<CauseKeys<Object>, Object>> failed = result.getFailedCause();
										if(failed.isPresent()){
											TwoStore<CauseKeys<Object>, Object> store = failed.get();
											store.getFirst().sendMessage(player, store.getSecond());
										}
									}
									break;								
							}
						}
					}
				}
			}
		}
	}

}

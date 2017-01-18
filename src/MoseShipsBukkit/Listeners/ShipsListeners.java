package MoseShipsBukkit.Listeners;

import java.util.Optional;

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

import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Plugin.ShipsMain;
import MoseShipsBukkit.ShipBlock.Signs.ShipSign;
import MoseShipsBukkit.Utils.LocationUtil;
import MoseShipsBukkit.Utils.ShipSignUtil;
import MoseShipsBukkit.Vessel.Data.LoadableShip;

public class ShipsListeners implements Listener {

	@EventHandler
	public void blockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		Player player = event.getPlayer();
		if (block.getState() instanceof Sign) {
			Sign sign = (Sign) block.getState();
			Optional<ShipSign> opSign = ShipSignUtil.getSign(sign);
			if(opSign.isPresent()){
				opSign.get().onRemove(player, sign);
			}
		}
		for (Sign sign : LocationUtil.getAttachedSigns(block)) {
			Optional<ShipSign> opSign = ShipSignUtil.getSign(sign);
			if(opSign.isPresent()){
				opSign.get().onRemove(player, sign);
			}
		}
	}

	@EventHandler
	public void signCreate(SignChangeEvent event) {
		Optional<ShipSign> opSign = ShipSignUtil.getSign(event.getLine(0));
		if(opSign.isPresent()){
			opSign.get().onCreation(event);
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
				Optional<ShipSign> opSignType = ShipSignUtil.getSign(sign);
				if (opSignType.isPresent()) {
					Optional<LoadableShip> opType = LoadableShip.getShip(opSignType.get(), sign, true);
					if (opType.isPresent()) {
						LoadableShip ship = opType.get();
						ShipsCause cause2 = new ShipsCause(event, player, direction, sign, opSignType.get(), ship);
						ship.load(cause2);
						if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
							if(player.isSneaking()){
								opSignType.get().onShiftRightClick(player, sign, ship);
							}else{
								opSignType.get().onRightClick(player, sign, ship);
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

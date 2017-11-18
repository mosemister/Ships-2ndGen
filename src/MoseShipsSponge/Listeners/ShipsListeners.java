package MoseShipsSponge.Listeners;

import java.util.Optional;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.ShipBlock.Signs.ShipSign;
import MoseShipsSponge.Utils.ShipSignUtil;
import MoseShipsSponge.Vessel.Common.OpenLoader.Loader;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class ShipsListeners {

	@Listener
	public void blockBreak(ChangeBlockEvent.Break event, @Root Player player) {
		event.getTransactions().stream().forEach(t -> {
			Optional<Location<World>> opLoc = t.getOriginal().getLocation();
			if (opLoc.isPresent()) {
				Optional<TileEntity> opTile = opLoc.get().getTileEntity();
				if (opTile.isPresent() && (opTile.get() instanceof Sign)) {
					Sign sign = (Sign) opTile.get();
					Optional<ShipSign> opSign = ShipSignUtil.getSign(sign);
					if (opSign.isPresent()) {
						if (!opSign.get().onRemove(player, sign)) {
							event.setCancelled(true);
						}
					}
				}
			}
		});
	}

	@Listener
	public void signCreate(ChangeSignEvent event, @Root Player player) {
		Optional<Text> opText = event.getText().get(0);
		if (opText.isPresent()) {
			Optional<ShipSign> opSign = ShipSignUtil.getSign(opText.get().toPlain());
			if (opSign.isPresent()) {
				opSign.get().onCreation(event, player);
			}
		}
	}

	@Listener
	public void playerLeaveEvent(ClientConnectionEvent.Disconnect event) {
		Player player = event.getTargetEntity();
		Location<World> loc = player.getLocation().getRelative(Direction.DOWN);
		Optional<LiveShip> opShip = Loader.safeLoadShip(loc, false);
		if (opShip.isPresent()) {
			LiveShip ship = opShip.get();
			Vector3i vector = ship.getStructure().createVector(ship, loc);
			ship.getPlayerVectorSpawns().put(player.getUniqueId(), vector);
		}
	}

	
	/*@Listener 
	public void playerSpawnEvent(ClientConnectionEvent.Join event){
		Player player = event.getTargetEntity(); 
		Optional<LiveShip> opShip = Loader.safeLoadShipByVectorSpawns(player.getUniqueId());
		if(opShip.isPresent()){ 
			LiveShip ship = opShip.get(); 
			Vector3i vector = ship.getPlayerVectorSpawns().get(player.getUniqueId()); 
			if(vector != null){ 
				Location<World> loc = ship.getStructure().getBlock(ship, vector);
				player.setLocationSafely(loc.getBlockRelative(Direction.UP));
			} 
		} 
	}*/

	@Listener
	public void playerInteractEvent(InteractBlockEvent event, @Root Player player) {
		BlockSnapshot block = event.getTargetBlock();
		Direction direction = event.getTargetSide().getOpposite();
		Optional<Location<World>> opLoc = block.getLocation();
		if (!opLoc.isPresent()) {
			return;
		}
		Location<World> loc = opLoc.get();
		Optional<TileEntity> opTile = loc.getTileEntity();
		if (!opTile.isPresent()) {
			return;
		}
		if (!(opTile.get() instanceof Sign)) {
			return;
		}
		Sign sign = (Sign) opTile.get();
		Optional<ShipSign> opSSign = ShipSignUtil.getSign(sign);
		if (!opSSign.isPresent()) {
			return;
		}
		ShipSign sSign = opSSign.get();
		Optional<LiveShip> opShip = sSign.getAttachedShip(sign);
		if (!opShip.isPresent()) {
			player.sendMessage(ShipsMain.format("Cannot find the connected ship", true));
			return;
		}
		LiveShip ship = opShip.get();
		Cause cause = Cause.builder().named("event", event).named("player", player).named("direction", direction)
				.named("sign", sign).named("ship", ship).build();
		ship.load(cause);
		if (event instanceof InteractBlockEvent.Secondary) {
			if (player.get(Keys.IS_SNEAKING).get()) {
				sSign.onShiftRightClick(player, sign, ship);
			} else {
				sSign.onRightClick(player, sign, ship);
			}
		} else {
			sSign.onLeftClick(player, sign, ship);
		}
	}

}

package MoseShipsBukkit.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.Events.ShipCreateEvent;
import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.ShipTypes.VesselType;
import MoseShipsBukkit.StillShip.Vessel;
import MoseShipsBukkit.Utils.ShipsAutoRuns;

public class BukkitListeners implements Listener {
	
	@EventHandler
	public static void signBreak(BlockBreakEvent event){
		if (event.getBlock().getState() instanceof Sign){
			Sign sign = (Sign)event.getBlock().getState();
			if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")){
				Vessel vessel = Vessel.getVessel(sign);
				if (vessel == null){
					event.getPlayer().sendMessage(Ships.runShipsMessage("Unknown ship", true));
				}else{
					if (event.getPlayer().equals(vessel.getOwner())){
						vessel.remove();
						event.getPlayer().sendMessage(Ships.runShipsMessage(vessel.getName() + " has been removed.", false));
					}else{
						event.getPlayer().sendMessage(Ships.runShipsMessage(vessel.getName() + "does not belong to you.", true));
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public static void signCreation(SignChangeEvent event){
		//Ships sign
		if (event.getLine(0).equalsIgnoreCase("[Ships]")){
			VesselType type = VesselType.getTypeByName(event.getLine(1));
			if (type != null){
				if ((event.getPlayer().hasPermission("ships." + type.getName() + ".make")) || (event.getPlayer().hasPermission("ships.*.make")) || (event.getPlayer().hasPermission("ships.*"))){
					if (Vessel.getVessel(event.getLine(2)) == null){
						Vessel vessel = new Vessel((Sign)event.getBlock().getState(),event.getLine(2), type, event.getPlayer());
						ShipCreateEvent event2 = new ShipCreateEvent(event.getPlayer(), (Sign)event.getBlock().getState(), vessel);
						Bukkit.getPluginManager().callEvent(event2);
						if (!event2.isCancelled()){
							event.setLine(0, ChatColor.YELLOW + "[Ships]");
							event.setLine(1, ChatColor.BLUE + event.getLine(1));
							event.setLine(2, ChatColor.GREEN + event.getLine(2));
						}
						return;
					}else{
						event.getPlayer().sendMessage(Ships.runShipsMessage("Name taken", true));
						event.setCancelled(true);
						return;
					}
				}
			}else{
				event.getPlayer().sendMessage(Ships.runShipsMessage("Vessel type does not exist", true));
				event.setCancelled(true);
				return;
			}
		}
		//Move sign
		if ((event.getLine(0).equalsIgnoreCase("[Move]")) || (event.getLine(0).equalsIgnoreCase("[Engine]"))){
			event.setLine(0, ChatColor.YELLOW + "[Move]");
			event.setLine(1, "{" + ChatColor.GREEN + "Engine" + ChatColor.BLACK + "}");
			event.setLine(2, "Boost");
			return;
		}
		//Wheel sign
		if (event.getLine(0).equalsIgnoreCase("[Wheel]")){
			event.setLine(0, ChatColor.YELLOW + "[Wheel]");
			event.setLine(1, ChatColor.RED + "\\\\ || //");
			event.setLine(2, ChatColor.RED + "==    ==");
			event.setLine(3, ChatColor.RED + "// || \\\\");
			return;
		}
		//Altitude
		if ((event.getLine(0).equalsIgnoreCase("[Burner]")) || (event.getLine(0).equalsIgnoreCase("[Altitude]"))){
			event.setLine(0, ChatColor.YELLOW + "[Altitude]");
			event.setLine(2, "[right] up");
			event.setLine(3, "[left] down");
			return;
		}
		//EOT
		if (event.getLine(0).equalsIgnoreCase("[EOT]")){
			event.setLine(0, ChatColor.YELLOW + "[E.O.T]");
			event.setLine(1, ChatColor.GREEN + "AHEAD");
			event.setLine(2, "-[" + ChatColor.WHITE + "STOP" + ChatColor.BLACK + "]-");
			return;
		}
	}
	
	@EventHandler
	public static void signClick(PlayerInteractEvent event){
		if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
			if (event.getClickedBlock().getState() instanceof Sign){
				Sign sign = (Sign)event.getClickedBlock().getState();
				//MOVE SIGN
				if (sign.getLine(0).equals(ChatColor.YELLOW + "[Move]")){
					//TODO change from engine to boost
					//TODO wind
				}
				//WHEEL SIGN
				else if (sign.getLine(0).equals(ChatColor.YELLOW + "[Wheel]")){
					Vessel vessel = Vessel.getVessel(sign.getBlock());
					if (vessel == null){
						event.getPlayer().sendMessage(Ships.runShipsMessage("Ships sign can not be found", true));
					}else{
						vessel.moveVessel(MovementMethod.ROTATE_LEFT, 0, event.getPlayer());
					}
				}
				//ALTITUDE SIGN
				else if (sign.getLine(0).equals(ChatColor.YELLOW + "[Altitude]")){
					Vessel vessel = Vessel.getVessel(sign.getBlock());
					if (vessel == null){
						event.getPlayer().sendMessage(Ships.runShipsMessage("Ships sign can not be found", true));
					}else{
						vessel.moveVessel(MovementMethod.MOVE_DOWN, 1, event.getPlayer());
					}
				}
				else if (sign.getLine(0).equals(ChatColor.YELLOW + "[E.O.T]")){
					if (sign.getLine(1).equals("-[" + ChatColor.GREEN + "AHEAD" + ChatColor.BLACK + "]-")){
						sign.setLine(1, ChatColor.GREEN + "AHEAD");
						sign.setLine(2, "-[" + ChatColor.WHITE + "STOP" + ChatColor.BLACK + "]-");
						sign.update();
						ShipsAutoRuns.EOTAUTORUN.remove(Vessel.getVessel(sign.getBlock()));
					}else if(sign.getLine(1).equals(ChatColor.GREEN + "AHEAD")){
						sign.setLine(1, "-[" + ChatColor.GREEN + "AHEAD" + ChatColor.BLACK + "]-");
						sign.setLine(2, ChatColor.WHITE + "STOP");
						sign.update();
						ShipsAutoRuns.EOTAUTORUN.put(Vessel.getVessel(sign.getBlock()), event.getPlayer());
					}
				}
			}
		}else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if (event.getClickedBlock().getState() instanceof Sign){
				Sign sign = (Sign)event.getClickedBlock().getState();
				Vessel vessel = Vessel.getVessel(sign.getBlock());
				if (vessel == null){
					event.getPlayer().sendMessage(Ships.runShipsMessage("Ships sign can not be found", true));
				}else{
					//MOVE SIGN
					if (sign.getLine(0).equals(ChatColor.YELLOW + "[Move]")){
						org.bukkit.material.Sign sign2 = (org.bukkit.material.Sign)sign.getData();
						vessel.moveVessel(MovementMethod.getMovingDirection(vessel, sign2.getAttachedFace()), vessel.getVesselType().getDefaultSpeed(), event.getPlayer());
					}
					//WHEEL SIGN
					else if (sign.getLine(0).equals(ChatColor.YELLOW + "[Wheel]")){
						vessel.moveVessel(MovementMethod.ROTATE_RIGHT, 0, event.getPlayer());
					}
					//ALTITUDE SIGN
					else if (sign.getLine(0).equals(ChatColor.YELLOW + "[Altitude]")){
						vessel.moveVessel(MovementMethod.MOVE_UP, 1, event.getPlayer());
					}
					//EOT SIGN
					else if (sign.getLine(0).equals(ChatColor.YELLOW + "[E.O.T]")){
						event.getPlayer().sendMessage(Ships.runShipsMessage("Moves the vessel in the same direction until this E.O.T sign is changed.", false));
					}
				}
			}
		}
	}

}

package MoseShipsBukkit.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.Events.ShipCreateEvent;
import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.ShipsTypes.VesselType;
import MoseShipsBukkit.StillShip.Vessel;
import MoseShipsBukkit.Utils.ShipsAutoRuns;
import MoseShipsBukkit.Utils.ConfigLinks.Config;
import MoseShipsBukkit.World.Wind.Direction;

public class BukkitListeners implements Listener {
	
	@EventHandler
	public static void blockBurn(BlockIgniteEvent event){
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		if (config.getBoolean("World.ProtectedVessels.FireProtect2")){
			Block block = event.getIgnitingBlock();
			if (block.getType().equals(Material.NETHERRACK)){
				Vessel vessel = Vessel.getVessel(block, false);
				if (vessel != null){
					if (vessel.isProtected()){
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public static void endermanProtect(EntityChangeBlockEvent event){
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		if (config.getBoolean("World.ProtectedVessels.EntityProtect.EnderMan")){
			if (event.getEntity() instanceof Enderman){
				Vessel vessel = Vessel.getVessel(event.getBlock(), false);
				if (vessel != null){
					if (vessel.isProtected()){
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public static void entityProtect(EntityExplodeEvent event){
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		if (config.getBoolean("World.ProtectedVessels.ExplodeProtect.Creeper")){
			if (event.getEntity() instanceof Creeper){
				for(Block block : event.blockList()){
					Vessel vessel = Vessel.getVessel(block, false);
					if (vessel != null){
						if (vessel.isProtected()){
							event.blockList().remove(block);
						}
					}
				}
			}
		}
		if (config.getBoolean("World.ProtectedVessels.ExplodeProtect.TNT")){
			if (event.getEntityType().equals(EntityType.PRIMED_TNT)){
				for(Block block : event.blockList()){
					Vessel vessel = Vessel.getVessel(block, false);
					if (vessel != null){
						if (vessel.isProtected()){
							event.blockList().remove(block);
						}
					}
				}
			}
		}
		if (config.getBoolean("World.ProtectedVessels.EntityProtect.EnderDragon")){
			if (event.getEntity() instanceof EnderDragon){
				for(Block block : event.blockList()){
					Vessel vessel = Vessel.getVessel(block, false);
					if (vessel != null){
						if (vessel.isProtected()){
							event.blockList().remove(block);
						}
					}
				}
			}
		}
		if (config.getBoolean("World.ProtectedVessels.EntityProtect.Wither")){
			if (event.getEntity() instanceof Wither){
				for(Block block : event.blockList()){
					Vessel vessel = Vessel.getVessel(block, false);
					if (vessel != null){
						if (vessel.isProtected()){
							event.blockList().remove(block);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public static void signBreak(BlockBreakEvent event){
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		if (config.getBoolean("World.ProtectedVessels.BlockBreak")){
			Vessel vessel = Vessel.getVessel(event.getBlock(), true);
			if (vessel != null){
				if (vessel.isProtected()){
					if (!event.getPlayer().equals(vessel.getOwner())){
						event.setCancelled(true);
						event.getPlayer().sendMessage(Ships.runShipsMessage("This Ship has protection on", true));
					}
				}
			}
		}
		if (event.getBlock().getState() instanceof Sign){
			Sign sign = (Sign)event.getBlock().getState();
			if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")){
				Vessel vessel = Vessel.getVessel(sign);
				if (vessel == null){
					event.getPlayer().sendMessage(Ships.runShipsMessage("Unknown ship", true));
				}else{
					if ((event.getPlayer().equals(vessel.getOwner())) || (event.getPlayer().hasPermission("ships.break.bypass")) || (event.getPlayer().hasPermission("ships.*"))){
						vessel.remove();
						event.getPlayer().sendMessage(Ships.runShipsMessage(vessel.getName() + " has been removed.", false));
					}else{
						event.getPlayer().sendMessage(Ships.runShipsMessage(vessel.getName() + "does not belong to you.", true));
						event.setCancelled(true);
					}
				}
			}else if (sign.getLine(0).equals(ChatColor.YELLOW + "[E.O.T]")){
				Vessel vessel = Vessel.getVessel(sign.getBlock(), false);
				if (vessel == null){
					event.getPlayer().sendMessage(Ships.runShipsMessage("Unknown ship", true));
				}else{
					ShipsAutoRuns.EOTAUTORUN.remove(vessel);
				}
			}
		}
	}
	
	@EventHandler
	public static void signCreation(SignChangeEvent event){
		//Ships sign
		if (event.getLine(0).equalsIgnoreCase("[Ships]")){
			VesselType type = VesselType.getTypeByName(event.getLine(1)).clone();
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
							YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
							if (config.getBoolean("Signs.ForceUsernameOnLicenceSign")){
								event.setLine(3, ChatColor.GREEN + event.getPlayer().getName());
							}
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
		//CELL
		if (event.getLine(0).equalsIgnoreCase("[Cell]")){
			event.setLine(0, ChatColor.YELLOW + "[Cell]");
			event.setLine(1, ChatColor.GREEN + "0");
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
					if (sign.getLine(1).equals("{" + ChatColor.GREEN + "Engine" + ChatColor.BLACK + "}")){
						sign.setLine(1, ChatColor.GREEN + "Engine");
						sign.setLine(2, "{Boost}");
						sign.update();
					}else{
						sign.setLine(1, "{" + ChatColor.GREEN + "Engine" + ChatColor.BLACK + "}");
						sign.setLine(2, "Boost");
						sign.update();
					}
				}
				//WHEEL SIGN
				else if (sign.getLine(0).equals(ChatColor.YELLOW + "[Wheel]")){
					Vessel vessel = Vessel.getVessel(sign.getBlock(), true);
					if (vessel == null){
						event.getPlayer().sendMessage(Ships.runShipsMessage("Ships sign can not be found", true));
					}else{
						vessel.moveVessel(MovementMethod.ROTATE_LEFT, 0, event.getPlayer());
					}
				}
				//ALTITUDE SIGN
				else if (sign.getLine(0).equals(ChatColor.YELLOW + "[Altitude]")){
					Vessel vessel = Vessel.getVessel(sign.getBlock(), true);
					if (vessel == null){
						event.getPlayer().sendMessage(Ships.runShipsMessage("Ships sign can not be found", true));
					}else{
						vessel.moveVessel(MovementMethod.MOVE_DOWN, 1, event.getPlayer());
					}
				}
				//EOT SIGN
				else if (sign.getLine(0).equals(ChatColor.YELLOW + "[E.O.T]")){
					if (sign.getLine(1).equals("-[" + ChatColor.GREEN + "AHEAD" + ChatColor.BLACK + "]-")){
						sign.setLine(1, ChatColor.GREEN + "AHEAD");
						sign.setLine(2, "-[" + ChatColor.WHITE + "STOP" + ChatColor.BLACK + "]-");
						sign.update();
						ShipsAutoRuns.EOTAUTORUN.remove(Vessel.getVessel(sign.getBlock(), true));
					}else if(sign.getLine(1).equals(ChatColor.GREEN + "AHEAD")){
						sign.setLine(1, "-[" + ChatColor.GREEN + "AHEAD" + ChatColor.BLACK + "]-");
						sign.setLine(2, ChatColor.WHITE + "STOP");
						sign.update();
						ShipsAutoRuns.EOTAUTORUN.put(Vessel.getVessel(sign.getBlock(), true), event.getPlayer());
					}
				}
			}
		}else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
			if (config.getBoolean("World.ProtectedVessels.InventoryOpen")){
				if ((event.getClickedBlock().getState() instanceof Chest) ||
						(event.getClickedBlock().getState() instanceof Furnace) ||
						(event.getClickedBlock().getState() instanceof Hopper) ||
						(event.getClickedBlock().getState() instanceof Dropper) ||
						(event.getClickedBlock().getState() instanceof Dispenser)){
					Vessel vessel = Vessel.getVessel(event.getClickedBlock(), false);
					if (vessel != null){
						if (!vessel.getOwner().equals(event.getPlayer())){
							event.setCancelled(true);
						}
					}
				}
			}
			if (event.getClickedBlock().getState() instanceof Sign){
				Sign sign = (Sign)event.getClickedBlock().getState();
				//MOVE SIGN
				if (sign.getLine(0).equals(ChatColor.YELLOW + "[Move]")){
					Vessel vessel = Vessel.getVessel(sign.getBlock(), true);
					if (vessel == null){
						event.getPlayer().sendMessage(Ships.runShipsMessage("Ships sign can not be found", true));
					}else{
						org.bukkit.material.Sign sign2 = (org.bukkit.material.Sign)sign.getData();
						BlockFace face;
						if (sign2.isWallSign()){
							face = sign2.getAttachedFace();
						}else{
							face = sign2.getFacing().getOppositeFace();
						}
						if (sign.getLine(1).equals("{" + ChatColor.GREEN + "Engine" + ChatColor.BLACK + "}")){
							vessel.moveVessel(MovementMethod.getMovingDirection(vessel, face), vessel.getVesselType().getDefaultSpeed(), event.getPlayer());
						}else{
							if (Direction.getDirection(vessel.getSign().getWorld()).getDirection().equals(face)){
								vessel.moveVessel(MovementMethod.getMovingDirection(vessel, face), vessel.getVesselType().getDefaultBoostSpeed(), event.getPlayer());
							}else{
								vessel.moveVessel(MovementMethod.getMovingDirection(vessel, face), vessel.getVesselType().getDefaultSpeed(), event.getPlayer());
							}
						}
					}
				}
				//WHEEL SIGN
				else if (sign.getLine(0).equals(ChatColor.YELLOW + "[Wheel]")){
					Vessel vessel = Vessel.getVessel(sign.getBlock(), true);
					if (vessel == null){
						event.getPlayer().sendMessage(Ships.runShipsMessage("Ships sign can not be found", true));
					}else{
						vessel.moveVessel(MovementMethod.ROTATE_RIGHT, 0, event.getPlayer());
					}
				}
				//ALTITUDE SIGN
				else if (sign.getLine(0).equals(ChatColor.YELLOW + "[Altitude]")){
					Vessel vessel = Vessel.getVessel(sign.getBlock(), true);
					if (vessel == null){
						event.getPlayer().sendMessage(Ships.runShipsMessage("Ships sign can not be found", true));
					}else{
						vessel.moveVessel(MovementMethod.MOVE_UP, 1, event.getPlayer());
					}
				}
				//EOT SIGN
				else if (sign.getLine(0).equals(ChatColor.YELLOW + "[E.O.T]")){
					event.getPlayer().sendMessage(Ships.runShipsMessage("Moves the vessel in the same direction until this E.O.T sign is changed.", false));
				}
				//licence sign
				else if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")){
					Vessel vessel = Vessel.getVessel(sign);
					if (vessel == null){
						event.getPlayer().sendMessage(Ships.runShipsMessage("A issue has occured. Sign is not licenced", false));
						sign.getBlock().breakNaturally();
					}else{
						if (vessel.getOwner().equals(event.getPlayer())){
							vessel.displayInfo(event.getPlayer());
						}else{
							event.getPlayer().sendMessage(Ships.runShipsMessage("Vessel is owned by " + vessel.getOwner().getName(), false));
						}
					}
				}
			}
		}
	}
}

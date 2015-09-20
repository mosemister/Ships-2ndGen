package MoseShipsBukkit.Listeners.ShipsCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.Listeners.CommandLauncher;
import MoseShipsBukkit.ShipsTypes.VesselType;
import MoseShipsBukkit.StillShip.Vessel.Vessel;
import MoseShipsBukkit.Utils.ConfigLinks.Config;
import MoseShipsBukkit.Utils.ConfigLinks.MaterialsList;

public class Reload extends CommandLauncher{

	public Reload() {
		super("Reload", "<<vessel name>/configs>", "reloads something", "ships.command.reload", true, true);
	}

	@Override
	public void playerCommand(final Player player, final String[] args) {
		if (args.length == 1){
			player.sendMessage(Ships.runShipsMessage("/Ships reload <<vesselname>/configs>", true));
		}else{
			if (args[1].equalsIgnoreCase("configs")){
				Bukkit.getScheduler().scheduleSyncDelayedTask(Ships.getPlugin(), new Runnable(){
					@Override
					public void run() {
						new Config();
						new MaterialsList();
						player.sendMessage(Ships.runShipsMessage("reload complete (config, materials list)", false));
					}
				});
			}else if (VesselType.getTypeByName(args[1]) != null){
				Bukkit.getScheduler().scheduleSyncDelayedTask(Ships.getPlugin(), new Runnable(){
					@Override
					public void run() {
						VesselType.getTypeByName(args[1]).loadDefault();
						player.sendMessage(Ships.runShipsMessage("reload complete (" + args[1] + " vesseltype)", false));
					}
				});
			}else{
				Vessel vessel = Vessel.getVessel(args[1]);
				if (vessel == null){
					player.sendMessage(Ships.runShipsMessage("No vessel by that name", true));
					return;
				}else{
					vessel.reload();
					player.sendMessage(Ships.runShipsMessage("reload complete (" + args[1] + " vessel)", false));
				}
			}
		}
		
	}

	@Override
	public void consoleCommand(ConsoleCommandSender sender, String[] args) {
		if (args.length == 1){
			sender.sendMessage(Ships.runShipsMessage("/Ships reload <<vesselname>/configs>", true));
		}else{
			if (args[1].equalsIgnoreCase("configs")){
				Bukkit.getScheduler().scheduleSyncDelayedTask(Ships.getPlugin(), new Runnable(){
					@Override
					public void run() {
						new Config();
						new MaterialsList();
					}
				});
			}else{
				Vessel vessel = Vessel.getVessel(args[1]);
				if (vessel == null){
					sender.sendMessage(Ships.runShipsMessage("No vessel by that name", true));
					return;
				}else{
					vessel.reload();
				}
			}
		}
	}

}

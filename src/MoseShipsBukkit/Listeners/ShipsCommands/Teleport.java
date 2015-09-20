package MoseShipsBukkit.Listeners.ShipsCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.Listeners.CommandLauncher;
import MoseShipsBukkit.StillShip.Vessel.Vessel;

public class Teleport extends CommandLauncher{

	public Teleport() {
		super("teleport", "<vessel name>", "teleport to a vessel", "ships.command.teleport", true, false);
	}

	@Override
	public void playerCommand(Player player, String[] args) {
		if (args.length == 1){
			if (player.hasPermission("ships.command.teleport.other")){
				player.sendMessage(ChatColor.GOLD + "/ships teleport <vessel name>" + ChatColor.AQUA + "; teleport to any vessel");
			}else{
				player.sendMessage(ChatColor.GOLD + "/ships teleport <vessel name>" + ChatColor.AQUA + "; teleport to your vessel");
			}
		}else{
			Vessel vessel = Vessel.getVessel(args[1]);
			if (vessel == null){
				player.sendMessage(Ships.runShipsMessage("Can not find vessel", true));
			}else{
				if ((vessel.getOwner().equals(player)) || (player.hasPermission("ships.command.teleport.other"))){
					player.teleport(vessel.getTeleportLocation());
				}
			}
		}
		
	}

	@Override
	public void consoleCommand(ConsoleCommandSender sender, String[] args) {
		
	}
}

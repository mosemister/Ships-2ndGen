package MoseShipsBukkit.Listeners.ShipsCommands;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.Listeners.CommandLauncher;
import MoseShipsBukkit.StillShip.Vessel;

public class Protected extends CommandLauncher{

	public Protected() {
		super("Protected", "", "Toggle protectedVessels for your vessel", "ships.command.protectedVessel", true, true);
	}

	@Override
	public void playerCommand(Player player, String[] args) {
		Block block = player.getLocation().getBlock().getRelative(0, -1, 0);
		Vessel vessel = Vessel.getVessel(block, false);
		if (vessel == null){
			player.sendMessage(Ships.runShipsMessage("must be standing on your vessel", true));
		}else{
			if (vessel.getOwner().equals(player)){
				vessel.setProtectVessel(!vessel.isProtected());
				player.sendMessage(Ships.runShipsMessage("Ship Protection has now been changed", false));
			}else{
				player.sendMessage(Ships.runShipsMessage("must be your vessel", true));
			}
		}
	}

	@Override
	public void consoleCommand(ConsoleCommandSender sender, String[] args) {
		if (args.length == 1){
			sender.sendMessage(ChatColor.GOLD + "/Ships Protected <vesselName> " + ChatColor.AQUA + ": for the console version of this command, you need to add the vessel name");
		}else{
			Vessel vessel = Vessel.getVessel(args[1]);
			if (vessel == null){
				sender.sendMessage(Ships.runShipsMessage("vessel does not exist", true));
			}else{
				vessel.setProtectVessel(!vessel.isProtected());
				sender.sendMessage(Ships.runShipsMessage("Ship Protection has now been changed", false));
			}
		}
		
	}

}

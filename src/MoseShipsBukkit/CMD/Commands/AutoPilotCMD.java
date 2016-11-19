package MoseShipsBukkit.CMD.Commands;

import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import MoseShipsBukkit.ShipsMain;
import MoseShipsBukkit.CMD.ShipsCMD;
import MoseShipsBukkit.Ships.Movement.AutoPilot.AutoPilot;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.Live.LiveAutoPilotable;

public class AutoPilotCMD implements ShipsCMD.ShipsPlayerCMD{

	public AutoPilotCMD() {
		ShipsCMD.SHIPS_COMMANDS.add(this);
	}
	
	@Override
	public String[] getAliases() {
		String[] args = {"AutoPilot", "AP"};
		return args;
	}

	@Override
	public String getDescription() {
		return "Move your ship without needing to be on it";
	}

	@Override
	public String getPermission() {
		return "ships.cmd.autopilot";
	}

	@Override
	public boolean execute(Player player, String... args) {
		if(args.length < 5){
			player.sendMessage(ShipsMain.formatCMDHelp("/ships autopilot l <ship name> <x> <y> <z> : Moves the targeted ship to the co-ords"));
			player.sendMessage(ShipsMain.formatCMDHelp("/ships autopilot a <ship name> <x> <y> <z> : Moves the targeted ship to its co-ords + the co-ords specified"));
		}else{
			Optional<LoadableShip> opShip = LoadableShip.getShip(args[2]);
			if(!opShip.isPresent()){
				player.sendMessage(ShipsMain.format(args[2] + " does not exsist", true));
				return true;
			}
			
			LoadableShip ship = opShip.get();
			if(player.hasPermission("ships.cmd.autopilot.other")){
				if(ship.getOwner().isPresent()){
					OfflinePlayer owner = ship.getOwner().get();
					if(!owner.getUniqueId().equals(player.getUniqueId())){
						player.sendMessage(ShipsMain.format("You do not have permission to modify the " + ship.getStatic().getName() + " " + ship.getName(), true));
						return true;
					}
				}
			}
			
			if(!(ship instanceof LiveAutoPilotable)){
				player.sendMessage(ShipsMain.format(ship.getName() + " can not be autopiloted", true));
				return true;
			}
			
			LiveAutoPilotable shipA = (LiveAutoPilotable)ship;
			if(shipA.getAutoPilotData().isPresent()){
				AutoPilot ap = shipA.getAutoPilotData().get();
				player.sendMessage(shipA.getName() + " is currently on a diffrent path. Estimated " + (ap.getMovements().size() - ap.getMovesDone()) + " moves left.");
				return true;
			}
			
			try{
				int x = Integer.parseInt(args[3]);
				int y = Integer.parseInt(args[4]);
				int z = Integer.parseInt(args[5]);
				AutoPilot ap = null;
				if(args[1].equalsIgnoreCase("l")){
					ap = new AutoPilot(shipA, new Location(ship.getLocation().getWorld(), x, y, z).getBlock(), ship.getStatic().getDefaultSpeed(), player);
				}else if(args[1].equalsIgnoreCase("a")){
					ap = new AutoPilot(shipA, x, y, z, ship.getStatic().getDefaultSpeed(), player);
				}else if(args[1].equalsIgnoreCase("s")){
					ap = new AutoPilot(shipA, -x, -y, -z, ship.getStatic().getDefaultSpeed(), player);
				}else{
					player.sendMessage(ShipsMain.format(args[1] + " is not valid", true));
					return true;
				}
				shipA.setAutoPilotData(ap);
				player.sendMessage(ShipsMain.format(ship.getName() + " is now in autopilot mode. Estimated " + ap.getMovements().size() + " moves", false));
				return true;
			}catch(NumberFormatException e){
				e.printStackTrace();
			}
			player.sendMessage(ShipsMain.format(args[3] + ", " + args[4] + "and/or " + args[5] + "are not whole numbers", true));
		}
		return false;
	}

}

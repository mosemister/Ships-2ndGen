package MoseShipsBukkit.Utils;

import org.bukkit.entity.Player;

import MoseShipsBukkit.Ships.VesselTypes.Satic.StaticShipType;

public class Permissions {

	public static final String CREATE_AIRSHIP = "ships.airship.make";
	
	public static final String BLOCK_LIST_CMD = "ships.cmd.blocklist";
	
	public static boolean hasPermissionToMake(Player player, StaticShipType type){
		return player.hasPermission("ships." + type.getName() + ".make");
	}
	
}

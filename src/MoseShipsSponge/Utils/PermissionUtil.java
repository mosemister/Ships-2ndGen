package MoseShipsSponge.Utils;

import org.spongepowered.api.entity.living.player.Player;

import MoseShipsSponge.Ships.VesselTypes.StaticShipType;

public class PermissionUtil {
	
	public static final String CREATE_AIRSHIP = "ship.airship.make";
	public static final String REMOVE_SHIP_LICENCE_OTHER = "ships.sign.licence.remove";
	public static final String BLOCK_LIST_CMD = "ships.cmd.blocklist";
	
	public static boolean hasPermissionToMake(Player player, StaticShipType type){
		return player.hasPermission("ships." + type.getName() + ".make");
	}

}

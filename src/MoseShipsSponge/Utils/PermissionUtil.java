package MoseShipsSponge.Utils;

import org.spongepowered.api.entity.living.player.Player;

import MoseShipsSponge.Vessel.Common.Static.StaticShipType;

public class PermissionUtil {

	public static final String CREATE_AIRSHIP = "ship.make.airship";
	
	public static final String USE_AIRSHIP_OWN = "ships.use.airship.own";
	public static final String USE_AIRSHIP_OTHER = "ships.use.airship";
	
	public static final String CHANGE_SHIP_OWNER = "ships.sign.owner.change";
	
	public static final String REMOVE_SHIP_LICENCE = "ships.sign.licence.remove";
	
	public static final String BLOCK_LIST_CMD = "ships.cmd.blocklist";

	public static boolean hasPermissionToMake(Player player, StaticShipType type) {
		return player.hasPermission("ships.make" + type.getName());
	}

}

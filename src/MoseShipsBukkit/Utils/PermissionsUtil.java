package MoseShipsBukkit.Utils;

import org.bukkit.entity.Player;

import MoseShipsBukkit.Vessel.Common.Static.StaticShipType;

public class PermissionsUtil {

	public static final String CREATE_AIRSHIP = "ships.airship.make";

	public static final String REMOVE_SHIP_LICENCE_OTHER = "ships.sign.licence.remove";

	public static final String BLOCK_LIST_CMD = "ships.cmd.blocklist";

	public static boolean hasPermissionToMake(Player player, StaticShipType type) {
		return player.hasPermission("ships." + type.getName() + ".make");
	}

}

package MoseShipsSponge.Utils;

import org.spongepowered.api.service.permission.Subject;

import MoseShipsSponge.Vessel.Common.Static.StaticShipType;

public class PermissionUtil {

	public static final String CREATE_AIRSHIP = "ship.make.airship";
	
	public static final String USE_AIRSHIP_OWN = "ships.use.airship.own";
	public static final String USE_AIRSHIP_OTHER = "ships.use.airship";
	
	public static final String REMOVE_SHIP_LICENCE = "ships.sign.licence.remove";
	
	public static final String BLOCK_LIST_CMD = "ships.cmd.blocklist";
	public static final String TRACK_CMD = "ships.cmd.sign.track";
	public static final String CHANGE_USER_CMD = "ships.cmd.sign.ownerchange.own";
	public static final String CHANGE_USER_CMD_OTHER = "ships.cmd.sign.ownerchange.other";
	public static final String INFO_CMD = "ships.cmd.info";
	public static final String INFO_SHIPS_CMD = "ships.cmd.info.ships.use";
	public static final String INFO_SHIPS_CMD_OTHER = "ships.cmd.info.ships.other";
	public static final String INFO_TYPES_CMD = "ships.cmd.info.types";
	public static final String SEE_BLOCK_CMD = "ships.cmd.blocklist.seeblock";
	public static final String SHOW_LIST_CMD = "ships.cmd.blocklist.showlist";
	public static final String SET_LIST_CMD = "ships.cmd.blocklist.setlist";
	public static final String LIST_TYPES_CMD = "ships.cmd.debug.listtypes";
	public static final String LIST_SHIPS_CMD = "ships.cmd.debug.listships";
	public static final String LOAD_SHIP_CMD = "ships.cmd.debug.loadship";
	public static final String RELOAD_CMD = "ships.cmd.debug.reload";
	public static final String TELEPORT_CMD = "ships.cmd.teleport.self";
	public static final String TELEPORT_CMD_OTHER = "ships.cmd.teleport.other";
	public static final String SET_TELEPORT_CMD = "ships.cmd.teleport.set";
	

	public static boolean hasPermissionToMake(Subject player, StaticShipType type) {
		return player.hasPermission("ships.make." + type.getName());
	}
	
	public static boolean hasPermissionToUse(Subject player, StaticShipType type) {
		return player.hasPermission("ships.use." + type.getName() + ".own");
	}

}

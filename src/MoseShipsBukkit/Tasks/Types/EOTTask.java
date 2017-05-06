package MoseShipsBukkit.Tasks.Types;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;

import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Tasks.ShipsTask;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;

public class EOTTask implements ShipsTask {

	Sign g_sign;
	OfflinePlayer g_player;

	public EOTTask(OfflinePlayer player, Sign sign) {
		g_sign = sign;
		g_player = player;
	}

	@Override
	public void onRun(LiveShip ship) {
		String line2 = g_sign.getLine(1);
		int speed = Integer.parseInt(g_sign.getLine(2));
		if (line2.equals("{" + ChatColor.GREEN + "Ahead" + ChatColor.BLACK + "} Stop")) {
			BlockFace direction = ((org.bukkit.material.Sign) g_sign.getData()).getFacing().getOppositeFace();
			ship.move(direction, speed, new ShipsCause(g_player, g_sign));
		} else {
			ship.getTaskRunner().unregister(this);
		}
	}

	@Override
	public long getDelay() {
		return 6;
	}

}

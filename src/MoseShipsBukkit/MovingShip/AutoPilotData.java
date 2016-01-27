package MoseShipsBukkit.MovingShip;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import MoseShipsBukkit.StillShip.Vessel.MovableVessel;

public class AutoPilotData {

	MovableVessel VESSEL;
	Location GOTO;
	int SPEED;
	OfflinePlayer PLAYER;

	public AutoPilotData(MovableVessel vessel, Location goTo, int speed, @Nullable OfflinePlayer player) {
		VESSEL = vessel;
		GOTO = goTo;
		SPEED = speed;
		PLAYER = player;
	}

	public MovableVessel getVessel() {
		return VESSEL;
	}

	public Location getMovingTo() {
		return GOTO;
	}

	public int getSpeed() {
		return SPEED;
	}

	public OfflinePlayer getOfflinePlayer() {
		return PLAYER;
	}

	public Player getPlayer() {
		return PLAYER.getPlayer();
	}

	public AutoPilotData setGoTo(Location loc) {
		GOTO = loc;
		return this;
	}

	public AutoPilotData setSpeed(int speed) {
		SPEED = speed;
		return this;
	}

	public AutoPilotData setPlayer(OfflinePlayer player) {
		PLAYER = player;
		return this;
	}

}

package org.ships.ship.movement;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.ships.ship.Ship;

public class AutoPilotData {

	Ship VESSEL;
	Location GOTO;
	int SPEED;
	OfflinePlayer PLAYER;

	public AutoPilotData(Ship vessel, Location goTo, int speed, OfflinePlayer player) {
		VESSEL = vessel;
		GOTO = goTo;
		SPEED = speed;
		PLAYER = player;
	}

	public Ship getVessel() {
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

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
		this.VESSEL = vessel;
		this.GOTO = goTo;
		this.SPEED = speed;
		this.PLAYER = player;
	}

	public Ship getVessel() {
		return this.VESSEL;
	}

	public Location getMovingTo() {
		return this.GOTO;
	}

	public int getSpeed() {
		return this.SPEED;
	}

	public OfflinePlayer getOfflinePlayer() {
		return this.PLAYER;
	}

	public Player getPlayer() {
		return this.PLAYER.getPlayer();
	}

	public AutoPilotData setGoTo(Location loc) {
		this.GOTO = loc;
		return this;
	}

	public AutoPilotData setSpeed(int speed) {
		this.SPEED = speed;
		return this;
	}

	public AutoPilotData setPlayer(OfflinePlayer player) {
		this.PLAYER = player;
		return this;
	}
}

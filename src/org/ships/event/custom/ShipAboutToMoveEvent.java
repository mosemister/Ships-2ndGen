package org.ships.event.custom;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.ships.ship.Ship;
import org.ships.ship.movement.MovementMethod;

public class ShipAboutToMoveEvent extends Event implements Cancellable {
	MovementMethod METHOD;
	int SPEED;
	Ship VESSEL;
	OfflinePlayer PLAYER;
	boolean CANCEL;
	static HandlerList LIST = new HandlerList();

	public ShipAboutToMoveEvent(MovementMethod method, int speed, Ship vessel, OfflinePlayer player) {
		this.METHOD = method;
		this.SPEED = speed;
		this.VESSEL = vessel;
		this.PLAYER = player;
	}

	public MovementMethod getMethod() {
		return this.METHOD;
	}

	public void setMethod(MovementMethod method) {
		this.METHOD = method;
	}

	public int getSpeed() {
		return this.SPEED;
	}

	public void setSpeed(int A) {
		this.SPEED = A;
	}

	public Player getPlayer() {
		return this.PLAYER.getPlayer();
	}

	public OfflinePlayer getOfflinePlayer() {
		return this.PLAYER;
	}

	public void setPlayer(OfflinePlayer player) {
		this.PLAYER = player;
	}

	public Ship getVessel() {
		return this.VESSEL;
	}

	@Override
	public boolean isCancelled() {
		return this.CANCEL;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.CANCEL = arg0;
	}

	@Override
	public HandlerList getHandlers() {
		return LIST;
	}

	public static HandlerList getHandlerList() {
		return LIST;
	}
}

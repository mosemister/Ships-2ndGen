package org.ships.event.custom;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.ships.ship.Ship;

public class ShipCreateEvent extends Event implements Cancellable {
	Player PLAYER;
	Sign SIGN;
	Ship VESSEL;
	boolean CANCELLED;
	static final HandlerList HANDLERS = new HandlerList();

	public ShipCreateEvent(Player player, Sign sign, Ship vessel) {
		this.PLAYER = player;
		this.SIGN = sign;
		this.VESSEL = vessel;
	}

	public Player getPlayer() {
		return this.PLAYER;
	}

	public Sign getSign() {
		return this.SIGN;
	}

	public Ship getVessel() {
		return this.VESSEL;
	}

	@Override
	public boolean isCancelled() {
		return this.CANCELLED;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.CANCELLED = arg0;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}

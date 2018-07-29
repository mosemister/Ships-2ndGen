package org.ships.event.custom;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.ships.ship.Ship;

public class ShipFinishedMovingEvent extends Event {
	Ship VESSEL;
	static final HandlerList HANDLERS = new HandlerList();

	public ShipFinishedMovingEvent(Ship vessel) {
		this.VESSEL = vessel;
	}

	public Ship getVessel() {
		return this.VESSEL;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}

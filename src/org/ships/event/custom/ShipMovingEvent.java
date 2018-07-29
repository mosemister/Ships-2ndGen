package org.ships.event.custom;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.ships.block.structure.MovingStructure;
import org.ships.ship.Ship;
import org.ships.ship.movement.MovementMethod;

public class ShipMovingEvent extends Event implements Cancellable {
	OfflinePlayer PLAYER;
	Ship VESSEL;
	MovementMethod MOVEMETHOD;
	MovingStructure MOVINGBLOCKS;
	boolean CANCELLED;
	static final HandlerList HANDLERS = new HandlerList();

	public ShipMovingEvent(OfflinePlayer player, Ship vessel, MovementMethod method, MovingStructure blocks) {
		this.PLAYER = player;
		this.VESSEL = vessel;
		this.MOVEMETHOD = method;
		this.MOVINGBLOCKS = blocks;
	}

	public Player getPlayer() {
		return this.PLAYER.getPlayer();
	}

	public OfflinePlayer getOfflinePlayer() {
		return this.PLAYER;
	}

	public Ship getVessel() {
		return this.VESSEL;
	}

	public MovementMethod getMovementMethod() {
		return this.MOVEMETHOD;
	}

	public MovingStructure getStructure() {
		return this.MOVINGBLOCKS;
	}

	public void setMovingBlocks(MovingStructure structure) {
		this.MOVINGBLOCKS = structure;
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

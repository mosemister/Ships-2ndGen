package org.ships.event.custom;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class ShipsSignCreation extends Event implements Cancellable {
	boolean CANCELLED;
	JavaPlugin PLUGIN;
	Player PLAYER;
	String[] SIGNTEXT;
	String[] TEXT;
	String[] RETURN_TEXT;
	Sign SIGN;
	static HandlerList LIST = new HandlerList();

	public ShipsSignCreation(JavaPlugin plugin, String[] signText, Sign sign) {
		this.PLUGIN = plugin;
		this.SIGNTEXT = signText;
		this.SIGN = sign;
		this.RETURN_TEXT = signText;
	}

	public ShipsSignCreation(JavaPlugin plugin, String[] signText, Sign sign, Player player, String... playerText) {
		this.PLUGIN = plugin;
		this.SIGNTEXT = signText;
		this.SIGN = sign;
		this.PLAYER = player;
		this.TEXT = playerText;
		this.RETURN_TEXT = signText;
	}

	public JavaPlugin getCause() {
		return this.PLUGIN;
	}

	public Player getPlayer() {
		return this.PLAYER;
	}

	public String[] getSignTypeResult() {
		return this.SIGNTEXT;
	}

	public String[] getTypedText() {
		return this.TEXT;
	}

	public Sign getSign() {
		return this.SIGN;
	}

	public String[] getReturnText() {
		return this.RETURN_TEXT;
	}

	public ShipsSignCreation setReturnText(String... text) {
		this.RETURN_TEXT = text;
		return this;
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
		return LIST;
	}

	public static HandlerList getHandlerList() {
		return LIST;
	}
}

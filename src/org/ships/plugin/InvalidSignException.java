package org.ships.plugin;

import java.io.IOException;

import org.bukkit.block.Sign;
import org.bukkit.event.block.SignChangeEvent;

public class InvalidSignException extends IOException {
	private static final long serialVersionUID = 1;

	public InvalidSignException() {
		super("Location is not sign");
	}

	public InvalidSignException(Sign sign) {
		super("Sign text can not be used (" + sign + ")");
	}

	public InvalidSignException(int line, Sign sign) {
		super("Sign line " + line + " (" + sign.getLine(line) + ") can not be used");
	}

	public InvalidSignException(int line, SignChangeEvent event) {
		super("Sign line " + line + " (" + event.getLine(line) + ") can not be used");
	}
}

package MoseShipsBukkit.Utils.Exceptions;

import java.io.IOException;

import org.bukkit.block.Sign;

public class InvalidSignException extends IOException {

	private static final long serialVersionUID = 1L;

	public InvalidSignException(Sign sign) {
		super("Sign text can not be used (" + sign + ")");
	}

	public InvalidSignException(int line, Sign sign) {
		super("Sign line " + line + " (" + sign.getLine(line) + ") can not be used ");
	}

}

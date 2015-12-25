package MoseShipsBukkit.ShipsTypes;

import org.bukkit.ChatColor;

public enum ShipSignTypes {
	MOVE_SIGN(ChatColor.YELLOW + "[Move]"), 
	WHEEL_SIGN(ChatColor.YELLOW + "[Wheel]"), 
	ALTITUDE_SIGN(ChatColor.YELLOW + "[Altitude]"), 
	EOT_SIGN(ChatColor.YELLOW + "[E.O.T]"), 
	LICENSE_SIGN(ChatColor.YELLOW + "[Ships]");

	private String text;

	private ShipSignTypes(String text) {
		this.text = text;
	}
	
	public static ShipSignTypes fromString(String text) {
		if (text != null) {
			for (ShipSignTypes s : ShipSignTypes.values()) {
				if (text.equalsIgnoreCase(s.text)) {
					return s;
				}
			}
		}
		return null;
	}
}

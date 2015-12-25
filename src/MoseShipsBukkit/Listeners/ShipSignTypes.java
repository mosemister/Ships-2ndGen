package MoseShipsBukkit.Listeners;

import org.bukkit.ChatColor;

public enum ShipSignTypes {
	MoveSign(ChatColor.YELLOW + "[Move]"), 
	WheelSign(ChatColor.YELLOW + "[Wheel]"), 
	AltitudeSign(ChatColor.YELLOW + "[Altitude]"), 
	EOTSign(ChatColor.YELLOW + "[E.O.T]"), 
	LicenseSign(ChatColor.YELLOW + "[Ships]");

	private String text;

	ShipSignTypes(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
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

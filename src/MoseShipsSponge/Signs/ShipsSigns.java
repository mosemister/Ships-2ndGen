package MoseShipsSponge.Signs;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;

public class ShipsSigns {

	/**
	 * this colours the sign in the licence sign format
	 * 
	 * @param sign:
	 *            the target sign
	 * @return: the same sign
	 */
	public static Sign colourSign(Sign sign) {
		String[] lines = sign.getLines();
		for(int A = 0; A < lines.length; A++){
			sign.setLine(A, lines[A]);
		}
		sign.update();
		return sign;
	}

	public static String[] colour(String... lines) {
		return colour(Arrays.asList(lines));
	}
	
	public static String[] colour(List<String> lines) {
		String[] lines2 = new String[lines.size()];
		lines2[0] = ChatColor.YELLOW + lines.get(0);
		lines2[1] = ChatColor.BLUE + lines.get(1);
		for (int A = 2; A < lines.size(); A++) {
			lines2[A] = ChatColor.GREEN + lines.get(A);
		}
		return lines2;
	}

	public static Optional<SignType> getSignType(String line1) {
		for(SignType type : SignType.values()){
			if(ChatColor.stripColor(type.LINES[0]).equalsIgnoreCase(ChatColor.stripColor(line1))){
				return Optional.of(type);
			}
		}
		return Optional.empty();
	}

	public static Optional<SignType> getSignType(Sign sign) {
		return getSignType(sign.getLine(0));
	}

	public enum SignType {
		LICENCE(ChatColor.YELLOW + "[Ships]"),
		MOVE(ChatColor.YELLOW + "[Move]", ChatColor.GREEN + "{Engine}", "Boost"),
		WHEEL(ChatColor.YELLOW + "[Wheel]", ChatColor.RED + "\\\\||//", ChatColor.RED + "==||==", ChatColor.RED + "//==\\\\"),
		EOT(ChatColor.YELLOW + "[EOT]"),
		ALTITUDE(ChatColor.YELLOW + "[Altitude]");

		String[] LINES;

		SignType(String... text) {
			LINES = text;
		}

		public Optional<String[]> getDefaultLines() {
			return Optional.ofNullable(LINES);
		}
	}

}

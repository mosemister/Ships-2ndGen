package MoseShipsSponge;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.spongepowered.api.MinecraftVersion;
import org.spongepowered.api.Sponge;

public class VersionChecking {
	
	public static final int[] MINECRAFT_VERSION = convert(getMinecraftVersion());
	public static final int[] SHIPS_VERSION = convert(ShipsMain.VERSION);
	
	public static int[] convert(String version) {
		String[] args = version.split(Pattern.quote("."));
		int[] version2 = new int[args.length];
		for (int A = 0; A < args.length; A++) {
			String part1 = args[A].split(Pattern.quote("|"))[0];
			version2[A] = Integer.parseInt(part1);
		}
		return version2;
}
	
	public static String toString(int... value) {
		String ret = null;
		for (int A : value) {
			if (ret == null) {
				ret = A + "";
			} else {
				ret = ret + "." + A;
			}
		}
		return ret;
}
	
	public static VersionOutcome isGreater(int[] origin, int... compare) {
		List<Integer> origin2 = new ArrayList<Integer>();
		for (int A : origin) {
			origin2.add(A);
		}
		List<Integer> compare2 = new ArrayList<Integer>();
		for (int A : compare) {
			compare2.add(A);
		}
		if (origin2.size() != compare2.size()) {
			if (origin2.size() > compare2.size()) {
				int add = origin2.size() - compare2.size();
				for (int A = 0; A < add; A++) {
					compare2.add(0);
				}
			} else {
				int add = compare2.size() - origin2.size();
				for (int A = 0; A < add; A++) {
					origin2.add(0);
				}
			}
		}

		for (int A = 0; A < origin2.size(); A++) {
			int oValue = origin2.get(A);
			int cValue = compare2.get(A);
			if (oValue > cValue) {
				return VersionOutcome.GREATER;
			} else if (oValue < cValue) {
				return VersionOutcome.LOWER;
			}
		}
		return VersionOutcome.EQUAL;
}
	
	private static String getMinecraftVersion() {
		
		MinecraftVersion version = Sponge.getPlatform().getMinecraftVersion();
		String versionS = version.getName();
		System.out.println("Minecraft version: " + versionS);
		return versionS;
	}

	public enum VersionOutcome {
		GREATER,
		LOWER,
		EQUAL;
	}


}

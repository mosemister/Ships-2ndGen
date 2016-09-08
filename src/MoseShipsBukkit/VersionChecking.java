package MoseShipsBukkit;

import java.util.ArrayList;
import java.util.List;

public class VersionChecking {
	
	public static int[] MINECRAFT_VERSION = convert(getMinecraftVersion());
	public static final int[] SHIPS_VERSION = convert(ShipsMain.VERSION);
	
	public static int[] convert(String version){
		String[] args = version.split("\\.");
		int[] version2 = new int[args.length];
		for(int A = 0; A < args.length; A++){
			version2[A] = Integer.parseInt(args[A]);
		}
		return version2;
	}
	
	public static String toString(int... value){
		String ret = null;
		for(int A : value){
			if(ret == null){
				ret = A + "";
			}else{
				ret = ret + "," + A;
			}
		}
		return ret;
	}
	
	public static VersionOutcome isGreater(int[] origin, int[] compare){
		List<Integer> origin2 = new ArrayList<Integer>();
		for(int A : origin){
			origin2.add(A);
		}
		List<Integer> compare2 = new ArrayList<Integer>();
		for(int A : compare){
			compare2.add(A);
		}
		if(origin2.size() != compare2.size()){
			if(origin2.size() > compare2.size()){
				int add = origin2.size() - compare2.size();
				for(int A = 0;A < add; A++){
					compare2.add(0);
				}
			}else{
				int add = compare2.size() - origin2.size();
				for(int A = 0;A < add; A++){
					origin2.add(0);
				}
			}
		}
		
		for(int A =0; A < origin2.size(); A++){
			int oValue = origin2.get(A);
			int cValue = compare2.get(A);
			if(oValue > cValue){
				return VersionOutcome.GREATER;
			}else if (oValue < cValue){
				return VersionOutcome.LOWER;
			}
		}
		return VersionOutcome.EQUAL;
	}
	
	private static String getMinecraftVersion() {
		String temp = ShipsMain.getPlugin().getServer().getVersion();
		// String version = tempVersion.split("'(MC: ', ')'")[1];
		String[] part1 = temp.split(":");
		String part2 = part1[1].replace(")", "");
		String part3 = part2.replace(" ", "");
		return part3;
}
	
	public enum VersionOutcome{
		GREATER,
		LOWER,
		EQUAL;
	}

}

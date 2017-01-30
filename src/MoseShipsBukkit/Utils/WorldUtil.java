package MoseShipsBukkit.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.BlockFace;

public class WorldUtil {

	public static Map<World, BlockFace> WIND_DIRECTIONS = loadDirections();

	public static Map<World, BlockFace> loadDirections() {
		Map<World, BlockFace> directions = new HashMap<World, BlockFace>();
		BlockFace[] faces = {
				BlockFace.EAST,
				BlockFace.NORTH,
				BlockFace.SOUTH,
				BlockFace.WEST };
		Random ran = new Random();
		for (World world : Bukkit.getWorlds()) {
			int random = ran.nextInt(faces.length - 1);
			directions.put(world, faces[random]);
		}
		return directions;

	}

}

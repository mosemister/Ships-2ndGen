package org.ships.configuration.parsers;

import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.World;

public class LocationParser implements Parser<Location, String> {
	@Override
	public Optional<Location> parse(String parse) {
		String[] array = parse.split(",");
		DoubleParser parser = new DoubleParser();
		WorldParser worldParser = new WorldParser();
		try {
			double X = parser.parse(array[0]).get();
			double Y = parser.parse(array[1]).get();
			double Z = parser.parse(array[2]).get();
			Optional<World> opWorld = worldParser.parse(array[3]);
			if (opWorld.isPresent()) {
				return Optional.of(new Location(opWorld.get(), X, Y, Z));
			}
		} catch (NumberFormatException X) {
		}
		return Optional.empty();
	}

	@Override
	public String toString(Location string) {
		DoubleParser parser = new DoubleParser();
		return parser.toString(string.getX()) + "," + parser.toString(string.getY()) + "," + parser.toString(string.getZ()) + "," + string.getWorld().getName();
	}
}

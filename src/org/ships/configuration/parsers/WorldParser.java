package org.ships.configuration.parsers;

import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldParser implements Parser<World, String> {
	@Override
	public Optional<World> parse(String parse) {
		World world = Bukkit.getWorld(parse);
		if (world != null) {
			world = Bukkit.getWorld(UUID.fromString(parse));
		}
		return Optional.ofNullable(world);
	}

	@Override
	public String toString(World string) {
		return string.getUID().toString();
	}
}

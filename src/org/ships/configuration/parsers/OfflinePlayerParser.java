package org.ships.configuration.parsers;

import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class OfflinePlayerParser implements Parser<OfflinePlayer, String> {
	@SuppressWarnings("deprecation")
	@Override
	public Optional<OfflinePlayer> parse(String parse) {
		try {
			UUID uuid = UUID.fromString(parse);
			OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
			return Optional.of(offlinePlayer);
		} catch (Throwable uuid) {
			OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(parse);
			return Optional.ofNullable(offlinePlayer);
		}
	}

	@Override
	public String toString(OfflinePlayer string) {
		return string.getUniqueId().toString();
	}
}

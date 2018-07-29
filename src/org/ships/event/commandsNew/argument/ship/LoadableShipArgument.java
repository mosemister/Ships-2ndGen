package org.ships.event.commandsNew.argument.ship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ships.event.commandsNew.argument.CommandArgument;
import org.ships.event.commandsNew.argument.OptionalCommandArgument;
import org.ships.event.commandsNew.executor.CMDArgumentResults;
import org.ships.ship.LoadableShip;

public class LoadableShipArgument implements OptionalCommandArgument<LoadableShip> {
	String key;
	String permissionToUseOtherShips;
	boolean hasPermissionToUseOtherShips;

	public LoadableShipArgument(String key) {
		this(key, null);
	}

	public LoadableShipArgument(String key, String permissionToUseOtherShips) {
		this.key = key;
		if (permissionToUseOtherShips != null) {
			this.permissionToUseOtherShips = permissionToUseOtherShips;
			this.hasPermissionToUseOtherShips = true;
		}
	}

	@Override
	public Optional<LoadableShip> getDefaultValue(CommandSender sender) {
		if (!(sender instanceof Player)) {
			return Optional.empty();
		}
		Player player = (Player) sender;
		Block block = player.getTargetBlock(new HashSet<Material>(Arrays.asList(new Material[] { Material.AIR })), 5);
		if (!(block.getState() instanceof Sign)) {
			return Optional.empty();
		}
		Sign sign = (Sign) block.getState();
		if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")) {
			return Optional.ofNullable(LoadableShip.getShip(sign));
		}
		return Optional.empty();
	}

	@Override
	public String getAssignedKey() {
		return this.key;
	}

	@Override
	public CommandArgument<LoadableShip> setAssignedKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public List<String> onTab(CommandSender sender, CMDArgumentResults results, String toParse) {
		ArrayList<String> list = new ArrayList<String>();
		LoadableShip.getShips().stream().filter(s -> s.getName().toLowerCase().startsWith(toParse.toLowerCase())).forEach(s -> {
			list.add(s.getName());
		});
		return list;
	}

	@Override
	public Optional<LoadableShip> onParse(CommandSender sender, CMDArgumentResults results, String toParse) {
		return Optional.ofNullable(LoadableShip.getShip(toParse));
	}
}

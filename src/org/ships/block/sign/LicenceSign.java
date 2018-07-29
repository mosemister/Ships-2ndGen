package org.ships.block.sign;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;
import org.ships.configuration.Config;
import org.ships.plugin.Ships;
import org.ships.ship.LoadableShip;
import org.ships.ship.type.VesselType;
import org.ships.ship.type.hooks.Fuel;
import org.ships.ship.type.hooks.RequiredMaterial;
import org.ships.utils.ListUtil;

public class LicenceSign implements ShipSign.OnRightClick.OnStand, ShipSign.OnRightClick.OnShift {
	@Override
	public String[] getLines() {
		String[] lines = new String[4];
		lines[0] = ChatColor.YELLOW + "[Ships]";
		return lines;
	}

	@Override
	public String[] getAliases() {
		return new String[0];
	}

	@Override
	public String getName() {
		return "Licence";
	}

	@Override
	public boolean isSign(Sign sign) {
		return sign.getLine(0).equals(this.getLines()[0]);
	}

	@Override
	public boolean onCreate(SignChangeEvent event) {
		String name = event.getLine(2);
		if (name.replace(" ", "").length() == 0) {
			event.getPlayer().sendMessage(Ships.runShipsMessage("Name must be specified on line 3", true));
			return true;
		}
		VesselType type = VesselType.getTypeByName(event.getLine(1));
		if (type == null) {
			event.getPlayer().sendMessage(Ships.runShipsMessage("Vessel type does not exist", true));
			return true;
		}
		type = type.createClone();
		if (event.getPlayer().hasPermission("ships." + type.getName() + ".make") || event.getPlayer().hasPermission("ships.*.make") || event.getPlayer().hasPermission("ships.*")) {
			if (LoadableShip.getShip(event.getLine(2)) != null) {
				event.getPlayer().sendMessage(Ships.runShipsMessage("Name taken", true));
				return true;
			}
			new LoadableShip(type, event);
			event.setLine(0, ChatColor.YELLOW + "[Ships]");
			event.setLine(1, ChatColor.BLUE + type.getName());
			event.setLine(2, ChatColor.GREEN + name);
			event.setLine(3, ChatColor.GREEN + event.getLine(3));
			YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
			if (config.getBoolean("Signs.ForceUsernameOnLicenceSign")) {
				event.setLine(3, ChatColor.GREEN + event.getPlayer().getName());
			}
			return false;
		}
		event.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to create that vessel");
		return false;
	}

	@Override
	public void onShiftRightClick(Player player, Sign sign) {
		LoadableShip ship = LoadableShip.getShip(sign);
		if (ship.hasPermissionToMove(player)) {
			ship.updateStructure();
			player.sendMessage(ChatColor.AQUA + "This ship's structure is now up to date");
			return;
		}
		player.sendMessage(ChatColor.AQUA + "You do not have permission to update the ships structure");
	}

	@Override
	public void onRightClick(Player player, Sign sign) {
		LoadableShip ship = LoadableShip.getShip(sign);
		player.sendMessage("===[Ships Information Log]===");
		player.sendMessage(ChatColor.AQUA + "Name: " + ChatColor.GOLD + ship.getName());
		player.sendMessage(ChatColor.AQUA + "Type: " + ChatColor.GOLD + ship.getVesselType().getName());
		player.sendMessage(ChatColor.AQUA + "Speed: " + ChatColor.GOLD + ship.getVesselType().getDefaultSpeed());
		player.sendMessage(ChatColor.AQUA + "Boost: " + ChatColor.GOLD + ship.getVesselType().getDefaultBoostSpeed());
		player.sendMessage(ChatColor.AQUA + "Owner: " + ChatColor.GOLD + ship.getOwner().getName());
		player.sendMessage(ChatColor.AQUA + "SubPlot count: " + ChatColor.GOLD + ship.getSubPilots().size());
		player.sendMessage(ChatColor.AQUA + "Structure size: " + ChatColor.GOLD + ship.getStructure().getAllBlocks().size());
		player.sendMessage(ChatColor.AQUA + "Location: " + ChatColor.GOLD + ship.getLocation().getBlockX() + ", " + ship.getLocation().getBlockY() + ", " + ship.getLocation().getBlockZ());
		player.sendMessage(ChatColor.AQUA + "Teleport Location: " + ChatColor.GOLD + ship.getTeleportLocation().getBlockX() + ", " + ship.getTeleportLocation().getBlockY() + ", " + ship.getTeleportLocation().getBlockZ());
		player.sendMessage(ChatColor.AQUA + "Water level: " + ChatColor.GOLD + ship.getWaterLevel());
		player.sendMessage(ChatColor.AQUA + "Entities on board: " + ChatColor.GOLD + ship.getEntities().size());
		player.sendMessage(ChatColor.AQUA + "Can you pilot: " + ChatColor.GOLD + ship.hasPermissionToMove(player));
		if (ship.getVesselType() instanceof Fuel) {
			Fuel fuelType = (Fuel) (ship.getVesselType());
			player.sendMessage(ChatColor.AQUA + "Fuel count: " + ChatColor.GOLD + fuelType.getTotalFuel(ship));
			player.sendMessage(ChatColor.AQUA + "Fuel consumption: " + ChatColor.GOLD + fuelType.getFuelConsumption());
			player.sendMessage(ChatColor.AQUA + "Structure size: " + ChatColor.GOLD + ListUtil.asStringList(fuelType.getFuelTypes(), ", ", m -> m.name()));
		}
		if (ship.getVesselType() instanceof RequiredMaterial) {
			RequiredMaterial materialType = (RequiredMaterial) (ship.getVesselType());
			player.sendMessage(ChatColor.AQUA + "Required percent of material: " + ChatColor.GOLD + materialType.getRequiredPercent());
			player.sendMessage(ChatColor.AQUA + "Structure size: " + ChatColor.GOLD + ListUtil.asStringList(materialType.getRequiredMaterials(), ", ", m -> m.name()));
		}
	}
}

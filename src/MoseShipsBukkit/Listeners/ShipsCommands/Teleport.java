package MoseShipsBukkit.Listeners.ShipsCommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.GUI.ShipsGUICommand;
import MoseShipsBukkit.Listeners.CommandLauncher;
import MoseShipsBukkit.StillShip.Vessel.Vessel;

public class Teleport extends CommandLauncher {

	public Teleport() {
		super("teleport", "<vessel name>", "teleport to a vessel", "ships.command.teleport", true, false,
				TeleportGUI.class);
	}

	@Override
	public void playerCommand(Player player, String[] args) {
		if (args.length == 1) {
			if (player.hasPermission("ships.command.teleport.other")) {
				player.sendMessage(
						ChatColor.GOLD + "/ships teleport <vessel name>" + ChatColor.AQUA + "; teleport to any vessel");
			} else {
				player.sendMessage(ChatColor.GOLD + "/ships teleport <vessel name>" + ChatColor.AQUA
						+ "; teleport to your vessel");
			}
		} else {
			Vessel vessel = Vessel.getVessel(args[1]);
			if (vessel == null) {
				player.sendMessage(Ships.runShipsMessage("Can not find vessel", true));
			} else {
				if ((vessel.getOwner().equals(player)) || (player.hasPermission("ships.command.teleport.other"))) {
					player.teleport(vessel.getTeleportLocation());
				}
			}
		}

	}

	@Override
	public void consoleCommand(ConsoleCommandSender sender, String[] args) {

	}

	public static class TeleportGUI extends ShipsGUICommand {

		public TeleportGUI(CommandLauncher command) {
			super(command);
		}

		@Override
		public void onScreenClick(HumanEntity players, ItemStack item, Inventory inv, int slot, ClickType type) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onInterfaceBoot(HumanEntity player) {
			List<ItemStack> stacks = new ArrayList<ItemStack>();
			for (Vessel vessel : Vessel.getVessels((Player) player)) {
				ItemStack stack = new ItemStack(Material.ARROW, 1);
				ItemMeta meta = stack.getItemMeta();
				meta.setDisplayName(vessel.getName());
				List<String> lore = new ArrayList<String>();
				Location loc = vessel.getTeleportLocation();
				lore.add(loc.getBlockX() + "," + loc.getBlockY() + loc.getBlockZ());
				meta.setLore(lore);
				stack.setItemMeta(meta);
				stacks.add(stack);
			}
			Inventory inv = createPageGUI(stacks, "Choose Vessel", 1, true);
			player.openInventory(inv);
		}

		@Override
		public String getInventoryName() {
			return "Choose Vessel";
		}

	}
}

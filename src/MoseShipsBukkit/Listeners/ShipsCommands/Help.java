package MoseShipsBukkit.Listeners.ShipsCommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import MoseShipsBukkit.GUI.ShipsGUICommand;
import MoseShipsBukkit.Listeners.CommandLauncher;

public class Help extends CommandLauncher {

	public Help() {
		super("help", "<page>/GUI", "Displays all commands", null, true, true, HelpGUI.class);
		new HelpGUI(this);

	}

	@Override
	public void playerCommand(Player player, String[] args) {
		if (args.length >= 2) {
			if (args[1].equalsIgnoreCase("GUI")) {
				runGUI(player);
				return;
			}
		}
		for (CommandLauncher command : CommandLauncher.getCommands()) {
			if (command.isPlayerCommand()) {
				if (command.getPermissions() != null) {
					if (player.hasPermission(command.getPermissions())) {
						player.sendMessage(ChatColor.GOLD + command.getCommand() + " " + command.getExtraArgs()
								+ ChatColor.AQUA + ": " + command.getDescription());
					}
				} else {
					player.sendMessage(ChatColor.GOLD + command.getCommand() + " " + command.getExtraArgs()
							+ ChatColor.AQUA + ": " + command.getDescription());
				}
			}
		}
	}

	@Override
	public void consoleCommand(ConsoleCommandSender sender, String[] args) {
		for (CommandLauncher command : CommandLauncher.getCommands()) {
			if (command.isConsoleCommand()) {
				sender.sendMessage(
						ChatColor.GOLD + command.getCommand() + ChatColor.AQUA + ": " + command.getDescription());
			}
		}
	}

	public static class HelpGUI extends ShipsGUICommand {

		public HelpGUI(CommandLauncher command) {
			super(command);
		}

		@Override
		public void onScreenClick(HumanEntity player, ItemStack item, Inventory inv, int slot, ClickType type) {
			if (item != null) {
				ItemMeta meta = item.getItemMeta();
				if (meta != null) {
					String name = meta.getDisplayName();
					try {
						player.closeInventory();
						CommandLauncher launcher = CommandLauncher.getCommand(name).get(0);
						launcher.runGUI(player);
					} catch (IndexOutOfBoundsException e) {
						if (item.equals(ShipsGUICommand.FORWARD_BUTTON)) {
							onInterfaceBoot(player, getPage(inv) + 1);
						} else if (item.equals(ShipsGUICommand.BACK_BUTTON)) {
							onInterfaceBoot(player, getPage(inv) - 1);
						}
					}
				}
			}

		}

		public void onInterfaceBoot(HumanEntity player, int page) {
			List<ItemStack> items = new ArrayList<ItemStack>();
			for (CommandLauncher launcher : CommandLauncher.getCommands()) {
				if (launcher.isPlayerCommand()) {
					if ((launcher.getPermissions() == null) || (player.hasPermission(launcher.getPermissions()))) {
						if (launcher.hasGUI()) {
							ItemStack item = new ItemStack(Material.PAPER, 1);
							ItemMeta data = item.getItemMeta();
							data.setDisplayName(launcher.getCommand());
							data.setLore(Arrays.asList(launcher.getDescription()));
							item.setItemMeta(data);
							items.add(item);
						}
					}
				}
			}
			Inventory inv = createPageGUI(items, "Help", page, true);
			player.openInventory(inv);
		}

		@Override
		public void onInterfaceBoot(HumanEntity player) {
			onInterfaceBoot(player, 1);
		}

		@Override
		public String getInventoryName() {
			return "Help";
		}
	}

}

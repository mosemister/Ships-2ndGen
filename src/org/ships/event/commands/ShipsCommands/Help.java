package org.ships.event.commands.ShipsCommands;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ships.event.commands.CommandLauncher;
import org.ships.event.commands.gui.ShipsGUICommand;

public class Help extends CommandLauncher {
	public Help() {
		super("help", "<page>/GUI", "Displays all commands", null, true, true, HelpGUI.class);
		new HelpGUI(this);
	}

	@Override
	public void playerCommand(Player player, String[] args) {
		if (args.length >= 2 && args[1].equalsIgnoreCase("GUI")) {
			this.runGUI(player);
			return;
		}
		for (CommandLauncher command : CommandLauncher.getCommands()) {
			if (!command.isPlayerCommand())
				continue;
			if (command.getPermissions() != null) {
				if (!player.hasPermission(command.getPermissions()))
					continue;
				player.sendMessage(ChatColor.GOLD + command.getCommand() + " " + command.getExtraArgs() + ChatColor.AQUA + ": " + command.getDescription());
				continue;
			}
			player.sendMessage(ChatColor.GOLD + command.getCommand() + " " + command.getExtraArgs() + ChatColor.AQUA + ": " + command.getDescription());
		}
	}

	@Override
	public void consoleCommand(ConsoleCommandSender sender, String[] args) {
		for (CommandLauncher command : CommandLauncher.getCommands()) {
			if (!command.isConsoleCommand())
				continue;
			sender.sendMessage(ChatColor.GOLD + command.getCommand() + ChatColor.AQUA + ": " + command.getDescription());
		}
	}

	public static class HelpGUI extends ShipsGUICommand {
		public HelpGUI(CommandLauncher command) {
			super(command);
		}

		@Override
		public void onScreenClick(HumanEntity player, ItemStack item, Inventory inv, int slot, ClickType type) {
			block4: {
				ItemMeta meta;
				if (item != null && (meta = item.getItemMeta()) != null) {
					String name = meta.getDisplayName();
					try {
						player.closeInventory();
						CommandLauncher launcher = CommandLauncher.getCommand(name).get(0);
						launcher.runGUI(player);
					} catch (IndexOutOfBoundsException e) {
						if (item.equals(ShipsGUICommand.FORWARD_BUTTON)) {
							this.onInterfaceBoot(player, this.getPage(inv) + 1);
						}
						if (!item.equals(ShipsGUICommand.BACK_BUTTON))
							break block4;
						this.onInterfaceBoot(player, this.getPage(inv) - 1);
					}
				}
			}
		}

		public void onInterfaceBoot(HumanEntity player, int page) {
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			for (CommandLauncher launcher : CommandLauncher.getCommands()) {
				if (!launcher.isPlayerCommand() || launcher.getPermissions() != null && !player.hasPermission(launcher.getPermissions()) || !launcher.hasGUI())
					continue;
				ItemStack item = new ItemStack(Material.PAPER, 1);
				ItemMeta data = item.getItemMeta();
				data.setDisplayName(launcher.getCommand());
				data.setLore(Arrays.asList(launcher.getDescription()));
				item.setItemMeta(data);
				items.add(item);
			}
			Inventory inv = ShipsGUICommand.createPageGUI(items, "Help", page, true);
			player.openInventory(inv);
		}

		@Override
		public void onInterfaceBoot(HumanEntity player) {
			this.onInterfaceBoot(player, 1);
		}

		@Override
		public String getInventoryName() {
			return "Help";
		}
	}

}

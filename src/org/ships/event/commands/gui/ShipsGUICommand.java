package org.ships.event.commands.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ships.event.commands.CommandLauncher;

public abstract class ShipsGUICommand {

	public static final ItemStack BACK_BUTTON = new ItemStack(Material.PAPER, 1);
	public static final ItemStack FORWARD_BUTTON = new ItemStack(Material.PAPER, 1);

	CommandLauncher LAUNCHER;

	static List<ShipsGUICommand> COMMANDS = new ArrayList<ShipsGUICommand>();

	public abstract void onScreenClick(HumanEntity humanEntity, ItemStack item, Inventory inv, int slot,
			ClickType type);

	public abstract void onInterfaceBoot(HumanEntity player);

	public abstract String getInventoryName();

	public ShipsGUICommand(CommandLauncher command) {
		LAUNCHER = command;
		COMMANDS.add(this);
	}

	public static List<ShipsGUICommand> getInterfaces() {
		return COMMANDS;
	}

	public static ItemStack getPageItem(int page) {
		ItemStack item = new ItemStack(Material.BOOK, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Page " + page);
		item.setItemMeta(meta);
		return item;
	}

	public static Inventory createPageGUI(List<ItemStack> items, String name, int page, boolean useAlgorthum) {
		Inventory inv = Bukkit.createInventory(null, 54, name);
		int min = ((page - 1) * 45);
		inv.setItem(inv.getSize() - 9, BACK_BUTTON);
		inv.setItem(inv.getSize() - 1, FORWARD_BUTTON);
		inv.setItem(inv.getSize() - 5, getPageItem(page));
		if (useAlgorthum) {
			try {
				for (int A = 0; A < 45; A++) {
					inv.setItem(A, items.get(min + A));
				}
			} catch (Exception e) {
				return inv;
			}
		} else {
			try {
				for (int A = 0; A < 45; A++) {
					inv.setItem(A, items.get(A));
				}
			} catch (Exception e) {
				return inv;
			}
		}
		return inv;
	}

	public int getPage(Inventory inv) {
		ItemStack item = inv.getItem(inv.getSize() - 5);
		String name = item.getItemMeta().getDisplayName();
		int page = Integer.parseInt(name.split(" ")[1]);
		return page;
	}

	public static void setGUITools() {
		ItemMeta backMeta = BACK_BUTTON.getItemMeta();
		backMeta.setDisplayName("Back");
		BACK_BUTTON.setItemMeta(backMeta);

		ItemMeta forwardMeta = FORWARD_BUTTON.getItemMeta();
		forwardMeta.setDisplayName("Forward");
		FORWARD_BUTTON.setItemMeta(forwardMeta);

	}

}

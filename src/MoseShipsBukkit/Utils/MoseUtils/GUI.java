package MoseShipsBukkit.Utils.MoseUtils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import MoseShipsBukkit.Ships;

public class GUI {
	
	public static void displayGUI(Player player, List<ItemStack> items, int pageNo){
		int size = (((int)items.size()/9)+2)*9;
		int maxPage = ((int)items.size()/64)+1;
		int pageDelay = pageNo*64;
		if (pageNo > maxPage){
			Bukkit.getConsoleSender().sendMessage(Ships.runShipsMessage("Something went wrong with GUI (Page number > max)", true));
			return;
		}
		if (size > 64){
			size = 64;
		}
		Inventory inv = Bukkit.createInventory(player, size, ChatColor.AQUA + "Ships");
		for (int A = 0; A < size; A++){
			inv.setItem(A, items.get(pageDelay + A));
		}
		player.openInventory(inv);
	}
	
	

}

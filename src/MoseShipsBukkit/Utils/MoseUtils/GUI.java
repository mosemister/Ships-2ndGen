package MoseShipsBukkit.Utils.MoseUtils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import MoseShipsBukkit.Ships;

public class GUI {
	
	Player PLAYER;
	Inventory INVENTORY;
	
	static List<GUI> GUIS = new ArrayList<GUI>();
	
	GUI(Player player){
		PLAYER = player;
	}
	
	public Inventory getInventory(){
		return INVENTORY;
	}
	
	public Player getPlayer(){
		return PLAYER;
	}
	
	public void remove(){
		GUIS.remove(this);
	}
	
	public void setInventory(Inventory inventory){
		INVENTORY = inventory;
	}
	
	public static GUI getInterface(Player player){
		for (GUI gui : GUIS){
			if (gui.getPlayer().equals(player)){
				return gui;
			}
		}
		return null;
	}
	
	public static class BlankGUI extends GUI{
		BlankGUI(Player player) {
			super(player);
		}

		public static GUI createGUI(Player player, List<ItemStack> items, int pageNo){
			int size = (((int)items.size()/9)+2)*9;
			int maxPage = ((int)items.size()/64)+1;
			int pageDelay = pageNo*64;
			if (pageNo > maxPage){
				Bukkit.getConsoleSender().sendMessage(Ships.runShipsMessage("Something went wrong with GUI (Page number > max)", true));
				return null;
			}
			if (size > 64){
				size = 64;
			}
			Inventory inv = Bukkit.createInventory(null, size, ChatColor.AQUA + "Ships GUI");
			for (int A = 0; A < size; A++){
				inv.setItem(A, items.get(pageDelay + A));
			}
			GUI gui = GUI.getInterface(player);
			gui.setInventory(inv);
			return gui;
		}
	}

}

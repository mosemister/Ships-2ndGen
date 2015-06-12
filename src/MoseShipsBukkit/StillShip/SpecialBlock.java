package MoseShipsBukkit.StillShip;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Dropper;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.block.Sign;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SpecialBlock {
	
	Block BLOCK;
	String LINE1;
	String LINE2;
	String LINE3;
	String LINE4;
	Map<Integer, ItemStack> INVENTORY = new HashMap<Integer, ItemStack>();
	String BLOCKTYPE;
	boolean DOUBLECHEST;
	
	public SpecialBlock(Sign sign){
		BLOCK = sign.getBlock();
		LINE1 = sign.getLine(0);
		LINE2 = sign.getLine(1);
		LINE3 = sign.getLine(2);
		LINE4 = sign.getLine(3);
		BLOCKTYPE = "sign";
	}
	
	public SpecialBlock(Furnace furnace){
		BLOCK = furnace.getBlock();
		INVENTORY.put(1, furnace.getInventory().getFuel());
		INVENTORY.put(2, furnace.getInventory().getResult());
		INVENTORY.put(3, furnace.getInventory().getSmelting());
		BLOCKTYPE = "furnace";
	}
	
	public SpecialBlock(Chest chest, boolean extraChest){
		BLOCK = chest.getBlock();
		for(int A = 0; A < chest.getInventory().getSize(); A++){
			INVENTORY.put(A, chest.getInventory().getItem(A));
		}
		if (extraChest){
			BLOCKTYPE = "double chest";
		}else{
			BLOCKTYPE = "chest";
		}
		DOUBLECHEST = extraChest;
	}
	
	public SpecialBlock(Dropper drop){
		BLOCK = drop.getBlock();
		for(int A = 0; A < drop.getInventory().getSize(); A++){
			INVENTORY.put(A, drop.getInventory().getItem(A));
		}
		BLOCKTYPE = "dropper";
	}
	
	public SpecialBlock(Hopper hop){
		BLOCK = hop.getBlock();
		for(int A = 0; A < hop.getInventory().getSize(); A++){
			INVENTORY.put(A, hop.getInventory().getItem(A));
		}
		BLOCKTYPE = "hopper";
	}
	
	public SpecialBlock(Dispenser disp){
		BLOCK = disp.getBlock();
		for(int A = 0; A < disp.getInventory().getSize(); A++){
			INVENTORY.put(A, disp.getInventory().getItem(A));
		}
		BLOCKTYPE = "dispenser";
	}
	
	public Block getBlock(){
		return BLOCK;
	}
	
	public boolean isRightChest(){
		return DOUBLECHEST;
	}
	
	public String getType(){
		return BLOCKTYPE;
	}
	
	public String getLine(int number){
		if (number == 1){
			return LINE1;
		}else if (number == 2){
			return LINE2;
		}else if (number == 3){
			return LINE3;
		}else if (number == 4){
			return LINE4;
		}
		return null;
	}

	public Map<Integer, ItemStack> getItems(){
		return INVENTORY;
	}
	
	public void clearInventory(Block block){
		Block bBlock = block;
		if (bBlock.getState() instanceof Furnace){
			Furnace furn = (Furnace) bBlock.getState();
			furn.getInventory().clear();
		}
		if (bBlock.getState() instanceof Chest){
			Chest chest = (Chest) bBlock.getState();
			chest.getInventory().clear();
		}
		if (bBlock.getState() instanceof Dispenser){
			Dispenser disp = (Dispenser)bBlock.getState();
			disp.getInventory().clear();
		}
		if (bBlock.getState() instanceof Hopper){
			Hopper hop = (Hopper)bBlock.getState();
			hop.getInventory().clear();
		}
		if (bBlock.getState() instanceof Dropper){
			Dropper drop = (Dropper) bBlock.getState();
			drop.getInventory().clear();
		}
	}
	
	public void setInventory(Block block){
		if (block.getState() instanceof Furnace){
			Furnace furn = (Furnace)block.getState();
			FurnaceInventory inv = furn.getInventory();
			Map<Integer, ItemStack> stack = getItems();
			for(Entry<Integer, ItemStack> entry : stack.entrySet()){
				if (entry.getKey() == 1){
					inv.setFuel(entry.getValue());
				}
				if (entry.getKey() == 2){
					inv.setResult(entry.getValue());
				}
				if (entry.getKey() == 3){
					inv.setSmelting(entry.getValue());
				}
			}
		}
		if (block.getState() instanceof Chest){
			Chest chest = (Chest) block.getState();
			Inventory inv = chest.getInventory();
			for(Entry<Integer, ItemStack> entry : getItems().entrySet()){
				if (isRightChest()){
					int A = entry.getKey();
					if ((A > 26) && (A <= 64)){
						inv.setItem(A - 27, entry.getValue());
					}
				}else{
					int A = entry.getKey(); 
					if (A <= 26){
						inv.setItem(A, entry.getValue());
					}
				}
			}
		}
		if (block.getState() instanceof Hopper){
			Hopper hop = (Hopper)block.getState();
			Inventory inv = hop.getInventory();
			for(Entry<Integer, ItemStack> entry : getItems().entrySet()){
				inv.setItem(entry.getKey(), entry.getValue());
			}
		}
		if (block.getState() instanceof Dropper){
			Dropper drop = (Dropper)block.getState();
			Inventory inv = drop.getInventory();
			for(Entry<Integer, ItemStack> entry : getItems().entrySet()){
				inv.setItem(entry.getKey(), entry.getValue());
			}
		}
		if (block.getState() instanceof Dispenser){
			Dispenser disp = (Dispenser)block.getState();
			Inventory inv = disp.getInventory();
			for(Entry<Integer, ItemStack> entry : getItems().entrySet()){
				inv.setItem(entry.getKey(), entry.getValue());
			}
		}
		if (block.getState() instanceof Sign){
			Sign sign = (Sign) block.getState();
			sign.setLine(0, getLine(1));
			sign.setLine(1, getLine(2));
			sign.setLine(2, getLine(3));
			sign.setLine(3, getLine(4));
			sign.update();
			if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")){
				Vessel vessel = Vessel.getVessel(sign);
				if (vessel != null){
					vessel.SIGN = sign;
					vessel.updateLocation(vessel.getTeleportLocation(), sign);
				}
			}
		}
	}
	
	public static SpecialBlock getSpecialBlock(Block block){
		if (block.getState() instanceof Sign){
			Sign sign = (Sign)block.getState();
			SpecialBlock sBlock = new SpecialBlock(sign);
			return sBlock;
		}else if (block.getState() instanceof Furnace){
			Furnace furn = (Furnace)block.getState();
			SpecialBlock sBlock = new SpecialBlock(furn);
			return sBlock;
		}else if (block.getState() instanceof Chest){
			Chest chest = (Chest)block.getState();
			if (chest.getInventory().getHolder() instanceof DoubleChest){
				DoubleChest dChest = (DoubleChest)chest.getInventory().getHolder();
				Chest lChest = (Chest)dChest.getLeftSide();
				if (lChest.equals(chest)){
					SpecialBlock block2 = new SpecialBlock((Chest)block.getState(), false);
					return block2;
				}else{
					SpecialBlock block2 = new SpecialBlock((Chest)block.getState(), true);
					return block2;
				}
			}else{
				SpecialBlock block2 = new SpecialBlock((Chest)block.getState(), false);
				return block2;
			}
		}else if (block.getState() instanceof Dropper){
			SpecialBlock block2 = new SpecialBlock((Dropper)block.getState());
			return block2;
		}else if (block.getState() instanceof Hopper){
			SpecialBlock block2 = new SpecialBlock((Hopper)block.getState());
			return block2;
		}else if (block.getState() instanceof Dispenser){
			SpecialBlock block2 = new SpecialBlock((Dispenser)block.getState());
			return block2;
		}
		return null;
	}
}

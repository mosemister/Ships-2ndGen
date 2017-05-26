package MoseShipsBukkit.Vessel.RootType.DataShip.Data.Required;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;

import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Movement.MovingBlock;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Movement.Result.MovementResult;
import MoseShipsBukkit.ShipBlock.BlockState;
import MoseShipsBukkit.Utils.Lists.MovingBlockList;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.RequirementData;

public class FuelRequirement implements RequirementData {

	BlockState[] g_state = new BlockState[0];
	int g_take_amount = 0;
	
	public String LOADER_STATES = "FuelRequirement.Blocks";
	public String LOADER_TAKE_AMOUNT = "FuelRequirement.TakeAmount";

	public BlockState[] getStates() {
		return g_state;
	}

	public FuelRequirement setStates(BlockState... map) {
		g_state = map;
		return this;
	}
	
	public int getTakeAmount(){
		return g_take_amount;
	}
	
	public FuelRequirement setTakeAmount(int take){
		g_take_amount = take;
		return this;
	}
	
	public MovingBlockList getFuelHolders(MovingBlockList blocks){
		MovingBlockList fur = blocks.filterBlocks(new BlockState(Material.FURNACE));
		fur.addAll(blocks.filterBlocks(new BlockState(Material.BURNING_FURNACE)));
		return fur;
	}

	@Override
	public Optional<FailedMovement> hasRequirements(LiveShip ship, MovingBlockList blocks) {
		MovingBlockList furnaces = getFuelHolders(blocks);
		Map<MovingBlock, List<ItemStack>> map = getFuel(furnaces);
		boolean check = true;
		int count = 0;
		Iterator<Entry<MovingBlock, List<ItemStack>>> entrySet = map.entrySet().iterator();
		while(entrySet.hasNext()){
			Entry<MovingBlock, List<ItemStack>> entry = entrySet.next();
			for(ItemStack item : entry.getValue()){
				int countT = count + item.getAmount();
				if((g_take_amount > countT) && (check)){
					count = g_take_amount;
				}else if((g_take_amount <= countT) && (check)){
					check = false;
				}else{
					map.remove(entry.getKey(), entry.getValue());
				}
			}
		}
		if(g_take_amount > count){
			return Optional.of(new FailedMovement(ship, MovementResult.OUT_OF_FUEL, true));
		}
		count = g_take_amount;
		for(Entry<MovingBlock, List<ItemStack>> entry : map.entrySet()){
			for(ItemStack item : entry.getValue()){
				if(count > item.getAmount()){
					count = count - item.getAmount();
					item.setAmount(0);
				}else{
					item.setAmount(item.getAmount() - count);
					return Optional.empty();
				}
			}
		}
		return Optional.of(new FailedMovement(ship, MovementResult.FUEL_REMOVE_ERROR, true));
	}

	@Override
	public void applyFromShip(BasicConfig config) {
		g_state = BlockState.getStates(config.getList(String.class, LOADER_STATES));
		g_take_amount = config.get(Integer.class, LOADER_TAKE_AMOUNT);
	}
	
	@Override
	public void saveShip(BasicConfig config) {
		config.set(g_state, LOADER_STATES);
		config.set(g_take_amount, LOADER_TAKE_AMOUNT);
	}
	
	@Override
	public Map<String, Object> getInfo(LiveShip ship) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Take Amount", g_take_amount);
		return map;
	}
	
	private Map<MovingBlock, List<ItemStack>> getFuel(MovingBlockList blocks){
		Map<MovingBlock, List<ItemStack>> map = new HashMap<MovingBlock, List<ItemStack>>();
		for(MovingBlock block : blocks){
			org.bukkit.block.BlockState state = block.getSnapshot().getLocation().getBlock().getState();
			if(state instanceof Furnace){
				Furnace furn = (Furnace)state;
				FurnaceInventory inv = furn.getInventory();
				List<ItemStack> list = new ArrayList<ItemStack>();
				if (inv.getFuel() != null){
					ItemStack item = inv.getFuel();
					if(isFuel(item)){
						list.add(item);
					}
				}
				if (inv.getSmelting() != null){
					ItemStack item = inv.getSmelting();
					if(isFuel(item)){
						list.add(item);
					}
				}
				if (inv.getResult() != null){
					ItemStack item = inv.getResult();
					if(isFuel(item)){
						list.add(item);
					}
				}
				map.put(block, list);
			}
		}
		return map;
	}
	
	private boolean isFuel(ItemStack item){
		for (BlockState entry : g_state){
			if(entry.looseMatch(item)){
				return true;
			}
		}
		return false;
	}

}

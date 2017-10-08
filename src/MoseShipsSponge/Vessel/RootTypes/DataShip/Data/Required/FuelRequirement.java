package MoseShipsSponge.Vessel.RootTypes.DataShip.Data.Required;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.carrier.Furnace;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Configs.BasicConfig;
import MoseShipsSponge.Movement.MovingBlock;
import MoseShipsSponge.Movement.Result.FailedMovement;
import MoseShipsSponge.Movement.Result.MovementResult;
import MoseShipsSponge.Utils.BlockUtil;
import MoseShipsSponge.Utils.Lists.MovingBlockList;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;
import MoseShipsSponge.Vessel.RootTypes.DataShip.Data.RequirementData;

public class FuelRequirement implements RequirementData {

	ItemStackSnapshot[] g_state = new ItemStackSnapshot[0];
	int g_take_amount = 0;
	
	public Object[] LOADER_STATES = {"FuelRequirement", "Blocks"};
	public Object[] LOADER_TAKE_AMOUNT = {"FuelRequirement", "TakeAmount"};
	
	public ItemStackSnapshot[] getStates() {
		return g_state;
	}

	public FuelRequirement setStates(ItemStackSnapshot... map) {
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
	
	private MovingBlockList getFuelHolders(MovingBlockList blocks){
		MovingBlockList fur = blocks.filterBlocks((BlockTypes.FURNACE.getAllBlockStates()));
		fur.addAll(blocks.filterBlocks(BlockTypes.LIT_FURNACE.getAllBlockStates()));
		return fur;
	}
	
	@Override
	public Optional<FailedMovement> hasRequirement(LiveShip ship, MovingBlockList blocks) {
		MovingBlockList furnaces = getFuelHolders(blocks);
		Map<MovingBlock, List<ItemStack>> map = getFuel(furnaces);
		boolean check = true;
		int count = 0;
		
		Iterator<Entry<MovingBlock, List<ItemStack>>> entrySet = map.entrySet().iterator();
		while(entrySet.hasNext()){
			Entry<MovingBlock, List<ItemStack>> entry = entrySet.next();
			for(int A = 0; A < entry.getValue().size(); A++){
				ItemStack item = entry.getValue().get(A);
				int countT = count + item.getQuantity();
				if((g_take_amount > countT) && (check)){
					count = countT;
				}else if((g_take_amount == countT) && (check)){
					count = g_take_amount;
					check = false;
					break;
				}else if((g_take_amount < countT) && (check)){
					count = countT;
					check = false;
					break;
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
				if(count > item.getQuantity()){
					count = count - item.getQuantity();
					item.setQuantity(0);
				}else{
					item.setQuantity(item.getQuantity() - count);
					return Optional.empty();
				}
			}
		}
		return Optional.of(new FailedMovement(ship, MovementResult.FUEL_REMOVE_ERROR, true));
	}

	@Override
	public void applyFromShip(BasicConfig config) {
		g_state = BlockUtil.deseriaItemSnapshot(config.getList(n -> n.getString(), LOADER_STATES));
		g_take_amount = config.get(Integer.class, LOADER_TAKE_AMOUNT);
	}

	@Override
	public void saveShip(BasicConfig config) {
		config.set(BlockUtil.serial(g_state), LOADER_STATES);
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
			Location<World> loc = block.getOrigin();
			Optional<TileEntity> opTile = loc.getTileEntity();
			if(!opTile.isPresent()){
				continue;
			}
			TileEntity tile = opTile.get();
			if(!(tile instanceof Furnace)){
				continue;
			}
			Furnace furn = (Furnace)tile;
			Inventory inventory = furn.getInventory();
			List<ItemStack> items = new ArrayList<>();
			for (Inventory invSlot : inventory.slots()){
				if(!(invSlot instanceof Slot)){
					continue;
				}
				Slot slot = (Slot)invSlot;
				Optional<ItemStack> opStack = slot.peek();
				if(!opStack.isPresent()){
					continue;
				}
				ItemStack item = opStack.get();
				if(isFuel(item)){
					items.add(item);
				}
			}
			map.put(block, items);
		}
		return map;
	}
	
	private boolean isFuel(ItemStack item){
		for (ItemStackSnapshot entry : g_state){
			if(item.equalTo(entry.createStack())){
				return true;
			}
		}
		return false;
	}

}

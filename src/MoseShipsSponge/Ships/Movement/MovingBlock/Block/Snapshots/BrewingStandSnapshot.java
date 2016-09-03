package MoseShipsSponge.Ships.Movement.MovingBlock.Block.Snapshots;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.BrewingStand;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;

import MoseShipsSponge.Ships.Movement.MovingBlock.Block.BlockSnapshot;
import MoseShipsSponge.Ships.Movement.MovingBlock.Block.SpecialSnapshot;

public class BrewingStandSnapshot extends BlockSnapshot implements SpecialSnapshot{

	ItemStack fuel, ingredient;
	
	protected BrewingStandSnapshot(BlockState state) {
		super(state);
	}

	@Override
	public void onRemove(Block block) {
		if(block.getState() instanceof BrewingStand){
			BrewingStand stand = (BrewingStand)block.getState();
			BrewerInventory inv = stand.getInventory();
			fuel = inv.getFuel();
			ingredient = inv.getIngredient();
		}
		
	}

	@Override
	public void onPlace(Block block) {
		if(block.getState() instanceof BrewingStand){
			BrewingStand stand = (BrewingStand)block.getState();
			BrewerInventory inv = stand.getInventory();
			inv.setFuel(fuel);
			inv.setIngredient(ingredient);
		}
	}
	
	

}

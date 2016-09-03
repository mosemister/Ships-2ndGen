package MoseShipsSponge.Ships.Movement.MovingBlock.Block.Snapshots;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.FlowerPot;
import org.bukkit.material.MaterialData;

import MoseShipsSponge.Ships.Movement.MovingBlock.Block.AttachableSnapshot;
import MoseShipsSponge.Ships.Movement.MovingBlock.Block.BlockSnapshot;
import MoseShipsSponge.Ships.Movement.MovingBlock.Block.SpecialSnapshot;

public class PotSnapshot extends BlockSnapshot implements SpecialSnapshot, AttachableSnapshot{

	MaterialData g_data;
	
	protected PotSnapshot(BlockState state) {
		super(state);
	}

	@Override
	public void onRemove(Block block) {
		if(block.getState() instanceof FlowerPot){
			FlowerPot pot = (FlowerPot)block.getState();
			g_data = pot.getContents();
		}
	}

	@Override
	public void onPlace(Block block) {
		if(block.getState() instanceof FlowerPot){
			FlowerPot pot = (FlowerPot)block.getState();
			pot.setContents(g_data);
		}
	}

}

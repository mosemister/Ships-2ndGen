package MoseShipsSponge.Ships.Movement.MovingBlock.Block.Snapshots;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Jukebox;

import MoseShipsSponge.Ships.Movement.MovingBlock.Block.BlockSnapshot;
import MoseShipsSponge.Ships.Movement.MovingBlock.Block.SpecialSnapshot;

public class JukeBoxSnapshot extends BlockSnapshot implements SpecialSnapshot{

	Material g_material;
	
	protected JukeBoxSnapshot(BlockState state) {
		super(state);
	}

	@Override
	public void onRemove(Block block) {
		if(block.getState() instanceof Jukebox){
			Jukebox box = (Jukebox)block.getState();
			g_material = box.getPlaying();
		}
	}

	@Override
	public void onPlace(Block block) {
		if(block.getState() instanceof Jukebox){
			Jukebox box = (Jukebox)block.getState();
			box.setPlaying(g_material);
		}
	}

}

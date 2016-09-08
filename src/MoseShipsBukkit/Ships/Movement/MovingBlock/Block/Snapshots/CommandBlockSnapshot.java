package MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CommandBlock;

import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.BlockSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.SpecialSnapshot;

public class CommandBlockSnapshot extends BlockSnapshot implements SpecialSnapshot{

	String g_command;
	
	protected CommandBlockSnapshot(BlockState state) {
		super(state);
	}

	@Override
	public void onRemove(Block block) {
		if(block.getState() instanceof CommandBlock){
			CommandBlock cmdBlock = (CommandBlock)block.getState();
			g_command = cmdBlock.getCommand();
		}
	}

	@Override
	public void onPlace(Block block) {
		if(block.getState() instanceof CommandBlock){
			CommandBlock cmdBlock = (CommandBlock)block.getState();
			cmdBlock.setCommand(g_command);
		}
	}

}

package MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CommandBlock;

import MoseShipsBukkit.ShipBlock.Snapshot.BlockSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.SpecialSnapshot;

public class CommandBlockSnapshot extends BlockSnapshot implements SpecialSnapshot {

	String g_command;

	public CommandBlockSnapshot(BlockState state) {
		super(state);
	}

	@Override
	public void onRemove(Block block) {
		if (block.getState() instanceof CommandBlock) {
			CommandBlock cmdBlock = (CommandBlock) block.getState();
			g_command = cmdBlock.getCommand();
		}
	}

	@Override
	public void onPlace(Block block) {
		if (block.getState() instanceof CommandBlock) {
			CommandBlock cmdBlock = (CommandBlock) block.getState();
			cmdBlock.setCommand(g_command);
			cmdBlock.update();
		}
	}

}

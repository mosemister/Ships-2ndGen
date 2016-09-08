package MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots;

import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;

import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.AttachableSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.BlockSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.SpecialSnapshot;

public class SkullSnapshot extends BlockSnapshot implements SpecialSnapshot, AttachableSnapshot{

	OfflinePlayer g_owner;
	BlockFace g_rotation;
	SkullType g_type;
	
	protected SkullSnapshot(BlockState state) {
		super(state);
	}

	@Override
	public void onRemove(Block block) {
		if(block.getState() instanceof Skull){
			Skull skull = (Skull)block.getState();
			g_owner = skull.getOwningPlayer();
			g_rotation = skull.getRotation();
			g_type = skull.getSkullType();
		}
		
	}

	@Override
	public void onPlace(Block block) {
		if(block.getState() instanceof Skull){
			Skull skull = (Skull)block.getState();
			skull.setOwningPlayer(g_owner);
			skull.setRotation(g_rotation);
			skull.setSkullType(g_type);
		}
	}

}

package MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots;

import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.banner.Pattern;

import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.BlockSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.RotatableSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.SpecialSnapshot;

public class BannerSnapshot extends BlockSnapshot implements RotatableSnapshot, SpecialSnapshot{

	DyeColor colour;
	List<Pattern> patterns;
	
	protected BannerSnapshot(BlockState state) {
		super(state);
	}

	@Override
	public void onRemove(Block block) {
		if(block.getState() instanceof Banner){
			Banner banner = (Banner)block.getState();
			colour = banner.getBaseColor();
			patterns = banner.getPatterns();
		}
		
	}

	@Override
	public void onPlace(Block block) {
		if(block.getState() instanceof Banner){
			Banner banner = (Banner)block.getState();
			banner.setBaseColor(colour);
			banner.setPatterns(patterns);
			banner.update();
		}
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public byte getRotateLeft() {
		byte data = getData().getData();
		switch(data){
		case 0: return 4;
		case 4: return 8;
		case 8: return 12;
		case 12: return 0;
		case 14: return 2;
		case 10: return 14;
		case 6: return 10;
		case 2: return 6;
		}
		return 0;
	}

	@SuppressWarnings("deprecation")
	@Override
	public byte getRotateRight() {
		byte data = getData().getData();
		switch(data){
		case 4: return 0;
		case 8: return 4;
		case 12: return 8;
		case 0: return 12;
		case 2: return 14;
		case 14: return 10;
		case 10: return 6;
		case 6: return 2;
		}
		return 0;
	}

}

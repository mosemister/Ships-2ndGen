package MoseShipsBukkit.Ships.Movement.MovementAlgorithm.Types;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import MoseShipsBukkit.ShipsMain;
import MoseShipsBukkit.Configs.Files.ShipsConfig;
import MoseShipsBukkit.Ships.Movement.MovementAlgorithm.MovementAlgorithm;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;

public class Ships6Movement implements MovementAlgorithm {

	@Override
	public void move(final LoadableShip vessel, final List<MovingBlock> blocks) {
		ShipsConfig config = ShipsConfig.CONFIG;
		long repeate = config.get(Integer.class, ShipsConfig.PATH_STRUCTURE_STRUCTURELIMITS_TRACKREPEATE);
		int stackSize = 300;
		final List<List<MovingBlock>> list = new ArrayList<List<MovingBlock>>();
		List<MovingBlock> toAdd = new ArrayList<MovingBlock>();
		for(int A = 0; A < blocks.size(); A++){
			MovingBlock block = blocks.get(A);
			toAdd.add(block);
			if(toAdd.size() == stackSize){
				list.add(toAdd);
				toAdd = new ArrayList<MovingBlock>();
			}
		}
		
		for(int A = 0; A < list.size(); A++){
			final int B = A;
			Bukkit.getScheduler().scheduleSyncDelayedTask(ShipsMain.getPlugin(), new Runnable(){

				@Override
				public void run() {
					Ships5Movement.SHIPS5.move(vessel, list.get(B));
				}
				
			}, (repeate*B));
		}

	}

	@Override
	public String getName() {
		return "Ships 6";
	}

}

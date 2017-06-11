package MoseShipsSponge.Movement.Result;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Movement.MovingBlock;
import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class CollideWithErrorMovementResult implements MovementResult<List<MovingBlock>>{

	@Override
	public void sendMessage(LiveShip ship, CommandSource source, List<MovingBlock> value) {
		if(!(source instanceof Player)){
			source.sendMessage(ShipsMain.format("Detection ahead", true));
			return;
		}
		final Player player = (Player)source;
		player.sendMessage(ShipsMain.format("Detection ahead. They are bedrock for 3 seconds.", true));
		for(MovingBlock block : value){
			player.sendBlockChange(block.getMovingTo().getBlockPosition(), BlockTypes.BEDROCK.getDefaultState());
		}
		Sponge.getScheduler().createTaskBuilder().delay(3, TimeUnit.SECONDS).execute(new Runnable(){

			@Override
			public void run() {
				for(MovingBlock block : value){
					Location<World> block2 = block.getMovingTo();
					player.resetBlockChange(block2.getBlockPosition());
				}
				
			}
			
		}).submit(ShipsMain.getPlugin());
	}


}

package MoseShipsSponge.Causes;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;

import MoseShips.Maps.OrderedMap;
import MoseShips.Stores.TwoStore;
import MoseShipsSponge.ShipsMain;
import MoseShipsSponge.Ships.Movement.MovingBlock.MovingBlock;

public class FailedCause {
	
	OrderedMap<FailedKeys<? extends Object>, Object> CAUSES = new OrderedMap<>();
	
	public Map<FailedKeys<? extends Object>, Object> getCauses(){
		return CAUSES;
	}
	
	@SuppressWarnings("unchecked")
	public <T> Optional<T> get(FailedKeys<T> key){
		Object obj = CAUSES.get(key);
		return Optional.of((T)obj);
	}
		
	public static abstract class FailedKeys <T extends Object>{
		
		public static FailedKeys<List<MovingBlock>> COLLIDE = new FailedKeys<List<MovingBlock>>(){

			@SuppressWarnings("unchecked")
			@Override
			public void sendMessage(Player player, Object value) {
				if(value instanceof List){
					player.sendMessage(ShipsMain.format("Detection ahead. They are bedrock for 2 seconds", true));
					List<MovingBlock> list = (List<MovingBlock>)value;
					list.stream().forEach(b -> {
						player.sendBlockChange(b.getMovingTo().getBlockPosition(), b.getMovingTo().getBlock());
					});
					Task.Builder builder = ShipsMain.getPlugin().getGame().getScheduler().createTaskBuilder();
					builder.delay(2, TimeUnit.SECONDS);
					builder.execute(new Runnable(){

						@Override
						public void run() {
							list.stream().forEach(b ->{
								player.resetBlockChange(b.getMovingTo().getBlockPosition());
							});
							
						}
						
					});
				}
				
			}
			
		};
		public static FailedKeys<Boolean> AUTO_PILOT_OUT_OF_MOVES = new FailedKeys<Boolean>(){

			@Override
			public void sendMessage(Player player, Object value) {
				player.sendMessage(ShipsMain.format("AutoPilot ran out of moves", true));
			}
			
		};
		public static FailedKeys<Boolean> OUT_OF_FUEL = new FailedKeys<Boolean>(){

			@Override
			public void sendMessage(Player player, Object value) {
				player.sendMessage(ShipsMain.format("Out of fuel", true));
			}
			
		};
		public static FailedKeys<BlockState> MISSING_REQUIRED_BLOCK = new FailedKeys<BlockState>(){

			@Override
			public void sendMessage(Player player, Object value) {
				if(value instanceof BlockState){
					BlockState state = (BlockState)value;
					player.sendMessage(ShipsMain.format("You are missing " + state.getName() + " from your ship", true));
				}
				
			}
			
		};
		public static FailedKeys<Integer> NOT_ENOUGH_BLOCKS = new FailedKeys<Integer>(){

			@Override
			public void sendMessage(Player player, Object value) {
				if(value instanceof Integer){
					int blocks = (int)value;
					player.sendMessage(ShipsMain.format("You need " + blocks + " more blocks", true));
				}
				
			}
			
		};
		public static FailedKeys<Integer> TOO_MANY_BLOCKS = new FailedKeys<Integer>(){

			@Override
			public void sendMessage(Player player, Object value) {
				if(value instanceof Integer){
					int blocks = (int)value;
					player.sendMessage(ShipsMain.format("You need " + blocks + " less blocks", true));
				}
			}
			
		};
		public static FailedKeys<TwoStore<BlockState, Integer>> NOT_ENOUGH_PERCENT = new FailedKeys<TwoStore<BlockState, Integer>>(){

			@SuppressWarnings("unchecked")
			@Override
			public void sendMessage(Player player, Object value) {
				if(value instanceof TwoStore){
					TwoStore<BlockState, Integer> value2 = (TwoStore<BlockState, Integer>)value;
					player.sendMessage(ShipsMain.format("You need " + value2.getSecond() + " more blocks of " + value2.getFirst().getName(), true));
				}
			}
			
		};
				
		public FailedKeys(){
		}
		
		public abstract void sendMessage(Player player, Object value);
	}
	
	public static class CauseKeys{
		public static final String MOVING_BLOCKS = "MovingBlock";
	}
}

package MoseShipsBukkit.Causes.Failed;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import MoseShips.Stores.TwoStore;
import MoseShipsBukkit.ShipsMain;
import MoseShipsBukkit.Configs.Files.ShipsConfig;
import MoseShipsBukkit.Ships.Movement.AutoPilot.AutoPilot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveShip;
import MoseShipsBukkit.Utils.State.BlockState;

public abstract class MovementResult <T extends Object> {
	
	public static final MovementResult<Boolean> FUEL_REMOVE_ERROR = new FuelRemoveErrorMovementResult();
	public static final MovementResult<Boolean> NOT_IN_WATER_ERROR = new NotInWaterErrorMovementResult();
	public static final MovementResult<AutoPilot> AUTO_PILOT_OUT_OF_MOVES = new AutoPilotOutOfMovesErrorMovementResult();
	public static final MovementResult<Boolean> OUT_OF_FUEL = new OutOfFuelErrorMovementResult();
	public static final MovementResult<BlockState> MISSING_REQUIRED_BLOCK = new MissingRequiredBlockErrorMovementResult();
	public static final MovementResult<Boolean> NO_BLOCKS = new NoBlocksErrorMovementResult();
	public static final MovementResult<Integer> NOT_ENOUGH_BLOCKS = new NotEnoughBlocksErrorMovementResult();
	public static final MovementResult<Integer> TOO_MANY_BLOCKS = new TooManyBlocksErrorMovementResult();
	public static final MovementResult<TwoStore<BlockState, Float>> NOT_ENOUGH_PERCENT = new NotEnoughPercentageErrorMovementResult();
	public static final MovementResult<Boolean> PLUGIN_CANCELLED = new PluginCancelledErrorMovementResult();
	public static final MovementResult<List<MovingBlock>> COLLIDE_WITH = new CollideWithErrorMovementResult();
	
	public abstract void sendMessage(LiveShip ship, Player player, T value);

	public static class FuelRemoveErrorMovementResult extends MovementResult<Boolean> {

		@Override
		public void sendMessage(LiveShip ship, Player player, Boolean value) {
			player.sendMessage("Ships failed to collect fuel to remove");
		}
		
	}
	
	public static class NotInWaterErrorMovementResult extends MovementResult<Boolean> {
		
		@Override
		public void sendMessage(LiveShip ship, final Player player, Boolean value) {
			player.sendMessage(ShipsMain.format("Ship is not in water", true));
		}
		
	}
	
	public static class AutoPilotOutOfMovesErrorMovementResult extends MovementResult<AutoPilot>{

		@Override
		public void sendMessage(LiveShip ship, Player player, AutoPilot value) {
			player.sendMessage(ShipsMain.format("AutoPilot ran out of moves", true));
		}
		
	}
	
	public static class OutOfFuelErrorMovementResult extends MovementResult<Boolean>{

		@Override
		public void sendMessage(LiveShip ship, Player player, Boolean value) {
			player.sendMessage(ShipsMain.format("Out of fuel", true));
		}
		
	}
	
	public static class MissingRequiredBlockErrorMovementResult extends MovementResult<BlockState>{

		@Override
		public void sendMessage(LiveShip ship, Player player, BlockState state) {
			if (state.getMaterial().equals(Material.FIRE)) {
				player.sendMessage("You are missing a burner from your ship");
			} else {
				player.sendMessage(ShipsMain.format(
						"You are missing " + state.getMaterial() + ":" + state.getData() + " from your ship",
						true));
			}
		}
		
	}
	
	public static class NoBlocksErrorMovementResult extends MovementResult<Boolean>{

		@Override
		public void sendMessage(LiveShip ship, Player player, Boolean value) {
			String message = ShipsConfig.CONFIG.get(String.class, ShipsConfig.PATH_MESSAGE_SIZE_NONE);
			if(message.contains("%Ship%")){
				message = message.replace("%Ship%", ship.getName());
			}
			player.sendMessage(message);
		}
		
	}
	
	public static class NotEnoughBlocksErrorMovementResult extends MovementResult<Integer>{

		@Override
		public void sendMessage(LiveShip ship, Player player, Integer blocks) {
			player.sendMessage(ShipsMain.format("You need " + blocks + " more blocks", true));
			
		}
		
	}
	
	public static class TooManyBlocksErrorMovementResult extends MovementResult<Integer>{

		@Override
		public void sendMessage(LiveShip ship, Player player, Integer blocks) {
			player.sendMessage(ShipsMain.format("You need " + blocks + " less blocks", true));
		}
		
	}
	
	public static class NotEnoughPercentageErrorMovementResult extends MovementResult<TwoStore<BlockState, Float>>{

		@Override
		public void sendMessage(LiveShip ship, Player player, TwoStore<BlockState, Float> value) {
			player.sendMessage(ShipsMain.format("You need " + value.getSecond() + " more blocks of "
					+ value.getFirst().getMaterial() + ":" + value.getFirst().getData(), true));
			
		}
		
	}
	
	public static class PluginCancelledErrorMovementResult extends MovementResult<Boolean>{

		@Override
		public void sendMessage(LiveShip ship, Player player, Boolean value) {
			if(value){
				player.sendMessage("A plugin cancelled the event");
			}
		}
		
	}
	
	public static class CollideWithErrorMovementResult extends MovementResult<List<MovingBlock>> {

		@SuppressWarnings("deprecation")
		@Override
		public void sendMessage(LiveShip ship, final Player player, final List<MovingBlock> list) {
			player.sendMessage(ShipsMain.format("Detection ahead. They are bedrock for 3 seconds", true));
			for (MovingBlock block : list) {
				player.sendBlockChange(block.getMovingTo(), Material.BEDROCK, (byte) 0);
			}
			Bukkit.getScheduler().scheduleSyncDelayedTask(ShipsMain.getPlugin(), new Runnable() {

				@Override
				public void run() {
					for (MovingBlock block : list) {
						Block block2 = block.getMovingTo().getBlock();
						player.sendBlockChange(block.getMovingTo(), block2.getType(), block2.getData());
					}

				}

			}, (3 * 20));
			
		}
	}

}

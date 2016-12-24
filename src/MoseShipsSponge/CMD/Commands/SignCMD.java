package MoseShipsSponge.CMD.Commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import MoseShipsSponge.ShipsMain;
import MoseShipsSponge.CMD.ShipsCMD;

public class SignCMD implements ShipsCMD.ShipsPlayerCMD{

	public SignCMD(){
		ShipsCMD.SHIPS_COMMANDS.add(this);
	}
	
	@Override
	public String[] getAliases() {
		String[] args = {"sign"};
		return args;
	}

	@Override
	public Text getDescription() {
		return Text.of("Sign tools");
	}

	@Override
	public String getPermission() {
		return null;
	}

	@Override
	public CommandResult execute(Player player, String... args) throws CommandException {
		if(args.length == 1){
			player.sendMessage(ShipsMain.formatCMDHelp("/ships sign track [time]"));
			return CommandResult.success();
		}else if (args[1].equalsIgnoreCase("track")){
			if(args.length == 2){
				track(player, 3);
			}else{
				try{
					track(player, Integer.parseInt(args[2]));
				}catch(NumberFormatException e){
					player.sendMessage(ShipsMain.format(args[2] + " is not a whole number", true));
				}
			}
		}
		return CommandResult.empty();
	}
	
	public void track(Player player, int sec){
		/*BlockRay<World> blockRay = BlockRay.from(player).filter(BlockRay.continueAfterFilter(BlockRay.onlyAirFilter(), 5)).build();
		
		while(blockRay.hasNext()){
			System.out.println("Next Block");
			BlockRayHit<World> hit = blockRay.next();
			Location<World> loc = hit.getLocation();
			Optional<TileEntity> opTile = loc.getTileEntity();
			if(opTile.isPresent()){
				TileEntity tile = opTile.get();
				if(tile instanceof Sign){
					Sign sign = (Sign)tile;
					Optional<ShipsSigns.SignType> sSign = ShipsSigns.getSignType(sign);
					if(sSign.isPresent()){
						Optional<LoadableShip> opShip = LoadableShip.getShip(loc, true);
						if(opShip.isPresent()){
							LoadableShip ship = opShip.get();
							final List<Location<World>> structure = ship.getBasicStructure();
							structure.stream().forEach(l -> player.sendBlockChange(l.getBlockPosition(), BlockTypes.BEDROCK.getDefaultState()));
							ShipsMain.getPlugin().getGame().getScheduler().createTaskBuilder().delay(sec, TimeUnit.SECONDS).execute(new Runnable(){

								@Override
								public void run() {
									structure.stream().forEach(l -> player.resetBlockChange(l.getBlockPosition()));
								}
								
							});
						}
					}
				}
			}
		}*/
	}

}

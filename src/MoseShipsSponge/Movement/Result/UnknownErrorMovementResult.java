package MoseShipsSponge.Movement.Result;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class UnknownErrorMovementResult implements MovementResult<Boolean>{

	@Override
	public void sendMessage(LiveShip ship, CommandSource source, Boolean value) {
		source.sendMessage(ShipsMain.format("A unknown error occured", true));
		Sponge.getServer().getConsole().sendMessage(Text.of("A Unknown Error Occured: Error code 'Move failed by running all the way through'"));
	}

}

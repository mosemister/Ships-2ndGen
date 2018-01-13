package MoseShipsSponge.Commands.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import MoseShipsSponge.Vessel.Common.OpenLoader.Loader;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class LiveShipElement extends CommandElement {

	public LiveShipElement(Text key) {
		super(key);
	}

	@Override
	public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
		List<String> list = new ArrayList<>();
		File[] files = Loader.getAllShipsFiles();
		for(File file : files) {
			list.add(file.getName().substring(0, file.getName().length() -4));
		}
		try {
			String peek = args.peek();
			return list.stream().filter(v -> peek.toLowerCase().contains(v.toLowerCase())).collect(Collectors.toList());
 		} catch (ArgumentParseException e) {
		}
		return list;
	}

	@Override
	protected LiveShip parseValue(CommandSource arg0, CommandArgs arg1) throws ArgumentParseException {
		String id = arg1.next();
		Optional<LiveShip> opShip = Loader.safeLoadShip(id);
		if(opShip.isPresent()) {
			return opShip.get();
		}
		throw arg1.createError(Text.builder("Ship does not exsit").color(TextColors.RED).build());
	}

}

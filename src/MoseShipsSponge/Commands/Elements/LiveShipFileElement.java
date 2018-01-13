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

import MoseShipsSponge.Vessel.Common.OpenLoader.Loader;

public class LiveShipFileElement extends CommandElement {

	public LiveShipFileElement(Text key) {
		super(key);
	}

	@Override
	public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
		List<String> ships = new ArrayList<>();
		for (File file : Loader.getAllShipsFiles()){
			ships.add(file.getName().substring(0, file.getName().length() - 4));
		}
		try {
			String peek = args.peek();
			return ships.stream().filter(s -> s.toLowerCase().contains(peek.toLowerCase())).collect(Collectors.toList());
		} catch (ArgumentParseException e) {
			return ships;
		}
	}

	@Override
	protected File parseValue(CommandSource src, CommandArgs args) throws ArgumentParseException {
		String next = args.next();
		Optional<File> opFile = Loader.getShipFile(next);
		if(opFile.isPresent()) {
			return opFile.get();
		}
		throw args.createError(Text.of("Could not find that ship"));
	}

}

package MoseShipsSponge.Commands.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import MoseShipsSponge.Vessel.Common.Static.StaticShipType;

public class ShipTypeElement extends CommandElement {

	public ShipTypeElement(Text key) {
		super(key);
	}

	@Override
	public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
		List<String> list = new ArrayList<>();
		String peek;
		try {
			peek = args.peek();
			StaticShipType.TYPES.stream().filter(t -> t.getName().toLowerCase().contains(peek.toLowerCase())).forEach(s -> list.add(s.getName()));
		} catch (ArgumentParseException e) {
			StaticShipType.TYPES.stream().forEach(s -> list.add(s.getName()));
		}
		return list;
	}

	@Override
	protected Object parseValue(CommandSource arg0, CommandArgs arg1) throws ArgumentParseException {
		String next = arg1.next();
		Optional<StaticShipType> opShip = StaticShipType.TYPES.stream().filter(s -> s.getName().toLowerCase().equals(next.toLowerCase())).findFirst();
		if(opShip.isPresent()) {
			return opShip.get();
		}
		throw arg1.createError(Text.builder("Ship type is not on this server").color(TextColors.RED).build());
	}

}

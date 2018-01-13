package MoseShipsSponge.Commands.Elements;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;

import MoseShipsSponge.Configs.BlockList.ListType;

public class ListTypeElement extends CommandElement {

	public ListTypeElement(Text key) {
		super(key);
	}

	@Override
	public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
		try {
			String peek = args.peek();
			List<String> list = new ArrayList<>();
			for(ListType type : ListType.values()) {
				if(type.name().toLowerCase().contains(peek.toLowerCase())) {
					list.add(type.name().toLowerCase());
				}
			}
			return list;
		} catch (ArgumentParseException e) {
			List<String> list = new ArrayList<>();
			for(ListType type : ListType.values()) {
				list.add(type.name().toLowerCase());
			}
			return list;
		}
	}

	@Override
	protected ListType parseValue(CommandSource arg0, CommandArgs arg1) throws ArgumentParseException {
		String list = arg1.next();
		for(ListType type : ListType.values()) {
			if(type.name().toLowerCase().equals(list.toLowerCase())) {
				return type;
			}
		}
		throw arg1.createError(Text.of("Unknown List Type"));
	}

}

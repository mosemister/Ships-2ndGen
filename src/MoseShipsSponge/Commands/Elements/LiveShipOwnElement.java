package MoseShipsSponge.Commands.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import MoseShipsSponge.Vessel.Common.OpenLoader.Loader;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class LiveShipOwnElement extends CommandElement {

	String permission;
	
	public LiveShipOwnElement(Text key) {
		this(key, null);
	}
	
	public LiveShipOwnElement(Text key, @Nullable String permissionOverride) {
		super(key);
		permission = permissionOverride;
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
			list = list.stream().filter(v -> peek.toLowerCase().contains(v.toLowerCase())).collect(Collectors.toList());
			if(src instanceof Player) {
				if((permission == null) && ((Player)src).hasPermission(permission)) {
					return list.stream().filter(s -> {
						Optional<LiveShip> opShip = Loader.safeLoadShip(s);
						if(!opShip.isPresent()) {
							return false;
						}
						LiveShip ship = opShip.get();
						if(ship.getOwner().isPresent() && ship.getOwner().get().equals((Player)src)) {
							return true;
						}
						return false;
					}).collect(Collectors.toList());
				}
			}
			return list;
		} catch (ArgumentParseException e) {
		}
		return list;
	}

	@Override
	protected Object parseValue(CommandSource arg0, CommandArgs arg1) throws ArgumentParseException {
		String id = arg1.next();
		Optional<LiveShip> opShip = Loader.safeLoadShip(id);
		if(opShip.isPresent()) {
			LiveShip ship = opShip.get();
			if((!(arg0 instanceof Player)) || ((permission != null) && ((Player)arg0).hasPermission(permission)) || (ship.getOwner().isPresent() && ship.getOwner().get().equals(arg0))) {
				
			}
		}
		throw arg1.createError(Text.builder("Ship does not exsit").color(TextColors.RED).build());
	
	}

}

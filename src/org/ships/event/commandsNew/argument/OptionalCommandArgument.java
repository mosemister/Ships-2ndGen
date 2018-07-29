package org.ships.event.commandsNew.argument;

import java.util.Optional;

import org.bukkit.command.CommandSender;
import org.ships.event.commandsNew.executor.CMDArgumentResults;

public interface OptionalCommandArgument<T> extends CommandArgument<T> {
	public Optional<T> getDefaultValue(CommandSender var1);

	@Override
	default public boolean canParse(CommandSender sender, CMDArgumentResults results, String toParse) {
		if (toParse.length() != 0) {
			return !this.onTab(sender, results, toParse).isEmpty();
		}
		if (this.getDefaultValue(sender).isPresent()) {
			return true;
		}
		return !this.onTab(sender, results, toParse).isEmpty();
	}
}

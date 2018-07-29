package org.ships.event.commandsNew.argument;

import java.util.List;
import java.util.Optional;

import org.bukkit.command.CommandSender;
import org.ships.event.commandsNew.executor.CMDArgumentResults;

public interface CommandArgument<T> {
	public String getAssignedKey();

	public CommandArgument<T> setAssignedKey(String var1);

	public List<String> onTab(CommandSender var1, CMDArgumentResults var2, String var3);

	public Optional<T> onParse(CommandSender var1, CMDArgumentResults var2, String var3);

	default public boolean canParse(CommandSender sender, CMDArgumentResults results, String toParse) {
		return !this.onTab(sender, results, toParse).isEmpty();
	}
}

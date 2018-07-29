package org.ships.event.commandsNew.argument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bukkit.command.CommandSender;
import org.ships.event.commandsNew.executor.CMDArgumentResults;

public class SingleCatagoryArgument implements CommandArgument<String> {
	String key;

	public SingleCatagoryArgument(String key) {
		this.key = key;
	}

	@Override
	public String getAssignedKey() {
		return this.key;
	}

	@Override
	public CommandArgument<String> setAssignedKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public List<String> onTab(CommandSender sender, CMDArgumentResults executor, String toParse) {
		if (this.key.toLowerCase().startsWith(toParse.toLowerCase())) {
			return Arrays.asList(this.key);
		}
		return new ArrayList<String>();
	}

	@Override
	public Optional<String> onParse(CommandSender sender, CMDArgumentResults exe, String toParse) {
		if (toParse.equalsIgnoreCase(this.key)) {
			return Optional.of(this.key);
		}
		return Optional.empty();
	}
}

package org.ships.event.commandsNew.argument.number;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bukkit.command.CommandSender;
import org.ships.event.commandsNew.argument.CommandArgument;
import org.ships.event.commandsNew.argument.OptionalCommandArgument;
import org.ships.event.commandsNew.executor.CMDArgumentResults;

public class OptionalIntergerArgument implements OptionalCommandArgument<Integer> {
	Integer defaultValue;
	int minValue;
	int maxValue;
	String key;

	public OptionalIntergerArgument(String key) {
		this(key, null);
	}

	public OptionalIntergerArgument(String key, Integer value) {
		this(key, 0, 0, value);
	}

	public OptionalIntergerArgument(String key, int min, int max, Integer value) {
		this.key = key;
		this.defaultValue = value;
		this.minValue = min;
		this.maxValue = max;
	}

	@Override
	public Optional<Integer> getDefaultValue(CommandSender sender) {
		return Optional.ofNullable(this.defaultValue);
	}

	@Override
	public String getAssignedKey() {
		return this.key;
	}

	@Override
	public CommandArgument<Integer> setAssignedKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public List<String> onTab(CommandSender sender, CMDArgumentResults results, String toParse) {
		try {
			int parsed = Integer.parseInt(toParse);
			if (this.maxValue == this.minValue) {
				if (this.defaultValue == null) {
					return new ArrayList<String>();
				}
				return Arrays.asList("" + parsed + "");
			}
			if (parsed < this.minValue) {
				int parsed2 = parsed;
				do {
					int fakeParse;
					if ((fakeParse = parsed2 * 10) > this.maxValue && parsed2 < this.minValue) {
						if (this.defaultValue == null) {
							return new ArrayList<String>();
						}
						return Arrays.asList(this.defaultValue + "");
					}
					if (fakeParse > this.maxValue && parsed2 < this.maxValue) {
						return Arrays.asList("" + parsed2 + "");
					}
					if (fakeParse >= this.maxValue)
						continue;
					parsed2 = fakeParse;
				} while (true);
			}
		} catch (NumberFormatException parsed) {
		}
		return new ArrayList<String>();
	}

	@Override
	public Optional<Integer> onParse(CommandSender sender, CMDArgumentResults results, String toParse) {
		try {
			int parsed = Integer.parseInt(toParse);
			return Optional.of(parsed);
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}
}
